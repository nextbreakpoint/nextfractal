/*
 * NextFractal 2.1.4
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2022 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.dsl.javacompiler;

import com.nextbreakpoint.nextfractal.core.common.SourceError;
import com.nextbreakpoint.nextfractal.mandelbrot.core.CompilerException;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.ClassFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.ParserResult;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaCompilerDSLCompiler {
	private static final Logger logger = Logger.getLogger(JavaCompilerDSLCompiler.class.getName());

	public ClassFactory<Orbit> compileOrbit(ParserResult report) throws CompilerException {
		List<SourceError> errors = new ArrayList<>();
		try {
			Class<Orbit> clazz = compileToClass(report.getOrbitSource(), report.getPackageName(), report.getClassName() + "Orbit", Orbit.class, errors);
			return new JavaClassFactory<>(clazz);
		} catch (Throwable e) {
			SourceError.ErrorType type = SourceError.ErrorType.JAVA_COMPILER;
			String message = e.getMessage();
			errors.add(new SourceError(type, 0, 0, 0, 0, message));
			throw new CompilerException("Can't compile orbit", report.getOrbitSource(), errors);
		}
	}

	public ClassFactory<Color> compileColor(ParserResult report) throws CompilerException {
		List<SourceError> errors = new ArrayList<>();
		try {
			Class<Color> clazz = compileToClass(report.getColorSource(), report.getPackageName(), report.getClassName() + "Color", Color.class, errors);
			return new JavaClassFactory<>(clazz);
		} catch (Throwable e) {
			SourceError.ErrorType type = SourceError.ErrorType.JAVA_COMPILER;
			String message = e.getMessage();
			errors.add(new SourceError(type, 0, 0, 0, 0, message));
			throw new CompilerException("Can't compile color", report.getColorSource(), errors);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> Class<T> compileToClass(String source, String packageName, String className, Class<T> clazz, List<SourceError> errors) throws CompilerException {
		logger.log(Level.FINE, "Compile Java source:\n" + source);
		List<SimpleJavaFileObject> compilationUnits = new ArrayList<>();
		compilationUnits.add(new JavaSourceFileObject(className, source));
		final String modulePath = System.getProperty("nextfractal.module.path", System.getProperty("jdk.module.path"));
		List<String> options = Arrays.asList(
				"-source",
				"11",
				"-target",
				"11",
				"-proc:none",
				"-Xdiags:verbose",
				"--module-path",
				modulePath,
				"--add-modules",
				"com.nextbreakpoint.nextfractal.mandelbrot"
		);
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		String fullClassName = packageName + "." + className;
		JavaFileManager fileManager = new JavaCompilerFileManager(compiler.getStandardFileManager(diagnostics, null, null), fullClassName);
		try {
			final JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
			if (task.call()) {
				JavaCompilerClassLoader loader = new JavaCompilerClassLoader();
				defineClasses(fileManager, loader, packageName, className);
				Class<?> compiledClazz = loader.loadClass(packageName + "." + className);
				logger.log(Level.FINE, compiledClazz.getCanonicalName());
				if (clazz.isAssignableFrom(compiledClazz)) {
					return (Class<T>) compiledClazz;
				}
			} else {
				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
					if (diagnostic.getCode().equals("compiler.err.cant.access")) {
						// TODO Not sure why it doesn't happen with Java 8, but only with Java 9.
						SourceError.ErrorType type = SourceError.ErrorType.JAVA_COMPILER;
						long line = diagnostic.getLineNumber();
						long charPositionInLine = diagnostic.getColumnNumber();
						long index = diagnostic.getStartPosition();
						long length = diagnostic.getEndPosition() - diagnostic.getStartPosition();
						String message = diagnostic.getMessage(null);
						SourceError error = new SourceError(type, line, charPositionInLine, index, length, message);
						logger.log(Level.WARNING, error.toString());
						errors.add(error);
					} else {
						SourceError.ErrorType type = SourceError.ErrorType.JAVA_COMPILER;
						long line = diagnostic.getLineNumber();
						long charPositionInLine = diagnostic.getColumnNumber();
						long index = diagnostic.getStartPosition();
						long length = diagnostic.getEndPosition() - diagnostic.getStartPosition();
						String message = diagnostic.getMessage(null);
						SourceError error = new SourceError(type, line, charPositionInLine, index, length, message);
						logger.log(Level.FINE, error.toString());
						errors.add(error);
					}
				}
			}
		} catch (Exception e) {
			SourceError.ErrorType type = SourceError.ErrorType.JAVA_COMPILER;
			String message = e.getMessage();
			SourceError error = new SourceError(type, 0, 0, 0, 0, message);
			logger.log(Level.SEVERE, e.getMessage());
			errors.add(error);
			throw new CompilerException("Can't compile class", source, errors);
		} finally {
			try {
				fileManager.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	private void defineClasses(JavaFileManager fileManager, JavaCompilerClassLoader loader, String packageName, String className) throws IOException {
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
