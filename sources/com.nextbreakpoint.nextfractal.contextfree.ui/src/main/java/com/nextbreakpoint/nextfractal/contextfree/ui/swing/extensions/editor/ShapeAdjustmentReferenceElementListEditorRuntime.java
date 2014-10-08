/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.ContextFreeSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ConfigurableExtensionComboBoxModel;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentReferenceElementListEditorRuntime extends ConfigurableReferenceElementListEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(ContextFreeRegistry.getInstance().getShapeAdjustmentRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final ShapeAdjustmentConfigElement configElement = new ShapeAdjustmentConfigElement();
		configElement.setExtensionReference(reference);
		return new ShapeAdjustmentConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.appendShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.removeAllShapeAdjustments");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.appendShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.removeAllShapeAdjustments");
	}
} 
