package com.nextbreakpoint.nextfractal.runtime.javafx.utils;

import com.nextbreakpoint.nextfractal.core.common.DefaultThreadFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadUtils {
    public static DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }
}
