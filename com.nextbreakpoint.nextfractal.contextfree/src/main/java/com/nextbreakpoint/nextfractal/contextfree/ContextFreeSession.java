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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ContextFreeSession extends AbstractSession {
	private ContextFreeData data = new ContextFreeData();
	private CompilerReport report;
	private File currentFile;

	public ContextFreeSession() {
		data.setSource(getInitialSource());
	}

	@Override
    public String getPluginId() {
        return "ContextFree";
    }

    @Override
	public String getGrammar() {
		return "ContextFree";
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

	private void setSource(String source) {
		data.setSource(source);
	}

	public CompilerReport getReport() {
		return report;
	}

	private void setReport(CompilerReport report) {
		this.report = report;
	}

	public double getTime() {
		return data.getTime();
	}

	private void setTime(double time) {
		data.setTime(time);
	}

	public ContextFreeData getDataAsCopy() {
		ContextFreeData data = new ContextFreeData();
		data.setSource(this.data.getSource());
		data.setTime(this.data.getTime());
		data.setSeed(this.data.getSeed());
		return data;
	}

	private void setData(ContextFreeData data) {
		this.data.setSource(data.getSource());
		this.data.setTime(data.getTime());
		this.data.setSeed(data.getSeed());
	}

	private String getInitialSource() {
		try {
			return readResource("/contextfree.txt");
		} catch (IOException e) {
		}
		return "";
	}

	protected String readResource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			return baos.toString();
		}
		return "";
	}
}
