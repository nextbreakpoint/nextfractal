package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class GridView extends Pane {
	private final GridViewCell[] cells;
	private GridViewDelegate delegate;
	protected final int cellSize;
	private Object[] data;
	protected int numRows;
	protected int numCols;
	private double offsetX;
	private double offsetY;
	private double prevOffsetY;
	private double prevOffsetX;
	
	public GridView(int numRows, int numCols, int cellSize) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.cellSize = cellSize;
		getStyleClass().add("grid-view");
				
		cells = new GridViewCell[(numRows + 1) * numCols];

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
					if (data != null) {
						double y = (data.length / numCols) * cellSize - Math.min(numRows * cellSize, getHeight());
						double x = numCols * cellSize - Math.min(numCols * cellSize, getWidth());
						if (y > 0) {
							offsetY += scrollEvent.getDeltaY();
							if (offsetY < -y) {
								offsetY = -y;
							}
							if (offsetY > 0) {
								offsetY = 0;
							}
						}
						if (x > 0) {
							offsetX += scrollEvent.getDeltaX();
							if (offsetX < -x) {
								offsetX = -x;
							}
							if (offsetX > 0) {
								offsetX = 0;
							}
						}
						if (prevOffsetX != offsetX || prevOffsetY != offsetY) {
							prevOffsetY = offsetY;
							prevOffsetX = offsetX;
							updateRows();
							layoutCells();
						}
					}
				}
			});

		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				resetScroll();
				layoutCells();
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				resetScroll();
				layoutCells();
			}
		});
	}
	
	private void updateRows() {
		int firstRow = (int)Math.abs(offsetY / cellSize);
		int lastRow = firstRow + numRows;
		for (int row = 0; row < numRows + 1; row++) {
			for (int col = 0; col < numCols; col++) {
				int index = (firstRow + row) * numCols + col;
				if (index < data.length) {
					cells[row * numCols + col].setData(data[index]);
				} else {
					cells[row * numCols + col].setData(null);
				}
			}
		}
		if (delegate != null) {
			delegate.didRangeChange(this, firstRow, lastRow);
		}
	}
	
	private void layoutCells() {
		for (int row = 0; row < numRows + 1; row++) {
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
	
	private void resetScroll() {
		offsetX = 0;
		offsetY = 0;
		prevOffsetX = 0;
		prevOffsetY = 0;
	}

	protected GridViewCell createCell(int index) {
		return new GridViewCell(index, cellSize, cellSize);
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
		resetScroll();
		updateRows();
		layoutCells();
	}
	
	public GridViewDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(GridViewDelegate delegate) {
		this.delegate = delegate;
	}

	public int getFirstRow() {
		int firstRow = (int)Math.abs(offsetY / cellSize);
		return firstRow;
	}

	public int getLastRow() {
		int firstRow = (int)Math.abs(offsetY / cellSize);
		int lastRow = firstRow + numRows;
		return lastRow;
	}

	public void updateCells() {
		for (int i = 0; i < cells.length; i++) {
			cells[i].update();
		}
	}
}
