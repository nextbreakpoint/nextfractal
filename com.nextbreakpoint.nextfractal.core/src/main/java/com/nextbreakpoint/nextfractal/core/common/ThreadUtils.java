package com.nextbreakpoint.nextfractal.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadUtils {
    public static DefaultThreadFactory createThreadFactory(String name) {
        return createThreadFactory(name, Thread.MIN_PRIORITY);
    }

    public static DefaultThreadFactory createThreadFactory(String name, int priority) {
        return new DefaultThreadFactory(name, true, priority);
    }
}
