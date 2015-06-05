/*
 * NextFractal 1.0.5
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
package com.nextbreakpoint.nextfractal.server;

import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

public class RemoteJob {
	private RemoteProfile profile;
	private volatile Throwable error;

	public RemoteJob() {
	}
	
	public RemoteJob(RemoteProfile profile) {
		this.profile = profile;
	}

	public RemoteProfile getProfile() {
		return profile;
	}
	
	public RendererTile getTile() {
		return profile.createTile();
	}

	public String getPluginId() {
		return profile.getPluginId();
	}
	
	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}
}
