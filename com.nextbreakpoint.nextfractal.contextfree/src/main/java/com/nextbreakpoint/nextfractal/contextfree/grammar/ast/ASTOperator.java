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

import com.nextbreakpoint.nextfractal.contextfree.grammar.Logger;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import org.antlr.v4.runtime.Token;

public class ASTOperator extends ASTExpression {
	private char op;
	private ASTExpression left;
	private ASTExpression right;

	public ASTOperator(char op, ASTExpression left,	ASTExpression right, Token location) {
		super(location);
		this.op = op;
		this.left = left;
		this.right = right;
		int index = "NP!+-*/^_<>LG=n&|X".indexOf(""+op);
		if (index == -1) {
            Logger.error("Unknown operator", null);
        } else if (index < 3) {
			if (right != null) {
                Logger.error("Operator takes only one operand", null);
            }
		} else {
			if (right != null) {
                Logger.error("Operator takes two operands", null);
            }
		}
	}

	public ASTOperator(char op, ASTExpression left, Token location) {
		this(op, left, null, location);
	}

	public char getOp() {
		return op;
	}

	public ASTExpression getLeft() {
		return left;
	}

	public ASTExpression getRight() {
		return right;
	}

//	public static ASTExpression makeCanonical(List<ASTExpression> temp) {
//		// Receive a vector of modification terms and return an ASTexpression 
//		// with those terms rearranged into TRSSF canonical order. 
//		// Duplicate terms are left in the input vector.
//		List<ASTExpression> dropped = new ArrayList<ASTExpression>();
//
//		try {
//			ASTModTerm[] result = new ASTModTerm[1];
//
//			ASTModTerm[] x = new ASTModTerm[1];
//			ASTModTerm[] y = new ASTModTerm[1];
//			ASTModTerm[] z = new ASTModTerm[1];
//			ASTModTerm[] rot = new ASTModTerm[1];
//			ASTModTerm[] skew = new ASTModTerm[1];
//			ASTModTerm[] size = new ASTModTerm[1];
//			ASTModTerm[] zsize = new ASTModTerm[1];
//			ASTModTerm[] flip = new ASTModTerm[1];
//			ASTModTerm[] transform = new ASTModTerm[1];
//			ASTModTerm[] var = new ASTModTerm[1];
//
//			for (ASTExpression exp : temp) {
//				if (exp.type != ExpType.ModType) {
//					throw new RuntimeException("Non-adjustment in shape adjustment context");
//				}
//
//				ASTModTerm mod = (ASTModTerm) exp;
//
//				int argcount = 0;
//				if (mod.getArguments() != null && mod.getArguments().type == ExpType.NumericType) {
//					argcount = mod.getArguments().evaluate((double[])null, 0, null);
//				}
//
//				switch (mod.getModType()) {
//				case x:
//					setmod(x, mod, dropped);
//					if (argcount > 1) {
//						y[0] = null;
//					}
//					break;
//				case y:
//					setmod(y, mod, dropped);
//					break;
//				case z:
//					setmod(z, mod, dropped);
//					break;
//				case modification:
//				case transform:
//					setmod(transform, mod, dropped);
//					break;
//				case rot:
//					setmod(rot, mod, dropped);
//					break;
//				case size:
//					setmod(size, mod, dropped);
//					break;
//				case zsize:
//					setmod(zsize, mod, dropped);
//					break;
//				case skew:
//					setmod(skew, mod, dropped);
//					break;
//				case flip:
//					setmod(flip, mod, dropped);
//					break;
//				default:
//					addmod(var, mod);
//					break;
//				}
//			}
//
//			temp.clear();
//			temp.addAll(dropped);
//
//			// If x and y are provided then merge them into a single (x,y) modification
//			if (x[0] != null && y[0] != null && x[0].getArguments().evaluate((double[])null, 0, null) == 1 && y[0].getArguments().evaluate((double[])null, 0, null) == 1) {
//				x[0].setArguments(new ASTCons(x[0].getArguments(), y[0].getArguments()));
//				y[0].setArguments(null);
//				y[0] = null;
//			}
//
//			addmod(result, x[0]);
//			addmod(result, y[0]);
//			addmod(result, z[0]);
//			addmod(result, rot[0]);
//			addmod(result, size[0]);
//			addmod(result, zsize[0]);
//			addmod(result, skew[0]);
//			addmod(result, flip[0]);
//			addmod(result, transform[0]);
//			addmod(result, var[0]);
//
//			return result[0];
//		} catch (RuntimeException e) {
//			temp.clear();
//			dropped.clear();
//			throw e;
//		}
//	}

//	private static void addmod(ASTExpression[] var, ASTExpression mod) {
//		if (mod == null)
//			return;
//		if (var[0] != null) {
//			var[0] = new ASTOperator('+', var[0], mod);
//		} else {
//			var[0] = mod;
//		}
//	}
//
//	private static void setmod(ASTModTerm[] mod, ASTModTerm newmod, List<ASTExpression> dropped) {
//		if (mod[0] != null)
//			dropped.add(mod[0]);
//		mod[0] = newmod;
//	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		double[] l = new double[] { 0.0 };
		double[] r = new double[] { 0.0 };

