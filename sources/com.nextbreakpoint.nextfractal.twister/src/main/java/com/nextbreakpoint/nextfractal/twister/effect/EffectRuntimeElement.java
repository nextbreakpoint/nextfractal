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
package com.nextbreakpoint.nextfractal.twister.effect;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class EffectRuntimeElement extends RuntimeElement {
	private EffectExtensionRuntime<?> effectRuntime;
	private EffectConfigElement effectElement;
	private ExtensionListener extensionListener;
	private EnabledListener enabledListener;
	private boolean enabled;

	/**
	 * Constructs a new effect.
	 * 
	 * @param registry
	 * @param effectElement
	 */
	public EffectRuntimeElement(final EffectConfigElement effectElement) {
		if (effectElement == null) {
			throw new IllegalArgumentException("effectElement is null");
		}
		this.effectElement = effectElement;
		createRuntime(effectElement.getReference());
		extensionListener = new ExtensionListener();
		effectElement.getExtensionElement().addChangeListener(extensionListener);
		setEnabled(effectElement.isEnabled());
		enabledListener = new EnabledListener();
		effectElement.getEnabledElement().addChangeListener(enabledListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((effectElement != null) && (extensionListener != null)) {
			effectElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if ((effectElement != null) && (enabledListener != null)) {
			effectElement.getEnabledElement().removeChangeListener(enabledListener);
		}
		enabledListener = null;
		if (effectRuntime != null) {
			effectRuntime.dispose();
			effectRuntime = null;
		}
		effectElement = null;
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<EffectExtensionConfig> reference) {
		try {
			if (reference != null) {
				final EffectExtensionRuntime effectRuntime = TwisterRegistry.getInstance().getEffectExtension(reference.getExtensionId()).createExtensionRuntime();
				final EffectExtensionConfig effectConfig = reference.getExtensionConfig();
				effectRuntime.setConfig(effectConfig);
				setEffectRuntime(effectRuntime);
			}
			else {
				setEffectRuntime(null);
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
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean effectChanged = (effectRuntime != null) && effectRuntime.isChanged();
		return super.isChanged() || effectChanged;
	}

	/**
	 * @return the effectRuntime
	 */
	public EffectExtensionRuntime<?> getEffectRuntime() {
		return effectRuntime;
	}

	private void setEffectRuntime(final EffectExtensionRuntime<?> effectRuntime) {
		if (this.effectRuntime != null) {
			this.effectRuntime.dispose();
		}
		this.effectRuntime = effectRuntime;
	}

	/**
	 * @return the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	private void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<EffectExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class EnabledListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setEnabled((Boolean) e.getParams()[0]);
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
