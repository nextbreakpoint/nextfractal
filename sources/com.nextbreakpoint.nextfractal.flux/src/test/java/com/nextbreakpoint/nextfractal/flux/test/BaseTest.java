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

import com.nextbreakpoint.nextfractal.flux.grammar.ASTBuilder;
import com.nextbreakpoint.nextfractal.flux.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.flux.grammar.NextFractalLexer;
import com.nextbreakpoint.nextfractal.flux.grammar.NextFractalParser;

public abstract class BaseTest {
	protected ASTFractal parse() {
		try {
			String source = getSource();
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			NextFractalLexer lexer = new NextFractalLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			NextFractalParser parser = new NextFractalParser(tokens);
			ParseTree fractalTree = parser.fractal();
            if (fractalTree != null) {
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
				}, fractalTree);
            	ASTBuilder builder = parser.getBuilder();
            	ASTFractal fractal = builder.getFractal();
            	return fractal;
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected abstract String getSource();
}