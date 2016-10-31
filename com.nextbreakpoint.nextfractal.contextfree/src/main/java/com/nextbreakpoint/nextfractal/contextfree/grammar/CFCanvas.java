package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;

public interface CFCanvas {
    public int getWidth();

    public int getHeight();

    public void primitive(int shapeType, double[] color, AffineTransform transform);

    public void path(double[] color, AffineTransform transform, CommandInfo info);

    public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight);

    public void end();
}