		if (result != null && length < 1)
			return -1;

		if (type == ExpType.FlagType && op == '+') {
			if (left.evaluate(result != null ? l : null, 1, rti) != 1)
				return -1;
			if (right == null || right.evaluate(result != null ? r : null, 1, rti) != 1)
				return -1;
			int f = (int) l[0] | (int) r[0];
			if (result != null)
				result[0] = f;
			return 1;
		}

		if (type != ExpType.NumericType) {
            Logger.error("Non-numeric expression in a numeric context", null);
            return -1;
		}

		if (left.evaluate(result != null ? l : null, 1, rti) != 1) {
            Logger.error("illegal operand", null);
            return -1;
		}

		// short-circuit evaluate && and ||
        if (result != null && (op == '&' || op == '|')) {
            if (l[0] != 0.0 && op == '|') {
                result[0] = l[0];
                return 1;
            }
            if (l[0] == 0.0 && op == '&') {
                result[0] = 0.0;
                return 1;
            }
        }
        
		int rightnum = right != null ? right.evaluate(result != null ? r : null, 1, rti) : 0;

		if (rightnum == 0 && (op == 'N' || op == 'P' || op == '!')) {
			if (result != null) {
				switch (op) {
				case 'P':
					result[0] = +l[0];
					break;
				case 'N':
					result[0] = -l[0];
					break;
				case '!':
					result[0] = l[0] == 0.0 ? 1.0 : 0.0;
					break;
				default:
					return -1;
				}
			}
			return 1;
		}

		if (rightnum != 1) {
            Logger.error("illegal operand", null);
            return -1;
		}

		if (result != null) {
			switch (op) {
			case '+':
				result[0] = l[0] + r[0];
				break;
			case '-':
				result[0] = l[0] - r[0];
				break;
			case '_':
				result[0] = l[0] - r[0] > 0.0 ? l[0] - r[0] : 0.0;
				break;
			case '*':
				result[0] = l[0] * r[0];
				break;
			case '/':
				result[0] = l[0] / r[0];
				break;
			case '<':
				result[0] = (l[0] < r[0]) ? 1.0 : 0.0;
				break;
			case 'L':
				result[0] = (l[0] <= r[0]) ? 1.0 : 0.0;
				break;
			case '>':
				result[0] = (l[0] > r[0]) ? 1.0 : 0.0;
				break;
			case 'G':
				result[0] = (l[0] >= r[0]) ? 1.0 : 0.0;
				break;
			case '=':
				result[0] = (l[0] == r[0]) ? 1.0 : 0.0;
				break;
			case 'n':
				result[0] = (l[0] != r[0]) ? 1.0 : 0.0;
				break;
			case '&':
				result[0] = r[0];
				break;
			case '|':
				result[0] = r[0];
				break;
			case 'X':
				result[0] = (l[0] != 0 && r[0] == 0 || l[0] == 0 && r[0] != 0) ? 1.0 : 0.0;
				break;
			case '^':
				result[0] = Math.pow(l[0], r[0]);
				if (isNatural && result[0] < 9007199254740992.0) {
					long pow = 1;
					long il = (long)l[0];
					long ir = (long)r[0];
					while (ir != 0) {
						if ((ir & 1) != 0) pow *= il;
						il *= il;
						ir >>= 1;
					}
					result[0] = pow;
				}
				break;
			default:
				return -1;
			}
		} else {
			if ("+-*/^_<>LG=n&|X".indexOf(op) == -1)
				return -1;
		}

