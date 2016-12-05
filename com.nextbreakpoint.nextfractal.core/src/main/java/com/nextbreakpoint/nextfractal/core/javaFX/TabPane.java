package com.nextbreakpoint.nextfractal.core.javaFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TabPane extends Pane {
    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }

        @Override
        public Object getBean() {
            return TabPane.this;
        }

        @Override
        public String getName() {
            return "onAction";
        }
    };

    private boolean entered;

    public TabPane(ImageView image) {
        Canvas canvas = new Canvas();
        getChildren().add(canvas);
        getChildren().add(image);

        widthProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
            canvas.setHeight(getHeight());
            image.setFitWidth(newValue.doubleValue() / 3);
            image.setLayoutX(newValue.doubleValue() / 3);
            redraw(canvas);
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(getWidth());
            canvas.setHeight(newValue.doubleValue());
            image.setFitHeight(newValue.doubleValue() * 2 / 3);
            image.setLayoutY(newValue.doubleValue() * 0.1);
            redraw(canvas);
        });

        addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            entered = true;
            redraw(canvas);
        });

        addEventFilter(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            entered = false;
            redraw(canvas);
        });

        addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> onActionProperty().getValue().handle(new ActionEvent(TabPane.this, null)));
    }

    private void redraw(Canvas canvas) {
        double width = getWidth();
        double height = getHeight();
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.clearRect(0,0, width, height);
        g2d.setFill(new Color(1,1,1,entered ? 0.9 : 0.7));
        g2d.fillOval(0, -height, width, height * 2);
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    public final void setOnAction(EventHandler<ActionEvent> value) { onActionProperty().set(value); }
}
