package com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Fractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Palette;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Trap;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTColorComponent;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTColorPalette;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTConditionCompareOp;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTConditionExpression;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTConditionLogicOp;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTConditionTrap;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTExpression;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTExpressionCompiler;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTFunction;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOperator;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbitBegin;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbitEnd;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbitLoop;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbitTrap;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTOrbitTrapOp;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTPalette;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTPaletteElement;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTParen;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTRule;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTRuleCompareOpExpression;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTRuleExpression;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTRuleLogicOpExpression;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTStatement;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.ASTVariable;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.NextFractalLexer;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar.NextFractalParser;

public class Compiler {
	private static final Logger logger = Logger.getLogger(Compiler.class.getName());
	private final String packageName;
	private final String className;
	private final String source;
	
	public Compiler(String packageName, String className, String source) {
		this.packageName = packageName;
		this.className = className;
		this.source = source;
	}

	public CompilerReport compile() throws Exception {
		ASTFractal ast = parse(source);
		String javaSource = compileToJava(ast);
		Fractal fractal = compileToClass(javaSource);
		return new CompilerReport(ast.toString(), javaSource, fractal);
	}

	private ASTFractal parse(String source) throws Exception {
		try {
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			NextFractalLexer lexer = new NextFractalLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			NextFractalParser parser = new NextFractalParser(tokens);
			lexer.addErrorListener(new ErrorListener());
//			parser.addErrorListener(new ErrorListener());
			ParseTree fractalTree = parser.fractal();
            if (fractalTree != null) {
            	ParseTreeWalker walker = new ParseTreeWalker();
            	walker.walk(new ParseTreeListener() {
					@Override
					public void visitTerminal(TerminalNode node) {
					}
					
					@Override
					public void visitErrorNode(ErrorNode node) {
					}
					
					@Override
					public void exitEveryRule(ParserRuleContext ctx) {
						logger.log(Level.FINE, ctx.getRuleContext().getClass().getSimpleName() + " " + ctx.getText());
					}
					
					@Override
					public void enterEveryRule(ParserRuleContext ctx) {
					}
				}, fractalTree);
            	ASTBuilder builder = parser.getBuilder();
            	ASTFractal fractal = builder.getFractal();
            	return fractal;
            }
            return null;
		}
		catch (Exception e) {
			throw new Exception("Parse error: " + e.getMessage(), e);
		}
	}

	private String compileToJava(ASTFractal fractal) throws Exception {
		StringBuilder builder = new StringBuilder();
		Map<String, CompilerVariable> variables = new HashMap<>();
		compile(builder, variables, fractal);
		return builder.toString();
	}
	
	private Fractal compileToClass(String source) throws IOException {
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new JavaSourceFromString(className, source));
		List<String> options = new ArrayList<>();
		options.addAll(Arrays.asList("-source", "1.8", "-target", "1.8", "-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			logger.log(Level.FINE, String.format("Error on line %d: %s\n", diagnostic.getLineNumber(), diagnostic.getMessage(null)));
		}
		if (diagnostics.getDiagnostics().size() == 0) {
			Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(className + ".class");
			Iterator<? extends JavaFileObject> iterator = files.iterator();
			if (iterator.hasNext()) {
				JavaFileObject file = files.iterator().next();
				byte[] fileData = loadBytes(file);
				JavaClassLoader loader = new JavaClassLoader(packageName + "." + className, fileData);
				try {
					Class<?> clazz = loader.loadClass(packageName + "." + className);
					Fractal fractal = (Fractal)clazz.newInstance();
					logger.log(Level.FINE, file.toUri().toString());
					logger.log(Level.FINE, clazz.getCanonicalName() + " (" + fileData.length + ")");
					return fractal;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				}
			}
		}
		fileManager.close();
		return null;
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

	private String compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		builder.append("package ");
		builder.append(packageName);
		builder.append(";\n");
		builder.append("import ");
		builder.append(Fractal.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Number.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Trap.class.getCanonicalName());
		builder.append(";\n");
		builder.append("import ");
		builder.append(Palette.class.getCanonicalName());
		builder.append(";\n");
		builder.append("public class ");
		builder.append(className);
		builder.append(" extends Fractal {\n");
		members(builder, variables, fractal);
		costructor(builder, fractal);
		methods(builder, variables, fractal);
		builder.append("}\n");
		return builder.toString();
	}

