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

import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTSelect extends ASTExpression {
		private static final int NOT_CACHED = -1;
		private List<ASTExpression> arguments;
		private ASTExpression selector;
		private int tupleSize;
		private int indexCache;
		private StringBuilder entropy;
		private boolean isSelect;
		
		public ASTSelect(ASTExpression arguments, boolean asIf, Token location) {
			super(location);
			tupleSize = -1;
			indexCache = NOT_CACHED;
			selector = arguments;
			isConstant = false;
			isSelect = asIf;
			if (selector == null || selector.size() < 3) {
				error("select()/if() function requires arguments");
			}
		}

		public boolean isSelect() {
			return isSelect;
		}

		@Override
		public void entropy(StringBuilder e) {
	        e.append(entropy);
		}

		@Override
		public StackRule evalArgs(RTI rti, StackRule parent) {
	        if (type != EExpType.RuleType) {
	            throw new RuntimeException("Evaluation of a non-shape select() in a shape context");
	        }
	        return arguments.get(getIndex(rti)).evalArgs(rti, parent);
		}

		@Override
		public ASTExpression simplify() {
			if (indexCache == NOT_CACHED) {
				for (ASTExpression argument : arguments) {
					if (argument != null) {
	            		argument = argument.simplify();
	            	}
				}
				selector.simplify();
		        return this;
			}
			ASTExpression chosenOne = arguments.get(indexCache);
	        return chosenOne.simplify();
		}
		
		@Override
		public int evaluate(double[] result, int length, RTI rti) {
	        if (type != EExpType.NumericType) {
	            throw new RuntimeException("Evaluation of a non-shape select() in a numeric context");
	        }
	        
	        if (result == null)
	            return tupleSize;
	        
	        return arguments.get(getIndex(rti)).evaluate(result, length, rti);
		}

		@Override
		public void evaluate(Modification[] modification, boolean shapeDest, RTI rti) {
	        if (type != EExpType.ModType) {
	            throw new RuntimeException("Evaluation of a non-adjustment select() in an adjustment context");
	        }
	        
	        arguments.get(getIndex(rti)).evaluate(modification, shapeDest, rti);
		}

		@Override
		public ASTExpression compile(ECompilePhase ph) {
			if (selector == null) {
				return null;
			}
			for (ASTExpression argument : arguments) {
				if (argument != null) {
					argument.compile(ph);
				}
			}
			if (selector != null) {
				selector.compile(ph);
			}
			
			switch (ph) {
				case TypeCheck:
					{
						selector.entropy(entropy);
						entropy.append("\u00B5\u00A2\u004A\u0074\u00A9\u00DF");
						locality = selector.getLocality();
						
						arguments = extract(selector);
						selector = arguments.get(0);
						arguments.remove(0);
						
						if (selector.getType() != EExpType.NumericType || selector.evaluate(null, 0) != 1) {
							error("if()/select() selector must be a numeric scalar");
							return null;
						}
						
						if (arguments.size() < 2) {
							error("if()/select() selector must have at least two arguments");
							return null;
						}
						
						type = arguments.get(0).getType();
						isNatural = arguments.get(0).isNatural();
						tupleSize = type == EExpType.NumericType ? arguments.get(0).evaluate(null, 0) : 1;
						for (int i = 1; i < arguments.size(); i++) {
							if (type != arguments.get(i).getType()) {
								error("select()/if() choices must be of same type");
							} else if (type == EExpType.NumericType && tupleSize != -1 && arguments.get(0).evaluate(null, 0)  != tupleSize) {
								error("select()/if() choices must be of same length");
								tupleSize = -1;
							}
							isNatural = isNatural && arguments.get(i).isNatural();
						}
						
						if (selector.isConstant()) {
							indexCache = getIndex(null);
							isConstant = arguments.get(indexCache).isConstant();
							locality = arguments.get(indexCache).getLocality();
							isNatural = arguments.get(indexCache).isNatural();
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
		
		private int getIndex(RTI rti) {
			if (indexCache != NOT_CACHED) {
				return indexCache;
			}
			
			double select[] = new double[] { 0.0 };
			selector.evaluate(select, 1, rti);
			
			if (isSelect) {
				return select[0] != 0 ? 0 : 1;
			}
			
			int i = (int)select[0];
			
			if (i >= arguments.size()) {
				return arguments.size() - 1;
			}
			
			return i;
		}
	}
