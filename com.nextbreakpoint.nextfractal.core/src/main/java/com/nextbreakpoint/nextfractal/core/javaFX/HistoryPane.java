package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryPlugin;

public class HistoryPane extends BorderPane {
    private static final int PADDING = 8;

    private final ThreadFactory threadFactory = new DefaultThreadFactory("History", true, Thread.MIN_PRIORITY);
    private final ExecutorService historyExecutor;
    private ListView<Bitmap> historyListView;
    private HistoryDelegate delegate;
    private volatile boolean noHistory;
    private RendererTile tile;
    private RendererSize size;

    public HistoryPane(RendererTile tile) {
        this.tile = tile;

        historyListView = new ListView<>();
        historyListView.setFixedCellSize(tile.getTileSize().getHeight() + PADDING);
        historyListView.getStyleClass().add("history");
        historyListView.setCellFactory(view -> new HistoryListCell(tile));

        setCenter(historyListView);

        historyListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Bitmap> c) -> historyItemSelected(historyListView));

        historyExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("History", true, Thread.MIN_PRIORITY));
    }

    private void historyItemSelected(ListView<Bitmap> historyList) {
        int index = historyList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            noHistory = true;
            if (delegate != null) {
                Bitmap bitmap = historyList.getItems().get(index);
                Session session = (Session) bitmap.getProperty("session");
                delegate.sessionChanged(session);
            }
            noHistory = false;
        }
    }

    private void submitItem(Session session, ImageGenerator generator) {
        historyExecutor.submit(() -> Try.of(() -> generator.renderImage(session.getData()))
            .ifPresent(pixels -> Platform.runLater(() -> historyAddItem(historyListView, session, pixels, generator.getSize()))));
    }

    private void historyAddItem(ListView<Bitmap> historyList, Session session, IntBuffer pixels, RendererSize size) {
        BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), pixels);
        bitmap.setProperty("session", session);
        historyList.getItems().add(0, bitmap);
    }

    public void appendSession(Session session) {
        if (noHistory) {
            return;
        }

        tryPlugin(session.getPluginId()).map(factory -> factory.createImageGenerator(threadFactory,
            new JavaFXRendererFactory(), tile, true)).ifPresent(generator -> submitItem(session, generator));
    }

    public HistoryDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(HistoryDelegate delegate) {
        this.delegate = delegate;
    }
}
