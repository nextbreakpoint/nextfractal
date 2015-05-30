package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class GridView extends Pane {
	private final GridViewCell[] cells;
	protected final int cellSize;
	private Object[] data;
	private int numRows;
	private int numCols;
	private double offsetX;
	private double offsetY;
	private double prevOffsetY;
	
	public GridView(int numOfRows, int numOfColumns, int cellSize) {
		this.cellSize = cellSize;
		
		numRows = numOfRows;
		numCols = numOfColumns;
				
		cells = new GridViewCell[(numRows + 2) * numCols];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = createCell(i);
			getChildren().add(cells[i]);
		}
		
		addEventFilter(ScrollEvent.SCROLL_STARTED,
			new EventHandler<ScrollEvent>() {	
				public void handle(final ScrollEvent scrollEvent) {
					layoutCells();
				}
			});
		
		addEventFilter(ScrollEvent.SCROLL_FINISHED,
			new EventHandler<ScrollEvent>() {
				public void handle(final ScrollEvent scrollEvent) {
					layoutCells();
				}
			});
		
		addEventFilter(ScrollEvent.SCROLL,
			new EventHandler<ScrollEvent>() {
				public void handle(final ScrollEvent scrollEvent) {
					offsetY += scrollEvent.getDeltaY();
					if (offsetY < -numRows * cellSize) {
						offsetY = -numRows * cellSize;
					}
					if (offsetY > 0) {
						offsetY = 0;
					}
					if (prevOffsetY != offsetY) {
						prevOffsetY = offsetY;
						if (offsetY < -cellSize) {
							int firstRow = (int)Math.abs(offsetY / cellSize);
							for (int row = 0; row < numRows; row++) {
								for (int col = 0; col < numCols; col++) {
									if (data != null) {
										if (firstRow * numCols + col < data.length) {
											cells[row * numCols + col].setData(data[firstRow * numCols + col]);
										} else {
											cells[row * numCols + col].setData(null);
										}
									}
								}
							}
						}
						layoutCells();
					}
				}
			});

		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				layoutCells();
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				layoutCells();
			}
		});

		layoutCells();
	}

	private void layoutCells() {
		for (int row = 0; row < numRows + 2; row++) {
			for (int col = 0; col < numCols; col++) {
				GridViewCell cell = cells[row * numCols + col];
				cell.setLayoutY(row * cellSize + (offsetY - ((int)(offsetY / cellSize)) * cellSize));
				cell.setLayoutX(col * cellSize + (offsetX - ((int)(offsetX / cellSize)) * cellSize));
				cell.setMaxWidth(cellSize);
				cell.setMaxHeight(cellSize);
				cell.setMinWidth(cellSize);
				cell.setMinHeight(cellSize);
				cell.setPrefWidth(cellSize);
				cell.setPrefHeight(cellSize);
			}
		}
	}

	protected GridViewCell createCell(int index) {
		return new GridViewCell(index, cellSize, cellSize);
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
		layoutCells();
	}
}
