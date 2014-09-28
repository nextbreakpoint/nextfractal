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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Andrea Medeghini
 */
public class NavigationModel {
	private final List<Component> history = new ArrayList<Component>();
	private final List<ChangeListener> listeners = new LinkedList<ChangeListener>();
	private final ChangeEvent event = new ChangeEvent(this);
	private final NavigationContainer container;
	private int index = -1;

	/**
	 * @param container
	 */
	public NavigationModel(final NavigationContainer container) {
		this.container = container;
	}

	/**
	 * @param listener
	 */
	public void addChangeListener(final ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeChangeListener(final ChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param e
	 */
	protected void fireStateChanged(final ChangeEvent e) {
		for (final ChangeListener listener : listeners) {
			listener.stateChanged(e);
		}
	}

	/**
	 * @param c
	 */
	public void setComponent(final Component c) {
		int i = 0;
		final Iterator<Component> it = history.iterator();
		while (it.hasNext()) {
			if ((it.next() == c) || (i > index)) {
				it.remove();
				while (it.hasNext()) {
					it.next();
					it.remove();
				}
				break;
			}
			i += 1;
		}
		history.add(c);
		index = history.size() - 1;
		container.loadComponent(history.get(index), 0);
		fireStateChanged(event);
	}

	/**
	 * 
	 */
	public void nextComponent() {
		if (history.size() == 0) {
			return;
		}
		if (index < history.size() - 1) {
			index += 1;
			container.loadComponent(history.get(index), 0);
			fireStateChanged(event);
		}
	}

	/**
	 * 
	 */
	public void prevComponent() {
		if (history.size() == 0) {
			return;
		}
		if (index > 0) {
			index -= 1;
			container.loadComponent(history.get(index), 0);
			fireStateChanged(event);
		}
	}

	/**
	 * 
	 */
	public void topComponent() {
		if (history.size() == 0) {
			return;
		}
		index = 0;
		container.loadComponent(history.get(index), 0);
		fireStateChanged(event);
	}

	/**
	 * @return
	 */
	public String getHistory() {
		final StringBuilder builder = new StringBuilder();
		if ((history.size() > 0) && (index >= 0)) {
			builder.append("> ");
			builder.append(history.get(0).getName());
			for (int i = 1; i <= index; i++) {
				builder.append(" > ");
				builder.append(history.get(i).getName());
			}
		}
		return builder.toString();
	}

	/**
	 * @param c
	 */
	public void removeComponent(final Component c) {
		int i = 0;
		final Iterator<Component> it = history.iterator();
		while (it.hasNext()) {
			if ((it.next() == c) || (i > index)) {
				it.remove();
				while (it.hasNext()) {
					it.next();
					it.remove();
				}
				break;
			}
			i += 1;
		}
		index = history.size() - 1;
		if (index >= 0) {
			container.loadComponent(history.get(index), 0);
		}
		fireStateChanged(event);
	}

	/**
	 * @param amount
	 */
	public void resize(final int amount) {
		if (index >= 0) {
			container.loadComponent(history.get(index), amount);
		}
		fireStateChanged(event);
	}

	/**
	 * @return
	 */
	public boolean isFirstComponent() {
		return index == 0;
	}

	/**
	 * @return
	 */
	public boolean isTopComponent() {
		return index == 0;
	}

	/**
	 * @return
	 */
	public boolean isLastComponent() {
		return index == history.size() - 1;
	}

	/**
	 * @param c
	 */
	public void restoreComponent(final Component c) {
		int i = 0;
		final Iterator<Component> it = history.iterator();
		while (it.hasNext()) {
			if (it.next() == c) {
				while (it.hasNext()) {
					it.next();
					it.remove();
				}
				break;
			}
			i += 1;
		}
		index = history.size() - 1;
		if (index >= 0) {
			container.loadComponent(history.get(index), 0);
		}
		fireStateChanged(event);
	}
}
