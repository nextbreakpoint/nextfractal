package com.nextbreakpoint.nextfractal.flux.grammar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.nextbreakpoint.nextfractal.flux.Fractal;
import com.nextbreakpoint.nextfractal.flux.Variable;

public class ASTJavaCompiler {
	private final ASTFractal fractal;
	private final String packageName;
	private final String className;
	
	public ASTJavaCompiler(ASTFractal fractal, String packageName, String className) {
		this.fractal = fractal;
		this.packageName = packageName;
		this.className = className;
	}

	public Fractal compile() throws IOException {
		StringBuilder builder = new StringBuilder();
		Map<String, Variable> variables = new HashMap<>();
		compile(builder, variables, fractal);
		String source = builder.toString();
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new JavaSourceFromString(className, source));
		List<String> options = new ArrayList<>();
		options.addAll(Arrays.asList("-source", "1.8", "-target", "1.8", "-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			System.out.format("Error on line %d: %s\n", diagnostic.getLineNumber(), diagnostic.getMessage(null));
		}
		Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(className + ".class");
		Iterator<? extends JavaFileObject> iterator = files.iterator();
		if (iterator.hasNext()) {
			JavaFileObject file = files.iterator().next();
			byte[] fileData = loadBytes(file);
			JavaClassLoader loader = new JavaClassLoader(packageName + "." + className, fileData);
			try {
				Class<?> clazz = loader.loadClass(packageName + "." + className);
				Fractal fractal = (Fractal)clazz.newInstance();
				fractal.setSourceCode(source);
				System.out.println(file.toUri().toString());
				System.out.println(clazz.getCanonicalName() + " (" + fileData.length + ")");
				return fractal;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
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

	private String compile(StringBuilder builder, Map<String, Variable> variables, ASTFractal fractal) {
		builder.append("package ");
		builder.append(packageName);
		builder.append(";\n");
		builder.append("import com.nextbreakpoint.nextfractal.flux.Fractal;\n");
		builder.append("import com.nextbreakpoint.nextfractal.flux.Number;\n");
		builder.append("import com.nextbreakpoint.nextfractal.flux.Variable;\n");
		builder.append("public class ");
		builder.append(className);
		builder.append(" extends Fractal {\n");
		builder.append("public Number compute(Number x, Number w) {\n");
		if (fractal != null) {
			for (Variable variable : fractal.getVariables()) {
				variables.put(variable.getName(), variable);
				if (variable.isReal()) {
					builder.append("double ");
					builder.append(variable.getName());
					builder.append(" = 0.0;\n");
				} else {
					builder.append("Number ");
					builder.append(variable.getName());
					builder.append(" = number(0.0,0.0);\n");
				}
			}
			compile(builder, variables, fractal.getOrbit());
			//TODO color
		}
		builder.append("return c;\n");
		builder.append("}\n");
		builder.append("}\n");
		return builder.toString();
	}

	private void compile(StringBuilder builder, Map<String, Variable> variables, ASTOrbit orbit) {
		if (orbit != null) {
			//TODO trap
			compile(builder, variables, orbit.getBegin());
			builder.append("for (int i = ");
			builder.append(orbit.getLoop().getBegin());
			builder.append("; i < ");
			builder.append(orbit.getLoop().getEnd());
			builder.append("; i++) {\n");
			builder.append("n = number(i);\n");
			//TODO projection
			compile(builder, variables, orbit.getLoop());
			//TODO condition
			builder.append("}\n");
			builder.append("c = number(0xFF000000);\n");
			compile(builder, variables, orbit.getEnd());
		}
	}

	private void compile(StringBuilder builder, Map<String, Variable> variables, ASTOrbitBegin begin) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, Variable> variables, ASTOrbitEnd end) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, Variable> variables, ASTOrbitLoop loop) {
		if (loop != null) {
			//TODO for
			for (ASTStatement statement : loop.getStatements()) {
				compile(builder, variables, statement);
			}
		}
	}

	private void compile(StringBuilder builder, Map<String, Variable> variables, ASTStatement statement) {
		if (statement != null) {
			Variable var = variables.get(statement.getName());
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
							builder.append("opNegReal");
						} else {
							builder.append("opNeg");
						}
						break;
					
					default:
						if (exp1.isReal()) {
							builder.append("opPosReal");
						} else {
							builder.append("opPos");
						}
						break;
				}
				builder.append("(");
				exp1.compile(this);
				builder.append(")");
			} else {
				if (exp1.isReal() && exp2.isReal()) {
					switch (operator.getOp()) {
						case "+":
							builder.append("opAddReal");
							break;
						
						case "-":
							builder.append("opSubReal");
							break;
							
						case "*":
							builder.append("opMulReal");
							break;
							
						case "/":
							builder.append("opDivReal");
							break;
							
						case "^":
							builder.append("opPowReal");
							break;
						
						default:
							break;
					}
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
							
						case "^":
							builder.append("opPow");
							break;
						
						default:
							break;
					}
				}
				builder.append("(");
				exp1.compile(this);
				builder.append(",");
				exp2.compile(this);
				builder.append(")");
			}
		}

		@Override
		public void compile(ASTParen paren) {
			paren.getExp().compile(this);
		}

		@Override
		public void compile(ASTVariable variable) {
			builder.append(variable.getName());
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
}	
