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

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;

public class FractalSession<T> {
	private final RendererSize imageSize;
	private final RendererSize tileSize;
	private final String pluginId;
	private List<RemoteJob<T>> jobs;

	public FractalSession(String pluginId, RendererSize imageSize, RendererSize tileSize) {
		this.pluginId = pluginId;
		this.imageSize = imageSize;
		this.tileSize = tileSize;
		createJobs(0);
	}

	public int getJobsCount() {
		return jobs.size();
	}

	public List<RemoteJob<T>> getJobs() {
		return jobs;
	}

	private void createJobs(int frameNumber) {
		jobs = new ArrayList<RemoteJob<T>>();
		final int imageWidth = imageSize.getWidth();
		final int imageHeight = imageSize.getHeight();
		final int nx = imageWidth / tileSize.getWidth();
		final int ny = imageHeight / tileSize.getHeight();
		final int rx = imageWidth - tileSize.getWidth() * nx;
		final int ry = imageHeight - tileSize.getHeight() * ny;
		if ((nx > 0) && (ny > 0)) {
			for (int tx = 0; tx < nx; tx++) {
				for (int ty = 0; ty < ny; ty++) {
					int tileOffsetX = tileSize.getWidth() * tx;
					int tileOffsetY = tileSize.getHeight() * ty;
					jobs.add(createJob(tileOffsetX, tileOffsetY));
				}
			}
		}
		if (rx > 0) {
			for (int ty = 0; ty < ny; ty++) {
				int tileOffsetX = tileSize.getWidth() * nx;
				int tileOffsetY = tileSize.getHeight() * ty;
				jobs.add(createJob(tileOffsetX, tileOffsetY));
			}
		}
		if (ry > 0) {
			for (int tx = 0; tx < nx; tx++) {
				int tileOffsetX = tileSize.getWidth() * tx;
				int tileOffsetY = tileSize.getHeight() * ny;
				jobs.add(createJob(tileOffsetX, tileOffsetY));
			}
		}
		if (rx > 0 && ry > 0) {
			int tileOffsetX = tileSize.getWidth() * nx;
			int tileOffsetY = tileSize.getHeight() * ny;
			jobs.add(createJob(tileOffsetX, tileOffsetY));
		}
	}

	private RemoteJob<T> createJob(int tileOffsetX, int tileOffsetY) {
		final RemoteJob<T> job = new RemoteJob<T>();
		job.setPluginId(pluginId);
		job.setQuality(1);
		job.setImageWidth(imageSize.getWidth());
		job.setImageHeight(imageSize.getHeight());
		job.setTileWidth(tileSize.getWidth());
		job.setTileHeight(tileSize.getHeight());
		job.setTileOffsetX(tileOffsetX);
		job.setTileOffsetY(tileOffsetY);
		job.setBorderWidth(0);
		job.setBorderHeight(0);
		return job;
	}
}
