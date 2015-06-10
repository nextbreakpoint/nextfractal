/*
 * NextFractal 1.0.5
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
package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledAssignStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledColorComponentExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledColorExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledConditionalStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncAbs;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncAcos;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncAsin;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncAtan;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncAtan2;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncCeil;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncCos;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncCosZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncExp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncExpZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncFloor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncHypot;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncImZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncLog;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncMax;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncMin;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncMod;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncMod2;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncModZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncModZ2;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncPhaZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncPow;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncPowZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncReZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncSin;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncSinZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncSqrt;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncSqrtZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncTan;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledFuncTanZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledInvertedCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledJuliaCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorAnd;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorEquals;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorGreather;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorGreatherOrEquals;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorLesser;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorLesserOrEquals;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorNotEquals;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorOr;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledLogicOperatorXor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledNumberZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorAdd;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorAddZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorDiv;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorDivZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorMul;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorMulZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorNeg;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorNegZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorPos;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorPosZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorPow;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorPowZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorSub;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledOperatorSubZ;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPaletteExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStopStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapInvertedCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpArcTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpArcToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpCurveTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpCurveToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpLineTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpLineToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpMoveTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpMoveToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpQuadTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpQuadToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledVariableZ;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTAssignStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorComponent;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionCompareOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionJulia;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionLogicOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionNeg;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionParen;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionalStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpressionCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFunction;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOperator;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTParen;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleCompareOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleLogicOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStopStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTVariable;

public class InterpreterExpressionCompiler implements ASTExpressionCompiler {
	private final ExpressionContext context;
	
	public InterpreterExpressionCompiler(ExpressionContext context) {
		this.context = context;
	}

	@Override
	public CompiledExpression compile(ASTNumber number) {
		if (number.isReal()) {
			return new CompiledNumber(context, number.r(), number.i());
		} else {
			return new CompiledNumberZ(context, number.r(), number.i());
		}
	}

	@Override
	public CompiledExpression compile(ASTFunction function) {
		switch (function.getName()) {
			case "mod":
			case "mod2":
			case "pha":
			case "re":
			case "im":
				if (function.getArguments().length != 1) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				break;
				
			case "sin":
			case "cos":
			case "tan":
			case "asin":
			case "acos":
			case "atan":
				if (function.getArguments().length != 1) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}	
				break;

			case "abs":
			case "ceil":
			case "floor":
			case "log":
				if (function.getArguments().length != 1) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				if (!function.getArguments()[0].isReal()) {
					throw new ASTException("Invalid type of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				break;
				
			case "min":
			case "max":
			case "atan2":
			case "hypot":
				if (function.getArguments().length != 2) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				if (!function.getArguments()[0].isReal()) {
					throw new ASTException("Invalid type of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				if (!function.getArguments()[1].isReal()) {
					throw new ASTException("Invalid type of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				break;
				
			case "pow":
				if (function.getArguments().length != 2) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				if (!function.getArguments()[1].isReal()) {
					throw new ASTException("Invalid type of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				break;

			case "sqrt":
			case "exp":
				if (function.getArguments().length != 1) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				break;
				
			default:
				throw new ASTException("Unsupported function: " + function.getLocation().getText(), function.getLocation());
		}
		switch (function.getName()) {
			case "mod":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncMod(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncModZ(context, compileArguments(function.getArguments()));
				}
			case "mod2":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncMod2(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncModZ2(context, compileArguments(function.getArguments()));
				}
			case "pha":
				return new CompiledFuncPhaZ(context, compileArguments(function.getArguments()));
			case "re":
				return new CompiledFuncReZ(context, compileArguments(function.getArguments()));
			case "im":
				return new CompiledFuncImZ(context, compileArguments(function.getArguments()));
				
			case "sin":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncSin(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncSinZ(context, compileArguments(function.getArguments()));
				}
			case "cos":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncCos(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncCosZ(context, compileArguments(function.getArguments()));
				}
			case "tan":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncTan(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncTanZ(context, compileArguments(function.getArguments()));
				}
			case "asin":
				return new CompiledFuncAsin(context, compileArguments(function.getArguments()));
			case "acos":
				return new CompiledFuncAcos(context, compileArguments(function.getArguments()));
			case "atan":
				return new CompiledFuncAtan(context, compileArguments(function.getArguments()));
	
			case "abs":
				return new CompiledFuncAbs(context, compileArguments(function.getArguments()));
			case "ceil":
				return new CompiledFuncCeil(context, compileArguments(function.getArguments()));
			case "floor":
				return new CompiledFuncFloor(context, compileArguments(function.getArguments()));
			case "log":
				return new CompiledFuncLog(context, compileArguments(function.getArguments()));
				
			case "min":
				return new CompiledFuncMin(context, compileArguments(function.getArguments()));
			case "max":
				return new CompiledFuncMax(context, compileArguments(function.getArguments()));
			case "atan2":
				return new CompiledFuncAtan2(context, compileArguments(function.getArguments()));
			case "hypot":
				return new CompiledFuncHypot(context, compileArguments(function.getArguments()));
				
			case "pow":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncPow(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncPowZ(context, compileArguments(function.getArguments()));
				}
	
			case "sqrt":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncSqrt(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncSqrtZ(context, compileArguments(function.getArguments()));
				}
			case "exp":
				if (function.getArguments()[0].isReal()) {
					return new CompiledFuncExp(context, compileArguments(function.getArguments()));
				} else {
					return new CompiledFuncExpZ(context, compileArguments(function.getArguments()));
				}
				
			default:
		}
		throw new ASTException("Unsupported function: " + function.getLocation().getText(), function.getLocation());
	}

	@Override
	public CompiledExpression compile(ASTOperator operator) {
		ASTExpression exp1 = operator.getExp1();
		ASTExpression exp2 = operator.getExp2();
		if (exp2 == null) {
			switch (operator.getOp()) {
				case "-":
					if (exp1.isReal()) {
						return new CompiledOperatorNeg(context, exp1.compile(this));
					} else {
						return new CompiledOperatorNegZ(context, exp1.compile(this));
					}
				
				case "+":
					if (exp1.isReal()) {
						return new CompiledOperatorPos(context, exp1.compile(this));
					} else {
						return new CompiledOperatorPosZ(context, exp1.compile(this));
					}
				
				default:
					throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
			}
		} else {
			if (exp1.isReal() && exp2.isReal()) {
				switch (operator.getOp()) {
					case "+":
						return new CompiledOperatorAdd(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new CompiledOperatorSub(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new CompiledOperatorMul(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new CompiledOperatorDiv(context, exp1.compile(this), exp2.compile(this));
						
					case "^":
						return new CompiledOperatorPow(context, exp1.compile(this), exp2.compile(this));
					
					case "<>":
						return new CompiledOperatorNumber(context, exp1.compile(this), exp2.compile(this));
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
			} else if (exp2.isReal()) {
				switch (operator.getOp()) {
					case "+":
						return new CompiledOperatorAddZ(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new CompiledOperatorSubZ(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new CompiledOperatorMulZ(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new CompiledOperatorDivZ(context, exp1.compile(this), exp2.compile(this));
						
					case "^":
						return new CompiledOperatorPowZ(context, exp1.compile(this), exp2.compile(this));
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
			} else {
				switch (operator.getOp()) {
					case "+":
						return new CompiledOperatorAddZ(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new CompiledOperatorSubZ(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new CompiledOperatorMulZ(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new CompiledOperatorDivZ(context, exp1.compile(this), exp2.compile(this));
						
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
			}
		}
	}

	@Override
	public CompiledExpression compile(ASTParen paren) {
		return paren.getExp().compile(this);
	}

	@Override
	public CompiledExpression compile(ASTVariable variable) {
		if (variable.isReal()) {
			return new CompiledVariable(context, variable.getName(), variable.isReal());
		} else {
			return new CompiledVariableZ(context, variable.getName(), variable.isReal());
		}
	}

	@Override
	public CompiledCondition compile(ASTConditionCompareOp compareOp) {
		ASTExpression exp1 = compareOp.getExp1();
		ASTExpression exp2 = compareOp.getExp2();
		if (exp1.isReal() && exp2.isReal()) {
			exp1.compile(this);
			switch (compareOp.getOp()) {
				case ">":
					return new CompiledLogicOperatorGreather(context, compileOperands(exp1, exp2));
				
				case "<":
					return new CompiledLogicOperatorLesser(context, compileOperands(exp1, exp2));
					
				case ">=":
					return new CompiledLogicOperatorGreatherOrEquals(context, compileOperands(exp1, exp2));
					
				case "<=":
					return new CompiledLogicOperatorLesserOrEquals(context, compileOperands(exp1, exp2));
					
				case "=":
					return new CompiledLogicOperatorEquals(context, compileOperands(exp1, exp2));
					
				case "<>":
					return new CompiledLogicOperatorNotEquals(context, compileOperands(exp1, exp2));
				
				default:
					throw new ASTException("Unsupported operator: " + compareOp.getLocation().getText(), compareOp.getLocation());
			}
		} else {
			throw new ASTException("Real expressions required: " + compareOp.getLocation().getText(), compareOp.getLocation());
		}
	}

	@Override
	public CompiledCondition compile(ASTConditionLogicOp logicOp) {
		ASTConditionExpression exp1 = logicOp.getExp1();
		ASTConditionExpression exp2 = logicOp.getExp2();
		switch (logicOp.getOp()) {
			case "&":
				return new CompiledLogicOperatorAnd(context, compileLogicOperands(exp1, exp2));
			
			case "|":
				return new CompiledLogicOperatorOr(context, compileLogicOperands(exp1, exp2));
				
			case "^":
				return new CompiledLogicOperatorXor(context, compileLogicOperands(exp1, exp2));
				
			default:
				throw new ASTException("Unsupported operator: " + logicOp.getLocation().getText(), logicOp.getLocation());
		}
	}

	@Override
	public CompiledCondition compile(ASTConditionTrap trap) {
		if (trap.isContains()) {
			return new CompiledTrapCondition(trap.getName(), trap.getExp().compile(this));
		} else {
			return new CompiledTrapInvertedCondition(trap.getName(), trap.getExp().compile(this));
		}
	}

	@Override
	public CompiledCondition compile(ASTRuleLogicOp logicOp) {
		ASTRuleExpression exp1 = logicOp.getExp1();
		ASTRuleExpression exp2 = logicOp.getExp2();
		switch (logicOp.getOp()) {
			case "&":
				return new CompiledLogicOperatorAnd(context, compileLogicOperands(exp1, exp2));
			
			case "|":
				return new CompiledLogicOperatorOr(context, compileLogicOperands(exp1, exp2));
				
			case "^":
				return new CompiledLogicOperatorXor(context, compileLogicOperands(exp1, exp2));
				
			default:
				throw new ASTException("Unsupported operator: " + logicOp.getLocation().getText(), logicOp.getLocation());
		}
	}

	@Override
	public CompiledCondition compile(ASTRuleCompareOp compareOp) {
		ASTExpression exp1 = compareOp.getExp1();
		ASTExpression exp2 = compareOp.getExp2();
		if (exp1.isReal() && exp2.isReal()) {
			switch (compareOp.getOp()) {
				case ">":
					return new CompiledLogicOperatorGreather(context, compileOperands(exp1, exp2));
				
				case "<":
					return new CompiledLogicOperatorLesser(context, compileOperands(exp1, exp2));
					
				case ">=":
					return new CompiledLogicOperatorGreatherOrEquals(context, compileOperands(exp1, exp2));
					
				case "<=":
					return new CompiledLogicOperatorLesserOrEquals(context, compileOperands(exp1, exp2));
					
				case "=":
					return new CompiledLogicOperatorEquals(context, compileOperands(exp1, exp2));
					
				case "<>":
					return new CompiledLogicOperatorNotEquals(context, compileOperands(exp1, exp2));
				
				default:
					throw new ASTException("Unsupported operator: " + compareOp.getLocation().getText(), compareOp.getLocation());
			}
		} else {
			throw new ASTException("Real expressions required: " + compareOp.getLocation().getText(), compareOp.getLocation());
		}
	}

	@Override
	public CompiledColorExpression compile(ASTColorPalette palette) {
		if (palette.getExp().isReal()) {
			return new CompiledPaletteExpression(palette.getName(), palette.getExp().compile(this));
		} else {
			throw new ASTException("Expression type not valid: " + palette.getLocation().getText(), palette.getLocation());
		}
	}

	@Override
	public CompiledColorExpression compile(ASTColorComponent component) {
		CompiledExpression exp1 = component.getExp1().compile(this);
		CompiledExpression exp2 = null;
		CompiledExpression exp3 = null;
		CompiledExpression exp4 = null;
		if (component.getExp2() != null) {
			exp2 = component.getExp2().compile(this);
		}
		if (component.getExp3() != null) {
			exp3 = component.getExp3().compile(this);
		}
		if (component.getExp4() != null) {
			exp4 = component.getExp4().compile(this);
		}
		return new CompiledColorComponentExpression(exp1, exp2, exp3, exp4);
	}

	@Override
	public CompiledStatement compile(ASTConditionalStatement statement) {
		List<CompiledStatement> thenStatements = new ArrayList<>();
		List<CompiledStatement> elseStatements = new ArrayList<>();
		for (ASTStatement innerStatement : statement.getThenStatementList().getStatements()) {
			thenStatements.add(innerStatement.compile(this));
		}
		if (statement.getElseStatementList() != null) {
			for (ASTStatement innerStatement : statement.getElseStatementList().getStatements()) {
				elseStatements.add(innerStatement.compile(this));
			}
		}
		return new CompiledConditionalStatement(statement.getConditionExp().compile(this), thenStatements, elseStatements);
	}

	@Override
	public CompiledStatement compile(ASTAssignStatement statement) {
		return new CompiledAssignStatement(statement.getName(), statement.getExp().compile(this));
	}

	@Override
	public CompiledStatement compile(ASTStopStatement statement) {
		return new CompiledStopStatement();
	}

	@Override
	public CompiledCondition compile(ASTConditionJulia condition) {
		return new CompiledJuliaCondition();
	}

	@Override
	public CompiledCondition compile(ASTConditionParen condition) {
		return condition.getExp().compile(this);
	}

	@Override
	public CompiledCondition compile(ASTConditionNeg condition) {
		return new CompiledInvertedCondition(condition.getExp().compile(this)) ;
	}

	@Override
	public CompiledTrap compile(ASTOrbitTrap orbitTrap) {
		CompiledTrap trap = new CompiledTrap();
		trap.setName(orbitTrap.getName());
		trap.setCenter(new Number(orbitTrap.getCenter().r(), orbitTrap.getCenter().i()));
		List<CompiledTrapOp> operators = new ArrayList<>();
		for (ASTOrbitTrapOp astTrapOp : orbitTrap.getOperators()) {
			operators.add(astTrapOp.compile(this));
		}
		trap.setOperators(operators);
		return trap;
	}

	@Override
	public CompiledTrapOp compile(ASTOrbitTrapOp orbitTrapOp) {
		Number c1 = null;
		Number c2 = null;
		Number c3 = null;
		if (orbitTrapOp.getC1() != null) {
			c1 = new Number(orbitTrapOp.getC1().r(), orbitTrapOp.getC1().i());
		}
		if (orbitTrapOp.getC2() != null) {
			c2 = new Number(orbitTrapOp.getC2().r(), orbitTrapOp.getC2().i());
		}
		if (orbitTrapOp.getC3() != null) {
			c3 = new Number(orbitTrapOp.getC3().r(), orbitTrapOp.getC3().i());
		}
		switch (orbitTrapOp.getOp()) {
			case "MOVETO":
				return new CompiledTrapOpMoveTo(c1);
	
			case "MOVETOREL":
				return new CompiledTrapOpMoveToRel(c1);
	
			case "LINETO":
				return new CompiledTrapOpLineTo(c1);
	
			case "LINETOREL":
				return new CompiledTrapOpLineToRel(c1);
	
			case "ARCTO":
				return new CompiledTrapOpArcTo(c1, c2);
	
			case "ARCTOREL":
				return new CompiledTrapOpArcToRel(c1, c2);
	
			case "QUADTO":
				return new CompiledTrapOpQuadTo(c1, c2);
	
			case "QUADTOREL":
				return new CompiledTrapOpQuadToRel(c1, c2);
	
			case "CURVETO":
				return new CompiledTrapOpCurveTo(c1, c2, c3);
	
			case "CURVETOREL":
				return new CompiledTrapOpCurveToRel(c1, c2, c3);
	
			default:
				throw new ASTException("Unsupported operator: " + orbitTrapOp.getLocation().getText(), orbitTrapOp.getLocation());
		}		
	}

	@Override
	public CompiledPalette compile(ASTPalette astPalette) {
		CompiledPalette palette = new CompiledPalette();
		palette.setName(astPalette.getName());
		List<CompiledPaletteElement> elements = new ArrayList<>();
		for (ASTPaletteElement astElement : astPalette.getElements()) {
			elements.add(astElement.compile(this));
		}
		palette.setElements(elements);
		return palette;
	}

	@Override
	public CompiledPaletteElement compile(ASTPaletteElement astElement) {
		return new CompiledPaletteElement(astElement.getBeginColor().getComponents(), astElement.getEndColor().getComponents(), astElement.getSteps(), astElement.getExp() != null ? astElement.getExp().compile(this) : null);
	}

	private CompiledExpression[] compileArguments(ASTExpression[] arguments) {
		CompiledExpression[] args = new CompiledExpression[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			args[i] = arguments[i].compile(this);
		}
		return args;
	}

	private CompiledExpression[] compileOperands(ASTExpression exp1, ASTExpression exp2) {
		CompiledExpression[] operands = new CompiledExpression[2];
		operands[0] = exp1.compile(this);
		operands[1] = exp2.compile(this);
		return operands;
	}

	private CompiledCondition[] compileLogicOperands(ASTConditionExpression exp1, ASTConditionExpression exp2) {
		CompiledCondition[] operands = new CompiledCondition[2];
		operands[0] = exp1.compile(this);
		operands[1] = exp2.compile(this);
		return operands;
	}

	private CompiledCondition[] compileLogicOperands(ASTRuleExpression exp1, ASTRuleExpression exp2) {
		CompiledCondition[] operands = new CompiledCondition[2];
		operands[0] = exp1.compile(this);
		operands[1] = exp2.compile(this);
		return operands;
	}
}
