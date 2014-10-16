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

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ReferenceEditorRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererFormulaReferenceEditorRuntime extends ReferenceEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ReferenceEditorRuntime#getExtensionList()
	 */
	@Override
	protected List<Extension<?>> getExtensionList() {
		List<Extension<?>> result = new ArrayList<Extension<?>>();
		for (Extension<?> extension : MandelbrotRegistry.getInstance().getPaletteRendererFormulaRegistry().getExtensionList()) {
			result.add(extension);
		}
		return result;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ReferenceEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.editor.ReferenceEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ExtensionReference)
	 */
	@Override
	protected NodeValue<?> createNodeValue(final ExtensionReference reference) {
		// return new PaletteRendererFormulaExtensionReferenceNodeValue(reference != null ? reference.clone() : null);
		return new PaletteRendererFormulaExtensionReferenceNodeValue(reference);
	}
}
