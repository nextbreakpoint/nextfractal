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

import org.antlr.v4.runtime.Token;

class ASTFunction extends ASTExpression {
		private ASTExpression arguments;
		private EFuncType funcType;
		private Rand64 random;
    	
    	public ASTFunction(String name, ASTExpression arguments, Token location) {
    		this(name, arguments, null, location);
    	}
    	
    	public ASTFunction(String name, ASTExpression arguments, Rand64 seed, Token location) {
    		super(arguments != null ? arguments.isConstant() : true, false, EExpType.NumericType, location);
    		this.funcType = EFuncType.NotAFunction;
    		this.arguments = arguments;
    		
            if (name == null || name.trim().length() == 0) {
                throw new RuntimeException("Invalid function name");
            }
            
            int argcount = arguments != null ? arguments.evaluate((double[])null, 0, null) : 0;
            
            funcType = EFuncType.getFuncTypeByName(name);
            
            if (funcType == EFuncType.NotAFunction) {
                throw new RuntimeException("Unknown function");
            }
            
            if (funcType == EFuncType.Infinity && argcount == 0) {
                arguments = new ASTReal(1.0f, location);
            }
            
            if (funcType.ordinal() >= EFuncType.Rand_Static.ordinal() && funcType.ordinal() <= EFuncType.RandInt.ordinal()) {
                if (funcType == EFuncType.Rand_Static) {
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
                
                if (!isConstant && funcType == EFuncType.Rand_Static) {
                    throw new RuntimeException("Argument(s) for rand_static() must be constant");
                }

                this.arguments = arguments;
            } else {
            	if (funcType.ordinal() < EFuncType.Atan2.ordinal()) {
            		if (argcount != 1) {
                        throw new RuntimeException(funcType == EFuncType.Infinity ? "Function takes zero or one arguments" : "Function takes one argument");
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
    	
    	public EFuncType getFuncType() {
    		return funcType;
    	}
    	
    	@Override
		public int evaluate(double[] result, int length, RTI rti) { 
	        if (type != EExpType.NumericType) {
	    	   throw new RuntimeException("Non-numeric expression in a numeric context");
	        }
	        
	        if (result != null && length < 1)
	            return -1;
	        
	        if (result == null)
	            return 1;
	        

	        double[] a = new double[2];
	        int count = arguments.evaluate(a, 2, rti);
	        // no need to check the argument count, the constructor already checked it
	        
	        // But check it anyway to make valgrind happy
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
		public ASTExpression compile(ECompilePhase ph) {
        	if (arguments != null) {
        		arguments = arguments.compile(ph);
        	}
        	switch (ph) {
				case TypeCheck:
					{
						isConstant = true;
						locality = ELocality.PureLocal;
						int argcount = 0;
						if (arguments != null) {
							isConstant = arguments.isConstant();
							locality = arguments.getLocality();
							if (locality == ELocality.PureNonlocal) {
								locality = ELocality.ImpureNonlocal;
							}
							if (arguments.getType() == EExpType.NumericType) {
								argcount = arguments.evaluate((double[])null, 0);
							} else {
								error("function arguments must be numeric");
							}
						}
						if (funcType == EFuncType.Infinity && argcount == 0) {
							arguments = new ASTReal(1.0, location);
							return null;
						}
						if (funcType == EFuncType.Ftime) {
							if (arguments != null) {
								error("ftime() function takes no arguments");
							}
							isConstant = false;
							arguments = new ASTReal(1.0, location);
						}
						if (funcType == EFuncType.Frame) {
							if (arguments != null) {
								error("time() function takes no arguments");
							}
							isConstant = false;
							arguments = new ASTReal(1.0, location);
						}
						if (funcType.ordinal() >= EFuncType.Rand_Static.ordinal() && funcType.ordinal() <= EFuncType.RandInt.ordinal()) {
							if (funcType != EFuncType.Rand_Static) {
								isConstant = false;
							}
							switch (argcount) {
							case 0:
								arguments = new ASTCons(location, new ASTReal(0.0, location), new ASTReal(funcType == EFuncType.RandInt ? 2.0 : 1.0, location));
								break;

							case 1:
								arguments = new ASTCons(location, new ASTReal(0.0, location), arguments);
								break;
								
							case 2:
								break;
								
							default:
								error("Illegal argument(s) for random function");
								break;
							}
							if (!isConstant && funcType == EFuncType.Rand_Static) {
								error("Argument(s) for rand_static() must be constant");
							}
							if (funcType == EFuncType.RandInt && arguments != null) {
								isNatural = arguments.isNatural();
							}
							return null;
						}
						
						if (funcType == EFuncType.Abs) {
							if (argcount < 1 || argcount > 2) {
								error("function takes one or two arguments");
							}
						} else if (funcType.ordinal() < EFuncType.BitOr.ordinal()) {
							if (argcount != 1) {
								if (funcType == EFuncType.Infinity) {
									error("function takes zero or one arguments");
								} else {
									error("function takes one argument");
								}
							}
							
						} else if (funcType.ordinal() < EFuncType.Min.ordinal()) {
							if (argcount != 2) {
								error("function takes two arguments");
							}
							
						} else if (funcType.ordinal() < EFuncType.BitOr.ordinal()) {
							if (argcount < 2) {
								error("function takes at least two arguments");
							}
						}
						
						if (funcType == EFuncType.Mod || funcType == EFuncType.Abs || funcType == EFuncType.Min || funcType == EFuncType.Max || (funcType.ordinal() >= EFuncType.BitNot.ordinal() && funcType.ordinal() <= EFuncType.BitRight.ordinal())) {
							isNatural = arguments == null || arguments.isNatural();
						}
						if (funcType == EFuncType.Factorial || funcType == EFuncType.Sg || funcType == EFuncType.IsNatural || funcType == EFuncType.Div || funcType == EFuncType.Divides) {
							if (arguments != null && !arguments.isNatural()) {
								error("function is defined over natural numbers only");
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
