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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError.ErrorType;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;

public class JavaClassCompiler {
	private static final Logger logger = Logger.getLogger(JavaClassCompiler.class.getName());
	private final String packageName;
	private final String className;
	
	public JavaClassCompiler(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public CompilerBuilder<Orbit> compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		try {
			Class<Orbit> clazz = compileToClass(report.getOrbitSource(), className + "Orbit", Orbit.class, errors);
			return new JavaClassBuilder<Orbit>(clazz, errors);
		} catch (Throwable e) {
			errors.add(new CompilerError(ErrorType.JAVA_COMPILER, 0, 0, 0, 0, e.getMessage()));
			return new JavaClassBuilder<Orbit>(null, errors);
		}
	}

	public CompilerBuilder<Color> compileColor(CompilerReport report) throws ClassNotFoundException, IOException {
		List<CompilerError> errors = new ArrayList<>();
		try {
			Class<Color> clazz = compileToClass(report.getColorSource(), className + "Color", Color.class, errors);
			return new JavaClassBuilder<Color>(clazz, errors);
		} catch (Throwable e) {
			errors.add(new CompilerError(ErrorType.JAVA_COMPILER, 0, 0, 0, 0, e.getMessage()));
			return new JavaClassBuilder<Color>(null, errors);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> Class<T> compileToClass(String source, String className, Class<T> clazz, List<CompilerError> errors) throws IOException, ClassNotFoundException {
		logger.log(Level.FINE, "Compile Java source:\n" + source);
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new JavaSourceFileObject(className, source));
		List<String> options = new ArrayList<>();
//		options.addAll(Arrays.asList("-source", "1.8", "-target", "1.8", "-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		options.addAll(Arrays.asList("-proc:none", "-Xdiags:verbose", "-classpath", System.getProperty("java.class.path")));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String fullClassName = packageName + "." + className;
		JavaFileManager fileManager = new JavaCompilerFileManager(compiler.getStandardFileManager(diagnostics, null, null), fullClassName);
		try {
			compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				CompilerError error = new CompilerError(CompilerError.ErrorType.JAVA_COMPILER, diagnostic.getLineNumber(), diagnostic.getColumnNumber(), diagnostic.getStartPosition(), diagnostic.getEndPosition() - diagnostic.getStartPosition(), diagnostic.getMessage(null));
				logger.log(Level.FINE, error.toString());
				errors.add(error);
			}
			if (diagnostics.getDiagnostics().size() == 0) {
				JavaCompilerClassLoader loader = new JavaCompilerClassLoader();
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

	private void defineClasses(JavaFileManager fileManager, JavaCompilerClassLoader loader, String className) throws IOException {
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
}	
