package com.nextbreakpoint.nextfractal.core.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class TabPane extends Pane {
    public TabPane() {
        Canvas canvas = new Canvas();
        getChildren().add(canvas);
    }
}
