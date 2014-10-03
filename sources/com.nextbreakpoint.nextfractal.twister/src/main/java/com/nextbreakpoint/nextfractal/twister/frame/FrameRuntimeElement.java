/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.twister.frame;

import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public class FrameRuntimeElement extends RuntimeElement {
	private final ListRuntimeElement<GroupLayerRuntimeElement> layerListElement = new ListRuntimeElement<GroupLayerRuntimeElement>(1);
	private final ListRuntimeElement<FrameFilterRuntimeElement> filterListElement = new ListRuntimeElement<FrameFilterRuntimeElement>(0);
	private FilterListElementListener filtersListener;
	private LayerListElementListener layersListener;
	private FrameConfigElement frameElement;

	/**
	 * Constructs a new frame.
	 * 
	 * @param frameElement
	 */
	public FrameRuntimeElement(final FrameConfigElement frameElement) {
		if (frameElement == null) {
			throw new IllegalArgumentException("frameElement is null");
		}
		this.frameElement = frameElement;
		createFilters(frameElement);
		filtersListener = new FilterListElementListener();
		frameElement.getFilterListElement().addChangeListener(filtersListener);
		createLayers(frameElement);
		layersListener = new LayerListElementListener();
		frameElement.getLayerListElement().addChangeListener(layersListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((frameElement != null) && (filtersListener != null)) {
			frameElement.getFilterListElement().removeChangeListener(filtersListener);
		}
		filtersListener = null;
		if ((frameElement != null) && (layersListener != null)) {
			frameElement.getLayerListElement().removeChangeListener(layersListener);
		}
		layersListener = null;
		filterListElement.dispose();
		layerListElement.dispose();
		frameElement = null;
		super.dispose();
	}

	private void createFilters(final FrameConfigElement frameElement) {
		for (int i = 0; i < frameElement.getFilterConfigElementCount(); i++) {
			final FrameFilterConfigElement filterElement = frameElement.getFilterConfigElement(i);
			final FrameFilterRuntimeElement filter = new FrameFilterRuntimeElement(filterElement);
			appendFilter(filter);
		}
	}

	private void createLayers(final FrameConfigElement frameElement) {
		for (int i = 0; i < frameElement.getLayerConfigElementCount(); i++) {
			final GroupLayerConfigElement layerElement = frameElement.getLayerConfigElement(i);
			final GroupLayerRuntimeElement layer = new GroupLayerRuntimeElement(layerElement);
			appendLayer(layer);
		}
	}

	/**
	 * Returns a layer.
	 * 
	 * @param index the layer index.
	 * @return the layer.
	 */
	public GroupLayerRuntimeElement getLayer(final int index) {
		return layerListElement.getElement(index);
	}

	/**
	 * Returns the layer index.
	 * 
	 * @param layer the layer.
	 * @return the index.
	 */
	public int indexOfLayer(final GroupLayerRuntimeElement layer) {
		return layerListElement.indexOfElement(layer);
	}

	/**
	 * Returns the number of layers.
	 * 
	 * @return the number of layers.
	 */
	public int getLayerCount() {
		return layerListElement.getElementCount();
	}

	private void appendLayer(final GroupLayerRuntimeElement layer) {
		layerListElement.appendElement(layer);
	}

	private void insertLayerAfter(final int index, final GroupLayerRuntimeElement layer) {
		layerListElement.insertElementAfter(index, layer);
	}

	private void insertLayerBefore(final int index, final GroupLayerRuntimeElement layer) {
		layerListElement.insertElementBefore(index, layer);
	}

	private void removeLayer(final int index) {
		layerListElement.getElement(index).dispose();
		layerListElement.removeElement(index);
	}

	/**
	 * Returns a filter.
	 * 
	 * @param index the filter index.
	 * @return the filter.
	 */
	public FrameFilterRuntimeElement getFilter(final int index) {
		return filterListElement.getElement(index);
	}

	/**
	 * Returns the filter index.
	 * 
	 * @param filter the filter.
	 * @return the index.
	 */
	public int indexOfFilter(final FrameFilterRuntimeElement filter) {
		return filterListElement.indexOfElement(filter);
	}

	/**
	 * Returns the number of filters.
	 * 
	 * @return the number of filters.
	 */
	public int getFilterCount() {
		return filterListElement.getElementCount();
	}

	private void appendFilter(final FrameFilterRuntimeElement filter) {
		filterListElement.appendElement(filter);
	}

	private void insertFilterAfter(final int index, final FrameFilterRuntimeElement filter) {
		filterListElement.insertElementAfter(index, filter);
	}

	private void insertFilterBefore(final int index, final FrameFilterRuntimeElement filter) {
		filterListElement.insertElementBefore(index, filter);
	}

	private void removeFilter(final int index) {
		filterListElement.getElement(index).dispose();
		filterListElement.removeElement(index);
	}

	private void moveUpLayer(final int index) {
		layerListElement.moveElementUp(index);
	}

	private void moveDownLayer(final int index) {
		layerListElement.moveElementDown(index);
	}

	private void setLayer(final int index, final GroupLayerRuntimeElement layer) {
		layerListElement.setElement(index, layer);
	}

	private void moveUpFilter(final int index) {
		filterListElement.moveElementUp(index);
	}

	private void moveDownFilter(final int index) {
		filterListElement.moveElementDown(index);
	}

	private void setFilter(final int index, final FrameFilterRuntimeElement filter) {
		filterListElement.setElement(index, filter);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean layersChanged = layerListElement.isChanged();
		final boolean filtersChanged = filterListElement.isChanged();
		return super.isChanged() || layersChanged || filtersChanged;
	}

	private class LayerListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendLayer(new GroupLayerRuntimeElement((GroupLayerConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertLayerAfter(((Integer) e.getParams()[1]).intValue(), new GroupLayerRuntimeElement((GroupLayerConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertLayerBefore(((Integer) e.getParams()[1]).intValue(), new GroupLayerRuntimeElement((GroupLayerConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeLayer(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpLayer(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownLayer(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setLayer(((Integer) e.getParams()[1]).intValue(), new GroupLayerRuntimeElement((GroupLayerConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class FilterListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendFilter(new FrameFilterRuntimeElement((FrameFilterConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertFilterAfter(((Integer) e.getParams()[1]).intValue(), new FrameFilterRuntimeElement((FrameFilterConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertFilterBefore(((Integer) e.getParams()[1]).intValue(), new FrameFilterRuntimeElement((FrameFilterConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeFilter(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpFilter(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownFilter(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setFilter(((Integer) e.getParams()[1]).intValue(), new FrameFilterRuntimeElement((FrameFilterConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
