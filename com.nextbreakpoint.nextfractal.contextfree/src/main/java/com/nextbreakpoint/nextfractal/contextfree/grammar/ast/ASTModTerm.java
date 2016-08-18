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

import com.nextbreakpoint.nextfractal.contextfree.core.AffineTransform1D;
import com.nextbreakpoint.nextfractal.contextfree.core.AffineTransformTime;
import com.nextbreakpoint.nextfractal.contextfree.grammar.DeferUntilRuntimeException;
import com.nextbreakpoint.nextfractal.contextfree.grammar.HSBColor;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Modification;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;
import org.antlr.v4.runtime.Token;

public class ASTModTerm extends ASTExpression {
	private int argCount;
	private ModType modType;
	private ASTExpression args;

	public ASTModTerm(ModType modType, ASTExpression args, Token location) {
		super(true, false, ExpType.ModType, location);

		this.modType = modType;
		this.args = args;
		this.argCount = 0;

		if (args.type == ExpType.RuleType)
			throw new RuntimeException("Illegal expression in shape adjustment");

		if (args.type == ExpType.ModType) {
			if (modType != ModType.transform)
				throw new RuntimeException(
						"Cannot accept a transform expression here");
			modType = ModType.modification;
		}
	}

	public ASTModTerm(ModType param, String entropy, Token location) {
		super(true, false, ExpType.ModType, location);
		// TODO Auto-generated constructor stub
	}

	public ASTModTerm(ModType param, Token location) {
		super(true, false, ExpType.ModType, location);
		// TODO Auto-generated constructor stub
	}

	public ModType getModType() {
		return modType;
	}

	public void setModType(ModType modType) {
		this.modType = modType;
	}
	
	public ASTExpression getArguments() {
		return args;
	}

	public void setArguments(ASTExpression arguments) {
		this.args = arguments;
	}
	
	public int getArgumentsCount() {
		return argCount;
	}

	public void setArgumentsCount(int argCount) {
		this.argCount = argCount;
	}
	
	@Override
	public void entropy(StringBuilder e) {
		if (args != null) {
			args.entropy(e);
		}
		e.append(modType.getEntropy());
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		error("Improper evaluation of an adjustment expression");
		return -1;
	}

