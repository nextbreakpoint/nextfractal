/*
 * NextFractal 1.3.0
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.session.Session;

import java.io.IOException;

public class MandelbrotSession extends Session {
	private MandelbrotData data = new MandelbrotData();

	public MandelbrotSession() {
		data.setSource(getInitialSource());
	}

    @Override
    public String getPluginId() {
        return "Mandelbrot";
    }

	public MandelbrotData getDataAsCopy() {
		MandelbrotData data = new MandelbrotData();
		data.setSource(this.data.getSource());
		data.setTranslation(this.data.getTranslation());
		data.setRotation(this.data.getRotation());
		data.setScale(this.data.getScale());
		data.setPoint(this.data.getPoint());
		data.setJulia(this.data.isJulia());
		return data;
	}

	private String getInitialSource() {
		try {
			return readResource("/mandelbrot.txt");
		} catch (IOException e) {
		}
		return "";
	}
}
