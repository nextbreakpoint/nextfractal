/*
 * NextFractal 2.2.0
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
package com.nextbreakpoint.nextfractal.mandelbrot.test;

import com.nextbreakpoint.nextfractal.mandelbrot.core.*;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.DSLCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.ClassFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.DSLParser;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.ParserResult;
import org.junit.Assert;
import org.junit.Test;

public class DSLCompilerTest1 extends BaseTest {
	@Test
	public void Compiler1() {
		try {
//			Assert.assertTrue(Pattern.matches("([A-Z][a-z]*)-(\\d).(.jpg|.png)", "Andrea-10.png"));
			DSLParser parser = new DSLParser("test", "Compile");
			ParserResult result = parser.parse(getSource("/source1.m"));
			Assert.assertEquals(0, result.getErrors().size());
			Assert.assertNotNull(result.getAST());
			System.out.println(result.getAST());
			Assert.assertNotNull(result.getOrbitSource());
			System.out.println(result.getOrbitSource());
			Assert.assertNotNull(result.getColorSource());
			System.out.println(result.getColorSource());
			DSLCompiler compiler = new DSLCompiler();
			Orbit orbit = compiler.compileOrbit(result).create();
			Color color = compiler.compileColor(result).create();
			Assert.assertNotNull(orbit);
			Assert.assertNotNull(color);
			Scope scope = new Scope();
			orbit.setScope(scope);
			color.setScope(scope);
			orbit.init();
			orbit.setX(new Number(0, 0));
			orbit.setW(new Number(0, 0));
			orbit.render(null);
			float[] c = color.getColor();
			Assert.assertNotNull(c);
			System.out.println(String.format("%f,%f,%f,%f", c[0], c[1], c[2], c[3]));
			Number z = orbit.getVariable(0);
			Assert.assertNotNull(z);
			System.out.println(String.format("%f,%f", z.r(), z.i()));
		} catch (CompilerException e) {
			printErrors(e.getErrors());
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (ParserException e) {
			printErrors(e.getErrors());
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
