package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;

public class GridViewCell extends BorderPane {
	private JavaFXRendererFactory renderFactory = new JavaFXRendererFactory();
	private boolean redraw;
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
		if (data != null) {
			GridItem item = (GridItem)data;
			RendererCoordinator coordinator = item.getCoordinator();
			if (coordinator != null) {
				if (redraw || coordinator.isPixelsChanged()) {
					RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					coordinator.drawImage(gc);
					redraw = false;
				}
			} else {
				if (redraw) {
					GraphicsContext g2d = canvas.getGraphicsContext2D();
					g2d.setFill(Color.WHITE);
					g2d.fillRect(0, 0, getWidth(), getHeight());
					redraw = false;
				}
			}
		} else {
			if (redraw) {
				GraphicsContext g2d = canvas.getGraphicsContext2D();
				g2d.setFill(Color.WHITE);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				redraw = false;
			}
		}
	}

	public void setData(Object data) {
		if (this.data != data) {
			this.data = data;
			redraw = true;
			//update();
		}
	}

	public int getIndex() {
		return index;
	}
}
