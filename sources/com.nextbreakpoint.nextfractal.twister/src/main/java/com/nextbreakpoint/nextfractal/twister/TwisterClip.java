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
package com.nextbreakpoint.nextfractal.twister;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class TwisterClip implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<TwisterSequence> sequences = new ArrayList<TwisterSequence>();

	/**
	 * @param sequence
	 */
	public void addSequence(final TwisterSequence sequence) {
		sequences.add(sequence);
	}

	/**
	 * @param sequence
	 */
	public void removeSequence(final TwisterSequence sequence) {
		sequences.remove(sequence);
	}

	/**
	 * @param index
	 * @return
	 */
	public TwisterSequence getSequence(final int index) {
		return sequences.get(index);
	}

	/**
	 * @return
	 */
	public int getSequenceCount() {
		return sequences.size();
	}
}
