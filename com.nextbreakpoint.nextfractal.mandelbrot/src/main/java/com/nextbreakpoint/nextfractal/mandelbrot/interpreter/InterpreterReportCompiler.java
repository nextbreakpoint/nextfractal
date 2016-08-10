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
package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerErrorStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport.Type;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotLexer;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotParser;

public class InterpreterReportCompiler {
	private static final Logger logger = Logger.getLogger(InterpreterReportCompiler.class.getName());
	
	public CompilerReport generateReport(String source) throws IOException {
		List<CompilerError> errors = new ArrayList<>();
		ASTFractal ast = parse(source, errors);
		return new CompilerReport(ast, Type.INTERPRETER, "", "", errors);
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
}	
