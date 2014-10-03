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
package com.nextbreakpoint.nextfractal.twister.effect;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class EffectConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "Effect";
	private final ConfigurableExtensionReferenceElement<EffectExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<EffectExtensionConfig>();
	private final BooleanElement enabledElement = new BooleanElement(true);
	private final BooleanElement lockedElement = new BooleanElement(false);

	/**
	 * 
	 */
	public EffectConfigElement() {
		super(EffectConfigElement.CLASS_ID);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		lockedElement.setContext(context);
		enabledElement.setContext(context);
		extensionElement.setContext(context);
	}

	/**
	 * @return
	 */
	@Override
	public EffectConfigElement clone() {
		final EffectConfigElement element = new EffectConfigElement();
		element.setLocked(isLocked());
		element.setEnabled(isEnabled());
		if (getReference() != null) {
			element.setReference(getReference().clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		EffectConfigElement element = (EffectConfigElement) source;
		element.setLocked(isLocked());
		element.setEnabled(isEnabled());
		if (element.getReference() != null) {
			setReference(element.getReference().clone());
		}
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReference<EffectExtensionConfig> getReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setReference(final ConfigurableExtensionReference<EffectExtensionConfig> reference) {
		extensionElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<EffectExtensionConfig> getExtensionElement() {
		return extensionElement;
	}

	/**
	 * @return
	 */
	public boolean isLocked() {
		return lockedElement.getValue();
	}

	/**
	 * @param locked
	 */
	public void setLocked(final boolean locked) {
		lockedElement.setValue(locked);
	}

	/**
	 * @return
	 */
	public BooleanElement getLockedElement() {
		return lockedElement;
	}

	/**
	 * @return
	 */
	public boolean isEnabled() {
		return enabledElement.getValue();
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(final boolean enabled) {
		enabledElement.setValue(enabled);
	}

	/**
	 * @return
	 */
	public BooleanElement getEnabledElement() {
		return enabledElement;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final EffectConfigElement other = (EffectConfigElement) obj;
		if (enabledElement == null) {
			if (other.enabledElement != null) {
				return false;
			}
		}
		else if (!enabledElement.equals(other.enabledElement)) {
			return false;
		}
		if (extensionElement == null) {
			if (other.extensionElement != null) {
				return false;
			}
		}
		else if (!extensionElement.equals(other.extensionElement)) {
			return false;
		}
		if (lockedElement == null) {
			if (other.lockedElement != null) {
				return false;
			}
		}
		else if (!lockedElement.equals(other.lockedElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		extensionElement.dispose();
		enabledElement.dispose();
		lockedElement.dispose();
		super.dispose();
	}
}
