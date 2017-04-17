/*
 * NextFractal 2.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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

    default Try<V, Exception> tryExecute() {
        return Try.of(this.toCallable(null));
    }

    default Try<V, Exception> tryExecute(V accumulator) {
        return Try.of(this.toCallable(accumulator));
    }
}
