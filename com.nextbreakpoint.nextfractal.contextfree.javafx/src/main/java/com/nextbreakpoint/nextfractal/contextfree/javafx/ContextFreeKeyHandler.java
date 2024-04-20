package com.nextbreakpoint.nextfractal.contextfree.javafx;

import com.nextbreakpoint.nextfractal.core.javafx.KeyHandler;
import com.nextbreakpoint.nextfractal.core.javafx.MetadataDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.RenderingContext;
import javafx.scene.input.KeyEvent;

public class ContextFreeKeyHandler implements KeyHandler {
    private final RenderingContext renderingContext;
    private final MetadataDelegate delegate;

    public ContextFreeKeyHandler(RenderingContext renderingContext, MetadataDelegate delegate) {
        this.renderingContext = renderingContext;
        this.delegate = delegate;
    }

    @Override
    public void handle(KeyEvent event) {
    }
}
