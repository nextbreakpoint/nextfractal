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
package com.nextbreakpoint.nextfractal.mandelbrot.swing.palette;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SingleSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent;
import com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener;
import com.nextbreakpoint.nextfractal.core.swing.color.ColorChooser;
import com.nextbreakpoint.nextfractal.core.swing.color.ColorField;
import com.nextbreakpoint.nextfractal.core.swing.color.ColorFieldModel;
import com.nextbreakpoint.nextfractal.core.swing.color.DefaultColorFieldModel;
import com.nextbreakpoint.nextfractal.core.swing.extension.ExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeEvent;
import com.nextbreakpoint.nextfractal.core.swing.palette.PaletteChangeListener;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.util.Registry;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.swing.MandelbrotSwingResources;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteParamPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final String FORMULA_COMBOBOX_MODEL_PREFIX = "formula.comboBox.model";
	protected static final String FORMULA_COMBOBOX_MODEL_ALPHA = RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_PREFIX + ".ALPHA";
	protected static final String FORMULA_COMBOBOX_MODEL_RED = RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_PREFIX + ".RED";
	protected static final String FORMULA_COMBOBOX_MODEL_GREEN = RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_PREFIX + ".GREEN";
	protected static final String FORMULA_COMBOBOX_MODEL_BLUE = RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_PREFIX + ".BLUE";
	protected static final String FORMULA_SELECTION_MODEL_PREFIX = "formula.selection.model";
	protected static final String FORMULA_SELECTION_MODEL_ALPHA = RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_PREFIX + ".ALPHA";
	protected static final String FORMULA_SELECTION_MODEL_RED = RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_PREFIX + ".RED";
	protected static final String FORMULA_SELECTION_MODEL_GREEN = RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_PREFIX + ".GREEN";
	protected static final String FORMULA_SELECTION_MODEL_BLUE = RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_PREFIX + ".BLUE";
	protected static final String COLOR_MODEL_PREFIX = "color.model";
	protected static final String COLOR_MODEL_START = RenderedPaletteParamPanel.COLOR_MODEL_PREFIX + ".START";
	protected static final String COLOR_MODEL_END = RenderedPaletteParamPanel.COLOR_MODEL_PREFIX + ".END";
	// protected static final String SIZE_SPINNER_MODEL = "size.spinner.model";
	private final Registry<Object> registry = new Registry<Object>();
	private final RenderedPaletteParamModel model;
	private final RenderedPaletteParamListener paramListener = new RenderedPaletteParamListener();
	private final ColorFieldListener[] colorFieldListeners = new ColorFieldListener[2];
	private final FormulaSelectionListener[] formulaSelectionListeners = new FormulaSelectionListener[4];

	/**
	 * @param model
	 */
	public RenderedPaletteParamPanel(final RenderedPaletteParamModel model) {
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(Box.createHorizontalGlue());
		panel.add(createColorsPanel(model));
		panel.add(Box.createHorizontalGlue());
		panel.add(createFormulasPanel(model));
		panel.add(Box.createHorizontalGlue());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.GRAY)));
		colorFieldListeners[0] = new ColorFieldListener(0);
		colorFieldListeners[1] = new ColorFieldListener(1);
		formulaSelectionListeners[0] = new FormulaSelectionListener();
		formulaSelectionListeners[1] = new FormulaSelectionListener();
		formulaSelectionListeners[2] = new FormulaSelectionListener();
		formulaSelectionListeners[3] = new FormulaSelectionListener();
		addParamListener();
		addColorFieldListeners();
		addFormulaSelectionListeners();
	}

	/**
	 * 
	 */
	protected void addParamListener() {
		model.addPaletteChangeListener(paramListener);
	}

	/**
	 * 
	 */
	protected void removeParamListener() {
		model.removePaletteChangeListener(paramListener);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected ColorFieldModel getColorFieldModel(final String modelId) {
		return (ColorFieldModel) registry.get(modelId);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected ComboBoxModel getComboBoxModel(final String modelId) {
		return (ComboBoxModel) registry.get(modelId);
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
	protected void addColorFieldListeners() {
		ColorFieldModel model = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_START);
		if (model != null) {
			model.addColorChangeListener(colorFieldListeners[0]);
		}
		model = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_END);
		if (model != null) {
			model.addColorChangeListener(colorFieldListeners[1]);
		}
	}

	/**
	 * 
	 */
	protected void addFormulaSelectionListeners() {
		SingleSelectionModel model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_ALPHA);
		if (model != null) {
			model.addChangeListener(formulaSelectionListeners[0]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_RED);
		if (model != null) {
			model.addChangeListener(formulaSelectionListeners[1]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_GREEN);
		if (model != null) {
			model.addChangeListener(formulaSelectionListeners[2]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_BLUE);
		if (model != null) {
			model.addChangeListener(formulaSelectionListeners[3]);
		}
	}

	/**
	 * 
	 */
	protected void removeColorFieldListeners() {
		ColorFieldModel model = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_START);
		if (model != null) {
			model.removeColorChangeListener(colorFieldListeners[0]);
		}
		model = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_END);
		if (model != null) {
			model.removeColorChangeListener(colorFieldListeners[1]);
		}
	}

	/**
	 * 
	 */
	protected void removeFormulaSelectionListeners() {
		SingleSelectionModel model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_ALPHA);
		if (model != null) {
			model.removeChangeListener(formulaSelectionListeners[0]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_RED);
		if (model != null) {
			model.removeChangeListener(formulaSelectionListeners[1]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_GREEN);
		if (model != null) {
			model.removeChangeListener(formulaSelectionListeners[2]);
		}
		model = getSingleSelectionModel(RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_BLUE);
		if (model != null) {
			model.removeChangeListener(formulaSelectionListeners[3]);
		}
	}

	/**
	 * @param paletteParam
	 */
	protected void updateParam(final RenderedPaletteParam paletteParam) {
		ColorFieldModel colorFieldModel = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_START);
		if (colorFieldModel != null) {
			colorFieldModel.setColor(new Color(paletteParam.getColor(0), true), false);
		}
		colorFieldModel = getColorFieldModel(RenderedPaletteParamPanel.COLOR_MODEL_END);
		if (colorFieldModel != null) {
			colorFieldModel.setColor(new Color(paletteParam.getColor(1), true), false);
		}
		ComboBoxModel comboBoxModel = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_ALPHA);
		if (comboBoxModel != null) {
			try {
				comboBoxModel.setSelectedItem(MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(paletteParam.getFormula(0).getExtensionId()));
			}
			catch (final ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}
		comboBoxModel = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_RED);
		if (comboBoxModel != null) {
			try {
				comboBoxModel.setSelectedItem(MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(paletteParam.getFormula(1).getExtensionId()));
			}
			catch (final ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}
		comboBoxModel = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_GREEN);
		if (comboBoxModel != null) {
			try {
				comboBoxModel.setSelectedItem(MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(paletteParam.getFormula(2).getExtensionId()));
			}
			catch (final ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}
		comboBoxModel = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_BLUE);
		if (comboBoxModel != null) {
			try {
				comboBoxModel.setSelectedItem(MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(paletteParam.getFormula(3).getExtensionId()));
			}
			catch (final ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param paletteParam
	 */
	protected void setParam(final RenderedPaletteParam paletteParam) {
		model.setPaletteParam(paletteParam, false);
	}

	/**
	 * @return
	 */
	protected RenderedPaletteParam getParam() {
		return model.getPaletteParam();
	}

	/**
	 * @param model
	 * @return
	 */
	protected JPanel createFormulasPanel(final RenderedPaletteParamModel model) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createFormulaPanel(MandelbrotSwingResources.getInstance().getString("formula.alpha.label"), MandelbrotSwingResources.getInstance().getString("formula.alpha.tooltip"), MandelbrotSwingResources.getInstance().getString("formula.alpha.options.tooltip"), RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_ALPHA, RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_ALPHA));
		panel.add(createFormulaPanel(MandelbrotSwingResources.getInstance().getString("formula.red.label"), MandelbrotSwingResources.getInstance().getString("formula.red.tooltip"), MandelbrotSwingResources.getInstance().getString("formula.red.options.tooltip"), RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_RED, RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_RED));
		panel.add(createFormulaPanel(MandelbrotSwingResources.getInstance().getString("formula.green.label"), MandelbrotSwingResources.getInstance().getString("formula.green.tooltip"), MandelbrotSwingResources.getInstance().getString("formula.green.options.tooltip"), RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_GREEN, RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_GREEN));
		panel.add(createFormulaPanel(MandelbrotSwingResources.getInstance().getString("formula.blue.label"), MandelbrotSwingResources.getInstance().getString("formula.blue.tooltip"), MandelbrotSwingResources.getInstance().getString("formula.blue.options.tooltip"), RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_BLUE, RenderedPaletteParamPanel.FORMULA_SELECTION_MODEL_BLUE));
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		return panel;
	}

	/**
	 * @param comboBoxModelId
	 * @param selectionModelId
	 * @return
	 */
	protected JPanel createFormulaPanel(final String text, final String tooltip, final String optionsTooltip, final String comboBoxModelId, final String selectionModelId) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		final ExtensionComboBoxModel extensionModel = new ExtensionComboBoxModel(MandelbrotRegistry.getInstance().getPaletteRendererFormulaRegistry(), true);
		final JComboBox comboBox = GUIFactory.createComboBox(extensionModel, tooltip);
		comboBox.setRenderer(new ExtensionListCellRenderer());
		final JLabel label = GUIFactory.createLabel(text, SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		label.setMinimumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		label.setMaximumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		panel.add(label);
		panel.add(Box.createHorizontalStrut(8));
		panel.add(comboBox);
		panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
		final FormulaSingleSelectionModel selectionModel = new FormulaSingleSelectionModel(comboBox);
		registry.put(selectionModelId, selectionModel);
		registry.put(comboBoxModelId, extensionModel);
		return panel;
	}

	/**
	 * @param model
	 * @return
	 */
	protected JPanel createColorsPanel(final RenderedPaletteParamModel model) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		final ColorFieldModel[] colorFieldModels = new ColorFieldModel[2];
		colorFieldModels[0] = new DefaultColorFieldModel(new Color(model.getPaletteParam().getColor(0)));
		colorFieldModels[1] = new DefaultColorFieldModel(new Color(model.getPaletteParam().getColor(1)));
		panel.add(createColorFieldPanel(0, MandelbrotSwingResources.getInstance().getString("color.start.label"), MandelbrotSwingResources.getInstance().getString("color.start.tooltip"), new StartColorAction(colorFieldModels[0]), colorFieldModels[0], RenderedPaletteParamPanel.COLOR_MODEL_START));
		panel.add(createColorFieldPanel(1, MandelbrotSwingResources.getInstance().getString("color.end.label"), MandelbrotSwingResources.getInstance().getString("color.end.tooltip"), new EndColorAction(colorFieldModels[1]), colorFieldModels[1], RenderedPaletteParamPanel.COLOR_MODEL_END));
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		return panel;
	}

	/**
	 * @param text
	 * @param model
	 * @param modelId
	 * @return
	 */
	protected JPanel createColorFieldPanel(final int index, final String text, final String tooltip, final Action action, final ColorFieldModel model, final String modelId) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		registry.put(modelId, model);
		final ColorField colorField = new ColorField(model);
		colorField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), BorderFactory.createLineBorder(Color.GRAY)));
		final Dimension size = new Dimension(64, 64);
		colorField.setPreferredSize(size);
		colorField.setMaximumSize(size);
		colorField.setMinimumSize(size);
		final JButton button = GUIFactory.createButton(action, tooltip);
		final JLabel label = GUIFactory.createLabel(text, SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		label.setMinimumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		label.setMaximumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		panel.add(label);
		panel.add(Box.createHorizontalStrut(8));
		panel.add(colorField);
		panel.add(Box.createHorizontalStrut(8));
		panel.add(button);
		colorField.addMouseListener(new ColorFieldListener(index));
		return panel;
	}

	/**
	 * @return
	 */
	protected RenderedPaletteParamModel getModel() {
		return model;
	}

	private void popupColorChooser(final int index, final ColorFieldModel model) throws HeadlessException {
		GUIUtil.executeTask(new RenderedPaletteRunnable(index, model), true);
	}

	private class RenderedPaletteRunnable implements Runnable {
		private final ColorFieldModel model;
		private final int index;

		/**
		 * @param index
		 * @param model
		 */
		public RenderedPaletteRunnable(final int index, final ColorFieldModel model) {
			this.index = index;
			this.model = model;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final Color color = ColorChooser.showColorChooser(RenderedPaletteParamPanel.this, MandelbrotSwingResources.getInstance().getString("palette.chooseColor"), new Color(getParam().getColor(index), true));
			if (color != null) {
				model.setColor(color, false);
			}
		}
	}

	private class RenderedPaletteParamListener implements PaletteChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeListener#paletteChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.PaletteChangeEvent)
		 */
		@Override
		public void paletteChanged(final PaletteChangeEvent e) {
			removeColorFieldListeners();
			removeFormulaSelectionListeners();
			updateParam(getParam());
			addColorFieldListeners();
			addFormulaSelectionListeners();
		}
	}

	private class ColorFieldListener implements ColorChangeListener, MouseListener {
		private final int index;

		/**
		 * @param index
		 */
		public ColorFieldListener(final int index) {
			this.index = index;
		}

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(final MouseEvent e) {
			popupColorChooser(index, ((ColorField) e.getSource()).getModel());
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(final MouseEvent e) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.platform.ui.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.platform.ui.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
			final Color color = ((ColorFieldModel) e.getSource()).getColor();
			removeParamListener();
			final ExtensionReference[] formulas = new ExtensionReference[4];
			formulas[0] = getParam().getFormula(0);
			formulas[1] = getParam().getFormula(1);
			formulas[2] = getParam().getFormula(2);
			formulas[3] = getParam().getFormula(3);
			final int[] colors = new int[2];
			colors[0] = getParam().getColor(0);
			colors[1] = getParam().getColor(1);
			colors[index] = color.getRGB();
			final RenderedPaletteParam param = new RenderedPaletteParam(formulas, colors, getParam().getSize());
			setParam(param);
			addParamListener();
		}
	}

	private class FormulaSelectionListener implements ChangeListener {
		/**
		 * @param e
		 */
		@Override
		@SuppressWarnings("unchecked")
		public void stateChanged(final ChangeEvent e) {
			removeParamListener();
			ComboBoxModel model = null;
			Extension<PaletteRendererFormulaExtensionRuntime> extension = null;
			final ExtensionReference[] formulas = new ExtensionReference[4];
			model = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_ALPHA);
			extension = (Extension<PaletteRendererFormulaExtensionRuntime>) model.getSelectedItem();
			formulas[0] = extension.getExtensionReference();
			model = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_RED);
			extension = (Extension<PaletteRendererFormulaExtensionRuntime>) model.getSelectedItem();
			formulas[1] = extension.getExtensionReference();
			model = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_GREEN);
			extension = (Extension<PaletteRendererFormulaExtensionRuntime>) model.getSelectedItem();
			formulas[2] = extension.getExtensionReference();
			model = getComboBoxModel(RenderedPaletteParamPanel.FORMULA_COMBOBOX_MODEL_BLUE);
			extension = (Extension<PaletteRendererFormulaExtensionRuntime>) model.getSelectedItem();
			formulas[3] = extension.getExtensionReference();
			final int[] colors = new int[2];
			colors[0] = getParam().getColor(0);
			colors[1] = getParam().getColor(1);
			final RenderedPaletteParam param = new RenderedPaletteParam(formulas, colors, getParam().getSize());
			setParam(param);
			addParamListener();
		}
	}

	private class FormulaSingleSelectionModel extends DefaultSingleSelectionModel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private final JComboBox comboBox;

		/**
		 * @param comboBox
		 */
		public FormulaSingleSelectionModel(final JComboBox comboBox) {
			comboBox.addActionListener(this);
			this.comboBox = comboBox;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			final JComboBox comboBox = (JComboBox) e.getSource();
			setSelectedIndex(comboBox.getSelectedIndex());
		}

		/**
		 * @param index
		 */
		public void selectIndex(final int index) {
			comboBox.setSelectedIndex(index);
		}
	}

	private class StartColorAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final ColorFieldModel model;

		/**
		 * @param model
		 */
		public StartColorAction(final ColorFieldModel model) {
			super(MandelbrotSwingResources.getInstance().getString("color.choose.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			popupColorChooser(0, model);
		}
	}

	private class EndColorAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final ColorFieldModel model;

		/**
		 * @param model
		 */
		public EndColorAction(final ColorFieldModel model) {
			super(MandelbrotSwingResources.getInstance().getString("color.choose.button.label"), null);
			this.model = model;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			popupColorChooser(1, model);
		}
	}
}
