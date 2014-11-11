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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.elements.ColorElement;
import com.nextbreakpoint.nextfractal.core.elements.DoubleElement;
import com.nextbreakpoint.nextfractal.core.elements.FontElement;
import com.nextbreakpoint.nextfractal.core.elements.StringElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class TextConfig extends ImageExtensionConfig {
	private static final Color32bit DEFAULT_COLOR = Color32bit.BLACK;
	private static final String DEFAULT_TEXT = "";
	private static final Font DEFAULT_FONT = new Font("arial", Font.PLAIN, 12);
	private static final Double DEFAULT_SIZE = new Double(5);
	private static final Double DEFAULT_LEFT = new Double(5);
	private static final Double DEFAULT_TOP = new Double(95);
	private static final Double DEFAULT_ROTATION = new Double(0);
	private static final long serialVersionUID = 1L;
	private ColorElement colorElement;
	private DoubleElement sizeElement;
	private FontElement fontElement;
	private StringElement textElement;
	private DoubleElement leftElement;
	private DoubleElement topElement;
	private DoubleElement rotationElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorElement = new ColorElement(getDefaultColor());
		sizeElement = new DoubleElement(getDefaultSize());
		fontElement = new FontElement(getDefaultFont());
		textElement = new StringElement(getDefaultText());
		leftElement = new DoubleElement(getDefaultLeft());
		topElement = new DoubleElement(getDefaultTop());
		rotationElement = new DoubleElement(getDefaultRotation());
		colorElement.addChangeListener(new ValueChangeEventDispatcher());
		sizeElement.addChangeListener(new ValueChangeEventDispatcher());
		fontElement.addChangeListener(new ValueChangeEventDispatcher());
		textElement.addChangeListener(new ValueChangeEventDispatcher());
		leftElement.addChangeListener(new ValueChangeEventDispatcher());
		topElement.addChangeListener(new ValueChangeEventDispatcher());
		rotationElement.addChangeListener(new ValueChangeEventDispatcher());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(colorElement);
		elements.add(fontElement);
		elements.add(sizeElement);
		elements.add(textElement);
		elements.add(leftElement);
		elements.add(topElement);
		elements.add(rotationElement);
		return elements;
	}

	/**
	 * @return the color.
	 */
	public Color32bit getColor() {
		return colorElement.getValue();
	}

	/**
	 * @return the default color.
	 */
	public Color32bit getDefaultColor() {
		return TextConfig.DEFAULT_COLOR;
	}

	/**
	 * @param color the color to set.
	 */
	public void setColor(final Color32bit color) {
		colorElement.setValue(color);
	}

	/**
	 * @return
	 */
	protected ColorElement getColorElement() {
		return colorElement;
	}

	/**
	 * @return the size.
	 */
	public Double getSize() {
		return sizeElement.getValue();
	}

	/**
	 * @return the default size.
	 */
	public Double getDefaultSize() {
		return TextConfig.DEFAULT_SIZE;
	}

	/**
	 * @param size the size to set.
	 */
	public void setSize(final Double size) {
		sizeElement.setValue(size);
	}

	/**
	 * @return
	 */
	protected DoubleElement getSizeElement() {
		return sizeElement;
	}

	/**
	 * @return the font.
	 */
	public Font getFont() {
		return fontElement.getValue();
	}

	/**
	 * @return the default font.
	 */
	public Font getDefaultFont() {
		return TextConfig.DEFAULT_FONT;
	}

	/**
	 * @param font the font to set.
	 */
	public void setFont(final Font font) {
		fontElement.setValue(font);
	}

	/**
	 * @return
	 */
	protected FontElement getFontElement() {
		return fontElement;
	}

	/**
	 * @return the text.
	 */
	public String getText() {
		return textElement.getValue();
	}

	/**
	 * @return the default text.
	 */
	public String getDefaultText() {
		return TextConfig.DEFAULT_TEXT;
	}

	/**
	 * @param text the text to set.
	 */
	public void setText(final String text) {
		textElement.setValue(text);
	}

	/**
	 * @return
	 */
	protected StringElement getTextElement() {
		return textElement;
	}

	/**
	 * @return the left.
	 */
	public Double getLeft() {
		return leftElement.getValue();
	}

	/**
	 * @return the default left.
	 */
	public Double getDefaultLeft() {
		return TextConfig.DEFAULT_LEFT;
	}

	/**
	 * @param left the left to set.
	 */
	public void setLeft(final Double left) {
		leftElement.setValue(left);
	}

	/**
	 * @return
	 */
	protected DoubleElement getLeftElement() {
		return leftElement;
	}

	/**
	 * @return the top.
	 */
	public Double getTop() {
		return topElement.getValue();
	}

	/**
	 * @return the default top.
	 */
	public Double getDefaultTop() {
		return TextConfig.DEFAULT_TOP;
	}

	/**
	 * @param top the top to set.
	 */
	public void setTop(final Double top) {
		topElement.setValue(top);
	}

	/**
	 * @return
	 */
	protected DoubleElement getTopElement() {
		return topElement;
	}

	/**
	 * @return the rotation.
	 */
	public Double getRotation() {
		return rotationElement.getValue();
	}

	/**
	 * @return the default rotation.
	 */
	public Double getDefaultRotation() {
		return TextConfig.DEFAULT_ROTATION;
	}

	/**
	 * @param rotation the rotation to set.
	 */
	public void setRotation(final Double rotation) {
		rotationElement.setValue(rotation);
	}

	/**
	 * @return
	 */
	protected DoubleElement getRotationElement() {
		return rotationElement;
	}

	/**
	 * @return
	 */
	@Override
	public TextConfig clone() {
		final TextConfig config = new TextConfig();
		config.setColor(getColor());
		config.setFont(getFont());
		config.setSize(getSize());
		config.setText(getText());
		config.setLeft(getLeft());
		config.setTop(getTop());
		config.setRotation(getRotation());
		return config;
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
		final TextConfig other = (TextConfig) obj;
		if (colorElement == null) {
			if (other.colorElement != null) {
				return false;
			}
		}
		else if (!colorElement.equals(other.colorElement)) {
			return false;
		}
		if (sizeElement == null) {
			if (other.sizeElement != null) {
				return false;
			}
		}
		else if (!sizeElement.equals(other.sizeElement)) {
			return false;
		}
		if (fontElement == null) {
			if (other.fontElement != null) {
				return false;
			}
		}
		else if (!fontElement.equals(other.fontElement)) {
			return false;
		}
		if (textElement == null) {
			if (other.textElement != null) {
				return false;
			}
		}
		else if (!textElement.equals(other.textElement)) {
			return false;
		}
		if (leftElement == null) {
			if (other.leftElement != null) {
				return false;
			}
		}
		else if (!leftElement.equals(other.leftElement)) {
			return false;
		}
		if (topElement == null) {
			if (other.topElement != null) {
				return false;
			}
		}
		else if (!topElement.equals(other.topElement)) {
			return false;
		}
		if (rotationElement == null) {
			if (other.rotationElement != null) {
				return false;
			}
		}
		else if (!rotationElement.equals(other.rotationElement)) {
			return false;
		}
		return true;
	}

	private class ValueChangeEventDispatcher implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			fireValueChanged(e);
		}
	}
}
