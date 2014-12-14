package com.nextbreakpoint.nextfractal.flux.ui;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.aquafx_project.AquaFx;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.flux.ui.editor.EditorPane;
import com.nextbreakpoint.nextfractal.flux.ui.render.RenderPane;

public class NextFractalApp extends Application {
	private final Pane editorRootPane = new Pane();
	private final Pane renderRootPane = new Pane();
	private AnimationTimer timer;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		int width = 800;
		int height = 500;
		int configPaneWidth = 300;
        primaryStage.setTitle("NextFractal");
        primaryStage.setResizable(false);
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        VBox editorPane = new VBox();
		editorPane.setPrefWidth(configPaneWidth);
        editorPane.setPrefHeight(height);
        editorPane.setOpacity(0.7);
        editorPane.setLayoutX(width - configPaneWidth);
        StackPane renderPane = new StackPane();
        renderPane.setPrefWidth(width - configPaneWidth);
        renderPane.setPrefHeight(height);
        editorRootPane.getStyleClass().add("editor-panel");
        editorRootPane.setPrefWidth(configPaneWidth);
        editorRootPane.setPrefHeight(height);
        renderRootPane.getStyleClass().add("render-panel");
        renderRootPane.setPrefWidth(width - configPaneWidth);
        renderRootPane.setPrefHeight(height);
        Canvas canvas = new Canvas(width - configPaneWidth, height);
        editorPane.getChildren().add(editorRootPane);
        renderPane.getChildren().add(canvas);
        renderPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(renderPane);
        mainPane.getChildren().add(editorPane);
        root.getChildren().add(mainPane);
        Scene scene = new Scene(root);
        AquaFx.style();
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());
		primaryStage.setScene(scene);
        primaryStage.show();
		execute(width - configPaneWidth, height, canvas);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
			}
		});
		runTimer(canvas);
    }

	private void execute(int width, int height, Canvas canvas) {
		DefaultViewContext context = new DefaultViewContext();
		RenderPane renderPane = new RenderPane(context.getRenderViewSize());
		EditorPane editorPane = new EditorPane(context.getEditorViewSize());
		context.showRenderView(renderPane);
		context.showEditorView(editorPane);
	}

//	private Pane createConfigView(ViewContext viewContext, RenderContext renderContext, TwisterConfig config) {
//		try {
//			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
//			final Extension<ViewExtensionRuntime> extension = TwisterUIRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
//			return extension.createExtensionRuntime().createConfigView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
//		}
//		catch (final ExtensionException x) {
//		}
//		return null;
//	}
//
//	private Pane createEditorView(ViewContext viewContext, RenderContext renderContext, TwisterConfig config) {
//		try {
//			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
//			final Extension<ViewExtensionRuntime> extension = TwisterUIRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
//			return extension.createExtensionRuntime().createEditorView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
//		}
//		catch (final ExtensionException x) {
//		}
//		return null;
//	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
//				if ((time - last) > 20 && runtime != null && runtime.isChanged()) {
//					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
//					//runtime.getFrameElement().getLayer(0).getLayer(0).getImage().getImageRuntime().drawImage(gc);
//					last = time;
//				}
			}
		};
		timer.start();
	}

	private class DefaultViewContext implements ViewContext {
		@Override
		public void showEditorView(Pane node) {
			if (editorRootPane.getChildren().size() > 0) {
				editorRootPane.getChildren().get(editorRootPane.getChildren().size() - 1).setDisable(true);
			}
			node.setLayoutY(editorRootPane.getHeight());
			Dimension2D size = getEditorViewSize();
			node.setLayoutX(editorRootPane.getPadding().getLeft());
//			node.setLayoutY(editorRootPane.getPadding().getTop());
			node.setPrefWidth(size.getWidth());
			node.setPrefHeight(size.getHeight());
			editorRootPane.getChildren().add(node);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromY(0);
			tt.setToY(-editorRootPane.getHeight() + editorRootPane.getPadding().getTop());
			tt.setNode(node);
			tt.play();
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				}
			});
		}

		@Override
		public void discardEditorView() {
			if (editorRootPane.getChildren().size() > 1) {
				Node node = editorRootPane.getChildren().get(editorRootPane.getChildren().size() - 1);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
				tt.setFromY(-editorRootPane.getHeight());
				tt.setToY(0);
				tt.setNode(node);
				tt.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						editorRootPane.getChildren().remove(node);
						if (editorRootPane.getChildren().size() > 0) {
							editorRootPane.getChildren().get(editorRootPane.getChildren().size() - 1).setDisable(false);
						}
					}
				});
				tt.play();
			}
		}

		@Override
		public void showRenderView(Pane node) {
			Dimension2D size = getRenderViewSize();
			node.setLayoutX(renderRootPane.getPadding().getLeft());
			node.setLayoutY(renderRootPane.getPadding().getTop());
			node.setPrefWidth(size.getWidth());
			node.setPrefHeight(size.getHeight());
			renderRootPane.getChildren().add(node);
			FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.setNode(node);
			ft.play();
		}

		@Override
		public void discardRenderView() {
			if (renderRootPane.getChildren().size() > 1) {
				Node node = renderRootPane.getChildren().get(renderRootPane.getChildren().size() - 1);
				FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setNode(node);
				ft.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						renderRootPane.getChildren().remove(node);
					}
				});
				ft.play();
			}
		}

		@Override
		public Dimension2D getEditorViewSize() {
			return new Dimension2D(editorRootPane.getWidth() - editorRootPane.getPadding().getLeft() - editorRootPane.getPadding().getRight(), editorRootPane.getHeight() - editorRootPane.getPadding().getTop() - editorRootPane.getPadding().getBottom());
		}

		@Override
		public Dimension2D getRenderViewSize() {
			return new Dimension2D(renderRootPane.getWidth() - renderRootPane.getPadding().getLeft() - renderRootPane.getPadding().getRight(), renderRootPane.getHeight() - renderRootPane.getPadding().getTop() - renderRootPane.getPadding().getBottom());
		}
	}
}