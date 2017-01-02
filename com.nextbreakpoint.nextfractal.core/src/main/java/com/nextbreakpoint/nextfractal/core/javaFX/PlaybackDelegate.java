package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.Session;

public interface PlaybackDelegate {
    void playbackStopped();

    void loadSessionData(Session session, boolean continuous, boolean imeAnimation);

    void updateSessionData(Session session, boolean continuous, boolean timeAnimation);
}
