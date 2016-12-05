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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class HistoryPane extends BorderPane {
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
}
