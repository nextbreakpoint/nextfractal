/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.core.utils.DateAdapter;
import com.nextbreakpoint.nextfractal.core.utils.Time;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.nio.IntBuffer;
import java.util.Date;

@XmlRootElement(name="mandelbrot")
public class MandelbrotDataV11 implements Cloneable {
	private final static String version = "1.1";
	private double[] translation = new double[] { 0, 0, 1, 0 };
	private double[] rotation = new double[] { 0, 0, 0, 0 };
	private double[] scale = new double[] { 1, 1, 1, 1 };
	private double[] point = new double[] { 0, 0 };
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timestamp = new Date();
	private String source = "";
	private boolean julia;
	private IntBuffer pixels;

	public MandelbrotDataV11() {
	}

	public MandelbrotDataV11(MandelbrotDataV11 otherData) {
		translation = otherData.translation.clone();
		rotation = otherData.rotation.clone();
		scale = otherData.scale.clone();
		point = otherData.point.clone();
		julia = otherData.julia;
		source = otherData.source;
	}

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

	public String getVersion() {
		return version;
	}

	public double[] getPoint() {
		return point;
	}

	public void setPoint(double[] constant) {
		this.point = constant;
	}

	public double[] getTranslation() {
		return translation;
	}

	public void setTranslation(double[] translation) {
		this.translation = translation;
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

	public MandelbrotMetadata getView() {
		return new MandelbrotMetadata(translation, rotation, scale, point, new Time(0, 1), julia, new MandelbrotOptions());
	}

	public void setView(MandelbrotMetadata view) {
		setTranslation(view.getTranslation().toArray());
		setRotation(view.getRotation().toArray());
		setScale(view.getScale().toArray());
		setPoint(view.getPoint().toArray());
		setJulia(view.isJulia());
	}

	public Object clone() {
		return new MandelbrotDataV11(this);
	}
}
