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

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.swing.extensions.MandelbrotSwingExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaElementListEditorRuntime extends ConfigurableReferenceElementListEditorRuntime {
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
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final OutcolouringFormulaConfigElement configElement = new OutcolouringFormulaConfigElement();
		configElement.setReference(reference);
		return new OutcolouringFormulaConfigElementNodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.appendOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return MandelbrotSwingExtensionResources.getInstance().getString("action.removeAllOutcolouringFormulas");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.appendOutcolouringFormula");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return MandelbrotSwingExtensionResources.getInstance().getString("tooltip.removeAllOutcolouringFormulas");
	}
}
