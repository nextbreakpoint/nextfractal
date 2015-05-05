/*
 * NextFractal 1.0.4
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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class HistoryListCell extends ListCell<MandelbrotData> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BorderPane pane;
	private Canvas canvas;
	private Label label;
	private RendererSize size;
	private RendererTile tile;

	public HistoryListCell(RendererSize size, RendererTile tile) {
		this.size = size;
		this.tile = tile;
		canvas = new Canvas(tile.getTileSize().getWidth(), tile.getTileSize().getHeight());
		label = new Label();
		pane = new BorderPane();
		pane.setLeft(canvas);
		pane.setCenter(label);
	}

	@Override
	public void updateItem(MandelbrotData data, boolean empty) {
		super.updateItem(data, empty);
		if (empty) {
			setGraphic(null);
		} else {
			if (data.getPixels() != null) {
				WritableImage image = new WritableImage(size.getWidth(), size.getHeight());
				image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
				canvas.getGraphicsContext2D().drawImage(image, (tile.getTileSize().getWidth() - size.getWidth()) / 2, (tile.getTileSize().getHeight() - size.getHeight()) / 2);
			}
			label.setText(df.format(data.getTimestamp()));
			this.setGraphic(pane);
		}
	}
}
