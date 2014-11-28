package com.nextbreakpoint.nextfractal.flux.grammar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.nextbreakpoint.nextfractal.flux.Fractal;

public class ASTJavaCompiler {
	private ASTFractal fractal;
	
	public ASTJavaCompiler(ASTFractal fractal) {
		this.fractal = fractal;
	}

	public Fractal compile() throws IOException {
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
		Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects("Fractal75.class");
		Iterator<? extends JavaFileObject> iterator = files.iterator();
		if (iterator.hasNext()) {
			JavaFileObject file = files.iterator().next();
			byte[] fileData = loadBytes(file);
			JavaClassLoader loader = new JavaClassLoader("com.nextbreakpoint.nextfractal.flux.Fractal75", fileData);
			try {
				Class<?> clazz = loader.loadClass("com.nextbreakpoint.nextfractal.flux.Fractal75");
				Fractal fractal = (Fractal)clazz.newInstance();
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
