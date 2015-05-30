package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GridViewCell extends BorderPane {
	private Canvas canvas;
	private Object data;
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
	}

	public void update() {
		GraphicsContext g2d = canvas.getGraphicsContext2D();
		if (data != null) {
			float[] color = ((MandelbrotData)data).getColor();
			g2d.setFill(new Color(color[0],color[1],color[2],color[3]));
			g2d.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g2d.setFill(Color.WHITE);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void setData(Object data) {
		if (this.data != data) {
			this.data = data;
			update();
		}
	}

	public int getIndex() {
		return index;
	}
}
