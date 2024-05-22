package com.nextbreakpoint.nextfractal.core.common;

import com.nextbreakpoint.nextfractal.core.params.Parameters;

public interface ParamsStrategy {
    Parameters create(Session session);
}
