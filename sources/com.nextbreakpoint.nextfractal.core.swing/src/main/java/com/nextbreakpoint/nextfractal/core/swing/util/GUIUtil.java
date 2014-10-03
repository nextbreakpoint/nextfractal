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
package com.nextbreakpoint.nextfractal.core.swing.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * @author Andrea Medeghini
 */
public class GUIUtil {
	private GUIUtil() {
	}

	/**
	 * @param frame
	 */
	public static void centerWindow(final Window frame) {
		int x = (int) (Math.rint(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2);
		int y = (int) (Math.rint(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	/**
	 * @param frame
	 * @param location
	 * @param bounds
	 */
	public static void centerWindow(final Window frame, final Point location, final Rectangle bounds) {
		int x = (int) (Math.rint(location.getX() + ((bounds.getWidth() - frame.getWidth()) / 2)));
		int y = (int) (Math.rint(location.getY() + ((bounds.getHeight() - frame.getHeight()) / 2)));
		frame.setLocation(x, y);
	}

	/**
	 * @param frame
	 * @return
	 */
	public static Point getCenterLocation(final Window frame) {
		int x = (int) (Math.rint(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2);
		int y = (int) (Math.rint(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2);
		return new Point(x, y);
	}

	/**
	 * @param task
	 */
	public static void executeTask(final Runnable task, final boolean async) {
		if (async) {
			SwingUtilities.invokeLater(task);
		}
		else {
			if (SwingUtilities.isEventDispatchThread()) {
				task.run();
			}
			else {
				try {
					SwingUtilities.invokeAndWait(task);
				}
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
