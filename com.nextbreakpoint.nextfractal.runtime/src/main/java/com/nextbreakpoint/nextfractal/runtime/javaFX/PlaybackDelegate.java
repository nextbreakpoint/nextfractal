package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.nextfractal.core.Session;

public interface PlaybackDelegate {
    void playbackStopped();

    void loadSessionData(Session session, boolean continuous);

    void updateSessionData(Session session, boolean continuous);
}
