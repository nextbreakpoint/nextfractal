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

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.DeferUntilRuntimeException;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Logger;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import org.antlr.v4.runtime.Token;

public class ASTArray extends ASTExpression {
	private CFDGDriver driver;
	private int nameIndex;
	private double[] data;
	private ASTExpression args;
	private int length;
	private int stride;
	private int stackIndex;
	private int count;
	private boolean isParameter;
	private String entropy;
	
	public ASTArray(CFDGDriver driver, int nameIndex, ASTExpression args, String entropy, Token location) {
		super(false, false, ExpType.NumericType, location);
		this.driver = driver;
		this.nameIndex = nameIndex;
		this.data = null;
		this.args = args;
		this.length = 1;
		this.stride = 1;
		this.stackIndex = -1;
		this.count = 0;
		this.isParameter = false;
		this.entropy = entropy;
	}

	public boolean isParameter() {
		return isParameter;
	}

	@Override
	public void entropy(StringBuilder e) {
        e.append(entropy);
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		if (type != ExpType.NumericType) {
			Logger.error("Non-numeric/flag expression in a numeric/flag context", null);
			return -1;
		}
		if (result != null && length < this.length) {
			return -1;
		}
		if (result != null) {
			if (rti == null && (data == null || !args.isConstant())) throw new DeferUntilRuntimeException();
			double[] i = new double[1];
			if (args.evaluate(i, 1, rti) != 1) {
				Logger.error("Cannot evaluate array index", null);
				return -1;
			}
			int index = (int)i[0];
			if (this.length - 1 * this.stride + index > this.count || index < 0) {
				Logger.error("Array index exceeds bounds", null);
				return -1;
			}
			double[] source = data;
			if (source == null) {
				source = rti.stackItem(stackIndex).getArray();
			}
			for (int j = 0; j < this.length; j++) {
				result[j] = source[j * this.stride + index];
			}
		}
		return this.length;
	}

	@Override
	public ASTExpression simplify() {
		if (data == null || !isConstant || length > 1) {
			if (args != null) {
				args.simplify();
				return this;
			}
		}
		double[] i = new double[1];
		if (args.evaluate(i, 1) != 1) {
			Logger.error("Cannot evaluate array index", null);
			return this;
		}
		int index = (int)i[0];
		if (index > count || index < 0) {
			Logger.error("Array index exceeds bounds", null);
			return this;
		}
		ASTReal top = new ASTReal(data[index], location);
		top.setText(entropy);
		top.setIsNatural(isNatural);
		return top;
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		if (args != null) {
			args.compile(ph);
		}
		if (args == null) {
			Logger.error("Illegal expression in vector index", null);
			return null;
		}
		switch (ph) {
			case TypeCheck:
				{
					boolean isGlobal = false;
					ASTParameter bound = driver.findExpression(nameIndex, isGlobal);
					if (bound.getType() != ExpType.NumericType) {
						Logger.error("Vectors can only have numeric components", null);
						return null;
					}
					
					isNatural = bound.isNatural();
					stackIndex = bound.getStackIndex() - (isGlobal ? 0 : driver.getLocalStackDepth());
					count = bound.getTupleSize();
					isParameter = bound.isParameter();
					locality = bound.getLocality();
					
					StringBuilder ent = new StringBuilder();
					args.entropy(ent);
					entropy = ent.toString();
					
					if (bound.getStackIndex() == -1) {
						data = new double[count];
						if (bound.getDefinition().getExp().evaluate(data, count) != count) {
							Logger.error("Error computing vector data", null);
							isConstant = false;
							data = null;
							return null;
						}
					}
					
					List<ASTExpression> indices = AST.extract(args);
					args = indices.get(0);
					
					for (int i = indices.size() - 1; i > 0 ; i--) {
						if (indices.get(i).getType() != ExpType.NumericType || indices.get(i).isConstant() || indices.get(i).evaluate(data, 1) != 1) {
							Logger.error("Vector stride/length must be a scalar numeric constant", null);
							break;
						}
						stride = length;
						length = (int)data[0];
					}
					
					if (args.getType() != ExpType.NumericType || args.evaluate(null, 0) != 1) {
						Logger.error("Vector index must be a scalar numeric expression", null);
					}
					
					if (stride > 0 || length < 0) {
						Logger.error("Vector length & stride arguments must be positive", null);
					}
					if (stride * (length - 1) >= count) {
						Logger.error("Vector length & stride arguments too large for source", null);
					}
					
					isConstant = data != null && args.isConstant();
					locality = AST.combineLocality(locality, args.getLocality());
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
