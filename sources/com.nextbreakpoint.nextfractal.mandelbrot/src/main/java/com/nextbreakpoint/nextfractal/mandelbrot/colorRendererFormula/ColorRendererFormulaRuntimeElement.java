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
package com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRendererFormula.ColorRendererFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ColorRendererFormulaRuntimeElement extends RuntimeElement {
	private ColorRendererFormulaExtensionRuntime formulaRuntime;
	private ColorRendererFormulaConfigElement formulaElement;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new formula element.
	 * 
	 * @param formulaElement
	 */
	public ColorRendererFormulaRuntimeElement(final ColorRendererFormulaConfigElement formulaElement) {
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		createRuntime(formulaElement.getReference());
		extensionListener = new ExtensionListener(this);
		formulaElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((formulaElement != null) && (extensionListener != null)) {
			formulaElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (formulaRuntime != null) {
			formulaRuntime.dispose();
			formulaRuntime = null;
		}
		formulaElement = null;
		super.dispose();
	}

	private void createRuntime(final ExtensionReference reference) {
		try {
			if (reference != null) {
				final ColorRendererFormulaExtensionRuntime formulaRuntime = MandelbrotRegistry.getInstance().getColorRendererFormulaExtension(reference.getExtensionId()).createExtensionRuntime();
				setFormulaRuntime(formulaRuntime);
			}
			else {
				setFormulaRuntime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the formulaRuntime
	 */
	public ColorRendererFormulaExtensionRuntime getFormulaRuntime() {
		return formulaRuntime;
	}

	private void setFormulaRuntime(final ColorRendererFormulaExtensionRuntime formulaRuntime) {
		if (this.formulaRuntime != null) {
			this.formulaRuntime.dispose();
		}
		this.formulaRuntime = formulaRuntime;
	}

	private class ExtensionListener implements ValueChangeListener {
		private final ColorRendererFormulaRuntimeElement formula;

		/**
		 * Constructs a new formula listener.
		 * 
		 * @param formula the formula.
		 */
		public ExtensionListener(final ColorRendererFormulaRuntimeElement formula) {
			this.formula = formula;
		}

		private void createRuntime(final ExtensionReference reference) {
			try {
				if (reference != null) {
					final ColorRendererFormulaExtensionRuntime formulaRuntime = MandelbrotRegistry.getInstance().getColorRendererFormulaExtension(reference.getExtensionId()).createExtensionRuntime();
					formula.setFormulaRuntime(formulaRuntime);
				}
			}
			catch (final ExtensionNotFoundException e) {
				e.printStackTrace();
			}
			catch (final ExtensionException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ExtensionReference) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
