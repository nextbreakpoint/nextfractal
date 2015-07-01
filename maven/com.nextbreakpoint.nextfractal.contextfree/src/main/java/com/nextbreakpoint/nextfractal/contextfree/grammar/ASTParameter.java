/*
 * NextFractal 1.1.1
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

import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTParameter extends ASTExpression {
    	public static boolean Impure = false;
		
    	private EExpType type;
    	private boolean isParameter;
    	private boolean isLoopIndex;
    	private boolean isNatural;
    	private ELocality locality;
    	private int nameIndex;
    	private ASTDefine definition;
    	private int stackIndex;
    	private int tupleSize;
    	
    	public ASTParameter(Token location) {
    		super(true, false, EExpType.NoType, location);
    		nameIndex = -1;
    		stackIndex = -1;
    		tupleSize = 1;
    	}
    	
    	public ASTParameter(String type, int nameIndex, Token location) {
    		super(true, false, EExpType.NoType, location);
    		nameIndex = -1;
    		stackIndex = -1;
    		tupleSize = 1;
    	}
    	
		public ASTParameter(int nameIndex, ASTDefine definition, Token location) {
    		super(true, false, EExpType.NoType, location);
			// TODO Auto-generated constructor stub
		}

		public ASTParameter(int nameIndex, boolean natural, boolean local, Token location) {
    		super(true, false, EExpType.NoType, location);
			// TODO Auto-generated constructor stub
		}

		public ASTParameter(ASTParameter param, Token location) {
    		super(true, false, EExpType.NoType, location);
			// TODO Auto-generated constructor stub
		}

		public void init(String type, int nameIndex) {
//			this.nameIndex = nameIndex;
//			type = expression.type;
//			expression = expression.simplify();
//
//			switch (type) {
//				case ModType: {
//					tupleSize = ASTModification.SIZE;
//					ASTExpression mod = expression;
//					expression = null;
//					modification.init(mod);
//					break;
//				}
//				case RuleType: {
//					tupleSize = 1;
//					break;
//				}
//				case NumericType: {
//					tupleSize = expression.evaluate((double[])null, 0, null);
//					if (tupleSize == 0)
//						tupleSize = 1; // loop index
//					if (tupleSize < 1 || tupleSize > 8) {
//						throw new RuntimeException("Illegal vector size (<1 or >8)");
//					}
//					break;
//				}
//				default: {
//					throw new RuntimeException("Definition expression has mixed type");
//				}
//			}
//
//			// Set the Modification entropy to parameter name, not its own contents
//			int[] i = new int[1];
//			modification.getModData().getRand64Seed().init();
//			modification.getModData().getRand64Seed().xorString(name, i);
		}

		public void init(int nameIndex, ASTDefine definition) {
//			this.nameIndex = nameIndex;
//			expression = null;
//
//			if (typeName.equals("number")) {
//				type = EExpType.NumericType;
//			} else if (typeName.equals("adjustment")) {
//				tupleSize = ASTModification.SIZE;
//				type = EExpType.ModType;
//			} else if (typeName.equals("shape")) {
//				type = EExpType.RuleType;
//				tupleSize = 1;
//			} else if (typeName.startsWith("vector") && typeName.length() == 7) {
////				String size = typeName.substring(6, 7);
//				if (typeName.charAt(6) > '0' && typeName.charAt(6) < '9') {
//					type = EExpType.NumericType;
//					tupleSize = typeName.charAt(6) - '0';
//					if (tupleSize < 1 || tupleSize > 8) {
//						throw new RuntimeException("Illegal vector size (<1 or >8)");
//					}
//				}
//			} else {
//				type = EExpType.NoType;
//			}
//
//			int[] i = new int[1];
//			modification.getModData().getRand64Seed().init();
//			modification.getModData().getRand64Seed().xorString(name, i);
		}
        
        public void check() 
        { 
            if (type == EExpType.NoType)
            	throw new RuntimeException("Unknown parameter type");
            if (nameIndex == -1)
            	throw new RuntimeException("Reserved keyword used for parameter name");
        }

        public boolean compare(ASTParameter p) {
            if (type != p.type) return true;
            if (type == EExpType.NumericType && tupleSize != p.tupleSize) return true;
            return false;
        }
        
        public boolean compare(ASTExpression e) {
            if (type != e.type) return true;
            if (type == EExpType.NumericType && tupleSize != e.evaluate((double[])null, 0, null)) return true;
            return false;
        }
        
        public static int checkType(List<? extends ASTParameter> types, ASTExpression args, boolean checkNumber)
        {
//            // Walks down the right edge of an expression tree checking that the types
//            // of the children match the specified argument types
//            if ((types == null || types.isEmpty()) && (args == null)) return 0;
//            if (types == null || types.isEmpty()) {
//            	throw new RuntimeException("Arguments are not expected.");
////                return -1;
//            }
//            if (args == null) {
//            	throw new RuntimeException("Arguments are expected.");
////                return -1;
//            }
//            boolean justCount = args == null || args.type == EExpType.NoType;
//            
//            int count = 0;
//            ASTExpIterator arg = args.begin();
//            ASTExpIterator arg_end = args.end();
//            
//            for (Iterator<? extends ASTParameter> it = types.iterator(); it.hasNext();) {
//            	ASTParameter param = it.next();
//                if (!justCount && arg == arg_end) {
//                	throw new RuntimeException(args != null ? "Not enough arguments" : "arguments expected");
////                    return -1;
//                }
//                if (!justCount && param.type != arg.getExpression().type) {
//                	throw new RuntimeException("Incorrect argument type.");
////                	throw new RuntimeException(param_it->mLocation, "This is the expected type.");
////                    return -1;
//                }
//                count += param.tupleSize;
//                
//                arg.next();
//            }
//            
//            if (arg != arg_end) {
//            	throw new RuntimeException("Too many arguments.");
////                return -1;
//            }
//            
//            if (justCount && types != parent) {
//                if (parent == null) {
//                	throw new RuntimeException("Parameter reuse not allowed in this context.");
////                    return -1;
//                }
//                Iterator<? extends ASTExpression> pit = parent.iterator();
//                for (Iterator<? extends ASTExpression> it = types.iterator(); it.hasNext();) {
//                    ASTExpression param = it.next();
//					ASTExpression pparam = pit.next();
//					if (!pit.hasNext() || param.equals(pparam)) {
//                    	throw new RuntimeException("Parameter reuse only allowed when type signature is identical.");
////                        return -1;
//                    }
//                }
//            }
//            
            return 0;
        }

		@Override
		public EExpType getType() {
			return type;
		}

		public boolean isParameter() {
			return isParameter;
		}
		
		public void setIsParameter(boolean isParameter) {
			this.isParameter = isParameter;			
		}

		public boolean isLoopIndex() {
			return isLoopIndex;
		}

		@Override
		public boolean isNatural() {
			return isNatural;
		}

		public int getNameIndex() {
			return nameIndex;
		}

		public ASTDefine getDefinition() {
			return definition;
		}
		
		public void setDefinition(ASTDefine definition) {
			this.definition = definition;
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

		@Override
		public ELocality getLocality() {
			return locality;
		}

		public void setLocality(ELocality locality) {
			this.locality = locality;
		}

		public ASTExpression constCopy(String entropy) {
			// TODO da completare
			return null;
		}
    }
