package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;

import java.awt.geom.AffineTransform;

public class SimpleCanvas {
    private int width;
    private int height;

    public SimpleCanvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void primitive(int shapeType, double[] color, AffineTransform transform) {
        //TODO completare
    }

    public void path(double[] color, AffineTransform tr, CommandInfo attr) {
        //TODO completare
    }

    public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight) {
        //TODO completare
    }

    public void end() {
        //TODO completare
    }

    public void tileTransform(Bounds bounds) {

    }
}
