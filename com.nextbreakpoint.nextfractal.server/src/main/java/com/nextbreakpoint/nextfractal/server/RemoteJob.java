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
package com.nextbreakpoint.nextfractal.server;

import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

import java.util.UUID;

public class RemoteJob {
	private UUID taskId;
	private Session session;
	private float quality;
	private int imageWidth;
	private int imageHeight;
	private int tileWidth;
	private int tileHeight;
	private int tileOffsetX;
	private int tileOffsetY;
	private int borderWidth;
	private int borderHeight;
	private int row;
	private int col;
	private int rows;
	private int cols;

	private RemoteJob() {}
	
	public RendererTile getTile() {
		return createTile();
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileOffsetX() {
		return tileOffsetX;
	}

	public int getTileOffsetY() {
		return tileOffsetY;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public int getBorderHeight() {
		return borderHeight;
	}

	public float getQuality() {
		return quality;
	}

	public Session getSession() {
		return session;
	}

	public UUID getTaskId() {
		return taskId;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public static RemoteJobBuilder builder() {
		return new RemoteJobBuilder();
	}

	public RendererTile createTile() {
		RendererSize imageSize = new RendererSize(imageWidth, imageHeight);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(borderWidth, borderHeight);
		RendererPoint tileOffset = new RendererPoint(tileOffsetX, tileOffsetY);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}

	public static class RemoteJobBuilder {
		private UUID taskId;
		private Session session;
		private float quality;
		private int imageWidth;
		private int imageHeight;
		private int tileWidth;
		private int tileHeight;
		private int tileOffsetX;
		private int tileOffsetY;
		private int borderWidth;
		private int borderHeight;
		private int row;
		private int col;
		private int rows;
		private int cols;

		public RemoteJobBuilder withTaskId(UUID taskId) {
			this.taskId = taskId;
			return this;
		}

		public RemoteJobBuilder withSession(Session session) {
			this.session = session;
			return this;
		}

		public RemoteJobBuilder withQuality(float quality) {
			this.quality = quality;
			return this;
		}

		public RemoteJobBuilder withImageWidth(int imageWidth) {
			this.imageWidth = imageWidth;
			return this;
		}

		public RemoteJobBuilder withImageHeight(int imageHeight) {
			this.imageHeight = imageHeight;
			return this;
		}

		public RemoteJobBuilder withTileWidth(int tileWidth) {
			this.tileWidth = tileWidth;
			return this;
		}

		public RemoteJobBuilder withTileHeight(int tileHeight) {
			this.tileHeight = tileHeight;
			return this;
		}

		public RemoteJobBuilder withTileOffsetX(int tileOffsetX) {
			this.tileOffsetX = tileOffsetX;
			return this;
		}

		public RemoteJobBuilder withTileOffsetY(int tileOffsetY) {
			this.tileOffsetY = tileOffsetY;
			return this;
		}

		public RemoteJobBuilder withBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public RemoteJobBuilder withBorderHeight(int borderHeight) {
			this.borderHeight = borderHeight;
			return this;
		}

		public RemoteJobBuilder withRow(int row) {
			this.row = row;
			return this;
		}

		public RemoteJobBuilder withCol(int col) {
			this.col = col;
			return this;
		}

		public RemoteJobBuilder withRows(int rows) {
			this.rows = rows;
			return this;
		}

		public RemoteJobBuilder withCols(int cols) {
			this.cols = cols;
			return this;
		}

		public RemoteJob build() {
			RemoteJob remoteJob = new RemoteJob();
			remoteJob.taskId = taskId;
			remoteJob.session = session;
			remoteJob.quality = quality;
			remoteJob.imageWidth = imageWidth;
			remoteJob.imageHeight = imageHeight;
			remoteJob.tileWidth = tileWidth;
			remoteJob.tileHeight = tileHeight;
			remoteJob.tileOffsetX = tileOffsetX;
			remoteJob.tileOffsetY = tileOffsetY;
			remoteJob.borderWidth = borderWidth;
			remoteJob.borderHeight = borderHeight;
			remoteJob.row = row;
			remoteJob.col = col;
			remoteJob.rows = rows;
			remoteJob.cols = cols;
			return remoteJob;
		}
	}
}
