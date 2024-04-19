package com.nextbreakpoint.nextfractal.core.params;

import com.nextbreakpoint.nextfractal.core.common.Session;
import lombok.Builder;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Builder(setterPrefix = "with")
public class Attribute {
    private String name;
    private String key;
    private String logicalType;
    private boolean readOnly;
    private Function<Session, String> mapper;
    private BiFunction<Session, String, Session> combiner;
}
