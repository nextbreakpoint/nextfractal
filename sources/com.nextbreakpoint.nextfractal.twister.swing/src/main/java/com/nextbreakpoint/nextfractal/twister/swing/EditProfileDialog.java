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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.RenderService.ServiceCallback;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;

/**
 * @author Andrea Medeghini
 */
public class EditProfileDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat format = new DecimalFormat("0.00");
	private static final String EDIT_PROFILE_FRAME_TITLE = "editProfileFrame.title";
	// private static final String EDIT_PROFILE_FRAME_WIDTH = "editProfileFrame.width";
	// private static final String EDIT_PROFILE_FRAME_HEIGHT = "editProfileFrame.height";
	// private static final String EDIT_PROFILE_FRAME_ICON = "editProfileFrame.icon";
	private final RenderProfileDataRow profile;
	private final RenderService service;
	private JTextField nameTextField;
	private JTextField widthTextField;
	private JTextField heightTextField;
	private JTextField offsetXTextField;
	private JTextField offsetYTextField;
	private JTextField widthTextField2;
	private JTextField heightTextField2;
	private JTextField offsetXTextField2;
	private JTextField offsetYTextField2;
	private JComboBox sizeUnitComboBox;
	private JComboBox offsetUnitComboBox;
	private JComboBox sizeUnitComboBox2;
	private JComboBox offsetUnitComboBox2;
	private JSpinner dpiSpinner;
	private JSpinner qualitySpinner;
	private JSpinner frameRateSpinner;
	private TimeSelector startTimeSelector;
	private TimeSelector stopTimeSelector;

	/**
	 * @param service
	 * @param profile
	 */
	public EditProfileDialog(final RenderService service, final RenderProfileDataRow profile) {
		this.profile = profile;
		this.service = service;
		// final int defaultWidth = Integer.parseInt(ServiceResources.getInstance().getString(EditProfileDialog.EDIT_PROFILE_FRAME_WIDTH));
		// final int defaultHeight = Integer.parseInt(ServiceResources.getInstance().getString(EditProfileDialog.EDIT_PROFILE_FRAME_HEIGHT));
		// final int width = Integer.getInteger(EditProfileFrame.EDIT_PROFILE_FRAME_WIDTH, defaultWidth);
		// final int height = Integer.getInteger(EditProfileFrame.EDIT_PROFILE_FRAME_HEIGHT, defaultHeight);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(EditProfileDialog.EDIT_PROFILE_FRAME_TITLE));
		getContentPane().add(createProfilePanel());
		addWindowListener(new DialogListener());
		// setSize(new Dimension(width, height));
		pack();
		setResizable(false);
		setModal(true);
	}

	private Box createProfilePanel() {
		final Box panel = Box.createVerticalBox();
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		nameTextField = GUIFactory.createTextField(profile.getProfileName(), null, 20);
		dpiSpinner = GUIFactory.createSpinner(new SpinnerNumberModel(72, 10, 2000, 1), null);
		dpiSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				updateSize();
				updateOffset();
			}
		});
		widthTextField = GUIFactory.createTextField(String.valueOf(profile.getImageWidth()), null, 6);
		widthTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateSize();
			}
		});
		heightTextField = GUIFactory.createTextField(String.valueOf(profile.getImageHeight()), null, 6);
		heightTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateSize();
			}
		});
		offsetXTextField = GUIFactory.createTextField(String.valueOf(profile.getOffsetX()), null, 6);
		offsetXTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateOffset();
			}
		});
		offsetYTextField = GUIFactory.createTextField(String.valueOf(profile.getOffsetX()), null, 6);
		offsetYTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateOffset();
			}
		});
		widthTextField2 = GUIFactory.createTextField(String.valueOf(profile.getImageWidth()), null, 6);
		widthTextField2.setEditable(false);
		heightTextField2 = GUIFactory.createTextField(String.valueOf(profile.getImageHeight()), null, 6);
		heightTextField2.setEditable(false);
		sizeUnitComboBox = GUIFactory.createComboBox(new UnitComboBoxModel(), null);
		sizeUnitComboBox.setRenderer(new UnitListCellRenderer());
		sizeUnitComboBox.setSelectedIndex(0);
		sizeUnitComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateSize();
			}
		});
		sizeUnitComboBox2 = GUIFactory.createComboBox(new UnitComboBoxModel(), null);
		sizeUnitComboBox2.setRenderer(new UnitListCellRenderer());
		sizeUnitComboBox2.setSelectedIndex(0);
		sizeUnitComboBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateSize();
			}
		});
		offsetXTextField2 = GUIFactory.createTextField(String.valueOf(profile.getOffsetX()), null, 6);
		offsetXTextField2.setEditable(false);
		offsetYTextField2 = GUIFactory.createTextField(String.valueOf(profile.getOffsetY()), null, 6);
		offsetYTextField2.setEditable(false);
		offsetUnitComboBox = GUIFactory.createComboBox(new UnitComboBoxModel(), null);
		offsetUnitComboBox.setRenderer(new UnitListCellRenderer());
		offsetUnitComboBox.setSelectedIndex(0);
		offsetUnitComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateOffset();
			}
		});
		offsetUnitComboBox2 = GUIFactory.createComboBox(new UnitComboBoxModel(), null);
		offsetUnitComboBox2.setRenderer(new UnitListCellRenderer());
		offsetUnitComboBox2.setSelectedIndex(0);
		offsetUnitComboBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateOffset();
			}
		});
		frameRateSpinner = GUIFactory.createSpinner(new SpinnerNumberModel(profile.getFrameRate(), 0, 100, 1), null);
		qualitySpinner = GUIFactory.createSpinner(new SpinnerNumberModel(profile.getQuality(), 0, 100, 1), null);
		startTimeSelector = new TimeSelector(profile.getStartTime());
		stopTimeSelector = new TimeSelector(profile.getStopTime());
		final Dimension labelSize = new Dimension(120, 20);
		final Box namePanel = Box.createHorizontalBox();
		final JLabel nameLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.name"), SwingConstants.RIGHT);
		nameLabel.setPreferredSize(labelSize);
		namePanel.add(nameLabel);
		namePanel.add(Box.createHorizontalStrut(8));
		namePanel.add(nameTextField);
		namePanel.add(Box.createHorizontalGlue());
		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.add(GUIFactory.createButton(new SaveAction(), null));
		buttonsPanel.add(GUIFactory.createButton(new CloseAction(), null));
		final Box sizePanel = Box.createHorizontalBox();
		final JLabel sizeLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.size"), SwingConstants.RIGHT);
		sizeLabel.setPreferredSize(labelSize);
		sizePanel.add(sizeLabel);
		sizePanel.add(Box.createHorizontalStrut(8));
		sizePanel.add(widthTextField);
		sizePanel.add(Box.createHorizontalStrut(8));
		sizePanel.add(GUIFactory.createLabel("W", SwingConstants.LEFT));
		sizePanel.add(Box.createHorizontalStrut(16));
		sizePanel.add(heightTextField);
		sizePanel.add(Box.createHorizontalStrut(8));
		sizePanel.add(GUIFactory.createLabel("H", SwingConstants.LEFT));
		sizePanel.add(Box.createHorizontalStrut(16));
		sizePanel.add(Box.createHorizontalGlue());
		sizePanel.add(sizeUnitComboBox);
		final Box offsetPanel = Box.createHorizontalBox();
		final JLabel offsetLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.offset"), SwingConstants.RIGHT);
		offsetLabel.setPreferredSize(labelSize);
		offsetPanel.add(offsetLabel);
		offsetPanel.add(Box.createHorizontalStrut(8));
		offsetPanel.add(offsetXTextField);
		offsetPanel.add(Box.createHorizontalStrut(8));
		offsetPanel.add(GUIFactory.createLabel("X", SwingConstants.LEFT));
		offsetPanel.add(Box.createHorizontalStrut(16));
		offsetPanel.add(offsetYTextField);
		offsetPanel.add(Box.createHorizontalStrut(8));
		offsetPanel.add(GUIFactory.createLabel("Y", SwingConstants.LEFT));
		offsetPanel.add(Box.createHorizontalStrut(16));
		offsetPanel.add(Box.createHorizontalGlue());
		offsetPanel.add(offsetUnitComboBox);
		final Box sizePanel2 = Box.createHorizontalBox();
		sizePanel2.add(Box.createHorizontalStrut(128));
		sizePanel2.add(widthTextField2);
		sizePanel2.add(Box.createHorizontalStrut(8));
		sizePanel2.add(GUIFactory.createLabel("W", SwingConstants.LEFT));
		sizePanel2.add(Box.createHorizontalStrut(16));
		sizePanel2.add(heightTextField2);
		sizePanel2.add(Box.createHorizontalStrut(8));
		sizePanel2.add(GUIFactory.createLabel("H", SwingConstants.LEFT));
		sizePanel2.add(Box.createHorizontalStrut(16));
		sizePanel2.add(Box.createHorizontalGlue());
		sizePanel2.add(sizeUnitComboBox2);
		final Box offsetPanel2 = Box.createHorizontalBox();
		offsetPanel2.add(Box.createHorizontalStrut(128));
		offsetPanel2.add(offsetXTextField2);
		offsetPanel2.add(Box.createHorizontalStrut(8));
		offsetPanel2.add(GUIFactory.createLabel("X", SwingConstants.LEFT));
		offsetPanel2.add(Box.createHorizontalStrut(16));
		offsetPanel2.add(offsetYTextField2);
		offsetPanel2.add(Box.createHorizontalStrut(8));
		offsetPanel2.add(GUIFactory.createLabel("Y", SwingConstants.LEFT));
		offsetPanel2.add(Box.createHorizontalStrut(16));
		offsetPanel2.add(Box.createHorizontalGlue());
		offsetPanel2.add(offsetUnitComboBox2);
		final Box resolutionPanel = Box.createHorizontalBox();
		final JLabel resolutionLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.resolution"), SwingConstants.RIGHT);
		final JLabel dpiLabel = GUIFactory.createLabel("dpi", SwingConstants.LEFT);
		dpiLabel.setPreferredSize(new Dimension(30, 20));
		resolutionLabel.setPreferredSize(labelSize);
		resolutionPanel.add(resolutionLabel);
		resolutionPanel.add(Box.createHorizontalStrut(8));
		resolutionPanel.add(dpiSpinner);
		resolutionPanel.add(Box.createHorizontalStrut(8));
		resolutionPanel.add(dpiLabel);
		resolutionPanel.add(Box.createHorizontalStrut(200));
		resolutionPanel.add(Box.createHorizontalGlue());
		final Box qualityPanel = Box.createHorizontalBox();
		final JLabel qualityLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.quality"), SwingConstants.RIGHT);
		final JLabel percentLabel = GUIFactory.createLabel("%", SwingConstants.LEFT);
		percentLabel.setPreferredSize(new Dimension(30, 20));
		qualityLabel.setPreferredSize(labelSize);
		qualityPanel.add(qualityLabel);
		qualityPanel.add(Box.createHorizontalStrut(8));
		qualityPanel.add(qualitySpinner);
		qualityPanel.add(Box.createHorizontalStrut(8));
		qualityPanel.add(percentLabel);
		qualityPanel.add(Box.createHorizontalStrut(200));
		qualityPanel.add(Box.createHorizontalGlue());
		final Box frameRatePanel = Box.createHorizontalBox();
		final JLabel frameRateLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.frameRate"), SwingConstants.RIGHT);
		final JLabel fpsLabel = GUIFactory.createLabel("fps", SwingConstants.LEFT);
		fpsLabel.setPreferredSize(new Dimension(30, 20));
		frameRateLabel.setPreferredSize(labelSize);
		frameRatePanel.add(frameRateLabel);
		frameRatePanel.add(Box.createHorizontalStrut(8));
		frameRatePanel.add(frameRateSpinner);
		frameRatePanel.add(Box.createHorizontalStrut(8));
		frameRatePanel.add(fpsLabel);
		frameRatePanel.add(Box.createHorizontalStrut(200));
		frameRatePanel.add(Box.createHorizontalGlue());
		final Box startTimePanel = Box.createHorizontalBox();
		final JLabel startTimeLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.startTime"), SwingConstants.RIGHT);
		startTimeLabel.setPreferredSize(labelSize);
		startTimePanel.add(startTimeLabel);
		startTimePanel.add(Box.createHorizontalStrut(8));
		startTimePanel.add(startTimeSelector);
		// startTimePanel.add(Box.createHorizontalGlue());
		final Box stopTimePanel = Box.createHorizontalBox();
		final JLabel stopTimeLabel = GUIFactory.createLabel(TwisterSwingResources.getInstance().getString("label.stopTime"), SwingConstants.RIGHT);
		stopTimeLabel.setPreferredSize(labelSize);
		stopTimePanel.add(stopTimeLabel);
		stopTimePanel.add(Box.createHorizontalStrut(8));
		stopTimePanel.add(stopTimeSelector);
		// stopTimePanel.add(Box.createHorizontalGlue());
		panel.add(namePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(sizePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(sizePanel2);
		panel.add(Box.createVerticalStrut(8));
		panel.add(offsetPanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(offsetPanel2);
		panel.add(Box.createVerticalStrut(8));
		panel.add(resolutionPanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(qualityPanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(frameRatePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(startTimePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(stopTimePanel);
		panel.add(Box.createVerticalStrut(8));
		panel.add(buttonsPanel);
		return panel;
	}

	private void updateSize() {
		final Unit u = (Unit) sizeUnitComboBox.getSelectedItem();
		final Unit u2 = (Unit) sizeUnitComboBox2.getSelectedItem();
		try {
			widthTextField2.setText(format.format(u2.getValue(format.parse(widthTextField.getText()).doubleValue(), ((Number) dpiSpinner.getValue()).intValue(), u)));
		}
		catch (final ParseException e) {
			e.printStackTrace();
		}
		try {
			heightTextField2.setText(format.format(u2.getValue(format.parse(heightTextField.getText()).doubleValue(), ((Number) dpiSpinner.getValue()).intValue(), u)));
		}
		catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	private void updateOffset() {
		final Unit u = (Unit) offsetUnitComboBox.getSelectedItem();
		final Unit u2 = (Unit) offsetUnitComboBox2.getSelectedItem();
		try {
			offsetXTextField2.setText(format.format(u2.getValue(format.parse(offsetXTextField.getText()).doubleValue(), ((Number) dpiSpinner.getValue()).intValue(), u)));
		}
		catch (final ParseException e) {
			e.printStackTrace();
		}
		try {
			offsetYTextField2.setText(format.format(u2.getValue(format.parse(offsetYTextField.getText()).doubleValue(), ((Number) dpiSpinner.getValue()).intValue(), u)));
		}
		catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	// private class SizeComboBoxModel extends DefaultComboBoxModel {
	// private static final long serialVersionUID = 1L;
	//
	// public SizeComboBoxModel() {
	// addElement(new Dimension(320, 200));
	// addElement(new Dimension(640, 480));
	// addElement(new Dimension(800, 600));
	// addElement(new Dimension(1024, 768));
	// addElement(new Dimension(1152, 720));
	// addElement(new Dimension(1280, 800));
	// addElement(new Dimension(1600, 900));
	// }
	// }
	//
	// private class SizeListCellRenderer extends DefaultListCellRenderer {
	// private static final long serialVersionUID = 1L;
	//
	// /**
	// * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	// */
	// @Override
	// public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
	// return super.getListCellRendererComponent(list, "W " + ((Dimension) value).getWidth() + ", H " + ((Dimension) value).getHeight(), index, isSelected, cellHasFocus);
	// }
	// }
	private interface Unit {
		public String getName();

		public double getValue(double value, double dpi, Unit u);

		public int getValueInPixels(double value, double dpi);
	}

	private class UnitPX implements Unit {
		@Override
		public String getName() {
			return "px";
		}

		@Override
		public double getValue(final double value, final double dpi, final Unit u) {
			return u.getValueInPixels(value, dpi);
		}

		@Override
		public int getValueInPixels(final double value, final double dpi) {
			return (int) Math.rint(value);
		}
	}

	private class UnitCM implements Unit {
		@Override
		public String getName() {
			return "cm";
		}

		@Override
		public double getValue(final double value, final double dpi, final Unit u) {
			return (u.getValueInPixels(value, dpi) * 2.54d) / dpi;
		}

		@Override
		public int getValueInPixels(final double value, final double dpi) {
			return (int) Math.rint((value * dpi) / 2.54d);
		}
	}

	private class UnitMM implements Unit {
		@Override
		public String getName() {
			return "mm";
		}

		@Override
		public double getValue(final double value, final double dpi, final Unit u) {
			return (u.getValueInPixels(value, dpi) * 25.4d) / dpi;
		}

		@Override
		public int getValueInPixels(final double value, final double dpi) {
			return (int) Math.rint((value * dpi) / 25.4d);
		}
	}

	private class UnitPT implements Unit {
		@Override
		public String getName() {
			return "pt";
		}

		@Override
		public double getValue(final double value, final double dpi, final Unit u) {
			return (u.getValueInPixels(value, dpi) * 72d) / dpi;
		}

		@Override
		public int getValueInPixels(final double value, final double dpi) {
			return (int) Math.rint((value * dpi) / 72d);
		}
	}

	private class UnitINCH implements Unit {
		@Override
		public String getName() {
			return "''";
		}

		@Override
		public double getValue(final double value, final double dpi, final Unit u) {
			return u.getValueInPixels(value, dpi) / dpi;
		}

		@Override
		public int getValueInPixels(final double value, final double dpi) {
			return (int) Math.rint(value * dpi);
		}
	}

	private class UnitComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;

		public UnitComboBoxModel() {
			addElement(new UnitPX());
			addElement(new UnitCM());
			addElement(new UnitMM());
			addElement(new UnitPT());
			addElement(new UnitINCH());
		}
	}

	private class UnitListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		/**
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, ((Unit) value).getName(), index, isSelected, cellHasFocus);
		}
	}

	private class TimeSelector extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JSpinner hhSpinner;
		private final JSpinner mmSpinner;
		private final JSpinner ssSpinner;

		public TimeSelector(final int time) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			hhSpinner = GUIFactory.createSpinner(new SpinnerNumberModel(0, 0, 24, 1), null);
			mmSpinner = GUIFactory.createSpinner(new SpinnerNumberModel(0, 0, 60, 1), null);
			ssSpinner = GUIFactory.createSpinner(new SpinnerNumberModel(0, 0, 60, 1), null);
			add(hhSpinner);
			add(Box.createHorizontalStrut(8));
			add(GUIFactory.createLabel("HH", SwingConstants.CENTER));
			add(Box.createHorizontalGlue());
			add(mmSpinner);
			add(Box.createHorizontalStrut(8));
			add(GUIFactory.createLabel("MM", SwingConstants.CENTER));
			add(Box.createHorizontalGlue());
			add(ssSpinner);
			add(Box.createHorizontalStrut(8));
			add(GUIFactory.createLabel("SS", SwingConstants.CENTER));
			setTimeInSeconds(time);
		}

		/**
		 * @return
		 */
		public int getTimeInSeconds() {
			return 3600 * (Integer) hhSpinner.getValue() + 60 * (Integer) mmSpinner.getValue() + (Integer) ssSpinner.getValue();
		}

		/**
		 * @param time
		 */
		public void setTimeInSeconds(final int time) {
			final int h = time / 3600;
			final int t = time % 3600;
			final int m = t / 60;
			final int s = t % 60;
			hhSpinner.setValue(Integer.valueOf(h));
			mmSpinner.setValue(Integer.valueOf(m));
			ssSpinner.setValue(Integer.valueOf(s));
		}
	}

	private class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public SaveAction() {
			super(TwisterSwingResources.getInstance().getString("action.save"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			try {
				boolean error = false;
				final Unit u1 = (Unit) sizeUnitComboBox.getSelectedItem();
				final Unit u2 = (Unit) offsetUnitComboBox.getSelectedItem();
				final int dpi = ((Number) dpiSpinner.getValue()).intValue();
				int width = 0;
				int height = 0;
				int offsetX = 0;
				int offsetY = 0;
				try {
					width = u1.getValueInPixels(format.parse(widthTextField.getText()).doubleValue(), dpi);
				}
				catch (final Exception x) {
					x.printStackTrace();
					widthTextField.setBackground(Color.RED);
					error = true;
				}
				try {
					height = u1.getValueInPixels(format.parse(heightTextField.getText()).doubleValue(), dpi);
				}
				catch (final Exception x) {
					x.printStackTrace();
					heightTextField.setBackground(Color.RED);
					error = true;
				}
				try {
					offsetX = u2.getValueInPixels(format.parse(offsetXTextField.getText()).doubleValue(), dpi);
				}
				catch (final Exception x) {
					x.printStackTrace();
					offsetXTextField.setBackground(Color.RED);
					error = true;
				}
				try {
					offsetY = u2.getValueInPixels(format.parse(offsetYTextField.getText()).doubleValue(), dpi);
				}
				catch (final Exception x) {
					x.printStackTrace();
					offsetYTextField.setBackground(Color.RED);
					error = true;
				}
				if (startTimeSelector.getTimeInSeconds() > stopTimeSelector.getTimeInSeconds()) {
					stopTimeSelector.setBackground(Color.RED);
					startTimeSelector.setBackground(Color.RED);
					error = true;
				}
				if (nameTextField.getText().trim().length() == 0) {
					nameTextField.setBackground(Color.RED);
					error = true;
				}
				if ((width == 0) || (width > 10240)) {
					widthTextField.setBackground(Color.RED);
					error = true;
				}
				if ((height == 0) || (height > 10240)) {
					heightTextField.setBackground(Color.RED);
					error = true;
				}
				if ((offsetX < -10240) || (offsetX > 10240)) {
					offsetXTextField.setBackground(Color.RED);
					error = true;
				}
				if ((offsetY < -10240) || (offsetY > 10240)) {
					offsetYTextField.setBackground(Color.RED);
					error = true;
				}
				if (!error) {
					profile.setProfileName(nameTextField.getText());
					profile.setImageWidth(width);
					profile.setImageHeight(height);
					profile.setOffsetX(offsetX);
					profile.setOffsetY(offsetY);
					profile.setFrameRate(((Integer) frameRateSpinner.getValue()).intValue());
					profile.setQuality(((Integer) qualitySpinner.getValue()).intValue());
					profile.setStartTime(startTimeSelector.getTimeInSeconds());
					profile.setStopTime(stopTimeSelector.getTimeInSeconds());
					service.execute(new ServiceCallback<Object>() {
						@Override
						public void executed(final Object value) {
							GUIUtil.executeTask(new CloseRunnable(), true);
						}

						@Override
						public void failed(final Throwable throwable) {
							JOptionPane.showMessageDialog(EditProfileDialog.this, TwisterSwingResources.getInstance().getString("error.saveProfile"), TwisterSwingResources.getInstance().getString("label.saveProfile"), JOptionPane.ERROR_MESSAGE);
						}

						@Override
						public Object execute(final LibraryService service) throws Exception {
							service.saveProfile(profile);
							service.cleanProfile(profile);
							return null;
						}
					});
				}
				else {
					JOptionPane.showMessageDialog(EditProfileDialog.this, TwisterSwingResources.getInstance().getString("error.saveProfile"), TwisterSwingResources.getInstance().getString("label.saveProfile"), JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (final Exception x) {
				x.printStackTrace();
			}
		}
	}

	private class CloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public CloseAction() {
			super(TwisterSwingResources.getInstance().getString("action.close"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class DialogListener extends WindowAdapter {
		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
			GUIUtil.executeTask(new CloseRunnable(), true);
		}
	}

	private class CloseRunnable implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			setVisible(false);
		}
	}
}
