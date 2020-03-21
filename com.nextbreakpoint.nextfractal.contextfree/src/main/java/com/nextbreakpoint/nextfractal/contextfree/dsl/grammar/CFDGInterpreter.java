package com.nextbreakpoint.nextfractal.contextfree.dsl.grammar;

import com.nextbreakpoint.nextfractal.core.render.RendererSize;

public class CFDGInterpreter {
    private CFDG cfdg;

    public CFDGInterpreter(CFDG cfdg) {
        this.cfdg = cfdg;
    }

    public CFDGRenderer create(RendererSize imageSize, String seed, CFDGLogger logger) {
        cfdg.getDriver().setLogger(logger);
        cfdg.rulesLoaded();
        return cfdg.renderer(imageSize.getWidth(), imageSize.getHeight(), 1, seed.hashCode(), 0.1);
    }
}
