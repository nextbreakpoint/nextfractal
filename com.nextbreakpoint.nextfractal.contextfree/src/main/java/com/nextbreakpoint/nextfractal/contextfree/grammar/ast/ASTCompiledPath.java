/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.grammar.ast;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FlagType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.PathOp;
import org.antlr.v4.runtime.Token;

public class ASTCompiledPath extends PathStorage {
	private static Long globalPathUID = new Long(100);
	private boolean complete;
	private PathStorage pathStorage;
	private InfoCache commandInfo;
	private ASTPathCommand terminalCommand;
	private boolean cached;
	private boolean useTerminal;
	private StackRule parameters;
	private Long pathUID;
	
	public ASTCompiledPath(CFDGDriver driver, Token location) {
		pathStorage = new PathStorage();
		commandInfo = new InfoCache();
		terminalCommand = new ASTPathCommand(driver, location);
		parameters = null;
		cached = false;
		useTerminal = false;
		pathUID = nextPathUID();
	}

	public InfoCache getCommandInfo() {
		return commandInfo;
	}

	public ASTPathCommand getTerminalCommand() {
		return terminalCommand;
	}

	public StackRule getParameters() {
		return parameters;
	}

	public void setParameters(StackRule parameters) {
		this.parameters = parameters;
	}

	public PathStorage getPath() {
		return pathStorage;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setIsComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isCached() {
		return cached;
	}

	public void setIsCached(boolean cached) {
		this.cached = cached;
	}

	public boolean useTerminal() {
		return useTerminal;
	}

	public void setUseTerminal(boolean useTerminal) {
		this.useTerminal = useTerminal;
	}

	public Long getPathUID() {
		return pathUID;
	}

	public void setPathUID(Long pathUID) {
		this.pathUID = pathUID;
	}

	public static Long nextPathUID() {
		return globalPathUID = new Long(globalPathUID.longValue() + 1);
	}

	public void finish(boolean setAttr, CFDGRenderer renderer) {
		if (!renderer.isClosed()) {
			getPath().endPoly(0);
			renderer.setClosed(true);
		}
		if (!renderer.isStop()) {
			getPath().startNewPath();
			renderer.setClosed(true);
		}
		renderer.setWantMoveTo(true);
		renderer.setIndex(getPath().getTotalVertices());
		if (setAttr && renderer.isWantCommand()) {
			useTerminal = true;
			renderer.setWantCommand(false);
		}
	}

	public void addPathOp(ASTPathOp pathOp, double[] data, Shape parent, boolean tr, CFDGRenderer renderer) {
		// Process the parameters for ARCTO/ARCREL
		double radiusX = 0.0, radiusY = 0.0, angle = 0.0;
		boolean sweep = (pathOp.getFlags() & FlagType.CF_ARC_CW.getMask()) == 0;
		boolean largeArc = (pathOp.getFlags() & FlagType.CF_ARC_LARGE.getMask()) != 0;
		Point2D.Double p0 = new Point2D.Double(data[0], data[1]);
		Point2D.Double p1 = new Point2D.Double(data[2], data[3]);
		Point2D.Double p2 = new Point2D.Double(data[4], data[4]);
		if (pathOp.getPathOp() == PathOp.ARCTO || pathOp.getPathOp() == PathOp.ARCREL) {
			if (pathOp.getArgCount() == 5) {
				radiusX = data[2];
				radiusY = data[3];
				angle = data[4] * 0.0174532925199;
			} else {
				radiusX = data[2];
				radiusY = data[2];
				angle = 0.0;
			}
			if (radiusX < 0.0 || radiusY < 0.0) {
				radiusX = Math.abs(radiusX);
				radiusY = Math.abs(radiusY);
				sweep = !sweep;
			}
		} else if (tr) {
			parent.getWorldState().getTransform().transform(p0, p0);
			parent.getWorldState().getTransform().transform(p1, p1);
			parent.getWorldState().getTransform().transform(p2, p2);
		}

		// If this is the first path operation following a path command then set the
		// path index used by subsequent path commands to the path sequence that the
		// current path operation is part of.
		// If this is not the first path operation following a path command then this
		// line does nothing.
		renderer.setIndex(renderer.getNextIndex());

		// If the op is anything other than a CLOSEPOLY then we are opening up a new path sequence.
		renderer.setClosed(false);
		renderer.setStop(false);

		// This new path op needs to be covered by a command, either from the cfdg file or default.
		renderer.setWantCommand(true);

		if (pathOp.getPathOp() == PathOp.CLOSEPOLY) {
			if (getPath().getTotalVertices() > 1 && getPath().isDrawing()) {
				// Find the MOVETO/MOVEREL that is the start of the current path sequence
				// and reset LastPoint to that.
				int last = getPath().getTotalVertices() - 1;
				int cmd = 0;
				for (int i = last - 1; i < last && getPath().isVertex(cmd = getPath().command(i)); i--) {
					if (cmd == 1) {
						getPath().vertex(renderer.getLastPoint());
						break;
					}
				}

				if (cmd != 1) {
					Logger.error("CLOSEPOLY: Unable to find a MOVETO/MOVEREL for start of path", pathOp.getLocation());
				}
			}

			getPath().closePolygon();
			renderer.setClosed(true);
			renderer.setWantMoveTo(true);
			return;
		}

		// Insert an implicit MOVETO unless the pathOp is a MOVETO/MOVEREL
		if (renderer.isWantMoveTo() && pathOp.getPathOp().ordinal() > PathOp.MOVEREL.ordinal()) {
			renderer.setWantMoveTo(false);
			getPath().moveTo(renderer.getLastPoint());
		}

		//TODO rivedere

		switch (pathOp.getPathOp()) {
			case MOVEREL:
				getPath().relToAbs(p0);
			case MOVETO:
				getPath().moveTo(p0);
				renderer.setWantMoveTo(false);
				break;
			case LINEREL:
				getPath().relToAbs(p0);
			case LINETO:
				getPath().lineTo(p0);
				break;
			case ARCREL:
				getPath().relToAbs(p0);
			case ARCTO:
				if (getPath().isVertex(getPath().lastVertex(p1)) || (tr && parent.getWorldState().getTransform().getDeterminant() < 1e-10)) {
					break;
				}
				// Transforming an arc as they are parameterized by AGG is VERY HARD.
				// So instead we insert the arc and then transform the bezier curves
				// that are used to approximate the arc. But first we have to inverse
				// transform the starting point to match the untransformed arc.
				// Afterwards the starting point is restored to its original value.
				if (tr) {
					int start = getPath().getTotalVertices() - 1;
					try {
						AffineTransform inverseTr = parent.getWorldState().getTransform().createInverse();
						getPath().transform(inverseTr, start);
						getPath().arcTo(radiusX, radiusY, angle, largeArc, sweep, p0);
						getPath().modifyVertex(start, p1);
						getPath().transform(parent.getWorldState().getTransform(), start + 1);
					} catch (NoninvertibleTransformException e) {
						Logger.fail("Cannot invert transform", pathOp.getLocation());
					}
				} else {
					getPath().arcTo(radiusX, radiusY, angle, largeArc, sweep, p0);
				}
			case CURVEREL:
				getPath().relToAbs(p0);
				getPath().relToAbs(p1);
				getPath().relToAbs(p2);
			case CURVETO:
				if ((pathOp.getFlags() & FlagType.CF_CONTINUOUS.getMask()) != 0 && getPath().isCurve(getPath().lastVertex(p2)) ) {
					Logger.error("Smooth curve operations must be preceded by another curve operation", pathOp.getLocation());
					break;
				}
				switch (pathOp.getArgCount()) {
					case 2:
						getPath().curve3(p0);
						break;
					case 4:
						if ((pathOp.getFlags() & FlagType.CF_CONTINUOUS.getMask()) != 0) {
							getPath().curve4(p1, p0);
						} else {
							getPath().curve3(p1, p0);
						}
						break;
					case 6:
						getPath().curve4(p1, p2, p0);
						break;
				}
				break;
			default:
				break;
		}

		getPath().lastVertex(renderer.getLastPoint());
	}
}
