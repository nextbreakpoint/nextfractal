package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import org.controlsfx.control.GridView;

public class LibraryGridView<T> extends GridView<T> {
	private class LibraryGridSelectionModel extends MultipleSelectionModel<T> {
		@Override
		public ObservableList<Integer> getSelectedIndices() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ObservableList<T> getSelectedItems() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void selectIndices(int index, int... indices) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectFirst() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectLast() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clearAndSelect(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void select(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void select(T obj) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clearSelection(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clearSelection() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isSelected(int index) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void selectPrevious() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectNext() {
			// TODO Auto-generated method stub
			
		}
	}
}
