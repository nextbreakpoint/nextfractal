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
package com.nextbreakpoint.nextfractal.core.runtime.model;

import com.nextbreakpoint.nextfractal.core.CoreResources;

public class DefaultRootNode extends RootNode {
	private static final String NODE_LABEL = CoreResources.getInstance().getString("node.label.Root");
	public static final String NODE_CLASS = "node.class.Root";
	public static final String NODE_ID = "root";

	/**
	 * 
	 */
	public DefaultRootNode() {
		super(DefaultRootNode.NODE_ID);
		setNodeLabel(DefaultRootNode.NODE_LABEL);
		setNodeClass(DefaultRootNode.NODE_CLASS);
	}
}
