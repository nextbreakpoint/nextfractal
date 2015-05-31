/*
 * NextFractal 1.0.5
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

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class BrowsePane extends Pane {
	private static final int FRAME_LENGTH_IN_MILLIS = 100;
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRendererFactory renderFactory;
	private final int numRows = 4;
	private final int numCols = 4;
	private List<GridItem> items = new ArrayList<>();
	private BorderPane box = new BorderPane();
	private BrowseDelegate delegate; 
	private DirectoryChooser directoryChooser;
	private File currentFolder;
	private ExecutorService executor;
	private RendererTile tile;
	private AnimationTimer timer;

	public BrowsePane(int size) {
		
		threadFactory = new DefaultThreadFactory("MandelbrotEditorPane", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		executor = Executors.newFixedThreadPool(4, threadFactory);
		
		this.tile = createSingleTile(size, size);
		
		Button closeButton = new Button("Close");
		Button chooseButton = new Button("Choose");

		HBox buttons = new HBox(10);
		buttons.getChildren().add(chooseButton);
		buttons.getChildren().add(closeButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.getStyleClass().add("buttons");

		GridView grid = new GridView(numRows, numCols, size);
		
		grid.setDelegate(new GridViewDelegate() {
			@Override
			public void didRangeChange(GridView source, int firstRow, int lastRow) {
				updateCells(grid, firstRow, lastRow);
			}
		});
		
		box.setCenter(grid);
		box.setBottom(buttons);
		box.getStyleClass().add("browse");
		
		closeButton.setOnMouseClicked(e -> {
			hide();
		});
		
		chooseButton.setOnMouseClicked(e -> {
			doChooseFolder(grid);
		});
		
		getChildren().add(box);
		
		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefWidth(newValue.doubleValue());
				box.setLayoutX(-newValue.doubleValue());
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefHeight(newValue.doubleValue());
			}
		});
		
		runTimer(grid);
	}

	public void show() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromX(box.getTranslateX());
		tt.setToX(box.getWidth());
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(false);
			}
		});
		tt.play();
	}
	
	public void hide() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromX(box.getTranslateX());
		tt.setToX(0);
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(true);
			}
		});
		tt.play();
	}

	public BrowseDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(BrowseDelegate delegate) {
		this.delegate = delegate;
	}

	private void createDirectoryChooser() {
		if (directoryChooser == null) {
			directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private void doChooseFolder(GridView grid) {
		createDirectoryChooser();
		directoryChooser.setTitle("Choose");
		if (currentFolder != null) {
			directoryChooser.setInitialDirectory(currentFolder);
		}
		File folder = directoryChooser.showDialog(null);
		if (folder != null) {
			currentFolder = folder;
			loadFiles(grid, currentFolder);
		}
	}

	private RendererTile createSingleTile(int width, int height) {
		int tileWidth = width;
		int tileHeight = height;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}

	private void loadFiles(GridView grid, File folder) {
		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.abort();
			}
		}
		
		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.waitFor();
			}
		}

		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				RendererCoordinator coordinator = item.coordinator;
				item.coordinator = null;
				coordinator.dispose();
			}
		}

		items.clear();
		
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".m");
			}
		});

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				GridItem item = new GridItem();
				item.row = i / numCols;
				item.col = i % numCols;
				item.file = files[i];
				items.add(item);
				
				executor.submit(new Runnable() {
					@Override
					public void run() {
						try {
							MandelbrotDataStore service = new MandelbrotDataStore();
							MandelbrotData data = service.loadFromFile(item.file);
							Compiler compiler = new Compiler();
							CompilerReport report = compiler.generateJavaSource(data.getSource());
							if (report.getErrors().size() > 0) {
								throw new RuntimeException("Failed to compile source");
							}
							CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
							if (orbitBuilder.getErrors().size() > 0) {
								throw new RuntimeException("Failed to compile Orbit class");
							}
							CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
							if (colorBuilder.getErrors().size() > 0) {
								throw new RuntimeException("Failed to compile Color class");
							}
							item.astOrbit = report.getOrbitSource();
							item.astColor = report.getColorSource();
							item.orbitBuilder = orbitBuilder;
							item.colorBuilder = colorBuilder;
							item.data = data;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}

			grid.setData(items.toArray(new GridItem[0]));

			updateCells(grid, grid.getFirstRow(), grid.getLastRow());
		}
	}
	
//	private void renderImage(GraphicsContext g2d, MandelbrotData data) {
//		data.setPixels(generator.renderImage(data));
//		WritableImage image = new WritableImage(size.getWidth(), size.getHeight());
//		image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
//		Affine affine = new Affine();
//		int x = (tile.getTileSize().getWidth() - size.getWidth()) / 2;
//		int y = (tile.getTileSize().getHeight() - size.getHeight()) / 2;
//		affine.append(Affine.translate(0, +image.getHeight() / 2 + y));
//		affine.append(Affine.scale(1, -1));
//		affine.append(Affine.translate(0, -image.getHeight() / 2 - y));
//		g2d.setTransform(affine);
//		g2d.drawImage(image, x, y);
//	}

	private void runTimer(GridView grid) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if (time - last > FRAME_LENGTH_IN_MILLIS) {
					redrawCells(grid, grid.getFirstRow(), grid.getLastRow());					
					last = time;
				}
			}
		};
		timer.start();
	}

	private void redrawCells(GridView grid, int firstRow, int lastRow) {
		if (grid.getData() == null) {
			return;
		}
		if (firstRow > 0) {
			firstRow -= 1;
		}
		if (lastRow < grid.getData().length / numCols - 1) {
			lastRow += 1;
		}
		int firstIndex = firstRow * numCols;
		int lastIndex = lastRow * numCols + numCols;
		for (int index = firstIndex; index < Math.min(lastIndex, items.size()); index++) {
			GridItem item = items.get(index);
			GridViewCell cell = grid.getCell(item.row, item.col);
			if (cell != null) {
				cell.update();
			}
		}
	}
	
	private void updateCells(GridView grid, int firstRow, int lastRow) {
		if (grid.getData() == null) {
			return;
		}
		if (firstRow > 0) {
			firstRow -= 1;
		}
		if (lastRow < grid.getData().length / numCols - 1) {
			lastRow += 1;
		}
		int firstIndex = firstRow * numCols;
		int lastIndex = lastRow * numCols + numCols;
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.abort();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.abort();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.waitFor();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				item.coordinator.waitFor();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				RendererCoordinator coordinator = item.coordinator;
				item.coordinator = null;
				coordinator.dispose();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.coordinator != null) {
				RendererCoordinator coordinator = item.coordinator;
				item.coordinator = null;
				coordinator.dispose();
			}
		}
		for (int index = firstIndex; index < Math.min(lastIndex, items.size()); index++) {
			GridItem item = items.get(index);
			if (item.data != null && item.coordinator == null) {
				try {
					Map<String, Integer> hints = new HashMap<String, Integer>();
					hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
					RendererCoordinator coordinator = new RendererCoordinator(threadFactory, renderFactory, tile, hints);
					coordinator.setOrbitAndColor(item.orbitBuilder.build(), item.colorBuilder.build());
					coordinator.init();
					RendererView view = new RendererView();
					view.setTraslation(new Double4D(item.data.getTraslation()));
					view.setRotation(new Double4D(item.data.getRotation()));
					view.setScale(new Double4D(item.data.getScale()));
					view.setState(new Integer4D(0, 0, 0, 0));
					view.setJulia(item.data.isJulia());
					view.setPoint(new Number(item.data.getPoint()));
					coordinator.setView(view);
					coordinator.run();
					item.coordinator = coordinator;
				} catch (Exception e) {
					e.printStackTrace();//TODO
				}
			}
		}
	}
}
