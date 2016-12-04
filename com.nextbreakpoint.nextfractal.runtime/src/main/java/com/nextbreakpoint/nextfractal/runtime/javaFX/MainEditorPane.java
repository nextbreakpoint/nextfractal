package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.session.Session;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class MainEditorPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    private EventBus localEventBus;
    private Session session;

    public MainEditorPane(EventBus eventBus) {
        eventBus.subscribe("session-data-loaded", event -> handleSessionChanged(eventBus, (Session) ((Object[])event)[0]));
    }

    private void handleSessionChanged(EventBus eventBus, Session session) {
        if (this.session == null || !this.session.getPluginId().equals(session.getPluginId())) {
            setCenter(createRootPane(eventBus, session));
        }
        this.session = session;
    }

    private Pane createRootPane(EventBus eventBus, Session session) {
        return createRenderPane(session, getLocalEventBus(eventBus)).orElse(null);
    }

    private EventBus getLocalEventBus(EventBus eventBus) {
        if (localEventBus != null) {
            localEventBus.detach();
        }
        localEventBus = new EventBus(eventBus);
        return localEventBus;
    }

    private static Try<Pane, Exception> createRenderPane(Session session, EventBus eventBus) {
        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createEditorPane(eventBus, session)))
            .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor panel with pluginId " + session.getPluginId(), e));
    }
}
