/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {
    private Map<String, List<EventListener>> listeners = new HashMap<>();
    private List<EventBus> children = new LinkedList<>();
    private EventBus parent;
    private boolean disabled;

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

    public void postEvent(String eventClass, Object... event) {
        postParent(eventClass, event);
    }

    private void postParent(String eventClass, Object... event) {
        if (disabled) return;
        if (parent != null) {
            parent.postParent(eventClass, event);
        } else {
            postChildren(eventClass, event);
        }
    }

    private void postChildren(String eventClass, Object... event) {
        if (disabled) return;
        List<EventListener> l = listeners.get(eventClass);
        if (l != null) {
            List<EventListener> copy = new ArrayList<>(l.size());
            copy.addAll(l);
            copy.forEach(listener -> listener.eventPosted(event));
        }
        children.forEach(child -> child.postChildren(eventClass, event));
    }

    public void disable() {
        disabled = true;
    }

    public void enable() {
        disabled = false;
    }
}
