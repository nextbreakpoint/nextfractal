package com.nextbreakpoint.nextfractal.javaFX;

import java.io.File;
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
import com.nextbreakpoint.nextfractal.FractalFactory;
import com.nextbreakpoint.nextfractal.FractalSession;

public class NextFractalApp extends Application {
	private BorderPane editorRootPane;
	private BorderPane renderRootPane;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		int width = 500;
		int height = 500;
		int editorWidth = 300;
        String pluginId = "Mandelbrot";
    	File outDir = new File(System.getProperty("output", "generated"));
//    	if (!outDir.exists()) {
//    		return;
//    	}
//    	try {
//			File lockFile = File.createTempFile("next-fractal", ".lock", outDir);
//		} catch (IOException e) {
//		}
		String packageName = "com.nextbreakpoint.nextfractal.mandelbrot.fractal";
		String className = pluginId;
        primaryStage.setTitle("NextFractal");
        primaryStage.setResizable(false);
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width + editorWidth);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width + editorWidth);
        mainPane.setMinHeight(height);
        editorRootPane = new BorderPane();
		editorRootPane.setPrefWidth(editorWidth);
        editorRootPane.setPrefHeight(height);
        editorRootPane.setLayoutX(width);
        editorRootPane.getStyleClass().add("editor-pane");
        renderRootPane = new BorderPane();
        renderRootPane.setPrefWidth(width);
        renderRootPane.setPrefHeight(height);
        renderRootPane.setMinWidth(width);
        renderRootPane.setMinHeight(height);
        renderRootPane.setMaxWidth(width);
        renderRootPane.setMaxHeight(height);
        renderRootPane.getStyleClass().add("render-pane");
        mainPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(editorRootPane);
        mainPane.getStyleClass().add("application");
        root.getChildren().add(mainPane);
        FractalSession session = createFractalSession(pluginId);
        session.setPackageName(packageName);
        session.setClassName(className);
        session.setOutDir(outDir);
        if (session != null) {
        	Pane renderPane = createRenderPane(session, pluginId, width, height);
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
				+ "fractal {\n"
				+ "\torbit [-2.0 - 1.5i,+1.0 + 1.5i] [z,n] {\n"
//				+ "\torbit [-1 - 1i,+1 + 1i] [z,x,n] {\n"
				+ "\t\tbegin {\n"
				+ "\t\t\tz = x;\n"
				+ "\t\t}\n"
				+ "\t\tloop [0, 200] (|pow(re(z),2)+pow(im(z),2)| > 4) {\n"
				+ "\t\t\tz = z * z + w;\n"
				+ "\t\t}\n"
				+ "\t}\n\tcolor [#FF000000] {\n"
				+ "\t\trule (re(n) = 0) [1.0] {\n"
				+ "\t\t\t1,0,0,0\n"
				+ "\t\t}\n"
				+ "\t\trule (re(n) > 0) [1.0] {\n"
				+ "\t\t\t1,1,1,1\n"
				+ "\t\t}\n"
				+ "\t}\n"
				+ "}\n";
		return source;
	}
}