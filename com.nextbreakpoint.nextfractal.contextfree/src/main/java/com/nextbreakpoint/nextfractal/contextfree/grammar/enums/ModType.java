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
package com.nextbreakpoint.nextfractal.contextfree.grammar.enums;

public enum ModType {
	unknown(0, ""),
	x(1, "\u0095\u00E7\u0048\u005E\u00CC\u0006"),
	y(2, "\u0084\u002B\u00F3\u00BB\u0093\u0059"),
	z(3, "\u00C8\u003A\u0012\u0032\u0036\u0071"),
	xyz(4, "\u006C\u0031\u00CA\u00BF\u008D\u0089"),
	transform(5, "\u0088\u0090\u0054\u00C5\u00D3\u0020"),
	size(6, "\u0064\u00EC\u005B\u004B\u00EE\u002B"),
	sizexyz(7, "\u00B0\u0031\u00D5\u001E\u007A\u005A"),
	rotate(8, "\u0084\u00B0\u0092\u0026\u0059\u00E2"),
	skew(9, "\u0084\u00B0\u0092\u0026\u0059\u00E3"),
	flip(10, "\u0043\u005A\u0017\u00EA\u0012\u0005"),
	zsize(11, "\u0064\u00EC\u005B\u004B\u00EE\u002B"),
	hue(12, "\u0002\u00DE\u002B\u002C\u0025\u00A1"),
	sat(13, "\u0018\u004F\u00CF\u0004\u003F\u00E5"),
	bright(14, "\u001F\u003F\u00EB\u00A2\u00A2\u007E"),
	alpha(15, "\u00B4\u00FF\u009E\u0045\u00EE\u007E"),
	hueTarg(16, "\u00AF\u00E5\u0058\u0033\u0020\u00F8"),
	satTarg(17, "\u0098\u0080\u00ED\u0044\u002F\u00F2"),
	brightTarg(18, "\u0068\u00D6\u00CB\u008A\u0096\u0020"),
	alphaTarg(19, "\u0024\u004C\u00CC\u0041\u0009\u00C7"),
	targHue(20, "\u00DB\u003F\u00A1\u00DA\u00E7\u0045"),
	targSat(21, "\u00DA\u0075\u0013\u00D3\u0030\u00EA"),
	targBright(22, "\u008F\u0001\u002B\u0075\u00C3\u0025"),
	targAlpha(23, "\u00E7\u00CD\u005E\u00E3\u0088\u00F4"),
	time(24, "\u0020\u00C6\u00E8\u0002\u00ED\u0027"),
	timescale(25, "\u0078\u008E\u00C8\u002C\u001C\u0096"),
	stroke(26, ""),
	param(27, ""),
	x1(28, ""),
	y1(29, ""),
	x2(30, ""),
	y2(31, ""),
	xrad(32, ""),
	yrad(33, ""),
	modification(34, "\u0088\u0090\u0054\u00C5\u00D3\u0020");

	private int type;
	private String entropy;
	
	private ModType(int type, String entropy) {
		this.type = type;
		this.entropy = entropy;
	}

	public static ModType fromType(int type) {
		for (ModType value : ModType.values()) {
			if (value.getType() == type) {
				return value;
			}
		}
		return unknown;
	}

	public static ModType byName(String name) {
		for (ModType value : ModType.values()) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return unknown;
	}

	public int getType() {
		return type;
	}

	public String getEntropy() {
		return entropy;
	}
}
