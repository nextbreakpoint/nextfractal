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

import com.nextbreakpoint.nextfractal.contextfree.core.Rand64;
import com.nextbreakpoint.nextfractal.contextfree.grammar.DeferUntilRuntimeException;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Log;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FuncType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTFunction extends ASTExpression {
	private ASTExpression arguments;
	private FuncType funcType;
	private Rand64 random;

	public ASTFunction(String name, ASTExpression arguments, Token location) {
		this(name, arguments, null, location);
	}

	public ASTFunction(String name, ASTExpression arguments, Rand64 seed, Token location) {
		super(arguments != null ? arguments.isConstant() : true, false, ExpType.NumericType, location);
		this.funcType = FuncType.NotAFunction;
		this.arguments = arguments;

		if (name == null || name.trim().length() == 0) {
			throw new RuntimeException("Invalid function name");
		}

		int argcount = arguments != null ? arguments.evaluate((double[])null, 0, null) : 0;

		funcType = FuncType.byName(name);

		if (funcType == FuncType.NotAFunction) {
			throw new RuntimeException("Unknown function");
		}

		if (funcType == FuncType.Infinity && argcount == 0) {
			arguments = new ASTReal(1.0f, location);
		}

		if (funcType.ordinal() >= FuncType.Rand_Static.ordinal() && funcType.ordinal() <= FuncType.RandInt.ordinal()) {
			if (funcType == FuncType.Rand_Static) {
				random = seed;
			} else {
				isConstant = false;
			}

			switch (argcount) {
				case 0:
					arguments = new ASTCons(location, new ASTReal(0.0f, location), new ASTReal(1.0f, location));
					break;
				case 1:
					arguments = new ASTCons(location, new ASTReal(0.0f, location), arguments);
					break;
				case 2:
					break;
				default:
					throw new RuntimeException("Illegal argument(s) for random function");
			}

			if (!isConstant && funcType == FuncType.Rand_Static) {
				throw new RuntimeException("Argument(s) for rand_static() must be constant");
			}

			this.arguments = arguments;
		} else {
			if (funcType.ordinal() < FuncType.Atan2.ordinal()) {
				if (argcount != 1) {
					throw new RuntimeException(funcType == FuncType.Infinity ? "Function takes zero or one arguments" : "Function takes one argument");
				}
			} else {
				if (argcount != 2) {
					throw new RuntimeException("Function takes two arguments");
				}
			}

			this.arguments = arguments;
		}
	}

	public ASTExpression getArguments() {
		return arguments;
	}

	public FuncType getFuncType() {
		return funcType;
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		if (type != ExpType.NumericType) {
		   throw new RuntimeException("Non-numeric expression in a numeric context");
		}

		if (result != null && length < 1)
			return -1;

		if (result == null)
			return 1;


		double[] a = new double[2];
		int count = arguments.evaluate(a, 2, rti);
		// no need to checkParam the argument count, the constructor already checked it

		// But checkParam it anyway to make valgrind happy
		if (count < 0) return 1;

		switch (funcType) {
			case  Cos:
				result[0] = Math.cos(a[0] * 0.0174532925199);
				break;
			case  Sin:
				result[0] = Math.sin(a[0] * 0.0174532925199);
				break;
			case  Tan:
				result[0] = Math.tan(a[0] * 0.0174532925199);
				break;
			case  Cot:
				result[0] = 1.0 / Math.tan(a[0] * 0.0174532925199);
				break;
			case  Acos:
				result[0] = Math.acos(a[0]) * 57.29577951308;
				break;
			case  Asin:
				result[0] = Math.asin(a[0]) * 57.29577951308;
				break;
			case  Atan:
				result[0] = Math.atan(a[0]) * 57.29577951308;
				break;
			case  Acot:
				result[0] = Math.atan(1.0 / a[0]) * 57.29577951308;
				break;
			case  Cosh:
				result[0] = Math.cosh(a[0]);
				break;
			case  Sinh:
				result[0] = Math.sinh(a[0]);
				break;
			case Tanh:
				result[0] = Math.tanh(a[0]);
				break;
			case Acosh:
				result[0] = Math.log(a[0] + Math.sqrt(a[0] * a[0] - 1));
				break;
			case Asinh:
				result[0] = Math.log(a[0] + Math.sqrt(a[0] * a[0] + 1));
				break;
			case Atanh:
				result[0] = Math.log((1 / a[0] + 1) / (1 / a[0] - 1)) / 2;
				break;
			case Log:
				result[0] = Math.log(a[0]);
				break;
			case Log10:
				result[0] = Math.log10(a[0]);
				break;
			case Sqrt:
				result[0] = Math.sqrt(a[0]);
				break;
			case Exp:
				result[0] = Math.exp(a[0]);
				break;
			case Abs:
				if (count == 1) {
					result[0] = Math.abs(a[0]);
				} else {
					result[0] = Math.abs(a[0] - a[1]);
				}
				break;
			case Infinity:
				result[0] = a[0] < 0.0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
				break;
			case Sg:
				result[0] = a[0] == 0.0 ? 0.0 : 1.0;
				break;
			case IsNatural:
				result[0] = evalIsNatural(rti, a[0]) ? 1 : 0;
				break;
			case BitNot:
				result[0] = (~((long)a[0])) & 0xFFFFFFFF;
				break;
			case BitOr:
				result[0] = (((long)a[0]) | ((long)a[1])) & 0xFFFFFFFF;
				break;
			case BitAnd:
				result[0] = (((long)a[0]) & ((long)a[1])) & 0xFFFFFFFF;
				break;
			case BitXOR:
				result[0] = (((long)a[0]) ^ ((long)a[1])) & 0xFFFFFFFF;
				break;
			case BitLeft:
				result[0] = (((long)a[0]) << ((long)a[1])) & 0xFFFFFFFF;
				break;
			case BitRight:
				result[0] = (((long)a[0]) >> ((long)a[1])) & 0xFFFFFFFF;
				break;
			case Atan2:
				result[0] = Math.atan2(a[0], a[1]) * 57.29577951308;
				break;
			case Mod:
				if (arguments.isNatural()) {
					result[0] = ((long)a[0]) % ((long)a[1]);
				} else {
					result[0] = Math.IEEEremainder(a[0], a[1]);
				}
				break;
			case Divides:
				result[0] = (((long)a[0]) % ((long)a[1])) == 0 ? 1.0 : 0.0;
				break;
			case Div:
				result[0] = ((long)a[0]) / ((long)a[1]);
				break;
			case Floor:
				if (rti == null) throw new DeferUntilRuntimeException();
				result[0] = Math.floor(a[0]);
				break;
			case Ftime:
				if (rti == null) throw new DeferUntilRuntimeException();
				result[0] = rti.getCurrentTime();
				break;
			case Frame:
				if (rti == null) throw new DeferUntilRuntimeException();
				result[0] = rti.getCurrentFrame();
				break;
			case Rand_Static:
				result[0] = random.getDouble() * Math.abs(a[1] - a[0]) + Math.min(a[0], a[1]);
				break;
			case Rand:
				if (rti == null) throw new DeferUntilRuntimeException();
				rti.setRandUsed(true);
				result[0] = rti.getCurrentSeed().getDouble() * Math.abs(a[1] - a[0]) + Math.min(a[0], a[1]);
				break;
			case Rand2:
				if (rti == null) throw new DeferUntilRuntimeException();
				rti.setRandUsed(true);
				result[0] = (rti.getCurrentSeed().getDouble() * 2.0 - 1.0) * a[1] + a[0];
				break;
			case RandInt:
				if (rti == null) throw new DeferUntilRuntimeException();
				rti.setRandUsed(true);
				result[0] = Math.floor(rti.getCurrentSeed().getDouble() * Math.abs(a[1] - a[0]) + Math.min(a[0], a[1]));
				break;
			case NotAFunction:
			case Min:
			case Max:
				return -1;
			default:
			   break;
		}

		return 1;
	}

	private boolean evalIsNatural(RTI rti, double n) {
		return n >= 0 && n <= (rti != null ? rti.getMaxNatural() : Integer.MAX_VALUE) && n == Math.floor(n);
	}

	@Override
	public void entropy(StringBuilder e) {
		arguments.entropy(e);
		e.append(funcType.getEntropy());
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		if (arguments != null) {
			arguments = arguments.compile(ph);
		}
		switch (ph) {
			case TypeCheck:
				{
					isConstant = true;
					locality = Locality.PureLocal;
					int argcount = 0;
					if (arguments != null) {
						isConstant = arguments.isConstant();
						locality = arguments.getLocality();
						if (locality == Locality.PureNonlocal) {
							locality = Locality.ImpureNonlocal;
						}
						if (arguments.getType() == ExpType.NumericType) {
							argcount = arguments.evaluate((double[])null, 0);
						} else {
							Log.error("function arguments must be numeric", null);
						}
					}
					if (funcType == FuncType.Infinity && argcount == 0) {
						arguments = new ASTReal(1.0, location);
						return null;
					}
					if (funcType == FuncType.Ftime) {
						if (arguments != null) {
							Log.error("ftime() function takes no arguments", null);
						}
						isConstant = false;
						arguments = new ASTReal(1.0, location);
					}
					if (funcType == FuncType.Frame) {
						if (arguments != null) {
							Log.error("time() function takes no arguments", null);
						}
						isConstant = false;
						arguments = new ASTReal(1.0, location);
					}
					if (funcType.ordinal() >= FuncType.Rand_Static.ordinal() && funcType.ordinal() <= FuncType.RandInt.ordinal()) {
						if (funcType != FuncType.Rand_Static) {
							isConstant = false;
						}
						switch (argcount) {
						case 0:
							arguments = new ASTCons(location, new ASTReal(0.0, location), new ASTReal(funcType == FuncType.RandInt ? 2.0 : 1.0, location));
							break;

						case 1:
							arguments = new ASTCons(location, new ASTReal(0.0, location), arguments);
							break;

						case 2:
							break;

						default:
							Log.error("Illegal argument(s) for random function", null);
							break;
						}
						if (!isConstant && funcType == FuncType.Rand_Static) {
							Log.error("Argument(s) for rand_static() must be constant", null);
						}
						if (funcType == FuncType.RandInt && arguments != null) {
							isNatural = arguments.isNatural();
						}
						return null;
					}

					if (funcType == FuncType.Abs) {
						if (argcount < 1 || argcount > 2) {
							Log.error("function takes one or two arguments", null);
						}
					} else if (funcType.ordinal() < FuncType.BitOr.ordinal()) {
						if (argcount != 1) {
							if (funcType == FuncType.Infinity) {
								Log.error("function takes zero or one arguments", null);
							} else {
								Log.error("function takes one argument", null);
							}
						}

					} else if (funcType.ordinal() < FuncType.Min.ordinal()) {
						if (argcount != 2) {
							Log.error("function takes two arguments", null);
						}

					} else if (funcType.ordinal() < FuncType.BitOr.ordinal()) {
						if (argcount < 2) {
							Log.error("function takes at least two arguments", null);
						}
					}

					if (funcType == FuncType.Mod || funcType == FuncType.Abs || funcType == FuncType.Min || funcType == FuncType.Max || (funcType.ordinal() >= FuncType.BitNot.ordinal() && funcType.ordinal() <= FuncType.BitRight.ordinal())) {
						isNatural = arguments == null || arguments.isNatural();
					}
					if (funcType == FuncType.Factorial || funcType == FuncType.Sg || funcType == FuncType.IsNatural || funcType == FuncType.Div || funcType == FuncType.Divides) {
						if (arguments != null && !arguments.isNatural()) {
							Log.error("function is defined over natural numbers only", null);
						}
						isNatural = true;
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

	@Override
	public ASTExpression simplify() {
		if (isConstant) {
			double[] result = new double[1];
			if (evaluate(result, 1, null) != 1) {
				return this;
			}
			ASTReal r = new ASTReal(result[0], location);
			r.setIsNatural(isNatural);
			return r;
		} else {
			if (arguments != null) {
				arguments = arguments.simplify();
			}
		}
		return this;
	}
}
