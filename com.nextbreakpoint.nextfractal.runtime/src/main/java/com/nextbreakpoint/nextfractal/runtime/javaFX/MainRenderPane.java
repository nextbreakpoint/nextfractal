package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.session.Session;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.runtime.Plugins.tryPlugin;

public class MainRenderPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainRenderPane.class.getName());

    public MainRenderPane(EventBus eventBus, int width, int height) {
        eventBus.subscribe("session-changed", event -> handleSessionChanged((Session)event, eventBus, width, height));
    }

    private void handleSessionChanged(Session session, EventBus eventBus, int width, int height) {
        setCenter(createRenderPane(session, eventBus, width, height).orElse(null));
    }

    private Try<Pane, Exception> createRenderPane(Session session, EventBus eventBus, int width, int height) {
        return tryPlugin(session.getPluginId(), plugin -> Objects.requireNonNull(plugin.createRenderPane(session, eventBus, width, height)))
                .onFailure(e -> logger.log(Level.WARNING, "Cannot create render panel with pluginId " + session.getPluginId(), e));
    }
}
