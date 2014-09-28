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
package com.nextbreakpoint.nextfractal.twister.util;

import java.io.Serializable;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;

/**
 * @author Andrea Medeghini
 */
public class View implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private final DoubleVector4D position;
	private final DoubleVector4D rotation;
	private final IntegerVector4D status;

	/**
	 * @param status
	 * @param position
	 * @param rotation
	 */
	public View(final IntegerVector4D status, final DoubleVector4D position, final DoubleVector4D rotation) {
		this.status = status;
		this.position = position;
		this.rotation = rotation;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("status = ");
		builder.append(status);
		builder.append(", position = ");
		builder.append(position);
		builder.append(", rotation = ");
		builder.append(rotation);
		return builder.toString();
	}

	/**
	 * @return the dynamic
	 */
	public IntegerVector4D getStatus() {
		return status;
	}

	/**
	 * @return the position
	 */
	public DoubleVector4D getPosition() {
		return position;
	}

	/**
	 * @return the rotation
	 */
	public DoubleVector4D getRotation() {
		return rotation;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final View other = (View) obj;
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		}
		else if (!position.equals(other.position)) {
			return false;
		}
		if (rotation == null) {
			if (other.rotation != null) {
				return false;
			}
		}
		else if (!rotation.equals(other.rotation)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public View clone() {
		return new View(status, position, rotation);
	}
}
