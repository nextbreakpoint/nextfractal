package com.nextbreakpoint.nextfractal;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JFXMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NextFractal");
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color:#444444");
        AnchorPane mainPane = new AnchorPane();
        GridPane configPane = new GridPane();
        configPane.setPrefWidth(300.0);
        configPane.setOpacity(0.7);
        configPane.setStyle("-fx-background-color:#777777");
        int width = 600;
		int height = 600;
		WritableImage image = new WritableImage(width, height);
        ImageView imageView = new ImageView(image);
		mainPane.getChildren().add(imageView);
		mainPane.getChildren().add(configPane);
        mainPane.setPrefWidth(900);
        mainPane.setPrefHeight(width);
        AnchorPane.setBottomAnchor(configPane, 0.0);
        AnchorPane.setRightAnchor(configPane, 0.0);
        AnchorPane.setTopAnchor(configPane, 0.0);
        AnchorPane.setBottomAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setTopAnchor(imageView, 0.0);
        root.getChildren().add(mainPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        int[] buffer = new int[width * height];
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (int i = 0, y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int p = ((int)Math.rint(Math.random() * 255) & 0xFF);
						buffer[i + x] = 0xFF000000 | (p << 16) | (p << 8) | p;
					}
					i += width;
				}
				image.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), buffer , 0, width);
			}
		};
		timer.start();
    }
}