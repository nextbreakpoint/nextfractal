package com.nextbreakpoint.nextfractal.flux.grammar;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ASTJavaCompiler {
	private ASTFractal fractal;
	
	public ASTJavaCompiler(ASTFractal fractal) {
		this.fractal = fractal;
	}

	public Class<?> compile() throws IOException {
		StringBuilder builder = new StringBuilder();
		compile(builder, fractal);
		String source = builder.toString();
		
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new JavaSourceFromString("Fractal75", source));
		List<String> options = new ArrayList<>();
		options.addAll(Arrays.asList("-source", "1.8", "-target", "1.8", "-proc:none", "-classpath", System.getProperty("java.class.path")));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			System.out.format("Error on line %d: %s\n", diagnostic.getLineNumber(), diagnostic.getMessage(null));
		}
		fileManager.close();
//		FileObject out = fileManager.getFileForOutput(StandardLocation.CLASS_OUTPUT, "com.nextbreakpoint.nextfractal.flux", "Fractal75", null);
//		CharSequence content = out.getCharContent(false);
//		System.out.println(content.toString());
		return null;
	}

	private String compile(StringBuilder builder, ASTFractal fractal) {
		builder.append("package com.nextbreakpoint.nextfractal.flux;\n");
		builder.append("import com.nextbreakpoint.nextfractal.flux.Fractal;\n");
		builder.append("import com.nextbreakpoint.nextfractal.core.math.Complex;\n");
		builder.append("public class Fractal75 extends Fractal {\n");
		builder.append("public int renderPoint(Complex z, Complex w) {\n");
		if (fractal != null) {
			compile(builder, fractal.getOrbit());
			//TODO color
		}
		builder.append("return _c;\n");
		builder.append("}\n");
		builder.append("}\n");
		return builder.toString();
	}

	private void compile(StringBuilder builder, ASTOrbit orbit) {
		if (orbit != null) {
			//TODO trap
			builder.append("Complex _z = new Complex(0, 0);\n");
			builder.append("Complex _w = new Complex(0, 0);\n");
			compile(builder, orbit.getBegin());
			builder.append("for (int i = ");
			builder.append(orbit.getLoop().getBegin());
			builder.append("; i < ");
			builder.append(orbit.getLoop().getEnd());
			builder.append("; i++) {\n");
			//TODO projection
//			compile(builder, orbit.getLoop());
			//TODO condition
			builder.append("}\n");
			builder.append("int _c = 0xFF000000;\n");
			compile(builder, orbit.getEnd());
		}
	}

	private void compile(StringBuilder builder, ASTOrbitBegin begin) {
		if (begin != null) {
			for (ASTStatement statement : begin.getStatements()) {
				compile(builder, statement);
			}
		}
	}

	private void compile(StringBuilder builder, ASTOrbitEnd end) {
		if (end != null) {
			for (ASTStatement statement : end.getStatements()) {
				compile(builder, statement);
			}
		}
	}

	private void compile(StringBuilder builder, ASTOrbitLoop loop) {
		if (loop != null) {
			//TODO for
			for (ASTStatement statement : loop.getStatements()) {
				compile(builder, statement);
			}
		}
	}

	private void compile(StringBuilder builder, ASTStatement statement) {
		if (statement != null) {
			builder.append("Complex ");
			builder.append(statement.getName());
			builder.append("=");
			statement.getExp().compile(builder);
			builder.append(";\n");
		}		
	}
	
	public class JavaSourceFromString extends SimpleJavaFileObject {
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
}	
