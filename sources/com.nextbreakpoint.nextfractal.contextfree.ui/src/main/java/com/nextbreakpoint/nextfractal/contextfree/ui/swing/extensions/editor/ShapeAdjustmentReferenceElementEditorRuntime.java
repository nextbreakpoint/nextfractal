/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.ContextFreeSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ConfigurableExtensionComboBoxModel;
/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentReferenceElementEditorRuntime extends ConfigurableReferenceElementEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(ContextFreeRegistry.getInstance().getShapeAdjustmentRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final ShapeAdjustmentConfigElement configElement = new ShapeAdjustmentConfigElement();
		configElement.setExtensionReference(reference);
		return new ShapeAdjustmentConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeLabel()
	 */
	@Override
	protected String getInsertBeforeLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.insertShapeAdjustmentBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.insertShapeAdjustmentAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.removeShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.insertShapeAdjustmentAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.insertShapeAdjustmentBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.removeShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.moveUpShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.moveDownShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.moveUpShapeAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.moveUpShapeAdjustment");
	}
} 
