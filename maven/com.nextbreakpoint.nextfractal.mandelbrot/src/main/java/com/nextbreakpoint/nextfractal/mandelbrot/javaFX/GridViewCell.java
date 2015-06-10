/*
 * NextFractal 1.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
					g2d.setFill(Color.LIGHTGREY);
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
