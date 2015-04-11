/*
 * NextFractal 1.0
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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTDefine extends ASTReplacement {
	private EDefineType defineType;
	private ASTExpression exp;
	private int tupleSize;
	private EExpType expType;
	private boolean isNatural;
	private List<ASTParameter> parameters = new ArrayList<ASTParameter>();
	private int stackCount;
	private String name;
	private int configDepth;
	
	public ASTDefine(String name, Token location) {
		super(new ASTModification(location), ERepElemType.empty, location);
		this.defineType = EDefineType.StackDefine;
		this.expType = EExpType.NoType;
		this.isNatural = false;
		this.stackCount = 0;
		this.name = name;
		this.configDepth = -1;
		int[] i = new int[1];
		getChildChange().getModData().getRand64Seed().init();
		getChildChange().getModData().getRand64Seed().xorString(name, i);
	}

	public EDefineType getDefineType() {
		return defineType;
	}

	public void setDefineType(EDefineType defineType) {
		this.defineType = defineType;
	}

	public ASTExpression getExp() {
		return exp;
	}

	public void setExp(ASTExpression exp) {
		this.exp = exp;
	}

	public int getTupleSize() {
		return tupleSize;
	}

	public void setTupleSize(int tupleSize) {
		this.tupleSize = tupleSize;
	}

	public EExpType getExpType() {
		return expType;
	}

	public void setExpType(EExpType expType) {
		this.expType = expType;
	}

	public boolean isNatural() {
		return isNatural;
	}

	public void setIsNatural(boolean isNatural) {
		this.isNatural = isNatural;
	}

	public List<ASTParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ASTParameter> parameters) {
		this.parameters = parameters;
	}

	public int getStackCount() {
		return stackCount;
	}

	public void setStackCount(int stackCount) {
		this.stackCount = stackCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getConfigDepth() {
		return configDepth;
	}

	public void setConfigDepth(int configDepth) {
		this.configDepth = configDepth;
	}

	public void incStackCount(int value) {
		stackCount += value;
	}
	
	public void idecStackCount(int value) {
		stackCount -= value;
	}
	
	@Override
	public void compile(ECompilePhase ph) {
		if (defineType == EDefineType.FunctionDefine || defineType == EDefineType.LetDefine) {
			ASTRepContainer tempCont = new ASTRepContainer();
			tempCont.setParameters(parameters);
			tempCont.setStackCount(stackCount);
			Builder.currentBuilder().pushRepContainer(tempCont);
			super.compile(ph);
			if (exp != null) {
				exp.compile(ph);
			}
			if (ph == ECompilePhase.Simplify) {
				if (exp != null) {
					exp.simplify();
				}
			}
			Builder.currentBuilder().popRepContainer(null);
		}
		
		switch (ph) {
			case TypeCheck:
				{
					if (defineType == EDefineType.ConfigDefine) {
						Builder.currentBuilder().makeConfig(this);
						return;
					}
					
					getChildChange().getModData().getRand64Seed().init();
					getChildChange().setEntropyIndex(0);
					getChildChange().addEntropy(name);
					
					EExpType t = exp != null ? exp.getType() : EExpType.ModType;
					int sz = 1;
					if (t == EExpType.NumericType) {
						sz = exp.evaluate(null, 0);
					}
					if (t == EExpType.ModType) {
						sz = 6;
					}
					if (defineType == EDefineType.FunctionDefine) {
						if (t != getExpType()) {
							error("Mismatch between declared and defined type of user function");
						}
						if (getExpType() == EExpType.NumericType && t == EExpType.NumericType && sz != tupleSize) {
							error("Mismatch between declared and defined vector length of user function");
						}
						if (isNatural() && (exp == null || exp.isNatural())) {
							error("Mismatch between declared natural and defined not-natural type of user function");
						}
					} else {
						if (getShapeSpecifier().getShapeType() >= 0) {
							ASTDefine[] func = new ASTDefine[1];
							@SuppressWarnings("unchecked")
							List<ASTParameter>[] shapeParams = new List[1];
							Builder.currentBuilder().getTypeInfo(getShapeSpecifier().getShapeType(), func, shapeParams);
							if (func[0] != null) {
								error("Variable name is also the name of a function");
								error(func[0].getLocation() + "   function definition is here");
							}
							if (shapeParams[0] != null) {
								error("Variable name is also the name of a shape");
							}
						}
						
						tupleSize = sz;
						expType = t;
						if (t.getType() != (t.getType() & (-t.getType())) || t.getType() == 0) {//TODO da controllare???
							error("Expression can only have one type");
						}
						if (defineType == EDefineType.StackDefine && (exp != null ? exp.isConstant() : getChildChange().getModExp().isEmpty())) {
							defineType = EDefineType.ConstDefine;
						}
						isNatural = exp != null && exp.isNatural() && expType == EExpType.NumericType;
						ASTParameter param = Builder.currentBuilder().getContainerStack().peek().addDefParameter(getShapeSpecifier().getShapeType(), this, getLocation());
						if (param.isParameter() || param.getDefinition() == null) {
							param.setStackIndex(Builder.currentBuilder().getLocalStackDepth());
							Builder.currentBuilder().getContainerStack().peek().setStackCount(Builder.currentBuilder().getContainerStack().peek().getStackCount() + param.getTupleSize());
							Builder.currentBuilder().setLocalStackDepth(Builder.currentBuilder().getLocalStackDepth() + param.getTupleSize()); 
						}
					}
				}
				break;
	
			case Simplify:
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		if (defineType != EDefineType.StackDefine) {
			return;
		}

		rti.getCurrentSeed().add(getChildChange().getModData().getRand64Seed());
		StackType dest = rti.getCFStack().get(rti.getCFStack().size() - 1);
		
		switch (expType) {
			case NumericType:
				double[] result = new double[1];
				if (exp.evaluate(result, tupleSize, rti) != tupleSize) {
					error("Error evaluating parameters (too many or not enough).");
				}
				dest.setNumber(result[0]);
				break;
	
			case ModType:
				Modification[] mod = new Modification[1];
				getChildChange().setVal(mod, rti);
				dest.setModification(mod[0]);
				break;
	
			case RuleType:
				dest.setRule(exp.evalArgs(rti, parent.getParameters()));
				break;
	
			default:
				error("Unimplemented parameter type.");
				break;
		}
	}
}
