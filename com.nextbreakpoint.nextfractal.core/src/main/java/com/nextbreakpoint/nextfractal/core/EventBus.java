package com.nextbreakpoint.nextfractal.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(String eventClass, EventListener listener) {
        List<EventListener> l = listeners.get(eventClass);
        if (l == null) {
            l = new ArrayList<>();
        }
        l.add(listener);
        listeners.put(eventClass, l);
    }

    public void unsubscribe(String eventClass, EventListener listener) {
        List<EventListener> l = listeners.get(eventClass);
        if (l != null) {
            l.remove(listener);
            if (l.size() == 0) {
                listeners.remove(eventClass);
            }
        }
    }

    public void postEvent(String eventClass, Object event) {
        List<EventListener> l = listeners.get(eventClass);
        if (l != null) {
            l.forEach(listener -> listener.eventPosted(event));
        }
    }
}
