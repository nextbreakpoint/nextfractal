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
package com.nextbreakpoint.nextfractal.core.media;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

final class LayerList extends LinkedList<Layer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LayerList() {
	}

	public LayerList(final Collection<Layer> c) {
		try {
			final Iterator<Layer> i = c.iterator();
			while (i.hasNext()) {
				final Layer o = i.next();
				this.add((Layer) o.clone());
			}
		}
		catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object clone() {
		return new LayerList(this);
	}
}
