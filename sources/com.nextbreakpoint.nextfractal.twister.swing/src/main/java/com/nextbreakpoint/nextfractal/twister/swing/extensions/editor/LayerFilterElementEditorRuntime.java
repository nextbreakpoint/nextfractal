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
import com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.swing.extensions.TwisterSwingExtensionResources;

import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class LayerFilterElementEditorRuntime extends ConfigurableReferenceElementEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getLayerFilterRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final LayerFilterConfigElement configElement = new LayerFilterConfigElement();
		configElement.setReference(reference);
		return new LayerFilterConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeLabel()
	 */
	@Override
	protected String getInsertBeforeLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.insertLayerFilterBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.insertLayerFilterAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.removeLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.insertLayerFilterAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.insertLayerFilterBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.removeLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.moveDownLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.moveDownLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return TwisterSwingExtensionResources.getInstance().getString("action.moveUpLayerFilter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return TwisterSwingExtensionResources.getInstance().getString("tooltip.moveUpLayerFilter");
	}
}
