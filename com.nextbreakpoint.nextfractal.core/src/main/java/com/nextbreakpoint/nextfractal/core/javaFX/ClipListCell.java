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
package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Affine;

import java.text.SimpleDateFormat;

public class ClipListCell extends ListCell<Bitmap> {
	private BorderPane pane;
	private Canvas canvas;
	private Label label1;
	private Label label2;
	private RendererTile tile;

	public ClipListCell(RendererTile tile) {
		this.tile = tile;
		canvas = new Canvas(tile.getTileSize().getWidth(), tile.getTileSize().getHeight());
		label1 = new Label();
		label1.getStyleClass().add("text-small");
		label2 = new Label();
		label2.getStyleClass().add("text-small");
		pane = new BorderPane();
		VBox image = new VBox(4);
		image.setAlignment(Pos.CENTER);
		image.getChildren().add(canvas);
		pane.setLeft(image);
		VBox labels = new VBox(4);
		labels.setAlignment(Pos.CENTER);
		labels.getChildren().add(label1);
		labels.getChildren().add(label2);
		pane.setCenter(labels);
	}

	@Override
	public void updateItem(Bitmap bitmap, boolean empty) {
		super.updateItem(bitmap, empty);
		if (empty) {
			setGraphic(null);
		} else {
			if (bitmap.getPixels() != null) {
				WritableImage image = new WritableImage(bitmap.getWidth(), bitmap.getHeight());
				image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), bitmap.getPixels(), (int)image.getWidth());
				GraphicsContext g2d = canvas.getGraphicsContext2D();
				Affine affine = new Affine();
				int x = (tile.getTileSize().getWidth() - bitmap.getWidth()) / 2;
				int y = (tile.getTileSize().getHeight() - bitmap.getHeight()) / 2;
				affine.append(Affine.translate(0, +image.getHeight() / 2 + y));
				affine.append(Affine.scale(1, -1));
				affine.append(Affine.translate(0, -image.getHeight() / 2 - y));
				g2d.setTransform(affine);
				g2d.drawImage(image, x, y);
			}
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
			label1.setText(df1.format(bitmap.getTimestamp()));
			label2.setText(df2.format(bitmap.getTimestamp()));
			this.setGraphic(pane);
		}
	}
}
