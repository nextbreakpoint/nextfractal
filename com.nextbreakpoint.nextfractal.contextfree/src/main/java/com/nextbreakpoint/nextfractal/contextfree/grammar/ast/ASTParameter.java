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

import com.nextbreakpoint.nextfractal.contextfree.grammar.Log;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.DefineType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTParameter {
	public static boolean Impure = false;

	protected Token location;
	private ExpType type = ExpType.NoType;
	private boolean isParameter;
	private boolean isLoopIndex;
	private boolean isNatural;
	private Locality locality;
	private int stackIndex = -1;
	private int nameIndex = -1;
	private int tupleSize = -1;
	private ASTDefine definition;

	public ASTParameter(Token location) {
		this.location = location;
	}

	public ASTParameter(String type, int nameIndex, Token location) {
		this.location = location;
		init(type, nameIndex);
	}

	public ASTParameter(int nameIndex, ASTDefine definition) {
		init(nameIndex, definition);
	}

	public ASTParameter(int nameIndex, boolean natural, boolean local, Token location) {
		this.isLoopIndex = true;
		this.isNatural = natural;
		this.nameIndex = nameIndex;
		this.location = location;
	}

	public ASTParameter(ASTParameter param) {
		this.isParameter = param.isParameter();
		this.isLoopIndex = param.isLoopIndex();
		this.stackIndex = param.getStackIndex();
		this.nameIndex = param.getNameIndex();
		this.isNatural = param.isNatural();
		this.locality = param.getLocality();
		this.tupleSize = param.getTupleSize();
		this.location = param.getLocation();
	}

	public void init(String type, int nameIndex) {
		locality = Locality.PureNonlocal;
		int[] tupleSizeVal = new int[1];
		boolean[] isNaturalVal = new boolean[1];
		this.type = AST.decodeType(type, tupleSizeVal, isNaturalVal, location);
		tupleSize = tupleSizeVal[0];
		isNatural = isNaturalVal[0];
		this.nameIndex = nameIndex;
		definition = null;
	}

	public void init(int nameIndex, ASTDefine definition) {
		type = definition.getExpType();
		locality = definition.getExp() != null ? definition.getExp().getLocality() : Locality.PureLocal;
		tupleSize = definition.getTupleSize();
		if (type == ExpType.NumericType) {
			isNatural = definition.getExp() != null && definition.getExp().isNatural();
			if (tupleSize == 0) {
				tupleSize = 1;
			}
			if (tupleSize < 1 || tupleSize > 1000000000) {
				Log.error("Illegal vector size (<1 or >99)", location);
			}
			this.nameIndex = nameIndex;
			if (definition.getDefineType() == DefineType.ConstDefine) {
				this.definition = definition;
			}
		}
	}

	public void checkParam() {
		if (nameIndex == -1) {
			throw new RuntimeException("Reserved keyword used for parameter name");
		}
	}

	//TODO controllare

	public boolean compare(ASTParameter p) {
		if (type != p.type) return true;
		if (type == ExpType.NumericType && tupleSize != p.tupleSize) return true;
		return false;
	}

	public boolean compare(ASTExpression e) {
		if (type != e.type) return true;
		if (type == ExpType.NumericType && tupleSize != e.evaluate(null, 0, null)) return true;
		return false;
	}

	public static int checkType(List<? extends ASTParameter> types, ASTExpression args, boolean checkNumber) {
		// Walks down the right edge of an expression tree checking that the types
		// of the children match the specified argument types
		if ((types == null || types.isEmpty()) && args == null) {
			return 0;
		}
		if (types == null || types.isEmpty()) {
			Log.error("Arguments are not expected.", args.getLocation());
			return -1;
		}
		if (args == null) {
			Log.error("Arguments are expected.", null);
			return -1;
		}

		boolean justCount = args.getType() == ExpType.NoType;

		int count = 0;
		int size = 0;
		int expect = args.size();

		for (ASTParameter param : types) {
			size += param.getTupleSize();
			count += 1;
			if (justCount) {
				continue;
			}
			if (count >= expect) {
				Log.error("Not enough arguments", args.getLocation());
				return -1;
			}
			ASTExpression arg = args.getChild(count);
			if (param.getType() != arg.getType()) {
				Log.error("Incorrect argument type", arg.getLocation());
				Log.error("This is the expected type", param.getLocation());
				return -1;
			}
			if (param.isNatural() && !arg.isNatural() && !Impure) {
				Log.error("This expression does not satisfy the natural number requirement", arg.getLocation());
			}
			if (param.getType() == ExpType.NumericType && param.getTupleSize() != arg.evaluate(null, 0)) {
				if (param.getTupleSize() == 1) {
					Log.error("This argument should be scalar", arg.getLocation());
				} else {
					Log.error("This argument should be a vector", arg.getLocation());
					Log.error("This is the expected type", param.getLocation());
				}
				return -1;
			}
			if (arg.getLocality() != Locality.PureLocal && arg.getLocality() != Locality.PureNonlocal && param.getType() == ExpType.NumericType && !param.isNatural() && !Impure && checkNumber) {
				Log.error("This expression does not satisfy the number parameter requirement", arg.getLocation());
				return -1;
			}
		}

		if (count < expect) {
			Log.error("Too many arguments", args.getChild(count).getLocation());
			return -1;
		}

		return size;
	}

	public ASTExpression constCopy(String entropy) {
		switch (type) {
			case NumericType: {
				double[] data = new double[tupleSize];
				boolean natural = isNatural;
				int valCount = definition.getExp().evaluate(data, tupleSize);
				if (valCount != tupleSize) {
					Log.error("Unexpected compile error", getLocation());
				}
				ASTReal top = new ASTReal(data[0], definition.getExp().getLocation());
				top.setText(entropy);
				ASTExpression list = top;
				for (int i = 1; i < valCount; i++) {
					ASTReal next = new ASTReal(data[i], getLocation());
					list = list.append(next);
				}
				list.setIsNatural(natural);
				list.setLocality(locality);
				return list;
			}
			case ModType: {
				ASTModification ret = null;
				if (definition.getExp() instanceof ASTModification) {
					ret = new ASTModification(definition.getDriver(), (ASTModification)definition.getExp(), getLocation());
				} else {
					ret = new ASTModification(definition.getDriver(), definition.getChildChange(), getLocation());
				}
				ret.setLocality(locality);
				return ret;
			}
			case RuleType: {
				if (definition.getExp() instanceof ASTRuleSpecifier) {
					ASTRuleSpecifier r = (ASTRuleSpecifier)definition.getExp();
					ASTRuleSpecifier ret = new ASTRuleSpecifier(definition.getDriver(), r.getShapeType(), entropy, null, null, getLocation());
					ret.grab(r);
					ret.setLocality(locality);
					return ret;
				} else {
					Log.error("Internal error computing bound rule specifier", getLocation());
				}
				break;
			}
			default: {
				break;
			}
		}
		return null;
	}

	public ExpType getType() {
		return type;
	}

	public boolean isParameter() {
		return isParameter;
	}

	public boolean isLoopIndex() {
		return isLoopIndex;
	}

	public ASTDefine getDefinition() {
		return definition;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public int getStackIndex() {
		return stackIndex;
	}

	public int getTupleSize() {
		return tupleSize;
	}

	public void setStackIndex(int stackIndex) {
		this.stackIndex = stackIndex;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}

	public boolean isNatural() {
		return isNatural;
	}

	public void setIsNatural(boolean natural) {
		this.isNatural = natural;
	}
}
