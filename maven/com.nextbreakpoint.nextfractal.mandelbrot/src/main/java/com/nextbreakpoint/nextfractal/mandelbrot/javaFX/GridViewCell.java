package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;

public class GridViewCell extends BorderPane {
	private JavaFXRendererFactory renderFactory = new JavaFXRendererFactory();
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
			GridItem item = (GridItem)data;
			if (item.coordinator != null) {
				if (item.coordinator.isPixelsChanged()) {
					RendererGraphicsContext gc = renderFactory.createGraphicsContext(g2d);
					item.coordinator.drawImage(gc);
				}
			} else if (item.data != null) {
				g2d.setFill(Color.GRAY);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			} else {
				g2d.setFill(Color.LIGHTGRAY);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
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
