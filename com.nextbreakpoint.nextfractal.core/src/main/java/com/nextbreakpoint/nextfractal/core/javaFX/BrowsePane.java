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
package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class BrowsePane extends BorderPane {
	public static final String BROWSER_DEFAULT_LOCATION = "browser.location";
	private static final Logger logger = Logger.getLogger(BrowsePane.class.getName());
	private static final int FRAME_LENGTH_IN_MILLIS = 50;
	private static final int SCROLL_BOUNCE_DELAY = 500;
	private final ThreadFactory threadFactory;
	private StringObservableValue sourcePathProperty;
	private StringObservableValue importPathProperty;
	private final int numRows = 3;
	private final int numCols = 3;
	private List<GridItem> items = new ArrayList<>();
	private BrowseDelegate delegate;
	private DirectoryChooser sourceDirectoryChooser;
	private File sourceCurrentFolder;
	private DirectoryChooser importDirectoryChooser;
	private File importCurrentFolder;
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

		Preferences prefs = Preferences.userNodeForPackage(BrowsePane.class);
		
		currentDir = getCurrentDir(prefs);

		threadFactory = createThreadFactory("Browser");
		
		sourcePathProperty = new StringObservableValue();

		sourcePathProperty.setValue(null);

		importPathProperty = new StringObservableValue();

		importPathProperty.setValue(null);

		int size = width / numCols;
		
		int maxThreads = numCols;
		
		executor = Executors.newFixedThreadPool(maxThreads, threadFactory);
		
		tile = createSingleTile(size, size);

		Button closeButton = new Button("", createIconImage("/icon-back.png"));
		Button reloadButton = new Button("", createIconImage("/icon-reload.png"));
		Button chooseButton = new Button("", createIconImage("/icon-folder.png"));
		Button importButton = new Button("", createIconImage("/icon-import.png"));
		closeButton.setTooltip(new Tooltip("Close fractals browser"));
		reloadButton.setTooltip(new Tooltip("Reload all fractals"));
		chooseButton.setTooltip(new Tooltip("Select source folder location"));
		importButton.setTooltip(new Tooltip("Select a folder from where to import fractals"));

		Label statusLabel = new Label("Initializing");
		statusLabel.getStyleClass().add("items-label");

		HBox toolbarButtons = new HBox(4);
		toolbarButtons.getChildren().add(closeButton);
		toolbarButtons.getChildren().add(chooseButton);
		toolbarButtons.getChildren().add(statusLabel);
		toolbarButtons.getChildren().add(reloadButton);
		toolbarButtons.getChildren().add(importButton);
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

		chooseButton.setOnMouseClicked(e -> doChooseSourceFolder(grid));

		importButton.setOnMouseClicked(e -> doChooseImportFolder(grid));

		reloadButton.setOnMouseClicked(e -> loadFiles(statusLabel, grid, sourceCurrentFolder));

		sourcePathProperty.addListener((observable, oldValue, newValue) -> {
			File path = new File(newValue);
			currentDir = path; 
			loadFiles(statusLabel, grid, path);
		});

		importPathProperty.addListener((observable, oldValue, newValue) -> {
			File path = new File(newValue);
			currentDir = path;
			importFiles(statusLabel, grid, path);
		});

		runTimer(grid);
	}

	private DefaultThreadFactory createThreadFactory(String name) {
		return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
	}

	public void reload() {
		sourcePathProperty.setValue(currentDir.getAbsolutePath());
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
		executors.forEach(executor -> await(executor));
	}

	private void await(ExecutorService executor) {
		Try.of(() -> executor.awaitTermination(5000, TimeUnit.MILLISECONDS)).onFailure(e -> logger.warning("Await termination timeout")).execute();
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

	private void ensureSourceDirectoryChooser() {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Dialog");
//		alert.setHeaderText("Path");
//		alert.setContentText(defaultDir.getAbsolutePath());
//		alert.showAndWait();
		if (sourceDirectoryChooser == null) {
			sourceDirectoryChooser = new DirectoryChooser();
			if (!currentDir.exists()) {
				currentDir = new File(System.getProperty("user.home"));
			}
			sourceDirectoryChooser.setInitialDirectory(currentDir);
		}
	}

	private void ensureImportDirectoryChooser() {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Dialog");
//		alert.setHeaderText("Path");
//		alert.setContentText(defaultDir.getAbsolutePath());
//		alert.showAndWait();
		if (importDirectoryChooser == null) {
			importDirectoryChooser = new DirectoryChooser();
			if (!currentDir.exists()) {
				currentDir = new File(System.getProperty("user.home"));
			}
			importDirectoryChooser.setInitialDirectory(currentDir);
		}
	}

	private void doClose(GridView grid) {
		delegate.didClose(this);
	}

	private void doChooseSourceFolder(GridView grid) {
		Block.create(a -> prepareSourceDirectoryChooser()).andThen(a -> doSelectSourceFolder()).tryExecute().execute();
	}

	private void doChooseImportFolder(GridView grid) {
		Block.create(a -> prepareImportDirectoryChooser()).andThen(a -> doSelectImportFolder()).tryExecute().execute();
	}

	private void doSelectSourceFolder() {
		Optional.ofNullable(sourceDirectoryChooser.showDialog(BrowsePane.this.getScene().getWindow())).ifPresent(folder -> sourcePathProperty.setValue(folder.getAbsolutePath()));
	}

	private void prepareSourceDirectoryChooser() {
		ensureSourceDirectoryChooser();
		sourceDirectoryChooser.setTitle("Choose source folrder");
		if (sourceCurrentFolder != null) {
			sourceCurrentFolder = ensureValidFolder(sourceCurrentFolder);
			sourceDirectoryChooser.setInitialDirectory(sourceCurrentFolder);
		}
	}

	private void doSelectImportFolder() {
		Optional.ofNullable(importDirectoryChooser.showDialog(BrowsePane.this.getScene().getWindow())).ifPresent(folder -> importPathProperty.setValue(folder.getAbsolutePath()));
	}

	private void prepareImportDirectoryChooser() {
		ensureImportDirectoryChooser();
		importDirectoryChooser.setTitle("Choose import folder");
		if (importCurrentFolder != null) {
			importCurrentFolder = ensureValidFolder(importCurrentFolder);
			importDirectoryChooser.setInitialDirectory(importCurrentFolder);
		}
	}

	private File ensureValidFolder(File folder) {
		return Optional.ofNullable(folder).filter(d -> d.exists()).orElseGet(() -> new File(System.getProperty("user.home")));
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
		sourceCurrentFolder = folder;
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

	private void importFiles(Label statusLabel, GridView grid, File folder) {
		importCurrentFolder = folder;
		File[] files = listAllFiles(folder);
		if (files != null && files.length > 0) {
			executor.submit(() -> copyFiles(statusLabel, grid, files, sourceCurrentFolder));
		}
	}

	private void copyFiles(Label statusLabel, GridView grid, File[] files, File location) {
		Platform.runLater(() -> {
			grid.setDisable(true);
		});

		for (File file : files) {
			Platform.runLater(() -> {
				statusLabel.setText("Importing " + files.length + " files...");
			});

			copyFile(statusLabel, grid, file, location);
		}

		Platform.runLater(() -> {
			grid.setDisable(false);
		});

		Platform.runLater(() -> {
			loadFiles(statusLabel, grid, sourceCurrentFolder);
		});
	}

	private void copyFile(Label statusLabel, GridView grid, File file, File location) {
		File outFile = createFileName(file, location);
		FileManager.loadFile(file).ifPresent(session -> FileManager.saveFile(outFile, session));
	}

	private File createFileName(File file, File location) {
		File tmpFile = new File(location, file.getName().substring(0, file.getName().indexOf(".")) + ".nf.zip");
		int i = 0;
		while (tmpFile.exists()) {
			tmpFile = new File(location, file.getName().substring(0, file.getName().indexOf(".")) + "-" + (i++) + ".nf.zip");
		}
		return tmpFile;
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

	private File[] listAllFiles(File folder) {
		Preferences prefs = Preferences.userNodeForPackage(BrowsePane.class);
		prefs.put(BROWSER_DEFAULT_LOCATION, folder.getAbsolutePath());
		if (delegate != null) {
			return folder.listFiles((dir, name) -> name.endsWith(".m") || name.endsWith(".nf.zip"));
		} else {
			return new File[0];
		}
	}

	private File[] listFiles(File folder) {
		Preferences prefs = Preferences.userNodeForPackage(BrowsePane.class);
		prefs.put(BROWSER_DEFAULT_LOCATION, folder.getAbsolutePath());
		if (delegate != null) {
			return folder.listFiles((dir, name) -> name.endsWith(".nf.zip"));
		} else {
			return new File[0];
		}
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
			if (item.getRenderer() != null) {
				item.getRenderer().abort();
			}
		}
		
//		for (int index = 0; index < items.size(); index++) {
//			GridItem item = items.get(index);
//			if (item.getRenderer() != null) {
//				item.getRenderer().waitFor();
//			}
//		}

		for (int index = 0; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
//				GridItemRenderer renderer = item.getRenderer();
				item.setRenderer(null);
//				renderer.dispose();
			}
		}

		items.clear();
	}

	private void loadItem(GridItem item) {
		try {
			if (delegate != null) {
				item.setBitmap(delegate.createBitmap(item.getFile(), tile.getTileSize()));
			}
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
			if (item.getRenderer() != null) {
				item.getRenderer().abort();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
				item.getRenderer().abort();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
				item.getRenderer().waitFor();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
				item.getRenderer().waitFor();
			}
		}
		for (int index = 0; index < firstIndex; index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
				GridItemRenderer renderer = item.getRenderer();
				item.setRenderer(null);
				renderer.dispose();
			}
		}
		for (int index = lastIndex; index < items.size(); index++) {
			GridItem item = items.get(index);
			if (item.getRenderer() != null) {
				GridItemRenderer renderer = item.getRenderer();
				item.setRenderer(null);
				renderer.dispose();
			}
		}
		for (int index = firstIndex; index < Math.min(lastIndex, items.size()); index++) {
			GridItem item = items.get(index);
			BrowseBitmap bitmap = item.getBitmap();
			long time = System.currentTimeMillis();
			if (bitmap == null && time - item.getLastChanged() > SCROLL_BOUNCE_DELAY && item.getFuture() == null) {
				Future<GridItem> task = executor.submit(new Callable<GridItem>() {
					@Override
					public GridItem call() throws Exception {
						loadItem(item);
						return null;
					}
				});
				item.setFuture(task);
			}  
			if (bitmap != null && time - item.getLastChanged() > SCROLL_BOUNCE_DELAY && item.getRenderer() == null) {
				initItem(item, bitmap);
			}
		}
		grid.updateCells();
	}

	private void initItem(GridItem item, BrowseBitmap bitmap) {
		try {
			if (delegate != null) {
				item.setRenderer(delegate.createRenderer(bitmap));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File getCurrentDir(Preferences prefs) {
		File currentDir = new File(prefs.get(BROWSER_DEFAULT_LOCATION, getDefaultBrowserDir()));
		if (!currentDir.exists() || !currentDir.canWrite() || Boolean.getBoolean("ignore.location")) {
			currentDir = new File(getDefaultBrowserDir());
		}
		if (!currentDir.exists() || !currentDir.canWrite()) {
			currentDir = new File(System.getProperty("user.home"));
		}
		logger.info("currentBrowserDir = " + currentDir.getAbsolutePath());
		return currentDir;
	}

	private String getDefaultBrowserDir() {
		String defaultBrowserDir = System.getProperty(BROWSER_DEFAULT_LOCATION, "[user.home]");
		String userHome = System.getProperty("user.home");
		String userDir = System.getProperty("user.dir");
		String currentDir = new File(".").getAbsoluteFile().getParent();
		defaultBrowserDir = defaultBrowserDir.replace("[current.path]", currentDir);
		defaultBrowserDir = defaultBrowserDir.replace("[user.home]", userHome);
		defaultBrowserDir = defaultBrowserDir.replace("[user.dir]", userDir);
		logger.info("defaultBrowserDir = " + defaultBrowserDir);
		return defaultBrowserDir;
	}
}
