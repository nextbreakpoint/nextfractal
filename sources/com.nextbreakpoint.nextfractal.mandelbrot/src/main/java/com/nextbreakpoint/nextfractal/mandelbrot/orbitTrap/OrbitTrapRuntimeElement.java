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
package com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class OrbitTrapRuntimeElement extends RuntimeElement {
	private OrbitTrapExtensionRuntime<?> trapRuntime;
	private OrbitTrapConfigElement trapElement;
	private ExtensionListener extensionListener;
	private CenterListener centerListener;
	private DoubleVector2D center;

	/**
	 * Constructs a new formula element.
	 * 
	 * @param trapElement
	 */
	public OrbitTrapRuntimeElement(final OrbitTrapConfigElement trapElement) {
		if (trapElement == null) {
			throw new IllegalArgumentException("trapElement is null");
		}
		this.trapElement = trapElement;
		setCenter(trapElement.getCenter());
		createRuntime(trapElement.getReference());
		centerListener = new CenterListener(this);
		trapElement.getCenterElement().addChangeListener(centerListener);
		extensionListener = new ExtensionListener();
		trapElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((trapElement != null) && (centerListener != null)) {
			trapElement.getCenterElement().removeChangeListener(centerListener);
		}
		if ((trapElement != null) && (extensionListener != null)) {
			trapElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		centerListener = null;
		extensionListener = null;
		if (trapRuntime != null) {
			trapRuntime.dispose();
			trapRuntime = null;
		}
		trapElement = null;
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<OrbitTrapExtensionConfig> reference) {
		try {
			if (reference != null) {
				final OrbitTrapExtensionRuntime formulaRuntime = MandelbrotRegistry.getInstance().getOrbitTrapExtension(reference.getExtensionId()).createExtensionRuntime();
				final OrbitTrapExtensionConfig formulaConfig = reference.getExtensionConfig();
				formulaRuntime.setConfig(formulaConfig);
				setOrbitTrapRuntime(formulaRuntime);
			}
			else {
				setOrbitTrapRuntime(null);
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
	public OrbitTrapExtensionRuntime<?> getOrbitTrapRuntime() {
		return trapRuntime;
	}

	private void setOrbitTrapRuntime(final OrbitTrapExtensionRuntime<?> trapRuntime) {
		if (this.trapRuntime != null) {
			this.trapRuntime.dispose();
		}
		this.trapRuntime = trapRuntime;
	}

	/**
	 * @return the center.
	 */
	public DoubleVector2D getCenter() {
		return center;
	}

	private void setCenter(final DoubleVector2D center) {
		this.center = center;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean trapChanged = (trapRuntime != null) && trapRuntime.isChanged();
		return super.isChanged() || trapChanged;
	}

	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<OrbitTrapExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class CenterListener implements ValueChangeListener {
		private final OrbitTrapRuntimeElement trap;

		/**
		 * @param trap the trap.
		 */
		public CenterListener(final OrbitTrapRuntimeElement trap) {
			this.trap = trap;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					trap.setCenter((DoubleVector2D) e.getParams()[0]);
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
