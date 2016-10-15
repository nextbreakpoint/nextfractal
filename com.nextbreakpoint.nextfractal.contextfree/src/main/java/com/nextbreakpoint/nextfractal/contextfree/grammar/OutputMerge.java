package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.List;
import java.util.function.Function;

public class OutputMerge {
    private List<FinishedShape> finishedShapes;

    public void addShapes(List<FinishedShape> finishedShapes) {
        this.finishedShapes = finishedShapes;
    }

    public void merge(Function<FinishedShape, Object> shapeFunction) {
        finishedShapes.stream().forEach(shape -> shapeFunction.apply(shape));
    }
}
