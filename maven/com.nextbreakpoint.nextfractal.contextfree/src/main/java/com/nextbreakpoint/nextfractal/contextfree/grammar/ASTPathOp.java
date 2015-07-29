/*
 * NextFractal 1.1.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;

import org.antlr.v4.runtime.Token;

class ASTPathOp extends ASTReplacement {
	private ASTExpression arguments;
	private ASTModification oldStyleArguments;
	private int argCount;
	private int flags;
	
	public ASTPathOp(String op, ASTModification args, Token location) {
		super(op, location);
		this.arguments = null;
		this.oldStyleArguments = args;
		this.argCount = 0;
		this.flags = 0;
	}

	public ASTPathOp(String op, ASTExpression args, Token location) {
		super(op, location);
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
	public void compile(ECompilePhase ph) {
		super.compile(ph);
		if (arguments != null) {
			arguments.compile(ph);
		}
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
					arguments.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	private void pathDataConst() {
		if (arguments != null && arguments.isConstant()) {
			double[] data = new double[7];
			if (arguments.evaluate(data, 7) < 0) {
				error("Cannot evaluate arguments");
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
						error("Flags must be the last argument");
					}
					if (temp instanceof ASTReal) {
						ASTReal rf = (ASTReal)temp;
						flags |= (int)rf.getValue();
					} else {
						error("Flag expressions must be constant");
					}
					argCount--;
					break;
	
				case NumericType:
					break;
					
				default:
					error("Path operation arguments must be numeric expressions or flags");
					break;
			}
		}
		
		switch (getPathOp()) {
			case LINETO:
			case LINEREL:
			case MOVETO:
			case MOVEREL:
				if (argCount != 2) {
					error("Move/line path operation requires two arguments");
				}
				break;
	
			case ARCTO:
			case ARCREL:
				if (argCount != 3 && argCount != 5) {
					error("Arc path operations require three or five arguments");
				}
				break;
				
			case CURVETO:
			case CURVEREL:
				if ((flags & EFlagType.CF_CONTINUOUS.getType()) != 0) {
					if (argCount != 2 && argCount != 4) {
						error("Continuous curve path operations require two or four arguments");
					}
				} else {
					if (argCount != 4 && argCount != 6) {
						error("Non-continuous curve path operations require four or six arguments");
					}
				}
				break;
				
			case CLOSEPOLY:
				if (argCount > 0) {
					error("CLOSEPOLY takes no arguments, only flags");
				}
				break;
				
			default:
				break;
		}
	}

	private void makePositional() {
		// TODO da completare
//        exp_ptr w = GetFlagsAndStroke(mOldStyleArguments->modExp, mFlags);
//        if (w)
//            CfdgError::Error(w->where, "Stroke width not allowed in a path operation");
//        
//        exp_ptr ax;
//        exp_ptr ay;
//        exp_ptr ax1;
//        exp_ptr ay1;
//        exp_ptr ax2;
//        exp_ptr ay2;
//        exp_ptr arx;
//        exp_ptr ary;
//        exp_ptr ar;
//        
//        for (term_ptr& mod: mOldStyleArguments->modExp) {
//            if (!mod)
//                continue;
//            switch (mod->modType) {
//                case ASTmodTerm::x:
//                    acquireTerm(ax, mod);
//                    break;
//                case ASTmodTerm::y:
//                    acquireTerm(ay, mod);
//                    break;
//                case ASTmodTerm::x1:
//                    acquireTerm(ax1, mod);
//                    break;
//                case ASTmodTerm::y1:
//                    acquireTerm(ay1, mod);
//                    break;
//                case ASTmodTerm::x2:
//                    acquireTerm(ax2, mod);
//                    break;
//                case ASTmodTerm::y2:
//                    acquireTerm(ay2, mod);
//                    break;
//                case ASTmodTerm::xrad:
//                    acquireTerm(arx, mod);
//                    break;
//                case ASTmodTerm::yrad:
//                    acquireTerm(ary, mod);
//                    break;
//                case ASTmodTerm::rot:
//                    acquireTerm(ar, mod);
//                    break;
//                case ASTmodTerm::z:
//                case ASTmodTerm::zsize:
//                    CfdgError::Error(mod->where, "Z changes are not permitted in paths");
//                    break;
//                case ASTmodTerm::unknownType:
//                default:
//                    CfdgError::Error(mod->where, "Unrecognized element in a path operation");
//                    break;
//            }
//        }
//        
//        ASTexpression* xy = nullptr;
//        if (mPathOp != CLOSEPOLY) {
//            xy = parseXY(std::move(ax), std::move(ay), 0.0, mLocation);
//        } 
//        
//        switch (mPathOp) {
//            case LINETO:
//            case LINEREL:
//            case MOVETO:
//            case MOVEREL:
//                mArguments.reset(xy);
//                break;
//            case ARCTO:
//            case ARCREL:
//                if (arx || ary) {
//                    ASTexpression* rxy = parseXY(std::move(arx), std::move(ary), 1.0, mLocation);
//                    ASTexpression* angle = ar.release();
//                    if (!angle)
//                        angle = new ASTreal(0.0, mLocation);
//                    
//                    if (angle->mType != NumericType || angle->evaluate(nullptr, 0) != 1)
//                        CfdgError(angle->where, "Arc angle must be a scalar value");
//                    
//                    mArguments.reset(xy->append(rxy)->append(angle));
//                } else {
//                    ASTexpression* radius = ar.release();
//                    if (!radius)
//                        radius = new ASTreal(1.0, mLocation);
//                    
//                    if (radius->mType != NumericType || radius->evaluate(nullptr, 0) != 1)
//                        CfdgError::Error(radius->where, "Arc radius must be a scalar value");
//                    
//                    mArguments.reset(xy->append(radius));
//                } 
//                break;
//            case CURVETO:
//            case CURVEREL: {
//                ASTexpression *xy1 = nullptr, *xy2 = nullptr;
//                if (ax1 || ay1) {
//                    xy1 = parseXY(std::move(ax1), std::move(ay1), 0.0, mLocation);
//                } else {
//                    mFlags |= CF_CONTINUOUS;
//                }
//                if (ax2 || ay2) {
//                    xy2 = parseXY(std::move(ax2), std::move(ay2), 0.0, mLocation);
//                }
//                
//                mArguments.reset(xy->append(xy1)->append(xy2));
//                break;
//            }
//            case CLOSEPOLY:
//                break;
//            default:
//                break;
//        }
//        
//        rejectTerm(ax);
//        rejectTerm(ay);
//        rejectTerm(ar);
//        rejectTerm(arx);
//        rejectTerm(ary);
//        rejectTerm(ax1);
//        rejectTerm(ay1);
//        rejectTerm(ax2);
//        rejectTerm(ay2);
//        
//        mArgCount = mArguments ? mArguments->evaluate(nullptr, 0) : 0;
//        mOldStyleArguments.reset();
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		if (rti.getCurrentPath().isComplete()) {
			return;
		}
		double[] opData = new double[7];
		pushData(opData, rti);
		rti.getCurrentPath().addPathOp(this, opData, parent, tr, rti);
	}

	private void pushData(double[] opData, RTI rti) {
		if (arguments != null) {
			if (arguments.evaluate(opData, 7, rti) < 0) {
				error("Cannot evaluate arguments");
			}
		} else {
			getChildChange().getModData().getTransform().getMatrix(opData);
		}
	}
}
