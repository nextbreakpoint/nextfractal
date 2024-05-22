package com.nextbreakpoint.nextfractal.core.params;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "with")
public class Parameters {
    private List<Group> groups;
}