	@Override
	public void evaluate(Modification result, boolean shapeDest, RTI rti) {
		double[] modArgs = new double[6];
		int argcount = 0;

		if (args != null) {
			if (modType != ModType.modification && args.type == ExpType.NumericType) {
				argcount = args.evaluate(modArgs, 6, rti);
			} else if (modType == ModType.modification && args.type != ExpType.ModType) {
				error("Adjustments require numeric arguments");
				return;
			}
		}

		if (argcount != argCount) {
			error("Error evaluating arguments");
			return;
		}
		
		double[] args = new double[6];
		for (int i = 0; i < argcount; ++i) {
			args[i] = Math.max(-1.0, Math.min(1.0, modArgs[i]));
		}
		
		double[] color = result.color().values();
		double[] target = result.colorTarget().values();
		int colorComp = 0;
		boolean hue = true;
		int mask = AssignmentType.HueMask.getType();

		switch (modType) {
		case x: {
			if (argcount == 1) {
				modArgs[1] = 0.0;
			}
			AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case y: {
			AffineTransform t2d = AffineTransform.getTranslateInstance(0.0, modArgs[0]);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case z: {
			AffineTransform1D t1d = AffineTransform1D.getTranslateInstance(modArgs[0]);
			result.getTransformZ().preConcatenate(t1d);
			break;
		}
		case xyz: {
			AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
			AffineTransform1D t1d = AffineTransform1D.getTranslateInstance(modArgs[0]);
			result.getTransform().preConcatenate(t2d);
			result.getTransformZ().preConcatenate(t1d);
			break;
		}
		case time: {
			AffineTransformTime tTime = AffineTransformTime.getTranslateInstance(modArgs[0], modArgs[1]);
			result.getTransformTime().preConcatenate(tTime);
			break;
		}
		case timescale: {
			AffineTransformTime tTime = AffineTransformTime.getScaleInstance(modArgs[0]);
			result.getTransformTime().preConcatenate(tTime);
			break;
		}
		case transform: {
			switch (argcount) {
				case 2:
				case 1:
					{
						if (argcount == 1) {
							modArgs[1] = 0.0;
						}
						AffineTransform t2d = AffineTransform.getTranslateInstance(modArgs[0], modArgs[1]);
						result.getTransform().preConcatenate(t2d);
					}
					break;
	
				case 4:
					{
						AffineTransform t2d = new AffineTransform();
						double dx = modArgs[2] - modArgs[0];
						double dy = modArgs[3] - modArgs[1];
						double s = Math.hypot(dx, dy);
						t2d.rotate(Math.atan2(dx, dy));
						t2d.scale(s, s);
						t2d.translate(modArgs[0], modArgs[1]);
						result.getTransform().preConcatenate(t2d);
					}
					break;
					
				case 6:
					{
						try {
							AffineTransform t2d = new AffineTransform(modArgs[2] - modArgs[0], modArgs[3] - modArgs[1], modArgs[4] - modArgs[0], modArgs[5] - modArgs[1], modArgs[0], modArgs[1]);
							AffineTransform par = new AffineTransform();
							par.shear(1, 0);
							par.invert();
							par.concatenate(t2d);
							result.getTransform().preConcatenate(par);
						} catch (NoninvertibleTransformException e) {
							error(e.getMessage());
						}
					}
					break;
					
				default:
					break;
			}
			break;
		}
		case size: {
			if (argcount == 1) {
				modArgs[1] =  modArgs[0];
			}
			AffineTransform t2d = AffineTransform.getScaleInstance(modArgs[0], modArgs[1]);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case sizexyz: {
			AffineTransform t2d = AffineTransform.getScaleInstance(modArgs[0], modArgs[1]);
			AffineTransform1D t1d = AffineTransform1D.getScaleInstance(modArgs[0]);
			result.getTransform().preConcatenate(t2d);
			result.getTransformZ().preConcatenate(t1d);
			break;
		}
		case zsize: {
			AffineTransform1D t1d = AffineTransform1D.getScaleInstance(modArgs[0]);
			result.getTransformZ().preConcatenate(t1d);
			break;
		}
		case rot: {
			AffineTransform t2d = AffineTransform.getRotateInstance(modArgs[0] * Math.PI / 180.0);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case skew: {
			AffineTransform t2d = AffineTransform.getShearInstance(modArgs[0] * Math.PI / 180.0, modArgs[1] * Math.PI / 180.0);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case flip: {
			double a = modArgs[0] * Math.PI / 180.0;
			double ux = Math.cos(a);
			double uy = Math.cos(a);
			AffineTransform t2d = new AffineTransform(2.0 * ux * ux - 1.0, 2.0 * ux * uy, 2.0 * ux * uy, 2.0 * uy * uy - 1.0, 0.0, 0.0);
			result.getTransform().preConcatenate(t2d);
			break;
		}
		case alpha: 
		case bright: 
		case sat: 
			{
				colorComp += modType.ordinal() - ModType.hue.ordinal();
				mask <<= 2 * modType.ordinal() - ModType.hue.ordinal();
				hue = false;
			}
		case hue: 
			{
				 if (argcount == 1) {
					 if ((result.colorAssignment() & mask) != 0 || (!hue && color[colorComp] != 0.0)) {
						 if (rti == null) throw new DeferUntilRuntimeException();
						 if (!shapeDest) {
							 rti.colorConflict(getLocation());
						 }
					 }
					 if (shapeDest) {
						 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], modArgs[0]) : HSBColor.adjust(color[colorComp], modArgs[0]);
					 } else {
						 color[colorComp] = hue ? color[colorComp] + modArgs[0] : args[0];
					 }
				 } else {
					 if ((result.colorAssignment() & mask) != 0 || (color[colorComp] != 0.0) || (!hue && target[colorComp] != 0.0)) {
						 if (rti == null) throw new DeferUntilRuntimeException();
						 if (!shapeDest) {
							 rti.colorConflict(getLocation());
						 }
					 }
					 if (shapeDest) {
						 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], args[0], AssignmentType.HueTarget.getType(), modArgs[1]) : HSBColor.adjust(color[colorComp], args[0], AssignmentType.ColorTarget.getType(), args[1]);
					 } else {
						 color[colorComp] = args[0];
						 target[colorComp] = hue ? modArgs[1] : args[1];
						 result.setColorAssignment(result.colorAssignment() | AssignmentType.HSBA2Value.getType() & mask);
					 }
				 }
			}
			break;
		case alphaTarg: 
		case brightTarg: 
		case satTarg: 
			{
				colorComp += modType.ordinal() - ModType.hueTarg.ordinal();
				mask <<= 2 * modType.ordinal() - ModType.hueTarg.ordinal();
				hue = false;
			}
		case hueTarg: 
			{
				 if ((result.colorAssignment() & mask) != 0 || (color[colorComp] != 0.0)) {
					 if (rti == null) throw new DeferUntilRuntimeException();
					 if (!shapeDest) {
						 rti.colorConflict(getLocation());
					 }
				 }
				 if (shapeDest) {
					 color[colorComp] = hue ? HSBColor.adjustHue(color[colorComp], args[0], AssignmentType.HueTarget.getType(), target[colorComp]) : HSBColor.adjust(color[colorComp], args[0], AssignmentType.ColorTarget.getType(), target[colorComp]);
				 } else {
					 color[colorComp] = args[0];
					 result.setColorAssignment(result.colorAssignment() | AssignmentType.HSBATarget.getType() & mask);
				 }
			}
			break;
		case targAlpha: 
		case targBright: 
		case targSat: 
			{
				colorComp += modType.ordinal() - ModType.targHue.ordinal();
				mask <<= 2 * modType.ordinal() - ModType.targHue.ordinal();
				if (target[colorComp] != 0.0) {
					 if (rti == null) throw new DeferUntilRuntimeException();
					 if (!shapeDest) {
						 rti.colorConflict(getLocation());
					 }
				}
				 if (shapeDest) {
					 target[colorComp] = HSBColor.adjust(target[colorComp], args[0]);
				 } else {
					 target[colorComp] = args[0];
				 }
			}
			break;
		case targHue: 
			{
				 target[0] += modArgs[0];
			}
			break;
		case param: {
			error("Cannot provide a parameter in this context");
			break;
		}
		case stroke: {
			error("Cannot provide a stroke width in this context");
			break;
		}
		case modification: {
			if (rti == null) {
				if (args != null && getArguments() instanceof ASTModification) {
					ASTModification mod = (ASTModification)getArguments();
					if ((mod.getModClass().ordinal() & (ModClass.HueClass.ordinal() | ModClass.HueTargetClass.ordinal() | ModClass.BrightClass.ordinal() | ModClass.BrightTargetClass.ordinal() | ModClass.SatClass.ordinal() | ModClass.SatTargetClass.ordinal() | ModClass.AlphaClass.ordinal() | ModClass.AlphaTargetClass.ordinal())) != 0) {
						throw new DeferUntilRuntimeException();
					}
				}
			}
			getArguments().evaluate(result, shapeDest, rti);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public ASTExpression simplify() {
		if (args != null) {
			args = args.simplify();
		}
		return this;
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		if (args != null) {
			args = args.compile(ph);
		}
		if (args == null) {
			if (modType == ModType.param) {
				error("Illegal expression in shape adjustment");
				return null;
			}
		}
		
		switch (ph) {
			case TypeCheck:
				{
					isConstant = args.isConstant();
					locality = args.getLocality();
					switch (args.getType()) {
						case NumericType:
							{
								argCount = args.evaluate(null, 0);
								int minCount = 1;
								int maxCount = 1;
								
								if (argCount == 3 && modType == ModType.x) {
									modType = ModType.xyz;
								}
								if (argCount == 3 && modType == ModType.size) {
									modType = ModType.sizexyz;
								}
								
								switch (modType) {
									case x:
									case size:
									case hue:
									case sat:
									case bright:
									case alpha:
										maxCount = 2;
										break;
	
									case y:
									case z:
									case timescale:
									case zsize:
									case rot:
									case flip:
									case hueTarg:
									case satTarg:
									case brightTarg:
									case alphaTarg:
									case targHue:
									case targSat:
									case targBright:
									case targAlpha:
									case stroke:
										break;
										
									case xyz:
									case sizexyz:
										minCount = maxCount = 3;
										break;
										
									case time:
									case skew:
										minCount = maxCount = 2;
										break;
										
									case transform:
										maxCount = 6;
										if (argCount != 1 && argCount != 2 && argCount != 4 && argCount != 6) {
											error("transform adjustment takes 1, 2, 4, or 6 parameters");
										}
										break;
										
									case param:
										minCount = maxCount = 0;
										break;
										
									case modification:
										break;
										
									default:
										break;
								}
								
		                        if (argCount < minCount) {
		                        	error("Not enough adjustment parameters");
		                        }
		                        if (argCount > maxCount) {
		                        	error("Too many adjustment parameters");
		                        }
							}
							break;
	
						case ModType: 
							{
								if (modType != ModType.transform) {
									error("Cannot accept a transform expression here");
								} else {
									modType = ModType.modification;
								}
							}
							break;
							
						default:
							error("Illegal expression in shape adjustment");
							break;
					}
				}
				break;
	
			case Simplify:
				break;
	
			default:
				break;
			}
		return null;
	}
}
