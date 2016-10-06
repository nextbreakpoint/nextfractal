/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import com.nextbreakpoint.nextfractal.core.utils.Block;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
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

public class BrowsePane extends BorderPane {
	private static final Logger logger = Logger.getLogger(BrowsePane.class.getName());
	private static final int FRAME_LENGTH_IN_MILLIS = 50;
	private static final int SCROLL_BOUNCE_DELAY = 500;
	private final ThreadFactory threadFactory;
	private final JavaFXRendererFactory renderFactory;
	private StringObservableValue pathProperty;
	private final int numRows = 3;
	private final int numCols = 3;
	private List<GridItem> items = new ArrayList<>();
	private BrowseDelegate delegate;
	private DirectoryChooser directoryChooser;
	private File currentFolder;
	private File currentDir;
	private ExecutorService executor;
	private RendererTile tile;
	private AnimationTimer timer;

	public BrowsePane(int width, int height) {
		setMinWidth(width);
		setMaxWidth(width);
		setPrefWidth(width);
		setMinHeight(height);
		setMaxHeight(height);
		setPrefHeight(height);

		Preferences prefs = Preferences.userNodeForPackage(MandelbrotRenderPane.class);
		
		currentDir = new File(prefs.get("mandelbrot.browser.default", getDefaultBrowserDir()));

		threadFactory = new DefaultThreadFactory("BrowserPane", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		pathProperty = new StringObservableValue();
		pathProperty.setValue(null);

		int size = width / numCols;
		
		int maxThreads = numCols;
		
		executor = Executors.newFixedThreadPool(maxThreads, threadFactory);
		
		tile = createSingleTile(size, size);
		
		Button reloadButton = new Button("Reload");
		Button chooseButton = new Button("Location...");

		Label statusLabel = new Label("Initializing");

		HBox toolbarButtons = new HBox(10);
		toolbarButtons.getChildren().add(reloadButton);
		toolbarButtons.getChildren().add(chooseButton);
		toolbarButtons.getChildren().add(statusLabel);
		toolbarButtons.setAlignment(Pos.CENTER);
		toolbarButtons.getStyleClass().add("toolbar");
		toolbarButtons.getStyleClass().add("translucent");
		toolbarButtons.setPrefHeight(height * 0.07);

		GridView grid = new GridView(numRows, numCols, size);
		
		grid.setDelegate(new GridViewDelegate() {
			@Override
			public void didRangeChange(GridView source, int firstRow, int lastRow) {
			}
			
			@Override
			public void didCellChange(GridView source, int row, int col) {
				source.updateCell(row * numCols + col);
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

		BorderPane box = new BorderPane();
		box.setCenter(grid);
		box.setBottom(toolbarButtons);
		box.getStyleClass().add("browse");
		
		setCenter(box);
		
		chooseButton.setOnMouseClicked(e -> doChooseFolder(grid));
		
		reloadButton.setOnMouseClicked(e -> loadFiles(statusLabel, grid, currentFolder));

		pathProperty.addListener((observable, oldValue, newValue) -> {
			File path = new File(newValue);
			currentDir = path; 
			loadFiles(statusLabel, grid, path);
		});

		runTimer(grid);
	}

	public void reload() {
		pathProperty.setValue(currentDir.getAbsolutePath());
	}
	
	public void setDelegate(BrowseDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	protected void finalize() throws Throwable {
		removeItems();
		super.finalize();
	}

	private void ensureDirectoryChooser() {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Dialog");
//		alert.setHeaderText("Path");
//		alert.setContentText(defaultDir.getAbsolutePath());
//		alert.showAndWait();
		if (directoryChooser == null) {
			directoryChooser = new DirectoryChooser();
			if (!currentDir.exists()) {
				currentDir = new File(System.getProperty("user.home"));
			}
			directoryChooser.setInitialDirectory(currentDir);
		}
	}

	private void doChooseFolder(GridView grid) {
		Block.create(a -> prepareDirectoryChooser()).andThen(a -> doSelectFolder()).tryExecute();
	}

	private void doSelectFolder() {
		Optional.ofNullable(directoryChooser.showDialog(BrowsePane.this.getScene().getWindow())).ifPresent(folder -> pathProperty.setValue(folder.getAbsolutePath()));
	}

	private void prepareDirectoryChooser() {
		ensureDirectoryChooser();
		directoryChooser.setTitle("Choose");
		if (currentFolder != null) {
			ensureValidFolder();
			directoryChooser.setInitialDirectory(currentFolder);
		}
	}

	private void ensureValidFolder() {
		currentFolder = Optional.ofNullable(currentFolder).filter(folder -> folder.exists()).orElseGet(() -> new File(System.getProperty("user.home")));
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

	private void loadFiles(Label statusLabel, GridView grid, File folder) {
		currentFolder = folder;
		removeItems();
		grid.setData(new GridItem[0]);
		File[] files = listFiles(folder);
		if (files != null && files.length > 0) {
			statusLabel.setText(files.length + " item" + (files.length > 1 ? "s" : "") + " found");
			loadItems(grid, files);
		} else {
			statusLabel.setText("No items found");
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
		return folder.listFiles((dir, name) -> name.endsWith(".m"));
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
			CompilerReport report = compiler.compileReport(data.getSource());
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
		int firstIndex = Math.min(firstRow * numCols, items.size());
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
			view.setTraslation(new Double4D(data.getTranslation()));
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
		String defaultBrowserDir = System.getProperty("mandelbrot.browser.default", "#[user.home]");
		String userHome = System.getProperty("user.home");
		String userDir = System.getProperty("user.dir");
		defaultBrowserDir = defaultBrowserDir.replace("#[user.home]", userHome);
		defaultBrowserDir = defaultBrowserDir.replace("#[user.dir]", userDir);
		logger.info("defaultBrowserDir = " + defaultBrowserDir);
		return defaultBrowserDir;
	}
}
