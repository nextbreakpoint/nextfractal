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
package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.nextfractal.core.utils.DateAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.nio.IntBuffer;
import java.util.Date;

@XmlRootElement(name="contextfree")
public class ContextFreeData implements Cloneable {
	private final static String version = "1.0";
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timestamp = new Date();
	private String source = "";
	private String seed = "ABCD";
	private IntBuffer pixels;

	public ContextFreeData() {
	}

	public ContextFreeData(ContextFreeData data) {
		seed = data.seed;
		source = data.source;
		timestamp = data.timestamp;
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

	public IntBuffer getPixels() {
		return pixels;
	}
	
	public void setPixels(IntBuffer pixels) {
		this.pixels = pixels;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public Object clone() {
		return new ContextFreeData(this);
	}
}
