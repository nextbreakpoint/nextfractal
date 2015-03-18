package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Expression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorComponent;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTColorPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionCompareOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionLogicOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTConditionTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTExpressionCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFunction;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOperator;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitBegin;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitEnd;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitLoop;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTOrbitTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTParen;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRule;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleCompareOpExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTRuleLogicOpExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotLexer;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotParser;

public class Compiler {
	private static final Logger logger = Logger.getLogger(Compiler.class.getName());
	private final String packageName;
	private final String className;
	
	public Compiler(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public CompilerReport generateJavaSource(String source) throws IOException {
		List<CompilerError> errors = new ArrayList<>();
		ASTFractal ast = parse(source, errors);
		if (errors.size() == 0) {
			String orbitSource = buildOrbit(ast);
			String colorSource = buildColor(ast);
			return new CompilerReport(ast, orbitSource, colorSource, errors);
		} else {
			return new CompilerReport(ast, "", "", errors);
		}
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
			logger.log(Level.INFO, error.toString(), e);
			errors.add(error);
		}
		return null;
	}

	private String buildOrbit(ASTFractal fractal) {
		StringBuilder builder = new StringBuilder();
		Map<String, CompilerVariable> variables = new HashMap<>();
		compileOrbit(builder, variables, fractal);
		return builder.toString();
	}
	
