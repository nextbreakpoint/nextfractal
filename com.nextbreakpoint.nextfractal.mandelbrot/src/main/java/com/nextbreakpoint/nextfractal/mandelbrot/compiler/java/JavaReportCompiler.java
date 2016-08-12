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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerErrorStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport.Type;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Expression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.FastExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitBegin;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitEnd;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitLoop;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRule;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotLexer;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotParser;

public class JavaReportCompiler {
	private static final Logger logger = Logger.getLogger(JavaReportCompiler.class.getName());
	private final String packageName;
	private final String className;
	
	public JavaReportCompiler(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public CompilerReport generateReport(String source) throws IOException {
		List<CompilerError> errors = new ArrayList<>();
		ASTFractal ast = parse(source, errors);
		if (errors.size() == 0) {
			ExpressionContext orbitContext = new ExpressionContext();
			String orbitSource = buildOrbit(orbitContext, ast, errors);
			ExpressionContext colorContext = new ExpressionContext();
			String colorSource = buildColor(colorContext, ast, errors);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine(orbitSource);
				logger.fine(colorSource);
			}
			return new CompilerReport(ast, Type.JAVA, orbitSource, colorSource, errors);
		}
		return new CompilerReport(ast, Type.JAVA, "", "", errors);
	}
	
