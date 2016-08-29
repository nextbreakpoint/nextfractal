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

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Logger;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGRenderer;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Shape;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FlagType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.PathOp;
import org.antlr.v4.runtime.Token;

public class ASTPathOp extends ASTReplacement {
	private ASTExpression arguments;
	private ASTModification oldStyleArguments;
	private int argCount;
	private int flags;
	
	public ASTPathOp(CFDGDriver driver, String op, ASTModification args, Token location) {
		super(driver, op, location);
		this.arguments = null;
		this.oldStyleArguments = args;
		this.argCount = 0;
		this.flags = 0;
	}

	public ASTPathOp(CFDGDriver driver, String op, ASTExpression args, Token location) {
		super(driver, op, location);
		this.arguments = args;
		this.oldStyleArguments = null;
		this.argCount = 0;
		this.flags = 0;
	}

	public ASTExpression getArguments() {
		return arguments != null ? arguments : oldStyleArguments;
	}
	
	public int getArgCount() {
		return argCount;
	}
	
	public int getFlags() {
		return flags;
	}

	@Override
	public void traverse(Shape parent, boolean tr, CFDGRenderer renderer) {
		if (renderer.getCurrentPath().isComplete()) {
			return;
		}
		double[] opData = new double[7];
		pushData(opData, renderer);
		renderer.getCurrentPath().addPathOp(this, opData, parent, tr, renderer);
	}

