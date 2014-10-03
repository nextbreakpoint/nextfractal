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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.RenderService.ServiceCallback;
import com.nextbreakpoint.nextfractal.queue.clip.ClipPreview;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;

/**
 * @author Andrea Medeghini
 */
public class RenderClipTableModel extends ServiceAdapter implements TableModel {
	private static final Logger logger = Logger.getLogger(RenderClipTableModel.class.getName());
	private static final long serialVersionUID = 1L;
	public static final int PREVIEW = 0;
	public static final int CLIPID = 1;
	public static final int NAME = 2;
	public static final int DESCRIPTION = 3;
	public static final int DURATION = 4;
	public static final int STATUS = 5;
	private final List<TableModelListener> listeners;
	private final List<RenderClipDataRow> model;
	private final RenderService service;

	/**
	 * @param service
	 */
	public RenderClipTableModel(final RenderService service) {
		listeners = new ArrayList<TableModelListener>();
		model = new ArrayList<RenderClipDataRow>();
		this.service = service;
		service.addServiceListener(this);
		this.reload();
	}

	/**
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void addTableModelListener(final TableModelListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void removeTableModelListener(final TableModelListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param e
	 */
	protected void fireTableChanged(final TableModelEvent e) {
		for (final TableModelListener listener : listeners) {
			listener.tableChanged(e);
		}
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		switch (columnIndex) {
			case PREVIEW: {
				return Image.class;
			}
			case CLIPID: {
				return String.class;
			}
			case NAME: {
				return String.class;
			}
			case DESCRIPTION: {
				return String.class;
			}
			case DURATION: {
				return String.class;
			}
			case STATUS: {
				return String.class;
			}
			default: {
				break;
			}
		}
		return null;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
			case PREVIEW: {
				return TwisterSwingResources.getInstance().getString("column.preview");
			}
			case CLIPID: {
				return TwisterSwingResources.getInstance().getString("column.id");
			}
			case NAME: {
				return TwisterSwingResources.getInstance().getString("column.name");
			}
			case DESCRIPTION: {
				return TwisterSwingResources.getInstance().getString("column.description");
			}
			case DURATION: {
				return TwisterSwingResources.getInstance().getString("column.duration");
			}
			case STATUS: {
				return TwisterSwingResources.getInstance().getString("column.status");
			}
			default: {
				break;
			}
		}
		return null;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return model.size();
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final RenderClipDataRow dataRow = model.get(rowIndex);
		switch (columnIndex) {
			case PREVIEW: {
				ClipPreview preview = service.getClipPreview(dataRow.getClipId());
				if (preview != null) {
					return preview.getImage();
				}
			}
			case CLIPID: {
				return String.valueOf(dataRow.getClipId());
			}
			case NAME: {
				return dataRow.getClipName();
			}
			case DESCRIPTION: {
				return dataRow.getDescription();
			}
			case DURATION: {
				final long h = dataRow.getDuration() / 3600000;
				final long t1 = dataRow.getDuration() % 3600000;
				final long m = t1 / 60000;
				final long t2 = t1 % 60000;
				final long s = t2 / 1000;
				final long ms = t2 % 1000;
				return String.format("%d:%02d:%02d:%03d", h, m, s, ms);
			}
			case STATUS: {
				return String.valueOf(dataRow.getStatus() == 0 ? TwisterSwingResources.getInstance().getString("label.unlocked") : TwisterSwingResources.getInstance().getString("label.locked") + " (" + dataRow.getStatus() + ")");
			}
			default: {
				break;
			}
		}
		return null;
	}

	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		// if (model.get(rowIndex).getStatus() != 0) {
		// return false;
		// }
		// switch (columnIndex) {
		// case NAME: {
		// return true;
		// }
		// case DESCRIPTION: {
		// return true;
		// }
		// default: {
		// break;
		// }
		// }
		return false;
	}

	/**
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		// final MovieClipDataRow dataRow = model.get(rowIndex);
		// if (aValue != null) {
		// switch (columnIndex) {
		// case NAME: {
		// dataRow.setClipName((String) aValue);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, MovieClipTableModel.NAME, TableModelEvent.UPDATE));
		// break;
		// }
		// case DESCRIPTION: {
		// dataRow.setDescription((String) aValue);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, MovieClipTableModel.DESCRIPTION, TableModelEvent.UPDATE));
		// break;
		// }
		// default: {
		// break;
		// }
		// }
		// }
	}

	/**
	 * 
	 */
	public void clear() {
		final int lastRow = model.size() - 1;
		model.clear();
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
		}
	}

	/**
	 * 
	 */
	public void reload() {
		service.execute(new ServiceCallback<List<RenderClipDataRow>>() {
			/**
			 * @param value
			 */
			@Override
			public void executed(final List<RenderClipDataRow> clips) {
				try {
					if (RenderClipTableModel.logger.isLoggable(Level.INFO)) {
						RenderClipTableModel.logger.info("Reload executed");
					}
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderClipTableModel.this.reload(clips);
							}
												}, true);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.service.RenderService.ServiceCallback#failed(java.lang.Throwable)
			 */
			@Override
			public void failed(final Throwable throwable) {
				try {
					RenderClipTableModel.logger.log(Level.WARNING, "Reload failed", throwable);
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderClipTableModel.this.reload(new LinkedList<RenderClipDataRow>());
							}
												}, true);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.service.RenderService.ServiceCallback#execute(com.nextbreakpoint.nextfractal.service.LibraryService)
			 */
			@Override
			public List<RenderClipDataRow> execute(final LibraryService service) throws Exception {
				return service.loadClips();
			}
		});
	}

	private void reload(final List<RenderClipDataRow> clips) {
		int lastRow = model.size() - 1;
		model.clear();
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
		}
		for (final RenderClipDataRow clip : clips) {
			model.add(clip);
		}
		lastRow = model.size() - 1;
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
		}
	}

	// private void saveRow(final int rowIndex) {
	// final MovieClipDataRow dataRow = model.get(rowIndex);
	// service.saveClip(new ServiceVoidCallback() {
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#executed()
	// */
	// public void executed() {
	// if (MovieClipTableModel.logger.isLoggable(Level.INFO)) {
	// MovieClipTableModel.logger.info("Save executed");
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#failed(java.lang.Throwable)
	// */
	// public void failed(final Throwable throwable) {
	// MovieClipTableModel.logger.log(Level.WARNING, "Save failed", throwable);
	// }
	// }, dataRow);
	// }
	/**
	 * @param rowIndex
	 * @return
	 */
	public RenderClipDataRow getClip(final int rowIndex) {
		return model.get(rowIndex);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#clipCreated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipCreated(final RenderClipDataRow clip) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getClipId() == clip.getClipId()) {
						model.remove(rowIndex);
						RenderClipTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				final int rowIndex = model.size();
				model.add(clip);
				RenderClipTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#clipDeleted(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipDeleted(final RenderClipDataRow clip) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getClipId() == clip.getClipId()) {
						model.remove(rowIndex);
						RenderClipTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#clipUpdated(com.nextbreakpoint.nextfractal.service.clip.RenderClipDataRow)
	 */
	@Override
	public void clipUpdated(final RenderClipDataRow clip) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getClipId() == clip.getClipId()) {
						model.set(rowIndex, clip);
						RenderClipTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
						break;
					}
				}
				}
						}, true);
	}

	private class TableModelAdapter implements Runnable {
		public TableModel tableModel;

		public TableModelAdapter(final TableModel tableModel) {
			this.tableModel = tableModel;
		}

		@Override
		public void run() {
		}
	}
}
