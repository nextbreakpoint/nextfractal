/*
 * NextFractal 1.0.3
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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.HashMap;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTAssignStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorComponent;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionCompareOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionLogicOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionNeg;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionParen;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionJulia;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionalStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpressionCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFunction;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOperator;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTParen;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleCompareOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleLogicOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatementList;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStopStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTVariable;

public class ExpressionCompiler implements ASTExpressionCompiler {
	private final Map<String, CompilerVariable> variables;
	private final StringBuilder builder;
	
	public ExpressionCompiler(Map<String, CompilerVariable> variables, StringBuilder builder) {
		this.variables = variables;
		this.builder = builder;
	}

	@Override
	public void compile(ASTNumber number) {
		if (number.isReal()) {
			builder.append(number.r());
		} else {
			builder.append("number(");
			builder.append(number.r());
			builder.append(",");
			builder.append(number.i());
			builder.append(")");
		}
	}

	@Override
	public void compile(ASTFunction function) {
		builder.append("func");
		builder.append(function.getName().toUpperCase().substring(0, 1));
		builder.append(function.getName().substring(1));
		builder.append("(");
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
		ASTExpression[] arguments = function.getArguments();
		for (int i = 0; i < arguments.length; i++) {
			arguments[i].compile(this);
			if (i < arguments.length - 1) {
				builder.append(",");
			}
		}
		builder.append(")");
	}

	@Override
	public void compile(ASTOperator operator) {
		ASTExpression exp1 = operator.getExp1();
		ASTExpression exp2 = operator.getExp2();
		if (exp2 == null) {
			switch (operator.getOp()) {
				case "-":
					if (exp1.isReal()) {
						builder.append("-");
						exp1.compile(this);
					} else {
						builder.append("opNeg");
						builder.append("(");
						exp1.compile(this);
						builder.append(")");
					}
					break;
				
				case "+":
					if (exp1.isReal()) {
						exp1.compile(this);
					} else {
						builder.append("opPos");
						builder.append("(");
						exp1.compile(this);
						builder.append(")");
					}
					break;
				
				default:
					throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
			}
		} else {
			if (exp1.isReal() && exp2.isReal()) {
				if (operator.getOp().equals("^")) {
					builder.append("opPow");
				}
				if (operator.getOp().equals("<>")) {
					builder.append("number");
				}
				builder.append("(");
				exp1.compile(this);
				switch (operator.getOp()) {
					case "+":
						builder.append("+");
						break;
					
					case "-":
						builder.append("-");
						break;
						
					case "*":
						builder.append("*");
						break;
						
					case "/":
						builder.append("/");
						break;
						
					case "^":
						builder.append(",");
						break;
					
					case "<>":
						builder.append(",");
						break;
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
				exp2.compile(this);
				builder.append(")");
			} else if (exp2.isReal()) {
				switch (operator.getOp()) {
					case "+":
						builder.append("opAdd");
						break;
					
					case "-":
						builder.append("opSub");
						break;
						
					case "*":
						builder.append("opMul");
						break;
						
					case "/":
						builder.append("opDiv");
						break;
						
					case "^":
						builder.append("opPow");
						break;
					
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
				builder.append("(");
				exp1.compile(this);
				builder.append(",");
				exp2.compile(this);
				builder.append(")");
			} else {
				switch (operator.getOp()) {
					case "+":
						builder.append("opAdd");
						break;
					
					case "-":
						builder.append("opSub");
						break;
						
					case "*":
						builder.append("opMul");
						break;
						
					case "/":
						builder.append("opDiv");
						break;
						
					default:
						throw new ASTException("Unsupported operator: " + operator.getLocation().getText(), operator.getLocation());
				}
				builder.append("(");
				exp1.compile(this);
				builder.append(",");
				exp2.compile(this);
				builder.append(")");
			}
		}
	}

	@Override
	public void compile(ASTParen paren) {
		builder.append("(");
		paren.getExp().compile(this);
		builder.append(")");
	}

	@Override
	public void compile(ASTVariable variable) {
		builder.append(variable.getName());
		if (variable.isReal()) {
			builder.append(".r()");
		}
	}

	@Override
	public void compile(ASTConditionCompareOp compareOp) {
		ASTExpression exp1 = compareOp.getExp1();
		ASTExpression exp2 = compareOp.getExp2();
		if (exp1.isReal() && exp2.isReal()) {
			builder.append("(");
			exp1.compile(this);
			switch (compareOp.getOp()) {
				case ">":
					builder.append(">");
					break;
				
				case "<":
					builder.append("<");
					break;
					
				case ">=":
					builder.append(">=");
					break;
					
				case "<=":
					builder.append("<=");
					break;
					
				case "=":
					builder.append("==");
					break;
					
				case "<>":
					builder.append("!=");
					break;
				
				default:
					throw new ASTException("Unsupported operator: " + compareOp.getLocation().getText(), compareOp.getLocation());
			}
			exp2.compile(this);
			builder.append(")");
		} else {
			throw new ASTException("Real expressions required: " + compareOp.getLocation().getText(), compareOp.getLocation());
		}
	}

	@Override
	public void compile(ASTConditionLogicOp logicOp) {
		ASTConditionExpression exp1 = logicOp.getExp1();
		ASTConditionExpression exp2 = logicOp.getExp2();
		builder.append("(");
		exp1.compile(this);
		switch (logicOp.getOp()) {
			case "&":
				builder.append("&&");
				break;
			
			case "|":
				builder.append("||");
				break;
				
			case "^":
				builder.append("^^");
				break;
				
			default:
				throw new ASTException("Unsupported operator: " + logicOp.getLocation().getText(), logicOp.getLocation());
		}
		exp2.compile(this);
		builder.append(")");
	}

	@Override
	public void compile(ASTConditionTrap trap) {
		if (!trap.isContains()) {
			builder.append("!");
		}
		builder.append("trap");
		builder.append(trap.getName().toUpperCase().substring(0, 1));
		builder.append(trap.getName().substring(1));
		builder.append(".contains(");
		trap.getExp().compile(this);
		builder.append(")");
	}

	@Override
	public void compile(ASTRuleLogicOp logicOp) {
		ASTRuleExpression exp1 = logicOp.getExp1();
		ASTRuleExpression exp2 = logicOp.getExp2();
		builder.append("(");
		exp1.compile(this);
		switch (logicOp.getOp()) {
			case "&":
				builder.append("&&");
				break;
			
			case "|":
				builder.append("||");
				break;
				
			case "^":
				builder.append("^^");
				break;
				
			default:
				throw new ASTException("Unsupported operator: " + logicOp.getLocation().getText(), logicOp.getLocation());
		}
		exp2.compile(this);
		builder.append(")");
	}

	@Override
	public void compile(ASTRuleCompareOp compareOp) {
		ASTExpression exp1 = compareOp.getExp1();
		ASTExpression exp2 = compareOp.getExp2();
		if (exp1.isReal() && exp2.isReal()) {
			builder.append("(");
			exp1.compile(this);
			switch (compareOp.getOp()) {
				case ">":
					builder.append(">");
					break;
				
				case "<":
					builder.append("<");
					break;
					
				case ">=":
					builder.append(">=");
					break;
					
				case "<=":
					builder.append("<=");
					break;
					
				case "=":
					builder.append("==");
					break;
					
				case "<>":
					builder.append("!=");
					break;
				
				default:
					throw new ASTException("Unsupported operator: " + compareOp.getLocation().getText(), compareOp.getLocation());
			}
			exp2.compile(this);
			builder.append(")");
		} else {
			throw new ASTException("Real expressions required: " + compareOp.getLocation().getText(), compareOp.getLocation());
		}
	}

	@Override
	public void compile(ASTColorPalette palette) {
		builder.append("palette");
		builder.append(palette.getName().toUpperCase().substring(0, 1));
		builder.append(palette.getName().substring(1));
		builder.append(".get(");
		if (palette.getExp().isReal()) {
			palette.getExp().compile(this);
		} else {
			throw new ASTException("Expression type not valid: " + palette.getLocation().getText(), palette.getLocation());
		}
		builder.append(")");
	}

	@Override
	public void compile(ASTColorComponent component) {
		builder.append("color(");
		component.getExp1().compile(this);
		if (component.getExp2() != null) {
			builder.append(",");
			component.getExp2().compile(this);
		}
		if (component.getExp3() != null) {
			builder.append(",");
			component.getExp3().compile(this);
		}
		if (component.getExp4() != null) {
			builder.append(",");
			component.getExp4().compile(this);
		}
		builder.append(")");
	}

	@Override
	public void compile(ASTConditionalStatement statement) {
		builder.append("if (");
		statement.getConditionExp().compile(this);
		builder.append(") {\n");
		if (statement.getStatementList() != null) {
			Map<String, CompilerVariable> vars = new HashMap<String, CompilerVariable>(variables);
			for (ASTStatement innerStatement : statement.getStatementList().getStatements()) {
				innerStatement.compile(new ExpressionCompiler(vars, builder));
			}
		}
		builder.append("}\n");
	}

	@Override
	public void compile(ASTAssignStatement statement) {
		CompilerVariable var = variables.get(statement.getName());
		if (var != null) {
			if ((var.isReal() && statement.getExp().isReal()) || (!var.isReal() && !statement.getExp().isReal())) {
				builder.append(statement.getName());
				builder.append(".set(");
				statement.getExp().compile(this);
				builder.append(");\n");
			} else if (!var.isReal() && statement.getExp().isReal()) {
				builder.append(statement.getName());
				builder.append(".set(");
				statement.getExp().compile(this);
				builder.append(");\n");
			} else if (var.isReal() && !statement.getExp().isReal()) {
				throw new ASTException("Cannot assign expression: " + statement.getLocation().getText(), statement.getLocation());
			}
		} else {
			var = new CompilerVariable(statement.getName(), statement.getExp().isReal(), false);
			variables.put(statement.getName(), var);
			builder.append("final Variable ");
			builder.append(statement.getName());
			builder.append(" = variable(");
			statement.getExp().compile(this);
			builder.append(");\n");
		}
	}

	@Override
	public void compile(ASTStopStatement statement) {
		builder.append("n.set(i);\nbreak;\n");
	}

	@Override
	public void compile(ASTConditionJulia condition) {
		builder.append("isJulia()");
	}

	@Override
	public void compile(ASTConditionParen condition) {
		builder.append("(");
		condition.getExp().compile(this);
		builder.append(")");
	}

	@Override
	public void compile(ASTConditionNeg condition) {
		builder.append("!");
		condition.getExp().compile(this);
	}

	@Override
	public void compile(ASTStatementList statementList) {
		// TODO Auto-generated method stub
		
	}
}
