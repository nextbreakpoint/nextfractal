package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;

public class Canvas {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void primitive(int shapeType, double[] color, AffineTransform transform) {
    }

    public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight) {

    }

    public void end() {

    }

    public void path(double[] color, AffineTransform tr, CommandInfo attr) {

    }
}
