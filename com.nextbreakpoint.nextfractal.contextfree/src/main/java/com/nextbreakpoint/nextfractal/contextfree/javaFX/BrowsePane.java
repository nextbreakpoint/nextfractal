/*
 * NextFractal 1.3.0
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
package com.nextbreakpoint.nextfractal.contextfree.javaFX;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeData;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeDataStore;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.contextfree.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.contextfree.compiler.Compiler;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class BrowsePane extends BorderPane {
	private static final String BROWSER_DEFAULT_LOCATION = "browser.default.location";
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

		Preferences prefs = Preferences.userNodeForPackage(RenderPane.class);
		
		currentDir = new File(prefs.get(BROWSER_DEFAULT_LOCATION, getDefaultBrowserDir()));

		threadFactory = new DefaultThreadFactory("BrowserPane", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		pathProperty = new StringObservableValue();
		pathProperty.setValue(null);

		int size = width / numCols;
		
		int maxThreads = numCols;
		
		executor = Executors.newFixedThreadPool(maxThreads, threadFactory);
		
		tile = createSingleTile(size, size);

		Button closeButton = new Button("", createIconImage("/icon-back.png"));
		Button reloadButton = new Button("", createIconImage("/icon-reload.png"));
		Button chooseButton = new Button("", createIconImage("/icon-folder.png"));
		closeButton.setTooltip(new Tooltip("Close fractals browser"));
		reloadButton.setTooltip(new Tooltip("Reload all fractals"));
		chooseButton.setTooltip(new Tooltip("Select source folder location"));

		Label statusLabel = new Label("Initializing");
		statusLabel.getStyleClass().add("items-label");

		HBox toolbarButtons = new HBox(4);
		toolbarButtons.getChildren().add(closeButton);
		toolbarButtons.getChildren().add(chooseButton);
		toolbarButtons.getChildren().add(statusLabel);
		toolbarButtons.getChildren().add(reloadButton);
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

		closeButton.setOnMouseClicked(e -> doClose(grid));

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
		shutdown();
		removeItems();
		super.finalize();
	}

	private void shutdown() {
		List<ExecutorService> executors = Arrays.asList(executor);
		executors.forEach(executor -> executor.shutdownNow());
		executors.forEach(executor -> Block.create(ExecutorService.class).andThen(e -> await(e)).tryExecute(executor));
	}

	private void await(ExecutorService executor) throws InterruptedException {
		executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
	}

	private ImageView createIconImage(String name, double percentage) {
		int size = (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(size);
		image.setFitHeight(size);
		return image;
	}

	private ImageView createIconImage(String name) {
		return createIconImage(name, 0.02);
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

	private void doClose(GridView grid) {
		delegate.didClose(this);
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
		Preferences prefs = Preferences.userNodeForPackage(RenderPane.class);
		prefs.put(BROWSER_DEFAULT_LOCATION, folder.getAbsolutePath());
		return folder.listFiles((dir, name) -> name.endsWith(".cf"));
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
			ContextFreeDataStore service = new ContextFreeDataStore();
			ContextFreeData data = service.loadFromFile(item.getFile());
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
			item.setData(data);
			item.setCFDG(report.getCFDG());
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
			ContextFreeData data = item.getData();
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

	private void initItem(GridItem item, ContextFreeData data) {
		try {
			Map<String, Integer> hints = new HashMap<String, Integer>();
			RendererCoordinator coordinator = new RendererCoordinator(threadFactory, renderFactory, tile, hints);
			coordinator.setCFDG(item.getCFDG());
			coordinator.init();
			coordinator.run();
			item.setCoordinator(coordinator);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getDefaultBrowserDir() {
		String defaultBrowserDir = System.getProperty(BROWSER_DEFAULT_LOCATION, "#[user.home]");
		String userHome = System.getProperty("user.home");
		String userDir = System.getProperty("user.dir");
		defaultBrowserDir = defaultBrowserDir.replace("#[user.home]", userHome);
		defaultBrowserDir = defaultBrowserDir.replace("#[user.dir]", userDir);
		logger.info("defaultBrowserDir = " + defaultBrowserDir);
		return defaultBrowserDir;
	}
}