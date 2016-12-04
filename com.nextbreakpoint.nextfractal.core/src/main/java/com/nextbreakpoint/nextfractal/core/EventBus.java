package com.nextbreakpoint.nextfractal.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class EventBus {
    private static Logger logger = Logger.getLogger(EventBus.class.getName());

    private Map<String, List<EventListener>> listeners = new HashMap<>();
    private List<EventBus> children = new LinkedList<>();
    private EventBus parent;
    private boolean detached;

    public EventBus() {
        this(null);
    }

    public EventBus(EventBus parent) {
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

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
        if (detached) return;
        if (parent != null) {
            parent.postEvent(eventClass, event);
        } else {
            logger.info(eventClass);
            postChildren(eventClass, event);
        }
    }

    private void postChildren(String eventClass, Object event) {
        if (detached) return;
        List<EventListener> l = listeners.get(eventClass);
        if (l != null) {
            List<EventListener> copy = new ArrayList<>(l.size());
            copy.addAll(l);
            copy.forEach(listener -> listener.eventPosted(event));
        }
        children.forEach(child -> child.postChildren(eventClass, event));
    }

    public void detach() {
        if (parent != null) {
            parent.children.remove(this);
            parent = null;
        }
        children.clear();
        detached = true;
    }
}
