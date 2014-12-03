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

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.flux.Fractal;
import com.nextbreakpoint.nextfractal.flux.FractalVariable;
import com.nextbreakpoint.nextfractal.flux.Number;
import com.nextbreakpoint.nextfractal.flux.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.flux.grammar.ASTJavaCompiler;

public class CompilerTest1 extends BaseTest {
	@Test
	public void TestCompiler() {
		try {
//			Assert.assertTrue(Pattern.matches("([A-Z][a-z]*)-(\\d).(.jpg|.png)", "Andrea-10.png"));
			ASTFractal astFractal = parse();
			System.out.println(astFractal);
			ASTJavaCompiler compiler = new ASTJavaCompiler(astFractal, "test", "TestFractal");
			String source = compiler.compileToJava();
			Assert.assertNotNull(source);
			System.out.println(source);
			Fractal fractal = compiler.compileToClass(source);
			Assert.assertNotNull(fractal);
			fractal.setX(new Number(0, 0));
			fractal.setW(new Number(0, 0));
			fractal.compute();
			float[] c = fractal.color();
			Assert.assertNotNull(c);
			System.out.println(String.format("%f,%f,%f,%f", c[0], c[1], c[2], c[3]));
			FractalVariable z = fractal.getVar("z");
			Assert.assertNotNull(z);
			System.out.println(String.format("%f,%f", z.get().r(), z.get().i()));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Override
	protected String getSource() {
		String source = ""
				+ "fractal {"
				+ "orbit [-1 - 1i,+1 + 1i] {"
				+ "trap trap1 [0] {"
				+ "MOVETO(1);"
				+ "LINETO(2);"
				+ "LINETO(2 + 2i);"
				+ "LINETO(1 + 2i);"
				+ "LINETO(1);"
				+ "}"
				+ "loop [1, 2] (|z| > 4 & trap1[z]) {"
				+ "y = 0;"
				+ "t = 3;"
				+ "x = t + 4 + 1i;"
				+ "k = t + 4;"
				+ "z = x * (y + 5i);"
				+ "t = |z|;"
				+ "}"
				+ "} color [#FF000000] {"
				+ "palette palette1 [200] {"
				+ "[0, #000000] > [100, #FFFFFF];"
				+ "[101, #FFFFFF] > [200, #FF0000];"
				+ "}"
				+ "rule (real(n) = 0) [1.0] {"
				+ "|x|,5,5,5"
				+ "}"
				+ "rule (real(n) > 0) [1.0] {"
				+ "palette1[real(n)]"
				+ "}"
				+ "}"
				+ "}";
		return source;
	}
}
