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

public class MainEditorPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    public MainEditorPane(EventBus eventBus) {
        eventBus.subscribe("session-changed", event -> handleSessionChanged((Session)event, eventBus));
    }

    private void handleSessionChanged(Session session, EventBus eventBus) {
        setCenter(createEditorPane(session, eventBus).orElse(null));
    }

    private Try<Pane, Exception> createEditorPane(Session session, EventBus eventBus) {
        return tryPlugin(session.getPluginId(), plugin -> Objects.requireNonNull(plugin.createEditorPane(session, eventBus)))
                .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor panel with pluginId " + session.getPluginId(), e));
    }
}
