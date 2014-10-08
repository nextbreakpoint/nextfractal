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
package com.nextbreakpoint.nextfractal.twister.ui.swing;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;

/**
 * @author Andrea Medeghini
 */
public class ServiceTable extends JTable {
	private static final long serialVersionUID = 1L;

	public ServiceTable() {
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public ServiceTable(final int numRows, final int numColumns) {
		super(numRows, numColumns);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public ServiceTable(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public ServiceTable(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public ServiceTable(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public ServiceTable(final TableModel dm) {
		super(dm);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	@SuppressWarnings("unchecked")
	public ServiceTable(final Vector rowData, final Vector columnNames) {
		super(rowData, columnNames);
		setFont(GUIFactory.SMALL_FONT);
		getTableHeader().setFont(GUIFactory.SMALL_FONT);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	/**
	 * @param i
	 * @return
	 */
	@Override
	public int convertRowIndexToModel(final int i) {
		return i;
	}
}
