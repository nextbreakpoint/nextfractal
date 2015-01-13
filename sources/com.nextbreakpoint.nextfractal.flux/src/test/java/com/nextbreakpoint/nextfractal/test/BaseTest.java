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
package com.nextbreakpoint.nextfractal.test;

import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.NextFractalLexer;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.NextFractalParser;

public abstract class BaseTest {
	protected ASTFractal parse(String source) throws Exception {
		try {
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			NextFractalLexer lexer = new NextFractalLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			NextFractalParser parser = new NextFractalParser(tokens);
			ParseTree fractalTree = parser.fractal();
            if (fractalTree != null) {
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
}