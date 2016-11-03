package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FriezeType;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

import java.awt.*;

public class TiledCanvas extends SimpleCanvas {
    private FriezeType frieze;
    private double scale;

    public TiledCanvas(Graphics2D g2d, RendererTile tile, FriezeType frieze) {
        //TODO rivedere
        super(g2d, tile);
        this.frieze = frieze;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void tileTransform(Bounds bounds) {
        //TODO completare
    }
}
