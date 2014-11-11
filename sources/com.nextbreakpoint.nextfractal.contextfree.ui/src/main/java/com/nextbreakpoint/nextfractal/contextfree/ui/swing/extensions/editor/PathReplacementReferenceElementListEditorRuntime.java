/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.editor;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.ContextFreeSwingExtensionResources;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ConfigurableExtensionComboBoxModel;
/**
 * @author Andrea Medeghini
 */
public class PathReplacementReferenceElementListEditorRuntime extends ConfigurableReferenceElementListEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(ContextFreeRegistry.getInstance().getPathReplacementRegistry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final PathReplacementConfigElement configElement = new PathReplacementConfigElement();
		configElement.setExtensionReference(reference);
		return new PathReplacementConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.appendPathReplacement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return ContextFreeSwingExtensionResources.getInstance().getString("action.removeAllPathReplacements");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.appendPathReplacement");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return ContextFreeSwingExtensionResources.getInstance().getString("tooltip.removeAllPathReplacements");
	}
} 
