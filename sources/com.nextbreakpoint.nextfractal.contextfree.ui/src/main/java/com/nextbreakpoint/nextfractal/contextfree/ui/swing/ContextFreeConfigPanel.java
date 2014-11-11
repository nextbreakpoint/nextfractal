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
package com.nextbreakpoint.nextfractal.contextfree.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.undo.UndoManager;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfig;
import com.nextbreakpoint.nextfractal.contextfree.parser.ContextFreeParser;
import com.nextbreakpoint.nextfractal.contextfree.parser.ContextFreeParserException;
import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSessionListener;
import com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.swing.extension.ExtensionListCellRenderer;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.StackLayout;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterConfigPanel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterSwingResources;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ViewPanel;
import com.nextbreakpoint.nextfractal.twister.util.Speed;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeConfigPanel extends ViewPanel {
	private static final Logger logger = Logger.getLogger(ContextFreeConfigPanel.class.getName());
	private static final long serialVersionUID = 1L;
	private final ContextFreeImagePanel imagePanel;
	private final ViewContext viewContext;
	private final RenderContext context;
	private final NodeSession session;
	private final ElementChangeListener configListener;
	private final ContextFreeConfig config;
	private final Color oddColor;
	private final Color evenColor;
	private NodeSessionListener sessionListener;
	private Worker worker = new Worker(new DefaultThreadFactory("ContextFree Parser", true, Thread.NORM_PRIORITY - 1));

	/**
	 * @param config
	 * @param viewContext
	 * @param context
	 * @param session
	 */
	public ContextFreeConfigPanel(final ContextFreeConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		this.viewContext = viewContext;
		this.context = context;
		this.session = session;
		this.config = config;
		oddColor = getBackground().brighter();
		evenColor = getBackground().brighter();
		imagePanel = new ContextFreeImagePanel(config);
		setLayout(new StackLayout());
		add(imagePanel);
		setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
		sessionListener = new NodeSessionListener() {
			@Override
			public void fireSessionChanged() {
			}
			
			@Override
			public void fireSessionCancelled() {
				imagePanel.refreshCFDG();
			}
			
			@Override
			public void fireSessionAccepted() {
				imagePanel.refreshCFDG();
			}
		};
		session.addSessionListener(sessionListener);
		configListener = new ElementChangeListener() {
			@Override
			public void valueChanged(final ElementChangeEvent e) {
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						viewContext.setComponent(ContextFreeConfigPanel.this);
						break;
					}
					default: {
						break;
					}
				}
			}
		};
		config.getCFDGSingleElement().addChangeListener(configListener);
		worker.start();
	}

	@Override
	public void dispose() {
		worker.stop();
		config.getCFDGSingleElement().removeChangeListener(configListener);
		session.removeSessionListener(sessionListener);
		imagePanel.dispose();
	}

	private static JCheckBox createIconCheckBox(final String key, final String iconKey, final int width, final int height) {
		final JCheckBox checkbox = GUIFactory.createCheckBox((String) null, ContextFreeSwingResources.getInstance().getString("tooltip." + key));
		try {
			checkbox.setIcon(new ImageIcon(ImageIO.read(ContextFreeConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			checkbox.setSelectedIcon(new ImageIcon(ImageIO.read(ContextFreeConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-selected-icon.png"))));
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
		final JButton button = GUIFactory.createButton((String) null, ContextFreeSwingResources.getInstance().getString("tooltip." + key));
		try {
			button.setIcon(new ImageIcon(ImageIO.read(ContextFreeConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-icon.png"))));
			button.setPressedIcon(new ImageIcon(ImageIO.read(ContextFreeConfigPanel.class.getResourceAsStream("/icons/" + iconKey + "-pressed-icon.png"))));
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
	// final JCheckBox checkbox = GUIFactory.createCheckBox(ContextFreeSwingResources.getInstance().getString("label." + key), ContextFreeSwingResources.getInstance().getString("tooltip." + key));
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
	// final JButton button = GUIFactory.createSmallButton(ContextFreeSwingResources.getInstance().getString("label." + key), ContextFreeSwingResources.getInstance().getString("tooltip." + key));
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
		final JLabel label = GUIFactory.createSmallLabel(ContextFreeSwingResources.getInstance().getString("label." + key), SwingConstants.LEFT);
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

	private class ContextFreeImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final ElementChangeListener speedListener;
		private final ContextFreeConfig config;
		private UndoManager undoManager = new UndoManager();
		private JTextField variationTextField;
		private JTextField baseDirTextField;
		private JEditorPane editorPane;
		private JButton loadButton;
		private JButton saveButton;
		private JButton stopButton;
		private JButton renderButton;
		private JFileChooser chooser;

		public ContextFreeImagePanel(final ContextFreeConfig config) {
			this.config = config;
			final JLabel editLabel = createTextLabel("drawing", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel zoomSpeedLabel = createTextLabel("zoomSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel shiftSpeedLabel = createTextLabel("shiftSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel rotationSpeedLabel = createTextLabel("rotationSpeed", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField zoomSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getPosition().getZ()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField shiftSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getPosition().getW()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JTextField rotationSpeedTextfield = createTextField(String.valueOf(config.getSpeed().getRotation().getZ()), 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel variationLabel = createTextLabel("variation", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			final JLabel baseDirLabel = createTextLabel("baseDir", SwingConstants.LEFT, 200, GUIFactory.DEFAULT_HEIGHT);
			variationTextField = createTextField(String.valueOf(config.getCFDG().getVariation()), 200, GUIFactory.DEFAULT_HEIGHT);
			baseDirTextField = createTextField(String.valueOf(config.getCFDG().getBaseDir()), 500, GUIFactory.DEFAULT_HEIGHT);
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
			editorPane = new JEditorPane();
			editorPane.setContentType("text/plain");
			editorPane.setText("");
			editorPane.getDocument().addUndoableEditListener(undoManager);
			JScrollPane scrollPane = new JScrollPane(editorPane);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			JPanel editorPanel = new JPanel(new BorderLayout());
			editorPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			editorPanel.add(scrollPane, BorderLayout.CENTER);
			Dimension preferredSize = new Dimension(550, 350);
			editorPanel.setPreferredSize(preferredSize);
			editorPanel.setMinimumSize(preferredSize);
			editorPanel.setMaximumSize(preferredSize);
			loadButton = createTextButton(80, GUIFactory.DEFAULT_HEIGHT);
			loadButton.setToolTipText(ContextFreeSwingResources.getInstance().getString("tooltip.load"));
			loadButton.setText(ContextFreeSwingResources.getInstance().getString("action.load"));
			saveButton = createTextButton(80, GUIFactory.DEFAULT_HEIGHT);
			saveButton.setToolTipText(ContextFreeSwingResources.getInstance().getString("tooltip.save"));
			saveButton.setText(ContextFreeSwingResources.getInstance().getString("action.save"));
			renderButton = createTextButton(80, GUIFactory.DEFAULT_HEIGHT);
			renderButton.setToolTipText(ContextFreeSwingResources.getInstance().getString("tooltip.render"));
			renderButton.setText(ContextFreeSwingResources.getInstance().getString("action.render"));
			stopButton = createTextButton(80, GUIFactory.DEFAULT_HEIGHT);
			stopButton.setToolTipText(ContextFreeSwingResources.getInstance().getString("tooltip.stop"));
			stopButton.setText(ContextFreeSwingResources.getInstance().getString("action.stop"));
			final Box tmpPanel8 = createHorizontalBox(false);
			tmpPanel8.add(baseDirLabel);
			tmpPanel8.add(createSpace());
			tmpPanel8.add(baseDirTextField);
			tmpPanel8.add(Box.createHorizontalGlue());
			final Box tmpPanel7 = createHorizontalBox(false);
			tmpPanel7.add(variationLabel);
			tmpPanel7.add(createSpace());
			tmpPanel7.add(variationTextField);
			tmpPanel7.add(Box.createHorizontalGlue());
			final Box tmpPanel3 = createVerticalBox(false);
			tmpPanel3.add(editLabel);
			tmpPanel3.add(Box.createVerticalGlue());
			final Box tmpPanel1 = createHorizontalBox(false);
			tmpPanel1.add(tmpPanel3);
			tmpPanel1.add(createSpace());
			tmpPanel1.add(editorPanel);
			tmpPanel1.add(Box.createHorizontalGlue());
			final Box tmpPanel2 = createHorizontalBox(false);
			tmpPanel2.add(Box.createHorizontalStrut(200));
			tmpPanel2.add(createSpace());
			tmpPanel2.add(loadButton);
			tmpPanel2.add(createSpace());
			tmpPanel2.add(saveButton);
			tmpPanel2.add(createSpace());
			tmpPanel2.add(renderButton);
			tmpPanel2.add(createSpace());
			tmpPanel2.add(stopButton);
			tmpPanel2.add(Box.createHorizontalGlue());
			final Box tmpPanel = createVerticalBox(false);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel8);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel7);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel1);
			tmpPanel.add(Box.createVerticalStrut(8));
			tmpPanel.add(tmpPanel2);
			tmpPanel.add(Box.createVerticalStrut(68));
			setLayout(new BorderLayout());
			add(tmpPanel, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
			setOpaque(false);
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
			final ActionListener variationActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.getCFDG().setVariation(variationTextField.getText() != null ? variationTextField.getText() : "ABC");
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			variationTextField.addActionListener(variationActionListener);
			final ActionListener baseDirActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						context.acquire();
						config.getContext().updateTimestamp();
						config.getCFDG().setBaseDir(baseDirTextField.getText() != null ? baseDirTextField.getText() : System.getProperty("user.home"));
						try {
							loadConfig(config, editorPane.getText(), variationTextField.getText());
						} catch (final ContextFreeParserException x) {
							logger.log(Level.WARNING, ContextFreeSwingResources.getInstance().getString("message.parserError"), x);
							GUIUtil.executeTask(new Runnable() {
								@Override
								public void run() {
									JTextArea textArea = new JTextArea();
									textArea.setText(x.getMessage());
									JOptionPane.showMessageDialog(ContextFreeImagePanel.this, textArea, ContextFreeSwingResources.getInstance().getString("message.parserError"), JOptionPane.ERROR_MESSAGE);
								}
							}, false);
						}
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			};
			baseDirTextField.addActionListener(baseDirActionListener);
			final ActionListener loadActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (chooser == null) {
						chooser = new JFileChooser();
						chooser.setDialogTitle(ContextFreeSwingResources.getInstance().getString("label.selectFile"));
						chooser.setMultiSelectionEnabled(false);
					}
					if (chooser.showOpenDialog(ContextFreeConfigPanel.this) == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						if (file != null) {
							loadConfig(config, file);
						}
					}
				}
			};
			loadButton.addActionListener(loadActionListener);
			final ActionListener saveActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					if (chooser == null) {
						chooser = new JFileChooser();
						chooser.setDialogTitle(ContextFreeSwingResources.getInstance().getString("label.selectFile"));
						chooser.setMultiSelectionEnabled(false);
					}
					if (chooser.showSaveDialog(ContextFreeConfigPanel.this) == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						if (file != null) {
							saveConfig(config, file);
						}
					}
				}
			};
			saveButton.addActionListener(saveActionListener);
			final ActionListener renderActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					renderConfig(config, editorPane.getText());
				}
			};
			renderButton.addActionListener(renderActionListener);
			final ActionListener stopActionListener = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					stopRender();
				}
			};
			stopButton.addActionListener(stopActionListener);
			final KeyListener keyListener = new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0 || (e.getModifiersEx() & InputEvent.META_DOWN_MASK) != 0) {
						if (e.getKeyCode() == KeyEvent.VK_Z) {
							if (undoManager.canUndo()) {
								undoManager.undo();
							}
						} else if (e.getKeyCode() == KeyEvent.VK_Y) {
							if (undoManager.canRedo()) {
								undoManager.redo();
							}
						}
					}
				}
			};
			editorPane.addKeyListener(keyListener);
			speedListener = new ElementChangeListener() {
				@Override
				public void valueChanged(final ElementChangeEvent e) {
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
			config.getSpeedElement().addChangeListener(speedListener);
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					refreshCFDG();
					revalidate();
				}
			}, true);
		}

		public void enableButtons() {
			loadButton.setEnabled(true);
			saveButton.setEnabled(true);
			renderButton.setEnabled(true);
			stopButton.setEnabled(true);
		}

		public void disableButtons() {
			loadButton.setEnabled(false);
			saveButton.setEnabled(false);
			renderButton.setEnabled(false);
			stopButton.setEnabled(false);
		}

		public void dispose() {
			config.getSpeedElement().removeChangeListener(speedListener);
		}

		public void refreshCFDG() {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					disableButtons();
				}
			}, false);
			worker.addTask(new Runnable() {
				@Override
				public void run() {
					final CFDGBuilder builder = new CFDGBuilder();
					try {
						context.acquire();
						config.getCFDG().toCFDG(builder);
						context.release();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					} finally {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								editorPane.setText(builder.toString());
								variationTextField.setText(config.getCFDG().getVariation());
								enableButtons();
							}
						}, false);
					}
				}
			});
		}

		private void renderConfig(final ContextFreeConfig config, final String text) {
			final String[] variation = new String[1];
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					variation[0] = variationTextField.getText();
					disableButtons();
				}
			}, false);
			worker.addTask(new Runnable() {
				@Override
				public void run() {
					try {
						context.acquire();
						session.removeSessionListener(sessionListener);
						config.getContext().updateTimestamp();
						try {
							loadConfig(config, text, variation[0]);
						} catch (final Exception e) {
							logger.log(Level.WARNING, ContextFreeSwingResources.getInstance().getString("message.parserError"), e);
							GUIUtil.executeTask(new Runnable() {
								@Override
								public void run() {
									JTextArea textArea = new JTextArea();
									textArea.setText(e.getMessage());
									JOptionPane.showMessageDialog(ContextFreeImagePanel.this, textArea, ContextFreeSwingResources.getInstance().getString("message.parserError"), JOptionPane.ERROR_MESSAGE);
								}
							}, false);
						}
						session.addSessionListener(sessionListener);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					} finally {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								enableButtons();
							}
						}, false);
					}
				}
			});
		}

		private void stopRender() {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					disableButtons();
				}
			}, false);
			worker.addTask(new Runnable() {
				@Override
				public void run() {
					try {
						context.acquire();
						context.stopRenderers();
						context.release();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					} finally {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								enableButtons();
							}
						}, false);
					}
				}
			});
		}

		private void loadConfig(final ContextFreeConfig config, final File file) {
			final String[] variation = new String[1];
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					variation[0] = variationTextField.getText();
					disableButtons();
				}
			}, false);
			worker.addTask(new Runnable() {
				@Override
				public void run() {
					final StringBuilder builder = new StringBuilder();
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(file));
						String line = null;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
							builder.append("\n");
						}
						context.acquire();
						session.removeSessionListener(sessionListener);
						config.getContext().updateTimestamp();
						String baseDir = file.getParent();
						config.getCFDG().setBaseDir(baseDir);
						try {
							loadConfig(config, builder.toString(), variation[0]);
							GUIUtil.executeTask(new Runnable() {
								@Override
								public void run() {
									editorPane.setText(builder.toString());
									variationTextField.setText(config.getCFDG().getVariation());
									baseDirTextField.setText(config.getCFDG().getBaseDir());
								}
							}, false);
						} catch (final ContextFreeParserException e) {
							logger.log(Level.WARNING, ContextFreeSwingResources.getInstance().getString("message.parserError"), e);
							GUIUtil.executeTask(new Runnable() {
								@Override
								public void run() {
									JTextArea textArea = new JTextArea();
									textArea.setText(e.getMessage());
									JOptionPane.showMessageDialog(ContextFreeImagePanel.this, textArea, ContextFreeSwingResources.getInstance().getString("message.parserError"), JOptionPane.ERROR_MESSAGE);
								}
							}, false);
						}
						session.addSessionListener(sessionListener);
						context.release();
						context.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					} 
					catch (final IOException x) {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								JOptionPane.showMessageDialog(ContextFreeImagePanel.this, x.getMessage(), ContextFreeSwingResources.getInstance().getString("message.readerError"), JOptionPane.ERROR_MESSAGE);
							}
						}, false);
					} 
					finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException x) {
							}
						}
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								enableButtons();
							}
						}, false);
					}
				}
			});
		}

		private void saveConfig(final ContextFreeConfig config, final File file) {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					disableButtons();
				}
			}, false);
			worker.addTask(new Runnable() {
				@Override
				public void run() {
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(new FileWriter(file));
						writer.print(editorPane.getText());
					} catch (final IOException x) {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								JOptionPane.showMessageDialog(ContextFreeImagePanel.this, x.getMessage(), ContextFreeSwingResources.getInstance().getString("message.writerError"), JOptionPane.ERROR_MESSAGE);
							}
						}, false);
					} 
					finally {
						if (writer != null) {
							writer.close();
						}
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								enableButtons();
							}
						}, false);
					}
				}
			});
		}

		private void loadConfig(ContextFreeConfig config, String text, String variation) throws ContextFreeParserException {
			ContextFreeParser parser = new ContextFreeParser();
			String baseDir = config.getCFDG().getBaseDir();
			ContextFreeConfig newConfig = parser.parseConfig(new File(baseDir), text);
			config.setCFDG(newConfig.getCFDG());
			config.getCFDG().setVariation(variation);
			config.getCFDG().setBaseDir(baseDir);
		}
	}
}
