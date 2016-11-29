package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryPane extends BorderPane {
    private static final int PADDING = 8;

    private final ExecutorService historyExecutor;
    private volatile boolean noHistory;

    public HistoryPane(ImageGenerator generator, RendererTile tile) {
        ListView<Bitmap> listView = new ListView<>();
        listView.setFixedCellSize(tile.getTileSize().getHeight() + PADDING);
        listView.getStyleClass().add("history");
        listView.setCellFactory(view -> new HistoryListCell(generator.getSize(), tile));

        setCenter(listView);

        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Bitmap> c) -> historyItemSelected(listView));

        historyExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("History", true, Thread.MIN_PRIORITY));
    }

    private void historyItemSelected(ListView<Bitmap> historyList) {
        int index = historyList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Bitmap bitmap = historyList.getItems().get(index);
            noHistory = true;
//            MandelbrotData data = (MandelbrotData) bitmap.getProperty("data");
//            getMandelbrotSession().setData(data);
            noHistory = false;
        }
    }

    private void addDataToHistory(ListView<Bitmap> historyList) {
        if (noHistory) {
            return;
        }
//        historyExecutor.submit(Block.create(ContextFreeData.class)
//                .andThen(data -> data.setPixels(generator.renderImage(data)))
//                .andThen(data -> Platform.runLater(() -> historyAddItem(historyList, data)))
//                .toCallable(getContextFreeSession().getDataAsCopy()));
    }

//    private void historyAddItem(ListView<Bitmap> historyList, ContextFreeData data) {
//        BrowseBitmap bitmap = new BrowseBitmap(generator.getSize().getWidth(), generator.getSize().getHeight(), data.getPixels());
//        bitmap.setProperty("data", data);
//        historyList.getItems().add(0, bitmap);
//    }
}
