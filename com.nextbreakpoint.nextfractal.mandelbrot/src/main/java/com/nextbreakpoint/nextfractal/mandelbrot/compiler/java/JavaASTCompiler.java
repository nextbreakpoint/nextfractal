/*
 * NextFractal 1.3.0
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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledColorExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledConditionalExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpArcTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpArcToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpClose;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpCurveTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpCurveToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpLineTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpLineToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpMoveTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpMoveToRel;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpQuadTo;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrapOpQuadToRel;
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
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionalExpression;
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

public class JavaASTCompiler implements ASTExpressionCompiler {
	private final Map<String, CompilerVariable> variables;
	private final ExpressionContext context;
	private final StringBuilder builder;
	
	public JavaASTCompiler(ExpressionContext context, Map<String, CompilerVariable> variables, StringBuilder builder) {
		this.variables = variables;
		this.context = context;
		this.builder = builder;
	}

	@Override
	public CompiledExpression compile(ASTNumber number) {
		if (number.isReal()) {
			builder.append(number.r());
		} else {
			builder.append("getNumber(");
			builder.append(context.newNumberIndex());
			builder.append(").set(");
			builder.append(number.r());
			builder.append(",");
			builder.append(number.i());
			builder.append(")");
		}
		return null;
	}

	@Override
	public CompiledExpression compile(ASTFunction function) {
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
				if (!function.getArguments()[0].isReal()) {
					builder.append("getNumber(");
					builder.append(context.newNumberIndex());
					builder.append("),");
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
				if (!function.getArguments()[0].isReal()) {
					builder.append("getNumber(");
					builder.append(context.newNumberIndex());
					builder.append("),");
				}
				break;

			case "sqrt":
			case "exp":
				if (function.getArguments().length != 1) {
					throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
				}				
				if (!function.getArguments()[0].isReal()) {
					builder.append("getNumber(");
					builder.append(context.newNumberIndex());
					builder.append("),");
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
		return null;
	}

	@Override
	public CompiledExpression compile(ASTOperator operator) {
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
						builder.append("getNumber(");
						builder.append(context.newNumberIndex());
						builder.append("),");
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
						builder.append("getNumber(");
						builder.append(context.newNumberIndex());
						builder.append("),");
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
					builder.append("getNumber(");
					builder.append(context.newNumberIndex());
					builder.append(").set");
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
				builder.append("getNumber(");
				builder.append(context.newNumberIndex());
				builder.append("),");
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
				builder.append("getNumber(");
				builder.append(context.newNumberIndex());
				builder.append("),");
				exp1.compile(this);
				builder.append(",");
				exp2.compile(this);
				builder.append(")");
			}
		}
		return null;
	}

	@Override
	public CompiledExpression compile(ASTParen paren) {
		builder.append("(");
		paren.getExp().compile(this);
		builder.append(")");
		return null;
	}

	@Override
	public CompiledExpression compile(ASTVariable variable) {
		builder.append(variable.getName());
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionCompareOp compareOp) {
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
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionLogicOp logicOp) {
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
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionTrap trap) {
		if (!trap.isContains()) {
			builder.append("!");
		}
		builder.append("trap");
		builder.append(trap.getName().toUpperCase().substring(0, 1));
		builder.append(trap.getName().substring(1));
		builder.append(".contains(");
		trap.getExp().compile(this);
		builder.append(")");
		return null;
	}

	@Override
	public CompiledCondition compile(ASTRuleLogicOp logicOp) {
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
		return null;
	}

	@Override
	public CompiledCondition compile(ASTRuleCompareOp compareOp) {
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
		return null;
	}

	@Override
	public CompiledColorExpression compile(ASTColorPalette palette) {
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
		return null;
	}

	@Override
	public CompiledColorExpression compile(ASTColorComponent component) {
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
		return null;
	}

	@Override
	public CompiledStatement compile(ASTConditionalStatement statement) {
		builder.append("if (");
		statement.getConditionExp().compile(this);
		builder.append(") {\n");
		Map<String, CompilerVariable> vars = new HashMap<String, CompilerVariable>(variables);
		for (ASTStatement innerStatement : statement.getThenStatementList().getStatements()) {
			innerStatement.compile(new JavaASTCompiler(context, vars, builder));
		}
		if (statement.getElseStatementList() != null) {
			builder.append("} else {\n");
			for (ASTStatement innerStatement : statement.getElseStatementList().getStatements()) {
				innerStatement.compile(new JavaASTCompiler(context, vars, builder));
			}
		}
		builder.append("}\n");
		return null;
	}

	@Override
	public CompiledStatement compile(ASTAssignStatement statement) {
		CompilerVariable var = variables.get(statement.getName());
		if (var != null) {
			if (var.isReal() && statement.getExp().isReal()) {
				builder.append(statement.getName());
				builder.append(" = real(");
				statement.getExp().compile(this);
				builder.append(");\n");
			} else if (!var.isReal() && !statement.getExp().isReal()) {
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
			if (var.isReal()) {
				builder.append("double ");
				builder.append(statement.getName());
				builder.append(" = real(");
				statement.getExp().compile(this);
				builder.append(");\n");
			} else {
				builder.append("final MutableNumber ");
				builder.append(statement.getName());
				builder.append(" = getNumber(");
				builder.append(context.newNumberIndex());
				builder.append(").set(");
				statement.getExp().compile(this);
				builder.append(");\n");
			}
		}
		return null;
	}

	@Override
	public CompiledStatement compile(ASTStopStatement statement) {
		builder.append("n = i;\nbreak;\n");
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionJulia condition) {
		builder.append("isJulia()");
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionParen condition) {
		builder.append("(");
		condition.getExp().compile(this);
		builder.append(")");
		return null;
	}

	@Override
	public CompiledCondition compile(ASTConditionNeg condition) {
		builder.append("!");
		condition.getExp().compile(this);
		return null;
	}

	@Override
	public CompiledTrap compile(ASTOrbitTrap orbitTrap) {
		CompiledTrap trap = new CompiledTrap(orbitTrap.getLocation());
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
				return new CompiledTrapOpMoveTo(c1, orbitTrapOp.getLocation());
	
			case "MOVETOREL":
				return new CompiledTrapOpMoveToRel(c1, orbitTrapOp.getLocation());
	
			case "LINETO":
				return new CompiledTrapOpLineTo(c1, orbitTrapOp.getLocation());
	
			case "LINETOREL":
				return new CompiledTrapOpLineToRel(c1, orbitTrapOp.getLocation());
	
			case "ARCTO":
				return new CompiledTrapOpArcTo(c1, c2, orbitTrapOp.getLocation());
	
			case "ARCTOREL":
				return new CompiledTrapOpArcToRel(c1, c2, orbitTrapOp.getLocation());
	
			case "QUADTO":
				return new CompiledTrapOpQuadTo(c1, c2, orbitTrapOp.getLocation());
	
			case "QUADTOREL":
				return new CompiledTrapOpQuadToRel(c1, c2, orbitTrapOp.getLocation());
	
			case "CURVETO":
				return new CompiledTrapOpCurveTo(c1, c2, c3, orbitTrapOp.getLocation());
	
			case "CURVETOREL":
				return new CompiledTrapOpCurveToRel(c1, c2, c3, orbitTrapOp.getLocation());
	
			case "CLOSE":
				return new CompiledTrapOpClose(orbitTrapOp.getLocation());
	
			default:
				throw new ASTException("Unsupported operator: " + orbitTrapOp.getLocation().getText(), orbitTrapOp.getLocation());
		}		
	}

	@Override
	public CompiledPalette compile(ASTPalette astPalette) {
		CompiledPalette palette = new CompiledPalette(astPalette.getLocation());
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
		return new CompiledPaletteElement(astElement.getBeginColor().getComponents(), astElement.getEndColor().getComponents(), astElement.getSteps(), astElement.getExp() != null ? astElement.getExp().compile(this) : null, astElement.getLocation());
	}

	@Override
	public CompiledExpression compile(ASTConditionalExpression astConditionalExpression) {
		return new CompiledConditionalExpression(context, astConditionalExpression.getConditionExp().compile(this), astConditionalExpression.getThenExp().compile(this), astConditionalExpression.getElseExp().compile(this), astConditionalExpression.getLocation());
	}
}
