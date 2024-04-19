package com.nextbreakpoint.nextfractal.core.javafx.params.editors;

import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditor;
import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditorFactory;
import com.nextbreakpoint.nextfractal.core.params.Attribute;

public class DoubleAttributeEditorFactory implements AttributeEditorFactory {
    @Override
    public String getId() {
        return "logical-type-double";
    }

    @Override
    public AttributeEditor createAttributeEditor(Attribute attribute) {
        return new DoubleAttributeEditor(attribute);
    }
}
