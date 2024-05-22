package com.nextbreakpoint.nextfractal.contextfree.javafx.editors;

import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.javafx.AttributeEditor;
import com.nextbreakpoint.nextfractal.core.params.Attribute;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.scene.control.Tooltip;

import java.util.concurrent.TimeUnit;

public class SeedAttributeEditor extends AttributeEditor {
    private final Attribute attribute;
    private final AdvancedTextField textField;

    public SeedAttributeEditor(Attribute attribute) {
        this.attribute = attribute;

        textField = new AdvancedTextField();
        textField.setRestrict(getRestriction());
        textField.setTransform(String::toUpperCase);
        textField.setTooltip(new Tooltip(attribute.getName()));

        setCenter(textField);

        JavaFxObservable.changesOf(textField.textProperty())
                .subscribeOn(JavaFxScheduler.platform())
                .throttleWithTimeout(500, TimeUnit.MILLISECONDS)
                .subscribe(result -> {
                    if (getDelegate() != null) {
                        getDelegate().onEditorChanged(this);
                    }
                });
    }

    @Override
    public void loadSession(Session session) {
        textField.setText(attribute.getMapper().apply(session));
    }

    @Override
    public Session updateSession(Session session) {
        return attribute.getCombiner().apply(session, textField.getText());
    }

    private String getRestriction() {
        return "[A-Z]*";
    }
}
