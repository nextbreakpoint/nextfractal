/*
 * NextFractal 2.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
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
    private static Logger logger = Logger.getLogger(EventBus.class.getName());
    private Map<String, EventValidator> validators = new HashMap<>();
    private Map<String, List<EventListener>> listeners = new HashMap<>();
    private List<EventBus> children = new LinkedList<>();
    private EventBus parent;
    private String name;
    private volatile boolean disabled;

    public EventBus(String name) {
        this(name, null);
    }

    public EventBus(String name, EventBus parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public final void register(String channel, EventValidator validator) {
        if (validators.containsKey(channel)) {
            throw new RuntimeException("Channel " + channel + " already exists");
        }
        validators.put(channel, validator);
    }

    public final void unregister(String channel) {
        if (!validators.containsKey(channel)) {
            throw new RuntimeException("Channel not found " + channel);
        }
        validators.remove(channel);
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
            if (listeners.size() == 0) {
                this.listeners.remove(channel);
            }
        }
    }

    public abstract void postEvent(String channel, Object... event);

    protected final void propagateRoot(String channel, Exception error, Object[] event) {
        logger.log(Level.INFO, "bus: " + name + " propagate root: " + channel);
        try {
            if (parent != null) {
                parent.propagateRoot(channel, error, event);
            } else {
                propagateChildren(channel, error, event);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while propagating event", e);
            logger.log(Level.WARNING, "The event was posted here", error);
        }
    }

    private void propagateChildren(String channel, Exception error, Object... event) {
        if (disabled) return;
        logger.log(Level.INFO, "bus: " + name + " propagate children: " + channel);
        final EventValidator validator = validators.get(channel);
        if (validator != null) {
            if (validator.validate(event)) {
                List<EventListener> listeners = this.listeners.get(channel);
                if (listeners != null) {
                    listeners.stream().forEach(listener -> listener.eventPosted(event));
                }
            } else {
                logger.log(Level.WARNING, "Event parameters not valid");
            }
        } else {
            // TODO remove this when validators have been implemented
            List<EventListener> listeners = this.listeners.get(channel);
            if (listeners != null) {
                listeners.stream().forEach(listener -> listener.eventPosted(event));
            }
        }
        children.forEach(child -> child.propagateChildren(channel, error, event));
    }

    public final void disable() {
        disabled = true;
    }

    public final void enable() {
        disabled = false;
    }
}
