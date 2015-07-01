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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.session.AbstractSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;

public class MandelbrotSession extends AbstractSession {
	private final List<MandelbrotListener> listeners = new ArrayList<>();
	private MandelbrotData data = new MandelbrotData();
	private CompilerReport report;
	private File currentFile;
	
	public void addMandelbrotListener(MandelbrotListener listener) {
		listeners.add(listener);
	}

	public void removeMandelbrotListener(MandelbrotListener listener) {
		listeners.remove(listener);
	}

	public String getVersion() {
		return data.getVersion();
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public String getSource() {
		return data.getSource();
	}
	
	public void setSource(String source) {
		if (!data.getSource().equals(source)) {
			data.setSource(source);
			fireSourceChanged();
		}
	}
	
	public CompilerReport getReport() {
		return report;
	}

	public void setReport(CompilerReport report) {
		this.report = report;
		fireReportChanged();
	}

	public double getTime() {
		return data.getTime();
	}

	public void setTime(double time) {
		data.setTime(time);
		fireDataChanged();
	}

	public double[] getPoint() {
		return data.getPoint();
	}
	
	public void setPoint(double[] point, boolean continuous) {
		data.setPoint(point);
		firePointChanged(continuous);
	}

	public MandelbrotView getViewAsCopy() {
		return new MandelbrotView(data.getTraslation(), data.getRotation(), data.getScale(), data.getPoint(), data.isJulia());
	}
	
	public void setView(MandelbrotView view, boolean continuous) {
		this.data.setTraslation(view.getTraslation());
		this.data.setRotation(view.getRotation());
		this.data.setScale(view.getScale());
		this.data.setPoint(view.getPoint());
		this.data.setJulia(view.isJulia());
		fireViewChanged(continuous);
	}
	
	public MandelbrotData getDataAsCopy() {
		MandelbrotData data = new MandelbrotData();
		data.setSource(this.data.getSource());
		data.setTraslation(this.data.getTraslation());
		data.setRotation(this.data.getRotation());
		data.setScale(this.data.getScale());
		data.setTime(this.data.getTime());
		data.setPoint(this.data.getPoint());
		data.setJulia(this.data.isJulia());
		return data;
	}

	public void setData(MandelbrotData data) {
		this.data.setSource(data.getSource());
		this.data.setTraslation(data.getTraslation());
		this.data.setRotation(data.getRotation());
		this.data.setScale(data.getScale());
		this.data.setTime(data.getTime());
		this.data.setPoint(data.getPoint());
		this.data.setJulia(data.isJulia());
		fireDataChanged();
	}

	protected void fireDataChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	protected void fireSourceChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.sourceChanged(this);
		}
	}

	protected void fireReportChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.reportChanged(this);
		}
	}

	protected void firePointChanged(boolean continuous) {
		for (MandelbrotListener listener : listeners) {
			listener.pointChanged(this, continuous);
		}
	}

	protected void fireViewChanged(boolean continuous) {
		for (MandelbrotListener listener : listeners) {
			listener.viewChanged(this, continuous);
		}
	}
}
