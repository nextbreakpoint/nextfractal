package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.session.Session;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryPlugin;

public class MainRenderPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainRenderPane.class.getName());

    private EventBus localEventBus;

    public MainRenderPane(EventBus eventBus, int width, int height) {
        eventBus.subscribe("session-changed", event -> handleSessionChanged(eventBus, width, height, (Session) event));
    }

    private void handleSessionChanged(EventBus eventBus, int width, int height, Session session) {
        setCenter(createRootPane(eventBus, width, height, session));
    }

    private Pane createRootPane(EventBus eventBus, int width, int height, Session session) {
        return createRenderPane(session, getLocalEventBus(eventBus), width, height).orElse(null);
    }

    private EventBus getLocalEventBus(EventBus eventBus) {
        if (localEventBus != null) {
            localEventBus.detach();
        }
        localEventBus = new EventBus(eventBus);
        return localEventBus;
    }

    private static Try<Pane, Exception> createRenderPane(Session session, EventBus eventBus, int width, int height) {
        return tryPlugin(session.getPluginId(), plugin -> Objects.requireNonNull(plugin.createRenderPane(eventBus, session, width, height)))
                .onFailure(e -> logger.log(Level.WARNING, "Cannot create render panel with pluginId " + session.getPluginId(), e));
    }
}
