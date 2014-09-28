/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.contextfree.swing.extensions.ContextFreeSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentReferenceElementEditorRuntime extends ConfigurableReferenceElementEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(ContextFreeRegistry.getInstance().getPathAdjustmentRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final PathAdjustmentConfigElement configElement = new PathAdjustmentConfigElement();
		configElement.setExtensionReference(reference);
		return new PathAdjustmentConfigElementNodeValue(configElement);
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
		return ContextFreeSwingExtensionResources.getInstance().getString("action.insertPathAdjustmentBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.insertPathAdjustmentAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.removePathAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.insertPathAdjustmentAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.insertPathAdjustmentBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.removePathAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.moveUpPathAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.moveDownPathAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.moveUpPathAdjustment");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.moveUpPathAdjustment");
	}
} 
