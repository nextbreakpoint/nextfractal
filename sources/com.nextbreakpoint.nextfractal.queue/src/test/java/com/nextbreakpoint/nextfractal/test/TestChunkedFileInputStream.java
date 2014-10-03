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

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.queue.io.ChunkedFileInputStream;

public class TestChunkedFileInputStream {
	@Test
	public void test() {
		try {
			ChunkedFileInputStream is = new ChunkedFileInputStream(new File("c:"), "test", ".bin", 2048);
			byte[] buffer = new byte[1024];
			is.read(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 16, buffer[i]);
			}
			is.read(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 32, buffer[i]);
			}
			is.read(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 64, buffer[i]);
			}
			is.read(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 128, buffer[i]);
			}
			is.close();
		}
		catch (IOException e) {
			Assert.fail();
		}
	}
}
