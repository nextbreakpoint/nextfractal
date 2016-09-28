package com.nextbreakpoint.nextfractal.core.utils;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Block {
    void execute() throws Exception;

    default <T> Callable<T> asCallable(T value) {
        return () -> { this.execute(); return value; };
    }
}
