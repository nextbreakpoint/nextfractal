/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.contextfree.test;

import java.io.FileReader;

import com.nextbreakpoint.nextfractal.contextfree.parser.CFDGLexer;
import com.nextbreakpoint.nextfractal.contextfree.parser.CFDGParser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

public class TestCF3Parser {
	@Test
	public void parse() {
		try {
			ANTLRInputStream is = new ANTLRInputStream(new FileReader("cfdg/test1.cfdg"));
			CFDGLexer lexer = new CFDGLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CFDGParser parser = new CFDGParser(tokens);
			ParseTree choose = parser.choose();
            if (choose != null) {
            	ParseTreeWalker walker = new ParseTreeWalker();
            	walker.walk(new ParseTreeListener() {
					@Override
					public void visitTerminal(TerminalNode node) {
						System.out.println(node.getText() + " " + node.getSymbol());
					}
					
					@Override
					public void visitErrorNode(ErrorNode node) {
//						System.out.println(node.getText() + " " + node.getSymbol());
					}
					
					@Override
					public void exitEveryRule(ParserRuleContext ctx) {
//						System.out.println(ctx.getText());
					}
					
					@Override
					public void enterEveryRule(ParserRuleContext ctx) {
//						System.out.println(ctx.getText());
					}
				}, choose);
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
