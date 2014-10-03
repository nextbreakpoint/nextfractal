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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.swing.util.Buttons;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIFactory;

/**
 * @author Andrea Medeghini
 */
public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final NavigationModel model;
	private final JLabel historyLabel;
	private final Box historyPanel;
	private final Box buttonsPanel;
	private JButton prevButton;
	private JButton topButton;
	private JButton nextButton;

	/**
	 * @param model
	 */
	public NavigationPanel(final NavigationModel model) {
		this.model = model;
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				historyLabel.setText(NavigationPanel.this.model.getHistory());
			}
		});
		historyPanel = new Box(BoxLayout.X_AXIS);
		buttonsPanel = new Box(BoxLayout.X_AXIS);
		historyLabel = GUIFactory.createLabel(getHistory(model), SwingConstants.LEFT);
		historyLabel.setFont(new Font(historyLabel.getFont().getFontName(), Font.PLAIN, 10));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		historyLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		final ButtonGroup buttonGroup = new ButtonGroup();
		final List<JButton> segmentButtons = Buttons.createSegmentedButtons(2, buttonGroup);
		final JComponent layoutComponent = Buttons.createLayoutComponent(segmentButtons);
		try {
			prevButton = (JButton) layoutComponent.getComponent(0);
			prevButton.setAction(new PrevAction());
			prevButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.prevComponent"));
			nextButton = (JButton) layoutComponent.getComponent(1);
			nextButton.setAction(new NextAction());
			nextButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.nextComponent"));
			topButton = GUIFactory.createButton(new TopAction(), TwisterSwingResources.getInstance().getString("tooltip.topComponent"));
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		layoutComponent.add(Box.createHorizontalStrut(16));
		layoutComponent.add(topButton);
		buttonsPanel.add(layoutComponent);
		// buttonsPanel.add(prevButton);
		// buttonsPanel.add(topButton);
		// buttonsPanel.add(nextButton);
		buttonsPanel.add(Box.createHorizontalGlue());
		historyPanel.add(historyLabel);
		add(buttonsPanel, BorderLayout.NORTH);
		add(historyPanel, BorderLayout.CENTER);
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				prevButton.setEnabled(!model.isFirstComponent());
				topButton.setEnabled(!model.isTopComponent());
				nextButton.setEnabled(!model.isLastComponent());
			}
		});
	}

	private String getHistory(final NavigationModel model) {
		return model.getHistory();
	}

	private class NextAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * @throws IOException
		 */
		public NextAction() throws IOException {
			super(null, new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/right-icon.png"))));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			model.nextComponent();
		}
	}

	private class TopAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * @throws IOException
		 */
		public TopAction() throws IOException {
			super(TwisterSwingResources.getInstance().getString("action.topComponent"), null);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			model.topComponent();
		}
	}

	private class PrevAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * @throws IOException
		 */
		public PrevAction() throws IOException {
			super(null, new ImageIcon(ImageIO.read(TwisterConfigPanel.class.getResourceAsStream("/icons/left-icon.png"))));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			model.prevComponent();
		}
	}

	/**
	 * @param button
	 */
	public void addButton(final JButton button) {
		buttonsPanel.add(button);
	}

	/**
	 * @param button
	 */
	public void removeButton(final JButton button) {
		buttonsPanel.remove(button);
	}
}
