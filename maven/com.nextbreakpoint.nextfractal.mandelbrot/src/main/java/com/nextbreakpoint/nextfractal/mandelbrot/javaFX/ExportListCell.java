/*
 * NextFractal 1.1.4
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

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class ExportListCell extends ListCell<ExportSession> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BorderPane pane;
	private ProgressBar progress;
	private Canvas canvas;
	private Label label;
	private RendererSize size;
	private RendererTile tile;

	public ExportListCell(RendererSize size, RendererTile tile) {
		this.size = size;
		this.tile = tile;
		canvas = new Canvas(tile.getTileSize().getWidth(), tile.getTileSize().getHeight());
		progress = new ProgressBar();
		label = new Label();
		pane = new BorderPane();
		pane.setLeft(canvas);
		pane.setCenter(label);
		pane.setRight(progress);
		BorderPane.setAlignment(label, Pos.CENTER);
		BorderPane.setAlignment(canvas, Pos.CENTER);
		BorderPane.setAlignment(progress, Pos.CENTER);
	}

	@Override
	public void updateItem(ExportSession session, boolean empty) {
		super.updateItem(session, empty);
		if (empty) {
			setGraphic(null);
		} else {
			MandelbrotData data = (MandelbrotData)session.getData();
			if (data.getPixels() != null) {
				WritableImage image = new WritableImage(size.getWidth(), size.getHeight());
				image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
				GraphicsContext g2d = canvas.getGraphicsContext2D();
				Affine affine = new Affine();
				int x = (tile.getTileSize().getWidth() - size.getWidth()) / 2;
				int y = (tile.getTileSize().getHeight() - size.getHeight()) / 2;
				affine.append(Affine.translate(0, +image.getHeight() / 2 + y));
				affine.append(Affine.scale(1, -1));
				affine.append(Affine.translate(0, -image.getHeight() / 2 - y));
				g2d.setTransform(affine);
				g2d.drawImage(image, x, y);
			}
			progress.setProgress(session.getProgress());
			label.setText(df.format(data.getTimestamp()));
			this.setGraphic(pane);
		}
	}
}