	private void costructor(StringBuilder builder, ASTFractal fractal) {
		builder.append("public ");
		builder.append(className);
		builder.append("() {\n");
		if (fractal != null) {
			for (CompilerVariable variable : fractal.getVariables()) {
				builder.append("registerVar(\"");
				builder.append(variable.getName());
				builder.append("\",() -> { return get");
				builder.append(variable.getName().toUpperCase());
				builder.append("(); });\n");
			}
		}
		builder.append("}\n");
	}

	private void members(StringBuilder builder, Map<String, CompilerVariable> variables, ASTFractal fractal) {
		if (fractal != null) {
			for (CompilerVariable variable : fractal.getVariables()) {
				variables.put(variable.getName(), variable);
				if (variable.isCreate()) {
					if (variable.isReal()) {
						builder.append("private double ");
						builder.append(variable.getName());
						builder.append(" = 0.0;\n");
						builder.append("private Number get");
						builder.append(variable.getName().toUpperCase());
						builder.append("() { return number(");
						builder.append(variable.getName());
						builder.append(",0); }\n");
					} else {
						builder.append("private Number ");
						builder.append(variable.getName());
						builder.append(" = number(0.0,0.0);\n");
						builder.append("private Number get");
						builder.append(variable.getName().toUpperCase());
						builder.append("() { return ");
						builder.append(variable.getName());
						builder.append("; }\n");
					}
				}
			}
		}
	}

