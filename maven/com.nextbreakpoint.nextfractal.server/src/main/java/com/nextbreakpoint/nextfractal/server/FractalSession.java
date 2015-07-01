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
package com.nextbreakpoint.nextfractal.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;

@XmlRootElement
public class FractalSession {
	private final RendererSize imageSize = new RendererSize(512, 512);
	private final int tileSize = 64;
	private final String pluginId;
	private List<RemoteJob> jobs;

	public FractalSession(String pluginId) {
		this.pluginId = pluginId;
		createJobs(0);
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getJobsCount() {
		return jobs.size();
	}

	public List<RemoteJob> getJobs() {
		return jobs;
	}

	private void createJobs(int frameNumber) {
		jobs = new ArrayList<RemoteJob>();
		final int imageWidth = imageSize.getWidth();
		final int imageHeight = imageSize.getHeight();
		final int nx = imageWidth / tileSize;
		final int ny = imageHeight / tileSize;
		final int rx = imageWidth - tileSize * nx;
		final int ry = imageHeight - tileSize * ny;
		if ((nx > 0) && (ny > 0)) {
			for (int tx = 0; tx < nx; tx++) {
				for (int ty = 0; ty < ny; ty++) {
					int tileOffsetX = tileSize * tx;
					int tileOffsetY = tileSize * ty;
					jobs.add(createJob(createProfile(imageWidth, imageHeight, tileOffsetX, tileOffsetY)));
				}
			}
		}
		if (rx > 0) {
			for (int ty = 0; ty < ny; ty++) {
				int tileOffsetX = tileSize * nx;
				int tileOffsetY = tileSize * ty;
				jobs.add(createJob(createProfile(imageWidth, imageHeight, tileOffsetX, tileOffsetY)));
			}
		}
		if (ry > 0) {
			for (int tx = 0; tx < nx; tx++) {
				int tileOffsetX = tileSize * tx;
				int tileOffsetY = tileSize * ny;
				jobs.add(createJob(createProfile(imageWidth, imageHeight, tileOffsetX, tileOffsetY)));
			}
		}
		if (rx > 0 && ry > 0) {
			int tileOffsetX = tileSize * nx;
			int tileOffsetY = tileSize * ny;
			jobs.add(createJob(createProfile(imageWidth, imageHeight, tileOffsetX, tileOffsetY)));
		}
	}

	private RemoteProfile createProfile(final int imageWidth, final int imageHeight, int tileOffsetX, int tileOffsetY) {
		final RemoteProfile profile = new RemoteProfile();
		profile.setPluginId(pluginId);
		profile.setQuality(1);
		profile.setImageWidth(imageWidth);
		profile.setImageHeight(imageHeight);
		profile.setTileWidth(tileSize);
		profile.setTileHeight(tileSize);
		profile.setTileOffsetX(tileOffsetX);
		profile.setTileOffsetY(tileOffsetY);
		profile.setBorderWidth(0);
		profile.setBorderHeight(0);
		return profile;
	}

	private RemoteJob createJob(RemoteProfile profile) {
		return new RemoteJob(profile);
	}
}
