package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GridViewCell extends BorderPane {
	private Canvas canvas;
	private int index;
	
	public GridViewCell(int index, int width, int height) {
		this.index = index;
		canvas = new Canvas(width, height);
		setCenter(canvas);
		
		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				update();
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				update();
			}
		});

		update();
	}

	private void update() {
		GraphicsContext g2d = canvas.getGraphicsContext2D();
		g2d.setFill(Color.RED);
		g2d.fillRect(0, 0, getWidth() - 2, getHeight() - 2);
	}

	public void setData(Object data) {
	}
}
