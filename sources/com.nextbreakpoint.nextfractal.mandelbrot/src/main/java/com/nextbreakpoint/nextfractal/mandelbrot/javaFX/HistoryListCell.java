package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.renderer.RendererTile;

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
		pane.setCenter(label);
		pane.setLeft(canvas);
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
