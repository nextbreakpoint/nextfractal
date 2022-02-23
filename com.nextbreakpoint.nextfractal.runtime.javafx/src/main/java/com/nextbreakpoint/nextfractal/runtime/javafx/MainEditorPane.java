/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2022 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.runtime.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.EventBus;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.javafx.UIPlugins.tryFindFactory;

public class MainEditorPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    private Map<String, PlatformEventBus> buses = new HashMap<>();
    private Map<String, Pane> panels = new HashMap<>();

    private Session session;

    public MainEditorPane(PlatformEventBus eventBus) {
        eventBus.subscribe("session-data-loaded", event -> handleSessionChanged(eventBus, (Session) event[0], (Boolean) event[1], (Boolean) event[2], this::createRootPane, this::setCenter));

        eventBus.subscribe("session-terminated", event -> buses.clear());
        eventBus.subscribe("session-terminated", event -> panels.clear());
    }

    private void handleSessionChanged(PlatformEventBus eventBus, Session session, boolean continuous, boolean timeAnimation, BiFunction<PlatformEventBus, Session, Pane> factory, Consumer<Pane> consumer) {
        if (this.session == null || !this.session.getPluginId().equals(session.getPluginId())) {
            if (this.session != null) {
                Optional.ofNullable(buses.get(this.session.getPluginId())).ifPresent(EventBus::disable);
            }
            Pane rootPane = panels.get(session.getPluginId());
            if (rootPane == null) {
                PlatformEventBus innerBus = new PlatformEventBus(session.getPluginId(), eventBus);
                rootPane = factory.apply(innerBus, session);
                panels.put(session.getPluginId(), rootPane);
                buses.put(session.getPluginId(), innerBus);
            }
            consumer.accept(rootPane);
            Optional.ofNullable(buses.get(session.getPluginId())).ifPresent(EventBus::enable);
        }
        this.session = session;
    }

    private Pane createRootPane(PlatformEventBus eventBus, Session session) {
        return createRenderPane(session, eventBus).orElse(null);
    }

    private static Try<Pane, Exception> createRenderPane(Session session, PlatformEventBus eventBus) {
        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createEditorPane(eventBus, session)))
            .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor panel with pluginId " + session.getPluginId(), e));
    }
}
