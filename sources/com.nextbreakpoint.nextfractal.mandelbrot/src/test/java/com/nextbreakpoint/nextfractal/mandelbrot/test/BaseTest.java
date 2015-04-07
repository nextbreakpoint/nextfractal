/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.mandelbrot.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotLexer;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.MandelbrotParser;

public abstract class BaseTest {
	protected ASTFractal parse(String source) throws Exception {
		ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
		MandelbrotLexer lexer = new MandelbrotLexer(is);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		lexer.addErrorListener(new CompilerErrorListener());
		MandelbrotParser parser = new MandelbrotParser(tokens);
		parser.addErrorListener(new CompilerErrorListener());
		ParseTree fractalTree = parser.fractal();
        if (fractalTree != null) {
        	ASTBuilder builder = parser.getBuilder();
        	ASTFractal fractal = builder.getFractal();
        	return fractal;
        }
        return null;
	}
	
	protected void printErrors(List<CompilerError> errors) {
		for (CompilerError error : errors) {
			System.out.println(error.toString());
		}
	}

	private class CompilerErrorListener extends DiagnosticErrorListener {
		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
			System.out.println("[" + line + ":" + charPositionInLine + "] " + msg);
			super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
		}
	}

	protected String getSource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int length = 0;
		while ((length = is.read(buffer)) > 0) {
			baos.write(buffer, 0, length);
		}
		return baos.toString();
	}
}