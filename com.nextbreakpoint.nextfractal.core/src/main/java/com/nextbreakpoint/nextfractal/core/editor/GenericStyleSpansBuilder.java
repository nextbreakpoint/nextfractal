package com.nextbreakpoint.nextfractal.core.editor;

import java.util.ArrayList;
import java.util.List;

public class GenericStyleSpansBuilder<T> {
    private final List<GenericStyleSpan<T>> styleSpans = new ArrayList<>();

    public void add(T styles, int index) {
        styleSpans.add(new GenericStyleSpan<>(styles, index));
    }

    public GenericStyleSpans<T> create() {
        return new GenericStyleSpans<>(styleSpans);
    }
}
