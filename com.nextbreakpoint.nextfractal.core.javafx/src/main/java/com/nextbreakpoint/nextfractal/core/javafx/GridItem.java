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
package com.nextbreakpoint.nextfractal.core.javafx;

import com.nextbreakpoint.nextfractal.core.Error;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

public class GridItem {
	private volatile long lastChanged;
	private volatile File file;
	private volatile BrowseBitmap bitmap;
	private volatile GridItemRenderer renderer;
	private volatile Future<GridItem> future;
	private volatile List<Error> errors = new LinkedList<>();

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		lastChanged = System.currentTimeMillis();
		this.file = file;
	}

	public BrowseBitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(BrowseBitmap bitmap) {
		lastChanged = System.currentTimeMillis();
		this.bitmap = bitmap;
	}

	public GridItemRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(GridItemRenderer renderer) {
		lastChanged = System.currentTimeMillis();
		this.renderer = renderer;
	}

	public Future<GridItem> getFuture() {
		return future;
	}

	public void setFuture(Future<GridItem> future) {
		lastChanged = System.currentTimeMillis();
		this.future = future;
	}

	public long getLastChanged() {
		return lastChanged;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors.clear();
		this.errors.addAll(errors);
	}
}
