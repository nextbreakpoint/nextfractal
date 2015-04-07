package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;

public class ExportPane extends Pane {
	private VBox box = new VBox(10);
	private ExportPaneDelegate delegate; 

	public ExportPane() {
		ComboBox<Integer[]> presets = new ComboBox<>();
		presets.getItems().add(new Integer[] { 1900, 1080 });
		presets.getItems().add(new Integer[] { 1650, 1050 });
		presets.getItems().add(new Integer[] { 1024, 768 });
		presets.getItems().add(new Integer[] { 640, 480 });
		presets.getItems().add(new Integer[] { 0, 0 });
		presets.setMinWidth(400);
		presets.setMaxWidth(400);
		presets.setPrefWidth(400);
		presets.getSelectionModel().select(0);
		Integer[] item0 = presets.getSelectionModel().getSelectedItem();
		AdvancedTextField widthField = new AdvancedTextField();
		widthField.setRestrict(getRestriction());
		widthField.setEditable(false);
		widthField.setMinWidth(400);
		widthField.setMaxWidth(400);
		widthField.setPrefWidth(400);
		widthField.setText(String.valueOf(item0[0]));
		AdvancedTextField heightField = new AdvancedTextField();
		heightField.setRestrict(getRestriction());
		heightField.setEditable(false);
		heightField.setMinWidth(400);
		heightField.setMaxWidth(400);
		heightField.setPrefWidth(400);
		heightField.setText(String.valueOf(item0[1]));
		Button close = new Button("Close");
		Button start = new Button("Start");

		HBox buttons = new HBox(10);
		buttons.getChildren().add(start);
		buttons.getChildren().add(close);
		buttons.setAlignment(Pos.CENTER);
		
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(new Label("Presets"));
		box.getChildren().add(presets);
		box.getChildren().add(new Label("Width"));
		box.getChildren().add(widthField);
		box.getChildren().add(new Label("Height"));
		box.getChildren().add(heightField);
		box.getChildren().add(buttons);
		box.getStyleClass().add("popup");
		
		presets.setConverter(new StringConverter<Integer[]>() {
			@Override
			public String toString(Integer[] item) {
				if (item == null) {
					return null;
				} else {
					if (item[0] == 0 || item[1] == 0) {
						return "Custom";
					} else {
						return item[0] + " \u00D7 " + item[1] + " px";
					}
				}
			}

			@Override
			public Integer[] fromString(String preset) {
				return null;
			}
		});
		
		presets.setCellFactory(new Callback<ListView<Integer[]>, ListCell<Integer[]>>() {
			@Override
			public ListCell<Integer[]> call(ListView<Integer[]> p) {
				return new ListCell<Integer[]>() {
					private final Label label;
					{
						label = new Label();
					}
					
					@Override
					protected void updateItem(Integer[] item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							label.setText(presets.getConverter().toString(item));
							setGraphic(label);
						}
					}
				};
			}
		});

		presets.valueProperty().addListener(new ChangeListener<Integer[]>() {
	        @Override 
	        public void changed(ObservableValue<? extends Integer[]> value, Integer[] oldItem, Integer[] newItem) {
				if (newItem[0] == 0 || newItem[1] == 0) {
					widthField.setEditable(true);
					heightField.setEditable(true);
					widthField.setText("1024");
					heightField.setText("768");
				} else {
					widthField.setEditable(false);
					heightField.setEditable(false);
					widthField.setText(String.valueOf(newItem[0]));
					heightField.setText(String.valueOf(newItem[1]));
				}
	        }    
	    });
		
		close.setOnMouseClicked(e -> {
			hide();
		});
		
		start.setOnMouseClicked(e -> {
			hide();
			int width = Integer.parseInt(widthField.getText());
			int height = Integer.parseInt(heightField.getText());
			if (delegate != null) {
				delegate.createExportSession(new RendererSize(width, height));
			}
		});
		
		getChildren().add(box);
		
		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefWidth(newValue.doubleValue());
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefHeight(newValue.doubleValue());
				box.setLayoutY(-newValue.doubleValue());
			}
		});
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}
	
	public void show() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromY(box.getTranslateY());
		tt.setToY(box.getHeight());
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(false);
			}
		});
		tt.play();
	}
	
	public void hide() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromY(box.getTranslateY());
		tt.setToY(0);
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(true);
			}
		});
		tt.play();
	}

	public ExportPaneDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ExportPaneDelegate delegate) {
		this.delegate = delegate;
	}
}