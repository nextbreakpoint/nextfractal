package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.control.ListCell;
import javafx.scene.image.PixelFormat;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class LibraryListCell extends ListCell<MandelbrotData> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Canvas canvas = new Canvas(100, 100);
	
	public LibraryListCell() {
		setMinHeight(100);
	}

	@Override
	public void updateItem(MandelbrotData data, boolean empty) {
		super.updateItem(data, empty);
		if (empty) {
			setGraphic(null);
		} else {
			WritableImage image = new WritableImage(100, 100);
			image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
			canvas.getGraphicsContext2D().drawImage(image, 0, 0);
			this.setGraphic(canvas);
		}
	}
}
