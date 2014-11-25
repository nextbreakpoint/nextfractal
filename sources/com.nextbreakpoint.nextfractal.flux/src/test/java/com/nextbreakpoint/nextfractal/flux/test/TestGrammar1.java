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
package com.nextbreakpoint.nextfractal.flux.test;

import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.flux.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.flux.grammar.ASTColor;
import com.nextbreakpoint.nextfractal.flux.grammar.ASTOrbit;
import com.nextbreakpoint.nextfractal.flux.grammar.NextFractalLexer;
import com.nextbreakpoint.nextfractal.flux.grammar.NextFractalParser;

public class TestGrammar1 {
	@Test
	public void parse() {
		try {
			String source = ""
					+ "orbit [-1 - 1i,+1 + 1i] {"
					+ "loop [1, 1000] {"
					+ "z = 5 + |4i| * x ^ 2 i + w + sin(5i);"
					+ "x = z;"
					+ "}"
					+ "condition {"
					+ "|z| > 4"
					+ "}"
					+ "} color [#000000] {"
					+ "}";
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			NextFractalLexer lexer = new NextFractalLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			NextFractalParser parser = new NextFractalParser(tokens);
			ParseTree root = parser.root();
            if (root != null) {
            	ParseTreeWalker walker = new ParseTreeWalker();
            	walker.walk(new ParseTreeListener() {
					@Override
					public void visitTerminal(TerminalNode node) {
//						System.out.println(node.getText() + " " + node.getSymbol());
					}
					
					@Override
					public void visitErrorNode(ErrorNode node) {
					}
					
					@Override
					public void exitEveryRule(ParserRuleContext ctx) {
						System.out.println(ctx.getRuleContext().getClass().getSimpleName() + " " + ctx.getText());
					}
					
					@Override
					public void enterEveryRule(ParserRuleContext ctx) {
					}
				}, root);
            	ASTBuilder builder = parser.getBuilder();
            	ASTOrbit orbit = builder.getOrbit();
            	ASTColor color = builder.getColor();
            	System.out.println(orbit);
            	System.out.println(color);
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
