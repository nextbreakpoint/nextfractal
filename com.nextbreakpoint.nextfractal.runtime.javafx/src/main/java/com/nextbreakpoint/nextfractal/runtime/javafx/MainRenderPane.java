/*
 * NextFractal 2.1.5
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
package com.nextbreakpoint.nextfractal.runtime.javafx;

import com.nextbreakpoint.nextfractal.core.common.EventBus;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.event.PlaybackDataLoaded;
import com.nextbreakpoint.nextfractal.core.event.SessionDataLoaded;
import com.nextbreakpoint.nextfractal.core.event.SessionTerminated;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import com.nextbreakpoint.nextfractal.runtime.javafx.utils.ApplicationUtils;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Level;

@Log
public class MainRenderPane extends BorderPane {
    private final Map<String, PlatformEventBus> buses = new HashMap<>();
    private final Map<String, Pane> panels = new HashMap<>();

    private Session session;

    public MainRenderPane(PlatformEventBus eventBus, int width, int height) {
        final BiFunction<PlatformEventBus, Session, Pane> createRootPane = (innerBus, session) -> createRootPane(innerBus, session, width, height);

        eventBus.subscribe(SessionDataLoaded.class.getSimpleName(), event -> handleSessionChanged(eventBus, ((SessionDataLoaded) event).session(), createRootPane, this::setCenter));

        eventBus.subscribe(PlaybackDataLoaded.class.getSimpleName(), event -> handleSessionChanged(eventBus, ((PlaybackDataLoaded) event).session(), createRootPane, this::setCenter));

        eventBus.subscribe(SessionTerminated.class.getSimpleName(), event -> buses.clear());
        eventBus.subscribe(SessionTerminated.class.getSimpleName(), event -> panels.clear());
    }

    private void handleSessionChanged(PlatformEventBus eventBus, Session session, BiFunction<PlatformEventBus, Session, Pane> factory, Consumer<Pane> consumer) {
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

    private Pane createRootPane(PlatformEventBus eventBus, Session session, int width, int height) {
        return ApplicationUtils.createRenderPane(eventBus, session, width, height)
                .onFailure(e -> log.log(Level.WARNING, "Can't create render panel: plugin " + session.getPluginId(), e))
                .orElse(null);
    }
}
