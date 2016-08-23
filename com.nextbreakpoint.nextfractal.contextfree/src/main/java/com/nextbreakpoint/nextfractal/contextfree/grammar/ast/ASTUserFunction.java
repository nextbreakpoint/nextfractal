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

import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTUserFunction extends ASTExpression {
	private ASTExpression arguments;
	private ASTDefine definition;
	private int nameIndex;
	protected boolean isLet;
	private CFDGDriver driver;
	private int oldTop;
	private int oldSize;

	public ASTUserFunction(CFDGDriver driver, int nameIndex, ASTExpression arguments, ASTDefine definition, Token location) {
		super(false, false, ExpType.NoType, location);
		this.driver = driver;
		this.nameIndex = nameIndex;
		this.definition = definition;
		this.arguments = arguments;
		isLet = false;
	}

	public ASTExpression getArguments() {
		return arguments;
	}

	protected void setArguments(ASTExpression arguments) {
		this.arguments = arguments;
	}

	public ASTDefine getDefinition() {
		return definition;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public boolean isLet() {
		return isLet;
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		if (type != ExpType.NumericType) {
			Logger.error("Function does not evaluate to a number", location);
			return -1;
		}
		if (result != null && length < definition.getTupleSize()) {
			return -1;
		}
		if (result == null) {
			return definition.getTupleSize();
		}
		if (rti == null) throw new DeferUntilRuntimeException();
		if (rti.isRequestStop() || RTI.abortEverything()) {
			throw new CFDGException("Stopping");
		}
		setupStack(rti);
		if (definition.getExp().evaluate(result, length, rti) != definition.getTupleSize()) {
			Logger.error("Error evaluating function", location);
		};
		cleanupStack(rti);
		return definition.getTupleSize();
	}

	@Override
	public void evaluate(Modification result, boolean shapeDest, RTI rti) {
		if (type != ExpType.ModType) {
			Logger.error("Function does not evaluate to an adjustment", location);
			return;
		}
		if (rti == null) throw new DeferUntilRuntimeException();
		if (rti.isRequestStop() || RTI.abortEverything()) {
			throw new CFDGException("Stopping");
		}
		setupStack(rti);
		definition.getExp().evaluate(result, shapeDest, rti);
		cleanupStack(rti);
	}

	@Override
	public void entropy(StringBuilder e) {
		if (arguments != null) {
			arguments.entropy(e);
		}
		e.append(definition.getName());
	}

	@Override
	public ASTExpression simplify() {
		if (arguments != null) {
			if (arguments instanceof ASTCons) {
				ASTCons carg = (ASTCons)arguments;
				for (int i = 0; i < carg.getChildren().size(); i++) {
					carg.setChild(i, carg.getChild(i).simplify());
				}
			} else {
				arguments = arguments.simplify();
			}
		}
		return this;
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		switch (ph) {
			case TypeCheck: {
				// Function calls and shape specifications are ambiguous at parse
				// time so the parser always chooses a function call. During
				// type checkParam we may need to convert to a shape spec.
				ASTDefine[] def = new ASTDefine[1];
				@SuppressWarnings("unchecked")
				List<ASTParameter>[] p = new List[1];
				String name = driver.getTypeInfo(nameIndex, def, p);
				if (def[0] != null && p[0] != null) {
					Logger.error("Name matches both a function and a shape", location);
					return null;
				}
				if (def[0] == null && p[0] == null) {
					Logger.error("Name does not match shape name or function name", location);
					return null;
				}
				if (def[0] != null) {
					if (arguments != null) {
						arguments = arguments.compile(ph);
					}
					definition = def[0];
					ASTParameter.checkType(def[0].getParameters(), arguments, false);
					isConstant = false;
					isNatural = definition.isNatural();
					type = definition.getExpType();
					locality = arguments != null ? arguments.getLocality() : Locality.PureLocal;
					if (definition.getExp() != null && definition.getExp().getLocality() == Locality.ImpureNonlocal && locality == Locality.PureNonlocal) {
						locality = Locality.ImpureNonlocal;
					}
					return null;
				}
				ASTRuleSpecifier r = new ASTRuleSpecifier(driver, nameIndex, name, arguments, null, location);
				r.compile(ph);
				return r;
			}
	
			case Simplify: 
				break;
	
			default:
				break;
		}
		return null;
	}

	@Override
	public StackRule evalArgs(RTI rti, StackRule parent) {
		if (type != ExpType.RuleType) {
			Logger.error("Function does not evaluate to a shape", location);
			return null;
		}
		if (rti == null) throw new DeferUntilRuntimeException();
		if (rti.isRequestStop() || RTI.abortEverything()) {
			throw new CFDGException("Stopping");
		}
		//TODO da controllare
		setupStack(rti);
		StackRule ret = definition.getExp().evalArgs(rti, parent);
		cleanupStack(rti);
		return ret;
	}
	
	private void setupStack(RTI rti) {
		oldTop = rti.getLogicalStackTop();
		oldSize = rti.getStackSize();
		if (definition.getStackCount() > 0) {
			if (oldSize + definition.getStackCount() > rti.getStackSize()) {
				Logger.error("Maximum stack size exceeded", location);
			}
			rti.setStackSize(oldSize + definition.getStackCount());
			rti.getStackItem(oldSize).evalArgs(rti, arguments, definition.getParameters(), isLet);
			rti.setLogicalStackTop(rti.getStackSize());
		}
	}

	private void cleanupStack(RTI rti) {
		if (definition.getStackCount() > 0) {
			rti.setStackItem(oldSize, null);
			rti.setLogicalStackTop(oldTop);
			rti.setStackSize(oldSize);
		}
	}
}
