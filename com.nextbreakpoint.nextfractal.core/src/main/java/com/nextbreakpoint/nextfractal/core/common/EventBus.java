/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());
    private final Map<String, List<EventListener>> listeners = new HashMap<>();
    private final List<EventBus> children = new LinkedList<>();
    private final EventBus parent;
    private final String name;
    private volatile boolean disabled;

    public EventBus(String name) {
        this(name, null);
    }

    //TODO remove support for parent bus
    public EventBus(String name, EventBus parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public final void subscribe(String channel, EventListener listener) {
        List<EventListener> listeners = this.listeners.get(channel);
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
        this.listeners.put(channel, listeners);
    }

    public final void unsubscribe(String channel, EventListener listener) {
        List<EventListener> listeners = this.listeners.get(channel);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(channel);
            }
        }
    }

    public abstract void postEvent(Object event);

    protected final void processEvent(String channel, Object event) {
        try {
            logger.log(Level.FINE, "Event posted on bus: " + name + ", channel: " + channel + ": " + event.toString());
            if (parent != null) {
                parent.processEvent(channel, event);
            } else {
                dispatchEvent(channel, event);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while propagating event", e);
        }
    }

    private void dispatchEvent(String channel, Object event) {
        if (disabled) return;
        logger.log(Level.FINE, "Event dispatched to bus: " + name + ", channel: " + channel);
        List<EventListener> listeners = this.listeners.get(channel);
        if (listeners != null) {
            listeners.forEach(listener -> listener.onEvent(event));
        }
        children.forEach(child -> child.dispatchEvent(channel, event));
    }

    public final void disable() {
        disabled = true;
    }

    public final void enable() {
        disabled = false;
    }
}
