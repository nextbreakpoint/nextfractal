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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;

public class BrowsePane extends Pane {
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRendererFactory renderFactory;
	private final MandelbrotImageGenerator generator;
	private final int numOfRows = 4;
	private final int numOfColumns = 4;
	private BorderPane box = new BorderPane();
	private BrowseDelegate delegate; 
	private FileChooser fileChooser;
	private File currentFolder;
	private ExecutorService executor;
	private RendererSize size;
	private RendererTile tile;

	public BrowsePane(int size) {
		
		threadFactory = new DefaultThreadFactory("MandelbrotEditorPane", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		executor = Executors.newSingleThreadExecutor(threadFactory);

		this.size = new RendererSize(size, size);
		this.tile = createSingleTile(size, size);
		
		generator = new MandelbrotImageGenerator(threadFactory, renderFactory, tile);
		
//		ListView<MandelbrotData[]> fileList = new ListView<>();
//		fileList.setFixedCellSize(size);
//		fileList.getStyleClass().add("browser");
//		fileList.setCellFactory(new Callback<ListView<MandelbrotData[]>, ListCell<MandelbrotData[]>>() {
//			@Override
//			public ListCell<MandelbrotData[]> call(ListView<MandelbrotData[]> listView) {
//				return new BrowseListCell(numOfColumns, generators[0].getSize(), generatorTile);
//			}
//		});

		Button closeButton = new Button("Close");
		Button chooseButton = new Button("Choose");

		HBox buttons = new HBox(10);
		buttons.getChildren().add(chooseButton);
		buttons.getChildren().add(closeButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.getStyleClass().add("buttons");

		//TODO to be completed
		MandelbrotData[] data = new MandelbrotData[8];
		for (int i = 0; i < 8; i++) {
			data[i] = new MandelbrotData();
		}
		
		GridView grid = new GridView(numOfRows, numOfColumns, size);
		
		grid.setData(data);
		
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

	private void createFileChooser() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private void doChooseFolder(GridView grid) {
		createFileChooser();
		fileChooser.setTitle("Choose");
		if (currentFolder != null) {
			fileChooser.setInitialDirectory(currentFolder);
		}
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			currentFolder = file;
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
//		fileList.getItems().clear();
		File[] plainFiles = folder.getParentFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".m");
			}
		});
		if (plainFiles == null) {
			return;
		}
//		MandelbrotDataStore service = new MandelbrotDataStore();
//		for (int i = 0; i < plainFiles.length; i += numOfColumns) {
//			File[] files = new File[numOfColumns];
//			for (int j = 0; j < numOfColumns; j++) {
//				files[j] = null;
//				if (i + j < plainFiles.length) {
//					files[j] = plainFiles[i + j];
//				}
//			}
//			MandelbrotData[] dataArray = new MandelbrotData[numOfColumns];
//			for (int j = 0; j < numOfColumns; j++) {
//				if (files[j] != null) {
//					try {
//						dataArray[j] = service.loadFromFile(files[j]);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			fileExecutor.submit(new Runnable() {
//				@Override
//				public void run() {
//					for (int j = 0; j < 4; j++) {
//						if (dataArray[j] != null) {
//							dataArray[j].setPixels(generators[j].renderImage(dataArray[j]));
//						}
//					}
//					Platform.runLater(new Runnable() {
//						@Override
//						public void run() {
//							fileList.getItems().add(dataArray);
//						}
//					});
//				}
//			});
//		}
	}

	private void renderImage(GraphicsContext g2d, MandelbrotData data) {
		data.setPixels(generator.renderImage(data));
		WritableImage image = new WritableImage(size.getWidth(), size.getHeight());
		image.getPixelWriter().setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), PixelFormat.getIntArgbInstance(), data.getPixels(), (int)image.getWidth());
		Affine affine = new Affine();
		int x = (tile.getTileSize().getWidth() - size.getWidth()) / 2;
		int y = (tile.getTileSize().getHeight() - size.getHeight()) / 2;
		affine.append(Affine.translate(0, +image.getHeight() / 2 + y));
		affine.append(Affine.scale(1, -1));
		affine.append(Affine.translate(0, -image.getHeight() / 2 - y));
		g2d.setTransform(affine);
		g2d.drawImage(image, x, y);
	}
}
