/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.java.JavaClassCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.java.JavaReportCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterClassCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterReportCompiler;

public class Compiler {
	private final String packageName;
	private final String className;
	
	public Compiler() {
		this(Compiler.class.getPackage().getName(), Compiler.class.getSimpleName());
	}
	
	public Compiler(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public CompilerReport compileReport(String source) throws IOException {
		JavaCompiler javaCompiler = getJavaCompiler();
		if (javaCompiler == null) {
			InterpreterReportCompiler compiler = new InterpreterReportCompiler();
			return compiler.generateReport(source);
		} else {
			JavaReportCompiler compiler = new JavaReportCompiler(packageName, className);
			return compiler.generateReport(source);
		}
	}
	
	public CompilerBuilder<Orbit> compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException {
		JavaCompiler javaCompiler = getJavaCompiler();
		if (javaCompiler == null) {
			InterpreterClassCompiler compiler = new InterpreterClassCompiler();
			return compiler.compileOrbit(report);
		} else {
			JavaClassCompiler compiler = new JavaClassCompiler(packageName, className);
			return compiler.compileOrbit(report);
		}
	}

	public CompilerBuilder<Color> compileColor(CompilerReport report) throws ClassNotFoundException, IOException {
		JavaCompiler javaCompiler = getJavaCompiler();
		if (javaCompiler == null) {
			InterpreterClassCompiler compiler = new InterpreterClassCompiler();
			return compiler.compileColor(report);
		} else {
			JavaClassCompiler compiler = new JavaClassCompiler(packageName, className);
			return compiler.compileColor(report);
		}
	}

	public JavaCompiler getJavaCompiler() {
		return !Boolean.getBoolean("mandelbrot.compiler.disabled") ? ToolProvider.getSystemJavaCompiler() : null;
	}
}	
