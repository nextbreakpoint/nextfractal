package com.nextbreakpoint.nextfractal.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Clip {
    private List<ClipEvent> events = new LinkedList<>();

    public void append(Date date, String pluginId, String script, Object metadata) {
        events.add(new ClipEvent(date, pluginId, script, metadata));
    }

    public ClipEvent getLastEvent() {
        return events.get(events.size() - 1);
    }

    public Stream<ClipEvent> events() {
        return events.stream();
    }

    public List<ClipEvent> getEvents() {
        return new ArrayList<>(events);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }
}
