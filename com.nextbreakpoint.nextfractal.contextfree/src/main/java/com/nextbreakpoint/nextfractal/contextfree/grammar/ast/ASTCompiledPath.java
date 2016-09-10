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
	private Dequeue commandInfo;
	private ASTPathCommand terminalCommand;
	private boolean cached;
	private boolean useTerminal;
	private CFStackRule parameters;
	private Long pathUID;
	
	public ASTCompiledPath(CFDGDriver driver, Token location) {
		commandInfo = new Dequeue();
		terminalCommand = new ASTPathCommand(driver, location);
		parameters = null;
		cached = false;
		useTerminal = false;
		pathUID = nextPathUID();
	}

	public Dequeue getCommandInfo() {
		return commandInfo;
	}

	public ASTPathCommand getTerminalCommand() {
		return terminalCommand;
	}

	public CFStackRule getParameters() {
		return parameters;
	}

	public void setParameters(CFStackRule parameters) {
		this.parameters = parameters;
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
			this.closePolygon();
			renderer.setClosed(true);
		}
		if (renderer.isStop()) {
			this.startNewPath();
			renderer.setClosed(true);
		}
		renderer.setWantMoveTo(true);
		renderer.setIndex(this.getTotalVertices());
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
			if (this.getTotalVertices() > 1 && this.isDrawing()) {
				// Find the MOVETO/MOVEREL that is the start of the current path sequence
				// and reset LastPoint to that.
				int last = this.getTotalVertices() - 1;
				int cmd = 0;
				for (int i = last - 1; i < last && this.isVertex(cmd = this.command(i)); i--) {
					if (cmd == 1) {
						this.addVertex(renderer.getLastPoint());
						break;
					}
				}

				if (cmd != 1) {
					Logger.error("CLOSEPOLY: Unable to find a MOVETO/MOVEREL for start of path", pathOp.getLocation());
				}
			}

			this.closePolygon();
			renderer.setClosed(true);
			renderer.setWantMoveTo(true);
			return;
		}

		// Insert an implicit MOVETO unless the pathOp is a MOVETO/MOVEREL
		//TODO completare replace ordinal()
		if (renderer.isWantMoveTo() && pathOp.getPathOp().ordinal() > PathOp.MOVEREL.ordinal()) {
			renderer.setWantMoveTo(false);
			this.moveTo(renderer.getLastPoint());
		}

		//TODO rivedere

		switch (pathOp.getPathOp()) {
			case MOVEREL:
				this.relToAbs(p0);
			case MOVETO:
				this.moveTo(p0);
				renderer.setWantMoveTo(false);
				break;
			case LINEREL:
				this.relToAbs(p0);
			case LINETO:
				this.lineTo(p0);
				break;
			case ARCREL:
				this.relToAbs(p0);
			case ARCTO:
				if (this.isVertex(this.lastVertex(p1)) || (tr && parent.getWorldState().getTransform().getDeterminant() < 1e-10)) {
					break;
				}
				// Transforming an arc as they are parameterized by AGG is VERY HARD.
				// So instead we insert the arc and then transform the bezier curves
				// that are used to approximate the arc. But first we have to inverse
				// transform the starting point to match the untransformed arc.
				// Afterwards the starting point is restored to its original value.
				if (tr) {
					int start = this.getTotalVertices() - 1;
					try {
						AffineTransform inverseTr = parent.getWorldState().getTransform().createInverse();
						this.transform(inverseTr, start);
						this.arcTo(radiusX, radiusY, angle, largeArc, sweep, p0);
						this.modifyVertex(start, p1);
						this.transform(parent.getWorldState().getTransform(), start + 1);
					} catch (NoninvertibleTransformException e) {
						Logger.fail("Cannot invert transform", pathOp.getLocation());
					}
				} else {
					this.arcTo(radiusX, radiusY, angle, largeArc, sweep, p0);
				}
			case CURVEREL:
				this.relToAbs(p0);
				this.relToAbs(p1);
				this.relToAbs(p2);
			case CURVETO:
				if ((pathOp.getFlags() & FlagType.CF_CONTINUOUS.getMask()) != 0 && this.isCurve(this.lastVertex(p2)) ) {
					Logger.error("Smooth curve operations must be preceded by another curve operation", pathOp.getLocation());
					break;
				}
				switch (pathOp.getArgCount()) {
					case 2:
						this.curve3(p0);
						break;
					case 4:
						if ((pathOp.getFlags() & FlagType.CF_CONTINUOUS.getMask()) != 0) {
							this.curve4(p1, p0);
						} else {
							this.curve3(p1, p0);
						}
						break;
					case 6:
						this.curve4(p1, p2, p0);
						break;
				}
				break;
			default:
				break;
		}

		this.lastVertex(renderer.getLastPoint());
	}
}
