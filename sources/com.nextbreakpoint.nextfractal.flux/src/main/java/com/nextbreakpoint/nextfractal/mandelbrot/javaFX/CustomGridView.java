package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import org.controlsfx.control.GridView;

public class CustomGridView<T> extends GridView<T> {
	private ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty = new SimpleObjectProperty<>();
	
	public CustomGridView() {
		setSelectionModel(new CustomGridSelectionModel());
	}
	
	public ObjectProperty<MultipleSelectionModel<T>> getSelectionModelProperty() {
		return selectionModelProperty;
	}

	public MultipleSelectionModel<T> getSelectionModel() {
		return selectionModelProperty.get();
	}

	public void setSelectionModel(MultipleSelectionModel<T> selectionModel) {
		selectionModelProperty.set(selectionModel);
	}
	
	private class CustomGridSelectionModel extends MultipleSelectionModel<T> {
		private List<Integer> indices = new ArrayList<>();
		
		@Override
		public ObservableList<Integer> getSelectedIndices() {
			return FXCollections.observableArrayList(indices);
		}

		@Override
		public ObservableList<T> getSelectedItems() {
			List<T> selectedItems = new ArrayList<>();
			for (Integer index : indices) {
				selectedItems.add(getItems().get(index));
			}
			return FXCollections.observableArrayList(selectedItems);
		}

		@Override
		public void selectIndices(int index, int... indices) {
			this.indices.add(index);
			if (indices != null) {
				for (int i = 0; i < indices.length; i++) {
					this.indices.add(i);
				}
			}
		}

		@Override
		public void selectAll() {
			this.indices.clear();
			for (int i = 0; i < getItems().size(); i++) {
				this.indices.add(i);
			}
		}

		@Override
		public void selectFirst() {
			if (getItems().size() > 0) {
				this.indices.add(0);
			}
		}

		@Override
		public void selectLast() {
			if (getItems().size() > 0) {
				this.indices.add(getItems().size() - 1);
			}
		}

		@Override
		public void clearAndSelect(int index) {
			this.indices.clear();
			if (index > 0 && index < getItems().size()) {
				this.indices.add(index);
			}
		}

		@Override
		public void select(int index) {
			if (index >= 0 && index < getItems().size()) {
				this.indices.add(index);
			}
		}

		@Override
		public void select(T obj) {
			int index = getItems().indexOf(obj);
			if (index >= 0) {
				this.indices.add(index);
			}
		}

		@Override
		public void clearSelection(int index) {
			indices.remove((Integer)index);
		}

		@Override
		public void clearSelection() {
			this.indices.clear();
		}

		@Override
		public boolean isSelected(int index) {
			return indices.contains(index);
		}

		@Override
		public boolean isEmpty() {
			return indices.isEmpty();
		}

		@Override
		public void selectPrevious() {
			if (indices.size() > 0) {
				int index = indices.get(0);
				select(index - 1);
			}
		}

		@Override
		public void selectNext() {
			if (indices.size() > 0) {
				int index = indices.get(indices.size() - 1);
				select(index + 1);
			}
		}
	}
}
