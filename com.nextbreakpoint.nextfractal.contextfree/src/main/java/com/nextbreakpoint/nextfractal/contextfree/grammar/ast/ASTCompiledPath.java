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

import java.util.ArrayDeque;
import java.util.Queue;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import org.antlr.v4.runtime.Token;

public class ASTCompiledPath {
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

	public void finish(boolean b, RTI rti) {
		// TODO da copletare
//		// Close and end the last path sequence if it wasn't already closed and ended
//		if (!r->mClosed) {
//			mPath.end_poly(0);
//			r->mClosed = true;
//		}
//
//		if (!r->mStop) {
//			mPath.start_new_path();
//			r->mStop = true;
//		}
//
//		r->mWantMoveTo = true;
//		r->mNextIndex = mPath.total_vertices();
//
//		// If setAttr is true then make sure that the last path sequence has a path
//		// attribute associated with it.
//		if (setAttr && r->mWantCommand) {
//			mUseTerminal = true;
//			r->mWantCommand = false;
//		}
	}

	public void addPathOp(ASTPathOp pathOp, double[] opData, Shape parent, boolean tr, RTI rti) {
		// TODO da copletare
//		// Process the parameters for ARCTO/ARCREL
//		double radius_x = 0.0, radius_y = 0.0, angle = 0.0;
//		bool sweep = (pop->mFlags & CF_ARC_CW) == 0;
//		bool largeArc = (pop->mFlags & CF_ARC_LARGE) != 0;
//		if (pop->mPathOp == ARCTO || pop->mPathOp == ARCREL) {
//			if (pop->mArgCount == 5) {
//				// If the radii are specified then use the ellipse ARCxx form
//				radius_x = data[2];
//				radius_y = data[3];
//				angle = data[4] * 0.0174532925199;
//			} else {
//				// Otherwise use the circle ARCxx form
//				radius_x = radius_y = data[2];
//				angle = 0.0;
//			}
//			if (radius_x < 0.0 || radius_y < 0.0) {
//				radius_x = fabs(radius_x);
//				radius_y = fabs(radius_y);
//				sweep = !sweep;
//			}
//		} else if (tr) {
//			s.mWorldState.m_transform.transform(data + 0, data + 1);
//			s.mWorldState.m_transform.transform(data + 2, data + 3);
//			s.mWorldState.m_transform.transform(data + 4, data + 5);
//		}
//
//		// If this is the first path operation following a path command then set the
//		// path index used by subsequent path commands to the path sequence that the
//		// current path operation is part of.
//		// If this is not the first path operation following a path command then this
//		// line does nothing.
//		r->mIndex = r->mNextIndex;
//
//		// If the op is anything other than a CLOSEPOLY then we are opening up a
//		// new path sequence.
//		r->mClosed = false;
//		r->mStop = false;
//
//		// This new path op needs to be covered by a command, either from the cfdg
//		// file or default.
//		r->mWantCommand = true;
//
//		if (pop->mPathOp == CLOSEPOLY) {
//			if (mPath.total_vertices() > 1 &&
//					agg::is_drawing(mPath.vertices().last_command()))
//			{
//				// Find the MOVETO/MOVEREL that is the start of the current path sequence
//				// and reset LastPoint to that.
//				unsigned last = mPath.total_vertices() - 1;
//				unsigned cmd = agg::path_cmd_stop;
//				for (unsigned i = last - 1;
//					 i < last && agg::is_vertex(cmd = mPath.command(i));
//				--i)
//				{
//					if (agg::is_move_to(cmd)) {
//					mPath.vertex(i, &(r->mLastPoint.x), &(r->mLastPoint.y));
//					break;
//				}
//				}
//
//				if (!agg::is_move_to(cmd))
//				CfdgError::Error(pop->mLocation, "CLOSEPOLY: Unable to find a MOVETO/MOVEREL for start of path.");
//
//				// If this is an aligning CLOSEPOLY then change the last vertex to
//				// exactly match the first vertex in the path sequence
//				if (pop->mFlags & CF_ALIGN) {
//					mPath.modify_vertex(last, r->mLastPoint.x, r->mLastPoint.y);
//				}
//			} else if (pop->mFlags & CF_ALIGN) {
//				CfdgError::Error(pop->mLocation, "Nothing to align to.");
//			}
//			mPath.close_polygon();
//			r->mClosed = true;
//			r->mWantMoveTo = true;
//			return;
//		}
//
//		// Insert an implicit MOVETO unless the pathOp is a MOVETO/MOVEREL
//		if (r->mWantMoveTo && pop->mPathOp > MOVEREL) {
//			r->mWantMoveTo = false;
//			mPath.move_to(r->mLastPoint.x, r->mLastPoint.y);
//		}
//
//		switch (pop->mPathOp) {
//			case MOVEREL:
//				mPath.rel_to_abs(data + 0, data + 1);
//			case MOVETO:
//				mPath.move_to(data[0], data[1]);
//				r->mWantMoveTo = false;
//				break;
//			case LINEREL:
//				mPath.rel_to_abs(data + 0, data + 1);
//			case LINETO:
//				mPath.line_to(data[0], data[1]);
//				break;
//			case ARCREL:
//				mPath.rel_to_abs(data + 0, data + 1);
//			case ARCTO: {
//				if (!agg::is_vertex(mPath.last_vertex(data + 2, data + 3)) ||
//						(tr && s.mWorldState.m_transform.determinant() < 1e-10))
//				{
//					break;
//				}
//
//				// Transforming an arc as they are parameterized by AGG is VERY HARD.
//				// So instead we insert the arc and then transform the bezier curves
//				// that are used to approximate the arc. But first we have to inverse
//				// transform the starting point to match the untransformed arc.
//				// Afterwards the starting point is restored to its original value.
//				if (tr) {
//					unsigned start = mPath.total_vertices() - 1;
//					agg::trans_affine inverseTr = ~s.mWorldState.m_transform;
//					mPath.transform(inverseTr, start);
//					mPath.arc_to(radius_x, radius_y, angle, largeArc, sweep, data[0], data[1]);
//					mPath.modify_vertex(start, data[2], data[3]);
//					mPath.transform(s.mWorldState.m_transform, start + 1);
//				} else {
//					mPath.arc_to(radius_x, radius_y, angle, largeArc, sweep, data[0], data[1]);
//				}
//				break;
//			}
//			case CURVEREL:
//				mPath.rel_to_abs(data + 0, data + 1);
//				mPath.rel_to_abs(data + 2, data + 3);
//				mPath.rel_to_abs(data + 4, data + 5);
//			case CURVETO:
//				if ((pop->mFlags & CF_CONTINUOUS) &&
//						!agg::is_curve(mPath.last_vertex(data + 4, data + 5)))
//			{
//				CfdgError::Error(pop->mLocation, "Smooth curve operations must be preceded by another curve operation.");
//				break;
//			}
//			switch (pop->mArgCount) {
//				case 2:
//					mPath.curve3(data[0], data[1]);
//					break;
//				case 4:
//					if (pop->mFlags & CF_CONTINUOUS)
//						mPath.curve4(data[2], data[3], data[0], data[1]);
//					else
//						mPath.curve3(data[2], data[3], data[0], data[1]);
//					break;
//				case 6:
//					mPath.curve4(data[2], data[3], data[4], data[5], data[0], data[1]);
//					break;
//				default:
//					break;
//			}
//			break;
//			default:
//				break;
//		}
//
//		mPath.last_vertex(&(r->mLastPoint.x), &(r->mLastPoint.y));
	}
}
