package com.nextbreakpoint.nextfractal.contextfree.javafx;

import com.nextbreakpoint.nextfractal.core.javafx.EventBusPublisher;
import com.nextbreakpoint.nextfractal.core.javafx.MetadataDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import com.nextbreakpoint.nextfractal.core.javafx.viewer.Toolbar;

public class ContextFreeToolbar extends Toolbar {
    public ContextFreeToolbar(MetadataDelegate delegate, EventBusPublisher publisher, ContextFreeToolContext toolContext) {
        super(delegate, publisher, toolContext);
    }
}
