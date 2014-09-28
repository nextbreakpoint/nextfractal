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
package com.nextbreakpoint.nextfractal.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;

public class TestChunkedRandomAccessFile {
	@Test
	public void test() {
		try {
			ChunkedRandomAccessFile raf = new ChunkedRandomAccessFile(new File("c:"), "test", ".bin", 2048);
			for (int i = 0; i < 1024; i++) {
				raf.write(i % 16);
			}
			for (int i = 0; i < 1024; i++) {
				raf.write(i % 32);
			}
			for (int i = 0; i < 1024; i++) {
				raf.write(i % 64);
			}
			for (int i = 0; i < 1024; i++) {
				raf.write(i % 128);
			}
			raf.seek(0);
			byte[] buffer = new byte[1024];
			raf.readFully(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 16, buffer[i]);
			}
			raf.readFully(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 32, buffer[i]);
			}
			raf.readFully(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 64, buffer[i]);
			}
			raf.readFully(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 128, buffer[i]);
			}
			raf.seek(2000);
			raf.readFully(buffer);
			for (int i = 0; i < 48; i++) {
				Assert.assertEquals((2000 + i) % 32, buffer[i]);
			}
			for (int i = 48; i < 1024; i++) {
				Assert.assertEquals((2000 + i) % 64, buffer[i]);
			}
			raf.seek(0);
			buffer = new byte[4096];
			raf.readFully(buffer);
			for (int i = 0; i < 1024; i++) {
				Assert.assertEquals(i % 16, buffer[i]);
			}
			for (int i = 1024; i < 2048; i++) {
				Assert.assertEquals(i % 32, buffer[i]);
			}
			for (int i = 2048; i < 3072; i++) {
				Assert.assertEquals(i % 64, buffer[i]);
			}
			for (int i = 3072; i < 4096; i++) {
				Assert.assertEquals(i % 128, buffer[i]);
			}
			raf.close();
		}
		catch (IOException e) {
			Assert.fail();
		}
	}
}
