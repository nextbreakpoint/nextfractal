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
package com.nextbreakpoint.nextfractal.core.runtime.scripting;

/**
 * @author Andrea Medeghini
 */
public interface JSNode {
	/**
	 * @return the nodeId.
	 */
	public String getId();

	/**
	 * @return
	 */
	public String getPath();

	/**
	 * @return
	 */
	public String getLabel();

	/**
	 * @return
	 */
	public String getClassId();

	/**
	 * @return true if the node is editable.
	 */
	public boolean isEditable();

	/**
	 * @return true if the node is mutable.
	 */
	public boolean isMutable();

	/**
	 * @return true if the node is an attribute.
	 */
	public boolean isAttribute();

	/**
	 * Returns the previous node value.
	 * 
	 * @return the previous node value.
	 */
	public JSNodeValue getPreviousValue();

	/**
	 * Returns the node value.
	 * 
	 * @return the node value.
	 */
	public JSNodeValue getValue();

	/**
	 * Returns true if value is set.
	 * 
	 * @return true if value is set.
	 */
	public boolean hasValue();

	/**
	 * Creates the node value.
	 * 
	 * @param args the constructor arguments.
	 * @return
	 * @throws JSException
	 */
	public JSNodeValue createValueByArgs(final Object... args) throws JSException;

	/**
	 * Sets the node value.
	 * 
	 * @param value the node value to set.
	 * @throws JSException
	 */
	public void setValue(final JSNodeValue value) throws JSException;

	/**
	 * Sets the node value.
	 * 
	 * @param args the constructor arguments.
	 * @throws JSException
	 */
	public void setValueByArgs(final Object... args) throws JSException;

	/**
	 * @return the node index.
	 */
	public int getIndex();

	/**
	 * Returns the number of childs.
	 * 
	 * @return the number of childs.
	 */
	public int getChildNodeCount();

	/**
	 * @param index
	 * @return the child.
	 */
	public JSNode getChildNode(final int index);

	/**
	 * @param value
	 * @throws JSException
	 */
	public void appendChildNode(final JSNodeValue value) throws JSException;

	/**
	 * @param index
	 * @param value
	 * @throws JSException
	 */
	public void insertChildNodeBefore(final int index, final JSNodeValue value) throws JSException;

	/**
	 * @param index
	 * @param value
	 * @throws JSException
	 */
	public void insertChildNodeAfter(final int index, final JSNodeValue value) throws JSException;

	/**
	 * @param index
	 * @param value
	 * @throws JSException
	 */
	public void insertChildNodeAt(final Integer index, final JSNodeValue value) throws JSException;

	/**
	 * @param index
	 * @throws JSException
	 */
	public void removeChildNode(final int index) throws JSException;

	/**
	 * @param index
	 * @throws JSException
	 */
	public void moveUpChildNode(final int index) throws JSException;

	/**
	 * @param index
	 * @throws JSException
	 */
	public void moveDownChildNode(final int index) throws JSException;

	/**
	 * @throws JSException
	 */
	public void removeAllChildNodes() throws JSException;

	/**
	 * @return the parent node.
	 */
	public JSNode getParentNode();

	/**
	 * @return
	 */
	public String dump();

	/**
	 * @param path
	 * @return
	 */
	public JSNode getNodeByPath(String path);
}
