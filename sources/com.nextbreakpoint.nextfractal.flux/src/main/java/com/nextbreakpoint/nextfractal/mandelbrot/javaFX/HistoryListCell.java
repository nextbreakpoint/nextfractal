package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class HistoryListCell extends ListCell<MandelbrotData> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BorderPane pane;
	private Canvas canvas;
	private Label label;
	private int imageWidth;
	private int imageHeight;

	public HistoryListCell(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		canvas = new Canvas(imageWidth, imageHeight);
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
				WritableImage image = new WritableImage(imageWidth, imageHeight);
				image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
				canvas.getGraphicsContext2D().drawImage(image, 0, 0);
			}
			label.setText(df.format(data.getTimestamp()));
			this.setGraphic(pane);
		}
	}
}
