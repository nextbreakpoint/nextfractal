package com.nextbreakpoint.nextfractal.mandelbrot.javafx.editors;

import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditor;
import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditorFactory;
import com.nextbreakpoint.nextfractal.core.params.Attribute;

public class AlgorithmAttributeEditorFactory implements AttributeEditorFactory {
    @Override
    public String getId() {
        return "key-mandelbrot-algorithm";
    }

    @Override
    public AttributeEditor createAttributeEditor(Attribute attribute) {
        return new AlgorithmAttributeEditor(attribute);
    }
}