	private void methods(StringBuilder builder,	Map<String, CompilerVariable> variables, ASTFractal fractal) {
		if (fractal != null) {
			compile(builder, variables, fractal.getOrbit());
			compile(builder, variables, fractal.getColor());
			builder.append("public void compute() {\n");
			builder.append("renderOrbit();\n");
			builder.append("renderColor();\n");
			builder.append("}\n");
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTColor color) {
		for (ASTPalette palette : color.getPalettes()) {
			compile(builder, variables, palette);
		}
		builder.append("private void renderColor() {\n");
		builder.append("addColor(1f,");
		builder.append(createArray(color.getArgb().getComponents()));
		builder.append(");\n");
		for (ASTRule rule : color.getRules()) {
			compile(builder, variables, rule);
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
		builder.append(" = palette(");
		builder.append(palette.getLength());
		builder.append(")");
		for (ASTPaletteElement element : palette.getElements()) {
			builder.append(".add(");
			compile(builder, variables, element);
			builder.append(")");
		}
		builder.append(".build();\n");
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTPaletteElement element) {
		builder.append("element(");
		builder.append(element.getBeginIndex());
		builder.append(",");
		builder.append(element.getEndIndex());
		builder.append(",");
		builder.append(createArray(element.getBeginColor().getComponents()));
		builder.append(",");
		builder.append(createArray(element.getEndColor().getComponents()));
		builder.append(",(start, end, step) -> { return ");
		if (element.getExp() != null) {
			if (element.getExp().isReal()) {
				element.getExp().compile(new ExpressionCompiler(builder));
			} else {
				throw new RuntimeException("Expression type not valid: " + element.getLocation().getText() + " [" + element.getLocation().getLine() + ":" + element.getLocation().getCharPositionInLine() + "]");
			}
		} else {
			builder.append("step / (end - start)");
		}
		builder.append(";})");
	}

	private String createArray(float[] components) {
		return "new float[] {" + components[0] + "f," + components[1] + "f," + components[2] + "f," + components[3] + "f}";
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbit orbit) {
		for (ASTOrbitTrap trap : orbit.getTraps()) {
			compile(builder, variables, trap);
		}
		builder.append("private void renderOrbit() {\n");
		compile(builder, variables, orbit.getBegin());
		compile(builder, variables, orbit.getLoop());
		compile(builder, variables, orbit.getEnd());
		builder.append("}\n");
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

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitBegin begin) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitEnd end) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, CompilerVariable> variables, ASTOrbitLoop loop) {
		if (loop != null) {
			builder.append("for (int i = ");
			builder.append(loop.getBegin());
			builder.append("; i < ");
			builder.append(loop.getEnd());
			builder.append("; i++) {\n");
			builder.append("n = number(i);\n");
			for (ASTStatement statement : loop.getStatements()) {
				compile(builder, variables, statement);
			}
			builder.append("if (");
			loop.getExpression().compile(new ExpressionCompiler(builder));
			builder.append(") break;\n");
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
					throw new RuntimeException("Expression not assignable: " + statement.getLocation().getText() + " [" + statement.getLocation().getLine() + ":" + statement.getLocation().getCharPositionInLine() + "]");
				}
			}
		}		
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
					if (function.getArguments().length != 1) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
					
				case "sin":
				case "cos":
				case "tan":
				case "asin":
				case "acos":
				case "atan":
					if (function.getArguments().length != 1) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
	
				case "log":
					if (function.getArguments().length != 1) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					if (!function.getArguments()[0].isReal()) {
						throw new RuntimeException("Invalid type of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
					
				case "atan2":
				case "hypot":
					if (function.getArguments().length != 2) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					if (!function.getArguments()[0].isReal()) {
						throw new RuntimeException("Invalid type of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					if (!function.getArguments()[1].isReal()) {
						throw new RuntimeException("Invalid type of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
					
				case "pow":
					if (function.getArguments().length != 2) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					if (!function.getArguments()[1].isReal()) {
						throw new RuntimeException("Invalid type of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
	
				case "sqrt":
				case "exp":
				case "real":
					if (function.getArguments().length != 1) {
						throw new RuntimeException("Invalid number of arguments: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
					}				
					break;
					
				default:
					throw new RuntimeException("Unsupported function: " + function.getLocation().getText() + " [" + function.getLocation().getLine() + ":" + function.getLocation().getCharPositionInLine() + "]");
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
						throw new RuntimeException("Unsupported operator: " + operator.getLocation().getText() + " [" + operator.getLocation().getLine() + ":" + operator.getLocation().getCharPositionInLine() + "]");
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
							throw new RuntimeException("Unsupported operator: " + operator.getLocation().getText() + " [" + operator.getLocation().getLine() + ":" + operator.getLocation().getCharPositionInLine() + "]");
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
							throw new RuntimeException("Unsupported operator: " + operator.getLocation().getText() + " [" + operator.getLocation().getLine() + ":" + operator.getLocation().getCharPositionInLine() + "]");
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
							throw new RuntimeException("Unsupported operator: " + operator.getLocation().getText() + " [" + operator.getLocation().getLine() + ":" + operator.getLocation().getCharPositionInLine() + "]");
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
						throw new RuntimeException("Unsupported operator: " + compareOp.getLocation().getText() + " [" + compareOp.getLocation().getLine() + ":" + compareOp.getLocation().getCharPositionInLine() + "]");
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
					throw new RuntimeException("Unsupported operator: " + logicOp.getLocation().getText() + " [" + logicOp.getLocation().getLine() + ":" + logicOp.getLocation().getCharPositionInLine() + "]");
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
					throw new RuntimeException("Unsupported operator: " + logicOp.getLocation().getText() + " [" + logicOp.getLocation().getLine() + ":" + logicOp.getLocation().getCharPositionInLine() + "]");
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
						throw new RuntimeException("Unsupported operator: " + compareOp.getLocation().getText() + " [" + compareOp.getLocation().getLine() + ":" + compareOp.getLocation().getCharPositionInLine() + "]");
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
				throw new RuntimeException("Expression type not valid: " + palette.getLocation().getText() + " [" + palette.getLocation().getLine() + ":" + palette.getLocation().getCharPositionInLine() + "]");
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

	private class JavaSourceFromString extends SimpleJavaFileObject {
        private final String code;

        public JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
	
	private class JavaClassLoader extends ClassLoader {
		public JavaClassLoader(String name, byte[] data) {
			Class<?> clazz = defineClass(name, data, 0, data.length);
			resolveClass(clazz);
		}
	}
	
	private class ErrorListener extends DiagnosticErrorListener {
		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
			logger.log(Level.WARNING, "[" + line + ":" + charPositionInLine + "] " + msg, e);
			super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
		}
	}
}	
