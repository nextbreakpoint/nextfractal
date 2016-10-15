package com.nextbreakpoint.nextfractal.core.utils;

import com.nextbreakpoint.Try;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface Block<V, E extends Exception> {
    void execute(V accumulator) throws E;

    default Callable<V> toCallable(V accumulator) {
        return () -> { this.execute(accumulator); return accumulator; };
    }

    default Block<V, E> andThen(Block<V, E> block) {
        return accumulator -> { this.execute(accumulator); block.execute(accumulator); };
    }

    static <V, X extends Exception> Block<V, X> create(Class<V> clazz) {
        return accumulator -> {};
    }

    static <V, X extends Exception> Block<V, X> create(Block<V, X> block) {
        return block;
    }

    default Try<V, E> tryExecute() {
        return Try.of(this.toCallable(null)).mapper(e -> (E)e).execute();
    }

    default Try<V, E> tryExecute(V accumulator) {
        return Try.of(this.toCallable(accumulator)).mapper(e -> (E)e).execute();
    }
}
