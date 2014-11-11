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
package com.nextbreakpoint.nextfractal.twister.ui.swing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.swing.color.ColorChangeEvent;
import com.nextbreakpoint.nextfractal.core.ui.swing.color.ColorChangeListener;
import com.nextbreakpoint.nextfractal.core.ui.swing.color.ColorChooser;
import com.nextbreakpoint.nextfractal.core.ui.swing.color.ColorField;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.layerFilter.LayerFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.layerFilter.LayerFilterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.java2D.Java2DRenderFactory;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.DefaultViewRuntime;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfigPanel extends ViewPanel {
	private static final Logger logger = Logger.getLogger(TwisterConfigPanel.class.getName());
	private static final long serialVersionUID = 1L;
	private final ViewContext viewContext;
	private final RenderContext context;
	private final NodeSession session;
	private final TwisterConfig config;
	private final ConfigPanel configPanel;
	private final Color oddColor;
	private final Color evenColor;
	private RepaintTask refreshPreview;

	/**
	 * @param config
	 * @param viewContext
	 * @param context
	 * @param session
	 */
	public TwisterConfigPanel(final TwisterConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		this.viewContext = viewContext;
		this.context = context;
		this.session = session;
		this.config = config;
		refreshPreview = new RepaintTask();
		setLayout(new BorderLayout());
		oddColor = getBackground().brighter();
		evenColor = getBackground().brighter();
		configPanel = new ConfigPanel(config);
		add(configPanel, BorderLayout.CENTER);
		refreshPreview.start();
	}

	@Override
	public void dispose() {
		config.getContext().setParentConfigContext(null);
		if (refreshPreview != null) {
			refreshPreview.stop();
		}
		configPanel.dispose();
	}

	private static JCheckBox createIconCheckBox(final String key, final String iconKey, final int width, final int height) {
		final JCheckBox checkbox = GUIFactory.createCheckBox((String) null, TwisterSwingResources.getInstance().getString("tooltip." + key));
		try {
			checkbox.setIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			checkbox.setSelectedIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-selected-icon.png"))));
		}
		catch (final Exception e) {
			logger.warning("key = " + key + ", iconKey = " + iconKey);
			e.printStackTrace();
		}
		checkbox.setOpaque(false);
		checkbox.setBorderPainted(false);
		checkbox.setPreferredSize(new Dimension(width, height));
		checkbox.setMinimumSize(new Dimension(width, height));
		checkbox.setMaximumSize(new Dimension(width, height));
		return checkbox;
	}

	private static JButton createIconButton(final String key, final String iconKey, final int width, final int height) {
		final JButton button = GUIFactory.createButton((String) null, TwisterSwingResources.getInstance().getString("tooltip." + key));
		try {
			button.setIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			button.setPressedIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-pressed-icon.png"))));
		}
		catch (final Exception e) {
			logger.warning("key = " + key + ", iconKey = " + iconKey);
			e.printStackTrace();
		}
		button.setOpaque(false);
		button.setPreferredSize(new Dimension(width, height));
		button.setMinimumSize(new Dimension(width, height));
		button.setMaximumSize(new Dimension(width, height));
		return button;
	}

	// private static JCheckBox createSelectionCheckBox() {
	// final JCheckBox checkbox = GUIFactory.createSmallCheckBox((String) null, (String) null);
	// checkbox.setOpaque(false);
	// checkbox.setPreferredSize(new Dimension(20, 20));
	// checkbox.setMinimumSize(new Dimension(20, 20));
	// checkbox.setMaximumSize(new Dimension(20, 20));
	// return checkbox;
	// }
	private static JCheckBox createCheckBox() {
		final JCheckBox checkbox = GUIFactory.createSmallCheckBox((String) null, (String) null);
		checkbox.setOpaque(false);
		return checkbox;
	}

	// private static JCheckBox createTextCheckBox(final String key, final int width, final int height) {
	// final JCheckBox checkbox = GUIFactory.createCheckBox(TwisterSwingResources.getInstance().getString("label." + key), TwisterSwingResources.getInstance().getString("tooltip." + key));
	// // final FontMetrics fm = checkbox.getFontMetrics(checkbox.getFont());
	// // int width = fm.stringWidth(checkbox.getText()) + 20;
	// checkbox.setPreferredSize(new Dimension(width, height));
	// checkbox.setMinimumSize(new Dimension(width, height));
	// checkbox.setMaximumSize(new Dimension(width, height));
	// checkbox.setOpaque(false);
	// return checkbox;
	// }
	// private static JButton createTextButton(final int width, final int height) {
	// final JButton button = GUIFactory.createSmallButton((String) null, (String) null);
	// button.setPreferredSize(new Dimension(width, height));
	// button.setMinimumSize(new Dimension(width, height));
	// button.setMaximumSize(new Dimension(width, height));
	// button.setOpaque(false);
	// return button;
	// }
	private static JButton createTextButton(final String key, final int width, final int height) {
		final JButton button = GUIFactory.createSmallButton(TwisterSwingResources.getInstance().getString("label." + key), TwisterSwingResources.getInstance().getString("tooltip." + key));
		// final FontMetrics fm = button.getFontMetrics(button.getFont());
		// int width = fm.stringWidth(button.getText());
		button.setPreferredSize(new Dimension(width, height));
		button.setMinimumSize(new Dimension(width, height));
		button.setMaximumSize(new Dimension(width, height));
		button.setOpaque(false);
		return button;
	}

	private static JButton createIconTextButton(final String key, final String iconKey, final int width, final int height) {
		final JButton button = GUIFactory.createSmallButton(TwisterSwingResources.getInstance().getString("label." + key), TwisterSwingResources.getInstance().getString("tooltip." + key));
		// final FontMetrics fm = button.getFontMetrics(button.getFont());
		// int width = fm.stringWidth(button.getText());
		try {
			button.setIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			// button.setPressedIcon(new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-pressed-icon.png"))));
		}
		catch (final Exception e) {
			logger.warning("key = " + key + ", iconKey = " + iconKey);
			e.printStackTrace();
		}
		button.setPreferredSize(new Dimension(width, height));
		button.setMinimumSize(new Dimension(width, height));
		button.setMaximumSize(new Dimension(width, height));
		button.setOpaque(false);
		return button;
	}

	private static JSpinner createSpinner(final int min, final int max, final int step) {
		final JSpinner spinner = GUIFactory.createSpinner(new SpinnerNumberModel(0, min, max, step), (String) null);
		// spinner.setPreferredSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		spinner.setMaximumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		spinner.setMinimumSize(new Dimension(60, GUIFactory.DEFAULT_HEIGHT));
		// if (!"Mac OS X".equals(System.getProperty("os.name")) || !UIManager.getLookAndFeel().isNativeLookAndFeel()) {
		// spinner.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		// }
		return spinner;
	}

	private static JComboBox createExtensionComboBox(final ComboBoxModel model, final int width, final int height) {
		final JComboBox extensionComboBox = GUIFactory.createSmallComboBox(model, (String) null);
		extensionComboBox.setRenderer(new ExtensionListCellRenderer());
		extensionComboBox.setPreferredSize(new Dimension(width, height));
		extensionComboBox.setMaximumSize(new Dimension(width, height));
		extensionComboBox.setMinimumSize(new Dimension(width, height));
		extensionComboBox.setOpaque(false);
		return extensionComboBox;
	}

	private static JLabel createTextLabel(final String key, final int alignment, final int width, final int height) {
		final JLabel label = GUIFactory.createSmallLabel(TwisterSwingResources.getInstance().getString("label." + key), SwingConstants.LEFT);
		label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		label.setPreferredSize(new Dimension(width, height));
		label.setMinimumSize(new Dimension(width, height));
		label.setMaximumSize(new Dimension(width, height));
		return label;
	}

	// private static JLabel createLabel(final String text) {
	// final JLabel label = GUIFactory.createSmallLabel(text, SwingConstants.LEFT);
	// label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
	// return label;
	// }
	private static JTextField createTextField(final String text, final int width, final int height) {
		final JTextField textfield = GUIFactory.createTextField(text, null);
		textfield.setPreferredSize(new Dimension(width, height));
		textfield.setMinimumSize(new Dimension(width, height));
		textfield.setMaximumSize(new Dimension(width, height));
		// if (!"Mac OS X".equals(System.getProperty("os.name")) || !UIManager.getLookAndFeel().isNativeLookAndFeel()) {
		// textfield.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		// }
		return textfield;
	}

	private static JPanel createPanel(final LayoutManager layoutManager, final boolean opaque) {
		final JPanel panel = new JPanel(layoutManager);
		panel.setOpaque(opaque);
		return panel;
	}

	private static Box createHorizontalBox(final boolean opaque) {
		final Box box = Box.createHorizontalBox();
		box.setOpaque(opaque);
		return box;
	}

	// private static Box createVerticalBox(final boolean opaque) {
	// final Box box = Box.createVerticalBox();
	// box.setOpaque(opaque);
	// return box;
	// }
	private static Component createSpace() {
		final Component box = Box.createHorizontalStrut(4);
		return box;
	}

	// private static JSlider createSlider(final String key, final int min, final int max, final int value, final int spacing, final String suffix) {
	// final JSlider slider = new JSlider(min, max);
	// final int mid = (min + max) / 2;
	// final Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
	// labels.put(new Integer(min), GUIFactory.createLabel(String.valueOf(min) + suffix));
	// labels.put(new Integer(mid), GUIFactory.createLabel(String.valueOf(mid) + suffix));
	// labels.put(new Integer(max), GUIFactory.createLabel(String.valueOf(max) + suffix));
	// slider.setLabelTable(labels);
	// slider.setMajorTickSpacing(spacing);
	// slider.setPaintTicks(true);
	// slider.setPaintTrack(true);
	// slider.setPaintLabels(true);
	// slider.setValue(value);
	// slider.setToolTipText(MandelbrotSwingResources.getInstance().getString("tooltip." + key) + " " + slider.getValue() + suffix);
	// slider.addChangeListener(new SliderChangeListener(slider, key));
	// slider.setOpaque(false);
	// slider.setFont(SMALL_FONT);
	// return slider;
	// }
	private class ConfigPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final TwisterConfig config;
		private EffectPanel effectPanel;
		private final ElementChangeListener filtersListener;
		private final ElementChangeListener layersListener;
		private final ElementChangeListener frameListener;
		private final ElementChangeListener effectListener;
		private final ElementChangeListener backgroundListener;
		private final JPanel filtersPanel;
		private final JPanel layersPanel;
		private final JPanel layersPanel2;
		private final JPanel filtersPanel2;

		/**
		 * @param config
		 */
		public ConfigPanel(final TwisterConfig config) {
			this.config = config;
			final JButton selectGroupButton = createIconButton("selectGroups", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton appendGroupButton = createIconButton("appendGroup", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton removeGroupButton = createIconButton("removeGroups", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveUpGroupButton = createIconButton("moveUpGroup", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveDownGroupButton = createIconButton("moveDownGroup", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton cloneGroupButton = createIconButton("cloneGroup", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			// final JButton insertGroupAfterButton = createIconButton("insertGroupAfter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			// final JButton insertGroupBeforeButton = createIconButton("insertGroupBefore", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton selectFilterButton = createIconButton("selectFilters", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton appendFilterButton = createIconButton("appendFilter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton removeFilterButton = createIconButton("removeFilters", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveUpFilterButton = createIconButton("moveUpFilter", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveDownFilterButton = createIconButton("moveDownFilter", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton cloneFilterButton = createIconButton("cloneFilter", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			// final JButton insertFilterAfterButton = createIconButton("insertFilterAfter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			// final JButton insertFilterBeforeButton = createIconButton("insertFilterBefore", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton editLayersButton = createTextButton("editLayers", 120, GUIFactory.DEFAULT_HEIGHT);
			final JButton editFiltersButton = createTextButton("editFilters", 120, GUIFactory.DEFAULT_HEIGHT);
			final JButton editEffectsButton = createTextButton("editEffects", 120, GUIFactory.DEFAULT_HEIGHT);
			final JButton editBackgroundButton = createTextButton("editBackground", 120, GUIFactory.DEFAULT_HEIGHT);
			final JButton colorChooseButton = createIconTextButton("editColor", "edit", 110, GUIFactory.DEFAULT_HEIGHT);
			layersPanel = createPanel(new StackLayout(), false);
			filtersPanel = createPanel(new StackLayout(), false);
			effectPanel = new EffectPanel(config, config.getEffectConfigElement());
			effectPanel.setName(createEffectPanelName(config.getEffectConfigElement()));
			effectPanel.setOpaque(true);
			final JLabel colorLabel = createTextLabel("background", SwingConstants.LEFT, 150, GUIFactory.DEFAULT_HEIGHT);
			colorLabel.setFont(GUIFactory.NORMAL_FONT);
			colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			final ColorField colorField = new ColorField();
			colorField.setPreferredSize(new Dimension(GUIFactory.DEFAULT_HEIGHT * 3, GUIFactory.DEFAULT_HEIGHT * 3));
			colorField.setMinimumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT * 3, GUIFactory.DEFAULT_HEIGHT * 3));
			colorField.setMaximumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT * 3, GUIFactory.DEFAULT_HEIGHT * 3));
			colorField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			colorField.setOpaque(false);
			final Box colorPanel = createHorizontalBox(true);
			colorPanel.setName(TwisterSwingResources.getInstance().getString("name.background"));
			colorPanel.add(Box.createHorizontalGlue());
			colorPanel.add(colorLabel);
			colorPanel.add(colorField);
			colorPanel.add(Box.createHorizontalStrut(16));
			colorPanel.add(colorChooseButton);
			colorPanel.add(Box.createHorizontalGlue());
			colorPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
			colorField.setColor(new Color(config.getBackground().getARGB(), true));
			final Box layersPanel4 = createHorizontalBox(false);
			layersPanel4.add(Box.createHorizontalGlue());
			layersPanel4.add(selectGroupButton);
			layersPanel4.add(createSpace());
			layersPanel4.add(appendGroupButton);
			layersPanel4.add(createSpace());
			layersPanel4.add(removeGroupButton);
			layersPanel4.add(createSpace());
			layersPanel4.add(moveUpGroupButton);
			layersPanel4.add(createSpace());
			layersPanel4.add(moveDownGroupButton);
			layersPanel4.add(createSpace());
			layersPanel4.add(cloneGroupButton);
			// layersPanel4.add(insertGroupAfterButton);
			// layersPanel4.add(insertGroupBeforeButton);
			layersPanel4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			final JLabel layersLabel = createTextLabel("noGroupLayer", SwingConstants.LEFT, 120, GUIFactory.DEFAULT_HEIGHT);
			layersLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			final Box layersPanel3 = createHorizontalBox(false);
			layersPanel3.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			layersPanel3.add(layersLabel);
			layersPanel3.add(Box.createHorizontalGlue());
			layersPanel2 = createPanel(new BorderLayout(), true);
			layersPanel2.setName(createLayersPanelName());
			layersPanel2.add(layersPanel4, BorderLayout.NORTH);
			layersPanel2.add(layersPanel, BorderLayout.CENTER);
			layersPanel2.add(layersPanel3, BorderLayout.SOUTH);
			layersPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			final Box filtersPanel4 = createHorizontalBox(false);
			filtersPanel4.add(Box.createHorizontalGlue());
			filtersPanel4.add(selectFilterButton);
			filtersPanel4.add(createSpace());
			filtersPanel4.add(appendFilterButton);
			filtersPanel4.add(createSpace());
			filtersPanel4.add(removeFilterButton);
			filtersPanel4.add(createSpace());
			filtersPanel4.add(moveUpFilterButton);
			filtersPanel4.add(createSpace());
			filtersPanel4.add(moveDownFilterButton);
			filtersPanel4.add(createSpace());
			filtersPanel4.add(cloneFilterButton);
			// filtersPanel4.add(insertFilterAfterButton);
			// filtersPanel4.add(insertFilterBeforeButton);
			filtersPanel4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			final JLabel filtersLabel = createTextLabel("noFrameFilter", SwingConstants.LEFT, 120, GUIFactory.DEFAULT_HEIGHT);
			filtersLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			final Box filtersPanel3 = createHorizontalBox(false);
			filtersPanel3.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			filtersPanel3.add(filtersLabel);
			filtersPanel3.add(Box.createHorizontalGlue());
			filtersPanel2 = createPanel(new BorderLayout(), true);
			filtersPanel2.setName(createFiltersPanelName());
			filtersPanel2.add(filtersPanel4, BorderLayout.NORTH);
			filtersPanel2.add(filtersPanel, BorderLayout.CENTER);
			filtersPanel2.add(filtersPanel3, BorderLayout.SOUTH);
			filtersPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			// JPanel b1 = createPanel(new FlowLayout(FlowLayout.RIGHT), false);
			// b1.setPreferredSize(new Dimension(200, 40));
			// b1.add(editLayersButton);
			// JPanel p1 = createPanel(new FlowLayout(FlowLayout.LEFT), false);
			// p1.add(b1);
			// JTextArea a1 = new JTextArea(TwisterSwingResources.getInstance().getString("text.editLayers"));
			// a1.setOpaque(false);
			// a1.setEditable(false);
			// a1.setFont(SMALL_FONT);
			// p1.add(a1);
			// JPanel b2 = createPanel(new FlowLayout(FlowLayout.RIGHT), false);
			// b2.setPreferredSize(new Dimension(200, 40));
			// b2.add(editFiltersButton);
			// JPanel p2 = createPanel(new FlowLayout(FlowLayout.LEFT), false);
			// p2.add(b2);
			// JTextArea a2 = new JTextArea(TwisterSwingResources.getInstance().getString("text.editFilters"));
			// a2.setOpaque(false);
			// a2.setEditable(false);
			// a2.setFont(SMALL_FONT);
			// p2.add(a2);
			// JPanel b3 = createPanel(new FlowLayout(FlowLayout.RIGHT), false);
			// b3.setPreferredSize(new Dimension(200, 40));
			// b3.add(editEffectsButton);
			// JPanel p3 = createPanel(new FlowLayout(FlowLayout.LEFT), false);
			// p3.add(b3);
			// JTextArea a3 = new JTextArea(TwisterSwingResources.getInstance().getString("text.editEffects"));
			// a3.setOpaque(false);
			// a3.setEditable(false);
			// a3.setFont(SMALL_FONT);
			// p3.add(a3);
			// JPanel b4 = createPanel(new FlowLayout(FlowLayout.RIGHT), false);
			// b4.setPreferredSize(new Dimension(200, 40));
			// b4.add(editBackgroundButton);
			// JPanel p4 = createPanel(new FlowLayout(FlowLayout.LEFT), false);
			// p4.add(b4);
			// JTextArea a4 = new JTextArea(TwisterSwingResources.getInstance().getString("text.editBackground"));
			// a4.setOpaque(false);
			// a4.setEditable(false);
			// a4.setFont(SMALL_FONT);
			// p4.add(a4);
			// final JPanel subpanelContainer = createPanel(new GridLayout(4, 1), false);
			// subpanelContainer.add(p1);
			// subpanelContainer.add(p2);
			// subpanelContainer.add(p3);
			// subpanelContainer.add(p4);
			// setLayout(new BorderLayout());
			// add(subpanelContainer, BorderLayout.CENTER);
			// subpanelContainer.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
			final Box subpanelContainer = createHorizontalBox(false);
			subpanelContainer.add(Box.createHorizontalGlue());
			subpanelContainer.add(editLayersButton);
			subpanelContainer.add(Box.createHorizontalStrut(8));
			subpanelContainer.add(editFiltersButton);
			subpanelContainer.add(Box.createHorizontalStrut(8));
			subpanelContainer.add(editEffectsButton);
			subpanelContainer.add(Box.createHorizontalStrut(8));
			subpanelContainer.add(editBackgroundButton);
			subpanelContainer.add(Box.createHorizontalGlue());
			setLayout(new BorderLayout());
			add(subpanelContainer, BorderLayout.CENTER);
			setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
			layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
			filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
			for (int i = 0; i < config.getFrameConfigElement().getLayerConfigElementCount(); i++) {
				final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
				final GroupLayerPanel groupLayerPanel = new GroupLayerPanel(config.getFrameConfigElement(), groupLayerElement);
				layersPanel.add(groupLayerPanel);
			}
			for (int i = 0; i < config.getFrameConfigElement().getFilterConfigElementCount(); i++) {
				final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
				final FrameFilterPanel frameFilterPanel = new FrameFilterPanel(config.getFrameConfigElement(), frameFilterElement);
				filtersPanel.add(frameFilterPanel);
			}
			for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
				filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
			}
			appendGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getLayerConfigElementCount() - 1; i >= 0; i--) {
							final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
							if (groupLayerElement.getUserData() != null) {
								config.getFrameConfigElement().insertLayerConfigElementBefore(i, new GroupLayerConfigElement());
								return;
							}
						}
						config.getFrameConfigElement().appendLayerConfigElement(new GroupLayerConfigElement());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			removeGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getLayerConfigElementCount() - 1; i >= 0; i--) {
							final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
							if (groupLayerElement.getUserData() != null) {
								config.getFrameConfigElement().removeLayerConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			// insertGroupAfterButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = 0; i < config.getFrameConfigElement().getLayerConfigElementCount(); i++) {
			// GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
			// if (groupLayerElement.getUserData() != null) {
			// config.getFrameConfigElement().insertLayerConfigElementAfter(i, new GroupLayerConfigElement());
			// }
			// }
			// }
			// });
			// insertGroupBeforeButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = config.getFrameConfigElement().getLayerConfigElementCount() - 1; i >= 0; i--) {
			// GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
			// if (groupLayerElement.getUserData() != null) {
			// config.getFrameConfigElement().insertLayerConfigElementBefore(i, new GroupLayerConfigElement());
			// }
			// }
			// }
			// });
			appendFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getFilterConfigElementCount() - 1; i >= 0; i--) {
							final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
							if (frameFilterElement.getUserData() != null) {
								config.getFrameConfigElement().insertFilterConfigElementBefore(i, new FrameFilterConfigElement());
								return;
							}
						}
						config.getFrameConfigElement().appendFilterConfigElement(new FrameFilterConfigElement());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			removeFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getFilterConfigElementCount() - 1; i >= 0; i--) {
							final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
							if (frameFilterElement.getUserData() != null) {
								config.getFrameConfigElement().removeFilterConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			// insertFilterAfterButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = 0; i < config.getFrameConfigElement().getFilterConfigElementCount(); i++) {
			// FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
			// if (frameFilterElement.getUserData() != null) {
			// config.getFrameConfigElement().insertFilterConfigElementAfter(i, new FrameFilterConfigElement());
			// }
			// }
			// }
			// });
			// insertFilterBeforeButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = config.getFrameConfigElement().getFilterConfigElementCount() - 1; i >= 0; i--) {
			// FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
			// if (frameFilterElement.getUserData() != null) {
			// config.getFrameConfigElement().insertFilterConfigElementBefore(i, new FrameFilterConfigElement());
			// }
			// }
			// }
			// });
			selectFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					boolean allSelected = true;
					for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
						if (!((FrameFilterPanel) filtersPanel.getComponent(i)).isSelected()) {
							allSelected = false;
						}
					}
					if (allSelected) {
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							((FrameFilterPanel) filtersPanel.getComponent(i)).setSelected(false);
						}
					}
					else {
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							((FrameFilterPanel) filtersPanel.getComponent(i)).setSelected(true);
						}
					}
				}
			});
			selectGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					boolean allSelected = true;
					for (int i = 0; i < layersPanel.getComponentCount(); i++) {
						if (!((GroupLayerPanel) layersPanel.getComponent(i)).isSelected()) {
							allSelected = false;
						}
					}
					if (allSelected) {
						for (int i = 0; i < layersPanel.getComponentCount(); i++) {
							((GroupLayerPanel) layersPanel.getComponent(i)).setSelected(false);
						}
					}
					else {
						for (int i = 0; i < layersPanel.getComponentCount(); i++) {
							((GroupLayerPanel) layersPanel.getComponent(i)).setSelected(true);
						}
					}
				}
			});
			moveUpGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = 0; i < config.getFrameConfigElement().getLayerConfigElementCount(); i++) {
							final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
							if (groupLayerElement.getUserData() != null) {
								config.getFrameConfigElement().moveUpLayerConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			moveDownGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getLayerConfigElementCount() - 1; i >= 0; i--) {
							final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
							if (groupLayerElement.getUserData() != null) {
								config.getFrameConfigElement().moveDownLayerConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			cloneGroupButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getLayerConfigElementCount() - 1; i >= 0; i--) {
							final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
							if (groupLayerElement.getUserData() != null) {
								config.getFrameConfigElement().insertLayerConfigElementAfter(i, groupLayerElement.clone());
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			moveUpFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = 0; i < config.getFrameConfigElement().getFilterConfigElementCount(); i++) {
							final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
							if (frameFilterElement.getUserData() != null) {
								config.getFrameConfigElement().moveUpFilterConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			moveDownFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getFilterConfigElementCount() - 1; i >= 0; i--) {
							final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
							if (frameFilterElement.getUserData() != null) {
								config.getFrameConfigElement().moveDownFilterConfigElement(i);
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			cloneFilterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getFrameConfigElement().getContext().updateTimestamp();
						for (int i = config.getFrameConfigElement().getFilterConfigElementCount() - 1; i >= 0; i--) {
							final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
							if (frameFilterElement.getUserData() != null) {
								config.getFrameConfigElement().insertFilterConfigElementAfter(i, frameFilterElement.clone());
							}
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			editEffectsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(effectPanel);
				}
			});
			editBackgroundButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(colorPanel);
				}
			});
			colorChooseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					final Color color = ColorChooser.showColorChooser(colorField, TwisterSwingResources.getInstance().getString("label.background"), colorField.getColor());
					if (color != null) {
						colorField.setColor(color);
					}
				}
			});
			colorField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					final Color color = ColorChooser.showColorChooser(colorField, TwisterSwingResources.getInstance().getString("label.background"), colorField.getColor());
					if (color != null) {
						colorField.setColor(color);
					}
				}
			});
			final ColorChangeListener colorChangeListener = new ColorChangeListener() {
				@Override
				public void colorChanged(final ColorChangeEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setBackground(new Color32bit(colorField.getColor().getRGB()));
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			colorField.addColorChangeListener(colorChangeListener);
			editLayersButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(layersPanel2);
				}
			});
			editFiltersButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(filtersPanel2);
				}
			});
			backgroundListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
					colorField.removeColorChangeListener(colorChangeListener);
					colorField.setColor(new Color(config.getBackground().getARGB(), true));
					colorField.addColorChangeListener(colorChangeListener);
				}
			};
			filtersListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
					switch (e.getEventType()) {
						case ListConfigElement.ELEMENT_ADDED: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							filtersPanel.add(new FrameFilterPanel(config.getFrameConfigElement(), (FrameFilterConfigElement) e.getParams()[0]));
							filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_AFTER: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							filtersPanel.add(new FrameFilterPanel(config.getFrameConfigElement(), (FrameFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
							filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							filtersPanel.add(new FrameFilterPanel(config.getFrameConfigElement(), (FrameFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_REMOVED: {
							FrameFilterPanel panel = (FrameFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							filtersPanel.remove(((Integer) e.getParams()[1]).intValue());
							panel.dispose();
							filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
							// viewContext.resize();
							viewContext.restoreComponent(filtersPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_UP: {
							FrameFilterPanel panel = (FrameFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							filtersPanel.remove(panel);
							filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_DOWN: {
							FrameFilterPanel panel = (FrameFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							filtersPanel.remove(panel);
							filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
							break;
						}
						case ListConfigElement.ELEMENT_CHANGED: {
							FrameFilterPanel panel = (FrameFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							filtersPanel.remove(panel);
							panel.dispose();
							filtersPanel.add(new FrameFilterPanel(config.getFrameConfigElement(), (FrameFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							break;
						}
						default: {
							break;
						}
					}
					for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
						filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
					}
				}
			};
			layersListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
					switch (e.getEventType()) {
						case ListConfigElement.ELEMENT_ADDED: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT * 2 + 8);
							layersPanel.add(new GroupLayerPanel(config.getFrameConfigElement(), (GroupLayerConfigElement) e.getParams()[0]));
							layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_AFTER: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT * 2 + 8);
							layersPanel.add(new GroupLayerPanel(config.getFrameConfigElement(), (GroupLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
							layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT * 2 + 8);
							layersPanel.add(new GroupLayerPanel(config.getFrameConfigElement(), (GroupLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_REMOVED: {
							GroupLayerPanel panel = (GroupLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							layersPanel.remove(((Integer) e.getParams()[1]).intValue());
							panel.dispose();
							layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
							// viewContext.resize();
							viewContext.restoreComponent(layersPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_UP: {
							GroupLayerPanel panel = (GroupLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							layersPanel.remove(panel);
							layersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
							viewContext.restoreComponent(layersPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_DOWN: {
							GroupLayerPanel panel = (GroupLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							layersPanel.remove(panel);
							layersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
							viewContext.restoreComponent(layersPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_CHANGED: {
							FrameFilterPanel panel = (FrameFilterPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							layersPanel.remove(panel);
							panel.dispose();
							layersPanel.add(new GroupLayerPanel(config.getFrameConfigElement(), (GroupLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			frameListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							viewContext.setComponent(TwisterConfigPanel.this);
							FrameConfigElement frameElement = (FrameConfigElement) e.getParams()[1];
							frameElement.getFilterListElement().removeChangeListener(filtersListener);
							frameElement.getLayerListElement().removeChangeListener(layersListener);
							for (int i = 0; i < layersPanel.getComponentCount(); i++) {
								GroupLayerPanel panel = (GroupLayerPanel) layersPanel.getComponent(i);
								panel.dispose();
							}
							for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
								FrameFilterPanel panel = (FrameFilterPanel) layersPanel.getComponent(i);
								panel.dispose();
							}
							layersPanel.removeAll();
							filtersPanel.removeAll();
							layersPanel3.setVisible(config.getFrameConfigElement().getLayerConfigElementCount() == 0);
							filtersPanel3.setVisible(config.getFrameConfigElement().getFilterConfigElementCount() == 0);
							for (int i = 0; i < config.getFrameConfigElement().getLayerConfigElementCount(); i++) {
								final GroupLayerConfigElement groupLayerElement = config.getFrameConfigElement().getLayerConfigElement(i);
								final GroupLayerPanel groupLayerPanel = new GroupLayerPanel(config.getFrameConfigElement(), groupLayerElement);
								layersPanel.add(groupLayerPanel);
							}
							for (int i = 0; i < config.getFrameConfigElement().getFilterConfigElementCount(); i++) {
								final FrameFilterConfigElement frameFilterElement = config.getFrameConfigElement().getFilterConfigElement(i);
								final FrameFilterPanel frameFilterPanel = new FrameFilterPanel(config.getFrameConfigElement(), frameFilterElement);
								filtersPanel.add(frameFilterPanel);
							}
							config.getFrameConfigElement().getFilterListElement().addChangeListener(filtersListener);
							config.getFrameConfigElement().getLayerListElement().addChangeListener(layersListener);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			effectListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							viewContext.setComponent(TwisterConfigPanel.this);
							effectPanel.dispose();
							effectPanel = new EffectPanel(config, config.getEffectConfigElement());
							effectPanel.setName(createEffectPanelName(config.getEffectConfigElement()));
							effectPanel.setOpaque(true);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			config.getBackgroundElement().addChangeListener(backgroundListener);
			config.getFrameSingleElement().addChangeListener(frameListener);
			config.getEffectSingleElement().addChangeListener(effectListener);
			config.getFrameConfigElement().getFilterListElement().addChangeListener(filtersListener);
			config.getFrameConfigElement().getLayerListElement().addChangeListener(layersListener);
		}

		public void dispose() {
			config.getBackgroundElement().removeChangeListener(backgroundListener);
			config.getFrameSingleElement().removeChangeListener(frameListener);
			config.getEffectSingleElement().removeChangeListener(effectListener);
			config.getFrameConfigElement().getFilterListElement().removeChangeListener(filtersListener);
			config.getFrameConfigElement().getLayerListElement().removeChangeListener(layersListener);
			for (int i = 0; i < layersPanel.getComponentCount(); i++) {
				final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
				layerPanel.dispose();
			}
			for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
				final FrameFilterPanel filterPanel = (FrameFilterPanel) filtersPanel.getComponent(i);
				filterPanel.dispose();
			}
			effectPanel.dispose();
		}

		private String createFiltersPanelName() {
			return TwisterSwingResources.getInstance().getString("name.frameFilters");
		}

		private String createLayersPanelName() {
			return TwisterSwingResources.getInstance().getString("name.groupLayers");
		}

		private String createEffectPanelName(final EffectConfigElement effectElement) {
			return TwisterSwingResources.getInstance().getString("name.effect");
		}

		// private class RenderTask implements Runnable {
		// private final Object lock = new Object();
		// private Thread thread;
		// private boolean dirty;
		// private boolean running;
		// private final JPanel layersPanel;
		// private final DefaultThreadFactory factory = new DefaultThreadFactory("ConfigPanel", true, Thread.MIN_PRIORITY);
		//
		// /**
		// * @param layersPanel
		// */
		// public RenderTask(final JPanel layersPanel) {
		// this.layersPanel = layersPanel;
		// }
		//
		// /**
		// * @see java.lang.Runnable#run()
		// */
		// public void run() {
		// try {
		// for (;;) {
		// synchronized (lock) {
		// if (!running) {
		// break;
		// }
		// while (!dirty) {
		// lock.wait();
		// }
		// dirty = false;
		// }
		// Thread.sleep(500);
		// GUIUtil.executeTask(new Runnable() {
		// public void run() {
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.abortPreviewRenderer();
		// }
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.joinPreviewRenderer();
		// }
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.prepareLayerPreview();
		// }
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.startPreviewRenderer();
		// }
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.joinPreviewRenderer();
		// }
		// for (int i = 0; i < layersPanel.getComponentCount(); i++) {
		// final GroupLayerPanel layerPanel = (GroupLayerPanel) layersPanel.getComponent(i);
		// layerPanel.drawLayerPreview();
		// }
		// }
		// }, true);
		// }
		// }
		// catch (final InterruptedException e) {
		// Thread.currentThread().interrupt();
		// }
		// }
		//
		// /**
		// *
		// */
		// public void stop() {
		// if (thread != null) {
		// running = false;
		// thread.interrupt();
		// try {
		// thread.join();
		// }
		// catch (InterruptedException e) {
		// }
		// thread = null;
		// }
		// }
		//
		// /**
		// *
		// */
		// public void start() {
		// if (thread == null) {
		// thread = factory.newThread(this);
		// thread.setName(thread.getName() + " RenderTask");
		// running = true;
		// thread.start();
		// }
		// }
		//
		// /**
		// *
		// */
		// public void executeTask() {
		// synchronized (lock) {
		// dirty = true;
		// lock.notify();
		// }
		// }
		// }
		private class GroupLayerPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final JCheckBox selectedCheckBox = createCheckBox();
			private final LayerPreviewCanvas preview;
			private final JPanel layersPanel;
			private final GroupLayerConfigElement groupLayerElement;
			private final ElementChangeListener lockedListener;
			private final ElementChangeListener visibleListener;
			private final ElementChangeListener opacityListener;
			private final ElementChangeListener labelListener;
			private final ElementChangeListener filtersListener;
			private final ElementChangeListener layersListener;
			private final JPanel filtersPanel;
			private final JPanel layersPanel2;
			private final JPanel filtersPanel2;
			private RenderTask renderTask;

			/**
			 * @param frameElement
			 * @param groupLayerElement
			 */
			public GroupLayerPanel(final FrameConfigElement frameElement, final GroupLayerConfigElement groupLayerElement) {
				this.groupLayerElement = groupLayerElement;
				final JTextField label = createTextField(groupLayerElement.getLabel(), 120, GUIFactory.DEFAULT_HEIGHT);
				final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox visibleCheckBox = createIconCheckBox("visible", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox showLayersButton = createIconCheckBox("showLayers", "group", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton editFiltersButton = createIconTextButton("filters", "edit", 100, GUIFactory.DEFAULT_HEIGHT);
				final JButton selectFilterButton = createIconButton("selectFilters", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton appendFilterButton = createIconButton("appendFilter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton removeFilterButton = createIconButton("removeFilters", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveUpFilterButton = createIconButton("moveUpFilter", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveDownFilterButton = createIconButton("moveDownFilter", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton cloneFilterButton = createIconButton("cloneFilter", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertFilterAfterButton = createIconButton("insertFilterAfter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertFilterBeforeButton = createIconButton("insertFilterBefore", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton selectLayerButton = createIconButton("selectLayers", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton appendLayerButton = createIconButton("appendLayer", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton removeLayerButton = createIconButton("removeLayers", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveUpLayerButton = createIconButton("moveUpLayer", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveDownLayerButton = createIconButton("moveDownLayer", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton cloneLayerButton = createIconButton("cloneLayer", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertLayerAfterButton = createIconButton("insertLayerAfter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertLayerBeforeButton = createIconButton("insertLayerBefore", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JSpinner opacitySpinner = createSpinner(0, 100, 1);
				final JLabel layersLabel = createTextLabel("noImageLayer", SwingConstants.LEFT, 120, GUIFactory.DEFAULT_HEIGHT);
				final JLabel filtersLabel = createTextLabel("noLayerFilter", SwingConstants.LEFT, 120, GUIFactory.DEFAULT_HEIGHT);
				filtersPanel = createPanel(new StackLayout(), false);
				layersPanel = createPanel(new StackLayout(), false);
				layersLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
				filtersLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
				final Box layersPanel3 = createHorizontalBox(false);
				layersPanel3.add(selectLayerButton);
				layersPanel3.add(createSpace());
				layersPanel3.add(appendLayerButton);
				layersPanel3.add(createSpace());
				layersPanel3.add(removeLayerButton);
				layersPanel3.add(createSpace());
				layersPanel3.add(moveUpLayerButton);
				layersPanel3.add(createSpace());
				layersPanel3.add(moveDownLayerButton);
				layersPanel3.add(createSpace());
				layersPanel3.add(cloneLayerButton);
				// layersPanel3.add(insertLayerAfterButton);
				// layersPanel3.add(insertLayerBeforeButton);
				final Box layersPanel4 = createHorizontalBox(false);
				layersPanel4.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
				layersPanel4.add(layersLabel);
				layersPanel4.add(Box.createHorizontalGlue());
				layersPanel2 = createPanel(new BorderLayout(), false);
				layersPanel2.setName(createLayersPanelName(groupLayerElement));
				layersPanel2.add(layersPanel4, BorderLayout.NORTH);
				layersPanel2.add(layersPanel, BorderLayout.CENTER);
				layersPanel2.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
				final Box filtersPanel3 = createHorizontalBox(false);
				filtersPanel3.add(Box.createHorizontalGlue());
				filtersPanel3.add(selectFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(appendFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(removeFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(moveUpFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(moveDownFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(cloneFilterButton);
				// filtersPanel3.add(insertFilterAfterButton);
				// filtersPanel3.add(insertFilterBeforeButton);
				filtersPanel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
				final Box filtersPanel4 = createHorizontalBox(false);
				filtersPanel4.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
				filtersPanel4.add(filtersLabel);
				filtersPanel4.add(Box.createHorizontalGlue());
				filtersPanel2 = createPanel(new BorderLayout(), true);
				filtersPanel2.setName(createFiltersPanelName(groupLayerElement));
				filtersPanel2.add(filtersPanel3, BorderLayout.NORTH);
				filtersPanel2.add(filtersPanel, BorderLayout.CENTER);
				filtersPanel2.add(filtersPanel4, BorderLayout.SOUTH);
				filtersPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
				layersPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 24, 4, 0), BorderFactory.createMatteBorder(0, 0, 0, 0, Color.DARK_GRAY)));
				preview = new LayerPreviewCanvas();
				// preview.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.DARK_GRAY)));
				preview.setPreferredSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setMinimumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setMaximumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				final TwisterConfig previewConfig = new TwisterConfig();
				buildPreviewConfig(groupLayerElement, previewConfig);
				final TwisterRuntime runtime = new TwisterRuntime(previewConfig);
				final Map<Object, Object> hints = new HashMap<Object, Object>();
				hints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_PREVIEW);
				DefaultTwisterRenderer renderer = new DefaultTwisterRenderer(runtime);
				renderer.setRenderFactory(new Java2DRenderFactory());
				renderer.setRenderingHints(hints);
				renderer.setTile(new Tile(new IntegerVector2D(GUIFactory.DEFAULT_HEIGHT - 4, GUIFactory.DEFAULT_HEIGHT - 4), new IntegerVector2D(GUIFactory.DEFAULT_HEIGHT - 4, GUIFactory.DEFAULT_HEIGHT - 4), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
				renderTask = new RenderTask(renderer, preview);
				context.addRenderContextListener(renderTask);
				refreshPreview.addRenderTask(renderTask);
				final Box layerPanel = createHorizontalBox(false);
				layerPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
				layerPanel.add(createSpace());
				layerPanel.add(showLayersButton);
				layerPanel.add(selectedCheckBox);
				layerPanel.add(preview);
				layerPanel.add(createSpace());
				layerPanel.add(label);
				layerPanel.add(createSpace());
				layerPanel.add(opacitySpinner);
				layerPanel.add(createSpace());
				layerPanel.add(lockedCheckBox);
				layerPanel.add(createSpace());
				layerPanel.add(visibleCheckBox);
				layerPanel.add(createSpace());
				layerPanel.add(editFiltersButton);
				layerPanel.add(createSpace());
				layerPanel.add(Box.createHorizontalGlue());
				layerPanel.add(layersPanel3);
				layersPanel3.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
				final JPanel subpanelContainer = createPanel(new StackLayout(), false);
				subpanelContainer.add(layersPanel2);
				setLayout(new BorderLayout());
				add(layerPanel, BorderLayout.NORTH);
				add(subpanelContainer, BorderLayout.CENTER);
				showLayersButton.setSelected(true);
				showLayersButton.setPreferredSize(new Dimension(24, 24));
				editFiltersButton.setText(createEditFiltersText(groupLayerElement));
				opacitySpinner.setValue(groupLayerElement.getOpacity().intValue());
				opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
				lockedCheckBox.setSelected(groupLayerElement.isLocked());
				visibleCheckBox.setSelected(groupLayerElement.isVisible());
				layersPanel4.setVisible(groupLayerElement.getLayerConfigElementCount() == 0);
				filtersPanel4.setVisible(groupLayerElement.getFilterConfigElementCount() == 0);
				for (int i = 0; i < groupLayerElement.getLayerConfigElementCount(); i++) {
					final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
					layersPanel.add(new ImageLayerPanel(groupLayerElement, imageLayerElement));
				}
				for (int i = 0; i < groupLayerElement.getFilterConfigElementCount(); i++) {
					final LayerFilterConfigElement imageFilterElement = groupLayerElement.getFilterConfigElement(i);
					filtersPanel.add(new LayerFilterPanel(groupLayerElement, imageFilterElement));
				}
				for (int i = 0; i < layersPanel.getComponentCount(); i++) {
					layersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
				}
				for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
					filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
				}
				appendLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							for (int i = groupLayerElement.getLayerConfigElementCount() - 1; i >= 0; i--) {
								final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
								if (imageLayerElement.getUserData() != null) {
									final ImageLayerConfigElement element = new ImageLayerConfigElement();
									element.setImageConfigElement(new ImageConfigElement());
									groupLayerElement.insertLayerConfigElementBefore(i, element);
									return;
								}
							}
							final ImageLayerConfigElement element = new ImageLayerConfigElement();
							element.setImageConfigElement(new ImageConfigElement());
							groupLayerElement.appendLayerConfigElement(element);
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				removeLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							for (int i = groupLayerElement.getLayerConfigElementCount() - 1; i >= 0; i--) {
								final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
								if (imageLayerElement.getUserData() != null) {
									groupLayerElement.removeLayerConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				// insertLayerAfterButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = 0; i < groupLayerElement.getLayerConfigElementCount(); i++) {
				// ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
				// if (imageLayerElement.getUserData() != null) {
				// groupLayerElement.insertLayerConfigElementAfter(i, new ImageLayerConfigElement());
				// }
				// }
				// }
				// });
				// insertLayerBeforeButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = groupLayerElement.getLayerConfigElementCount() - 1; i >= 0; i--) {
				// ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
				// if (imageLayerElement.getUserData() != null) {
				// groupLayerElement.insertLayerConfigElementBefore(i, new ImageLayerConfigElement());
				// }
				// }
				// }
				// });
				appendFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							for (int i = groupLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									groupLayerElement.insertFilterConfigElementBefore(i, new LayerFilterConfigElement());
									return;
								}
							}
							groupLayerElement.appendFilterConfigElement(new LayerFilterConfigElement());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				removeFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							for (int i = groupLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									groupLayerElement.removeFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				// insertFilterAfterButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = 0; i < groupLayerElement.getFilterConfigElementCount(); i++) {
				// LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
				// if (layerFilterElement.getUserData() != null) {
				// groupLayerElement.insertFilterConfigElementAfter(i, new LayerFilterConfigElement());
				// }
				// }
				// }
				// });
				// insertFilterBeforeButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = groupLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
				// LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
				// if (layerFilterElement.getUserData() != null) {
				// groupLayerElement.insertFilterConfigElementBefore(i, new LayerFilterConfigElement());
				// }
				// }
				// }
				// });
				selectLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						boolean allSelected = true;
						for (int i = 0; i < layersPanel.getComponentCount(); i++) {
							if (!((ImageLayerPanel) layersPanel.getComponent(i)).isSelected()) {
								allSelected = false;
							}
						}
						if (allSelected) {
							for (int i = 0; i < layersPanel.getComponentCount(); i++) {
								((ImageLayerPanel) layersPanel.getComponent(i)).setSelected(false);
							}
						}
						else {
							for (int i = 0; i < layersPanel.getComponentCount(); i++) {
								((ImageLayerPanel) layersPanel.getComponent(i)).setSelected(true);
							}
						}
					}
				});
				selectFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						boolean allSelected = true;
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							if (!((LayerFilterPanel) filtersPanel.getComponent(i)).isSelected()) {
								allSelected = false;
							}
						}
						if (allSelected) {
							for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
								((LayerFilterPanel) filtersPanel.getComponent(i)).setSelected(false);
							}
						}
						else {
							for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
								((LayerFilterPanel) filtersPanel.getComponent(i)).setSelected(true);
							}
						}
					}
				});
				moveUpLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = 0; i < groupLayerElement.getLayerConfigElementCount(); i++) {
								final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
								if (imageLayerElement.getUserData() != null) {
									groupLayerElement.moveUpLayerConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				moveDownLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = groupLayerElement.getLayerConfigElementCount() - 1; i >= 0; i--) {
								final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
								if (imageLayerElement.getUserData() != null) {
									groupLayerElement.moveDownLayerConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				cloneLayerButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = groupLayerElement.getLayerConfigElementCount() - 1; i >= 0; i--) {
								final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(i);
								if (imageLayerElement.getUserData() != null) {
									groupLayerElement.insertLayerConfigElementAfter(i, imageLayerElement.clone());
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				moveUpFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = 0; i < groupLayerElement.getFilterConfigElementCount(); i++) {
								final LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									groupLayerElement.moveUpFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				moveDownFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = groupLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									groupLayerElement.moveDownFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				cloneFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = groupLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = groupLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									groupLayerElement.insertFilterConfigElementAfter(i, layerFilterElement.clone());
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				selectedCheckBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (selectedCheckBox.isSelected()) {
							groupLayerElement.setUserData(Boolean.TRUE);
						}
						else {
							groupLayerElement.setUserData(null);
						}
					}
				});
				final ActionListener lockedActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						groupLayerElement.getContext().updateTimestamp();
						groupLayerElement.setLocked(lockedCheckBox.isSelected());
					}
				};
				lockedCheckBox.addActionListener(lockedActionListener);
				final ActionListener visibleActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							groupLayerElement.setVisible(visibleCheckBox.isSelected());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				visibleCheckBox.addActionListener(visibleActionListener);
				final ChangeListener opacityChangeListener = new ChangeListener() {
					@Override
					public void stateChanged(final ChangeEvent e) {
						try {
							context.acquire();
							groupLayerElement.getContext().updateTimestamp();
							groupLayerElement.setOpacity(((Number) opacitySpinner.getValue()).intValue());
							opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				opacitySpinner.addChangeListener(opacityChangeListener);
				label.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						// groupLayerElement.getLabelElement().removeChangeListener(labelListener);
						groupLayerElement.getContext().updateTimestamp();
						groupLayerElement.setLabel(label.getText());
						layersPanel2.setName(createLayersPanelName(groupLayerElement));
						filtersPanel2.setName(createFiltersPanelName(groupLayerElement));
						// groupLayerElement.getLabelElement().addChangeListener(labelListener);
					}
				});
				showLayersButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						layersPanel2.setVisible(showLayersButton.isSelected());
						viewContext.resize();
					}
				});
				editFiltersButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						viewContext.setComponent(filtersPanel2);
					}
				});
				lockedListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						lockedCheckBox.removeActionListener(lockedActionListener);
						lockedCheckBox.setSelected(groupLayerElement.isLocked());
						lockedCheckBox.addActionListener(lockedActionListener);
					}
				};
				visibleListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						visibleCheckBox.removeActionListener(visibleActionListener);
						visibleCheckBox.setSelected(groupLayerElement.isVisible());
						visibleCheckBox.addActionListener(visibleActionListener);
					}
				};
				opacityListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						opacitySpinner.removeChangeListener(opacityChangeListener);
						opacitySpinner.setValue(groupLayerElement.getOpacity().intValue());
						opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
						opacitySpinner.addChangeListener(opacityChangeListener);
					}
				};
				labelListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						label.setText(groupLayerElement.getLabel());
						layersPanel2.setName(createLayersPanelName(groupLayerElement));
						filtersPanel2.setName(createFiltersPanelName(groupLayerElement));
					}
				};
				filtersListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ListConfigElement.ELEMENT_ADDED: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(groupLayerElement, (LayerFilterConfigElement) e.getParams()[0]));
								filtersPanel4.setVisible(groupLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_AFTER: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(groupLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
								filtersPanel4.setVisible(groupLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(groupLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								filtersPanel4.setVisible(groupLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_REMOVED: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(((Integer) e.getParams()[1]).intValue());
								panel.dispose();
								filtersPanel4.setVisible(groupLayerElement.getFilterConfigElementCount() == 0);
								// viewContext.resize();
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_UP: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_DOWN: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_CHANGED: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								panel.dispose();
								filtersPanel.add(new LayerFilterPanel(groupLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								break;
							}
							default: {
								break;
							}
						}
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
						}
						editFiltersButton.setText(createEditFiltersText(groupLayerElement));
					}
				};
				layersListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ListConfigElement.ELEMENT_ADDED: {
								if (groupLayerElement.getLayerConfigElementCount() > 1) {
									viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								}
								layersPanel.add(new ImageLayerPanel(groupLayerElement, (ImageLayerConfigElement) e.getParams()[0]));
								layersPanel4.setVisible(groupLayerElement.getLayerConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_AFTER: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								layersPanel.add(new ImageLayerPanel(groupLayerElement, (ImageLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
								layersPanel4.setVisible(groupLayerElement.getLayerConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								layersPanel.add(new ImageLayerPanel(groupLayerElement, (ImageLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								layersPanel4.setVisible(groupLayerElement.getLayerConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_REMOVED: {
								ImageLayerPanel panel = (ImageLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								layersPanel.remove(((Integer) e.getParams()[1]).intValue());
								panel.dispose();
								layersPanel4.setVisible(groupLayerElement.getLayerConfigElementCount() == 0);
								// viewContext.resize();
								viewContext.restoreComponent(ConfigPanel.this.layersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_UP: {
								ImageLayerPanel panel = (ImageLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								layersPanel.remove(panel);
								layersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
								viewContext.restoreComponent(ConfigPanel.this.layersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_DOWN: {
								ImageLayerPanel panel = (ImageLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								layersPanel.remove(panel);
								layersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
								viewContext.restoreComponent(ConfigPanel.this.layersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_CHANGED: {
								ImageLayerPanel panel = (ImageLayerPanel) layersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								layersPanel.remove(panel);
								panel.dispose();
								layersPanel.add(new ImageLayerPanel(groupLayerElement, (ImageLayerConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								break;
							}
							default: {
								break;
							}
						}
						for (int i = 0; i < layersPanel.getComponentCount(); i++) {
							layersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
						}
					}
				};
				groupLayerElement.getLabelElement().addChangeListener(labelListener);
				groupLayerElement.getLockedElement().addChangeListener(lockedListener);
				groupLayerElement.getVisibleElement().addChangeListener(visibleListener);
				groupLayerElement.getOpacityElement().addChangeListener(opacityListener);
				groupLayerElement.getLayerListElement().addChangeListener(layersListener);
				groupLayerElement.getFilterListElement().addChangeListener(filtersListener);
			}

			public void dispose() {
				refreshPreview.removeRenderTask(renderTask);
				context.removeRenderContextListener(renderTask);
				groupLayerElement.getLabelElement().removeChangeListener(labelListener);
				groupLayerElement.getLockedElement().removeChangeListener(lockedListener);
				groupLayerElement.getVisibleElement().removeChangeListener(visibleListener);
				groupLayerElement.getOpacityElement().removeChangeListener(opacityListener);
				groupLayerElement.getLayerListElement().removeChangeListener(layersListener);
				groupLayerElement.getFilterListElement().removeChangeListener(filtersListener);
				for (int i = 0; i < layersPanel.getComponentCount(); i++) {
					final ImageLayerPanel layerPanel = (ImageLayerPanel) layersPanel.getComponent(i);
					layerPanel.dispose();
				}
				for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
					final LayerFilterPanel filterPanel = (LayerFilterPanel) filtersPanel.getComponent(i);
					filterPanel.dispose();
				}
				renderTask.dispose();
			}

			private void buildPreviewConfig(final GroupLayerConfigElement groupLayerElement, final TwisterConfig previewConfig) {
				final FrameConfigElement frameElement = new FrameConfigElement();
				previewConfig.setFrameConfigElement(frameElement);
				final EffectConfigElement effectElement = new EffectConfigElement();
				previewConfig.setEffectConfigElement(effectElement);
				frameElement.appendLayerConfigElement(groupLayerElement);
			}

			private String createEditFiltersText(final GroupLayerConfigElement groupLayerElement) {
				if (groupLayerElement.getFilterConfigElementCount() == 1) {
					return groupLayerElement.getFilterConfigElementCount() + " " + TwisterSwingResources.getInstance().getString("label.filter");
				}
				else {
					return groupLayerElement.getFilterConfigElementCount() + " " + TwisterSwingResources.getInstance().getString("label.filters");
				}
			}

			private String createFiltersPanelName(final GroupLayerConfigElement groupLayerElement) {
				return groupLayerElement.getLabel();
			}

			private String createLayersPanelName(final GroupLayerConfigElement groupLayerElement) {
				return groupLayerElement.getLabel();
			}

			private String createOpacityTooltip(final JSpinner opacitySpinner) {
				return TwisterSwingResources.getInstance().getString("label.opacity") + " " + opacitySpinner.getValue() + "%";
			}

			/**
			 * @param selected
			 */
			public void setSelected(final boolean selected) {
				selectedCheckBox.setSelected(selected);
			}

			/**
			 * @return
			 */
			public boolean isSelected() {
				return selectedCheckBox.isSelected();
			}
		}

		private class ImageLayerPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final JCheckBox selectedCheckBox = createCheckBox();
			private final LayerPreviewCanvas preview;
			private final ImageLayerConfigElement imageLayerElement;
			private final ElementChangeListener lockedListener;
			private final ElementChangeListener visibleListener;
			private final ElementChangeListener opacityListener;
			private final ElementChangeListener filtersListener;
			private final ElementChangeListener labelListener;
			private final JPanel filtersPanel;
			private final ImagePanel imagePanel;
			private RenderTask renderTask;

			/**
			 * @param groupLayerElement
			 * @param imageLayerElement
			 */
			public ImageLayerPanel(final GroupLayerConfigElement groupLayerElement, final ImageLayerConfigElement imageLayerElement) {
				this.imageLayerElement = imageLayerElement;
				final JTextField label = createTextField(imageLayerElement.getLabel(), 120, GUIFactory.DEFAULT_HEIGHT);
				final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox visibleCheckBox = createIconCheckBox("visible", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton showFiltersButton = createIconTextButton("filters", "edit", 100, GUIFactory.DEFAULT_HEIGHT);
				final JButton selectFilterButton = createIconButton("selectFilters", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton appendFilterButton = createIconButton("appendFilter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton removeFilterButton = createIconButton("removeFilters", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveUpFilterButton = createIconButton("moveUpFilter", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton moveDownFilterButton = createIconButton("moveDownFilter", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton cloneFilterButton = createIconButton("cloneFilter", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertFilterAfterButton = createIconButton("insertFilterAfter", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				// final JButton insertFilterBeforeButton = createIconButton("insertFilterBefore", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JSpinner opacitySpinner = createSpinner(0, 100, 1);
				final JLabel filtersLabel = createTextLabel("noLayerFilter", SwingConstants.LEFT, 120, GUIFactory.DEFAULT_HEIGHT);
				filtersPanel = createPanel(new StackLayout(), false);
				imagePanel = new ImagePanel(imageLayerElement, imageLayerElement.getImageConfigElement());
				imagePanel.setOpaque(false);
				filtersLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
				final Box filtersPanel3 = createHorizontalBox(false);
				filtersPanel3.add(Box.createHorizontalGlue());
				filtersPanel3.add(selectFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(appendFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(removeFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(moveUpFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(moveDownFilterButton);
				filtersPanel3.add(createSpace());
				filtersPanel3.add(cloneFilterButton);
				// filtersPanel3.add(insertFilterAfterButton);
				// filtersPanel3.add(insertFilterBeforeButton);
				filtersPanel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
				final Box filtersPanel4 = createHorizontalBox(false);
				filtersPanel4.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
				filtersPanel4.add(filtersLabel);
				filtersPanel4.add(Box.createHorizontalGlue());
				final JPanel filtersPanel2 = createPanel(new BorderLayout(), true);
				filtersPanel2.setName(createFiltersPanelName(imageLayerElement));
				filtersPanel2.add(filtersPanel3, BorderLayout.NORTH);
				filtersPanel2.add(filtersPanel, BorderLayout.CENTER);
				filtersPanel2.add(filtersPanel4, BorderLayout.SOUTH);
				filtersPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
				preview = new LayerPreviewCanvas();
				// preview.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.DARK_GRAY)));
				preview.setPreferredSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setMinimumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setMaximumSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				preview.setSize(new Dimension(GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT));
				final TwisterConfig previewConfig = new TwisterConfig();
				buildPreviewConfig(imageLayerElement, previewConfig);
				final TwisterRuntime runtime = new TwisterRuntime(previewConfig);
				final Map<Object, Object> hints = new HashMap<Object, Object>();
				hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
				hints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_PREVIEW);
				DefaultTwisterRenderer renderer = new DefaultTwisterRenderer(runtime);
				renderer.setRenderFactory(new Java2DRenderFactory());
				renderer.setRenderingHints(hints);
				renderer.setTile(new Tile(new IntegerVector2D(GUIFactory.DEFAULT_HEIGHT - 4, GUIFactory.DEFAULT_HEIGHT - 4), new IntegerVector2D(GUIFactory.DEFAULT_HEIGHT - 4, GUIFactory.DEFAULT_HEIGHT - 4), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
				renderTask = new RenderTask(renderer, preview);
				context.addRenderContextListener(renderTask);
				refreshPreview.addRenderTask(renderTask);
				final Box layerPanel = createHorizontalBox(false);
				layerPanel.add(createSpace());
				layerPanel.add(selectedCheckBox);
				layerPanel.add(preview);
				layerPanel.add(createSpace());
				layerPanel.add(label);
				layerPanel.add(createSpace());
				layerPanel.add(opacitySpinner);
				layerPanel.add(createSpace());
				layerPanel.add(lockedCheckBox);
				layerPanel.add(createSpace());
				layerPanel.add(visibleCheckBox);
				layerPanel.add(createSpace());
				layerPanel.add(showFiltersButton);
				layerPanel.add(Box.createHorizontalGlue());
				layerPanel.add(imagePanel);
				setLayout(new BorderLayout());
				add(layerPanel, BorderLayout.CENTER);
				setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
				layerPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
				showFiltersButton.setText(createEditFiltersText(imageLayerElement));
				opacitySpinner.setValue(imageLayerElement.getOpacity().intValue());
				opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
				lockedCheckBox.setSelected(imageLayerElement.isLocked());
				visibleCheckBox.setSelected(imageLayerElement.isVisible());
				filtersPanel4.setVisible(imageLayerElement.getFilterConfigElementCount() == 0);
				for (int i = 0; i < imageLayerElement.getFilterConfigElementCount(); i++) {
					final LayerFilterConfigElement imageFilterElement = imageLayerElement.getFilterConfigElement(i);
					filtersPanel.add(new LayerFilterPanel(imageLayerElement, imageFilterElement));
				}
				for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
					filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
				}
				appendFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageLayerElement.getContext().updateTimestamp();
							for (int i = imageLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									imageLayerElement.insertFilterConfigElementBefore(i, new LayerFilterConfigElement());
									return;
								}
							}
							imageLayerElement.appendFilterConfigElement(new LayerFilterConfigElement());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				removeFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageLayerElement.getContext().updateTimestamp();
							for (int i = imageLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									imageLayerElement.removeFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				// insertFilterAfterButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = 0; i < imageLayerElement.getFilterConfigElementCount(); i++) {
				// LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
				// if (layerFilterElement.getUserData() != null) {
				// imageLayerElement.insertFilterConfigElementAfter(i, new LayerFilterConfigElement());
				// }
				// }
				// }
				// });
				// insertFilterBeforeButton.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// for (int i = imageLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
				// LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
				// if (layerFilterElement.getUserData() != null) {
				// imageLayerElement.insertFilterConfigElementBefore(i, new LayerFilterConfigElement());
				// }
				// }
				// }
				// });
				selectFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						boolean allSelected = true;
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							if (!((LayerFilterPanel) filtersPanel.getComponent(i)).isSelected()) {
								allSelected = false;
							}
						}
						if (allSelected) {
							for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
								((LayerFilterPanel) filtersPanel.getComponent(i)).setSelected(false);
							}
						}
						else {
							for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
								((LayerFilterPanel) filtersPanel.getComponent(i)).setSelected(true);
							}
						}
					}
				});
				moveUpFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = 0; i < imageLayerElement.getFilterConfigElementCount(); i++) {
								final LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									imageLayerElement.moveUpFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				moveDownFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = imageLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									imageLayerElement.moveDownFilterConfigElement(i);
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				cloneFilterButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							config.getFrameConfigElement().getContext().updateTimestamp();
							for (int i = imageLayerElement.getFilterConfigElementCount() - 1; i >= 0; i--) {
								final LayerFilterConfigElement layerFilterElement = imageLayerElement.getFilterConfigElement(i);
								if (layerFilterElement.getUserData() != null) {
									imageLayerElement.insertFilterConfigElementAfter(i, layerFilterElement.clone());
								}
							}
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				});
				selectedCheckBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (selectedCheckBox.isSelected()) {
							imageLayerElement.setUserData(Boolean.TRUE);
						}
						else {
							imageLayerElement.setUserData(null);
						}
					}
				});
				final ActionListener lockedActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageLayerElement.getContext().updateTimestamp();
							imageLayerElement.setLocked(lockedCheckBox.isSelected());
							context.release();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				lockedCheckBox.addActionListener(lockedActionListener);
				final ActionListener visibleActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageLayerElement.getContext().updateTimestamp();
							imageLayerElement.setVisible(visibleCheckBox.isSelected());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				visibleCheckBox.addActionListener(visibleActionListener);
				final ChangeListener opacityChangeListener = new ChangeListener() {
					@Override
					public void stateChanged(final ChangeEvent e) {
						try {
							context.acquire();
							imageLayerElement.getContext().updateTimestamp();
							imageLayerElement.setOpacity(((Number) opacitySpinner.getValue()).intValue());
							opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				opacitySpinner.addChangeListener(opacityChangeListener);
				label.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						// imageLayerElement.getLabelElement().removeChangeListener(labelListener);
						imageLayerElement.getContext().updateTimestamp();
						imageLayerElement.setLabel(label.getText());
						filtersPanel2.setName(createFiltersPanelName(imageLayerElement));
						// imageLayerElement.getLabelElement().addChangeListener(labelListener);
					}
				});
				showFiltersButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						viewContext.setComponent(filtersPanel2);
					}
				});
				lockedListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						lockedCheckBox.removeActionListener(lockedActionListener);
						lockedCheckBox.setSelected(imageLayerElement.isLocked());
						lockedCheckBox.addActionListener(lockedActionListener);
					}
				};
				visibleListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						visibleCheckBox.removeActionListener(visibleActionListener);
						visibleCheckBox.setSelected(imageLayerElement.isVisible());
						visibleCheckBox.addActionListener(visibleActionListener);
					}
				};
				opacityListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						opacitySpinner.removeChangeListener(opacityChangeListener);
						opacitySpinner.setValue(imageLayerElement.getOpacity().intValue());
						opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
						opacitySpinner.addChangeListener(opacityChangeListener);
					}
				};
				filtersListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ListConfigElement.ELEMENT_ADDED: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(imageLayerElement, (LayerFilterConfigElement) e.getParams()[0]));
								filtersPanel4.setVisible(imageLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_AFTER: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(imageLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
								filtersPanel4.setVisible(imageLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
								viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
								filtersPanel.add(new LayerFilterPanel(imageLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								filtersPanel4.setVisible(imageLayerElement.getFilterConfigElementCount() == 0);
								break;
							}
							case ListConfigElement.ELEMENT_REMOVED: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(((Integer) e.getParams()[1]).intValue());
								panel.dispose();
								filtersPanel4.setVisible(imageLayerElement.getFilterConfigElementCount() == 0);
								// viewContext.resize();
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_UP: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_MOVED_DOWN: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								filtersPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
								viewContext.restoreComponent(filtersPanel2);
								break;
							}
							case ListConfigElement.ELEMENT_CHANGED: {
								LayerFilterPanel panel = (LayerFilterPanel) filtersPanel.getComponent(((Integer) e.getParams()[1]).intValue());
								filtersPanel.remove(panel);
								panel.dispose();
								filtersPanel.add(new LayerFilterPanel(imageLayerElement, (LayerFilterConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
								break;
							}
							default: {
								break;
							}
						}
						for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
							filtersPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
						}
						showFiltersButton.setText(createEditFiltersText(imageLayerElement));
					}
				};
				labelListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						label.setText(imageLayerElement.getLabel());
						filtersPanel2.setName(createFiltersPanelName(imageLayerElement));
					}
				};
				imageLayerElement.getLabelElement().addChangeListener(labelListener);
				imageLayerElement.getLockedElement().addChangeListener(lockedListener);
				imageLayerElement.getVisibleElement().addChangeListener(visibleListener);
				imageLayerElement.getOpacityElement().addChangeListener(opacityListener);
				imageLayerElement.getFilterListElement().addChangeListener(filtersListener);
			}

			public void dispose() {
				refreshPreview.removeRenderTask(renderTask);
				context.removeRenderContextListener(renderTask);
				imageLayerElement.getLabelElement().removeChangeListener(labelListener);
				imageLayerElement.getLockedElement().removeChangeListener(lockedListener);
				imageLayerElement.getVisibleElement().removeChangeListener(visibleListener);
				imageLayerElement.getOpacityElement().removeChangeListener(opacityListener);
				imageLayerElement.getFilterListElement().removeChangeListener(filtersListener);
				for (int i = 0; i < filtersPanel.getComponentCount(); i++) {
					final LayerFilterPanel filterPanel = (LayerFilterPanel) filtersPanel.getComponent(i);
					filterPanel.dispose();
				}
				imagePanel.dispose();
				renderTask.dispose();
			}

			private void buildPreviewConfig(final ImageLayerConfigElement imageLayerElement, final TwisterConfig previewConfig) {
				final FrameConfigElement frameElement = new FrameConfigElement();
				previewConfig.setFrameConfigElement(frameElement);
				final EffectConfigElement effectElement = new EffectConfigElement();
				previewConfig.setEffectConfigElement(effectElement);
				final GroupLayerConfigElement groupLayerElement = new GroupLayerConfigElement();
				frameElement.appendLayerConfigElement(groupLayerElement);
				groupLayerElement.appendLayerConfigElement(imageLayerElement);
			}

			private String createEditFiltersText(final ImageLayerConfigElement imageLayerElement) {
				if (imageLayerElement.getFilterConfigElementCount() == 1) {
					return imageLayerElement.getFilterConfigElementCount() + " " + TwisterSwingResources.getInstance().getString("label.filter");
				}
				else {
					return imageLayerElement.getFilterConfigElementCount() + " " + TwisterSwingResources.getInstance().getString("label.filters");
				}
			}

			private String createFiltersPanelName(final ImageLayerConfigElement imageLayerElement) {
				return imageLayerElement.getLabel();
			}

			private String createOpacityTooltip(final JSpinner opacitySpinner) {
				return TwisterSwingResources.getInstance().getString("label.opacity") + " " + opacitySpinner.getValue() + "%";
			}

			/**
			 * @param selected
			 */
			public void setSelected(final boolean selected) {
				selectedCheckBox.setSelected(selected);
			}

			/**
			 * @return
			 */
			public boolean isSelected() {
				return selectedCheckBox.isSelected();
			}
		}

		private class LayerFilterPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final JCheckBox selectedCheckBox = createCheckBox();
			private ViewPanel configView;
			private final LayerFilterConfigElement imageFilterElement;
			private final ElementChangeListener lockedListener;
			private final ElementChangeListener enabledListener;
			private final ElementChangeListener labelListener;
			private final ElementChangeListener filterListener;

			/**
			 * @param layerElement
			 * @param imageFilterElement
			 */
			public LayerFilterPanel(final LayerConfigElement layerElement, final LayerFilterConfigElement imageFilterElement) {
				this.imageFilterElement = imageFilterElement;
				final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getLayerFilterRegistry(), true);
				final JTextField label = createTextField(imageFilterElement.getLabel(), 120, GUIFactory.DEFAULT_HEIGHT);
				final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox enabledCheckBox = createIconCheckBox("enabled", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
				final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
				final Box filterPanel = createHorizontalBox(false);
				filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
				filterPanel.add(createSpace());
				filterPanel.add(selectedCheckBox);
				filterPanel.add(label);
				filterPanel.add(createSpace());
				filterPanel.add(lockedCheckBox);
				filterPanel.add(createSpace());
				filterPanel.add(enabledCheckBox);
				filterPanel.add(createSpace());
				filterPanel.add(Box.createHorizontalGlue());
				filterPanel.add(extensionComboBox);
				filterPanel.add(createSpace());
				filterPanel.add(editOptionsButton);
				setLayout(new BorderLayout());
				setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
				add(filterPanel, BorderLayout.CENTER);
				lockedCheckBox.setSelected(imageFilterElement.isLocked());
				enabledCheckBox.setSelected(imageFilterElement.isEnabled());
				editOptionsButton.setEnabled((imageFilterElement != null) && (imageFilterElement.getReference() != null));
				if (imageFilterElement.getReference() != null) {
					model.setSelectedItemByExtensionId(imageFilterElement.getReference().getExtensionId());
				}
				final ActionListener comboListener = new ActionListener() {
					@Override
					@SuppressWarnings("unchecked")
					public void actionPerformed(final ActionEvent e) {
						try {
							ConfigurableExtension<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig> extension = (ConfigurableExtension<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig>) extensionComboBox.getSelectedItem();
							context.acquire();
							context.stopRenderers();
							imageFilterElement.getContext().updateTimestamp();
							if (extension instanceof NullConfigurableExtension) {
								imageFilterElement.setReference(null);
							}
							else {
								imageFilterElement.setReference(extension.createConfigurableExtensionReference());
							}
							context.startRenderers();
							context.release();
							context.refresh();
							if (configView != null) {
								viewContext.removeComponent(configView);
								configView = null;
							}
						}
						catch (ExtensionException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				extensionComboBox.addActionListener(comboListener);
				editOptionsButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						if (imageFilterElement.getReference() != null) {
							if (configView == null) {
								try {
									final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(imageFilterElement.getReference().getExtensionId());
									configView = extension.createExtensionRuntime().createView(imageFilterElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
								catch (final ExtensionException x) {
									configView = new DefaultViewRuntime().createView(imageFilterElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
							}
							if (configView != null) {
								configView.setName(createFiltersPanelName(imageFilterElement));
							}
							viewContext.setComponent(configView);
						}
					}
				});
				selectedCheckBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (selectedCheckBox.isSelected()) {
							imageFilterElement.setUserData(Boolean.TRUE);
						}
						else {
							imageFilterElement.setUserData(null);
						}
					}
				});
				label.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						// imageFilterElement.getLabelElement().removeChangeListener(labelListener);
						imageFilterElement.getContext().updateTimestamp();
						imageFilterElement.setLabel(label.getText());
						if (configView != null) {
							configView.setName(createFiltersPanelName(imageFilterElement));
						}
						// imageFilterElement.getLabelElement().addChangeListener(labelListener);
					}
				});
				final ActionListener lockedActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageFilterElement.getContext().updateTimestamp();
							imageFilterElement.setLocked(lockedCheckBox.isSelected());
							context.release();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				lockedCheckBox.addActionListener(lockedActionListener);
				final ActionListener enabledActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							imageFilterElement.getContext().updateTimestamp();
							imageFilterElement.setEnabled(enabledCheckBox.isSelected());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				enabledCheckBox.addActionListener(enabledActionListener);
				filterListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
								editOptionsButton.setEnabled(imageFilterElement.getReference() != null);
								extensionComboBox.removeActionListener(comboListener);
								if (imageFilterElement.getReference() != null) {
									model.setSelectedItemByExtensionId(imageFilterElement.getReference().getExtensionId());
								}
								else {
									model.setSelectedItem(0);
								}
								if (configView != null) {
									viewContext.removeComponent(configView);
									configView = null;
								}
								extensionComboBox.addActionListener(comboListener);
								break;
							}
							default: {
								break;
							}
						}
					}
				};
				lockedListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						lockedCheckBox.removeActionListener(lockedActionListener);
						lockedCheckBox.setSelected(imageFilterElement.isLocked());
						lockedCheckBox.addActionListener(lockedActionListener);
					}
				};
				enabledListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						enabledCheckBox.removeActionListener(enabledActionListener);
						enabledCheckBox.setSelected(imageFilterElement.isEnabled());
						enabledCheckBox.addActionListener(enabledActionListener);
					}
				};
				labelListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						label.setText(imageFilterElement.getLabel());
						if (configView != null) {
							configView.setName(createFiltersPanelName(imageFilterElement));
						}
					}
				};
				imageFilterElement.getLabelElement().addChangeListener(labelListener);
				imageFilterElement.getLockedElement().addChangeListener(lockedListener);
				imageFilterElement.getEnabledElement().addChangeListener(enabledListener);
				imageFilterElement.getExtensionElement().addChangeListener(filterListener);
			}

			public void dispose() {
				imageFilterElement.getLabelElement().removeChangeListener(labelListener);
				imageFilterElement.getLockedElement().removeChangeListener(lockedListener);
				imageFilterElement.getEnabledElement().removeChangeListener(enabledListener);
				imageFilterElement.getExtensionElement().removeChangeListener(filterListener);
				if (configView != null) {
					viewContext.removeComponent(configView);
					configView.dispose();
					configView = null;
				}
			}

			private String createFiltersPanelName(final LayerFilterConfigElement imageFilterElement) {
				return imageFilterElement.getLabel();
			}

			/**
			 * @param selected
			 */
			public void setSelected(final boolean selected) {
				selectedCheckBox.setSelected(selected);
			}

			/**
			 * @return
			 */
			public boolean isSelected() {
				return selectedCheckBox.isSelected();
			}
		}

		private class FrameFilterPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private final JCheckBox selectedCheckBox = createCheckBox();
			private ViewPanel configView;
			private final FrameFilterConfigElement frameFilterElement;
			private final ElementChangeListener lockedListener;
			private final ElementChangeListener enabledListener;
			private final ElementChangeListener labelListener;
			private final ElementChangeListener filterListener;

			/**
			 * @param frameElement
			 * @param frameFilterElement
			 */
			public FrameFilterPanel(final FrameConfigElement frameElement, final FrameFilterConfigElement frameFilterElement) {
				this.frameFilterElement = frameFilterElement;
				final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getFrameFilterRegistry(), true);
				final JTextField label = createTextField(frameFilterElement.getLabel(), 120, GUIFactory.DEFAULT_HEIGHT);
				final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox enabledCheckBox = createIconCheckBox("enabled", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
				final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
				final Box filterPanel = createHorizontalBox(false);
				filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
				filterPanel.add(createSpace());
				filterPanel.add(selectedCheckBox);
				filterPanel.add(label);
				filterPanel.add(createSpace());
				filterPanel.add(lockedCheckBox);
				filterPanel.add(createSpace());
				filterPanel.add(enabledCheckBox);
				filterPanel.add(createSpace());
				filterPanel.add(Box.createHorizontalGlue());
				filterPanel.add(extensionComboBox);
				filterPanel.add(createSpace());
				filterPanel.add(editOptionsButton);
				setLayout(new BorderLayout());
				setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
				add(filterPanel, BorderLayout.CENTER);
				lockedCheckBox.setSelected(frameFilterElement.isLocked());
				enabledCheckBox.setSelected(frameFilterElement.isEnabled());
				editOptionsButton.setEnabled((frameFilterElement != null) && (frameFilterElement.getReference() != null));
				if (frameFilterElement.getReference() != null) {
					model.setSelectedItemByExtensionId(frameFilterElement.getReference().getExtensionId());
				}
				final ActionListener comboListener = new ActionListener() {
					@Override
					@SuppressWarnings("unchecked")
					public void actionPerformed(final ActionEvent e) {
						try {
							ConfigurableExtension<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig> extension = (ConfigurableExtension<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig>) extensionComboBox.getSelectedItem();
							context.acquire();
							context.stopRenderers();
							frameFilterElement.getContext().updateTimestamp();
							if (extension instanceof NullConfigurableExtension) {
								frameFilterElement.setReference(null);
							}
							else {
								frameFilterElement.setReference(extension.createConfigurableExtensionReference());
							}
							context.startRenderers();
							context.release();
							context.refresh();
							if (configView != null) {
								viewContext.removeComponent(configView);
								configView = null;
							}
						}
						catch (ExtensionException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				extensionComboBox.addActionListener(comboListener);
				editOptionsButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						if (frameFilterElement.getReference() != null) {
							if (configView == null) {
								try {
									final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(frameFilterElement.getReference().getExtensionId());
									configView = extension.createExtensionRuntime().createView(frameFilterElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
								catch (final ExtensionException x) {
									configView = new DefaultViewRuntime().createView(frameFilterElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
							}
							if (configView != null) {
								configView.setName(createFiltersPanelName(frameFilterElement));
							}
							viewContext.setComponent(configView);
						}
					}
				});
				label.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						// frameFilterElement.getLabelElement().removeChangeListener(labelListener);
						frameFilterElement.getContext().updateTimestamp();
						frameFilterElement.setLabel(label.getText());
						if (configView != null) {
							configView.setName(createFiltersPanelName(frameFilterElement));
						}
						// frameFilterElement.getLabelElement().addChangeListener(labelListener);
					}
				});
				selectedCheckBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent e) {
						if (selectedCheckBox.isSelected()) {
							frameFilterElement.setUserData(Boolean.TRUE);
						}
						else {
							frameFilterElement.setUserData(null);
						}
					}
				});
				final ActionListener lockedActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						frameFilterElement.getContext().updateTimestamp();
						frameFilterElement.setLocked(lockedCheckBox.isSelected());
					}
				};
				lockedCheckBox.addActionListener(lockedActionListener);
				final ActionListener enabledActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							frameFilterElement.getContext().updateTimestamp();
							frameFilterElement.setEnabled(enabledCheckBox.isSelected());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				enabledCheckBox.addActionListener(enabledActionListener);
				filterListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
								editOptionsButton.setEnabled(frameFilterElement.getReference() != null);
								extensionComboBox.removeActionListener(comboListener);
								if (frameFilterElement.getReference() != null) {
									model.setSelectedItemByExtensionId(frameFilterElement.getReference().getExtensionId());
								}
								else {
									model.setSelectedItem(0);
								}
								if (configView != null) {
									viewContext.removeComponent(configView);
									configView = null;
								}
								extensionComboBox.addActionListener(comboListener);
								break;
							}
							default: {
								break;
							}
						}
					}
				};
				lockedListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						lockedCheckBox.removeActionListener(lockedActionListener);
						lockedCheckBox.setSelected(frameFilterElement.isLocked());
						lockedCheckBox.addActionListener(lockedActionListener);
					}
				};
				enabledListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						enabledCheckBox.removeActionListener(enabledActionListener);
						enabledCheckBox.setSelected(frameFilterElement.isEnabled());
						enabledCheckBox.addActionListener(enabledActionListener);
					}
				};
				labelListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						label.setText(frameFilterElement.getLabel());
						if (configView != null) {
							configView.setName(createFiltersPanelName(frameFilterElement));
						}
					}
				};
				frameFilterElement.getLabelElement().addChangeListener(labelListener);
				frameFilterElement.getLockedElement().addChangeListener(lockedListener);
				frameFilterElement.getEnabledElement().addChangeListener(enabledListener);
				frameFilterElement.getExtensionElement().addChangeListener(filterListener);
			}

			public void dispose() {
				frameFilterElement.getLabelElement().removeChangeListener(labelListener);
				frameFilterElement.getLockedElement().removeChangeListener(lockedListener);
				frameFilterElement.getEnabledElement().removeChangeListener(enabledListener);
				frameFilterElement.getExtensionElement().removeChangeListener(filterListener);
				if (configView != null) {
					viewContext.removeComponent(configView);
					configView.dispose();
					configView = null;
				}
			}

			private String createFiltersPanelName(final FrameFilterConfigElement frameFilterElement) {
				return frameFilterElement.getLabel();
			}

			/**
			 * @param selected
			 */
			public void setSelected(final boolean selected) {
				selectedCheckBox.setSelected(selected);
			}

			/**
			 * @return
			 */
			public boolean isSelected() {
				return selectedCheckBox.isSelected();
			}
		}

		private class ImagePanel extends JPanel {
			private static final long serialVersionUID = 1L;
			// private ViewPanel configView;
			private final ImageConfigElement imageElement;
			private final ElementChangeListener imageListener;
			private NavigationFrame navigationFrame;

			/**
			 * @param layerElement
			 * @param imageElement
			 */
			public ImagePanel(final ImageLayerConfigElement layerElement, final ImageConfigElement imageElement) {
				this.imageElement = imageElement;
				final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getImageRegistry(), true);
				final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
				final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
				final Box imagePanel = createHorizontalBox(false);
				imagePanel.add(Box.createHorizontalGlue());
				imagePanel.add(extensionComboBox);
				imagePanel.add(createSpace());
				imagePanel.add(editOptionsButton);
				setLayout(new BorderLayout());
				add(imagePanel, BorderLayout.CENTER);
				editOptionsButton.setEnabled((imageElement != null) && (imageElement.getReference() != null));
				if (imageElement.getReference() != null) {
					model.setSelectedItemByExtensionId(imageElement.getReference().getExtensionId());
				}
				final ActionListener comboListener = new ActionListener() {
					@Override
					@SuppressWarnings("unchecked")
					public void actionPerformed(final ActionEvent e) {
						try {
							ConfigurableExtension<ImageExtensionRuntime<?>, ImageExtensionConfig> extension = (ConfigurableExtension<ImageExtensionRuntime<?>, ImageExtensionConfig>) extensionComboBox.getSelectedItem();
							context.acquire();
							context.stopRenderers();
							imageElement.getContext().updateTimestamp();
							if (extension instanceof NullConfigurableExtension) {
								imageElement.setReference(null);
							}
							else {
								imageElement.setReference(extension.createConfigurableExtensionReference());
							}
							context.startRenderers();
							context.release();
							context.refresh();
							// if (configView != null) {
							// viewContext.removeComponent(configView);
							// configView = null;
							// }
						}
						catch (ExtensionException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				extensionComboBox.addActionListener(comboListener);
				editOptionsButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						if (imageElement.getReference() != null) {
							if (navigationFrame == null) {
								navigationFrame = new NavigationFrame(imageElement.getExtensionElement(), context, session, createImagePanelName(layerElement));
								navigationFrame.addWindowListener(new WindowAdapter() {
									/**
									 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
									 */
									@Override
									public void windowClosing(final WindowEvent e) {
										if (navigationFrame != null) {
											navigationFrame.dispose();
											navigationFrame = null;
										}
									}
								});
							}
							// if (configView == null) {
							// try {
							// final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
							// configView = extension.createExtensionRuntime().createView(imageElement.getReference().getExtensionConfig(), viewContext, context, session);
							// }
							// catch (final ExtensionException x) {
							// configView = new DefaultViewRuntime().createView(imageElement.getReference().getExtensionConfig(), viewContext, context, session);
							// }
							// }
							// if (configView != null) {
							// configView.setName(createImagePanelName(layerElement));
							// }
							navigationFrame.setVisible(true);
							navigationFrame.toFront();
							GUIUtil.executeTask(new Runnable() {
								@Override
								public void run() {
									navigationFrame.setup();
								}
							}, true);
							// viewContext.setComponent(configView);
						}
					}
				});
				imageListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
								editOptionsButton.setEnabled(imageElement.getReference() != null);
								extensionComboBox.removeActionListener(comboListener);
								if (imageElement.getReference() != null) {
									model.setSelectedItemByExtensionId(imageElement.getReference().getExtensionId());
								}
								else {
									model.setSelectedItem(0);
								}
								// if (configView != null) {
								// viewContext.removeComponent(configView);
								// configView = null;
								// }
								extensionComboBox.addActionListener(comboListener);
								break;
							}
							default: {
								break;
							}
						}
					}
				};
				imageElement.getExtensionElement().addChangeListener(imageListener);
			}

			public void dispose() {
				imageElement.getExtensionElement().removeChangeListener(imageListener);
				if (navigationFrame != null) {
					navigationFrame.dispose();
					navigationFrame = null;
				}
				// if (configView != null) {
				// viewContext.removeComponent(configView);
				// configView.dispose();
				// configView = null;
				// }
			}

			private String createImagePanelName(final ImageLayerConfigElement layerElement) {
				return layerElement.getLabel();
			}
		}

		private class EffectPanel extends JPanel {
			private static final long serialVersionUID = 1L;
			private ViewPanel configView;
			private final EffectConfigElement effectElement;
			private final ElementChangeListener lockedListener;
			private final ElementChangeListener enabledListener;
			private final ElementChangeListener effectListener;

			/**
			 * @param config
			 * @param effectElement
			 */
			public EffectPanel(final TwisterConfig config, final EffectConfigElement effectElement) {
				this.effectElement = effectElement;
				final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(TwisterRegistry.getInstance().getEffectRegistry(), true);
				final JLabel label = createTextLabel("effect", SwingConstants.LEFT, 80, GUIFactory.DEFAULT_HEIGHT);
				final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JCheckBox enabledCheckBox = createIconCheckBox("enabled", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
				final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
				final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
				label.setPreferredSize(new Dimension(55, GUIFactory.DEFAULT_HEIGHT));
				final Box effectPanel = createHorizontalBox(false);
				effectPanel.add(Box.createHorizontalGlue());
				effectPanel.add(label);
				effectPanel.add(createSpace());
				effectPanel.add(lockedCheckBox);
				effectPanel.add(createSpace());
				effectPanel.add(enabledCheckBox);
				effectPanel.add(createSpace());
				effectPanel.add(extensionComboBox);
				effectPanel.add(createSpace());
				effectPanel.add(editOptionsButton);
				effectPanel.add(Box.createHorizontalGlue());
				setLayout(new BorderLayout());
				add(effectPanel, BorderLayout.CENTER);
				setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
				editOptionsButton.setEnabled((effectElement != null) && (effectElement.getReference() != null));
				if (effectElement.getReference() != null) {
					model.setSelectedItemByExtensionId(effectElement.getReference().getExtensionId());
				}
				lockedCheckBox.setSelected(effectElement.isLocked());
				enabledCheckBox.setSelected(effectElement.isEnabled());
				final ActionListener comboListener = new ActionListener() {
					@Override
					@SuppressWarnings("unchecked")
					public void actionPerformed(final ActionEvent e) {
						try {
							ConfigurableExtension<EffectExtensionRuntime<?>, EffectExtensionConfig> extension = (ConfigurableExtension<EffectExtensionRuntime<?>, EffectExtensionConfig>) extensionComboBox.getSelectedItem();
							context.acquire();
							context.stopRenderers();
							effectElement.getContext().updateTimestamp();
							if (extension == NullConfigurableExtension.getInstance()) {
								effectElement.setReference(null);
							}
							else {
								effectElement.setReference(extension.createConfigurableExtensionReference());
							}
							context.startRenderers();
							context.release();
							context.refresh();
							if (configView != null) {
								viewContext.removeComponent(configView);
								configView = null;
							}
						}
						catch (ExtensionException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				extensionComboBox.addActionListener(comboListener);
				editOptionsButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						if (effectElement.getReference() != null) {
							if (configView == null) {
								try {
									final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(effectElement.getReference().getExtensionId());
									configView = extension.createExtensionRuntime().createView(effectElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
								catch (final ExtensionException x) {
									configView = new DefaultViewRuntime().createView(effectElement.getReference().getExtensionConfig(), viewContext, context, session);
								}
							}
							if (configView != null) {
								configView.setName(createEffectPanelName());
							}
							viewContext.setComponent(configView);
						}
					}
				});
				final ActionListener lockedActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						effectElement.getContext().updateTimestamp();
						effectElement.setLocked(lockedCheckBox.isSelected());
					}
				};
				lockedCheckBox.addActionListener(lockedActionListener);
				final ActionListener enabledActionListener = new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							context.acquire();
							effectElement.getContext().updateTimestamp();
							effectElement.setEnabled(enabledCheckBox.isSelected());
							context.release();
							context.refresh();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				};
				enabledCheckBox.addActionListener(enabledActionListener);
				effectListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						switch (e.getEventType()) {
							case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
								editOptionsButton.setEnabled(effectElement.getReference() != null);
								extensionComboBox.removeActionListener(comboListener);
								if (effectElement.getReference() != null) {
									model.setSelectedItemByExtensionId(effectElement.getReference().getExtensionId());
								}
								else {
									model.setSelectedItem(0);
								}
								if (configView != null) {
									viewContext.removeComponent(configView);
									configView = null;
								}
								extensionComboBox.addActionListener(comboListener);
								break;
							}
							default: {
								break;
							}
						}
					}
				};
				lockedListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						lockedCheckBox.removeActionListener(lockedActionListener);
						lockedCheckBox.setSelected(effectElement.isLocked());
						lockedCheckBox.addActionListener(lockedActionListener);
					}
				};
				enabledListener = new ElementChangeListener() {
					@Override
					public void valueChanged(final ElementChangeEvent e) {
						enabledCheckBox.removeActionListener(enabledActionListener);
						enabledCheckBox.setSelected(effectElement.isEnabled());
						enabledCheckBox.addActionListener(enabledActionListener);
					}
				};
				effectElement.getLockedElement().addChangeListener(lockedListener);
				effectElement.getEnabledElement().addChangeListener(enabledListener);
				effectElement.getExtensionElement().addChangeListener(effectListener);
			}

			public void dispose() {
				effectElement.getLockedElement().removeChangeListener(lockedListener);
				effectElement.getEnabledElement().removeChangeListener(enabledListener);
				effectElement.getExtensionElement().removeChangeListener(effectListener);
				if (configView != null) {
					viewContext.removeComponent(configView);
					configView.dispose();
					configView = null;
				}
			}

			private String createEffectPanelName() {
				return TwisterSwingResources.getInstance().getString("name.effect");
			}
		}
	}

	private class LayerPreviewCanvas extends Canvas {
		private static final long serialVersionUID = 1L;
		private VolatileImage volatileImage;

		/**
		 * @see java.awt.Canvas#update(java.awt.Graphics)
		 */
		@Override
		public void update(final Graphics g) {
			paint(g);
		}

		/**
		 * @see java.awt.Canvas#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			do {
				if ((volatileImage == null) || (volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)) {
					volatileImage = createVolatileImage(getWidth(), getHeight());
					context.refresh();
				}
				else if (volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_RESTORED) {
					context.refresh();
				}
				g.drawImage(volatileImage, 0, 0, this);
			}
			while (volatileImage.contentsLost());
		}

		/**
		 * @param renderer
		 */
		public void draw(TwisterRenderer renderer) {
			if (volatileImage != null) {
				Graphics2D g = volatileImage.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				renderer.drawImage(g, 1, 1, GUIFactory.DEFAULT_HEIGHT, GUIFactory.DEFAULT_HEIGHT);
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.dispose();
			}
		}
	}

	private class RepaintTask implements Runnable {
		private final List<RenderTask> tasks = new LinkedList<RenderTask>();
		private LinkedList<RenderTask> tasksList = new LinkedList<RenderTask>();
		private final Object lock = new Object();
		private Thread repaintThread;
		private boolean running;
		private boolean refresh;

		/**
		 * @param o
		 * @return
		 * @see java.util.List#add(java.lang.Object)
		 */
		public void addRenderTask(RenderTask o) {
			synchronized (tasks) {
				tasks.add(o);
			}
		}

		/**
		 * @param o
		 * @return
		 * @see java.util.List#remove(java.lang.Object)
		 */
		public void removeRenderTask(RenderTask o) {
			synchronized (tasks) {
				tasks.remove(o);
			}
		}

		/**
		 * 
		 */
		public void refresh() {
			synchronized (lock) {
				refresh = true;
				lock.notify();
			}
		}

		/**
		 * 
		 */
		public void start() {
			if (repaintThread == null) {
				running = true;
				repaintThread = new Thread(this);
				repaintThread.setName("ConfigPanel RepaintTask");
				repaintThread.setPriority(Thread.NORM_PRIORITY);
				repaintThread.setDaemon(true);
				repaintThread.start();
			}
		}

		/**
		 * 
		 */
		public void stop() {
			if (repaintThread != null) {
				running = false;
				repaintThread.interrupt();
				try {
					repaintThread.join();
				}
				catch (final InterruptedException e) {
				}
				repaintThread = null;
			}
		}

		/**
		 * @return
		 */
		public boolean isStarted() {
			return repaintThread != null;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				long idleTime = 0;
				while (running) {
					synchronized (tasks) {
						tasksList.clear();
						tasksList.addAll(tasks);
					}
					for (RenderTask task : tasksList) {
						task.repaint();
					}
					synchronized (lock) {
						if (refresh) {
							idleTime = System.currentTimeMillis();
						}
						if (System.currentTimeMillis() - idleTime > 750) {
							if (!refresh) {
								lock.wait();
							}
							idleTime = System.currentTimeMillis();
						}
						refresh = false;
					}
					if (!running) {
						break;
					}
					Thread.sleep(250);
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class RenderTask implements RenderContextListener {
		private TwisterRenderer renderer;
		private LayerPreviewCanvas preview;

		public RenderTask(TwisterRenderer renderer, LayerPreviewCanvas preview) {
			this.renderer = renderer;
			this.preview = preview;
		}

		/**
		 * 
		 */
		public void dispose() {
			renderer.dispose();
			renderer = null;
			preview = null;
		}

		/**
		 * 
		 */
		public void repaint() {
			preview.repaint();
		}

		/**
		 * 
		 */
		@Override
		public void drawImage() {
			preview.draw(renderer);
		}

		/**
		 * @param isDynamic
		 */
		@Override
		public void prepareImage(boolean isDynamic) {
			renderer.prepareImage(isDynamic);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContextListener#startRenderer()
		 */
		@Override
		public void startRenderer() {
			renderer.startRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContextListener#stopRenderer()
		 */
		@Override
		public void stopRenderer() {
			renderer.stopRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContextListener#joinRenderer()
		 */
		@Override
		public void joinRenderer() {
			renderer.joinRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContextListener#abortRenderer()
		 */
		@Override
		public void abortRenderer() {
			renderer.abortRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContextListener#refresh()
		 */
		@Override
		public void refresh() {
			refreshPreview.refresh();
		}
	}
}
