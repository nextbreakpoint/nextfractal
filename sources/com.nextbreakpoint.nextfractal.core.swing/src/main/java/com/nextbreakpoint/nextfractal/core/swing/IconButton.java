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
package com.nextbreakpoint.nextfractal.core.swing;

import java.awt.Image;

import javax.swing.Action;
import javax.swing.GrayFilter;
import javax.swing.JButton;

/**
 * @author Andrea Medeghini
 */
public class IconButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Image disabledImage;
	private Image normalImage;
	private Image pressedImage;
	private Image focusedImage;
	private long repeatTime;

	/**
	 * @param normalImage
	 * @param pressedImage
	 * @param focusedImage
	 * @param action
	 */
	public IconButton(final Image normalImage, final Image pressedImage, final Image focusedImage, final Action action) {
		super(action);
		this.normalImage = normalImage;
		this.pressedImage = pressedImage;
		this.focusedImage = focusedImage;
		setFocusable(true);
		setOpaque(false);
		this.setUI(new IconButtonUI());
	}

	/**
	 * @return the focusedImage
	 */
	public Image getFocusedImage() {
		return focusedImage;
	}

	/**
	 * @param focusedImage the focusedImage to set
	 */
	public void setFocusedImage(final Image focusedImage) {
		this.focusedImage = focusedImage;
	}

	/**
	 * @return the normalImage
	 */
	public Image getNormalImage() {
		return normalImage;
	}

	/**
	 * @param normalImage the normalImage to set
	 */
	public void setNormalImage(final Image normalImage) {
		this.normalImage = normalImage;
	}

	/**
	 * @return the pressedImage
	 */
	public Image getPressedImage() {
		return pressedImage;
	}

	/**
	 * @param pressedImage the pressedImage to set
	 */
	public void setPressedImage(final Image pressedImage) {
		this.pressedImage = pressedImage;
	}

	/**
	 * @return
	 */
	public Image getDisabledImage() {
		if (disabledImage == null) {
			disabledImage = GrayFilter.createDisabledImage(normalImage);
		}
		return disabledImage;
	}

	/**
	 * @return the repeatTime
	 */
	public long getRepeatTime() {
		return repeatTime;
	}

	/**
	 * @param repeatTime the repeatTime to set
	 */
	public void setRepeatTime(final long repeatTime) {
		this.repeatTime = repeatTime;
	}
}
