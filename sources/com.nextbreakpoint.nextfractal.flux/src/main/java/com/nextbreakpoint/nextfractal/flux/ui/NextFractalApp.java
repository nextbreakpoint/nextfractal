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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.aquafx_project.AquaFx;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;

public class NextFractalApp extends Application {
	private final Pane configViewPane = new Pane();
	private final Pane editorViewPane = new Pane();
	private final Button close = new Button("<");
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
        VBox configPane = new VBox(10);
		configPane.setPrefWidth(configPaneWidth);
        configPane.setPrefHeight(height);
        configPane.setOpacity(0.7);
        configPane.setLayoutX(width - configPaneWidth);
        configPane.setId("config-panel");
        StackPane editorPane = new StackPane();
        editorPane.setPrefWidth(width - configPaneWidth);
        editorPane.setPrefHeight(height);
        configViewPane.setPrefWidth(configPaneWidth);
        configViewPane.setPrefHeight(height - 40);
        editorViewPane.setPrefWidth(width - configPaneWidth);
        editorViewPane.setPrefHeight(height);
        close.setVisible(false);
        Canvas canvas = new Canvas(width - configPaneWidth, height);
        configPane.getChildren().add(close);
        configPane.getChildren().add(configViewPane);
        editorPane.getChildren().add(canvas);
        editorPane.getChildren().add(editorViewPane);
        editorPane.setId("editor-panel");
        mainPane.getChildren().add(editorPane);
        mainPane.getChildren().add(configPane);
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
		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#showConfigView(com.nextbreakpoint.nextfractal.core.ui.javafx.View)
		 */
		@Override
		public void showConfigView(Pane node) {
			if (configViewPane.getChildren().size() > 0) {
				configViewPane.getChildren().get(configViewPane.getChildren().size() - 1).setDisable(true);
			}
			close.setDisable(true);
			node.setLayoutY(configViewPane.getHeight());
			node.setPrefWidth(configViewPane.getWidth());
			node.setPrefHeight(configViewPane.getHeight());
			configViewPane.getChildren().add(node);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromY(0);
			tt.setToY(-configViewPane.getHeight());
			tt.setNode(node);
			tt.play();
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (configViewPane.getChildren().size() > 1) {
						close.setVisible(true);
					}
					close.setDisable(false);
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#discardConfigView()
		 */
		@Override
		public void discardConfigView() {
			if (configViewPane.getChildren().size() > 1) {
				close.setDisable(true);
				Node node = configViewPane.getChildren().get(configViewPane.getChildren().size() - 1);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
				tt.setFromY(-configViewPane.getHeight());
				tt.setToY(0);
				tt.setNode(node);
				tt.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						configViewPane.getChildren().remove(node);
						if (node instanceof Disposable) {
							((Disposable)node).dispose();
						}
						if (configViewPane.getChildren().size() <= 1) {
							close.setVisible(false);
						}
						if (configViewPane.getChildren().size() > 0) {
							configViewPane.getChildren().get(configViewPane.getChildren().size() - 1).setDisable(false);
						}
						close.setDisable(false);
					}
				});
				tt.play();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#showEditorView(com.nextbreakpoint.nextfractal.core.ui.javafx.View)
		 */
		@Override
		public void showEditorView(Pane node) {
			node.setPrefWidth(editorViewPane.getWidth());
			node.setPrefHeight(editorViewPane.getHeight());
			editorViewPane.getChildren().add(node);
			FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.setNode(node);
			ft.play();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#discardEditorView()
		 */
		@Override
		public void discardEditorView() {
			if (editorViewPane.getChildren().size() > 1) {
				Node node = editorViewPane.getChildren().get(editorViewPane.getChildren().size() - 1);
				FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setNode(node);
				ft.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						editorViewPane.getChildren().remove(node);
						if (node instanceof Disposable) {
							((Disposable)node).dispose();
						}
					}
				});
				ft.play();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#getConfigViewSize()
		 */
		@Override
		public Dimension2D getConfigViewSize() {
			return new Dimension2D(configViewPane.getWidth(), configViewPane.getHeight());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#getEditorViewSize()
		 */
		@Override
		public Dimension2D getEditorViewSize() {
			return new Dimension2D(editorViewPane.getWidth(), editorViewPane.getHeight());
		}
	}
}