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
package com.nextbreakpoint.nextfractal.core;

import java.io.Serializable;


/**
 * @author Andrea Medeghini
 */
public class Tile implements Serializable {
	private static final long serialVersionUID = 1L;
	private final IntegerVector2D imageSize;
	private final IntegerVector2D tileSize;
	private final IntegerVector2D tileOffset;
	private final IntegerVector2D tileBorder;

	/**
	 * @param imageSize
	 * @param tileSize
	 * @param tileOffset
	 * @param tileBorder
	 */
	public Tile(final IntegerVector2D imageSize, final IntegerVector2D tileSize, final IntegerVector2D tileOffset, final IntegerVector2D tileBorder) {
		this.imageSize = imageSize;
		this.tileSize = tileSize;
		this.tileOffset = tileOffset;
		this.tileBorder = tileBorder;
	}

	/**
	 * @return the imageSize
	 */
	public IntegerVector2D getImageSize() {
		return imageSize;
	}

	/**
	 * @return the tileBorder
	 */
	public IntegerVector2D getTileBorder() {
		return tileBorder;
	}

	/**
	 * @return the tileOffset
	 */
	public IntegerVector2D getTileOffset() {
		return tileOffset;
	}

	/**
	 * @return the tileSize
	 */
	public IntegerVector2D getTileSize() {
		return tileSize;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(imageSize.toString());
		builder.append(", ");
		builder.append(tileSize.toString());
		builder.append(", ");
		builder.append(tileOffset.toString());
		builder.append(", ");
		builder.append(tileBorder.toString());
		return builder.toString();
	}
}
