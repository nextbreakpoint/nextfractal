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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.SingleConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfig extends AbstractConfigElement {
	public static final String CLASS_ID = "TwisterConfig";
	private static final long serialVersionUID = 1L;
	private final ColorElement backgroundElement = new ColorElement(Color32bit.BLACK);
	private final SingleConfigElement<FrameConfigElement> frameSingleElement = new SingleConfigElement<FrameConfigElement>("FrameSingleElement");
	private final SingleConfigElement<EffectConfigElement> effectSingleElement = new SingleConfigElement<EffectConfigElement>("EffectSingleElement");

	/**
	 * 
	 */
	public TwisterConfig() {
		super(TwisterConfig.CLASS_ID);
	}

	/**
	 * Returns the background color.
	 * 
	 * @return the background color.
	 */
	public Color32bit getBackground() {
		return backgroundElement.getValue();
	}

	/**
	 * Sets the background color.
	 * 
	 * @param background the background color to set.
	 */
	public void setBackground(final Color32bit background) {
		backgroundElement.setValue(background);
	}

	/**
	 * Returns the frameConfigElement.
	 * 
	 * @return the frameConfigElement.
	 */
	public FrameConfigElement getFrameConfigElement() {
		return frameSingleElement.getValue();
	}

	/**
	 * Sets the frameConfigElement.
	 * 
	 * @param frameElement the frameConfigElement to set.
	 */
	public void setFrameConfigElement(final FrameConfigElement frameElement) {
		frameSingleElement.setValue(frameElement);
	}

	/**
	 * Returns the effectConfigElement.
	 * 
	 * @return the effectConfigElement.
	 */
	public EffectConfigElement getEffectConfigElement() {
		return effectSingleElement.getValue();
	}

	/**
	 * Sets the effectConfigElement.
	 * 
	 * @param listElement the effectConfigElement to set.
	 */
	public void setEffectConfigElement(final EffectConfigElement effectElement) {
		effectSingleElement.setValue(effectElement);
	}

	/**
	 * @return
	 */
	@Override
	public TwisterConfig clone() {
		final TwisterConfig config = new TwisterConfig();
		config.setBackground(getBackground());
		config.setFrameConfigElement(getFrameConfigElement().clone());
		config.setEffectConfigElement(getEffectConfigElement().clone());
		return config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		TwisterConfig config = (TwisterConfig) source;
		getBackgroundElement().copyFrom(config.getBackgroundElement());
		getFrameSingleElement().copyFrom(config.getFrameSingleElement());
		getEffectSingleElement().copyFrom(config.getEffectSingleElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		backgroundElement.setContext(getContext());
		frameSingleElement.setContext(getContext());
		effectSingleElement.setContext(getContext());
	}

	/**
	 * @return
	 */
	public ColorElement getBackgroundElement() {
		return backgroundElement;
	}

	/**
	 * @return the effectSingleElement
	 */
	public SingleConfigElement<EffectConfigElement> getEffectSingleElement() {
		return effectSingleElement;
	}

	/**
	 * @return the frameSingleElement
	 */
	public SingleConfigElement<FrameConfigElement> getFrameSingleElement() {
		return frameSingleElement;
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
		final TwisterConfig other = (TwisterConfig) obj;
		if (backgroundElement == null) {
			if (other.backgroundElement != null) {
				return false;
			}
		}
		else if (!backgroundElement.equals(other.backgroundElement)) {
			return false;
		}
		if (effectSingleElement == null) {
			if (other.effectSingleElement != null) {
				return false;
			}
		}
		else if (!effectSingleElement.equals(other.effectSingleElement)) {
			return false;
		}
		if (frameSingleElement == null) {
			if (other.frameSingleElement != null) {
				return false;
			}
		}
		else if (!frameSingleElement.equals(other.frameSingleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		backgroundElement.dispose();
		frameSingleElement.dispose();
		effectSingleElement.dispose();
		super.dispose();
	}
}
