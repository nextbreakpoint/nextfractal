package com.nextbreakpoint.nextfractal.core.javafx;

import com.nextbreakpoint.nextfractal.core.common.Metadata;
import com.nextbreakpoint.nextfractal.core.common.Session;

public interface MetadataDelegate {
    void onMetadataChanged(Metadata metadata, boolean continuous, boolean appendHistory);

    Metadata getMetadata();

    Session newSession(Metadata metadata);

    boolean hasChanged(Session newSession);

    void updateRenderingContext(RenderingContext renderingContext);
}