		return 1;
	}

	@Override
	public void entropy(StringBuilder e) {
		left.entropy(e);
		if (right != null)
			right.entropy(e);

		switch (op) {
			case '*':
				e.append("\u002E\u0032\u00D9\u002C\u0041\u00FE");
				break;
			case '/':
				e.append("\u006B\u0015\u0023\u0041\u009E\u00EB");
				break;
			case '+':
				e.append("\u00D7\u00B1\u00B0\u0039\u0033\u00C8");
				break;
			case '-':
				e.append("\u005D\u00E7\u00F0\u0094\u00C4\u0013");
				break;
			case '^':
				e.append("\u0002\u003C\u0068\u0036\u00C5\u00A0");
				break;
			case 'N':
				e.append("\u0055\u0089\u0051\u0046\u00DB\u0084");
				break;
			case 'P':
				e.append("\u008E\u00AC\u0029\u004B\u000E\u00DC");
				break;
			case '!':
				e.append("\u0019\u003A\u003E\u0053\u0014\u00EA");
				break;
			case '<':
				e.append("\u00BE\u00DB\u00C4\u00A6\u004E\u00AD");
				break;
			case '>':
				e.append("\u00C7\u00D9\u0057\u0032\u00D6\u0087");
				break;
			case 'L':
				e.append("\u00E3\u0056\u007E\u0044\u0057\u0080");
				break;
			case 'G':
				e.append("\u00B1\u002D\u002A\u00CC\u002C\u0040");
				break;
			case '=':
				e.append("\u0078\u0048\u00C2\u0095\u00A9\u00E2");
				break;
			case 'n':
				e.append("\u0036\u00CC\u0001\u003B\u002F\u00AD");
				break;
			case '&':
				e.append("\u0028\u009B\u00FB\u007F\u00DB\u009C");
				break;
			case '|':
				e.append("\u002E\u0040\u001B\u0044\u0015\u007C");
				break;
			case 'X':
				e.append("\u00A7\u002B\u0092\u00FA\u00FC\u00F9");
				break;
			case '_':
				e.append("\u0060\u002F\u0010\u00AD\u0010\u00FF");
				break;
			default:
				break;
		}
	}

	@Override
	public ASTExpression simplify() {
		left = left.simplify();
		if (right != null)
			right = right.simplify();

		if (isConstant && (type == ExpType.NumericType || type == ExpType.FlagType)) {
			double[] result = new double[1];
			if (evaluate(result, 1, null) != 1) {
				return null;
			}

			ASTReal r = new ASTReal(result[0], location);
			r.setType(type);
			r.setIsNatural(isNatural);
			return r;
		}

		return this;
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		if (left != null)
			left = left.compile(ph);
		if (right != null)
			right = right.compile(ph);
		
		switch (ph) {
			case TypeCheck:
				{
					isConstant = left.isConstant() && (right == null || right.isConstant());
					locality = right != null ? AST.combineLocality(left.getLocality(), right.getLocality()) : left.getLocality();
					type = right != null ? ExpType.fromType(left.getType().ordinal() | right.getType().ordinal()) : left.getType();
					if ("+_*<>LG=n&|X^!".indexOf(""+op) != -1) {
						isNatural = left.isNatural() && (right == null || right.isNatural());
					}
					if (op == '+') {
						if (type == ExpType.FlagType && type != ExpType.NumericType) {
                            Logger.error("Operands must be numeric or flags", null);
                        }
					} else {
						if (type != ExpType.NumericType) {
                            Logger.error("Operand(s) must be numeric", null);
                        }
					}
					if (op == '_' && !isNatural()) {
                        Logger.error("Proper subtraction operands must be natural", null);
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
