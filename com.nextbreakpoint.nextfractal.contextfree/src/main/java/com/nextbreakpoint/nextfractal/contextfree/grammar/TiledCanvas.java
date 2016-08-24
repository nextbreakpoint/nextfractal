package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FriezeType;

import java.awt.geom.AffineTransform;

public class TiledCanvas extends Canvas {
    private Canvas canvas;
    private AffineTransform transform;
    private FriezeType frieze;
    private double scale;

    public TiledCanvas(Canvas canvas, AffineTransform transform, FriezeType frieze) {
        this.canvas = canvas;
        this.transform = transform;
        this.frieze = frieze;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void tileTransform(Bounds bounds) {
        // TODO completare
    }
}
