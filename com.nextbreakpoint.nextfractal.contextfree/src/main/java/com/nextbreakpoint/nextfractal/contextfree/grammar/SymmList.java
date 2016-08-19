package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

public class SymmList {
    private List<AffineTransform> syms = new ArrayList<>();

    public void clear() {
        syms.clear();
    }

    public boolean contains(AffineTransform transform) {
        return syms.contains(transform);
    }

    public void add(AffineTransform transform) {
        syms.add(transform);
    }

    public int size() {
        return syms.size();
    }

    public AffineTransform getTransform(int index) {
        return syms.get(index);
    }
}
