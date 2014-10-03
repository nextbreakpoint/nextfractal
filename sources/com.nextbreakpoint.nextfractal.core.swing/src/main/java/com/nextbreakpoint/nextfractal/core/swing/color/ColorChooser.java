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
package com.nextbreakpoint.nextfractal.core.swing.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.swing.CoreSwingResources;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.util.Registry;

/**
 * @author Andrea Medeghini
 */
public class ColorChooser extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final String SPINNER_PREFIX = "spinner";
	protected static final String SPINNER_ALPHA = ColorChooser.SPINNER_PREFIX + ".ALPHA";
	protected static final String SPINNER_RED = ColorChooser.SPINNER_PREFIX + ".RED";
	protected static final String SPINNER_GREEN = ColorChooser.SPINNER_PREFIX + ".GREEN";
	protected static final String SPINNER_BLUE = ColorChooser.SPINNER_PREFIX + ".BLUE";
	protected static final String SPINNER_HUE = ColorChooser.SPINNER_PREFIX + ".HUE";
	protected static final String SPINNER_SATURATION = ColorChooser.SPINNER_PREFIX + ".SATURATION";
	protected static final String SPINNER_BRIGHTNESS = ColorChooser.SPINNER_PREFIX + ".BRIGHTNESS";
	protected static final String SPINNER_MODEL_PREFIX = "spinner.model";
	protected static final String SPINNER_MODEL_ALPHA = ColorChooser.SPINNER_MODEL_PREFIX + ".ALPHA";
	protected static final String SPINNER_MODEL_RED = ColorChooser.SPINNER_MODEL_PREFIX + ".RED";
	protected static final String SPINNER_MODEL_GREEN = ColorChooser.SPINNER_MODEL_PREFIX + ".GREEN";
	protected static final String SPINNER_MODEL_BLUE = ColorChooser.SPINNER_MODEL_PREFIX + ".BLUE";
	protected static final String SPINNER_MODEL_HUE = ColorChooser.SPINNER_MODEL_PREFIX + ".HUE";
	protected static final String SPINNER_MODEL_SATURATION = ColorChooser.SPINNER_MODEL_PREFIX + ".SATURATION";
	protected static final String SPINNER_MODEL_BRIGHTNESS = ColorChooser.SPINNER_MODEL_PREFIX + ".BRIGHTNESS";
	protected static final String COLOR_FIELD_MODEL = "colorField.model";
	protected static final String COLOR_TABLE_MODEL = "colorTable.model";
	protected static final int[] size = new int[] { 8, 16 };
	protected static final Color[][] colors = new Color[ColorChooser.size[0]][ColorChooser.size[1]];
	private final Registry<Object> registry = new Registry<Object>();
	private final ColorFieldListener colorFieldListener = new ColorFieldListener();
	private final ColorTableListener colorTableListener = new ColorTableListener();
	private final RGBSpinnerListener rgbSpinnerListener = new RGBSpinnerListener();
	private final HSBSpinnerListener hsbSpinnerListener = new HSBSpinnerListener();

	/**
	 * 
	 */
	public ColorChooser() {
		setupPanel(this);
	}

	/**
	 * @param color
	 */
	public ColorChooser(final Color color) {
		setupPanel(this);
		setColor(color);
	}

	/**
	 * @param panel
	 */
	protected void setupPanel(final JPanel panel) {
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(CoreSwingResources.getInstance().getString("tab.table.label"), createTablePanel(registry));
		tabbedPane.addTab(CoreSwingResources.getInstance().getString("tab.rgbhsb.label"), createRGBHSBPanel(registry));
		tabbedPane.addTab(CoreSwingResources.getInstance().getString("tab.colors.label"), createColorsPanel(registry));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createFieldPanel(registry));
		panel.add(tabbedPane);
		addColorFieldListener();
		addColorTableListener();
		addRGBSpinnerListener();
		addHSBSpinnerListener();
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
	protected ColorTableModel getColorTableModel(final String modelId) {
		return (ColorTableModel) registry.get(modelId);
	}

	/**
	 * @param modelId
	 * @return
	 */
	protected SpinnerNumberModel getSpinnerNumberModel(final String modelId) {
		return (SpinnerNumberModel) registry.get(modelId);
	}

	/**
	 * @param spinnerId
	 * @return
	 */
	protected JSpinner getSpinner(final String spinnerId) {
		return (JSpinner) registry.get(spinnerId);
	}

	/**
	 * 
	 */
	protected void addColorFieldListener() {
		final ColorFieldModel model = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (model != null) {
			model.addColorChangeListener(colorFieldListener);
		}
	}

	/**
	 * 
	 */
	protected void addColorTableListener() {
		final ColorTableModel model = getColorTableModel(ColorChooser.COLOR_TABLE_MODEL);
		if (model != null) {
			model.addColorChangeListener(colorTableListener);
		}
	}

	/**
	 * 
	 */
	protected void addRGBSpinnerListener() {
		SpinnerNumberModel model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_ALPHA);
		if (model != null) {
			model.addChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_RED);
		if (model != null) {
			model.addChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_GREEN);
		if (model != null) {
			model.addChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BLUE);
		if (model != null) {
			model.addChangeListener(rgbSpinnerListener);
		}
	}

	/**
	 * 
	 */
	protected void addHSBSpinnerListener() {
		SpinnerNumberModel model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_HUE);
		if (model != null) {
			model.addChangeListener(hsbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_SATURATION);
		if (model != null) {
			model.addChangeListener(hsbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BRIGHTNESS);
		if (model != null) {
			model.addChangeListener(hsbSpinnerListener);
		}
	}

	/**
	 * 
	 */
	protected void removeColorFieldListener() {
		final ColorFieldModel model = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (model != null) {
			model.removeColorChangeListener(colorFieldListener);
		}
	}

	/**
	 * 
	 */
	protected void removeColorTableListener() {
		final ColorTableModel model = getColorTableModel(ColorChooser.COLOR_TABLE_MODEL);
		if (model != null) {
			model.removeColorChangeListener(colorTableListener);
		}
	}

	/**
	 * 
	 */
	protected void removeRGBSpinnerListener() {
		SpinnerNumberModel model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_ALPHA);
		if (model != null) {
			model.removeChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_RED);
		if (model != null) {
			model.removeChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_GREEN);
		if (model != null) {
			model.removeChangeListener(rgbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BLUE);
		if (model != null) {
			model.removeChangeListener(rgbSpinnerListener);
		}
	}

	/**
	 * 
	 */
	protected void removeHSBSpinnerListener() {
		SpinnerNumberModel model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_HUE);
		if (model != null) {
			model.removeChangeListener(hsbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_SATURATION);
		if (model != null) {
			model.removeChangeListener(hsbSpinnerListener);
		}
		model = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BRIGHTNESS);
		if (model != null) {
			model.removeChangeListener(hsbSpinnerListener);
		}
	}

	/**
	 * @return
	 */
	protected Color getColor() {
		final ColorFieldModel model = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (model != null) {
			return model.getColor();
		}
		return null;
	}

	/**
	 * @param color
	 */
	protected void setColor(final Color color) {
		final ColorFieldModel model = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (model != null) {
			model.setColor(color, false);
		}
	}

	/**
	 * @return
	 */
	protected Color updateRGB() {
		final ColorFieldModel colorFieldModel = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (colorFieldModel != null) {
			final int[] rgb = new int[4];
			SpinnerNumberModel spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_ALPHA);
			if (spinnerModel != null) {
				rgb[0] = spinnerModel.getNumber().intValue();
			}
			JSpinner spinner = getSpinner(ColorChooser.SPINNER_ALPHA);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_RED);
			if (spinnerModel != null) {
				rgb[1] = spinnerModel.getNumber().intValue();
			}
			spinner = getSpinner(ColorChooser.SPINNER_RED);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_GREEN);
			if (spinnerModel != null) {
				rgb[2] = spinnerModel.getNumber().intValue();
			}
			spinner = getSpinner(ColorChooser.SPINNER_GREEN);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BLUE);
			if (spinnerModel != null) {
				rgb[3] = spinnerModel.getNumber().intValue();
			}
			spinner = getSpinner(ColorChooser.SPINNER_BLUE);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			colorFieldModel.setColor(new Color(rgb[1], rgb[2], rgb[3], rgb[0]), false);
			return colorFieldModel.getColor();
		}
		return Color.BLACK;
	}

	/**
	 * @return
	 */
	protected Color updateHSB() {
		final ColorFieldModel colorFieldModel = getColorFieldModel(ColorChooser.COLOR_FIELD_MODEL);
		if (colorFieldModel != null) {
			final float[] hsb = new float[3];
			SpinnerNumberModel spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_HUE);
			if (spinnerModel != null) {
				hsb[0] = spinnerModel.getNumber().intValue() / 255f;
			}
			JSpinner spinner = getSpinner(ColorChooser.SPINNER_HUE);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_SATURATION);
			if (spinnerModel != null) {
				hsb[1] = spinnerModel.getNumber().intValue() / 255f;
			}
			spinner = getSpinner(ColorChooser.SPINNER_SATURATION);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BRIGHTNESS);
			if (spinnerModel != null) {
				hsb[2] = spinnerModel.getNumber().intValue() / 255f;
			}
			spinner = getSpinner(ColorChooser.SPINNER_BRIGHTNESS);
			if (spinner != null) {
				spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
			}
			colorFieldModel.setColor(new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])), false);
			return colorFieldModel.getColor();
		}
		return Color.BLACK;
	}

	/**
	 * @param color
	 */
	protected void setRGB(final Color color) {
		SpinnerNumberModel spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_ALPHA);
		if (spinnerModel != null) {
			spinnerModel.setValue(color.getAlpha());
		}
		JSpinner spinner = getSpinner(ColorChooser.SPINNER_ALPHA);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
		spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_RED);
		if (spinnerModel != null) {
			spinnerModel.setValue(color.getRed());
		}
		spinner = getSpinner(ColorChooser.SPINNER_RED);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
		spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_GREEN);
		if (spinnerModel != null) {
			spinnerModel.setValue(color.getGreen());
		}
		spinner = getSpinner(ColorChooser.SPINNER_GREEN);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
		spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BLUE);
		if (spinnerModel != null) {
			spinnerModel.setValue(color.getBlue());
		}
		spinner = getSpinner(ColorChooser.SPINNER_BLUE);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
	}

	/**
	 * @param color
	 */
	protected void setHSB(final Color color) {
		final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
		SpinnerNumberModel spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_HUE);
		if (spinnerModel != null) {
			spinnerModel.setValue(Math.round(hsb[0] * 255f));
		}
		JSpinner spinner = getSpinner(ColorChooser.SPINNER_HUE);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
		spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_SATURATION);
		if (spinnerModel != null) {
			spinnerModel.setValue(Math.round(hsb[1] * 255f));
		}
		spinner = getSpinner(ColorChooser.SPINNER_SATURATION);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
		spinnerModel = getSpinnerNumberModel(ColorChooser.SPINNER_MODEL_BRIGHTNESS);
		if (spinnerModel != null) {
			spinnerModel.setValue(Math.round(hsb[2] * 255f));
		}
		spinner = getSpinner(ColorChooser.SPINNER_BRIGHTNESS);
		if (spinner != null) {
			spinner.setToolTipText(createSpinnerTooltip(spinnerModel));
		}
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createRGBHSBPanel(final Registry<Object> registry) {
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(createPanelRGB(registry));
		panel.add(createPanelHSB(registry));
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createPanelRGB(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_ALPHA, ColorChooser.SPINNER_MODEL_ALPHA, CoreSwingResources.getInstance().getString("alpha.label")));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_RED, ColorChooser.SPINNER_MODEL_RED, CoreSwingResources.getInstance().getString("red.label")));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_GREEN, ColorChooser.SPINNER_MODEL_GREEN, CoreSwingResources.getInstance().getString("green.label")));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_BLUE, ColorChooser.SPINNER_MODEL_BLUE, CoreSwingResources.getInstance().getString("blue.label")));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createPanelHSB(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_HUE, ColorChooser.SPINNER_MODEL_HUE, CoreSwingResources.getInstance().getString("hue.label")));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_SATURATION, ColorChooser.SPINNER_MODEL_SATURATION, CoreSwingResources.getInstance().getString("saturation.label")));
		panel.add(createSpinnerPanel(registry, ColorChooser.SPINNER_BRIGHTNESS, ColorChooser.SPINNER_MODEL_BRIGHTNESS, CoreSwingResources.getInstance().getString("brightness.label")));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createTablePanel(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new BorderLayout());
		final ColorTableModel model = new DefaultColorTableModel(256, 128, false);
		final ColorTable colorTable = new ColorTable(model);
		// colorTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		colorTable.setShowColorEnabled(false);
		panel.add(colorTable);
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		final Dimension size = new Dimension(256, 136);
		colorTable.setPreferredSize(size);
		colorTable.setMinimumSize(size);
		colorTable.setMaximumSize(size);
		registry.put(ColorChooser.COLOR_TABLE_MODEL, model);
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createFieldPanel(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new GridLayout(1, 1));
		final ColorFieldModel model = new DefaultColorFieldModel();
		final ColorField colorField = new ColorField(model);
		colorField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(colorField);
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		final Dimension size = new Dimension(256, 128);
		colorField.setPreferredSize(size);
		colorField.setMinimumSize(size);
		colorField.setMaximumSize(size);
		registry.put(ColorChooser.COLOR_FIELD_MODEL, model);
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @return
	 */
	protected JPanel createColorsPanel(final Registry<Object> registry) {
		final JPanel panel = new JPanel(new GridLayout(ColorChooser.size[0], ColorChooser.size[1]));
		for (int i = 0; i < ColorChooser.size[0]; i++) {
			for (int j = 0; j < ColorChooser.size[1]; j++) {
				if (ColorChooser.colors[i][j] == null) {
					ColorChooser.colors[i][j] = Color.WHITE;
				}
				final ColorField colorField = new ColorField(ColorChooser.colors[i][j]);
				colorField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.GRAY)));
				colorField.getModel().addColorChangeListener(new ColorListener(i, j));
				panel.add(colorField);
			}
		}
		// final Dimension size = new Dimension(128, 128);
		// panel.setPreferredSize(size);
		// panel.setMinimumSize(size);
		// panel.setMaximumSize(size);
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param registry
	 * @param modelId
	 * @param text
	 * @return
	 */
	protected JPanel createSpinnerPanel(final Registry<Object> registry, final String spinnerId, final String modelId, final String text) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		final JLabel label = GUIFactory.createLabel(text, SwingConstants.CENTER);
		final Dimension size = new Dimension(80, 20);
		label.setPreferredSize(size);
		label.setMinimumSize(size);
		label.setMaximumSize(size);
		final SpinnerNumberModel model = new SpinnerNumberModel(255, 0, 255, 1);
		final JSpinner spinner = GUIFactory.createSpinner(model, null);
		panel.add(label);
		panel.add(spinner);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		spinner.setToolTipText(createSpinnerTooltip(model));
		registry.put(spinnerId, spinner);
		registry.put(modelId, model);
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * @param model
	 * @return
	 */
	protected String createSpinnerTooltip(final SpinnerNumberModel model) {
		return "Value " + model.getNumber().intValue() + " [ #" + Integer.toHexString(model.getNumber().intValue()).toUpperCase() + " ]";
	}

	private class ColorFieldListener implements ColorChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
			removeRGBSpinnerListener();
			removeHSBSpinnerListener();
			final Color color = ((ColorFieldModel) e.getSource()).getColor();
			setRGB(color);
			setHSB(color);
			addRGBSpinnerListener();
			addHSBSpinnerListener();
		}
	}

	private class ColorTableListener implements ColorChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
			final Color color = ((ColorTableModel) e.getSource()).getColor();
			setColor(color);
		}
	}

	private class RGBSpinnerListener implements ChangeListener {
		/**
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(final ChangeEvent e) {
			removeColorFieldListener();
			removeHSBSpinnerListener();
			final Color color = updateRGB();
			setHSB(color);
			addColorFieldListener();
			addHSBSpinnerListener();
		}
	}

	private class HSBSpinnerListener implements ChangeListener {
		/**
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(final ChangeEvent e) {
			removeColorFieldListener();
			removeRGBSpinnerListener();
			final Color color = updateHSB();
			setRGB(color);
			addColorFieldListener();
			addRGBSpinnerListener();
		}
	}

	private class ColorListener implements ColorChangeListener {
		private final int x;
		private final int y;

		/**
		 * @param x
		 * @param y
		 */
		public ColorListener(final int x, final int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeListener#colorChanged(com.nextbreakpoint.nextfractal.core.swing.color.ColorChangeEvent)
		 */
		@Override
		public void colorChanged(final ColorChangeEvent e) {
			final Color color = ((ColorFieldModel) e.getSource()).getColor();
			ColorChooser.colors[x][y] = color;
		}
	}

	/**
	 * @param c
	 * @param title
	 * @param color
	 * @return
	 */
	public static Color showColorChooser(final JComponent c, final String title, final Color color) {
		final ColorChooserDialog dialog = ColorChooser.createColorChooserDialog(c, title, color);
		dialog.setVisible(true);
		return dialog.getColor();
	}

	/**
	 * @param c
	 * @param title
	 * @param color
	 * @return
	 */
	protected static ColorChooserDialog createColorChooserDialog(final JComponent c, final String title, final Color color) {
		return new ColorChooserDialog(c, title, color);
	}
}
