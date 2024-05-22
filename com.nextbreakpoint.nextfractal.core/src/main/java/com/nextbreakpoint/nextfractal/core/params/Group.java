package com.nextbreakpoint.nextfractal.core.params;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder(setterPrefix = "with")
public class Group {
    private String name;
    private List<Attribute> attributes;
}
