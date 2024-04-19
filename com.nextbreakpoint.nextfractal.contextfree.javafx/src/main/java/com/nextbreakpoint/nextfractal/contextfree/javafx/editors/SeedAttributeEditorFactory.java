package com.nextbreakpoint.nextfractal.contextfree.javafx.editors;

import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditor;
import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditorFactory;
import com.nextbreakpoint.nextfractal.core.params.Attribute;

public class SeedAttributeEditorFactory implements AttributeEditorFactory {
    @Override
    public String getId() {
        return "key-contextfree-seed";
    }

    @Override
    public AttributeEditor createAttributeEditor(Attribute attribute) {
        return new SeedAttributeEditor(attribute);
    }
}