	private ASTFractal parse(String source, List<CompilerError> errors) throws IOException {
		try {
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			MandelbrotLexer lexer = new MandelbrotLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MandelbrotParser parser = new MandelbrotParser(tokens);
			parser.setErrorHandler(new CompilerErrorStrategy(errors));
			ParseTree fractalTree = parser.fractal();
            if (fractalTree != null) {
            	ASTBuilder driver = parser.getBuilder();
            	ASTFractal fractal = driver.getFractal();
            	return fractal;
            }
		} catch (ASTException e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		} catch (Exception e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, 0L, 0L, 0L, 0L, e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return null;
	}

	private String buildOrbit(ExpressionContext context, ASTFractal fractal, List<CompilerError> errors) {
		try {
			StringBuilder driver = new StringBuilder();
			Map<String, CompilerVariable> variables = new HashMap<>();
			compileOrbit(context, driver, variables, fractal);
			return driver.toString();
		} catch (ASTException e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return "";
	}
	
	private String buildColor(ExpressionContext context, ASTFractal fractal, List<CompilerError> errors) {
		try {
			StringBuilder driver = new StringBuilder();
			Map<String, CompilerVariable> variables = new HashMap<>();
			compileColor(context, driver, variables, fractal);
			return driver.toString();
		} catch (ASTException e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return "";
	}
	
	private String compileOrbit(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		driver.append("package ");
		driver.append(packageName);
		driver.append(";\n");
		driver.append("import static ");
		if (Boolean.getBoolean("mandelbrot.expression.fastmath")) {
			driver.append(FastExpression.class.getCanonicalName());
		} else {
			driver.append(Expression.class.getCanonicalName());
		}
		driver.append(".*;\n");
		driver.append("import ");
		driver.append(Number.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(MutableNumber.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Trap.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Orbit.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Scope.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(List.class.getCanonicalName());
		driver.append(";\n");
		driver.append("@SuppressWarnings(value=\"unused\")\n");
		driver.append("public class ");
		driver.append(className);
		driver.append("Orbit extends Orbit {\n");
		buildOrbit(context, driver, variables, fractal);
		driver.append("}\n");
		return driver.toString();
	}

	private String compileColor(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		driver.append("package ");
		driver.append(packageName);
		driver.append(";\n");
		driver.append("import static ");
		if (Boolean.getBoolean("mandelbrot.expression.fastmath")) {
			driver.append(FastExpression.class.getCanonicalName());
		} else {
			driver.append(Expression.class.getCanonicalName());
		}
		driver.append(".*;\n");
		driver.append("import ");
		driver.append(Number.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(MutableNumber.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Palette.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Color.class.getCanonicalName());
		driver.append(";\n");
		driver.append("import ");
		driver.append(Scope.class.getCanonicalName());
		driver.append(";\n");
		driver.append("@SuppressWarnings(value=\"unused\")\n");
		driver.append("public class ");
		driver.append(className);
		driver.append("Color extends Color {\n");
		buildColor(context, driver, variables, fractal);
		driver.append("}\n");
		return driver.toString();
	}

	private void buildOrbit(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> scope, ASTFractal fractal) {
		if (fractal != null) {
			for (CompilerVariable var : fractal.getOrbitVariables()) {
				scope.put(var.getName(),  var);
			}
			for (CompilerVariable var : fractal.getStateVariables()) {
				scope.put(var.getName(),  var);
			}
			compile(context, driver, scope, fractal.getStateVariables(), fractal.getOrbit());
		}
	}

	private void buildColor(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> scope, ASTFractal fractal) {
		if (fractal != null) {
			for (CompilerVariable var : fractal.getColorVariables()) {
				scope.put(var.getName(),  var);
			}
			for (CompilerVariable var : fractal.getStateVariables()) {
				scope.put(var.getName(),  var);
			}
			compile(context, driver, scope, fractal.getStateVariables(), fractal.getColor());
		}
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> scope, Collection<CompilerVariable> stateVariables, ASTOrbit orbit) {
		driver.append("public void init() {\n");
		if (orbit != null) {
			driver.append("setInitialRegion(");
			driver.append("number(");
			driver.append(orbit.getRegion().getA());
			driver.append("),number(");
			driver.append(orbit.getRegion().getB());
			driver.append("));\n");
			for (CompilerVariable var : stateVariables) {
				driver.append("addVariable(");
				driver.append(var.getName());
				driver.append(");\n");
			}
			for (ASTOrbitTrap trap : orbit.getTraps()) {
				driver.append("addTrap(trap");
				driver.append(trap.getName().toUpperCase().substring(0, 1));
				driver.append(trap.getName().substring(1));
				driver.append(");\n");
			}
		}
		driver.append("}\n");
		for (CompilerVariable var : scope.values()) {
			scope.put(var.getName(), var);
			if (var.isCreate()) {
				if (var.isReal()) {
					driver.append("private double ");
					driver.append(var.getName());
					driver.append(" = 0.0;\n");
				} else {
					driver.append("private final MutableNumber ");
					driver.append(var.getName());
					driver.append(" = getNumber(");
					driver.append(context.newNumberIndex());
					driver.append(").set(0.0,0.0);\n");
				}
			}
		}
		if (orbit != null) {
			for (ASTOrbitTrap trap : orbit.getTraps()) {
				compile(context, driver, scope, trap);
			}
		}
		driver.append("public void render(List<Number[]> states) {\n");
		if (orbit != null) {
			compile(context, driver, scope, orbit.getBegin(), stateVariables);
			Map<String, CompilerVariable> vars = new HashMap<String, CompilerVariable>(scope);
			compile(context, driver, vars, orbit.getLoop(), stateVariables);
			compile(context, driver, scope, orbit.getEnd(), stateVariables);
		}
		int i = 0;
		for (CompilerVariable var : stateVariables) {
			driver.append("setVariable(");
			driver.append(i++);
			driver.append(",");
			driver.append(var.getName());
			driver.append(");\n");
		}
		driver.append("}\n");
		driver.append("protected MutableNumber[] createNumbers() {\n");
		driver.append("return new MutableNumber[");
		driver.append(context.getNumberCount());
		driver.append("];\n");
		driver.append("}\n");
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> scope, Collection<CompilerVariable> stateVariables, ASTColor color) {
		driver.append("public void init() {\n");
		if (color != null) {
		}
		driver.append("}\n");
		for (CompilerVariable var : stateVariables) {
			scope.put(var.getName(), var);
		}
		if (color != null) {
			for (ASTPalette palette : color.getPalettes()) {
				compile(context, driver, scope, palette);
			}
		}
		driver.append("public void render() {\n");
		int i = 0;
		for (CompilerVariable var : stateVariables) {
			if (var.isReal()) {
				driver.append("double ");
				driver.append(var.getName());
				driver.append(" = getRealVariable(");
				driver.append(i++);
				driver.append(");\n");
			} else {
				driver.append("final MutableNumber ");
				driver.append(var.getName());
				driver.append(" = getVariable(");
				driver.append(i++);
				driver.append(");\n");
			}
		}
		i = 0;
		for (CompilerVariable var : scope.values()) {
			if (!stateVariables.contains(var)) {
				if (var.isReal()) {
					driver.append("double ");
					driver.append(var.getName());
					driver.append(" = 0;\n");
				} else {
					driver.append("final MutableNumber ");
					driver.append(var.getName());
					driver.append(" = getNumber(");
					driver.append(i++);
					driver.append(");\n");
				}
			}
		}
		if (color != null) {
			driver.append("setColor(color(");
			driver.append(color.getArgb().getComponents()[0]);
			driver.append(",");
			driver.append(color.getArgb().getComponents()[1]);
			driver.append(",");
			driver.append(color.getArgb().getComponents()[2]);
			driver.append(",");
			driver.append(color.getArgb().getComponents()[3]);
			driver.append("));\n");
			if (color.getInit() != null) {
				for (ASTStatement statement : color.getInit().getStatements()) {
					compile(context, driver, scope, statement);
				}
			}
			for (ASTRule rule : color.getRules()) {
				compile(context, driver, scope, rule);
			}
		}
		driver.append("}\n");
		driver.append("protected MutableNumber[] createNumbers() {\n");
		driver.append("return new MutableNumber[");
		driver.append(context.getNumberCount());
		driver.append("];\n");
		driver.append("}\n");
	}

	private void compile(ExpressionContext context, StringBuilder driver,	Map<String, CompilerVariable> variables, ASTRule rule) {
		driver.append("if (");
		rule.getRuleExp().compile(new JavaASTCompiler(context, variables, driver));
		driver.append(") {\n");
		driver.append("addColor(");
		driver.append(rule.getOpacity());
		driver.append(",");
		rule.getColorExp().compile(new JavaASTCompiler(context, variables, driver));
		driver.append(");\n}\n");
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTPalette palette) {
		driver.append("private Palette palette");
		driver.append(palette.getName().toUpperCase().substring(0, 1));
		driver.append(palette.getName().substring(1));
		driver.append(" = palette()");
		for (ASTPaletteElement element : palette.getElements()) {
			driver.append(".add(");
			compile(context, driver, variables, element);
			driver.append(")");
		}
		driver.append(".build();\n");
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTPaletteElement element) {
		driver.append("element(");
		driver.append(createArray(element.getBeginColor().getComponents()));
		driver.append(",");
		driver.append(createArray(element.getEndColor().getComponents()));
		driver.append(",");
		driver.append(element.getSteps());
		driver.append(",(start, end, step) -> { return ");
		if (element.getExp() != null) {
			if (element.getExp().isReal()) {
				element.getExp().compile(new JavaASTCompiler(context, variables, driver));
			} else {
				throw new ASTException("Expression type not valid: " + element.getLocation().getText(), element.getLocation());
			}
		} else {
			driver.append("step / (end - start)");
		}
		driver.append(";})");
	}

	private void compile(ExpressionContext context, StringBuilder driver,	Map<String, CompilerVariable> variables, ASTOrbitTrap trap) {
		driver.append("private Trap trap");
		driver.append(trap.getName().toUpperCase().substring(0, 1));
		driver.append(trap.getName().substring(1));
		driver.append(" = trap(number(");
		driver.append(trap.getCenter());
		driver.append("))");
		for (ASTOrbitTrapOp operator : trap.getOperators()) {
			driver.append(".");
			switch (operator.getOp()) {
				case "MOVETO":
					driver.append("moveTo");
					break;

				case "MOVETOREL":
					driver.append("moveToRel");
					break;

				case "LINETO":
					driver.append("lineTo");
					break;

				case "LINETOREL":
					driver.append("lineToRel");
					break;

				case "ARCTO":
					driver.append("arcTo");
					break;

				case "ARCTOREL":
					driver.append("arcToRel");
					break;

				case "QUADTO":
					driver.append("quadTo");
					break;

				case "QUADTOREL":
					driver.append("quadToRel");
					break;

				case "CURVETO":
					driver.append("curveTo");
					break;

				case "CURVETOREL":
					driver.append("curveToRel");
					break;

				case "CLOSE":
					driver.append("close");
					break;

				default:
					break;
			}
			driver.append("(");
			if (operator.getC1() != null) {
				if (operator.getC1().isReal()) {
					driver.append("number(");
					operator.getC1().compile(new JavaASTCompiler(context, variables, driver));
					driver.append(")");
				} else {
					operator.getC1().compile(new JavaASTCompiler(context, variables, driver));
				}
			}
			if (operator.getC2() != null) {
				driver.append(",");
				if (operator.getC2().isReal()) {
					driver.append("number(");
					operator.getC2().compile(new JavaASTCompiler(context, variables, driver));
					driver.append(")");
				} else {
					operator.getC2().compile(new JavaASTCompiler(context, variables, driver));
				}
			}
			if (operator.getC3() != null) {
				driver.append(",");
				if (operator.getC3().isReal()) {
					driver.append("number(");
					operator.getC3().compile(new JavaASTCompiler(context, variables, driver));
					driver.append(")");
				} else {
					operator.getC3().compile(new JavaASTCompiler(context, variables, driver));
				}
			}
			driver.append(")");
		}
		driver.append(";\n");
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTOrbitBegin begin, Collection<CompilerVariable> stateVariables) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(context, driver, variables, statement);
			}
		}
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTOrbitEnd end, Collection<CompilerVariable> stateVariables) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(context, driver, variables, statement);
			}
		}
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTOrbitLoop loop, Collection<CompilerVariable> stateVariables) {
		if (loop != null) {
			driver.append("n = ");
			driver.append(loop.getBegin());
			driver.append(";\n");
			driver.append("if (states != null) {\n");
			driver.append("states.add(new Number[] { ");
			int i = 0;
			for (CompilerVariable var : stateVariables) {
				if (i > 0) {
					driver.append(", ");
				}
				driver.append("number(");
				driver.append(var.getName());
				driver.append(")");
				i += 1;
			}
			driver.append(" });\n");
			driver.append("}\n");
			driver.append("for (int i = ");
			driver.append(loop.getBegin());
			driver.append(" + 1; i <= ");
			driver.append(loop.getEnd());
			driver.append("; i++) {\n");
			for (ASTStatement statement : loop.getStatements()) {
				compile(context, driver, variables, statement);
			}
			driver.append("if (");
			loop.getExpression().compile(new JavaASTCompiler(context, variables, driver));
			driver.append(") { n = i; break; }\n");
			driver.append("if (states != null) {\n");
			driver.append("states.add(new Number[] { ");
			i = 0;
			for (CompilerVariable var : stateVariables) {
				if (i > 0) {
					driver.append(", ");
				}
				driver.append("number(");
				driver.append(var.getName());
				driver.append(")");
				i += 1;
			}
			driver.append(" });\n");
			driver.append("}\n");
			driver.append("}\n");
			driver.append("if (states != null) {\n");
			driver.append("states.add(new Number[] { ");
			i = 0;
			for (CompilerVariable var : stateVariables) {
				if (i > 0) {
					driver.append(", ");
				}
				driver.append("number(");
				driver.append(var.getName());
				driver.append(")");
				i += 1;
			}
			driver.append(" });\n");
			driver.append("}\n");
		}
	}

	private void compile(ExpressionContext context, StringBuilder driver, Map<String, CompilerVariable> variables, ASTStatement statement) {
		if (statement != null) {
			statement.compile(new JavaASTCompiler(context, variables, driver));
		}		
	}
	
	private String createArray(float[] components) {
		return "new float[] {" + components[0] + "f," + components[1] + "f," + components[2] + "f," + components[3] + "f}";
	}
}	
