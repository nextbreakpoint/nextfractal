package com.nextbreakpoint.nextfractal.core.javafx;

import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.common.SourceError;
import com.nextbreakpoint.nextfractal.core.render.RendererFactory;

import java.util.List;

public interface RenderingStrategy {
    RendererFactory getRenderFactory();

    void updateAndRedraw(long timestampInMillis);

    void updateCoordinators(Session session, boolean continuous, boolean timeAnimation);

    List<SourceError> updateCoordinators(Object report);

    void disposeCoordinators();
}
