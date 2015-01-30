package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.text.SimpleDateFormat;

import javafx.scene.control.Label;

import org.controlsfx.control.GridCell;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class LibraryGridCell extends GridCell<MandelbrotData> {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Label label = new Label();
	
	@Override
	public void updateItem(MandelbrotData data, boolean empty) {
		super.updateItem(data, empty);
		if (empty) {
			setGraphic(null);
		} else {
			label.setText(df.format(data.getTimestamp()));
			this.setGraphic(label);
		}
	}
}
