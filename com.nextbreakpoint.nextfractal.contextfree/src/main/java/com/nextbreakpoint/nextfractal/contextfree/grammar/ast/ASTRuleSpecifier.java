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

import java.util.Iterator;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ArgSource;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTRuleSpecifier extends ASTExpression {
	private int shapeType;
	private int argSize;
	private String entropy;
	private ArgSource argSource;
	private ASTExpression arguments;
	private CFDGStack simpleRule;
	private int stackIndex;
	private List<ASTParameter> typeSignature;
	private List<ASTParameter> parentSignature;
	private CFDGDriver driver;

	public ASTRuleSpecifier(CFDGDriver driver, Token location) {
		super(false, false, ExpType.RuleType, location);
		this.driver = driver;
		this.shapeType = -1;
		this.argSize = 0;
		this.entropy = "";
		this.argSource = ArgSource.NoArgs;
		this.arguments = null;
		this.simpleRule = null;
		this.stackIndex = 0;
		this.typeSignature = null;
		this.parentSignature = null;
	}

	public ASTRuleSpecifier(CFDGDriver driver, int nameIndex, String name, ASTExpression arguments, List<ASTParameter> parent, Token location) {
		super(arguments == null || arguments.isConstant(), false, ExpType.RuleType, location);
		this.driver = driver;
		this.shapeType = nameIndex;
		this.entropy = name;
		this.argSource = ArgSource.DynamicArgs;
		this.arguments = arguments;
		this.simpleRule = null;
		this.stackIndex = 0;
		this.typeSignature = null;
		this.parentSignature = parent;
		if (parentSignature != null && parentSignature.isEmpty()) {
			parentSignature = null;
		}
	}

	public ASTRuleSpecifier(CFDGDriver driver, int nameIndex, String name, Token location) {
		super(false, false, ExpType.RuleType, location);
		this.driver = driver;
		this.shapeType = nameIndex;
		this.argSize = 0;
		this.entropy = name;
		this.argSource = ArgSource.StackArgs;
		this.arguments = null;
		this.simpleRule = null;
		this.stackIndex = 0;
		this.typeSignature = null;
		this.parentSignature = null;
	}

	public ASTRuleSpecifier(CFDGDriver driver, ASTExpression args, Token location) {
		super(false, false, ExpType.RuleType, location);
		this.driver = driver;
		this.shapeType = -1;
		this.argSize = 0;
		this.entropy = "";
		this.argSource = ArgSource.ShapeArgs;
		this.arguments = args;
		this.simpleRule = null;
		this.stackIndex = 0;
		this.typeSignature = null;
		this.parentSignature = null;
	}

	public ASTRuleSpecifier(CFDGDriver driver, ASTRuleSpecifier spec) {
		super(spec.isConstant, false, spec.type, spec.location);
		this.driver = driver;
		this.argSize = spec.argSize;
		this.entropy = spec.entropy;
		this.argSource = spec.argSource;
		this.arguments = spec.arguments;
		this.simpleRule = spec.simpleRule;
		this.stackIndex = spec.stackIndex;
		this.typeSignature = spec.typeSignature;
		this.parentSignature = spec.parentSignature;
	}

	public int getShapeType() {
		return shapeType;
	}

	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}

	public ArgSource getArgSource() {
		return argSource;
	}

	public void setArgSouce(ArgSource argSource) {
		this.argSource = argSource;
	}

	public List<ASTParameter> getTypeSignature() {
		return typeSignature;
	}

	public void setTypeSignature(List<ASTParameter> typeSignature) {
		this.typeSignature = typeSignature;
	}

	public List<ASTParameter> getParentSignature() {
		return parentSignature;
	}

	public void setParentSignature(List<ASTParameter> parentSignature) {
		this.parentSignature = parentSignature;
	}

	public String getEntropy() {
		return entropy;
	}

	public void setEntropy(String entropy) {
		this.entropy = entropy;
	}

	public int getArgSize() {
		return argSize;
	}

	public ASTExpression getArguments() {
		return arguments;
	}

	public CFDGStack getSimpleRule() {
		return simpleRule;
	}

	public int getStackIndex() {
		return stackIndex;
	}

	public void grab(ASTRuleSpecifier src) {
		isConstant = true;
		shapeType = src.getShapeType();
		argSize = 0;
		argSource = src.getArgSource();
		arguments = null;
		simpleRule = src.getSimpleRule();
		stackIndex = 0;
		typeSignature = src.getTypeSignature();
		parentSignature = src.getParentSignature();
	}

	@Override
	public CFDGStack evalArgs(CFDGRenderer renderer, CFDGStack parent) {
		switch (argSource) {
			case NoArgs:
			case SimpleArgs: {
				return simpleRule;
			}
			case StackArgs: {
				return new CFDGStack(parent.getStack());
			}
			case ParentArgs: {
				if (shapeType != ((RuleHeader)parent.getStack()[0]).getRuleName()) {
					// Child shape is different fromType parent, even though parameters are reused,
					// and we can't finesse it in ASTreplacement::traverse(). Just
					// copy the parameters with the correct shape type.
					CFDGStack ret = new CFDGStack(parent.getStack());
					((RuleHeader)ret.getStack()[0]).setRuleName(shapeType);
					return ret;
				}
			}
			case SimpleParentArgs: {
				return parent;
			}
			case DynamicArgs: {
				//TODO controllare
				CFDGStack ret = CFDGStack.createStackRule(shapeType, argSize, typeSignature);
				AST.evalArgs(renderer, ret, typeSignature.iterator(), arguments, false);
				return ret;
			}
			case ShapeArgs: {
				return arguments.evalArgs(renderer, parent);
			}
			default: {
				return null;
			}
		}
	}

	@Override
	public int evaluate(double[] result, int length, CFDGRenderer renderer) {
		Logger.error("Improper evaluation of a rule specifier", location);
		return -1;
	}

	@Override
	public void entropy(StringBuilder e) {
		e.append(entropy);
	}

	@Override
	public ASTExpression simplify() {
		if (arguments != null) {
			if (arguments instanceof ASTCons) {
				ASTCons cargs = (ASTCons)arguments;
				for (int i = 0; i < cargs.getChildren().size(); i++) {
					ASTExpression expression = cargs.getChild(i).simplify();
					if (expression != null) {
						cargs.setChild(i, expression);
					}
				}
			} else {
				arguments = arguments.simplify();
			}
		}
		if (argSource == ArgSource.StackArgs) {
			boolean isGlobal = false;
			ASTParameter bound = driver.findExpression(shapeType, isGlobal);
			if (bound.getType() != ExpType.RuleType) {
				return this;
			}
			if (bound.getStackIndex() == -1) {
				if (bound.getDefinition().getExp() instanceof ASTRuleSpecifier) {
					ASTRuleSpecifier r = (ASTRuleSpecifier)bound.getDefinition().getExp();
					// The source ASTruleSpec must already be type-checked
					// because it is lexically earlier
					shapeType = r.getShapeType();
					argSize = r.getArgSize();
					argSource = r.getArgSource();
					arguments = null;
					simpleRule = r.getSimpleRule();
					typeSignature = r.getTypeSignature();
					parentSignature = r.getParentSignature();
					isConstant = true;
					locality = Locality.PureLocal;
				} else {
					Logger.error("Error processing shape variable.", location);
				}
			}
		}
		return this;
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		if (arguments != null) {
			arguments = arguments.compile(ph);
		}
		switch (ph) {
			case TypeCheck: {
				switch (argSource) {
					case ShapeArgs: {
						if (arguments.getType() == ExpType.RuleType) {
							Logger.error("Expression does not return a shape", location);
						}
						isConstant = true;
						locality = arguments.getLocality();
						StringBuilder ent = new StringBuilder();
						arguments.entropy(ent);
						entropy = ent.toString();
						return null;
					}

					case SimpleParentArgs: {
						isConstant = true;
						locality = Locality.PureLocal;
						return null;
					}

					case StackArgs: {
						boolean isGlobal = false;
						ASTParameter bound = driver.findExpression(shapeType, isGlobal);
						if (bound.getType() != ExpType.RuleType) {
							Logger.error("Shape name does not bind to a rule variable", location);
							Logger.error("this is what it binds to", bound.getLocation());
						}
						if (bound.getStackIndex() == -1) {
							if (bound.getDefinition() == null || bound.getDefinition().getExp() == null) {
								Logger.error("Error processing shape variable", location);
								return null;
							}
							if (bound.getDefinition().getExp() instanceof ASTRuleSpecifier) {
								ASTRuleSpecifier r = (ASTRuleSpecifier)bound.getDefinition().getExp();
								grab(r);
								locality = Locality.PureLocal;
							} else {
								Logger.error("Error processing shape variable", location);
							}
						} else {
							stackIndex = bound.getStackIndex() - (isGlobal ? 0 : driver.getLocalStackDepth());
							isConstant = false;
							locality = bound.getLocality();
						}
						if (arguments != null && arguments.getType() != ExpType.NoType) {
							Logger.error("Cannot bind parameters twice", arguments.getLocation());
						}
						return null;
					}

					case NoArgs: {
						isConstant = true;
						locality = Locality.PureLocal;
						return null;
					}

					case ParentArgs:
					case SimpleArgs:
						break;

					case DynamicArgs: {
						ASTDefine[] func = new ASTDefine[1];
						List<ASTParameter>[] signature = new List[1];
						String name = driver.getTypeInfo(shapeType, func, signature);
						typeSignature = signature[0];
						if (typeSignature != null && typeSignature.isEmpty()) {
							typeSignature = null;
						}
						if (func[0] != null) {
							if (func[0].getExpType() == ExpType.RuleType) {
								argSource = ArgSource.ShapeArgs;
								arguments = new ASTUserFunction(driver, shapeType, arguments, func[0], location);
								arguments = arguments.compile(ph);
								isConstant = false;
								locality = arguments.getLocality();
							} else {
								Logger.error("Function does not return a shape", arguments.getLocation());
							}
							if (arguments != null) {
								StringBuilder ent = new StringBuilder();
								arguments.entropy(ent);
								entropy = ent.toString();
							}
							return null;
						}
						boolean isGlobal = false;
						ASTParameter bound = driver.findExpression(shapeType, isGlobal);
						if (bound != null && bound.getType() == ExpType.RuleType) {
							// Shape was a stack variable but the variable type
							// was not known to be a ruleSpec until now. Convert
							// to a StackArgs and recompile as such.
							argSource = ArgSource.StackArgs;
							compile(ph);
							return null;
						}
						if (arguments != null && arguments.getType() == ExpType.ReuseType) {
							argSource = ArgSource.ParentArgs;
							if (typeSignature != parentSignature) {
								Iterator<ASTParameter> paramIt = typeSignature.iterator();
								Iterator<ASTParameter> parentIt = parentSignature.iterator();
								ASTParameter param = null;
								ASTParameter parent = null;
								while (paramIt.hasNext() && parentIt.hasNext()) {
									param = paramIt.next();
									parent = parentIt.next();
									if (param != parent) {
										Logger.error("Parameter reuse only allowed when type signature is identical", location);
										Logger.error("target shape parameter type", param.getLocation());
										Logger.error("does not equal source shape parameter type", parent.getLocation());
										break;
									}
								}
								if (!paramIt.hasNext() && parentIt.hasNext()) {
									Logger.error("Source shape has more parameters than target shape.", location);
									Logger.error("extra source parameters start here", parent.getLocation());
								}
								if (paramIt.hasNext() && !parentIt.hasNext()) {
									Logger.error("Target shape has more parameters than source shape.", location);
									Logger.error("extra target parameters start here", param.getLocation());
								}
							}
							isConstant = true;
							locality = Locality.PureLocal;
							return null;
						}
						argSize = ASTParameter.checkType(typeSignature, arguments, true);
						if (argSize < 0) {
							argSource = ArgSource.NoArgs;
							return null;
						}
						if (arguments != null && arguments.getType() != ExpType.NoType) {
							if (arguments.isConstant()) {
								simpleRule = evalArgs(null, null);
								argSource = ArgSource.SimpleArgs;
								driver.storeParams(simpleRule);
								isConstant = true;
								locality = Locality.PureLocal;
							} else {
								isConstant = false;
								locality = arguments.getLocality();
							}
							StringBuilder ent = new StringBuilder();
							arguments.entropy(ent);
							entropy = ent.toString();
						} else {
							argSource = ArgSource.NoArgs;
							simpleRule = CFDGStack.createStackRule(shapeType, 0, typeSignature);
							isConstant = true;
							locality = Locality.PureLocal;
							driver.storeParams(simpleRule);
						}
						break;
					}

					default:
						break;
				}
				break;
			}

			case Simplify:
				break;

			default:
				break;
		}
		return null;
	}
}
