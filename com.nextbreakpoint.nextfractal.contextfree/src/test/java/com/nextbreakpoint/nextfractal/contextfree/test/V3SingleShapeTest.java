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
package com.nextbreakpoint.nextfractal.contextfree.test;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Shape;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.nextbreakpoint.nextfractal.contextfree.grammar.ECompilePhase.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class V3SingleShapeTest extends AbstractBaseTest {
	public static final String RESOURCE_NAME = "/v3-single-shape.cfdg";

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldParseSource() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		assertThat(cfdg, is(notNullValue()));
	}

	@Test
	public void shouldHaveTwoShapes() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		assertThat(cfdg.tryEncodeShapeName("Foo"), is(equalTo(0)));
		assertThat(cfdg.tryEncodeShapeName("SQUARE"), is(equalTo(1)));
	}

	@Test
	public void shouldCompleteTypeCheck() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		cfdg.compile(TypeCheck);
	}

	@Test
	public void shouldCompleteSimplify() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		cfdg.compile(Simplify);
	}

	@Test
	public void shouldTraverse() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		cfdg.compile(Simplify);
//		cfdg.traverse(new Shape(), false, new RTI());
	}
}
