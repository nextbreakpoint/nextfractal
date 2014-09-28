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

import java.io.IOException;

import com.nextbreakpoint.nextfractal.queue.encoder.EncoderContext;

/**
 * @author Andrea Medeghini
 */
public class TestEncoderContext implements EncoderContext {
	private final int imageWidth;
	private final int imageHeight;
	private final int frameRate;
	private final int frameCount;

	public TestEncoderContext(final int imageWidth, final int imageHeight, final int frameRate, final int frameCount) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.frameRate = frameRate;
		this.frameCount = frameCount;
	}

	public byte[] getTileAsByteArray(final int n, final int x, final int y, final int w, final int h, final int s) throws IOException {
		final byte[] data = new byte[w * h * s];
		for (int i = 0; i < data.length; i += s) {
			final byte v = (byte) Math.rint(Math.random() * 255);
			if (s == 3) {
				data[i + 0] = 0;
				data[i + 1] = 0;
				data[i + 2] = v;
			}
			else if (s == 4) {
				data[i + 0] = v;
				data[i + 1] = 0;
				data[i + 2] = v;
				data[i + 3] = (byte) 255;
			}
		}
		return data;
	}

	public int[] getTileAsIntArray(final int n, final int x, final int y, final int w, final int h, final int s) throws IOException {
		final int[] data = new int[w * h * s];
		for (int i = 0; i < data.length; i += s) {
			final int v = (int) Math.rint(Math.random() * 255);
			if (s == 3) {
				data[i + 0] = v;
				data[i + 1] = v;
				data[i + 2] = v;
			}
			else if (s == 4) {
				data[i + 0] = v;
				data[i + 1] = v;
				data[i + 2] = v;
				data[i + 3] = (byte) 255;
			}
		}
		return data;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public int getFrameCount() {
		return frameCount;
	}
}
