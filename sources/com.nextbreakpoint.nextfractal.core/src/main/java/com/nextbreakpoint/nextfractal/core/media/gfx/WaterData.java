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

public final class WaterData extends Effect {
	int[][] data;

	public WaterData(final int w, final int h) {
		if ((w > 0) && (h > 0)) {
			data = new int[64][h];
			final double d = Math.PI / 32d;
			double c = 0d;
			for (int f = 0; f < 64; f++) {
				for (int i = 0; i < h; i++) {
					data[f][i] = (int) ((6d * (i + (h / 4d)) * Math.sin(((double) (10 * (h - i)) / (double) (i + 1)) + c)) / h);
				}
				c += d;
			}
		}
	}

	@Override
	public String getName() {
		return "Water";
	}
}
