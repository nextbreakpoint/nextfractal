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

import org.junit.Test;

public class GrammarTest2 extends BaseGrammarTest {
	@Test
	public void TestGrammar() {
		parse();
	}

	@Override
	protected String getSource() {
		String source = ""
				+ "orbit [-1 - 1i,+1 + 1i] {"
				+ "trap trap1 [0] {"
				+ "MOVETO(1);"
				+ "LINETO(2);"
				+ "LINETO(2 + 2i);"
				+ "LINETO(1 + 2i);"
				+ "LINETO(1);"
				+ "}"
				+ "loop [1, 1000] {"
				+ "z = x * y + 5;"
				+ "}"
				+ "condition {"
				+ "|z| > 4 & trap1[z]"
				+ "}"
				+ "} color [#000000] {"
				+ "palette palette1 [200] {"
				+ "[0, #000000] > [100, #FFFFFF];"
				+ "[101, #FFFFFF] > [200, #FF0000];"
				+ "}"
				+ "rule (n = 0) [1.0] {"
				+ "x,5,5,5"
				+ "}"
				+ "rule (n > 0) [1.0] {"
				+ "palette1[n]"
				+ "}"
				+ "}";
		return source;
	}
}
