package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;

/**
 * Created by andrea on 25/11/2016.
 */
public interface GridItemRenderer {
    void abort();

    void waitFor();

    void dispose();

    boolean isPixelsChanged();

    void drawImage(RendererGraphicsContext gc, int x, int y);
}
