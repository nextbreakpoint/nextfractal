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
package com.nextbreakpoint.nextfractal.core.media;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Effect {
	protected String name;
	private final float[] value = new float[8];
	private final int[] value_int = new int[8];

	protected Effect(final String name) {
		this(name, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	}

	protected Effect(final String name, final float a1, final float a2, final float r1, final float r2, final float g1, final float g2, final float b1, final float b2) {
		this.name = name;
		setValue(a1, a2, r1, r2, g1, g2, b1, b2);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Effect(name + "_copy", value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
	}

	@Override
	public final String toString() {
		return name;
	}

	final void setValue(final float a1, final float a2, final float r1, final float r2, final float g1, final float g2, final float b1, final float b2) {
		value[0] = a1;
		value[1] = a2;
		value[2] = r1;
		value[3] = r2;
		value[4] = g1;
		value[5] = g2;
		value[6] = b1;
		value[7] = b2;
		for (int i = 0; i < 8; i++) {
			value_int[i] = (int) Math.rint(65536f * value[i]);
		}
	}

	final Effect add(final Effect effect) {
		if (effect != null) {
			final float a1 = effect.value[0] * value[0];
			final float a2 = (effect.value[1] * value[0]) + value[1];
			final float r1 = effect.value[2] * value[2];
			final float r2 = (effect.value[3] * value[2]) + value[3];
			final float g1 = effect.value[4] * value[4];
			final float g2 = (effect.value[5] * value[4]) + value[5];
			final float b1 = effect.value[6] * value[6];
			final float b2 = (effect.value[7] * value[6]) + value[7];
			return new Effect(name + "->" + effect.name, a1, a2, r1, r2, g1, g2, b1, b2);
		}
		return this;
	}

	public final int filter(final int argb, final int transparency) {
		if (transparency != Transparency.OPAQUE) {
			int a = (((((argb >> 24) & 0xFF) * value_int[0]) + (value_int[1] << 8)) << 8) & 0xFF000000;
			int r = ((((argb >> 16) & 0xFF) * value_int[2]) + (value_int[3] << 8)) & 0xFF0000;
			int g = (((((argb >> 8) & 0xFF) * value_int[4]) + (value_int[5] << 8)) >> 8) & 0xFF00;
			int b = ((((argb & 0xFF) * value_int[6]) + (value_int[7] << 8)) >> 16) & 0xFF;
			return a | r | g | b;
		}
		else {
			int a = (((255 * value_int[0]) + (value_int[1] << 8)) << 8) & 0xFF000000;
			int r = ((((argb >> 16) & 0xFF) * value_int[2]) + (value_int[3] << 8)) & 0xFF0000;
			int g = (((((argb >> 8) & 0xFF) * value_int[4]) + (value_int[5] << 8)) >> 8) & 0xFF00;
			int b = ((((argb & 0xFF) * value_int[6]) + (value_int[7] << 8)) >> 16) & 0xFF;
			return a | r | g | b;
		}
	}

	public final void filterImage(final BufferedImage src, final BufferedImage dst) {
		int[] src_buffer = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
		int[] dst_buffer = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
		for (int i = src_buffer.length - 1; i >= 0; i--) {
			int argb = src_buffer[i];
			int a = (((((argb >> 24) & 0xFF) * value_int[0]) + (value_int[1] << 8)) << 8) & 0xFF000000;
			int r = ((((argb >> 16) & 0xFF) * value_int[2]) + (value_int[3] << 8)) & 0xFF0000;
			int g = (((((argb >> 8) & 0xFF) * value_int[4]) + (value_int[5] << 8)) >> 8) & 0xFF00;
			int b = ((((argb & 0xFF) * value_int[6]) + (value_int[7] << 8)) >> 16) & 0xFF;
			dst_buffer[i] = a | r | g | b;
		}
	}

	void init(final int frames) {
	}

	void reset() {
	}

	void setFrame(final int frame) {
	}

	void nextFrame() {
	}

	void prevFrame() {
	}
}
