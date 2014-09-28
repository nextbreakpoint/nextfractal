/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.twister.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.swing.extensions.TwisterSwingExtensionResources;

import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class LayerFilterElementListEditorRuntime extends ConfigurableReferenceElementListEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getLayerFilterRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final LayerFilterConfigElement configElement = new LayerFilterConfigElement();
		configElement.setReference(reference);
		return new LayerFilterConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.appendLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.removeAllLayerFilters");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.appendLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.removeAllLayerFilters");
	}
}
