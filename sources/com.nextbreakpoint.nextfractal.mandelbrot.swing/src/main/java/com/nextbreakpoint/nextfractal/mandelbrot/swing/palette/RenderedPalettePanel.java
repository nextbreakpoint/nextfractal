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
package com.nextbreakpoint.nextfractal.mandelbrot.swing.palette;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SingleSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeEvent;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteField;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.util.Registry;
import com.nextbreakpoint.nextfractal.mandelbrot.swing.MandelbrotSwingResources;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPaletteParam;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPalettePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final String PARAM_SELECTION_MODEL = "param.selection.model";
	protected static final String PALETTE_PARAM_MODEL = "palette.param.model";
	protected static final String PALETTE_MODEL = "palette.model";
	protected static final String COMBOBOX = "comboBox";
	protected static final int[] size = new int[] { 8, 1 };
	protected static final RenderedPalette[][] palettes = new RenderedPalette[RenderedPalettePanel.size[0]][RenderedPalettePanel.size[1]];
	private final Registry<Object> registry = new Registry<Object>();
	private final ComboboxListener actionListener = new ComboboxListener();
	private final RenderedPaletteSelectionListener selectionListener = new RenderedPaletteSelectionListener();
	private final RenderedPaletteParamListener paletteParamListener = new RenderedPaletteParamListener();
	private final RenderedPaletteListener paletteListener = new RenderedPaletteListener();
	private final RenderedPaletteModel model;

	/**
	 * @param model
	 */
	public RenderedPalettePanel(final RenderedPaletteModel model) {
		if (model == null) {
			throw new NullPointerException("model == nul");
		}
		this.model = model;
		setupPanel(this);
	}

	/**
	 * @param panel
	 */
	protected void setupPanel(final JPanel panel) {
		registry.put(RenderedPalettePanel.PALETTE_MODEL, model);
		panel.setLayout(new BorderLayout());
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(MandelbrotSwingResources.getInstance().getString("tab.params.label"), createParamsPanel(registry, model));
		tabbedPane.addTab(MandelbrotSwingResources.getInstance().getString("tab.palettes.label"), createPalettesPanel(registry));
		panel.add(createPalettePanel(registry, model), BorderLayout.CENTER);
		panel.add(tabbedPane, BorderLayout.SOUTH);
		addRenderedPaletteListener();
		addRenderedPaletteParamListener();
		addRenderedPaletteActionListener();
		addSelectionListener();
		getComboBox(RenderedPalettePanel.COMBOBOX).setSelectedIndex(0);
		// getSingleSelectionModel(PARAM_SELECTION_MODEL).setSelectedIndex(getComboBox(COMBOBOX).getSelectedIndex());
	}

	/**
	 * @param registry
	 * @param model
	 * @return
	 */
	protected JPanel createParamsPanel(final Registry<Object> registry, final RenderedPaletteModel model) {
		final JPanel panel = new JPanel(new BorderLayout());
		final ComboBoxModel comboBoxModel = new RenderedPaletteComboBoxModel(model);
		final JComboBox comboBox = GUIFactory.createComboBox(comboBoxModel, MandelbrotSwingResources.getInstance().getString("params.select.tooltip"));
		registry.put(RenderedPalettePanel.COMBOBOX, comboBox);
		final JPanel comboBoxPanel = new JPanel(new BorderLayout());
		comboBoxPanel.add(comboBox, BorderLayout.CENTER);
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JButton buttonCreate = GUIFactory.createButton(new CreateAction(model), MandelbrotSwingResources.getInstance().getString("params.create.button.tooltip"));
		final JButton buttonDelete = GUIFactory.createButton(new DeleteAction(model), MandelbrotSwingResources.getInstance().getString("params.delete.button.tooltip"));
		final JButton buttonInsert = GUIFactory.createButton(new InsertAction(model), MandelbrotSwingResources.getInstance().getString("params.insert.button.tooltip"));
		final JButton buttonReverse = GUIFactory.createButton(new ReverseAction(model), MandelbrotSwingResources.getInstance().getString("params.reverse.button.tooltip"));
		buttonPanel.add(buttonCreate);
		buttonPanel.add(buttonDelete);
		buttonPanel.add(buttonInsert);
		buttonPanel.add(buttonReverse);
		comboBox.setRenderer(new RenderedPaletteParamListCellRenderer());
		comboBoxPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		final RenderedPaletteParamModel paramModel = new DefaultRenderedPaletteParamModel();
		final RenderedPaletteParamPanel paramPanel = new RenderedPaletteParamPanel(paramModel);
		registry.put(RenderedPalettePanel.PALETTE_PARAM_MODEL, paramModel);
		panel.add(comboBoxPanel, BorderLayout.NORTH);
		panel.add(paramPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.setOpaque(false);
		// paramModel.setPaletteParam((RenderedPaletteParam) comboBox.getModel().getSelectedItem(), false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createPalettesPanel(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new GridLayout(RenderedPalettePanel.size[0], RenderedPalettePanel.size[1]));
		for (int i = 0; i < RenderedPalettePanel.size[0]; i++) {
			for (int j = 0; j < RenderedPalettePanel.size[1]; j++) {
				PaletteField paletteField = null;
				if (RenderedPalettePanel.palettes[i][j] != null) {
					paletteField = new PaletteField(new DefaultRenderedPaletteModel(RenderedPalettePanel.palettes[i][j]));
				}
				else {
					paletteField = new PaletteField(new DefaultRenderedPaletteModel(new DefaultRenderedPalette(Color.WHITE, Color.BLACK)));
				}
				final Dimension size = new Dimension(128, 32);
				paletteField.setPreferredSize(size);
				paletteField.setMinimumSize(size);
				paletteField.setMaximumSize(size);
				paletteField.getModel().addPaletteChangeListener(new PaletteListener(i, j));
				paletteField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.GRAY)));
				panel.add(paletteField);
			}
		}
		return panel;
	}

	/**
	 * @param registry
	 * @param model
	 * @return
	 */
	protected JPanel createPalettePanel(final Registry<Object> registry, final RenderedPaletteModel model) {
		final JPanel panel = new JPanel(new BorderLayout());
		final RenderedPaletteField paletteField = new RenderedPaletteField(model);
		// paletteField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		paletteField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		registry.put(RenderedPalettePanel.PARAM_SELECTION_MODEL, paletteField.getSelectionModel());
		panel.add(paletteField);
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		return panel;
	}

	/**
	 * @param comboBoxId
	 * @return
	 */
	protected JComboBox getComboBox(final String comboBoxId) {
		return (JComboBox) registry.get(comboBoxId);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected RenderedPaletteModel getRenderedPaletteModel(final String modelId) {
		return (RenderedPaletteModel) registry.get(modelId);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected RenderedPaletteParamModel getRenderedPaletteParamModel(final String modelId) {
		return (RenderedPaletteParamModel) registry.get(modelId);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected SingleSelectionModel getSingleSelectionModel(final String modelId) {
		return (SingleSelectionModel) registry.get(modelId);
	}

	/**
	 * 
	 */
	protected void addRenderedPaletteActionListener() {
		final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
		if (comboBox != null) {
			comboBox.addActionListener(actionListener);
		}
	}

	/**
	 * 
	 */
	protected void addRenderedPaletteListener() {
		final RenderedPaletteModel model = getRenderedPaletteModel(RenderedPalettePanel.PALETTE_MODEL);
		if (model != null) {
			model.addPaletteChangeListener(paletteListener);
		}
	}

	/**
	 * 
	 */
	protected void addRenderedPaletteParamListener() {
		final RenderedPaletteParamModel model = getRenderedPaletteParamModel(RenderedPalettePanel.PALETTE_PARAM_MODEL);
		if (model != null) {
			model.addPaletteChangeListener(paletteParamListener);
		}
	}

	/**
	 * 
	 */
	protected void addSelectionListener() {
		final SingleSelectionModel model = getSingleSelectionModel(RenderedPalettePanel.PARAM_SELECTION_MODEL);
		if (model != null) {
			model.addChangeListener(selectionListener);
		}
	}

	/**
	 * 
	 */
	protected void removeRenderedPaletteActionListener() {
		final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
		if (comboBox != null) {
			comboBox.removeActionListener(actionListener);
		}
	}

	/**
	 * 
	 */
	protected void removeRenderedPaletteListener() {
		final RenderedPaletteModel model = getRenderedPaletteModel(RenderedPalettePanel.PALETTE_MODEL);
		if (model != null) {
			model.removePaletteChangeListener(paletteListener);
		}
	}

	/**
	 * 
	 */
	protected void removeRenderedPaletteParamListener() {
		final RenderedPaletteParamModel model = getRenderedPaletteParamModel(RenderedPalettePanel.PALETTE_PARAM_MODEL);
		if (model != null) {
			model.removePaletteChangeListener(paletteParamListener);
		}
	}

	/**
	 * 
	 */
	protected void removeSelectionListener() {
		final SingleSelectionModel model = getSingleSelectionModel(RenderedPalettePanel.PARAM_SELECTION_MODEL);
		if (model != null) {
			model.removeChangeListener(selectionListener);
		}
	}

	/**
	 * @return
	 */
	protected RenderedPaletteModel getModel() {
		return model;
	}

	private class ComboboxListener implements ActionListener {
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final RenderedPaletteParamModel paramModel = getRenderedPaletteParamModel(RenderedPalettePanel.PALETTE_PARAM_MODEL);
			final SingleSelectionModel selectionModel = getSingleSelectionModel(RenderedPalettePanel.PARAM_SELECTION_MODEL);
			final JComboBox comboBox = (JComboBox) e.getSource();
			if (comboBox.getModel().getSelectedItem() != null) {
				removeRenderedPaletteParamListener();
				removeSelectionListener();
				selectionModel.setSelectedIndex(comboBox.getSelectedIndex());
				paramModel.setPaletteParam((RenderedPaletteParam) comboBox.getModel().getSelectedItem(), false);
				addRenderedPaletteParamListener();
				addSelectionListener();
			}
		}
	}

	private class RenderedPaletteParamListener implements PaletteChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeEvent)
		 */
		public void paletteChanged(final PaletteChangeEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			final RenderedPaletteParamModel paramModel = (RenderedPaletteParamModel) e.getSource();
			final RenderedPaletteParam selectedParam = (RenderedPaletteParam) comboBox.getSelectedItem();
			final RenderedPaletteParam[] newParams = new RenderedPaletteParam[model.getRenderedPalette().getParamCount()];
			for (int i = 0; i < newParams.length; i++) {
				if (model.getRenderedPalette().getParam(i) != selectedParam) {
					newParams[i] = model.getRenderedPalette().getParam(i);
				}
				else {
					newParams[i] = paramModel.getPaletteParam();
				}
			}
			final RenderedPalette palette = new RenderedPalette(newParams);
			removeRenderedPaletteActionListener();
			model.setRenderedPalette(palette, false);
			addRenderedPaletteActionListener();
		}
	}

	private class RenderedPaletteListener implements PaletteChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeEvent)
		 */
		public void paletteChanged(final PaletteChangeEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			int index = comboBox.getSelectedIndex();
			if (index >= model.getRenderedPalette().getParamCount()) {
				index = 0;
			}
			((RenderedPaletteComboBoxModel) comboBox.getModel()).updateModel(index);
		}
	}

	private class RenderedPaletteSelectionListener implements ChangeListener {
		/**
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(final ChangeEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			final SingleSelectionModel selectionModel = (SingleSelectionModel) e.getSource();
			if (selectionModel.getSelectedIndex() != -1) {
				comboBox.setSelectedIndex(selectionModel.getSelectedIndex());
			}
		}
	}

	private class PaletteListener implements PaletteChangeListener {
		private final int x;
		private final int y;

		/**
		 * @param x
		 * @param y
		 */
		public PaletteListener(final int x, final int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeEvent)
		 */
		public void paletteChanged(final PaletteChangeEvent e) {
			final RenderedPaletteModel model = (RenderedPaletteModel) e.getSource();
			RenderedPalettePanel.palettes[x][y] = model.getRenderedPalette();
		}
	}

	private class CreateAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final RenderedPaletteModel model;

		/**
		 * @param model
		 */
		public CreateAction(final RenderedPaletteModel model) {
			super(MandelbrotSwingResources.getInstance().getString("params.create.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			if (comboBox != null) {
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[model.getRenderedPalette().getParamCount() + 1];
				for (int i = 0; i < (newParams.length - 1); i++) {
					final ExtensionReference[] formulas = new ExtensionReference[4];
					formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
					formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
					formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
					formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
					final int[] colors = new int[2];
					colors[0] = model.getRenderedPalette().getParam(i).getColor(0);
					colors[1] = model.getRenderedPalette().getParam(i).getColor(1);
					newParams[i] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize() * 0.75);
				}
				newParams[newParams.length - 1] = new DefaultRenderedPaletteParam(25);
				removeRenderedPaletteActionListener();
				final RenderedPalette palette = new RenderedPalette(newParams);
				model.setRenderedPalette(palette, false);
				addRenderedPaletteActionListener();
				comboBox.setSelectedIndex(newParams.length - 1);
			}
		}
	}

	private class DeleteAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final RenderedPaletteModel model;

		/**
		 * @param model
		 */
		public DeleteAction(final RenderedPaletteModel model) {
			super(MandelbrotSwingResources.getInstance().getString("params.delete.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			if (model.getRenderedPalette().getParamCount() == 1) {
				return;
			}
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			if (comboBox != null) {
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[model.getRenderedPalette().getParamCount() - 1];
				final double size = ((RenderedPaletteParam) comboBox.getSelectedItem()).getSize() / newParams.length;
				final int selectedIndex = comboBox.getSelectedIndex();
				for (int i = 0; i < selectedIndex; i++) {
					final ExtensionReference[] formulas = new ExtensionReference[4];
					formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
					formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
					formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
					formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
					final int[] colors = new int[2];
					colors[0] = model.getRenderedPalette().getParam(i).getColor(0);
					colors[1] = model.getRenderedPalette().getParam(i).getColor(1);
					newParams[i] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize() + size);
				}
				for (int i = selectedIndex + 1; i < (newParams.length + 1); i++) {
					final ExtensionReference[] formulas = new ExtensionReference[4];
					formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
					formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
					formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
					formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
					final int[] colors = new int[2];
					colors[0] = model.getRenderedPalette().getParam(i).getColor(0);
					colors[1] = model.getRenderedPalette().getParam(i).getColor(1);
					newParams[i - 1] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize() + size);
				}
				if (comboBox.getSelectedIndex() > 0) {
					comboBox.setSelectedIndex(comboBox.getSelectedIndex() - 1);
				}
				else {
					comboBox.setSelectedIndex(0);
				}
				removeRenderedPaletteActionListener();
				final RenderedPalette palette = new RenderedPalette(newParams);
				model.setRenderedPalette(palette, false);
				addRenderedPaletteActionListener();
			}
		}
	}

	private class InsertAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final RenderedPaletteModel model;

		/**
		 * @param model
		 */
		public InsertAction(final RenderedPaletteModel model) {
			super(MandelbrotSwingResources.getInstance().getString("params.insert.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			if (comboBox != null) {
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[model.getRenderedPalette().getParamCount() + 1];
				final int selectedIndex = comboBox.getSelectedIndex();
				for (int i = 0; i < selectedIndex; i++) {
					final ExtensionReference[] formulas = new ExtensionReference[4];
					formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
					formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
					formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
					formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
					final int[] colors = new int[2];
					colors[0] = model.getRenderedPalette().getParam(i).getColor(0);
					colors[1] = model.getRenderedPalette().getParam(i).getColor(1);
					newParams[i] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize() * 0.75);
				}
				newParams[selectedIndex] = new DefaultRenderedPaletteParam(25);
				for (int i = selectedIndex; i < (newParams.length - 1); i++) {
					final ExtensionReference[] formulas = new ExtensionReference[4];
					formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
					formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
					formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
					formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
					final int[] colors = new int[2];
					colors[0] = model.getRenderedPalette().getParam(i).getColor(0);
					colors[1] = model.getRenderedPalette().getParam(i).getColor(1);
					newParams[i + 1] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize() * 0.75);
				}
				removeRenderedPaletteActionListener();
				final RenderedPalette palette = new RenderedPalette(newParams);
				model.setRenderedPalette(palette, false);
				addRenderedPaletteActionListener();
				comboBox.setSelectedIndex(selectedIndex);
			}
		}
	}

	private class ReverseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final RenderedPaletteModel model;

		/**
		 * @param model
		 */
		public ReverseAction(final RenderedPaletteModel model) {
			super(MandelbrotSwingResources.getInstance().getString("params.reverse.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			final JComboBox comboBox = getComboBox(RenderedPalettePanel.COMBOBOX);
			if (comboBox != null) {
				final RenderedPaletteParam[] newParams = new RenderedPaletteParam[model.getRenderedPalette().getParamCount()];
				final int selectedIndex = comboBox.getSelectedIndex();
				for (int i = 0; i < newParams.length; i++) {
					if (i == selectedIndex) {
						final ExtensionReference[] formulas = new ExtensionReference[4];
						formulas[0] = model.getRenderedPalette().getParam(i).getFormula(0);
						formulas[1] = model.getRenderedPalette().getParam(i).getFormula(1);
						formulas[2] = model.getRenderedPalette().getParam(i).getFormula(2);
						formulas[3] = model.getRenderedPalette().getParam(i).getFormula(3);
						final int[] colors = new int[2];
						colors[0] = model.getRenderedPalette().getParam(i).getColor(1);
						colors[1] = model.getRenderedPalette().getParam(i).getColor(0);
						newParams[i] = new RenderedPaletteParam(formulas, colors, model.getRenderedPalette().getParam(i).getSize());
					}
					else {
						newParams[i] = model.getRenderedPalette().getParam(i);
					}
				}
				removeRenderedPaletteActionListener();
				final RenderedPalette palette = new RenderedPalette(newParams);
				model.setRenderedPalette(palette, false);
				addRenderedPaletteActionListener();
			}
		}
	}
}
