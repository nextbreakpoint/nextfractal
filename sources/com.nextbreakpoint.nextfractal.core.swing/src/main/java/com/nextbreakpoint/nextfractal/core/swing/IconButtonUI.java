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
package com.nextbreakpoint.nextfractal.core.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;

/**
 * @author Andrea Medeghini
 */
public class IconButtonUI extends BasicButtonUI {
	private IconButtonController listener;

	/**
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new IconButtonUI();
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		listener = null;
		super.finalize();
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		c.setBorder(null);
		listener = new IconButtonController((IconButton) c);
		installListeners((IconButton) c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallListeners((IconButton) c);
		listener = null;
		super.uninstallUI(c);
	}

	private void installListeners(final IconButton c) {
		c.addMouseListener(listener);
		c.addFocusListener(listener);
	}

	private void uninstallListeners(final IconButton c) {
		c.removeMouseListener(listener);
		c.removeFocusListener(listener);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		final IconButton button = (IconButton) c;
		final Insets insets = c.getInsets();
		final int w = button.getNormalImage().getWidth(null) + insets.left + insets.right;
		final int h = button.getNormalImage().getHeight(null) + insets.top + insets.bottom;
		return new Dimension(w, h);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		return getPreferredSize(c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		return getPreferredSize(c);
	}

	/**
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final IconButton button = (IconButton) c;
		// System.out.println(button.getModel().isPressed());
		final Insets insets = c.getInsets();
		// final Dimension size = c.getSize();
		if (!button.getModel().isEnabled()) {
			g.drawImage(button.getDisabledImage(), insets.left, insets.top, null);
		}
		else if (button.getModel().isPressed()) {
			g.drawImage(button.getFocusedImage(), insets.left, insets.top, null);
		}
		else if (button.getModel().isRollover()) {
			g.drawImage(button.getPressedImage(), insets.left, insets.top, null);
		}
		else if (button.getModel().isSelected()) {
			g.drawImage(button.getPressedImage(), insets.left, insets.top, null);
		}
		else if (button.hasFocus()) {
			g.drawImage(button.getFocusedImage(), insets.left, insets.top, null);
		}
		else {
			g.drawImage(button.getNormalImage(), insets.left, insets.top, null);
		}
	}

	private class IconButtonController implements ChangeListener, MouseListener, FocusListener {
		private final RepeatTask task = new RepeatTask();
		private final IconButton button;
		private Thread thread;
		private boolean running;
		private long repeatTime;

		/**
		 * @param button
		 */
		public IconButtonController(final IconButton button) {
			this.button = button;
		}

		public void stateChanged(final ChangeEvent e) {
		}

		public void mouseClicked(final MouseEvent e) {
		}

		public void mouseEntered(final MouseEvent e) {
			button.getModel().setRollover(true);
			if (button.getModel().isPressed()) {
				button.getModel().setArmed(true);
			}
			button.repaint();
		}

		public void mouseExited(final MouseEvent e) {
			if (thread != null) {
				running = false;
				thread.interrupt();
				try {
					thread.join();
				}
				catch (final InterruptedException x) {
				}
				thread = null;
			}
			button.getModel().setRollover(false);
			button.getModel().setArmed(false);
			button.repaint();
		}

		public void mousePressed(final MouseEvent e) {
			button.getModel().setSelected(true);
			button.getModel().setPressed(true);
			button.getModel().setArmed(true);
			button.requestFocus();
			button.repaint();
			if (e.isShiftDown()) {
				repeatTime = button.getRepeatTime() / 4;
			}
			else {
				repeatTime = button.getRepeatTime();
			}
			if ((repeatTime > 0) && (thread == null)) {
				thread = new Thread(task, "IconButtonUI Task");
				thread.setPriority(Thread.MIN_PRIORITY);
				thread.setDaemon(true);
				running = true;
				thread.start();
			}
		}

		public void mouseReleased(final MouseEvent e) {
			if (thread != null) {
				running = false;
				thread.interrupt();
				try {
					thread.join();
				}
				catch (final InterruptedException x) {
				}
				thread = null;
			}
			button.getModel().setSelected(false);
			button.getModel().setPressed(false);
			button.getModel().setArmed(false);
			button.repaint();
		}

		public void focusGained(final FocusEvent e) {
			button.repaint();
		}

		public void focusLost(final FocusEvent e) {
			button.repaint();
		}

		private class RepeatTask implements Runnable {
			/**
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				try {
					Thread.sleep(500);
					while (running) {
						GUIUtil.executeTask(new Runnable() {
							public void run() {
								button.getModel().setSelected(false);
								button.getModel().setPressed(false);
								button.getModel().setArmed(false);
								button.getModel().setSelected(true);
								button.getModel().setPressed(true);
								button.getModel().setArmed(true);
							}
						}, false);
						Thread.sleep(repeatTime);
					}
				}
				catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
