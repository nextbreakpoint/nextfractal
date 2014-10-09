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
package com.nextbreakpoint.nextfractal.mandelbrot.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.NullConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ConfigurableExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionComboBoxModel;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.processingFormula.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterConfigPanel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterSwingRegistry;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterSwingResources;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ViewPanel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.DefaultViewRuntime;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.util.Speed;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotConfigPanel extends ViewPanel {
	private static final Logger logger = Logger.getLogger(MandelbrotConfigPanel.class.getName());
	private static final long serialVersionUID = 1L;
	private MandelbrotFractalPanel fractalPanel;
	private final MandelbrotImagePanel imagePanel;
	private final ViewContext viewContext;
	private final RenderContext context;
	private final NodeSession session;
	private final ValueChangeListener configListener;
	private final MandelbrotConfig config;
	private final Color oddColor;
	private final Color evenColor;

	/**
	 * @param config
	 * @param viewContext
	 * @param context
	 * @param session
	 */
	public MandelbrotConfigPanel(final MandelbrotConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		this.viewContext = viewContext;
		this.context = context;
		this.session = session;
		this.config = config;
		oddColor = getBackground().brighter();
		evenColor = getBackground().brighter();
		imagePanel = new MandelbrotImagePanel(config);
		fractalPanel = new MandelbrotFractalPanel(config, config.getMandelbrotFractal());
		setLayout(new StackLayout());
		add(fractalPanel);
		add(imagePanel);
		setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
		configListener = new ValueChangeListener() {
			@Override
			public void valueChanged(final ValueChangeEvent e) {
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						remove(fractalPanel);
						fractalPanel.dispose();
						fractalPanel = new MandelbrotFractalPanel(config, config.getMandelbrotFractal());
						add(fractalPanel, BorderLayout.CENTER);
						viewContext.setComponent(MandelbrotConfigPanel.this);
						break;
					}
					default: {
						break;
					}
				}
			}
		};
		config.getFractalSingleElement().addChangeListener(configListener);
	}

	@Override
	public void dispose() {
		config.getFractalSingleElement().removeChangeListener(configListener);
		fractalPanel.dispose();
		imagePanel.dispose();
	}

	private static JCheckBox createIconCheckBox(final String key, final String iconKey, final int width, final int height) {
		final JCheckBox checkbox = GUIFactory.createCheckBox((String) null, MandelbrotSwingResources.getInstance().getString("tooltip." + key));
		try {
			checkbox.setIcon(new ImageIcon(ImageIO.read(MandelbrotConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			checkbox.setSelectedIcon(new ImageIcon(ImageIO.read(MandelbrotConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-selected-icon.png"))));
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
		final JButton button = GUIFactory.createButton((String) null, MandelbrotSwingResources.getInstance().getString("tooltip." + key));
		try {
			button.setIcon(new ImageIcon(ImageIO.read(MandelbrotConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			button.setPressedIcon(new ImageIcon(ImageIO.read(MandelbrotConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-pressed-icon.png"))));
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
	// final JCheckBox checkbox = GUIFactory.createCheckBox(MandelbrotSwingResources.getInstance().getString("label." + key), MandelbrotSwingResources.getInstance().getString("tooltip." + key));
	// // final FontMetrics fm = checkbox.getFontMetrics(checkbox.getFont());
	// // int width = fm.stringWidth(checkbox.getText()) + 20;
	// checkbox.setPreferredSize(new Dimension(width, height));
	// checkbox.setMinimumSize(new Dimension(width, height));
	// checkbox.setMaximumSize(new Dimension(width, height));
	// checkbox.setOpaque(false);
	// return checkbox;
	// }
	private static JButton createTextButton(final int width, final int height) {
		final JButton button = GUIFactory.createSmallButton((String) null, (String) null);
		button.setPreferredSize(new Dimension(width, height));
		button.setMinimumSize(new Dimension(width, height));
		button.setMaximumSize(new Dimension(width, height));
		button.setOpaque(false);
		return button;
	}

	// private static JButton createTextButton(final String key, final int width, final int height) {
	// final JButton button = GUIFactory.createSmallButton(MandelbrotSwingResources.getInstance().getString("label." + key), MandelbrotSwingResources.getInstance().getString("tooltip." + key));
	// // final FontMetrics fm = button.getFontMetrics(button.getFont());
	// // int width = fm.stringWidth(button.getText());
	// button.setPreferredSize(new Dimension(width, height));
	// button.setMinimumSize(new Dimension(width, height));
	// button.setMaximumSize(new Dimension(width, height));
	// button.setOpaque(false);
	// return button;
	// }
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
		final JLabel label = GUIFactory.createSmallLabel(MandelbrotSwingResources.getInstance().getString("label." + key), SwingConstants.LEFT);
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

	private static Box createVerticalBox(final boolean opaque) {
		final Box box = Box.createVerticalBox();
		box.setOpaque(opaque);
		return box;
	}

	private static Component createSpace() {
		final Component box = Box.createHorizontalStrut(4);
		return box;
	}

	private class MandelbrotImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final ValueChangeListener showPreviewListener;
		private final ValueChangeListener showOrbitListener;
		private final ValueChangeListener showOrbitTrapListener;
		private final ValueChangeListener imageModeListener;
		private final ValueChangeListener inputModeListener;
		private final ValueChangeListener speedListener;
		private final MandelbrotConfig config;

		public MandelbrotImagePanel(final MandelbrotConfig config) {
			this.config = config;
			final JCheckBox showPreviewCheckBox = createCheckBox();
			final JCheckBox showOrbitCheckBox = createCheckBox();
			final JCheckBox showOrbitTrapCheckBox = createCheckBox();
			final JLabel showPreviewLabel = createTextLabel("showPreview", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel showOrbitLabel = createTextLabel("showOrbit", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel showOrbitTrapLabel = createTextLabel("showOrbitTrap", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel imageModeLabel = createTextLabel("imageMode", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel inputModeLabel = createTextLabel("inputMode", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel zoomSpeedLabel = createTextLabel("zoomSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel shiftSpeedLabel = createTextLabel("shiftSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel rotationSpeedLabel = createTextLabel("rotationSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField zoomSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getPosition().getZ()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField shiftSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getPosition().getW()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField rotationSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getRotation().getZ()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JComboBox imageModeComboBox = GUIFactory.createSmallComboBox(null);
			imageModeComboBox.setOpaque(false);
			imageModeComboBox.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			imageModeComboBox.setMaximumSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			imageModeComboBox.setMinimumSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			imageModeComboBox.addItem(new Object[] { "Mandelbrot", MandelbrotImageConfig.IMAGE_MODE_MANDELBROT });
			imageModeComboBox.addItem(new Object[] { "Julia", MandelbrotImageConfig.IMAGE_MODE_JULIA });
			imageModeComboBox.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((Object[]) value)[0], index, isSelected, cellHasFocus);
				}
			});
			final JComboBox inputModeComboBox = GUIFactory.createSmallComboBox(null);
			inputModeComboBox.setOpaque(false);
			inputModeComboBox.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			inputModeComboBox.setMaximumSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			inputModeComboBox.setMinimumSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			inputModeComboBox.addItem(new Object[] { TwisterSwingResources.getInstance().getString("label.zoom"), MandelbrotImageConfig.INPUT_MODE_ZOOM });
			inputModeComboBox.addItem(new Object[] { TwisterSwingResources.getInstance().getString("label.select"), MandelbrotImageConfig.INPUT_MODE_SELECT });
			inputModeComboBox.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((Object[]) value)[0], index, isSelected, cellHasFocus);
				}
			});
			final Box tmpPanel0 = createHorizontalBox(false);
			tmpPanel0.add(imageModeLabel);
			tmpPanel0.add(createSpace());
			tmpPanel0.add(imageModeComboBox);
			tmpPanel0.add(Box.createHorizontalGlue());
			final Box tmpPanel1 = createHorizontalBox(false);
			tmpPanel1.add(showOrbitLabel);
			tmpPanel1.add(createSpace());
			tmpPanel1.add(showOrbitCheckBox);
			tmpPanel1.add(Box.createHorizontalGlue());
			final Box tmpPanel2 = createHorizontalBox(false);
			tmpPanel2.add(showPreviewLabel);
			tmpPanel2.add(createSpace());
			tmpPanel2.add(showPreviewCheckBox);
			tmpPanel2.add(Box.createHorizontalGlue());
			final Box tmpPanel3 = createHorizontalBox(false);
			tmpPanel3.add(inputModeLabel);
			tmpPanel3.add(createSpace());
			tmpPanel3.add(inputModeComboBox);
			tmpPanel3.add(Box.createHorizontalGlue());
			final Box tmpPanel4 = createHorizontalBox(false);
			tmpPanel4.add(zoomSpeedLabel);
			tmpPanel4.add(createSpace());
			tmpPanel4.add(zoomSpeedTextfield);
			tmpPanel4.add(Box.createHorizontalGlue());
			final Box tmpPanel5 = createHorizontalBox(false);
			tmpPanel5.add(rotationSpeedLabel);
			tmpPanel5.add(createSpace());
			tmpPanel5.add(rotationSpeedTextfield);
			tmpPanel5.add(Box.createHorizontalGlue());
			final Box tmpPanel6 = createHorizontalBox(false);
			tmpPanel6.add(shiftSpeedLabel);
			tmpPanel6.add(createSpace());
			tmpPanel6.add(shiftSpeedTextfield);
			tmpPanel6.add(Box.createHorizontalGlue());
			final Box tmpPanel7 = createHorizontalBox(false);
			tmpPanel7.add(showOrbitTrapLabel);
			tmpPanel7.add(createSpace());
			tmpPanel7.add(showOrbitTrapCheckBox);
			tmpPanel7.add(Box.createHorizontalGlue());
			final Box tmpPanel = createVerticalBox(false);
			tmpPanel.add(tmpPanel0);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel3);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel2);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel1);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel7);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel4);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel6);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel5);
			tmpPanel.add(Box.createVerticalStrut(8));
			setLayout(new BorderLayout());
			add(tmpPanel, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			setOpaque(false);
			showPreviewCheckBox.setSelected(config.getShowPreview());
			showOrbitCheckBox.setSelected(config.getShowOrbit());
			showOrbitTrapCheckBox.setSelected(config.getShowOrbitTrap());
			updateImageMode(config, imageModeComboBox);
			updateInputMode(config, inputModeComboBox);
			final ActionListener showPreviewActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setShowPreview(showPreviewCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			showPreviewCheckBox.addActionListener(showPreviewActionListener);
			final ActionListener showOrbitActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setShowOrbit(showOrbitCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			showOrbitCheckBox.addActionListener(showOrbitActionListener);
			final ActionListener showOrbitTrapActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setShowOrbitTrap(showOrbitTrapCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			showOrbitTrapCheckBox.addActionListener(showOrbitTrapActionListener);
			final ActionListener imageModeActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setImageMode((Integer) ((Object[]) imageModeComboBox.getSelectedItem())[1]);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			imageModeComboBox.addActionListener(imageModeActionListener);
			final ActionListener inputModeActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.setInputMode((Integer) ((Object[]) inputModeComboBox.getSelectedItem())[1]);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			inputModeComboBox.addActionListener(inputModeActionListener);
			final ActionListener zoomSpeedActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						Speed speed = config.getSpeed();
						Speed newSpeed = new Speed(new DoubleVector4D(0, 0, Double.valueOf(zoomSpeedTextfield.getText()), speed.getPosition().getW()), speed.getRotation());
						config.setSpeed(newSpeed);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			zoomSpeedTextfield.addActionListener(zoomSpeedActionListener);
			final ActionListener shiftSpeedActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						Speed speed = config.getSpeed();
						Speed newSpeed = new Speed(new DoubleVector4D(0, 0, speed.getPosition().getZ(), Double.valueOf(shiftSpeedTextfield.getText())), speed.getRotation());
						config.setSpeed(newSpeed);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			shiftSpeedTextfield.addActionListener(shiftSpeedActionListener);
			final ActionListener rotationSpeedActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						Speed speed = config.getSpeed();
						Speed newSpeed = new Speed(speed.getPosition(), new DoubleVector4D(0, 0, Double.valueOf(rotationSpeedTextfield.getText()), 0));
						config.setSpeed(newSpeed);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			rotationSpeedTextfield.addActionListener(rotationSpeedActionListener);
			showPreviewListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					showPreviewCheckBox.removeActionListener(showPreviewActionListener);
					showPreviewCheckBox.setSelected(config.getShowPreview());
					showPreviewCheckBox.addActionListener(showPreviewActionListener);
				}
			};
			showOrbitListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					showOrbitCheckBox.removeActionListener(showOrbitActionListener);
					showOrbitCheckBox.setSelected(config.getShowOrbit());
					showOrbitCheckBox.addActionListener(showOrbitActionListener);
				}
			};
			showOrbitTrapListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					showOrbitTrapCheckBox.removeActionListener(showOrbitTrapActionListener);
					showOrbitTrapCheckBox.setSelected(config.getShowOrbitTrap());
					showOrbitTrapCheckBox.addActionListener(showOrbitTrapActionListener);
				}
			};
			imageModeListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					imageModeComboBox.removeActionListener(imageModeActionListener);
					updateImageMode(config, imageModeComboBox);
					imageModeComboBox.addActionListener(imageModeActionListener);
				}
			};
			inputModeListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					inputModeComboBox.removeActionListener(inputModeActionListener);
					updateInputMode(config, inputModeComboBox);
					inputModeComboBox.addActionListener(inputModeActionListener);
				}
			};
			speedListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					zoomSpeedTextfield.removeActionListener(zoomSpeedActionListener);
					shiftSpeedTextfield.removeActionListener(shiftSpeedActionListener);
					rotationSpeedTextfield.removeActionListener(rotationSpeedActionListener);
					zoomSpeedTextfield.setText(String.valueOf(config.getSpeed().getPosition().getZ()));
					shiftSpeedTextfield.setText(String.valueOf(config.getSpeed().getPosition().getW()));
					rotationSpeedTextfield.setText(String.valueOf(config.getSpeed().getRotation().getZ()));
					zoomSpeedTextfield.addActionListener(zoomSpeedActionListener);
					shiftSpeedTextfield.addActionListener(shiftSpeedActionListener);
					rotationSpeedTextfield.addActionListener(rotationSpeedActionListener);
				}
			};
			config.getShowPreviewElement().addChangeListener(showPreviewListener);
			config.getShowOrbitElement().addChangeListener(showOrbitListener);
			config.getShowOrbitTrapElement().addChangeListener(showOrbitTrapListener);
			config.getImageModeElement().addChangeListener(imageModeListener);
			config.getInputModeElement().addChangeListener(inputModeListener);
			config.getSpeedElement().addChangeListener(speedListener);
		}

		public void dispose() {
			config.getShowPreviewElement().removeChangeListener(showPreviewListener);
			config.getShowOrbitElement().removeChangeListener(showOrbitListener);
			config.getShowOrbitTrapElement().removeChangeListener(showOrbitTrapListener);
			config.getImageModeElement().removeChangeListener(imageModeListener);
			config.getInputModeElement().removeChangeListener(inputModeListener);
			config.getSpeedElement().removeChangeListener(speedListener);
		}

		private void updateImageMode(final MandelbrotConfig config, final JComboBox imageModeComboBox) {
			switch (config.getImageMode()) {
				case MandelbrotImageConfig.IMAGE_MODE_MANDELBROT: {
					imageModeComboBox.setSelectedIndex(0);
					break;
				}
				case MandelbrotImageConfig.IMAGE_MODE_JULIA: {
					imageModeComboBox.setSelectedIndex(1);
					break;
				}
				default: {
					break;
				}
			}
		}

		private void updateInputMode(final MandelbrotConfig config, final JComboBox inputModeComboBox) {
			switch (config.getInputMode()) {
				case MandelbrotImageConfig.INPUT_MODE_ZOOM: {
					inputModeComboBox.setSelectedIndex(0);
					break;
				}
				case MandelbrotImageConfig.INPUT_MODE_SELECT: {
					inputModeComboBox.setSelectedIndex(1);
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private class MandelbrotFractalPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JPanel incolouringFormulasPanel;
		private final JPanel outcolouringFormulasPanel;
		private RenderingFormulaPanel renderingFormulaPanel;
		private TransformingFormulaPanel transformingFormulaPanel;
		private ProcessingFormulaPanel processingFormulaPanel;
		private OrbitTrapPanel orbitTrapPanel;
		private final ValueChangeListener incolouringFormulasListener;
		private final ValueChangeListener outcolouringFormulasListener;
		private final ValueChangeListener renderingFormulaListener;
		private final ValueChangeListener transformingFormulaListener;
		private final ValueChangeListener processingFormulaListener;
		private final ValueChangeListener orbitTrapListener;
		private final MandelbrotFractalConfigElement fractalElement;

		// private ValueChangeListener centerListener;
		// private CenterListener centerFieldListener;
		// private JTextField[] textFields;
		/**
		 * @param config
		 * @param fractalElement
		 */
		public MandelbrotFractalPanel(final MandelbrotConfig config, final MandelbrotFractalConfigElement fractalElement) {
			this.fractalElement = fractalElement;
			incolouringFormulasPanel = createPanel(new StackLayout(), false);
			outcolouringFormulasPanel = createPanel(new StackLayout(), false);
			final JButton editIncolouringFormulasButton = createTextButton(200, GUIFactory.DEFAULT_HEIGHT);
			final JButton editOutcolouringFormulasButton = createTextButton(200, GUIFactory.DEFAULT_HEIGHT);
			final JButton selectIncolouringFormulaButton = createIconButton("selectIncolouringFormulas", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton appendIncolouringFormulaButton = createIconButton("appendIncolouringFormula", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton removeIncolouringFormulaButton = createIconButton("removeIncolouringFormulas", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveUpIncolouringFormulaButton = createIconButton("moveUpIncolouringFormula", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveDownIncolouringFormulaButton = createIconButton("moveDownIncolouringFormula", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton cloneIncolouringFormulaButton = createIconButton("cloneIncolouringFormula", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton selectOutcolouringFormulaButton = createIconButton("selectOutcolouringFormulas", "select", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton appendOutcolouringFormulaButton = createIconButton("appendOutcolouringFormula", "add", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton removeOutcolouringFormulaButton = createIconButton("removeOutcolouringFormulas", "remove", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveUpOutcolouringFormulaButton = createIconButton("moveUpOutcolouringFormula", "up", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton moveDownOutcolouringFormulaButton = createIconButton("moveDownOutcolouringFormula", "down", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JButton cloneOutcolouringFormulaButton = createIconButton("cloneOutcolouringFormula", "clone", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JLabel incolouringFormulasLabel2 = createTextLabel("incolouringFormulas", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel outcolouringFormulasLabel2 = createTextLabel("outcolouringFormulas", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			// JLabel[] labels = new JLabel[1];
			// labels[0] = createTextLabel("orbitTrapCenter", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			// textFields = new JTextField[2];
			// textFields[0] = createTextField(String.valueOf(fractalElement.getOrbitTrapConfigElement().getCenter().getX()), 100, GUIFactory.DEFAULT_HEIGHT);
			// centerFieldListener = new CenterListener(fractalElement.getOrbitTrapConfigElement().getCenterElement(), textFields);
			// textFields[0].addActionListener(centerFieldListener);
			// textFields[0].addFocusListener(centerFieldListener);
			// // textFields[0].setColumns(8);
			// textFields[0].setCaretPosition(0);
			// textFields[1] = createTextField(String.valueOf(fractalElement.getOrbitTrapConfigElement().getCenter().getY()), 100, GUIFactory.DEFAULT_HEIGHT);
			// textFields[1].addActionListener(centerFieldListener);
			// textFields[1].addFocusListener(centerFieldListener);
			// // textFields[1].setColumns(8);
			// textFields[1].setCaretPosition(0);
			// Box[] panels = new Box[1];
			// panels[0] = createHorizontalBox(false);
			// panels[0].add(labels[0]);
			// panels[0].add(createSpace());
			// panels[0].add(textFields[0]);
			// panels[0].add(textFields[1]);
			// panels[0].add(Box.createHorizontalGlue());
			// Box orbitTrapElementPanel = createVerticalBox(false);
			// orbitTrapElementPanel.add(Box.createVerticalStrut(8));
			// orbitTrapElementPanel.add(panels[0]);
			// orbitTrapElementPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			final Box outcolouringFormulasPanel4 = createHorizontalBox(false);
			outcolouringFormulasPanel4.add(Box.createHorizontalGlue());
			outcolouringFormulasPanel4.add(selectOutcolouringFormulaButton);
			outcolouringFormulasPanel4.add(createSpace());
			outcolouringFormulasPanel4.add(appendOutcolouringFormulaButton);
			outcolouringFormulasPanel4.add(createSpace());
			outcolouringFormulasPanel4.add(removeOutcolouringFormulaButton);
			outcolouringFormulasPanel4.add(createSpace());
			outcolouringFormulasPanel4.add(moveUpOutcolouringFormulaButton);
			outcolouringFormulasPanel4.add(createSpace());
			outcolouringFormulasPanel4.add(moveDownOutcolouringFormulaButton);
			outcolouringFormulasPanel4.add(createSpace());
			outcolouringFormulasPanel4.add(cloneOutcolouringFormulaButton);
			outcolouringFormulasPanel4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			final JLabel outcolouringFormulasLabel = createTextLabel("noOutcolouringFormula", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			outcolouringFormulasLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			final Box outcolouringFormulasPanel3 = createHorizontalBox(false);
			outcolouringFormulasPanel3.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			outcolouringFormulasPanel3.add(outcolouringFormulasLabel);
			outcolouringFormulasPanel3.add(Box.createHorizontalGlue());
			final JPanel outcolouringFormulasPanel2 = createPanel(new BorderLayout(), true);
			outcolouringFormulasPanel2.setName(createOutcolouringFormulasPanelName());
			outcolouringFormulasPanel2.add(outcolouringFormulasPanel4, BorderLayout.NORTH);
			outcolouringFormulasPanel2.add(outcolouringFormulasPanel, BorderLayout.CENTER);
			outcolouringFormulasPanel2.add(outcolouringFormulasPanel3, BorderLayout.SOUTH);
			outcolouringFormulasPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			final Box incolouringFormulasPanel4 = createHorizontalBox(false);
			incolouringFormulasPanel4.add(Box.createHorizontalGlue());
			incolouringFormulasPanel4.add(selectIncolouringFormulaButton);
			incolouringFormulasPanel4.add(createSpace());
			incolouringFormulasPanel4.add(appendIncolouringFormulaButton);
			incolouringFormulasPanel4.add(createSpace());
			incolouringFormulasPanel4.add(removeIncolouringFormulaButton);
			incolouringFormulasPanel4.add(createSpace());
			incolouringFormulasPanel4.add(moveUpIncolouringFormulaButton);
			incolouringFormulasPanel4.add(createSpace());
			incolouringFormulasPanel4.add(moveDownIncolouringFormulaButton);
			incolouringFormulasPanel4.add(createSpace());
			incolouringFormulasPanel4.add(cloneIncolouringFormulaButton);
			incolouringFormulasPanel4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			final JLabel incolouringFormulasLabel = createTextLabel("noIncolouringFormula", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			incolouringFormulasLabel.setPreferredSize(new Dimension(200, GUIFactory.DEFAULT_HEIGHT));
			final Box incolouringFormulasPanel3 = createHorizontalBox(false);
			incolouringFormulasPanel3.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			incolouringFormulasPanel3.add(incolouringFormulasLabel);
			incolouringFormulasPanel3.add(Box.createHorizontalGlue());
			final JPanel incolouringFormulasPanel2 = createPanel(new BorderLayout(), true);
			incolouringFormulasPanel2.setName(createIncolouringFormulasPanelName());
			incolouringFormulasPanel2.add(incolouringFormulasPanel4, BorderLayout.NORTH);
			incolouringFormulasPanel2.add(incolouringFormulasPanel, BorderLayout.CENTER);
			incolouringFormulasPanel2.add(incolouringFormulasPanel3, BorderLayout.SOUTH);
			incolouringFormulasPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			renderingFormulaPanel = new RenderingFormulaPanel(fractalElement, fractalElement.getRenderingFormulaConfigElement());
			transformingFormulaPanel = new TransformingFormulaPanel(fractalElement, fractalElement.getTransformingFormulaConfigElement());
			processingFormulaPanel = new ProcessingFormulaPanel(fractalElement, fractalElement.getProcessingFormulaConfigElement());
			orbitTrapPanel = new OrbitTrapPanel(fractalElement, fractalElement.getOrbitTrapConfigElement());
			final Box outcolouringFormulasPanel5 = createHorizontalBox(false);
			outcolouringFormulasPanel5.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			outcolouringFormulasPanel5.add(outcolouringFormulasLabel2);
			outcolouringFormulasPanel5.add(createSpace());
			outcolouringFormulasPanel5.add(editOutcolouringFormulasButton);
			outcolouringFormulasPanel5.add(Box.createHorizontalGlue());
			final Box incolouringFormulasPanel5 = createHorizontalBox(false);
			incolouringFormulasPanel5.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			incolouringFormulasPanel5.add(incolouringFormulasLabel2);
			incolouringFormulasPanel5.add(createSpace());
			incolouringFormulasPanel5.add(editIncolouringFormulasButton);
			incolouringFormulasPanel5.add(Box.createHorizontalGlue());
			final Box subpanelContainer = createVerticalBox(false);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(renderingFormulaPanel);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(transformingFormulaPanel);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(processingFormulaPanel);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(orbitTrapPanel);
			// subpanelContainer.add(orbitTrapElementPanel);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(incolouringFormulasPanel5);
			subpanelContainer.add(Box.createVerticalStrut(8));
			subpanelContainer.add(outcolouringFormulasPanel5);
			subpanelContainer.add(Box.createVerticalStrut(8));
			// Box formulasPanel = createHorizontalBox(false);
			// formulasPanel.add(Box.createHorizontalGlue());
			// formulasPanel.add(appendIncolouringFormulaButton);
			// formulasPanel.add(removeIncolouringFormulaButton);
			// formulasPanel.add(appendOutcolouringFormulaButton);
			// formulasPanel.add(removeOutcolouringFormulaButton);
			// formulasPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
			setLayout(new BorderLayout());
			add(subpanelContainer, BorderLayout.CENTER);
			// add(formulasPanel, BorderLayout.SOUTH);
			editIncolouringFormulasButton.setText(createEditIncolouringFormulasText(fractalElement));
			editOutcolouringFormulasButton.setText(createEditOutcolouringFormulasText(fractalElement));
			incolouringFormulasPanel3.setVisible(fractalElement.getIncolouringFormulaConfigElementCount() == 0);
			outcolouringFormulasPanel3.setVisible(fractalElement.getOutcolouringFormulaConfigElementCount() == 0);
			for (int i = 0; i < fractalElement.getIncolouringFormulaConfigElementCount(); i++) {
				final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
				final IncolouringFormulaPanel formulaPanel = new IncolouringFormulaPanel(fractalElement, formulaElement);
				incolouringFormulasPanel.add(formulaPanel);
			}
			for (int i = 0; i < fractalElement.getOutcolouringFormulaConfigElementCount(); i++) {
				final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
				final OutcolouringFormulaPanel formulaPanel = new OutcolouringFormulaPanel(fractalElement, formulaElement);
				outcolouringFormulasPanel.add(formulaPanel);
			}
			for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
				incolouringFormulasPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
				// ((JComponent) incolouringFormulasPanel.getComponent(i)).setOpaque(i % 2 == 0);
			}
			for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
				outcolouringFormulasPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
				// ((JComponent) outcolouringFormulasPanel.getComponent(i)).setOpaque(i % 2 == 0);
			}
			incolouringFormulasListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ListConfigElement.ELEMENT_ADDED: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							incolouringFormulasPanel.add(new IncolouringFormulaPanel(fractalElement, (IncolouringFormulaConfigElement) e.getParams()[0]));
							incolouringFormulasPanel3.setVisible(fractalElement.getIncolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_AFTER: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							incolouringFormulasPanel.add(new IncolouringFormulaPanel(fractalElement, (IncolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
							incolouringFormulasPanel3.setVisible(fractalElement.getIncolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							incolouringFormulasPanel.add(new IncolouringFormulaPanel(fractalElement, (IncolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							incolouringFormulasPanel3.setVisible(fractalElement.getIncolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_REMOVED: {
							IncolouringFormulaPanel panel = (IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							incolouringFormulasPanel.remove(panel);
							panel.dispose();
							incolouringFormulasPanel3.setVisible(fractalElement.getIncolouringFormulaConfigElementCount() == 0);
							// viewContext.resize();
							viewContext.restoreComponent(incolouringFormulasPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_UP: {
							IncolouringFormulaPanel panel = (IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							incolouringFormulasPanel.remove(panel);
							incolouringFormulasPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_DOWN: {
							IncolouringFormulaPanel panel = (IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							incolouringFormulasPanel.remove(panel);
							incolouringFormulasPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
							break;
						}
						case ListConfigElement.ELEMENT_CHANGED: {
							IncolouringFormulaPanel panel = (IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							incolouringFormulasPanel.remove(panel);
							panel.dispose();
							incolouringFormulasPanel.add(new IncolouringFormulaPanel(fractalElement, (IncolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							break;
						}
						default: {
							break;
						}
					}
					for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
						incolouringFormulasPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
						// ((JComponent) incolouringFormulasPanel.getComponent(i)).setOpaque(i % 2 == 0);
					}
					editIncolouringFormulasButton.setText(createEditIncolouringFormulasText(fractalElement));
				}
			};
			outcolouringFormulasListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ListConfigElement.ELEMENT_ADDED: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							outcolouringFormulasPanel.add(new OutcolouringFormulaPanel(fractalElement, (OutcolouringFormulaConfigElement) e.getParams()[0]));
							outcolouringFormulasPanel3.setVisible(fractalElement.getOutcolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_AFTER: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							outcolouringFormulasPanel.add(new OutcolouringFormulaPanel(fractalElement, (OutcolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue() + 1);
							outcolouringFormulasPanel3.setVisible(fractalElement.getOutcolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
							viewContext.resize(GUIFactory.DEFAULT_HEIGHT);
							outcolouringFormulasPanel.add(new OutcolouringFormulaPanel(fractalElement, (OutcolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							outcolouringFormulasPanel3.setVisible(fractalElement.getOutcolouringFormulaConfigElementCount() == 0);
							break;
						}
						case ListConfigElement.ELEMENT_REMOVED: {
							OutcolouringFormulaPanel panel = (OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							outcolouringFormulasPanel.remove(panel);
							panel.dispose();
							outcolouringFormulasPanel3.setVisible(fractalElement.getOutcolouringFormulaConfigElementCount() == 0);
							// viewContext.resize();
							viewContext.restoreComponent(outcolouringFormulasPanel2);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_UP: {
							OutcolouringFormulaPanel panel = (OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							outcolouringFormulasPanel.remove(panel);
							outcolouringFormulasPanel.add(panel, ((Integer) e.getParams()[1]).intValue() - 1);
							break;
						}
						case ListConfigElement.ELEMENT_MOVED_DOWN: {
							OutcolouringFormulaPanel panel = (OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							outcolouringFormulasPanel.remove(panel);
							outcolouringFormulasPanel.add(panel, ((Integer) e.getParams()[1]).intValue() + 1);
							break;
						}
						case ListConfigElement.ELEMENT_CHANGED: {
							OutcolouringFormulaPanel panel = (OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(((Integer) e.getParams()[1]).intValue());
							outcolouringFormulasPanel.remove(panel);
							panel.dispose();
							outcolouringFormulasPanel.add(new OutcolouringFormulaPanel(fractalElement, (OutcolouringFormulaConfigElement) e.getParams()[0]), ((Integer) e.getParams()[1]).intValue());
							break;
						}
						default: {
							break;
						}
					}
					for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
						outcolouringFormulasPanel.getComponent(i).setBackground((i % 2 == 0) ? evenColor : oddColor);
						// ((JComponent) outcolouringFormulasPanel.getComponent(i)).setOpaque(i % 2 == 0);
					}
					editOutcolouringFormulasButton.setText(createEditOutcolouringFormulasText(fractalElement));
				}
			};
			renderingFormulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							subpanelContainer.remove(renderingFormulaPanel);
							renderingFormulaPanel = new RenderingFormulaPanel(fractalElement, fractalElement.getRenderingFormulaConfigElement());
							subpanelContainer.add(renderingFormulaPanel);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			transformingFormulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							subpanelContainer.remove(transformingFormulaPanel);
							transformingFormulaPanel = new TransformingFormulaPanel(fractalElement, fractalElement.getTransformingFormulaConfigElement());
							subpanelContainer.add(transformingFormulaPanel);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			processingFormulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							subpanelContainer.remove(processingFormulaPanel);
							processingFormulaPanel = new ProcessingFormulaPanel(fractalElement, fractalElement.getProcessingFormulaConfigElement());
							subpanelContainer.add(processingFormulaPanel);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			orbitTrapListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ValueConfigElement.VALUE_CHANGED: {
							// textFields[0].removeActionListener(centerFieldListener);
							// textFields[0].removeFocusListener(centerFieldListener);
							// textFields[1].removeActionListener(centerFieldListener);
							// textFields[1].removeFocusListener(centerFieldListener);
							subpanelContainer.remove(orbitTrapPanel);
							orbitTrapPanel = new OrbitTrapPanel(fractalElement, fractalElement.getOrbitTrapConfigElement());
							subpanelContainer.add(orbitTrapPanel);
							// textFields[0].setText(String.valueOf(fractalElement.getOrbitTrapConfigElement().getCenter().getX()));
							// textFields[1].setText(String.valueOf(fractalElement.getOrbitTrapConfigElement().getCenter().getY()));
							// textFields[0].setCaretPosition(0);
							// textFields[1].setCaretPosition(0);
							// centerFieldListener = new CenterListener(fractalElement.getOrbitTrapConfigElement().getCenterElement(), textFields);
							// textFields[0].addActionListener(centerFieldListener);
							// textFields[0].addFocusListener(centerFieldListener);
							// textFields[1].addActionListener(centerFieldListener);
							// textFields[1].addFocusListener(centerFieldListener);
							break;
						}
						default: {
							break;
						}
					}
				}
			};
			// centerListener = new ValueChangeListener() {
			// public void valueChanged(ValueChangeEvent e) {
			// switch (e.getEventType()) {
			// case ValueConfigElementEvents.VALUE_CHANGED: {
			// textFields[0].removeActionListener(centerFieldListener);
			// textFields[0].removeFocusListener(centerFieldListener);
			// textFields[1].removeActionListener(centerFieldListener);
			// textFields[1].removeFocusListener(centerFieldListener);
			// textFields[0].setText(String.valueOf(((DoubleVector2D) e.getParams()[0]).getX()));
			// textFields[1].setText(String.valueOf(((DoubleVector2D) e.getParams()[0]).getY()));
			// textFields[0].setCaretPosition(0);
			// textFields[1].setCaretPosition(0);
			// textFields[0].addActionListener(centerFieldListener);
			// textFields[0].addFocusListener(centerFieldListener);
			// textFields[1].addActionListener(centerFieldListener);
			// textFields[1].addFocusListener(centerFieldListener);
			// break;
			// }
			// default: {
			// break;
			// }
			// }
			// }
			// };
			fractalElement.getIncolouringFormulaListElement().addChangeListener(incolouringFormulasListener);
			fractalElement.getOutcolouringFormulaListElement().addChangeListener(outcolouringFormulasListener);
			fractalElement.getRenderingFormulaSingleElement().addChangeListener(renderingFormulaListener);
			fractalElement.getTransformingFormulaSingleElement().addChangeListener(transformingFormulaListener);
			fractalElement.getProcessingFormulaConfigElement().addChangeListener(processingFormulaListener);
			fractalElement.getOrbitTrapConfigElement().addChangeListener(orbitTrapListener);
			// fractalElement.getOrbitTrapConfigElement().getCenterElement().addChangeListener(centerListener);
			appendIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getIncolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.insertIncolouringFormulaConfigElementBefore(i, new IncolouringFormulaConfigElement());
								return;
							}
						}
						fractalElement.appendIncolouringFormulaConfigElement(new IncolouringFormulaConfigElement());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			removeIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getIncolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.removeIncolouringFormulaConfigElement(i);
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
			// insertIncolouringFormulaAfterButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = 0; i < fractalElement.getIncolouringFormulaConfigElementCount(); i++) {
			// IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
			// if (formulaElement.getUserData() != null) {
			// fractalElement.insertIncolouringFormulaConfigElementAfter(i, new IncolouringFormulaConfigElement());
			// }
			// }
			// }
			// });
			// insertIncolouringFormulaBeforeButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = fractalElement.getIncolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
			// IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
			// if (formulaElement.getUserData() != null) {
			// fractalElement.insertIncolouringFormulaConfigElementBefore(i, new IncolouringFormulaConfigElement());
			// }
			// }
			// }
			// });
			appendOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getOutcolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.insertOutcolouringFormulaConfigElementBefore(i, new OutcolouringFormulaConfigElement());
								return;
							}
						}
						fractalElement.appendOutcolouringFormulaConfigElement(new OutcolouringFormulaConfigElement());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			});
			removeOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getOutcolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.removeOutcolouringFormulaConfigElement(i);
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
			// insertOutcolouringFormulaAfterButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = 0; i < fractalElement.getOutcolouringFormulaConfigElementCount(); i++) {
			// OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
			// if (formulaElement.getUserData() != null) {
			// fractalElement.insertOutcolouringFormulaConfigElementAfter(i, new OutcolouringFormulaConfigElement());
			// }
			// }
			// }
			// });
			// insertOutcolouringFormulaBeforeButton.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// for (int i = fractalElement.getOutcolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
			// OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
			// if (formulaElement.getUserData() != null) {
			// fractalElement.insertOutcolouringFormulaConfigElementBefore(i, new OutcolouringFormulaConfigElement());
			// }
			// }
			// }
			// });
			selectIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					boolean allSelected = true;
					for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
						if (!((IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(i)).isSelected()) {
							allSelected = false;
						}
					}
					if (allSelected) {
						for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
							((IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(i)).setSelected(false);
						}
					}
					else {
						for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
							((IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(i)).setSelected(true);
						}
					}
				}
			});
			selectOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					boolean allSelected = true;
					for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
						if (!((OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(i)).isSelected()) {
							allSelected = false;
						}
					}
					if (allSelected) {
						for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
							((OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(i)).setSelected(false);
						}
					}
					else {
						for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
							((OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(i)).setSelected(true);
						}
					}
				}
			});
			moveUpIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = 0; i < fractalElement.getIncolouringFormulaConfigElementCount(); i++) {
							final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.moveUpIncolouringFormulaConfigElement(i);
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
			moveDownIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getIncolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.moveDownIncolouringFormulaConfigElement(i);
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
			cloneIncolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getIncolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final IncolouringFormulaConfigElement formulaElement = fractalElement.getIncolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.insertIncolouringFormulaConfigElementAfter(i, formulaElement.clone());
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
			moveUpOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = 0; i < fractalElement.getOutcolouringFormulaConfigElementCount(); i++) {
							final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.moveUpOutcolouringFormulaConfigElement(i);
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
			moveDownOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getOutcolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.moveDownOutcolouringFormulaConfigElement(i);
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
			cloneOutcolouringFormulaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						fractalElement.getContext().updateTimestamp();
						for (int i = fractalElement.getOutcolouringFormulaConfigElementCount() - 1; i >= 0; i--) {
							final OutcolouringFormulaConfigElement formulaElement = fractalElement.getOutcolouringFormulaConfigElement(i);
							if (formulaElement.getUserData() != null) {
								fractalElement.insertOutcolouringFormulaConfigElementAfter(i, formulaElement.clone());
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
			editIncolouringFormulasButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(incolouringFormulasPanel2);
				}
			});
			editOutcolouringFormulasButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					viewContext.setComponent(outcolouringFormulasPanel2);
				}
			});
		}

		public void dispose() {
			fractalElement.getIncolouringFormulaListElement().removeChangeListener(incolouringFormulasListener);
			fractalElement.getOutcolouringFormulaListElement().removeChangeListener(outcolouringFormulasListener);
			fractalElement.getRenderingFormulaSingleElement().removeChangeListener(renderingFormulaListener);
			fractalElement.getTransformingFormulaSingleElement().removeChangeListener(transformingFormulaListener);
			fractalElement.getProcessingFormulaConfigElement().removeChangeListener(processingFormulaListener);
			fractalElement.getOrbitTrapConfigElement().removeChangeListener(orbitTrapListener);
			// fractalElement.getOrbitTrapConfigElement().getCenterElement().removeChangeListener(centerListener);
			for (int i = 0; i < outcolouringFormulasPanel.getComponentCount(); i++) {
				final OutcolouringFormulaPanel formulaPanel = (OutcolouringFormulaPanel) outcolouringFormulasPanel.getComponent(i);
				formulaPanel.dispose();
			}
			for (int i = 0; i < incolouringFormulasPanel.getComponentCount(); i++) {
				final IncolouringFormulaPanel formulaPanel = (IncolouringFormulaPanel) incolouringFormulasPanel.getComponent(i);
				formulaPanel.dispose();
			}
			renderingFormulaPanel.dispose();
			transformingFormulaPanel.dispose();
			processingFormulaPanel.dispose();
			orbitTrapPanel.dispose();
		}

		private String createEditIncolouringFormulasText(final MandelbrotFractalConfigElement fractalElement) {
			if (fractalElement.getIncolouringFormulaConfigElementCount() == 1) {
				return fractalElement.getIncolouringFormulaConfigElementCount() + " " + MandelbrotSwingResources.getInstance().getString("label.incolouringFormula");
			}
			else {
				return fractalElement.getIncolouringFormulaConfigElementCount() + " " + MandelbrotSwingResources.getInstance().getString("label.incolouringFormulas");
			}
		}

		private String createEditOutcolouringFormulasText(final MandelbrotFractalConfigElement fractalElement) {
			if (fractalElement.getOutcolouringFormulaConfigElementCount() == 1) {
				return fractalElement.getOutcolouringFormulaConfigElementCount() + " " + MandelbrotSwingResources.getInstance().getString("label.outcolouringFormula");
			}
			else {
				return fractalElement.getOutcolouringFormulaConfigElementCount() + " " + MandelbrotSwingResources.getInstance().getString("label.outcolouringFormulas");
			}
		}

		private String createIncolouringFormulasPanelName() {
			return MandelbrotSwingResources.getInstance().getString("name.incolouringFormulas");
		}

		private String createOutcolouringFormulasPanelName() {
			return MandelbrotSwingResources.getInstance().getString("name.outcolouringFormulas");
		}
		// private class CenterListener implements ActionListener, FocusListener {
		// private final ComplexElement centerElement;
		// private final JTextField[] textFields;
		//
		// public CenterListener(final ComplexElement centerElement, final JTextField[] textFields) {
		// this.centerElement = centerElement;
		// this.textFields = textFields;
		// }
		//
		// /**
		// * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		// */
		// public void actionPerformed(final ActionEvent e) {
		// final DoubleVector2D c = centerElement.getValue();
		// double r = c.getX();
		// double i = c.getY();
		// try {
		// final String text = textFields[0].getText();
		// r = Double.parseDouble(text);
		// }
		// catch (final NumberFormatException nfe) {
		// textFields[0].setText(String.valueOf(c.getX()));
		// textFields[0].setCaretPosition(0);
		// }
		// try {
		// final String text = textFields[1].getText();
		// i = Double.parseDouble(text);
		// }
		// catch (final NumberFormatException nfe) {
		// textFields[1].setText(String.valueOf(c.getY()));
		// textFields[1].setCaretPosition(0);
		// }
		// final DoubleVector2D value = new DoubleVector2D(r, i);
		// if (!centerElement.getValue().equals(value)) {
		// centerElement.setValue(value);
		// }
		// context.refresh();
		// }
		//
		// /**
		// * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		// */
		// public void focusGained(final FocusEvent e) {
		// }
		//
		// /**
		// * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		// */
		// public void focusLost(final FocusEvent e) {
		// final DoubleVector2D c = centerElement.getValue();
		// double r = c.getX();
		// double i = c.getY();
		// try {
		// final String text = textFields[0].getText();
		// r = Double.parseDouble(text);
		// }
		// catch (final NumberFormatException nfe) {
		// textFields[0].setText(String.valueOf(c.getX()));
		// textFields[0].setCaretPosition(0);
		// }
		// try {
		// final String text = textFields[1].getText();
		// i = Double.parseDouble(text);
		// }
		// catch (final NumberFormatException nfe) {
		// textFields[1].setText(String.valueOf(c.getY()));
		// textFields[1].setCaretPosition(0);
		// }
		// final DoubleVector2D value = new DoubleVector2D(r, i);
		// if (!centerElement.getValue().equals(value)) {
		// centerElement.setValue(value);
		// }
		// context.refresh();
		// }
		// }
	}

	private class RenderingFormulaPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ViewPanel configView;
		private final ValueChangeListener formulaListener;
		private final RenderingFormulaConfigElement formulaElement;

		/**
		 * @param fractalElement
		 * @param formulaElement
		 */
		public RenderingFormulaPanel(final MandelbrotFractalConfigElement fractalElement, final RenderingFormulaConfigElement formulaElement) {
			this.formulaElement = formulaElement;
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(MandelbrotRegistry.getInstance().getRenderingFormulaRegistry(), true);
			final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel label = createTextLabel("renderingFormula", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(editOptionsButton);
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			editOptionsButton.setEnabled((formulaElement != null) && (formulaElement.getReference() != null));
			if (formulaElement.getReference() != null) {
				model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						ConfigurableExtension<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig> extension = (ConfigurableExtension<RenderingFormulaExtensionRuntime<?>, RenderingFormulaExtensionConfig>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						formulaElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							formulaElement.setReference(null);
						}
						else {
							formulaElement.setReference(extension.createConfigurableExtensionReference());
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
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(formulaElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
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
			formulaElement.getExtensionElement().addChangeListener(formulaListener);
			extensionComboBox.addActionListener(comboListener);
			editOptionsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (formulaElement.getReference() != null) {
						if (configView == null) {
							try {
								final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(formulaElement.getReference().getExtensionId());
								configView = extension.createExtensionRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
							catch (final ExtensionException x) {
								configView = new DefaultViewRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
						}
						configView.setName(createRenderingFormulaPanelName());
						viewContext.setComponent(configView);
					}
				}
			});
		}

		public void dispose() {
			formulaElement.getExtensionElement().removeChangeListener(formulaListener);
			if (configView != null) {
				viewContext.removeComponent(configView);
				configView.dispose();
				configView = null;
			}
		}

		private String createRenderingFormulaPanelName() {
			return MandelbrotSwingResources.getInstance().getString("name.renderingFormula");
		}
	}

	private class TransformingFormulaPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ViewPanel configView;
		private final TransformingFormulaConfigElement formulaElement;
		private final ValueChangeListener formulaListener;

		/**
		 * @param fractalElement
		 * @param formulaElement
		 */
		public TransformingFormulaPanel(final MandelbrotFractalConfigElement fractalElement, final TransformingFormulaConfigElement formulaElement) {
			this.formulaElement = formulaElement;
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(MandelbrotRegistry.getInstance().getTransformingFormulaRegistry(), true);
			final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel label = createTextLabel("transformingFormula", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(editOptionsButton);
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			editOptionsButton.setEnabled((formulaElement != null) && (formulaElement.getReference() != null));
			if (formulaElement.getReference() != null) {
				model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						ConfigurableExtension<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig> extension = (ConfigurableExtension<TransformingFormulaExtensionRuntime<?>, TransformingFormulaExtensionConfig>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						formulaElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							formulaElement.setReference(null);
						}
						else {
							formulaElement.setReference(extension.createConfigurableExtensionReference());
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
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(formulaElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
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
			formulaElement.getExtensionElement().addChangeListener(formulaListener);
			extensionComboBox.addActionListener(comboListener);
			editOptionsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (formulaElement.getReference() != null) {
						if (configView == null) {
							try {
								final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(formulaElement.getReference().getExtensionId());
								configView = extension.createExtensionRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
							catch (final ExtensionException x) {
								configView = new DefaultViewRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
						}
						configView.setName(createTransformingFormulaPanelName());
						viewContext.setComponent(configView);
					}
				}
			});
		}

		public void dispose() {
			formulaElement.getExtensionElement().removeChangeListener(formulaListener);
			if (configView != null) {
				viewContext.removeComponent(configView);
				configView.dispose();
				configView = null;
			}
		}

		private String createTransformingFormulaPanelName() {
			return MandelbrotSwingResources.getInstance().getString("name.transformingFormula");
		}
	}

	private class ProcessingFormulaPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final ProcessingFormulaConfigElement formulaElement;
		private final ValueChangeListener formulaListener;

		/**
		 * @param fractalElement
		 * @param formulaElement
		 */
		public ProcessingFormulaPanel(final MandelbrotFractalConfigElement fractalElement, final ProcessingFormulaConfigElement formulaElement) {
			this.formulaElement = formulaElement;
			final ExtensionComboBoxModel model = new ExtensionComboBoxModel(MandelbrotRegistry.getInstance().getProcessingFormulaRegistry(), true);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel label = createTextLabel("processingFormula", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			if (formulaElement.getReference() != null) {
				model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						Extension<ProcessingFormulaExtensionRuntime> extension = (Extension<ProcessingFormulaExtensionRuntime>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						formulaElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							formulaElement.setReference(null);
						}
						else {
							formulaElement.setReference(extension.getExtensionReference());
						}
						context.startRenderers();
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
							}
							else {
								model.setSelectedItem(0);
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
			formulaElement.getExtensionElement().addChangeListener(formulaListener);
			extensionComboBox.addActionListener(comboListener);
		}

		public void dispose() {
			formulaElement.getExtensionElement().removeChangeListener(formulaListener);
		}
	}

	private class OrbitTrapPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ViewPanel configView;
		private final OrbitTrapConfigElement orbitTrapElement;
		private final ValueChangeListener orbitTrapListener;

		/**
		 * @param fractalElement
		 * @param orbitTrapElement
		 */
		public OrbitTrapPanel(final MandelbrotFractalConfigElement fractalElement, final OrbitTrapConfigElement orbitTrapElement) {
			this.orbitTrapElement = orbitTrapElement;
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(MandelbrotRegistry.getInstance().getOrbitTrapRegistry(), true);
			final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel label = createTextLabel("orbitTrap", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(editOptionsButton);
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			editOptionsButton.setEnabled((orbitTrapElement != null) && (orbitTrapElement.getReference() != null));
			if (orbitTrapElement.getReference() != null) {
				model.setSelectedItemByExtensionId(orbitTrapElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						ConfigurableExtension<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig> extension = (ConfigurableExtension<OrbitTrapExtensionRuntime<?>, OrbitTrapExtensionConfig>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						orbitTrapElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							orbitTrapElement.setReference(null);
						}
						else {
							orbitTrapElement.setReference(extension.createConfigurableExtensionReference());
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
			orbitTrapListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(orbitTrapElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (orbitTrapElement.getReference() != null) {
								model.setSelectedItemByExtensionId(orbitTrapElement.getReference().getExtensionId());
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
			orbitTrapElement.getExtensionElement().addChangeListener(orbitTrapListener);
			extensionComboBox.addActionListener(comboListener);
			editOptionsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (orbitTrapElement.getReference() != null) {
						if (configView == null) {
							try {
								final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(orbitTrapElement.getReference().getExtensionId());
								configView = extension.createExtensionRuntime().createView(orbitTrapElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
							catch (final ExtensionException x) {
								configView = new DefaultViewRuntime().createView(orbitTrapElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
						}
						configView.setName(createOrbitTrapPanelName());
						viewContext.setComponent(configView);
					}
				}
			});
		}

		public void dispose() {
			orbitTrapElement.getExtensionElement().removeChangeListener(orbitTrapListener);
			if (configView != null) {
				viewContext.removeComponent(configView);
				configView.dispose();
				configView = null;
			}
		}

		private String createOrbitTrapPanelName() {
			return MandelbrotSwingResources.getInstance().getString("name.orbitTrap");
		}
	}

	private class IncolouringFormulaPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JCheckBox selectedCheckBox = createCheckBox();
		private ViewPanel configView;
		private final IncolouringFormulaConfigElement formulaElement;
		private final ValueChangeListener formulaListener;
		private final ValueChangeListener lockedListener;
		private final ValueChangeListener enabledListener;
		private final ValueChangeListener labelListener;
		private final ValueChangeListener autoIterationsListener;
		private final ValueChangeListener iterationsListener;
		private final ValueChangeListener opacityListener;

		/**
		 * @param fractalElement
		 * @param formulaElement
		 */
		public IncolouringFormulaPanel(final MandelbrotFractalConfigElement fractalElement, final IncolouringFormulaConfigElement formulaElement) {
			this.formulaElement = formulaElement;
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(MandelbrotRegistry.getInstance().getIncolouringFormulaRegistry(), true);
			final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
			final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JCheckBox enabledCheckBox = createIconCheckBox("enabled", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JCheckBox autoIterationsCheckBox = createIconCheckBox("autoIterations", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JSpinner opacitySpinner = createSpinner(0, 100, 1);
			final JSpinner iterationsSpinner = createSpinner(0, 5000, 1);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
			final JTextField label = createTextField(formulaElement.getLabel(), 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			formulaPanel.add(createSpace());
			formulaPanel.add(selectedCheckBox);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(opacitySpinner);
			formulaPanel.add(createSpace());
			formulaPanel.add(lockedCheckBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(enabledCheckBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(iterationsSpinner);
			formulaPanel.add(createSpace());
			formulaPanel.add(autoIterationsCheckBox);
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(editOptionsButton);
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			lockedCheckBox.setSelected(formulaElement.isLocked());
			enabledCheckBox.setSelected(formulaElement.isEnabled());
			editOptionsButton.setEnabled((formulaElement != null) && (formulaElement.getReference() != null));
			opacitySpinner.setValue(formulaElement.getOpacity().intValue());
			opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
			iterationsSpinner.setValue(formulaElement.getIterations());
			iterationsSpinner.setToolTipText(createIterationsTooltip(iterationsSpinner));
			autoIterationsCheckBox.setSelected(formulaElement.getAutoIterations());
			iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
			if (formulaElement.getReference() != null) {
				model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						ConfigurableExtension<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig> extension = (ConfigurableExtension<IncolouringFormulaExtensionRuntime<?>, IncolouringFormulaExtensionConfig>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						formulaElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							formulaElement.setReference(null);
						}
						else {
							formulaElement.setReference(extension.createConfigurableExtensionReference());
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
					if (formulaElement.getReference() != null) {
						if (configView == null) {
							try {
								final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(formulaElement.getReference().getExtensionId());
								configView = extension.createExtensionRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
							catch (final ExtensionException x) {
								configView = new DefaultViewRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
						}
						if (configView != null) {
							configView.setName(createIncolouringFormulaPanelName(formulaElement));
						}
						viewContext.setComponent(configView);
					}
				}
			});
			label.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// formulaElement.getLabelElement().removeChangeListener(labelListener);
					formulaElement.getContext().updateTimestamp();
					formulaElement.setLabel(label.getText());
					if (configView != null) {
						configView.setName(createIncolouringFormulaPanelName(formulaElement));
					}
					// formulaElement.getLabelElement().addChangeListener(labelListener);
				}
			});
			selectedCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (selectedCheckBox.isSelected()) {
						formulaElement.setUserData(Boolean.TRUE);
					}
					else {
						formulaElement.setUserData(null);
					}
				}
			});
			final ActionListener autoIterationsActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setAutoIterations(autoIterationsCheckBox.isSelected());
						iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			autoIterationsCheckBox.addActionListener(autoIterationsActionListener);
			final ChangeListener iterationsChangeListener = new ChangeListener() {
				@Override
				public void stateChanged(final ChangeEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setIterations(((Number) iterationsSpinner.getValue()).intValue());
						iterationsSpinner.setToolTipText(createIterationsTooltip(iterationsSpinner));
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			iterationsSpinner.addChangeListener(iterationsChangeListener);
			final ChangeListener opacityChangeListener = new ChangeListener() {
				@Override
				public void stateChanged(final ChangeEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setOpacity(((Number) opacitySpinner.getValue()).intValue());
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
			final ActionListener lockedActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setLocked(lockedCheckBox.isSelected());
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
						formulaElement.getContext().updateTimestamp();
						formulaElement.setEnabled(enabledCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			enabledCheckBox.addActionListener(enabledActionListener);
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(formulaElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
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
			lockedListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					lockedCheckBox.removeActionListener(lockedActionListener);
					lockedCheckBox.setSelected(formulaElement.isLocked());
					lockedCheckBox.addActionListener(lockedActionListener);
				}
			};
			enabledListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					enabledCheckBox.removeActionListener(enabledActionListener);
					enabledCheckBox.setSelected(formulaElement.isEnabled());
					enabledCheckBox.addActionListener(enabledActionListener);
				}
			};
			labelListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					label.setText(formulaElement.getLabel());
					if (configView != null) {
						configView.setName(createIncolouringFormulaPanelName(formulaElement));
					}
				}
			};
			autoIterationsListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					autoIterationsCheckBox.removeActionListener(autoIterationsActionListener);
					iterationsSpinner.removeChangeListener(iterationsChangeListener);
					autoIterationsCheckBox.setSelected(formulaElement.getAutoIterations());
					iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
					autoIterationsCheckBox.addActionListener(autoIterationsActionListener);
					iterationsSpinner.addChangeListener(iterationsChangeListener);
				}
			};
			iterationsListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					iterationsSpinner.removeChangeListener(iterationsChangeListener);
					iterationsSpinner.setValue(formulaElement.getIterations());
					iterationsSpinner.addChangeListener(iterationsChangeListener);
				}
			};
			opacityListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					opacitySpinner.removeChangeListener(opacityChangeListener);
					opacitySpinner.setValue(formulaElement.getOpacity().intValue());
					opacitySpinner.addChangeListener(opacityChangeListener);
				}
			};
			formulaElement.getLabelElement().addChangeListener(labelListener);
			formulaElement.getLockedElement().addChangeListener(lockedListener);
			formulaElement.getEnabledElement().addChangeListener(enabledListener);
			formulaElement.getExtensionElement().addChangeListener(formulaListener);
			formulaElement.getAutoIterationsElement().addChangeListener(autoIterationsListener);
			formulaElement.getIterationsElement().addChangeListener(iterationsListener);
			formulaElement.getOpacityElement().addChangeListener(opacityListener);
		}

		public void dispose() {
			formulaElement.getLabelElement().removeChangeListener(labelListener);
			formulaElement.getLockedElement().removeChangeListener(lockedListener);
			formulaElement.getEnabledElement().removeChangeListener(enabledListener);
			formulaElement.getExtensionElement().removeChangeListener(formulaListener);
			formulaElement.getAutoIterationsElement().removeChangeListener(autoIterationsListener);
			formulaElement.getIterationsElement().removeChangeListener(iterationsListener);
			formulaElement.getOpacityElement().removeChangeListener(opacityListener);
			if (configView != null) {
				viewContext.removeComponent(configView);
				configView.dispose();
				configView = null;
			}
		}

		private String createOpacityTooltip(final JSpinner opacitySpinner) {
			return MandelbrotSwingResources.getInstance().getString("label.opacity") + " " + opacitySpinner.getValue() + "%";
		}

		private String createIterationsTooltip(final JSpinner iterationsSpinner) {
			return MandelbrotSwingResources.getInstance().getString("label.iterations") + " " + iterationsSpinner.getValue();
		}

		private String createIncolouringFormulaPanelName(final IncolouringFormulaConfigElement formulaElement) {
			return formulaElement.getLabel();
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

	private class OutcolouringFormulaPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JCheckBox selectedCheckBox = createCheckBox();
		private ViewPanel configView;
		private final OutcolouringFormulaConfigElement formulaElement;
		private ValueChangeListener formulaListener;
		private final ValueChangeListener lockedListener;
		private final ValueChangeListener enabledListener;
		private final ValueChangeListener labelListener;
		private final ValueChangeListener autoIterationsListener;
		private final ValueChangeListener iterationsListener;
		private final ValueChangeListener opacityListener;

		/**
		 * @param fractalElement
		 * @param formulaElement
		 */
		public OutcolouringFormulaPanel(final MandelbrotFractalConfigElement fractalElement, final OutcolouringFormulaConfigElement formulaElement) {
			this.formulaElement = formulaElement;
			final ConfigurableExtensionComboBoxModel model = new ConfigurableExtensionComboBoxModel(MandelbrotRegistry.getInstance().getOutcolouringFormulaRegistry(), true);
			final JButton editOptionsButton = createIconTextButton("editConfig", "edit", 120, GUIFactory.DEFAULT_HEIGHT);
			final JCheckBox lockedCheckBox = createIconCheckBox("locked", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JCheckBox enabledCheckBox = createIconCheckBox("enabled", "visible", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JCheckBox autoIterationsCheckBox = createIconCheckBox("autoIterations", "locked", GUIFactory.ICON_WIDTH, GUIFactory.ICON_HEIGHT);
			final JSpinner opacitySpinner = createSpinner(0, 100, 1);
			final JSpinner iterationsSpinner = createSpinner(0, 5000, 1);
			final JComboBox extensionComboBox = createExtensionComboBox(model, 140, GUIFactory.DEFAULT_HEIGHT);
			final JTextField label = createTextField(formulaElement.getLabel(), 200, GUIFactory.DEFAULT_HEIGHT);
			final Box formulaPanel = createHorizontalBox(false);
			formulaPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
			formulaPanel.add(createSpace());
			formulaPanel.add(selectedCheckBox);
			formulaPanel.add(label);
			formulaPanel.add(createSpace());
			formulaPanel.add(opacitySpinner);
			formulaPanel.add(createSpace());
			formulaPanel.add(lockedCheckBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(enabledCheckBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(iterationsSpinner);
			formulaPanel.add(createSpace());
			formulaPanel.add(autoIterationsCheckBox);
			formulaPanel.add(Box.createHorizontalGlue());
			formulaPanel.add(extensionComboBox);
			formulaPanel.add(createSpace());
			formulaPanel.add(editOptionsButton);
			setLayout(new BorderLayout());
			add(formulaPanel, BorderLayout.CENTER);
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			lockedCheckBox.setSelected(formulaElement.isLocked());
			enabledCheckBox.setSelected(formulaElement.isEnabled());
			editOptionsButton.setEnabled((formulaElement != null) && (formulaElement.getReference() != null));
			opacitySpinner.setValue(formulaElement.getOpacity().intValue());
			opacitySpinner.setToolTipText(createOpacityTooltip(opacitySpinner));
			iterationsSpinner.setValue(formulaElement.getIterations());
			iterationsSpinner.setToolTipText(createIterationsTooltip(iterationsSpinner));
			autoIterationsCheckBox.setSelected(formulaElement.getAutoIterations());
			iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
			if (formulaElement.getReference() != null) {
				model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
			}
			final ActionListener comboListener = new ActionListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(final ActionEvent e) {
					try {
						ConfigurableExtension<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig> extension = (ConfigurableExtension<OutcolouringFormulaExtensionRuntime<?>, OutcolouringFormulaExtensionConfig>) extensionComboBox.getSelectedItem();
						context.acquire();
						context.stopRenderers();
						formulaElement.getContext().updateTimestamp();
						if (extension instanceof NullConfigurableExtension) {
							formulaElement.setReference(null);
						}
						else {
							formulaElement.setReference(extension.createConfigurableExtensionReference());
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
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(formulaElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
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
			extensionComboBox.addActionListener(comboListener);
			editOptionsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (formulaElement.getReference() != null) {
						if (configView == null) {
							try {
								final Extension<ViewExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getViewExtension(formulaElement.getReference().getExtensionId());
								configView = extension.createExtensionRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
							catch (final ExtensionException x) {
								configView = new DefaultViewRuntime().createView(formulaElement.getReference().getExtensionConfig(), viewContext, context, session);
							}
						}
						if (configView != null) {
							configView.setName(createOutcolouringFormulaPanelName(formulaElement));
						}
						viewContext.setComponent(configView);
					}
				}
			});
			label.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// formulaElement.getLabelElement().removeChangeListener(labelListener);
					formulaElement.getContext().updateTimestamp();
					formulaElement.setLabel(label.getText());
					if (configView != null) {
						configView.setName(createOutcolouringFormulaPanelName(formulaElement));
					}
					// formulaElement.getLabelElement().addChangeListener(labelListener);
				}
			});
			selectedCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(final ItemEvent e) {
					if (selectedCheckBox.isSelected()) {
						formulaElement.setUserData(Boolean.TRUE);
					}
					else {
						formulaElement.setUserData(null);
					}
				}
			});
			final ActionListener autoIterationsActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setAutoIterations(autoIterationsCheckBox.isSelected());
						iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			autoIterationsCheckBox.addActionListener(autoIterationsActionListener);
			final ChangeListener iterationsChangeListener = new ChangeListener() {
				@Override
				public void stateChanged(final ChangeEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setIterations(((Number) iterationsSpinner.getValue()).intValue());
						iterationsSpinner.setToolTipText(createIterationsTooltip(iterationsSpinner));
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			iterationsSpinner.addChangeListener(iterationsChangeListener);
			final ChangeListener opacityChangeListener = new ChangeListener() {
				@Override
				public void stateChanged(final ChangeEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setOpacity(((Number) opacitySpinner.getValue()).intValue());
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
			final ActionListener lockedActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						formulaElement.getContext().updateTimestamp();
						formulaElement.setLocked(lockedCheckBox.isSelected());
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
						formulaElement.getContext().updateTimestamp();
						formulaElement.setEnabled(enabledCheckBox.isSelected());
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			enabledCheckBox.addActionListener(enabledActionListener);
			formulaListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					switch (e.getEventType()) {
						case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
							editOptionsButton.setEnabled(formulaElement.getReference() != null);
							extensionComboBox.removeActionListener(comboListener);
							if (formulaElement.getReference() != null) {
								model.setSelectedItemByExtensionId(formulaElement.getReference().getExtensionId());
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
			lockedListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					lockedCheckBox.removeActionListener(lockedActionListener);
					lockedCheckBox.setSelected(formulaElement.isLocked());
					lockedCheckBox.addActionListener(lockedActionListener);
				}
			};
			enabledListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					enabledCheckBox.removeActionListener(enabledActionListener);
					enabledCheckBox.setSelected(formulaElement.isEnabled());
					enabledCheckBox.addActionListener(enabledActionListener);
				}
			};
			labelListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					label.setText(formulaElement.getLabel());
					if (configView != null) {
						configView.setName(createOutcolouringFormulaPanelName(formulaElement));
					}
				}
			};
			autoIterationsListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					autoIterationsCheckBox.removeActionListener(autoIterationsActionListener);
					iterationsSpinner.removeChangeListener(iterationsChangeListener);
					autoIterationsCheckBox.setSelected(formulaElement.getAutoIterations());
					iterationsSpinner.setEnabled(!autoIterationsCheckBox.isSelected());
					autoIterationsCheckBox.addActionListener(autoIterationsActionListener);
					iterationsSpinner.addChangeListener(iterationsChangeListener);
				}
			};
			iterationsListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					iterationsSpinner.removeChangeListener(iterationsChangeListener);
					iterationsSpinner.setValue(formulaElement.getIterations());
					iterationsSpinner.addChangeListener(iterationsChangeListener);
				}
			};
			opacityListener = new ValueChangeListener() {
				@Override
				public void valueChanged(final ValueChangeEvent e) {
					opacitySpinner.removeChangeListener(opacityChangeListener);
					opacitySpinner.setValue(formulaElement.getOpacity().intValue());
					opacitySpinner.addChangeListener(opacityChangeListener);
				}
			};
			formulaElement.getLabelElement().addChangeListener(labelListener);
			formulaElement.getLockedElement().addChangeListener(lockedListener);
			formulaElement.getEnabledElement().addChangeListener(enabledListener);
			formulaElement.getExtensionElement().addChangeListener(formulaListener);
			formulaElement.getAutoIterationsElement().addChangeListener(autoIterationsListener);
			formulaElement.getIterationsElement().addChangeListener(iterationsListener);
			formulaElement.getOpacityElement().addChangeListener(opacityListener);
		}

		public void dispose() {
			formulaElement.getLabelElement().removeChangeListener(labelListener);
			formulaElement.getLockedElement().removeChangeListener(lockedListener);
			formulaElement.getEnabledElement().removeChangeListener(enabledListener);
			formulaElement.getExtensionElement().removeChangeListener(formulaListener);
			formulaElement.getAutoIterationsElement().removeChangeListener(autoIterationsListener);
			formulaElement.getIterationsElement().removeChangeListener(iterationsListener);
			formulaElement.getOpacityElement().removeChangeListener(opacityListener);
			if (configView != null) {
				viewContext.removeComponent(configView);
				configView.dispose();
				configView = null;
			}
		}

		private String createOpacityTooltip(final JSpinner opacitySpinner) {
			return MandelbrotSwingResources.getInstance().getString("label.opacity") + " " + opacitySpinner.getValue() + "%";
		}

		private String createIterationsTooltip(final JSpinner iterationsSpinner) {
			return MandelbrotSwingResources.getInstance().getString("label.iterations") + " " + iterationsSpinner.getValue();
		}

		private String createOutcolouringFormulaPanelName(final OutcolouringFormulaConfigElement formulaElement) {
			return formulaElement.getLabel();
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
}
