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
package com.nextbreakpoint.nextfractal.core.ui.swing.osgi;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * A model for extension points trees.
 * 
 * @author Andrea Medeghini
 */
public class IExtensionPointTreeModel extends DefaultTreeModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new model.
	 * 
	 * @param root the root node.
	 */
	public IExtensionPointTreeModel(final DefaultMutableTreeNode root) {
		super(root);
//		final IExtensionRegistry registry = Platform.getExtensionRegistry();
//		final IExtensionPoint[] extensionPoints = registry.getExtensionPoints();
//		if (extensionPoints != null) {
//			Arrays.sort(extensionPoints, new IExtensionPointComparator());
//			for (final IExtensionPoint extensionPoint : extensionPoints) {
//				root.add(createExtensionPointNode(extensionPoint));
//			}
//		}
	}

//	/**
//	 * Creates a new extension point node.
//	 * 
//	 * @param extensionPoint the extension point.
//	 * @return a new extension point node.
//	 */
//	protected DefaultMutableTreeNode createExtensionPointNode(final IExtensionPoint extensionPoint) {
//		final DefaultMutableTreeNode extensionPointNode = new DefaultMutableTreeNode(extensionPoint);
//		final IExtension[] extensions = extensionPoint.getExtensions();
//		if (extensions != null) {
//			Arrays.sort(extensions, new IExtensionComparator());
//			for (final IExtension extension : extensions) {
//				extensionPointNode.add(createExtensionNode(extension));
//			}
//		}
//		return extensionPointNode;
//	}
//
//	/**
//	 * Creates a new extension node.
//	 * 
//	 * @param extension the extension.
//	 * @return a new extension node.
//	 */
//	protected DefaultMutableTreeNode createExtensionNode(final IExtension extension) {
//		final DefaultMutableTreeNode extensionNode = new DefaultMutableTreeNode(extension);
//		final IConfigurationElement[] cfgElements = extension.getConfigurationElements();
//		if (cfgElements != null) {
//			Arrays.sort(cfgElements, new IConfigurationElementComparator());
//			for (final IConfigurationElement cfgElement : cfgElements) {
//				extensionNode.add(createConfigurationElementNode(cfgElement));
//			}
//		}
//		return extensionNode;
//	}
//
//	/**
//	 * Creates a new configuration element node.
//	 * 
//	 * @param cfgElement the configuration element.
//	 * @return a new configuration element node.
//	 */
//	protected DefaultMutableTreeNode createConfigurationElementNode(final IConfigurationElement cfgElement) {
//		final DefaultMutableTreeNode cfgElementNode = new DefaultMutableTreeNode(cfgElement);
//		final String[] attributeNames = cfgElement.getAttributeNames();
//		if (attributeNames != null) {
//			Arrays.sort(attributeNames);
//			for (final String attributeName : attributeNames) {
//				cfgElementNode.add(createAttributeNode(attributeName, cfgElement.getAttribute(attributeName)));
//			}
//		}
//		return cfgElementNode;
//	}

	/**
	 * Creates a new attribute node.
	 * 
	 * @param attributeName the attribute name.
	 * @param attributeValue the attribute value.
	 * @return a new attribute node.
	 */
	protected DefaultMutableTreeNode createAttributeNode(final String attributeName, final String attributeValue) {
		return new DefaultMutableTreeNode(attributeName + " = " + attributeValue);
	}
}
