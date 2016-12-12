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
package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class HistoryPane extends BorderPane {
    private static Logger logger = Logger.getLogger(HistoryPane.class.getName());
    private static final int PADDING = 8;

    private final ExecutorService executor;
    private ListView<Bitmap> listView;
    private HistoryDelegate delegate;
    private RendererTile tile;
    private volatile boolean noHistory;

    public HistoryPane(RendererTile tile) {
        this.tile = tile;

        listView = new ListView<>();
        listView.setFixedCellSize(tile.getTileSize().getHeight() + PADDING);
        listView.getStyleClass().add("history");
        listView.setCellFactory(view -> new HistoryListCell(tile));

        setCenter(listView);

        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Bitmap> c) -> itemSelected(listView));

        executor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("History Generator", true, Thread.MIN_PRIORITY));
    }

    private DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }

    private void itemSelected(ListView<Bitmap> listView) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            noHistory = true;
            if (delegate != null) {
                Bitmap bitmap = listView.getItems().get(index);
                Session session = (Session) bitmap.getProperty("session");
                delegate.sessionChanged(session);
            }
            noHistory = false;
        }
    }

    private void submitItem(Session session, ImageGenerator generator) {
        executor.submit(() -> Try.of(() -> generator.renderImage(session))
            .ifPresent(pixels -> Platform.runLater(() -> addItem(listView, session, pixels, generator.getSize()))));
    }

    private void addItem(ListView<Bitmap> listView, Session session, IntBuffer pixels, RendererSize size) {
        BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), pixels);
        bitmap.setProperty("session", session);
        listView.getItems().add(0, bitmap);
    }

    public void appendSession(Session session) {
//        if (noHistory) {
//            return;
//        }

        tryFindFactory(session.getPluginId()).map(factory -> factory.createImageGenerator(createThreadFactory("History Renderer"),
            new JavaFXRendererFactory(), tile, true)).ifPresent(generator -> submitItem(session, generator));
    }

    public void setDelegate(HistoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    public void dispose() {
        List<ExecutorService> executors = Arrays.asList(executor);
        executors.forEach(executor -> executor.shutdownNow());
        executors.forEach(executor -> await(executor));
    }

    private void await(ExecutorService executor) {
        Try.of(() -> executor.awaitTermination(5000, TimeUnit.MILLISECONDS)).onFailure(e -> logger.warning("Await termination timeout")).execute();
    }
}
