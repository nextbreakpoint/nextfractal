/*
 * NextFractal 1.2
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.nextbreakpoint.nextfractal.core.utils.DateAdapter;

@XmlRootElement(name="mandelbrot")
public class MandelbrotData {
	private final static String version = "1.0";
	private double[] traslation = new double[] { 0, 0, 1, 0 };
	private double[] rotation = new double[] { 0, 0, 0, 0 };
	private double[] scale = new double[] { 1, 1, 1, 1 };
	private double[] point = new double[] { 0, 0 };
	@XmlElement(name = "timestamp", required = true) 
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timestamp = new Date();
	private String source = "";
	private boolean julia;
	private double time;
	private IntBuffer pixels;
	private float[] color;

	public boolean isJulia() {
		return julia;
	}

	public void setJulia(boolean julia) {
		this.julia = julia;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getVersion() {
		return version;
	}

	public double[] getPoint() {
		return point;
	}

	public void setPoint(double[] constant) {
		this.point = constant;
	}

	public double[] getTraslation() {
		return traslation;
	}

	public void setTraslation(double[] traslation) {
		this.traslation = traslation;
	}

	public double[] getRotation() {
		return rotation;
	}

	public void setRotation(double[] rotation) {
		this.rotation = rotation;
	}

	public double[] getScale() {
		return scale;
	}

	public void setScale(double[] scale) {
		this.scale = scale;
	}
	
	public IntBuffer getPixels() {
		return pixels;
	}
	
	public void setPixels(IntBuffer pixels) {
		this.pixels = pixels;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "[traslation=" + Arrays.toString(traslation)	+ ", rotation=" + Arrays.toString(rotation) + ", scale=" + Arrays.toString(scale) + ", julia=" + julia + ", point=" + Arrays.toString(point) + ", time=" + time + "]";
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}
}
