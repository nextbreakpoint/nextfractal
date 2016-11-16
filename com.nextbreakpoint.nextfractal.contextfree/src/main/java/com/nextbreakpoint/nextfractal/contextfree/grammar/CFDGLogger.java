package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.renderer.RendererError;
import org.antlr.v4.runtime.Token;

public class CFDGLogger extends Logger {
    private List<RendererError> errors = new ArrayList<>();

    @Override
    public void error(String message, Token location) {
        super.error(message, location);
        if (location != null) {
            errors.add(new RendererError(location.getLine(), location.getCharPositionInLine(), location.getStartIndex(), location.getStopIndex() - location.getStartIndex(), message));
        } else {
            errors.add(new RendererError(0, 0, 0, 0, message));
        }
    }

    @Override
    public void fail(String message, Token location) {
        super.fail(message, location);
        if (location != null) {
            errors.add(new RendererError(location.getLine(), location.getCharPositionInLine(), location.getStartIndex(), location.getStopIndex() - location.getStartIndex(), message));
        } else {
            errors.add(new RendererError(0, 0, 0, 0, message));
        }
    }

    public List<RendererError> getErrors() {
        return errors;
    }
}
