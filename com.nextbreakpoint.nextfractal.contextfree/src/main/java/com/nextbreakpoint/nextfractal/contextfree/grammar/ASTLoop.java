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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

class ASTLoop extends ASTReplacement {
	private ASTExpression loopArgs;
	private ASTModification loopModHolder;
	private double[] loopData = new double[3];
	private ASTRepContainer loopBody;
	private ASTRepContainer finallyBody;
	private int loopIndexName;
	private String loopName;
	
	public ASTLoop(CFDGDriver driver, int nameIndex, String name, ASTExpression args, ASTModification mods, Token location) {
		super(driver, mods, ERepElemType.empty, location);
		loopBody = new ASTRepContainer(driver);
		finallyBody = new ASTRepContainer(driver);
		this.loopArgs = args;
		this.loopModHolder = null;
		this.loopIndexName = nameIndex;
		this.loopName = name;
		loopBody.addLoopParameter(loopIndexName, false, false, location);
		finallyBody.addLoopParameter(loopIndexName, false, false, location);
	}
	
	public ASTRepContainer getLoopBody() {
		return loopBody;
	}
	
	public ASTRepContainer getFinallyBody() {
		return finallyBody;
	}

	public void setLoopHolder(ASTModification loopModHolder) {
		this.loopModHolder = loopModHolder;
	}

	public ASTExpression getLoopArgs() {
		return loopArgs;
	}

	public ASTModification getLoopModHolder() {
		return loopModHolder;
	}

	public double[] getLoopData() {
		return loopData;
	}

	public int getLoopIndexName() {
		return loopIndexName;
	}

	public String getLoopName() {
		return loopName;
	}

	public void compileLoopMod() {
       if (loopModHolder != null) {
            loopModHolder.compile(ECompilePhase.TypeCheck);
            getChildChange().grab(loopModHolder);
        } else {
        	getChildChange().compile(ECompilePhase.TypeCheck);
        }
 	}

	@Override
	public void compile(ECompilePhase ph) {
		if (loopArgs != null) {
			loopArgs.compile(ph);
		}
		
		switch (ph) {
			case TypeCheck:
				{
					if (loopArgs == null) {
						error("A loop must have one to three index parameters.");
						return;
					}
					StringBuilder ent = new StringBuilder();
					ent.append(loopName);
					loopArgs.entropy(ent);
					if (loopModHolder != null) {
						getChildChange().addEntropy(ent.toString());
					}
					
					boolean bodyNatural = false;
					boolean finallyNatural = false;
					ELocality locality = loopArgs.getLocality();
					
					if (loopArgs.isConstant()) {
						setupLoop(loopData, loopArgs, null);
						bodyNatural = loopData[0] == Math.floor(loopData[0]) && loopData[1] == Math.floor(loopData[1]) && loopData[2] == Math.floor(loopData[2]) &&	loopData[0] >= 0 && loopData[1] >= 0 && loopData[0] < 9007199254740992.0 && loopData[1] < 9007199254740992.0;  
						finallyNatural = bodyNatural && loopData[1] + loopData[2] >= -1.0 && loopData[1] + loopData[2] < 9007199254740992.0; 
						loopArgs = null;
					} else {
						int c = loopArgs.evaluate(null, 0);
						if (c < 1 || c > 3) {
							error("A loop must have one to three index parameters.");
						}
						
						for (int i = 0, count = 0; i < loopArgs.size(); i++) {
							ASTExpression loopArg = loopArgs.getChild(i);
							int num = loopArg.evaluate(null, 0);
							switch (count) {
								case 0:
									if (loopArg.isNatural()) {
										bodyNatural = finallyNatural = true;
									}
									break;
								
								case 2:
		                            // Special case: if 1st & 2nd args are natural and 3rd
	                                // is -1 then that is ok
									double[] step = new double[1]; 
									if (loopArg.isConstant() && loopArg.evaluate(step, 1) == 1 && step[0] == -1.0) {
										break;
									} // else fall through
								
								case 1:
									if (!loopArg.isNatural()) {
										bodyNatural = finallyNatural = false;
									}
									break;

								default:
									break;
							}
							count += num;
						}
					}
					loopBody.getParameters().get(loopBody.getParameters().size() - 1).setIsNatural(bodyNatural);
					loopBody.getParameters().get(loopBody.getParameters().size() - 1).setLocality(locality);
					loopBody.compile(ph, this, null);
					finallyBody.getParameters().get(finallyBody.getParameters().size() - 1).setIsNatural(finallyNatural);
					finallyBody.getParameters().get(finallyBody.getParameters().size() - 1).setLocality(locality);
					finallyBody.compile(ph, null, null);
					
					if (loopModHolder == null) {
						getChildChange().addEntropy(ent.toString());
					}
				}
				break;
	
			case Simplify:
				if (loopArgs != null) {
					loopArgs.simplify();
				}
				loopBody.compile(ph, null, null);
				finallyBody.compile(ph, null, null);
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		Shape loopChild = parent;
		boolean opsOnly = (loopBody.getRepType() | finallyBody.getRepType()) == ERepElemType.op.getType();
		if (opsOnly && !tr) {
			loopChild.getWorldState().setTransform(null);
		}
		double[] data = new double[2];
		rti.getCurrentSeed().add(getChildChange().getModData().getRand64Seed());
		if (loopArgs != null) {
			setupLoop(data, loopArgs, rti);
		} else {
			data[0] = loopData[0];
			data[1] = loopData[1];
			data[2] = loopData[2];
		}
		StackType t = new StackType(data[0]);
		StackType oldTop = rti.getLogicalStackTop();
		rti.getCFStack().add(t);
		StackType index = rti.getCFStack().get(rti.getCFStack().size() - 1);
		index.addNumber(1);
		rti.setLogicalStackTop(index);
		for (;;) {
			if (rti.getRequestStop() /*TODO || Renderer.abortEverything()*/) {
				throw new RuntimeException("Stopping");
			}
			if (data[2] > 0.0) {
				if (index.getNumber() >= data[1]) {
					break;
				}
			} else {
				if (index.getNumber() <= data[1]) {
					break;
				}
			}
			loopBody.traverse(loopChild, tr || opsOnly, rti, false);
			
			getChildChange().evaluate(loopChild.getWorldState(), true, rti);
			index.addNumber(data[2]);
		}
		finallyBody.traverse(loopChild, tr || opsOnly, rti, false);
		rti.getCFStack().remove(rti.getCFStack().size() - 1);
		rti.setLogicalStackTop(oldTop);
	}
	
	private void setupLoop(double[] data, ASTExpression exp, RTI rti) {
		switch (exp.evaluate(data, 3, rti)) {
			case 1:
				data[1] = data[0];
				data[0] = 0.0;
				break;
	
			case 2:
				data[2] = 1.0;
				break;
	
			case 3:
				break;
			
			default:
				error("A loop must have one to three index parameters.");
				break;
		}
	}
}
