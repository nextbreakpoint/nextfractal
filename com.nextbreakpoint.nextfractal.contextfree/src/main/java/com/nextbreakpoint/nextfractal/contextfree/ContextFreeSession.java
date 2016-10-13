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

import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.core.session.AbstractSession;
import java.util.List;

import java.io.File;
import java.util.ArrayList;

public class ContextFreeSession extends AbstractSession {
	private final List<ContextFreeListener> listeners = new ArrayList<>();
	private ContextFreeData data = new ContextFreeData();
	private CompilerReport report;
	private File currentFile;
	private String status;
	private String error;

	@Override
	public String getGrammar() {
		return "ContextFree";
	}

	public void addContextFreeListener(ContextFreeListener listener) {
		listeners.add(listener);
	}

	public void removeContextFreeListener(ContextFreeListener listener) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		fireStatusChanged();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
		fireErrorChanged();
	}

	public ContextFreeData getDataAsCopy() {
		ContextFreeData data = new ContextFreeData();
		data.setSource(this.data.getSource());
		data.setTime(this.data.getTime());
		return data;
	}

	public void setData(ContextFreeData data) {
		this.data.setSource(data.getSource());
		this.data.setTime(data.getTime());
		fireDataChanged();
	}

	protected void fireDataChanged() {
		for (ContextFreeListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	protected void fireSourceChanged() {
		for (ContextFreeListener listener : listeners) {
			listener.sourceChanged(this);
		}
	}

	protected void fireReportChanged() {
		for (ContextFreeListener listener : listeners) {
			listener.reportChanged(this);
		}
	}

	protected void fireStatusChanged() {
		for (ContextFreeListener listener : listeners) {
			listener.statusChanged(this);
		}
	}

	protected void fireErrorChanged() {
		for (ContextFreeListener listener : listeners) {
			listener.errorChanged(this);
		}
	}
}
