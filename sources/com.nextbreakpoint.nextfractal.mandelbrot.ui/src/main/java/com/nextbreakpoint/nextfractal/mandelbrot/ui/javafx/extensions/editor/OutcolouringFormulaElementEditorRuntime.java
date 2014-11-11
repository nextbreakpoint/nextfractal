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
package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.extensions.editor;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.extensions.MandelbrotSwingExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaElementEditorRuntime extends ConfigurableReferenceElementEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceEditorRuntime#getExtensionList()
	 */
	@Override
	protected List<ConfigurableExtension<?, ?>> getExtensionList() {
		List<ConfigurableExtension<?, ?>> result = new ArrayList<ConfigurableExtension<?,?>>();
		for (ConfigurableExtension<?, ?> extension : MandelbrotRegistry.getInstance().getOutcolouringFormulaRegistry().getConfigurableExtensionList()) {
			result.add(extension);
		}
		return result;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final OutcolouringFormulaConfigElement configElement = new OutcolouringFormulaConfigElement();
		configElement.setReference(reference);
		return new OutcolouringFormulaConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeLabel()
	 */
	@Override
	protected String getInsertBeforeLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.insertOutcolouringFormulaBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.insertOutcolouringFormulaAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.removeOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.insertOutcolouringFormulaAfter");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.insertOutcolouringFormulaBefore");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.removeOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.moveUpOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.moveDownOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.moveUpOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.moveUpOutcolouringFormula");
	}
}
