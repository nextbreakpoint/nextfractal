package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.ExportSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class ExportListCell extends ListCell<ExportSession> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BorderPane pane;
	private ProgressBar progress;
	private Canvas canvas;
	private Label label;
	private int imageWidth;
	private int imageHeight;

	public ExportListCell(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		canvas = new Canvas(imageWidth, imageHeight);
		progress = new ProgressBar();
		label = new Label();
		pane = new BorderPane();
		pane.setCenter(label);
		pane.setLeft(canvas);
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
				WritableImage image = new WritableImage(imageWidth, imageHeight);
				image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
				canvas.getGraphicsContext2D().drawImage(image, 0, 0);
			}
			progress.setProgress(session.getProgress());
			label.setText(df.format(data.getTimestamp()));
			this.setGraphic(pane);
		}
	}
}
