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
package com.nextbreakpoint.nextfractal.core.ui.swing.util;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

/**
 * @author Andrea Medeghini
 */
public class GUIFactory {
	public static final Font BIG_FONT = new Font("arial", Font.PLAIN, 14);
	public static final Font SMALL_FONT = new Font("arial", Font.PLAIN, 10);
	public static final Font NORMAL_FONT = new Font("arial", Font.PLAIN, 12);
	public static final int DEFAULT_HEIGHT = 24;
	public static final int ICON_WIDTH = 20;
	public static final int ICON_HEIGHT = 20;

	private GUIFactory() {
	}

	/**
	 * @param text
	 * @param alignment
	 * @return
	 */
	public static JLabel createLabel(final String text, final int alignment) {
		final JLabel label = new JLabel(text, alignment);
		label.setFont(NORMAL_FONT);
		return label;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JTextField createTextField(final String text, final String tooltip) {
		final JTextField textField = new JTextField(text);
		textField.setToolTipText(tooltip);
		textField.setFont(NORMAL_FONT);
		return textField;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @param columns
	 * @return
	 */
	public static JTextField createTextField(final String text, final String tooltip, final int columns) {
		final JTextField textField = new JTextField(text, columns);
		textField.setToolTipText(tooltip);
		textField.setFont(NORMAL_FONT);
		return textField;
	}

	/**
	 * @param action
	 * @param tooltip
	 * @return
	 */
	public static JButton createButton(final Action action, final String tooltip) {
		final JButton button = new JButton(action);
		button.setToolTipText(tooltip);
		button.setFont(NORMAL_FONT);
		return button;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JButton createButton(final String text, final String tooltip) {
		final JButton button = new JButton(text);
		button.setToolTipText(tooltip);
		button.setFont(NORMAL_FONT);
		return button;
	}

	/**
	 * @param action
	 * @param tooltip
	 * @return
	 */
	public static JCheckBox createCheckBox(final Action action, final String tooltip) {
		final JCheckBox checkBox = new JCheckBox(action);
		checkBox.setToolTipText(tooltip);
		checkBox.setFont(NORMAL_FONT);
		return checkBox;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JCheckBox createCheckBox(final String text, final String tooltip) {
		final JCheckBox checkBox = new JCheckBox(text);
		checkBox.setToolTipText(tooltip);
		checkBox.setFont(NORMAL_FONT);
		return checkBox;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JComboBox createComboBox(final ComboBoxModel model, final String tooltip) {
		final JComboBox comboBox = new JComboBox(model);
		comboBox.setToolTipText(tooltip);
		comboBox.setFont(NORMAL_FONT);
		return comboBox;
	}

	/**
	 * @param tooltip
	 * @return
	 */
	public static JComboBox createComboBox(final String tooltip) {
		final JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText(tooltip);
		comboBox.setFont(NORMAL_FONT);
		return comboBox;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JTable createTable(final TableModel model, final String tooltip) {
		final JTable table = new JTable(model);
		table.setToolTipText(tooltip);
		table.setFont(NORMAL_FONT);
		table.getTableHeader().setFont(NORMAL_FONT);
		return table;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JTree createTree(final TreeModel model, final String tooltip) {
		final JTree tree = new JTree(model);
		tree.setToolTipText(tooltip);
		tree.setFont(NORMAL_FONT);
		return tree;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JSpinner createSpinner(final SpinnerModel model, final String tooltip) {
		final JSpinner spinner = new JSpinner(model);
		spinner.setToolTipText(tooltip);
		if (spinner.getEditor() instanceof JSpinner.DefaultEditor) {
			((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFont(NORMAL_FONT);
		}
		spinner.getEditor().setFont(NORMAL_FONT);
		spinner.setFont(NORMAL_FONT);
		return spinner;
	}

	/**
	 * @param text
	 * @param alignment
	 * @return
	 */
	public static JLabel createSmallLabel(final String text, final int alignment) {
		final JLabel label = new JLabel(text, alignment);
		label.setFont(SMALL_FONT);
		return label;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JTextField createSmallTextField(final String text, final String tooltip) {
		final JTextField textField = new JTextField(text);
		textField.setToolTipText(tooltip);
		textField.setFont(SMALL_FONT);
		return textField;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @param columns
	 * @return
	 */
	public static JTextField createSmallTextField(final String text, final String tooltip, final int columns) {
		final JTextField textField = new JTextField(text, columns);
		textField.setToolTipText(tooltip);
		textField.setFont(SMALL_FONT);
		return textField;
	}

	/**
	 * @param action
	 * @param tooltip
	 * @return
	 */
	public static JButton createSmallButton(final Action action, final String tooltip) {
		final JButton button = new JButton(action);
		button.setToolTipText(tooltip);
		button.setFont(SMALL_FONT);
		return button;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JButton createSmallButton(final String text, final String tooltip) {
		final JButton button = new JButton(text);
		button.setToolTipText(tooltip);
		button.setFont(SMALL_FONT);
		return button;
	}

	/**
	 * @param action
	 * @param tooltip
	 * @return
	 */
	public static JCheckBox createSmallCheckBox(final Action action, final String tooltip) {
		final JCheckBox checkBox = new JCheckBox(action);
		checkBox.setToolTipText(tooltip);
		checkBox.setFont(SMALL_FONT);
		return checkBox;
	}

	/**
	 * @param text
	 * @param tooltip
	 * @return
	 */
	public static JCheckBox createSmallCheckBox(final String text, final String tooltip) {
		final JCheckBox checkBox = new JCheckBox(text);
		checkBox.setToolTipText(tooltip);
		checkBox.setFont(SMALL_FONT);
		return checkBox;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JComboBox createSmallComboBox(final ComboBoxModel model, final String tooltip) {
		final JComboBox comboBox = new JComboBox(model);
		comboBox.setToolTipText(tooltip);
		comboBox.setFont(SMALL_FONT);
		return comboBox;
	}

	/**
	 * @param tooltip
	 * @return
	 */
	public static JComboBox createSmallComboBox(final String tooltip) {
		final JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText(tooltip);
		comboBox.setFont(SMALL_FONT);
		return comboBox;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JTable createSmallTable(final TableModel model, final String tooltip) {
		final JTable table = new JTable(model);
		table.setToolTipText(tooltip);
		table.setFont(SMALL_FONT);
		table.getTableHeader().setFont(SMALL_FONT);
		return table;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JTree createSmallTree(final TreeModel model, final String tooltip) {
		final JTree tree = new JTree(model);
		tree.setToolTipText(tooltip);
		tree.setFont(SMALL_FONT);
		return tree;
	}

	/**
	 * @param model
	 * @param tooltip
	 * @return
	 */
	public static JSpinner createSmallSpinner(final SpinnerModel model, final String tooltip) {
		final JSpinner spinner = new JSpinner(model);
		spinner.setToolTipText(tooltip);
		if (spinner.getEditor() instanceof JSpinner.DefaultEditor) {
			((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFont(SMALL_FONT);
		}
		spinner.getEditor().setFont(SMALL_FONT);
		spinner.setFont(SMALL_FONT);
		return spinner;
	}
}