	@Override
	public void compile(CompilePhase ph) {
		super.compile(ph);
		arguments = compile(arguments, ph);

		if (oldStyleArguments != null) {
			oldStyleArguments.compile(ph);
		}
		switch (ph) {
			case TypeCheck: 
				if (oldStyleArguments != null) {
					makePositional();
				} else {
					checkArguments();
				}
				break;
	
			case Simplify:
				pathDataConst();
				if (arguments != null) {
					arguments = arguments.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	private void pushData(double[] opData, CFDGRenderer renderer) {
		if (arguments != null) {
			if (arguments.evaluate(opData, 7, renderer) < 0) {
				Logger.error("Cannot evaluate arguments", location);
			}
		} else {
			getChildChange().getModData().getTransform().getMatrix(opData);
		}
	}

	private void pathDataConst() {
		if (arguments != null && arguments.isConstant()) {
			double[] data = new double[7];
			if (arguments.evaluate(data, 7) < 0) {
				Logger.error("Cannot evaluate arguments", location);
			}
			arguments = null;
			getChildChange().getModData().setTransform(new AffineTransform(data));
		}
	}

	private void checkArguments() {
		if (arguments != null) {
			argCount = arguments.evaluate(null, 0);
		}
		
		for (int i = 0; arguments != null && i < arguments.size(); i++) {
			ASTExpression temp = arguments.getChild(i);
			switch (temp.getType()) {
				case FlagType:
					if (i != arguments.size() - 1) {
						Logger.error("Flags must be the last argument", location);
					}
					if (temp instanceof ASTReal) {
						ASTReal rf = (ASTReal)temp;
						flags |= (int)rf.getValue();
					} else {
						Logger.error("Flag expressions must be constant", location);
					}
					argCount--;
					break;
	
				case NumericType:
					break;
					
				default:
					Logger.error("Path operation arguments must be numeric expressions or flags", location);
					break;
			}
		}
		
		switch (getPathOp()) {
			case LINETO:
			case LINEREL:
			case MOVETO:
			case MOVEREL:
				if (argCount != 2) {
					Logger.error("Move/line path operation requires two arguments", location);
				}
				break;
	
			case ARCTO:
			case ARCREL:
				if (argCount != 3 && argCount != 5) {
					Logger.error("Arc path operations require three or five arguments", location);
				}
				break;
				
			case CURVETO:
			case CURVEREL:
				if ((flags & FlagType.CF_CONTINUOUS.getMask()) != 0) {
					if (argCount != 2 && argCount != 4) {
						Logger.error("Continuous curve path operations require two or four arguments", location);
					}
				} else {
					if (argCount != 4 && argCount != 6) {
						Logger.error("Non-continuous curve path operations require four or six arguments", location);
					}
				}
				break;
				
			case CLOSEPOLY:
				if (argCount > 0) {
					Logger.error("CLOSEPOLY takes no arguments, only flags", location);
				}
				break;
				
			default:
				break;
		}
	}

	private void makePositional() {
		int[] value = new int[1];
		ASTExpression w = AST.getFlagsAndStroke(oldStyleArguments.getModExp(), value);
		flags = value[0];
		if (w != null) {
			Logger.error("Stroke width not allowed in a path operation", w.getLocation());
		}

		ASTExpression ax = null;
		ASTExpression ay = null;
		ASTExpression ax1 = null;
		ASTExpression ay1 = null;
		ASTExpression ax2 = null;
		ASTExpression ay2 = null;
		ASTExpression arx = null;
		ASTExpression ary = null;
		ASTExpression ar = null;

		for (ASTModTerm term : oldStyleArguments.getModExp()) {
			switch (term.getModType()) {
				case x:
					ax = acquireTerm(ax, term);
					break;
				case y:
					ay = acquireTerm(ay, term);
					break;
				case x1:
					ax1 = acquireTerm(ax1, term);
					break;
				case y1:
					ay1 = acquireTerm(ay1, term);
					break;
				case x2:
					ax2 = acquireTerm(ax2, term);
					break;
				case y2:
					ay2 = acquireTerm(ay2, term);
					break;
				case xrad:
					arx = acquireTerm(arx, term);
					break;
				case yrad:
					ary = acquireTerm(ary, term);
					break;
				case rot:
					ar = acquireTerm(ar, term);
					break;
				case z:
				case zsize:
					Logger.error("Z changes are not permitted in paths", term.getLocation());
					break;
				case unknown:
					Logger.error("Unrecognized element in a path operation", term.getLocation());
					break;
				default:
					Logger.error("Unrecognized element in a path operation", term.getLocation());
					break;
			}
		}

		ASTExpression xy = null;
		if (getPathOp() == PathOp.CLOSEPOLY) {
			xy = parseXY(ax, ay, 0.0, location);
			ax = null;
			ay = null;
		}

		//TODO controllare

		switch (getPathOp()) {
			case LINETO:
			case LINEREL:
			case MOVETO:
			case MOVEREL:
				arguments = xy;
				break;
			case ARCTO:
			case ARCREL:
				if (arx != null && ary != null) {
					ASTExpression rxy = parseXY(arx, ary, 1.0, location);
					arx = null;
					ary = null;
					ASTExpression angle = ar;
					if (angle == null) {
						angle = new ASTReal(0.0, location);
					}
					if (angle.getType() != ExpType.NumericType || angle.evaluate(null , 0) != 1) {
						Logger.error("Arc angle must be a scalar value", angle.getLocation());
					}
					arguments = xy.append(rxy).append(angle);
				} else {
					ASTExpression radius = ar;
					ar = null;
					if (radius == null) {
						radius = new ASTReal(1.0, location);
					}
					if (radius.getType() != ExpType.NumericType || radius.evaluate(null , 0) != 1) {
						Logger.error("Arc radius must be a scalar value", radius.getLocation());
					}
					arguments = xy.append(radius);
				}
				break;
			case CURVETO:
			case CURVEREL:
				ASTExpression xy1 = null;
				ASTExpression xy2 = null;
				if (ax1 != null || ay1 != null) {
					xy1 = parseXY(ax1, ay1, 0.0, location);
				} else {
					flags |= FlagType.CF_CONTINUOUS.getMask();
				}
				if (ax2 != null || ay2 != null) {
					xy2 = parseXY(ax2, ay2, 0.0, location);
				}
				ax1 = null;
				ay1 = null;
				ax2 = null;
				ay2 = null;
				arguments = xy.append(xy1).append(xy2);
				break;
			case CLOSEPOLY:
				break;
			default:
				break;
		}

		rejectTerm(ax);
		rejectTerm(ay);
		rejectTerm(ar);
		rejectTerm(arx);
		rejectTerm(ary);
		rejectTerm(ax1);
		rejectTerm(ay1);
		rejectTerm(ax2);
		rejectTerm(ay2);

		argCount = arguments != null ? arguments.evaluate(null, 0) : 0;
		oldStyleArguments = null;
	}

	private ASTExpression acquireTerm(ASTExpression exp, ASTModTerm term) {
		if (exp != null) {
			Logger.error("Duplicate argument", exp.getLocation());
			Logger.error("Conflicts with this argument", term.getLocation());
		}
		return term.getArguments();
	}

	private void rejectTerm(ASTExpression exp) {
		if (exp != null) {
			Logger.error("Illegal argument", exp.getLocation());
		}
	}

	private ASTExpression parseXY(ASTExpression ax, ASTExpression ay, double def, Token location) {
		//TODO controllare
		if (ax == null) {
			ax = new ASTReal(def, location);
		}
		int sz = 0;
		if (ax.getType() == ExpType.NumericType) {
			sz = ax.evaluate(null, 0);
		} else {
			Logger.error("Path argument must be a scalar value", ax.getLocation());
		}
		if (sz == 1 && ay == null) {
			ay = new ASTReal(def, location);
		}
		if (ay != null && sz >= 0) {
			if (ay.getType() == ExpType.NumericType) {
				sz += ay.evaluate(null, 0);
			} else {
				Logger.error("Path argument must be a scalar value", ay.getLocation());
			}
		}
		if (sz != 2) {
			Logger.error("Error parsing path operation arguments", location);
		}
		return ax.append(ay);
	}
}
