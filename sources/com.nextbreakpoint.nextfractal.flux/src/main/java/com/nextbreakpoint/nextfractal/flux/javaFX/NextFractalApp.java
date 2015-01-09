package com.nextbreakpoint.nextfractal.flux.javaFX;

import java.util.ServiceLoader;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.aquafx_project.AquaFx;
import com.nextbreakpoint.nextfractal.flux.FractalFactory;
import com.nextbreakpoint.nextfractal.flux.FractalSession;

public class NextFractalApp extends Application {
	private BorderPane editorRootPane;
	private BorderPane renderRootPane;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		int width = 800;
		int height = 500;
		int editorWidth = 300;
        primaryStage.setTitle("NextFractal");
        primaryStage.setResizable(false);
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        editorRootPane = new BorderPane();
		editorRootPane.setPrefWidth(editorWidth);
        editorRootPane.setPrefHeight(height);
        editorRootPane.setLayoutX(width - editorWidth);
        editorRootPane.getStyleClass().add("editor-pane");
        renderRootPane = new BorderPane();
        renderRootPane.setPrefWidth(width - editorWidth);
        renderRootPane.setPrefHeight(height);
        renderRootPane.getStyleClass().add("render-pane");
        mainPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(editorRootPane);
        root.getChildren().add(mainPane);
        String pluginId = "Mandelbrot";
		String packageName = "com.nextbreakpoint.generated";
		String className = pluginId + "Fractal";
        FractalSession session = createFractalSession(pluginId);
        session.setPackageName(packageName);
        session.setClassName(className);
        if (session != null) {
        	Pane renderPane = createRenderPane(session, pluginId, width - editorWidth, height);
        	if (renderPane != null) {
        		renderRootPane.setCenter(renderPane);
        	}
        	Pane editorPane = createEditorPane(session, pluginId);
        	if (editorPane != null) {
        		editorRootPane.setCenter(editorPane);
        	}
        }
        Scene scene = new Scene(root);
        AquaFx.style();
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());
		primaryStage.setScene(scene);
        primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				session.terminate();
			}
		});
        session.setSource(getInitialSource());
    }

	private Pane createEditorPane(FractalSession session, String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createEditorPane(session);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private Pane createRenderPane(FractalSession session, String pluginId, int width, int height) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createRenderPane(session, width, height);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private FractalSession createFractalSession(String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createSession();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	protected String getInitialSource() {
		String source = ""
				+ "fractal [z,x,n] {\n"
				+ "\torbit [-1 - 1i,+1 + 1i] {\n"
				+ "\t\ttrap trap1 [0] {\n"
				+ "\t\t\tMOVETO(1);\n"
				+ "\t\t\tLINETO(2);\n"
				+ "\t\t\tLINETO(2 + 2i);\n"
				+ "\t\t\tLINETO(1 + 2i);\n"
				+ "\t\t\tLINETO(1);\n"
				+ "\t\t}\n"
				+ "\t\tloop [0, 2] (|z| > 4 & trap1[z]) {\n"
				+ "\t\t\ty = 0;\n"
				+ "\t\t\tt = 3;\n"
				+ "\t\t\tx = t + 4 + 1i;\n"
				+ "\t\t\tk = t + 4;\n"
				+ "\t\t\tz = x * (y + 5i);\n"
				+ "\t\t\tt = |z|;\n"
				+ "\t\t}\n"
				+ "\t}\n\tcolor [#FF000000] {\n"
				+ "\t\tpalette palette1 [200] {\n"
				+ "\t\t\t[0, #000000] > [100, #FFFFFF];\n"
				+ "\t\t\t[101, #FFFFFF] > [200, #FF0000];\n"
				+ "\t\t}\n"
				+ "\t\trule (real(n) = 0) [0.5] {\n"
				+ "\t\t\t|x|,5,5,5\n"
				+ "\t\t}\n"
				+ "\t\trule (real(n) > 0) [0.5] {\n"
				+ "\t\t\tpalette1[real(n)]\n"
				+ "\t\t}\n"
				+ "\t}\n"
				+ "}\n";
		return source;
	}
	
/*	private class DefaultViewContext implements ViewContext {
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
	}*/
}