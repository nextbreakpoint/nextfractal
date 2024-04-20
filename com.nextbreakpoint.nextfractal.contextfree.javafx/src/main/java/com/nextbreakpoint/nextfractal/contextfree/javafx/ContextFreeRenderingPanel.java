package com.nextbreakpoint.nextfractal.contextfree.javafx;

import com.nextbreakpoint.nextfractal.core.javafx.RenderingContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class ContextFreeRenderingPanel extends Pane {
    public ContextFreeRenderingPanel(RenderingContext renderingContext, int width, int height) {
        Canvas fractalCanvas = new Canvas(width, height);
        GraphicsContext gcFractalCanvas = fractalCanvas.getGraphicsContext2D();
        gcFractalCanvas.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gcFractalCanvas.fillRect(0, 0, width, height);

        Canvas toolCanvas = new Canvas(width, height);
        GraphicsContext gcToolCanvas = toolCanvas.getGraphicsContext2D();
        gcToolCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcToolCanvas.fillRect(0, 0, width, height);

        getChildren().add(fractalCanvas);
        getChildren().add(toolCanvas);

        renderingContext.registerCanvas("fractal", fractalCanvas);
        renderingContext.registerCanvas("tool", toolCanvas);
    }
}