package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.session.Session;

public interface JobsDelegate {
    void sessionStarted(Session session);

    void sessionStopped(Session session);

    void sessionSuspended(Session session);

    void sessionResumed(Session session);
}
