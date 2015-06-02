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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.prefs.Preferences;

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
	private static final int FRAME_LENGTH_IN_MILLIS = 50;
	private static final int SCROLL_BOUNCE_DELAY = 500;
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRendererFactory renderFactory;
	private final int numRows = 3;
	private final int numCols = 3;
	private List<GridItem> items = new ArrayList<>();
	private BorderPane box = new BorderPane();
	private BrowseDelegate delegate; 
	private DirectoryChooser directoryChooser;
	private File currentFolder;
	private File defaultDir;
	private ExecutorService executor;
	private RendererTile tile;
	private AnimationTimer timer;

	public BrowsePane(int width) {
		Preferences prefs = Preferences.userNodeForPackage(MandelbrotRenderPane.class);
		
	    defaultDir = new File(prefs.get("mandelbrot.browser.default", getDefaultBrowserDir()));

		threadFactory = new DefaultThreadFactory("BrowserPane", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		int size = width / numCols;
		
		int maxThreads = numCols;
		
		executor = Executors.newFixedThreadPool(maxThreads, threadFactory);
		
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
				updateCells(source);
			}

			@Override
			public void didSelectionChange(GridView source, int selectedRow, int selectedCol) {
				int index = selectedRow * numCols + selectedCol;
				if (index >= 0 && index < items.size()) {
					GridItem item = items.get(index);
					File file = item.getFile();
					if (file != null) {
						if (delegate != null) {
							delegate.didSelectFile(BrowsePane.this, file);
						}
					}
				}
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
		
		loadFiles(grid, defaultDir);
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

	@Override
	protected void finalize() throws Throwable {
		removeItems();
		super.finalize();
	}

	private void createDirectoryChooser() {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Dialog");
//		alert.setHeaderText("Path");
//		alert.setContentText(defaultDir.getAbsolutePath());
//		alert.showAndWait();
		if (directoryChooser == null) {
			directoryChooser = new DirectoryChooser();
			if (defaultDir.exists()) {
				directoryChooser.setInitialDirectory(defaultDir);
			} else {
				directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			}
		}
	}

	private void doChooseFolder(GridView grid) {
		createDirectoryChooser();
		directoryChooser.setTitle("Choose");
		if (currentFolder != null) {
			currentFolder = currentFolder.exists() ? currentFolder : new File(System.getProperty("user.home"));
			directoryChooser.setInitialDirectory(currentFolder);
		}
		File folder = directoryChooser.showDialog(null);
		if (folder != null) {
			loadFiles(grid, folder);
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
		currentFolder = folder;
		removeItems();
		File[] files = listFiles(folder);
		if (files != null) {
			loadItems(grid, files);
		}
	}

	private void loadItems(GridView grid, File[] files) {
		GridItem[] items = new GridItem[files.length];
		for (int i = 0; i < files.length; i++) {
			items[i] = new GridItem();
			items[i].setFile(files[i]);
			this.items.add(items[i]);
		}
		grid.setData(items);
	}

	private File[] listFiles(File folder) {
		Preferences prefs = Preferences.userNodeForPackage(MandelbrotRenderPane.class);

		prefs.put("mandelbrot.browser.default", folder.getAbsolutePath()); 
		
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".m");
			}
		});
		return files;
	}

	private void removeItems() {
		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getFuture() != null && !item.getFuture().isDone()) {
				item.getFuture().cancel(true);
			}
		}

		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				item.getCoordinator().abort();
			}
		}
		
//		for (int index = 0; index < items.size(); index++) {
//			GridItem item = items.get(index);
//			if (item.getCoordinator() != null) {
//				item.getCoordinator().waitFor();
//			}
//		}

		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
//				RendererCoordinator coordinator = item.getCoordinator();
				item.setCoordinator(null);
//				coordinator.dispose();
			}
		}

		items.clear();
	}

	private void loadItem(GridItem item) {
		try {
			MandelbrotDataStore service = new MandelbrotDataStore();
			MandelbrotData data = service.loadFromFile(item.getFile());
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			Compiler compiler = new Compiler();
			CompilerReport report = compiler.generateJavaSource(data.getSource());
			if (report.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile source");
			}
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			if (orbitBuilder.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile Orbit class");
			}
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			if (colorBuilder.getErrors().size() > 0) {
				throw new RuntimeException("Failed to compile Color class");
			}
			item.setOrbitBuilder(orbitBuilder);
			item.setColorBuilder(colorBuilder);
			item.setData(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runTimer(GridView grid) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if (time - last > FRAME_LENGTH_IN_MILLIS) {
					updateCells(grid);
					last = time;
				}
			}
		};
		timer.start();
	}

	private void updateCells(GridView grid) {
		if (grid.getData() == null) {
			return;
		}
		int firstRow = grid.getFirstRow(); 
		int lastRow = grid.getLastRow();
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
			if (item.getFuture() != null && !item.getFuture().isDone()) {
				item.getFuture().cancel(true);
				item.setFuture(null);
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getFuture() != null && !item.getFuture().isDone()) {
				item.getFuture().cancel(true);
				item.setFuture(null);
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				item.getCoordinator().abort();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				item.getCoordinator().abort();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				item.getCoordinator().waitFor();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				item.getCoordinator().waitFor();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				RendererCoordinator coordinator = item.getCoordinator();
				item.setCoordinator(null);
				coordinator.dispose();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getCoordinator() != null) {
				RendererCoordinator coordinator = item.getCoordinator();
				item.setCoordinator(null);
				coordinator.dispose();
			}
		}
		for (int index = firstIndex; index < Math.min(lastIndex, items.size()); index++) {
			GridItem item = items.get(index);
			MandelbrotData data = item.getData();
			long time = System.currentTimeMillis();
			if (data == null && time - item.getLastChanged() > SCROLL_BOUNCE_DELAY && item.getFuture() == null) {
				Future<GridItem> task = executor.submit(new Callable<GridItem>() {
					@Override
					public GridItem call() throws Exception {
						loadItem(item);
						return null;
					}
				});
				item.setFuture(task);
			}  
			if (data != null && time - item.getLastChanged() > SCROLL_BOUNCE_DELAY && item.getCoordinator() == null) {
				initItem(item, data);
			}
		}
		grid.updateCells();
	}

	private void initItem(GridItem item, MandelbrotData data) {
		try {
			Map<String, Integer> hints = new HashMap<String, Integer>();
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
			hints.put(RendererCoordinator.KEY_MULTITHREAD, RendererCoordinator.VALUE_SINGLE_THREAD);
			RendererCoordinator coordinator = new RendererCoordinator(threadFactory, renderFactory, tile, hints);
			coordinator.setOrbitAndColor(item.getOrbitBuilder().build(), item.getColorBuilder().build());
			coordinator.init();
			RendererView view = new RendererView();
			view.setTraslation(new Double4D(data.getTraslation()));
			view.setRotation(new Double4D(data.getRotation()));
			view.setScale(new Double4D(data.getScale()));
			view.setState(new Integer4D(0, 0, 0, 0));
			view.setPoint(new Number(data.getPoint()));
			view.setJulia(data.isJulia());
			coordinator.setView(view);
			coordinator.run();
			item.setCoordinator(coordinator);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getDefaultBrowserDir() {
		String defaultBrowserDir = System.getProperty("mandelbrot.browser.default", "#[user.home]}");
		String userHome = System.getProperty("user.home");
		String userDir = System.getProperty("user.dir");
		defaultBrowserDir = defaultBrowserDir.replace("#[user.home]", userHome);
		defaultBrowserDir = defaultBrowserDir.replace("#[user.dir]", userDir);
		return defaultBrowserDir;
	}
}
