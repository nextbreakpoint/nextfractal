/*
 * NextFractal 1.1.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.awt.Color;
import java.util.Arrays;

public class CFColor {
	public static final int HUE_TARGET = 1 << 0;
	public static final int SATURATION_TARGET = 1 << 1;
	public static final int BRIGHTNESS_TARGET = 1 << 2;
	public static final int ALPHA_TARGET = 1 << 3;
	private static final float EQUALITY_THRESHOLD = 0.00001f;
	private float[] hsba;
	private int useTarget; 

	public CFColor() {
		this.hsba = new float[] { 0, 0, 0, 0 };
	}

	public CFColor(float h, float s, float b, float a) {
		this.hsba = new float[] { h, s, b, a };
	}

	public CFColor(int argb) {
		this.hsba = new float[4];
		Color.RGBtoHSB((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, (argb >> 0) & 0xFF, hsba);
		hsba[0] = hsba[0] * 360;
		hsba[3] = ((argb >> 24) & 0xFF) / 255;
	}

	public CFColor(float[] hsba) {
		this.hsba = hsba;
	}

	public CFColor(float[] hsba, int useTarget) {
		this.hsba = hsba;
		this.useTarget = useTarget;
	}

	public float getAlpha() {
		return hsba[3];
	}

	public float getHue() {
		return hsba[0];
	}

	public float getSaturation() {
		return hsba[1];
	}

	public float getBrightness() {
		return hsba[2];
	}

	public void setAlpha(float a) {
		hsba[3] = a;
	}

	public void setHue(float h) {
		hsba[0] = h;
	}

	public void setSaturation(float s) {
		hsba[1] = s;
	}

	public void setBrightness(float b) {
		hsba[2] = b;
	}

	private float adjust(float base, float adjustment, boolean useTarget, float target) {
		if (adjustment == 0.0)
			return base;
		float v;
		if (useTarget) {
			// If we are really close to the target then don't change, even if
			// the adjustment is negative (which way would we go?)
			if (adjustment > 0 && Math.abs(base - target) < EQUALITY_THRESHOLD)
				return base;
			// Otherwise move away from or toward the target
			float edge = base < target ? 0 : 1;
			if (adjustment < 0)
				v = base + (base - edge) * adjustment;
			else
				v = base + (target - base) * adjustment;
		} else {
			// Move toward 0 or toward 1
			if (adjustment < 0)
				v = base + base * adjustment;
			else
				v = base + (1 - base) * adjustment;
		}
		return v;
	}

	private float adjustHue(float base, float adjustment, boolean useTarget, float target) {
		if (adjustment == 0.0)
			return base;
		float h;
		if (useTarget) {
			// decrease or increase toward target. If the target hue does not
			// cooperate by being smaller (or larger) than the current hue then
			// add/subtract 360 to make it so. This only works if all hues are
			// within the interval [0,360).
			if (adjustment < 0) {
				if (target > base)
					target -= 360;
				h = base + (base - target) * adjustment;
			} else {
				if (target < base)
					target += 360;
				h = base + (target - base) * adjustment;
			}
		} else {
			h = base + adjustment;
		}
		// Normalize result to the interval [0,360)
		int H = (int) Math.rint(h);
		return H < 0.0 ? (H + 360) % 360 : H % 360;
	}

	public void adjustWith(CFColor adj, CFColor target) {
	    // Adjust parent color w/shape color
	    hsba[0] = adjustHue(hsba[0], adj.hsba[0], (adj.getUseTarget() & HUE_TARGET) != 0, target.hsba[0]);
	    hsba[1] = adjust(hsba[1], adj.hsba[1], (adj.getUseTarget() & SATURATION_TARGET) != 0, target.hsba[1]);
	    hsba[2] = adjust(hsba[2], adj.hsba[2], (adj.getUseTarget() & BRIGHTNESS_TARGET) != 0, target.hsba[2]);
	    hsba[3] = adjust(hsba[3], adj.hsba[3], (adj.getUseTarget() & ALPHA_TARGET) != 0, target.hsba[3]);
	}

	public float delta(float to, float from, int steps) {
	    if (Math.abs(to - from) < EQUALITY_THRESHOLD) 
	        return 0.0f;
	    if (to > from)
	        return -delta(1.0f - to, 1.0f - from, steps);
	    // from >= EQUALITY_THRESHOLD
	    if (steps == 1)
	        return to / from - 1.0f;
	    return (float) (Math.pow(to / from, 1.0f / steps) - 1.0f);
	}

	public float deltaHue(float to, float from, int steps) { 
		float diff = (int) Math.rint((to - from) / steps) % 360;
	    // Normalize result to the interval (-180,180]
	    if (diff <= -180.0) return diff + 360;
	    if (diff >   180.0) return diff - 360;
	    return diff;
	}

	public int getUseTarget() {
		return useTarget;
	}

	public void setUseTarget(int useTarget) {
		this.useTarget |= useTarget;
	}

	public void unsetUseTarget(int useTarget) {
		this.useTarget ^= useTarget;
	}
	
	@Override
	public CFColor clone() {
		return new CFColor(hsba.clone(), useTarget);
	}

	public float[] getHSBA() {
		return hsba;
	}

	public int getARGB() {
		int argb = Color.HSBtoRGB(hsba[0] / 360, hsba[1], hsba[2]);
		argb |= ((int) (hsba[3] * 255)) << 24; 
		return argb;
	}

	@Override
	public String toString() {
		return "CFColor [hsba=" + Arrays.toString(hsba) + ", useTarget=" + useTarget + "]";
	}
}
