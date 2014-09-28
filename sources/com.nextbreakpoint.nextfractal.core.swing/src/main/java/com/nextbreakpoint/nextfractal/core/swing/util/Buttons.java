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
package com.nextbreakpoint.nextfractal.core.swing.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * @author Andrea Medeghini
 */
public class Buttons {
	public static JComponent createLayoutComponent(final List<JButton> segmentButtons) {
		final Box layoutBox = Box.createHorizontalBox();
		for (final JButton button : segmentButtons) {
			layoutBox.add(button);
		}
		return layoutBox;
	}

	public static JButton createSegmentButton(final String style, final String position, final ButtonGroup buttonGrp) {
		final JButton button = GUIFactory.createButton((String) null, (String) null);
		button.putClientProperty("JButton.buttonType", style);
		button.putClientProperty("JButton.segmentPosition", position);
		if (buttonGrp != null) {
			buttonGrp.add(button);
		}
		return button;
	}

	public static List<JButton> createSegmentButtonsWithStyle(final int numButtons, final ButtonGroup buttonGrp, final String style) {
		final List<JButton> buttons = new ArrayList<JButton>();
		if (numButtons == 1) {
			buttons.add(createSegmentButton(style, "only", buttonGrp));
		}
		else {
			buttons.add(createSegmentButton(style, "first", buttonGrp));
			for (int i = 0; i < numButtons - 2; ++i) {
				buttons.add(createSegmentButton(style, "middle", buttonGrp));
			}
			buttons.add(createSegmentButton(style, "last", buttonGrp));
		}
		return buttons;
	}

	public static List<JButton> createSegmentedButtons(final int numButtons, final ButtonGroup buttonGroup) {
		return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmented");
	}

	public static List<JButton> createSegmentedRoundRectButtons(final int numButtons, final ButtonGroup buttonGroup) {
		return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedRoundRect");
	}

	public static List<JButton> createSegmentedCapsuleButtons(final int numButtons, final ButtonGroup buttonGroup) {
		return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedCapsule");
	}

	public static List<JButton> createSegmentedTexturedButtons(final int numButtons, final ButtonGroup buttonGroup) {
		return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedTextured");
	}
}
