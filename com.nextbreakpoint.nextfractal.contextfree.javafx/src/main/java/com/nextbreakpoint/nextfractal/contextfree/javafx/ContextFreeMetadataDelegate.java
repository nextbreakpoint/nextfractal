package com.nextbreakpoint.nextfractal.contextfree.javafx;

import com.nextbreakpoint.nextfractal.contextfree.module.ContextFreeMetadata;
import com.nextbreakpoint.nextfractal.contextfree.module.ContextFreeSession;
import com.nextbreakpoint.nextfractal.core.common.Metadata;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.event.RenderDataChanged;
import com.nextbreakpoint.nextfractal.core.javafx.MetadataDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import com.nextbreakpoint.nextfractal.core.javafx.RenderingContext;

import java.util.function.Supplier;

public class ContextFreeMetadataDelegate implements MetadataDelegate {
    private final PlatformEventBus eventBus;
    private final Supplier<Session> supplier;

    public ContextFreeMetadataDelegate(PlatformEventBus eventBus, Supplier<Session> supplier) {
        this.eventBus = eventBus;
        this.supplier = supplier;
    }

    @Override
    public void onMetadataChanged(Metadata metadata, boolean continuous, boolean appendHistory) {
        final ContextFreeSession newSession = ((ContextFreeSession) supplier.get()).toBuilder().withMetadata((ContextFreeMetadata) metadata).build();
        eventBus.postEvent(RenderDataChanged.builder().session(newSession).continuous(continuous).appendToHistory(appendHistory).build());
    }

    @Override
    public Metadata getMetadata() {
        return supplier.get().getMetadata();
    }

    @Override
    public Session newSession(Metadata metadata) {
        return ((ContextFreeSession) supplier.get()).toBuilder().withMetadata((ContextFreeMetadata) metadata).build();
    }

    @Override
    public boolean hasChanged(Session newSession) {
        return !supplier.get().equals(newSession);
    }

    @Override
    public void updateRenderingContext(RenderingContext renderingContext) {
    }
}
