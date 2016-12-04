package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.session.Session;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class MainEditorPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    private Map<String, EventBus> buses = new HashMap<>();
    private Map<String, Pane> panels = new HashMap<>();
    private Session session;

    public MainEditorPane(EventBus eventBus) {
        eventBus.subscribe("session-data-loaded", event -> handleSessionChanged(eventBus, (Session) ((Object[])event)[0]));
    }

    private void handleSessionChanged(EventBus eventBus, Session session) {
        if (this.session == null || !this.session.getPluginId().equals(session.getPluginId())) {
            if (this.session != null) {
                Optional.ofNullable(buses.get(this.session.getPluginId())).ifPresent(bus -> bus.disable());
            }
            Pane rootPane = panels.get(session.getPluginId());
            if (rootPane == null) {
                EventBus innerBus = new EventBus(eventBus);
                rootPane = createRootPane(innerBus, session);
                panels.put(session.getPluginId(), rootPane);
                buses.put(session.getPluginId(), innerBus);
            }
            setCenter(rootPane);
            Optional.ofNullable(buses.get(session.getPluginId())).ifPresent(bus -> bus.enable());
        }
        this.session = session;
    }

    private Pane createRootPane(EventBus eventBus, Session session) {
        return createRenderPane(session, eventBus).orElse(null);
    }

    private static Try<Pane, Exception> createRenderPane(Session session, EventBus eventBus) {
        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createEditorPane(eventBus, session)))
            .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor panel with pluginId " + session.getPluginId(), e));
    }
}
