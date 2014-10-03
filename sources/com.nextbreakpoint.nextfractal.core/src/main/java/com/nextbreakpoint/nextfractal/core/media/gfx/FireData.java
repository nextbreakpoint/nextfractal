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
package com.nextbreakpoint.nextfractal.core.media.gfx;

public final class FireData extends Effect {
	private final ColorMap colormap = new FireMap();

	public FireData() {
	}

	@Override
	public String getName() {
		return "Fire";
	}

	public ColorMap getColorMap() {
		return colormap;
	}

	private final class FireMap extends ColorMap {
		private final byte[][] data;

		public FireMap() {
			data = new byte[4][256];
			int i = 1;
			byte c = (byte) 0;
			data[0][0] = (byte) 0;
			data[1][0] = (byte) 0;
			data[2][0] = (byte) 0;
			data[3][0] = (byte) 0;
			// black to red
			while (i < 64) {
				data[0][i] = c;
				data[1][i] = (byte) 0;
				data[2][i] = (byte) 0;
				data[3][i] = (byte) (i * 2);
				c += 4;
				i++;
			}
			c = 0;
			// red to yellow
			while (i < 128) {
				data[0][i] = (byte) 255;
				data[1][i] = c;
				data[2][i] = (byte) 0;
				data[3][i] = (byte) 196;
				c += 4;
				i++;
			}
			c = 0;
			// yellow to white
			while (i < 256) {
				data[0][i] = (byte) 255;
				data[1][i] = (byte) 255;
				data[2][i] = c;
				data[3][i] = (byte) 196;
				c += 4;
				i++;
			}
		}

		@Override
		public byte[] getRed() {
			return data[0];
		}

		@Override
		public byte[] getGreen() {
			return data[1];
		}

		@Override
		public byte[] getBlue() {
			return data[2];
		}

		@Override
		public byte[] getAlpha() {
			return data[3];
		}
	}
}
