package com.nextbreakpoint.nextfractal.core.ui.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class AdvancedTextField extends TextField {
	private StringProperty restrict = new SimpleStringProperty();
	private IntegerProperty maxLength = new SimpleIntegerProperty(-1);

	public int getMaxLength() {
		return maxLength.get();
	}

	public void setMaxLength(int maxLength) {
		this.maxLength.set(maxLength);
	}

	public void setRestrict(String restrict) {
		this.restrict.set(restrict);
	}

	public String getRestrict() {
		return restrict.get();
	}

	public StringProperty restrictProperty() {
		return restrict;
	}

	public IntegerProperty maxLengthProperty() {
		return maxLength;
	}

	public AdvancedTextField() {
		textProperty().addListener(new ChangeListener<String>() {
			private boolean ignore;

			@Override
			public void changed(ObservableValue<? extends String> observableValue, String text, String newText) {
				if (ignore)
					return;
				if (maxLength.get() > -1 && newText.length() > maxLength.get()) {
					ignore = true;
					setText(newText.substring(0, maxLength.get()));
					ignore = false;
				}
				if (restrict.get() != null && !restrict.get().equals("") && !newText.matches(restrict.get())) {
					ignore = true;
					setText(text);
					ignore = false;
				}
			}
		});
	}
}