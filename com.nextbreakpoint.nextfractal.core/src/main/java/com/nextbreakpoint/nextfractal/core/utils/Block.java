package com.nextbreakpoint.nextfractal.core.utils;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Block<E extends Exception> {
    void execute() throws E;

    default <T> Callable<T> asCallable(T value) {
        return () -> { this.execute(); return value; };
    }

    default Block<E> andThen(Block<E> block) {
        return () -> { this.execute(); block.execute(); };
    }

    static <X extends  Exception> Block<X> create() {
        return () -> {};
    }
}
