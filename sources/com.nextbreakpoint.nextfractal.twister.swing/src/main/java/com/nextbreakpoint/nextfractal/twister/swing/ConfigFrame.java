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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import com.nextbreakpoint.nextfractal.core.swing.ViewContext;
import com.nextbreakpoint.nextfractal.core.swing.util.Buttons;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;

/**
 * @author Andrea Medeghini
 */
public class ConfigFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String CONFIG_FRAME_TITLE = "configFrame.title";
	private static final String CONFIG_FRAME_WIDTH = "configFrame.width";
	private static final String CONFIG_FRAME_MIN_HEIGHT = "configFrame.minHeight";
	private static final String CONFIG_FRAME_MAX_HEIGHT = "configFrame.maxHeight";
	private static final String CONFIG_FRAME_ICON = "configFrame.icon";
	private final NavigationPanel navigationPanel;
	private final TwisterConfigContext configContext;
	private TwisterConfigPanel configPanel;
	private final NavigationModel model;

	/**
	 * @param configContext
	 * @param config
	 * @param renderContext
	 * @param session
	 * @throws HeadlessException
	 */
	public ConfigFrame(final TwisterConfigContext configContext, final TwisterConfig config, final RenderContext renderContext, final NodeSession session) throws HeadlessException {
		this.configContext = configContext;
		setResizable(false);
		final DefaultNavigationContainer container = new DefaultNavigationContainer();
		model = new NavigationModel(container);
		container.setOpaque(false);
		container.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		navigationPanel = new NavigationPanel(model);
		final List<JButton> buttons = Buttons.createSegmentedButtons(1, null);
		buttons.get(0).setAction(new AdvancedConfigAction());
		buttons.get(0).setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.advancedConfig"));
		navigationPanel.addButton(buttons.get(0));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(navigationPanel, BorderLayout.NORTH);
		getContentPane().add(container, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(ConfigFrame.CONFIG_FRAME_TITLE));
		final URL resource = ConfigFrame.class.getClassLoader().getResource(TwisterSwingResources.getInstance().getString(ConfigFrame.CONFIG_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		configPanel = new TwisterConfigPanel(config, new DefaultViewContext(model), renderContext, session);
		configPanel.setName(TwisterSwingResources.getInstance().getString("name.config"));
		configPanel.setVisible(false);
		model.setComponent(configPanel);
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		setLocation(p);
	}

	/**
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (configPanel != null) {
			configPanel.dispose();
			configPanel = null;
		}
	}

	public void setup() {
		configPanel.setVisible(true);
	}

	private class DefaultNavigationContainer extends JScrollPane implements NavigationContainer {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public DefaultNavigationContainer() {
			setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.NavigationContainer#loadComponent(java.awt.Component, int)
		 */
		@Override
		public void loadComponent(final Component c, final int amount) {
			final int defaultWidth = Integer.parseInt(TwisterSwingResources.getInstance().getString(ConfigFrame.CONFIG_FRAME_WIDTH));
			final int defaultMinHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(ConfigFrame.CONFIG_FRAME_MIN_HEIGHT));
			final int defaultMaxHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(ConfigFrame.CONFIG_FRAME_MAX_HEIGHT));
			final int width = Integer.getInteger(ConfigFrame.CONFIG_FRAME_WIDTH, defaultWidth);
			final int minHeight = Integer.getInteger(ConfigFrame.CONFIG_FRAME_MIN_HEIGHT, defaultMinHeight);
			final int maxHeight = Integer.getInteger(ConfigFrame.CONFIG_FRAME_MAX_HEIGHT, defaultMaxHeight);
			final Dimension newSize = c.getPreferredSize();
			final Dimension size = new Dimension(width, 40 + Math.min(Math.max(navigationPanel.getHeight() + newSize.height + getInsets().top + getInsets().bottom + ConfigFrame.this.getInsets().top + ConfigFrame.this.getInsets().bottom + amount, minHeight), maxHeight));
			ConfigFrame.this.setSize(size);
			if (c != null) {
				getViewport().setView(c);
			}
		}
	}

	private class DefaultViewContext implements ViewContext {
		private final NavigationModel model;

		/**
		 * @param model
		 */
		public DefaultViewContext(final NavigationModel model) {
			this.model = model;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.ViewContext#setComponent(java.awt.Component)
		 */
		@Override
		public void setComponent(final Component c) {
			model.setComponent(c);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.ViewContext#removeComponent(java.awt.Component)
		 */
		@Override
		public void removeComponent(final Component c) {
			model.removeComponent(c);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.ViewContext#resize()
		 */
		@Override
		public void resize() {
			model.resize(0);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.ViewContext#resize(int)
		 */
		@Override
		public void resize(final int amount) {
			model.resize(amount + 20);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.swing.ViewContext#restoreComponent(java.awt.Component)
		 */
		@Override
		public void restoreComponent(final Component c) {
			model.restoreComponent(c);
		}
	}

	private class AdvancedConfigAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public AdvancedConfigAction() {
			super(TwisterSwingResources.getInstance().getString("action.advancedConfig"));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			configContext.openAdvancedConfigWindow();
		}
	}
}
