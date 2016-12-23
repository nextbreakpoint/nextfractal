package com.nextbreakpoint.nextfractal.core;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bundle {
    private Session session;
    private List<Clip> clips;

    public Bundle(Session session, List<Clip> clips) {
        this.session = Objects.requireNonNull(session);
        this.clips = Objects.requireNonNull(clips);
    }

    public Session getSession() {
        return session;
    }

    public List<Clip> getClips() {
        return Collections.unmodifiableList(clips);
    }
}
