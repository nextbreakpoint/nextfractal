/*
 * NextFractal 1.1.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
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
		setAlignment(Pos.CENTER_RIGHT);
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