	private String buildColor(ASTFractal fractal) {
		StringBuilder builder = new StringBuilder();
		Map<String, CompilerVariable> variables = new HashMap<>();
		compileColor(builder, variables, fractal);
		return builder.toString();
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
		JavaFileManager fileManager = new CompilerJavaFileManager(compiler.getStandardFileManager(diagnostics, null, null));
		try {
			compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				CompilerError error = new CompilerError(CompilerError.ErrorType.JAVA_COMPILER, diagnostic.getLineNumber(), diagnostic.getColumnNumber(), diagnostic.getStartPosition(), diagnostic.getEndPosition() - diagnostic.getStartPosition(), diagnostic.getMessage(null));
				logger.log(Level.INFO, error.toString());
				errors.add(error);
			}
			if (diagnostics.getDiagnostics().size() == 0) {
				CompilerClassLoader loader = new CompilerClassLoader();
				defineClasses(fileManager, loader, className);
				Class<?> compiledClazz = loader.loadClass(packageName + "." + className);
				logger.log(Level.INFO, compiledClazz.getCanonicalName());
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
		builder.append("@SuppressWarnings(value=\"unused\")\n");
		builder.append("public class ");
		builder.append(className);
		builder.append("Color extends Color {\n");
		buildColor(builder, variables, fractal);
		builder.append("}\n");
		return builder.toString();
	}

	private void buildOrbit(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		if (fractal != null) {
			compile(builder, variables, fractal.getVars(), fractal.getOrbit());
		}
	}

	private void buildColor(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		if (fractal != null) {
			compile(builder, variables, fractal.getVars(), fractal.getColor());
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, Collection<CompilerVariable> vars, ASTOrbit orbit) {
		builder.append("public void init() {\n");
		if (orbit != null) {
			builder.append("setInitialRegion(");
			builder.append("new Number(");
			builder.append(orbit.getRegion().getA());
			builder.append("),new Number(");
			builder.append(orbit.getRegion().getB());
			builder.append("));\n");
			for (String varName : orbit.getVariables()) {
				builder.append("createVariable(");
				builder.append(varName);
				builder.append(");\n");
			}
		}
		builder.append("}\n");
		if (vars != null) {
			for (CompilerVariable var : vars) {
				variables.put(var.getName(), var);
				if (var.isCreate()) {
					if (var.isReal()) {
						builder.append("private double ");
						builder.append(var.getName());
						builder.append(" = 0.0;\n");
						builder.append("public Number get");
						builder.append(var.getName().toUpperCase());
						builder.append("() { return number(");
						builder.append(var.getName());
						builder.append(",0); }\n");
					} else {
						builder.append("private Number ");
						builder.append(var.getName());
						builder.append(" = number(0.0,0.0);\n");
						builder.append("public Number get");
						builder.append(var.getName().toUpperCase());
						builder.append("() { return ");
						builder.append(var.getName());
						builder.append("; }\n");
					}
				}
			}
		}
		if (orbit != null) {
			for (ASTOrbitTrap trap : orbit.getTraps()) {
				compile(builder, variables, trap);
			}
		}
		builder.append("public void render(List<Number[]> states) {\n");
		if (orbit != null) {
			compile(builder, variables, orbit.getBegin(), orbit.getVariables());
			compile(builder, variables, orbit.getLoop(), orbit.getVariables());
			compile(builder, variables, orbit.getEnd(), orbit.getVariables());
			int i = 0;
			for (String varName : orbit.getVariables()) {
				builder.append("setVariable(");
				builder.append(i++);
				builder.append(",");
				builder.append(varName);
				builder.append(");\n");
			}
		}
		builder.append("}\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, Collection<CompilerVariable> vars, ASTColor color) {
		if (color != null) {
			for (ASTPalette palette : color.getPalettes()) {
				compile(builder, variables, palette);
			}
		}
		for (CompilerVariable var : vars) {
			variables.put(var.getName(), var);
		}
		builder.append("public void render() {\n");
		if (color != null) {
			int i = 0;
			for (String varName : color.getVariables()) {
				CompilerVariable variable = variables.get(varName);
				builder.append("Number ");
				builder.append(variable.getName());
				builder.append(" = getVariable(");
				builder.append(i++);
				builder.append(");\n");
			}
			builder.append("setColor(color(");
			builder.append(color.getArgb().getComponents()[0]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[1]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[2]);
			builder.append(",");
			builder.append(color.getArgb().getComponents()[3]);
			builder.append("));\n");
			for (ASTRule rule : color.getRules()) {
				compile(builder, variables, rule);
			}
		}
		builder.append("}\n");
	}

	private void compile(StringBuilder builder,	Map<String, CompilerVariable> variables, ASTRule rule) {
		builder.append("if (");
		rule.getRuleExp().compile(new ExpressionCompiler(builder));
		builder.append(") {\n");
		builder.append("addColor(");
		builder.append(rule.getOpacity());
		builder.append(",");
		rule.getColorExp().compile(new ExpressionCompiler(builder));
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
				element.getExp().compile(new ExpressionCompiler(builder));
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

				case "LINETO":
					builder.append("lineTo");
					break;

				case "ARCTO":
					builder.append("arcTo");
					break;

				case "MOVEREL":
					builder.append("moveRel");
					break;

				case "LINEREL":
					builder.append("lineRel");
					break;

				case "ARCREL":
					builder.append("arcRel");
					break;

				default:
					break;
			}
			builder.append("(");
			if (operator.getC1().isReal()) {
				builder.append("number(");
				operator.getC1().compile(new ExpressionCompiler(builder));
				builder.append(")");
			} else {
				operator.getC1().compile(new ExpressionCompiler(builder));
			}
			if (operator.getC2() != null) {
				builder.append(",");
				if (operator.getC2().isReal()) {
					builder.append("number(");
					operator.getC2().compile(new ExpressionCompiler(builder));
					builder.append(")");
				} else {
					operator.getC2().compile(new ExpressionCompiler(builder));
				}
			}
			builder.append(")");
		}
		builder.append(";\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitBegin begin, List<String> stateVariables) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitEnd end, List<String> stateVariables) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitLoop loop, List<String> stateVariables) {
		if (loop != null) {
			builder.append("n = number(0);\n");
			builder.append("for (int i = 1; i <= ");
			builder.append(loop.getEnd());
			builder.append("; i++) {\n");
			for (ASTStatement statement : loop.getStatements()) {
				compile(builder, variables, statement);
			}
			builder.append("if (states != null) {\n");
			builder.append("states.add(new Number[] { ");
			int i = 0;
			for (String varName : stateVariables) {
				if (i > 0) {
					builder.append(", ");
				}
				builder.append("new Number(");
				builder.append(varName);
				builder.append(")");
				i += 1;
			}
			builder.append(" });\n");
			builder.append("}\n");
			builder.append("if (");
			loop.getExpression().compile(new ExpressionCompiler(builder));
			builder.append(") { n = number(i); break; }\n");
			builder.append("}\n");
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTStatement statement) {
		if (statement != null) {
			CompilerVariable var = variables.get(statement.getName());
			if (var != null) {
				if ((var.isReal() && statement.getExp().isReal()) || (!var.isReal() && !statement.getExp().isReal())) {
					builder.append(statement.getName());
					builder.append(" = ");
					statement.getExp().compile(new ExpressionCompiler(builder));
					builder.append(";\n");
				} else if (!var.isReal() && statement.getExp().isReal()) {
					builder.append(statement.getName());
					builder.append(" = number(");
					statement.getExp().compile(new ExpressionCompiler(builder));
					builder.append(",0);\n");
				} else if (var.isReal() && !statement.getExp().isReal()) {
					throw new ASTException("Expression not assignable: " + statement.getLocation().getText(), statement.getLocation());
				}
			}
		}		
	}
	
	private String createArray(float[] components) {
		return "new float[] {" + components[0] + "f," + components[1] + "f," + components[2] + "f," + components[3] + "f}";
	}

	private class ExpressionCompiler implements ASTExpressionCompiler {
		private final StringBuilder builder;
		
		public ExpressionCompiler(StringBuilder builder) {
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
	
				case "log":
					if (function.getArguments().length != 1) {
						throw new ASTException("Invalid number of arguments: " + function.getLocation().getText(), function.getLocation());
					}				
					if (!function.getArguments()[0].isReal()) {
						throw new ASTException("Invalid type of arguments: " + function.getLocation().getText(), function.getLocation());
					}				
					break;
					
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
							builder.append("-");
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
							builder.append("^");
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
			builder.append("trap");
			builder.append(trap.getName().toUpperCase().substring(0, 1));
			builder.append(trap.getName().substring(1));
			builder.append(".contains(");
			trap.getExp().compile(this);
			builder.append(")");
		}

		@Override
		public void compile(ASTRuleLogicOpExpression logicOp) {
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
		public void compile(ASTRuleCompareOpExpression compareOp) {
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
	}

	private class SourceJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        public SourceJavaFileObject(String name, String code) {
            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

	private class ClassJavaFileObject extends SimpleJavaFileObject {
		private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        public ClassJavaFileObject(String name) {
            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.CLASS);
        }

		@Override
		public InputStream openInputStream() throws IOException {
			return new ByteArrayInputStream(baos.toByteArray());
		}

		@Override
		public OutputStream openOutputStream() throws IOException {
			baos.reset();
			return baos;
		}
    }
	
	private static class CompilerClassLoader extends ClassLoader {
		private static final AtomicInteger count = new AtomicInteger();
		
		public CompilerClassLoader() {
			logger.fine("Create classloader (" + count.addAndGet(1) + ")");
		}
		
		public void defineClassFromData(String name, byte[] data) {
			Class<?> clazz = defineClass(name, data, 0, data.length);
			super.resolveClass(clazz);
		}

		@Override
		protected void finalize() throws Throwable {
			logger.fine("Finalize classloader (" + count.addAndGet(-1) + ")");
			super.finalize();
		}
	}
	
	private class CompilerErrorStrategy extends DefaultErrorStrategy {
		private List<CompilerError> errors;
		
		public CompilerErrorStrategy(List<CompilerError> errors) {
			this.errors = errors;
		}

		@Override
		public void reportError(Parser recognizer, RecognitionException e) {
			String message = generateErrorMessage("Expected tokens", recognizer);
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
			logger.log(Level.WARNING, error.toString(), e);
			errors.add(error);
		}

		@Override
		protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
			String message = generateErrorMessage("Input mismatch", recognizer);
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
			logger.log(Level.WARNING, error.toString(), e);
			errors.add(error);
		}

		@Override
		protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
			String message = generateErrorMessage("Failed predicate", recognizer);
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
			logger.log(Level.WARNING, error.toString(), e);
			errors.add(error);
		}

		@Override
		protected void reportUnwantedToken(Parser recognizer) {
			String message = generateErrorMessage("Unwanted token", recognizer);
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
			logger.log(Level.WARNING, error.toString());
			errors.add(error);
		}

		@Override
		protected void reportMissingToken(Parser recognizer) {
			String message = generateErrorMessage("Missing token", recognizer);
			CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
			logger.log(Level.WARNING, error.toString());
			errors.add(error);
		}
	}
	
	private class CompilerJavaFileManager implements JavaFileManager {
		private Map<String, JavaFileObject> files = new HashMap<>();
		private JavaFileManager fileManager;
		
		public CompilerJavaFileManager(JavaFileManager fileManager) {
			this.fileManager = fileManager;
		}

		/**
		 * @see javax.tools.OptionChecker#isSupportedOption(java.lang.String)
		 */
		@Override
		public int isSupportedOption(String option) {
			return fileManager.isSupportedOption(option);
		}

		/**
		 * @see javax.tools.JavaFileManager#getClassLoader(javax.tools.JavaFileManager.Location)
		 */
		@Override
		public ClassLoader getClassLoader(Location location) {
			return fileManager.getClassLoader(location);
		}

		/**
		 * @see javax.tools.JavaFileManager#list(javax.tools.JavaFileManager.Location, java.lang.String, java.util.Set, boolean)
		 */
		@Override
		public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse) throws IOException {
			return fileManager.list(location, packageName, kinds, recurse);
		}

		/**
		 * @see javax.tools.JavaFileManager#inferBinaryName(javax.tools.JavaFileManager.Location, javax.tools.JavaFileObject)
		 */
		@Override
		public String inferBinaryName(Location location, JavaFileObject file) {
			return fileManager.inferBinaryName(location, file);
		}

		/**
		 * @see javax.tools.JavaFileManager#isSameFile(javax.tools.FileObject, javax.tools.FileObject)
		 */
		@Override
		public boolean isSameFile(FileObject a, FileObject b) {
			return fileManager.isSameFile(a, b);
		}

		/**
		 * @see javax.tools.JavaFileManager#handleOption(java.lang.String, java.util.Iterator)
		 */
		@Override
		public boolean handleOption(String current, Iterator<String> remaining) {
			return fileManager.handleOption(current, remaining);
		}

		/**
		 * @see javax.tools.JavaFileManager#hasLocation(javax.tools.JavaFileManager.Location)
		 */
		@Override
		public boolean hasLocation(Location location) {
			return fileManager.hasLocation(location);
		}

		/**
		 * @see javax.tools.JavaFileManager#getJavaFileForInput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind)
		 */
		@Override
		public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
			return fileManager.getJavaFileForInput(location, className, kind);
		}

		/**
		 * @see javax.tools.JavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind, javax.tools.FileObject)
		 */
		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
			if (className.equals(Compiler.this.packageName + "." + Compiler.this.className + "Orbit") || className.equals(Compiler.this.packageName + "." + Compiler.this.className + "Color")) {
				JavaFileObject file = files.get(className);
				if (file == null) {
					file = new ClassJavaFileObject(className);
					files.put(className, file);
				}
				return file;
			} else {
				return fileManager.getJavaFileForOutput(location, className, kind, sibling);
			}
		}

		/**
		 * @see javax.tools.JavaFileManager#getFileForInput(javax.tools.JavaFileManager.Location, java.lang.String, java.lang.String)
		 */
		@Override
		public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
			return fileManager.getFileForInput(location, packageName, relativeName);
		}

		/**
		 * @see javax.tools.JavaFileManager#getFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, java.lang.String, javax.tools.FileObject)
		 */
		@Override
		public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
			return fileManager.getFileForOutput(location, packageName, relativeName, sibling);
		}

		/**
		 * @see javax.tools.JavaFileManager#flush()
		 */
		@Override
		public void flush() throws IOException {
			fileManager.flush();
		}

		/**
		 * @see javax.tools.JavaFileManager#close()
		 */
		@Override
		public void close() throws IOException {
			fileManager.close();
			files.clear();
		}
	}

	private String generateErrorMessage(String message, Parser recognizer) {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		IntervalSet tokens = recognizer.getExpectedTokens();
		boolean first = true;
		for (Entry<String, Integer> entry : recognizer.getTokenTypeMap().entrySet()) {
			if (tokens.contains(entry.getValue())) {
				if (first) {
					first = false;
					builder.append(" ");
				} else {
					builder.append(", ");
				}
				builder.append(entry.getKey());
			}
		}
		return builder.toString();
	}
}	
