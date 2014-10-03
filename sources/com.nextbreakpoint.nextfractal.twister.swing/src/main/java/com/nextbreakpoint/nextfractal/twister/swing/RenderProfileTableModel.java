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
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;

/**
 * @author Andrea Medeghini
 */
public class RenderProfileTableModel extends ServiceAdapter implements TableModel {
	private static final Logger logger = Logger.getLogger(RenderProfileTableModel.class.getName());
	private static final long serialVersionUID = 1L;
	public static final int PROFILEID = 0;
	public static final int NAME = 1;
	public static final int WIDTH = 2;
	public static final int HEIGHT = 3;
	public static final int OFFSET_X = 4;
	public static final int OFFSET_Y = 5;
	public static final int FRAME_RATE = 6;
	public static final int START_TIME = 7;
	public static final int STOP_TIME = 8;
	public static final int QUALITY = 9;
	public static final int STATUS = 10;
	public static final int PERCENT = 11;
	private final List<TableModelListener> listeners;
	private final List<RenderProfileDataRow> model;
	private final RenderService service;

	/**
	 * @param service
	 */
	public RenderProfileTableModel(final RenderService service) {
		listeners = new ArrayList<TableModelListener>();
		model = new ArrayList<RenderProfileDataRow>();
		this.service = service;
		service.addServiceListener(this);
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
			case PROFILEID: {
				return String.class;
			}
			case NAME: {
				return String.class;
			}
			case WIDTH: {
				return String.class;
			}
			case HEIGHT: {
				return String.class;
			}
			case OFFSET_X: {
				return String.class;
			}
			case OFFSET_Y: {
				return String.class;
			}
			case FRAME_RATE: {
				return String.class;
			}
			case START_TIME: {
				return String.class;
			}
			case STOP_TIME: {
				return String.class;
			}
			case QUALITY: {
				return String.class;
			}
			case STATUS: {
				return String.class;
			}
			case PERCENT: {
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
		return 11;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
			case PROFILEID: {
				return TwisterSwingResources.getInstance().getString("column.id");
			}
			case NAME: {
				return TwisterSwingResources.getInstance().getString("column.name");
			}
			case WIDTH: {
				return TwisterSwingResources.getInstance().getString("column.width");
			}
			case HEIGHT: {
				return TwisterSwingResources.getInstance().getString("column.height");
			}
			case OFFSET_X: {
				return TwisterSwingResources.getInstance().getString("column.offsetX");
			}
			case OFFSET_Y: {
				return TwisterSwingResources.getInstance().getString("column.offsetY");
			}
			case FRAME_RATE: {
				return TwisterSwingResources.getInstance().getString("column.frameRate");
			}
			case START_TIME: {
				return TwisterSwingResources.getInstance().getString("column.startTime");
			}
			case STOP_TIME: {
				return TwisterSwingResources.getInstance().getString("column.stopTime");
			}
			case QUALITY: {
				return TwisterSwingResources.getInstance().getString("column.quality");
			}
			case STATUS: {
				return TwisterSwingResources.getInstance().getString("column.status");
			}
			case PERCENT: {
				return TwisterSwingResources.getInstance().getString("column.percentage");
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
		final RenderProfileDataRow dataRow = model.get(rowIndex);
		switch (columnIndex) {
			case PROFILEID: {
				return String.valueOf(dataRow.getProfileId());
			}
			case NAME: {
				return dataRow.getProfileName();
			}
			case WIDTH: {
				return String.valueOf(dataRow.getImageWidth()) + " px";
			}
			case HEIGHT: {
				return String.valueOf(dataRow.getImageHeight()) + " px";
			}
			case OFFSET_X: {
				return String.valueOf(dataRow.getOffsetX()) + " px";
			}
			case OFFSET_Y: {
				return String.valueOf(dataRow.getOffsetY()) + " px";
			}
			case FRAME_RATE: {
				return String.valueOf(dataRow.getFrameRate()) + " fps";
			}
			case START_TIME: {
				final long h = dataRow.getStartTime() / 3600;
				final long t = dataRow.getStartTime() % 3600;
				final long m = t / 60;
				final long s = t % 60;
				return String.format("%d:%02d:%02d", h, m, s);
			}
			case STOP_TIME: {
				final long h = dataRow.getStopTime() / 3600;
				final long t = dataRow.getStopTime() % 3600;
				final long m = t / 60;
				final long s = t % 60;
				return String.format("%d:%02d:%02d", h, m, s);
			}
			case QUALITY: {
				return String.valueOf(dataRow.getQuality()) + "%";
			}
			case STATUS: {
				return String.valueOf(dataRow.getStatus() == 0 ? TwisterSwingResources.getInstance().getString("label.unlocked") : TwisterSwingResources.getInstance().getString("label.locked") + " (" + dataRow.getStatus() + ")");
			}
			case PERCENT: {
				if (dataRow.getJobCreated() > 0) {
					return String.format("%.0f", (100f * dataRow.getJobStored()) / dataRow.getJobCreated()) + "%";
				}
				else {
					return "0%";
				}
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
		// case WIDTH: {
		// return true;
		// }
		// case HEIGHT: {
		// return true;
		// }
		// case OFFSET_X: {
		// return true;
		// }
		// case OFFSET_Y: {
		// return true;
		// }
		// case FRAME_RATE: {
		// return true;
		// }
		// case START_TIME: {
		// return true;
		// }
		// case STOP_TIME: {
		// return true;
		// }
		// case QUALITY: {
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
		// final RenderProfileDataRow dataRow = model.get(rowIndex);
		// if (aValue != null) {
		// switch (columnIndex) {
		// case NAME: {
		// dataRow.setProfileName((String) aValue);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.NAME, TableModelEvent.UPDATE));
		// break;
		// }
		// case WIDTH: {
		// dataRow.setImageWidth((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.WIDTH, TableModelEvent.UPDATE));
		// break;
		// }
		// case HEIGHT: {
		// dataRow.setImageHeight((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.HEIGHT, TableModelEvent.UPDATE));
		// break;
		// }
		// case OFFSET_X: {
		// dataRow.setOffsetX((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.WIDTH, TableModelEvent.UPDATE));
		// break;
		// }
		// case OFFSET_Y: {
		// dataRow.setOffsetY((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.HEIGHT, TableModelEvent.UPDATE));
		// break;
		// }
		// case FRAME_RATE: {
		// dataRow.setFrameRate((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.FRAME_RATE, TableModelEvent.UPDATE));
		// break;
		// }
		// case START_TIME: {
		// dataRow.setStartTime((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.START_TIME, TableModelEvent.UPDATE));
		// break;
		// }
		// case STOP_TIME: {
		// dataRow.setStopTime((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.STOP_TIME, TableModelEvent.UPDATE));
		// break;
		// }
		// case QUALITY: {
		// dataRow.setQuality((Integer) aValue);
		// dataRow.setJobCreated(0);
		// dataRow.setJobCreated(0);
		// saveRow(rowIndex);
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, RenderProfileTableModel.QUALITY, TableModelEvent.UPDATE));
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
	 * @param clipId
	 */
	public void reload(final int clipId) {
		service.execute(new ServiceCallback<List<RenderProfileDataRow>>() {
			/**
			 * @param value
			 */
			@Override
			public void executed(final List<RenderProfileDataRow> profiles) {
				try {
					if (RenderProfileTableModel.logger.isLoggable(Level.INFO)) {
						RenderProfileTableModel.logger.info("Reload executed");
					}
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderProfileTableModel.this.reload(profiles);
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
					RenderProfileTableModel.logger.log(Level.WARNING, "Reload failed", throwable);
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderProfileTableModel.this.reload(new LinkedList<RenderProfileDataRow>());
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
			public List<RenderProfileDataRow> execute(final LibraryService service) throws Exception {
				return service.loadProfiles(clipId);
			}
		});
	}

	private void reload(final List<RenderProfileDataRow> profiles) {
		int lastRow = model.size() - 1;
		model.clear();
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
		}
		for (final RenderProfileDataRow profile : profiles) {
			model.add(profile);
		}
		lastRow = model.size() - 1;
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
		}
	}

	// private void saveRow(final int rowIndex) {
	// final RenderProfileDataRow dataRow = model.get(rowIndex);
	// service.saveProfile(new ServiceVoidCallback() {
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#executed()
	// */
	// public void executed() {
	// if (RenderProfileTableModel.logger.isInfoEnabled()) {
	// RenderProfileTableModel.logger.info("Save executed");
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#failed(java.lang.Throwable)
	// */
	// public void failed(final Throwable throwable) {
	// RenderProfileTableModel.logger.error("Save failed", throwable);
	// }
	// }, dataRow);
	// }
	/**
	 * @param rowIndex
	 * @return
	 */
	public RenderProfileDataRow getProfile(final int rowIndex) {
		return model.get(rowIndex);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#profileCreated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileCreated(final RenderProfileDataRow profile) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getProfileId() == profile.getProfileId()) {
						model.remove(rowIndex);
						RenderProfileTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				final int rowIndex = model.size();
				model.add(profile);
				RenderProfileTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#profileDeleted(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileDeleted(final RenderProfileDataRow profile) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getProfileId() == profile.getProfileId()) {
						model.remove(rowIndex);
						RenderProfileTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#profileUpdated(com.nextbreakpoint.nextfractal.service.profile.RenderProfileDataRow)
	 */
	@Override
	public void profileUpdated(final RenderProfileDataRow profile) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getProfileId() == profile.getProfileId()) {
						model.set(rowIndex, profile);
						RenderProfileTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
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
