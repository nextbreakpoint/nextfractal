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
package com.nextbreakpoint.nextfractal.mandelbrot.test;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;

public class CompilerTest1 extends BaseTest {
	@Test
	public void Compiler1() {
		try {
//			Assert.assertTrue(Pattern.matches("([A-Z][a-z]*)-(\\d).(.jpg|.png)", "Andrea-10.png"));
			Compiler compiler = new Compiler();
			CompilerReport report = compiler.compileReport(getSource("/source1.m"));
			printErrors(report.getErrors());
			Assert.assertEquals(0, report.getErrors().size());
			Assert.assertNotNull(report.getAST());
			System.out.println(report.getAST());
			Assert.assertNotNull(report.getOrbitSource());
			System.out.println(report.getOrbitSource());
			Assert.assertNotNull(report.getColorSource());
			System.out.println(report.getColorSource());
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			printErrors(orbitBuilder.getErrors());
			Assert.assertEquals(0, orbitBuilder.getErrors().size());
			Assert.assertNotNull(orbitBuilder);
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			printErrors(colorBuilder.getErrors());
			Assert.assertEquals(0, colorBuilder.getErrors().size());
			Assert.assertNotNull(colorBuilder);
			Orbit orbit = orbitBuilder.build();
			Color color = colorBuilder.build();
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
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
