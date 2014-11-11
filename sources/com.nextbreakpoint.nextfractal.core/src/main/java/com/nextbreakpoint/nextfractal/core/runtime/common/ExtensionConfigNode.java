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
package com.nextbreakpoint.nextfractal.core.runtime.common;

import com.nextbreakpoint.nextfractal.core.CoreResources;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class ExtensionConfigNode<T extends ExtensionConfig> extends DefaultNode {
	public static final String NODE_CLASS = "node.class.ExtensionConfigElement";
	public static final String NODE_LABEL = CoreResources.getInstance().getString("node.label.ExtensionConfigElement");
	private final T config;

	/**
	 * @param config
	 */
	public ExtensionConfigNode(final T config) {
		super(config.getExtensionId());
		this.config = config;
		setNodeClass(ExtensionConfigNode.NODE_CLASS);
		setNodeLabel(ExtensionConfigNode.NODE_LABEL);
		setNodeValue(this.createNodeValue(config));
		this.createChildNodes();
	}

	/**
	 * @return the config
	 */
	public T getConfig() {
		return this.config;
	}

	/**
	 * 
	 */
	protected void createChildNodes() {
	}

	/**
	 * @param value
	 * @return
	 */
	protected NodeValue<?> createNodeValue(final T value) {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return null;
	}
}
