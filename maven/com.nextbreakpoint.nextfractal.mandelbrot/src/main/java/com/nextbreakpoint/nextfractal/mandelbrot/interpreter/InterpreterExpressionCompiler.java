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

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
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
		return new InterpreterNumber(context, number.r(), number.i());
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
				return new InterpreterFuncMod(context, compileArguments(function.getArguments()));
			case "mod2":
				return new InterpreterFuncMod2(context, compileArguments(function.getArguments()));
			case "pha":
				return new InterpreterFuncPha(context, compileArguments(function.getArguments()));
			case "re":
				return new InterpreterFuncRe(context, compileArguments(function.getArguments()));
			case "im":
				return new InterpreterFuncIm(context, compileArguments(function.getArguments()));
				
			case "sin":
				return new InterpreterFuncSin(context, compileArguments(function.getArguments()));
			case "cos":
				return new InterpreterFuncCos(context, compileArguments(function.getArguments()));
			case "tan":
				return new InterpreterFuncTan(context, compileArguments(function.getArguments()));
			case "asin":
				return new InterpreterFuncAsin(context, compileArguments(function.getArguments()));
			case "acos":
				return new InterpreterFuncAcos(context, compileArguments(function.getArguments()));
			case "atan":
				return new InterpreterFuncAtan(context, compileArguments(function.getArguments()));
	
			case "abs":
				return new InterpreterFuncAbs(context, compileArguments(function.getArguments()));
			case "ceil":
				return new InterpreterFuncCeil(context, compileArguments(function.getArguments()));
			case "floor":
				return new InterpreterFuncFloor(context, compileArguments(function.getArguments()));
			case "log":
				return new InterpreterFuncLog(context, compileArguments(function.getArguments()));
				
			case "min":
				return new InterpreterFuncMin(context, compileArguments(function.getArguments()));
			case "max":
				return new InterpreterFuncMax(context, compileArguments(function.getArguments()));
			case "atan2":
				return new InterpreterFuncAtan2(context, compileArguments(function.getArguments()));
			case "hypot":
				return new InterpreterFuncHypot(context, compileArguments(function.getArguments()));
				
			case "pow":
				return new InterpreterFuncPow(context, compileArguments(function.getArguments()));
	
			case "sqrt":
				return new InterpreterFuncSqrt(context, compileArguments(function.getArguments()));
			case "exp":
				return new InterpreterFuncExp(context, compileArguments(function.getArguments()));
				
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
					return new InterpreterOperatorNeg(context, exp1.compile(this));
				
				case "+":
					return new InterpreterOperatorPos(context, exp1.compile(this));
				
				default:
					throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
			}
		} else {
			if (exp1.isReal() && exp2.isReal()) {
				switch (operator.getOp()) {
					case "+":
						return new InterpreterOperatorAdd(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new InterpreterOperatorSub(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new InterpreterOperatorMul(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new InterpreterOperatorDiv(context, exp1.compile(this), exp2.compile(this));
						
					case "^":
						return new InterpreterOperatorPow(context, exp1.compile(this), exp2.compile(this));
					
					case "<>":
						return new InterpreterOperatorNumber(context, exp1.compile(this), exp2.compile(this));
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
			} else if (exp2.isReal()) {
				switch (operator.getOp()) {
					case "+":
						return new InterpreterOperatorAdd(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new InterpreterOperatorSub(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new InterpreterOperatorMul(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new InterpreterOperatorDiv(context, exp1.compile(this), exp2.compile(this));
						
					case "^":
						return new InterpreterOperatorPow(context, exp1.compile(this), exp2.compile(this));
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
			} else {
				switch (operator.getOp()) {
					case "+":
						return new InterpreterOperatorAdd(context, exp1.compile(this), exp2.compile(this));
					
					case "-":
						return new InterpreterOperatorSub(context, exp1.compile(this), exp2.compile(this));
						
					case "*":
						return new InterpreterOperatorMul(context, exp1.compile(this), exp2.compile(this));
						
					case "/":
						return new InterpreterOperatorDiv(context, exp1.compile(this), exp2.compile(this));
						
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
		return new InterpreterVariable(variable.getName(), variable.isReal());
	}

	@Override
	public CompiledCondition compile(ASTConditionCompareOp compareOp) {
		ASTExpression exp1 = compareOp.getExp1();
		ASTExpression exp2 = compareOp.getExp2();
		if (exp1.isReal() && exp2.isReal()) {
			exp1.compile(this);
			switch (compareOp.getOp()) {
				case ">":
					return new InterpreterLogicOperatorGreather(context, compileOperands(exp1, exp2));
				
				case "<":
					return new InterpreterLogicOperatorLesser(context, compileOperands(exp1, exp2));
					
				case ">=":
					return new InterpreterLogicOperatorGreatherOrEquals(context, compileOperands(exp1, exp2));
					
				case "<=":
					return new InterpreterLogicOperatorLesserOrEquals(context, compileOperands(exp1, exp2));
					
				case "=":
					return new InterpreterLogicOperatorEquals(context, compileOperands(exp1, exp2));
					
				case "<>":
					return new InterpreterLogicOperatorNotEquals(context, compileOperands(exp1, exp2));
				
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
				return new InterpreterLogicOperatorAnd(context, compileLogicOperands(exp1, exp2));
			
			case "|":
				return new InterpreterLogicOperatorOr(context, compileLogicOperands(exp1, exp2));
				
			case "^":
				return new InterpreterLogicOperatorXor(context, compileLogicOperands(exp1, exp2));
				
			default:
				throw new ASTException("Unsupported operator: " + logicOp.getLocation().getText(), logicOp.getLocation());
		}
	}

	@Override
	public CompiledCondition compile(ASTConditionTrap trap) {
		return new InterpreterTrapCondition(trap.getName(), trap.getExp().compile(this));
	}

	@Override
	public CompiledCondition compile(ASTRuleLogicOp logicOp) {
		ASTRuleExpression exp1 = logicOp.getExp1();
		ASTRuleExpression exp2 = logicOp.getExp2();
		switch (logicOp.getOp()) {
			case "&":
				return new InterpreterLogicOperatorAnd(context, compileLogicOperands(exp1, exp2));
			
			case "|":
				return new InterpreterLogicOperatorOr(context, compileLogicOperands(exp1, exp2));
				
			case "^":
				return new InterpreterLogicOperatorXor(context, compileLogicOperands(exp1, exp2));
				
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
					return new InterpreterLogicOperatorGreather(context, compileOperands(exp1, exp2));
				
				case "<":
					return new InterpreterLogicOperatorLesser(context, compileOperands(exp1, exp2));
					
				case ">=":
					return new InterpreterLogicOperatorGreatherOrEquals(context, compileOperands(exp1, exp2));
					
				case "<=":
					return new InterpreterLogicOperatorLesserOrEquals(context, compileOperands(exp1, exp2));
					
				case "=":
					return new InterpreterLogicOperatorEquals(context, compileOperands(exp1, exp2));
					
				case "<>":
					return new InterpreterLogicOperatorNotEquals(context, compileOperands(exp1, exp2));
				
				default:
					throw new ASTException("Unsupported operator: " + compareOp.getLocation().getText(), compareOp.getLocation());
			}
		} else {
			throw new ASTException("Real expressions required: " + compareOp.getLocation().getText(), compareOp.getLocation());
		}
	}

	@Override
	public CompiledColor compile(ASTColorPalette palette) {
		if (palette.getExp().isReal()) {
			return new InterpreterPalette(palette.getName(), palette.getExp().compile(this));
		} else {
			throw new ASTException("Expression type not valid: " + palette.getLocation().getText(), palette.getLocation());
		}
	}

	@Override
	public CompiledColor compile(ASTColorComponent component) {
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
		return new InterpreterColorComponent(exp1, exp2, exp3, exp4);
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
		return new InterpreterConditionalStatement(statement.getConditionExp().compile(this), thenStatements, elseStatements);
	}

	@Override
	public CompiledStatement compile(ASTAssignStatement statement) {
		return new InterpreterAssignStatement(statement.getName(), statement.getExp().compile(this));
	}

	@Override
	public CompiledStatement compile(ASTStopStatement statement) {
		return new InterpreterStopStatement();
	}

	@Override
	public CompiledCondition compile(ASTConditionJulia condition) {
		return new IntrepreterJuliaCondition();
	}

	@Override
	public CompiledCondition compile(ASTConditionParen condition) {
		return condition.getExp().compile(this);
	}

	@Override
	public CompiledCondition compile(ASTConditionNeg condition) {
		return new InterpreterInvertedCondition(condition.getExp().compile(this)) ;
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
