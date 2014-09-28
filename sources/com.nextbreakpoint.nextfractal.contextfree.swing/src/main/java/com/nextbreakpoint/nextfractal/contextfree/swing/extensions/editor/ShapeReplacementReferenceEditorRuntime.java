/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime;
import com.nextbreakpoint.nextfractal.core.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementReferenceEditorRuntime extends ConfigurableReferenceEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(ContextFreeRegistry.getInstance().getShapeReplacementRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		return new ShapeReplacementExtensionReferenceNodeValue(reference);
	}
}
