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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Expression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Variable;
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

public class Compiler {
	private static final Logger logger = Logger.getLogger(Compiler.class.getName());
	private final String packageName;
	private final String className;
	
	public Compiler() {
		this(Compiler.class.getPackage().getName(), Compiler.class.getSimpleName());
	}
	
	public Compiler(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public CompilerReport generateJavaSource(String source) throws IOException {
		List<CompilerError> errors = new ArrayList<>();
		ASTFractal ast = parse(source, errors);
		if (errors.size() == 0) {
			String orbitSource = buildOrbit(ast, errors);
			String colorSource = buildColor(ast, errors);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine(orbitSource);
				logger.fine(colorSource);
			}
			return new CompilerReport(ast, orbitSource, colorSource, errors);
		}
		return new CompilerReport(ast, "", "", errors);
	}
	
	public CompilerBuilder<Orbit> compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		Class<Orbit> clazz = compileToClass(report.getOrbitSource(), className + "Orbit", Orbit.class, errors);
		return new CompilerBuilder<Orbit>(clazz, errors);
	}

	public CompilerBuilder<Color> compileColor(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		Class<Color> clazz = compileToClass(report.getColorSource(), className + "Color", Color.class, errors);
		return new CompilerBuilder<Color>(clazz, errors);
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
            	ASTBuilder builder = parser.getBuilder();
            	ASTFractal fractal = builder.getFractal();
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

	private String buildOrbit(ASTFractal fractal, List<CompilerError> errors) {
		try {
			StringBuilder builder = new StringBuilder();
			Map<String, CompilerVariable> variables = new HashMap<>();
			compileOrbit(builder, variables, fractal);
			return builder.toString();
		} catch (ASTException e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return "";
	}
	
	private String buildColor(ASTFractal fractal, List<CompilerError> errors) {
		try {
			StringBuilder builder = new StringBuilder();
			Map<String, CompilerVariable> variables = new HashMap<>();
			compileColor(builder, variables, fractal);
			return builder.toString();
		} catch (ASTException e) {
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private <T> Class<T> compileToClass(String source, String className, Class<T> clazz, List<CompilerError> errors) throws IOException, ClassNotFoundException {
		logger.log(Level.FINE, "Compile Java source:\n" + source);
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new SourceJavaFileObject(className, source));
		List<String> options = new ArrayList<>();
//		options.addAll(Arrays.asList("-source", "1.8", "-target", "1.8", "-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		options.addAll(Arrays.asList("-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String orbitClassName = Compiler.this.packageName + "." + Compiler.this.className + "Orbit";
		String colorClassName = Compiler.this.packageName + "." + Compiler.this.className + "Color";
		JavaFileManager fileManager = new CompilerJavaFileManager(compiler.getStandardFileManager(diagnostics, null, null), orbitClassName, colorClassName);
		try {
			compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				CompilerError error = new CompilerError(CompilerError.ErrorType.JAVA_COMPILER, diagnostic.getLineNumber(), diagnostic.getColumnNumber(), diagnostic.getStartPosition(), diagnostic.getEndPosition() - diagnostic.getStartPosition(), diagnostic.getMessage(null));
				logger.log(Level.FINE, error.toString());
				errors.add(error);
			}
			if (diagnostics.getDiagnostics().size() == 0) {
				CompilerClassLoader loader = new CompilerClassLoader();
				defineClasses(fileManager, loader, className);
				Class<?> compiledClazz = loader.loadClass(packageName + "." + className);
				logger.log(Level.FINE, compiledClazz.getCanonicalName());
				if (clazz.isAssignableFrom(compiledClazz)) {
					return (Class<T>) compiledClazz;
				}
			}
		} finally {
			try {
				fileManager.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	private void defineClasses(JavaFileManager fileManager, CompilerClassLoader loader, String className) throws IOException {
		String name = packageName + "." + className;
		JavaFileObject file = fileManager.getJavaFileForOutput(StandardLocation.locationFor(name), name, Kind.CLASS, null);
		byte[] fileData = loadBytes(file);
		logger.log(Level.FINE, file.toUri().toString() + " (" + fileData.length + ")");
		loader.defineClassFromData(name, fileData);
	}

	private byte[] loadBytes(JavaFileObject file) throws IOException {
		InputStream is = null;
		try {
			is = file.openInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			byte[] out = baos.toByteArray();
			return out;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private String compileOrbit(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		builder.append("package ");
		builder.append(packageName);
		builder.append(";\n");
		builder.append("import static ");
		builder.append(Expression.class.getCanonicalName());
		builder.append(".*;\n");
		builder.append("import ");
		builder.append(Number.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(MutableNumber.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Trap.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Orbit.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Scope.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(List.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Variable.class.getCanonicalName());
		builder.append(";\n");
		builder.append("@SuppressWarnings(value=\"unused\")\n");
		builder.append("public class ");
		builder.append(className);
		builder.append("Orbit extends Orbit {\n");
		buildOrbit(builder, variables, fractal);
		builder.append("}\n");
		return builder.toString();
	}

	private String compileColor(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		builder.append("package ");
		builder.append(packageName);
		builder.append(";\n");
		builder.append("import static ");
		builder.append(Expression.class.getCanonicalName());
		builder.append(".*;\n");
		builder.append("import ");
		builder.append(Number.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(MutableNumber.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Palette.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Color.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Scope.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Variable.class.getCanonicalName());
		builder.append(";\n");
		builder.append("@SuppressWarnings(value=\"unused\")\n");
		builder.append("public class ");
		builder.append(className);
		builder.append("Color extends Color {\n");
		buildColor(builder, variables, fractal);
		builder.append("}\n");
		return builder.toString();
	}

	private void buildOrbit(StringBuilder builder, Map<String, CompilerVariable> scope, ASTFractal fractal) {
		if (fractal != null) {
			compile(builder, scope, fractal.getStateVariables(), fractal.getOrbit());
		}
	}

	private void buildColor(StringBuilder builder, Map<String, CompilerVariable> scope, ASTFractal fractal) {
		if (fractal != null) {
			compile(builder, scope, fractal.getStateVariables(), fractal.getColor());
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> scope, Collection<CompilerVariable> stateVariables, ASTOrbit orbit) {
		builder.append("public void init() {\n");
		if (orbit != null) {
			builder.append("setInitialRegion(");
			builder.append("new Number(");
			builder.append(orbit.getRegion().getA());
			builder.append("),new Number(");
			builder.append(orbit.getRegion().getB());
			builder.append("));\n");
			for (CompilerVariable var : stateVariables) {
				builder.append("addVariable(");
				builder.append(var.getName());
				builder.append(");\n");
			}
		}
		builder.append("}\n");
		if (stateVariables != null) {
			for (CompilerVariable var : stateVariables) {
				scope.put(var.getName(), var);
				if (var.isCreate()) {
					builder.append("private final Variable ");
					builder.append(var.getName());
					builder.append(" = variable(0.0,0.0);\n");
				}
			}
		}
		if (orbit != null) {
			for (ASTOrbitTrap trap : orbit.getTraps()) {
				compile(builder, scope, trap);
			}
		}
		builder.append("public void render(List<Number[]> states) {\n");
		if (orbit != null) {
			compile(builder, scope, orbit.getBegin(), stateVariables);
			Map<String, CompilerVariable> vars = new HashMap<String, CompilerVariable>(scope);
			compile(builder, vars, orbit.getLoop(), stateVariables);
			compile(builder, scope, orbit.getEnd(), stateVariables);
		}
		int i = 0;
		for (CompilerVariable var : stateVariables) {
			builder.append("setVariable(");
			builder.append(i++);
			builder.append(",");
			builder.append(var.getName());
			builder.append(");\n");
		}
		builder.append("}\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> scope, Collection<CompilerVariable> stateVariables, ASTColor color) {
		for (CompilerVariable var : stateVariables) {
			scope.put(var.getName(), var);
		}
		if (color != null) {
			for (ASTPalette palette : color.getPalettes()) {
				compile(builder, scope, palette);
			}
		}
		builder.append("public void render() {\n");
		int i = 0;
		for (CompilerVariable var : stateVariables) {
			builder.append("final Variable ");
			builder.append(var.getName());
			builder.append(" = getVariable(");
			builder.append(i++);
			builder.append(");\n");
		}
		if (color != null) {
			builder.append("setColor(color(");
			builder.append(color.getArgb().getComponents()[0]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[1]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[2]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[3]);
			builder.append("));\n");
			if (color.getInit() != null) {
				for (ASTStatement statement : color.getInit().getStatements()) {
					compile(builder, scope, statement);
				}
			}
			for (ASTRule rule : color.getRules()) {
				compile(builder, scope, rule);
			}
		}
		builder.append("}\n");
	}

	private void compile(StringBuilder builder,	Map<String, CompilerVariable> variables, ASTRule rule) {
		builder.append("if (");
		rule.getRuleExp().compile(new ExpressionCompiler(variables, builder));
		builder.append(") {\n");
		builder.append("addColor(");
		builder.append(rule.getOpacity());
		builder.append(",");
		rule.getColorExp().compile(new ExpressionCompiler(variables, builder));
		builder.append(");\n}\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTPalette palette) {
		builder.append("private Palette palette");
		builder.append(palette.getName().toUpperCase().substring(0, 1));
		builder.append(palette.getName().substring(1));
		builder.append(" = palette()");
		for (ASTPaletteElement element : palette.getElements()) {
			builder.append(".add(");
			compile(builder, variables, element);
			builder.append(")");
		}
		builder.append(".build();\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTPaletteElement element) {
		builder.append("element(");
		builder.append(createArray(element.getBeginColor().getComponents()));
		builder.append(",");
		builder.append(createArray(element.getEndColor().getComponents()));
		builder.append(",");
		builder.append(element.getSteps());
		builder.append(",(start, end, step) -> { return ");
		if (element.getExp() != null) {
			if (element.getExp().isReal()) {
				element.getExp().compile(new ExpressionCompiler(variables, builder));
			} else {
				throw new ASTException("Expression type not valid: " + element.getLocation().getText(), element.getLocation());
			}
		} else {
			builder.append("step / (end - start)");
		}
		builder.append(";})");
	}

	private void compile(StringBuilder builder,	Map<String, CompilerVariable> variables, ASTOrbitTrap trap) {
		builder.append("private Trap trap");
		builder.append(trap.getName().toUpperCase().substring(0, 1));
		builder.append(trap.getName().substring(1));
		builder.append(" = trap(number(");
		builder.append(trap.getCenter());
		builder.append("))");
		for (ASTOrbitTrapOp operator : trap.getOperators()) {
			builder.append(".");
			switch (operator.getOp()) {
				case "MOVETO":
					builder.append("moveTo");
					break;

				case "MOVETOREL":
					builder.append("moveToRel");
					break;

				case "LINETO":
					builder.append("lineTo");
					break;

				case "LINETOREL":
					builder.append("lineToRel");
					break;

				case "ARCTO":
					builder.append("arcTo");
					break;

				case "ARCTOREL":
					builder.append("arcToRel");
					break;

				case "CURVETO":
					builder.append("curveTo");
					break;

				case "CURVETOREL":
					builder.append("curveToRel");
					break;

				default:
					break;
			}
			builder.append("(");
			if (operator.getC1().isReal()) {
				builder.append("number(");
				operator.getC1().compile(new ExpressionCompiler(variables, builder));
				builder.append(")");
			} else {
				operator.getC1().compile(new ExpressionCompiler(variables, builder));
			}
			if (operator.getC2() != null) {
				builder.append(",");
				if (operator.getC2().isReal()) {
					builder.append("number(");
					operator.getC2().compile(new ExpressionCompiler(variables, builder));
					builder.append(")");
				} else {
					operator.getC2().compile(new ExpressionCompiler(variables, builder));
				}
			}
			builder.append(")");
		}
		builder.append(";\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitBegin begin, Collection<CompilerVariable> stateVariables) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitEnd end, Collection<CompilerVariable> stateVariables) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitLoop loop, Collection<CompilerVariable> stateVariables) {
		if (loop != null) {
			builder.append("n.set(");
			builder.append(loop.getBegin());
			builder.append(");\n");
			builder.append("for (int i = ");
			builder.append(loop.getBegin());
			builder.append(" + 1; i <= ");
			builder.append(loop.getEnd());
			builder.append("; i++) {\n");
			for (ASTStatement statement : loop.getStatements()) {
				compile(builder, variables, statement);
			}
			builder.append("if (states != null) {\n");
			builder.append("states.add(new Number[] { ");
			int i = 0;
			for (CompilerVariable var : stateVariables) {
				if (i > 0) {
					builder.append(", ");
				}
				builder.append("new Number(");
				builder.append(var.getName());
				builder.append(")");
				i += 1;
			}
			builder.append(" });\n");
			builder.append("}\n");
			builder.append("if (");
			loop.getExpression().compile(new ExpressionCompiler(variables, builder));
			builder.append(") { n.set(i); break; }\n");
			builder.append("}\n");
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTStatement statement) {
		if (statement != null) {
			statement.compile(new ExpressionCompiler(variables, builder));
		}		
	}
	
	private String createArray(float[] components) {
		return "new float[] {" + components[0] + "f," + components[1] + "f," + components[2] + "f," + components[3] + "f}";
	}
}	
