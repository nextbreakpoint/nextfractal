package com.nextbreakpoint.nextfractal.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Clip {
    private List<ClipEvent> events = new LinkedList<>();

    public Clip() {
    }

    public Clip(List<ClipEvent> events) {
        this.events.addAll(events);
    }

    public void append(Date date, String pluginId, String script, Metadata metadata) {
        events.add(new ClipEvent(date, pluginId, script, metadata));
    }

    public ClipEvent getFirstEvent() {
        return events.get(0);
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

    public long duration() {
        return events.size() > 1 ? events.get(events.size() - 1).getDate().getTime() - events.get(0).getDate().getTime() : 0;
    }
}
