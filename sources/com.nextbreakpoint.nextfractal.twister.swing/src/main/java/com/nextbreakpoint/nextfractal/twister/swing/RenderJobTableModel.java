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
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;

/**
 * @author Andrea Medeghini
 */
public class RenderJobTableModel extends ServiceAdapter implements TableModel {
	private static final Logger logger = Logger.getLogger(RenderJobTableModel.class.getName());
	private static final long serialVersionUID = 1L;
	public static final int JOBID = 0;
	public static final int NAME = 1;
	public static final int TYPE = 2;
	public static final int FRAME_NUMBER = 3;
	public static final int TILE_WIDTH = 4;
	public static final int TILE_HEIGHT = 5;
	public static final int TILE_OFFSETX = 6;
	public static final int TILE_OFFSETY = 7;
	public static final int STATUS = 8;
	private final List<TableModelListener> listeners;
	private final List<RenderJobDataRow> model;
	private final RenderService service;

	/**
	 * @param service
	 */
	public RenderJobTableModel(final RenderService service) {
		listeners = new ArrayList<TableModelListener>();
		model = new ArrayList<RenderJobDataRow>();
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
			case JOBID: {
				return String.class;
			}
			case NAME: {
				return String.class;
			}
			case FRAME_NUMBER: {
				return Integer.class;
			}
			case TILE_WIDTH: {
				return String.class;
			}
			case TILE_HEIGHT: {
				return String.class;
			}
			case TILE_OFFSETX: {
				return String.class;
			}
			case TILE_OFFSETY: {
				return String.class;
			}
			case STATUS: {
				return String.class;
			}
			case TYPE: {
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
		return 8;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
			case JOBID: {
				return TwisterSwingResources.getInstance().getString("column.id");
			}
			case NAME: {
				return TwisterSwingResources.getInstance().getString("column.name");
			}
			case FRAME_NUMBER: {
				return TwisterSwingResources.getInstance().getString("column.frameNumber");
			}
			case TILE_WIDTH: {
				return TwisterSwingResources.getInstance().getString("column.tileWidth");
			}
			case TILE_HEIGHT: {
				return TwisterSwingResources.getInstance().getString("column.tileHeight");
			}
			case TILE_OFFSETX: {
				return TwisterSwingResources.getInstance().getString("column.tileOffsetX");
			}
			case TILE_OFFSETY: {
				return TwisterSwingResources.getInstance().getString("column.tileOffsetY");
			}
			case STATUS: {
				return TwisterSwingResources.getInstance().getString("column.status");
			}
			case TYPE: {
				return TwisterSwingResources.getInstance().getString("column.type");
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
		final RenderJobDataRow dataRow = model.get(rowIndex);
		switch (columnIndex) {
			case JOBID: {
				return String.valueOf(dataRow.getJobId());
			}
			case NAME: {
				return dataRow.getClipName() + " / " + dataRow.getProfileName();
			}
			case FRAME_NUMBER: {
				return (dataRow.getFrameNumber() + 1) + "/" + (dataRow.getStopTime() - dataRow.getStartTime()) * dataRow.getFrameRate();
			}
			case TILE_WIDTH: {
				return String.valueOf(dataRow.getTileWidth()) + " px";
			}
			case TILE_HEIGHT: {
				return String.valueOf(dataRow.getTileHeight()) + " px";
			}
			case TILE_OFFSETX: {
				return String.valueOf(dataRow.getTileOffsetX()) + " px";
			}
			case TILE_OFFSETY: {
				return String.valueOf(dataRow.getTileOffsetY()) + " px";
			}
			case TYPE: {
				switch (dataRow.getJobType()) {
					case JobData.PROCESS_JOB: {
						return TwisterSwingResources.getInstance().getString("label.process");
					}
					case JobData.COPY_PROCESS_JOB: {
						return TwisterSwingResources.getInstance().getString("label.copy-process");
					}
					case JobData.POST_PROCESS_JOB: {
						return TwisterSwingResources.getInstance().getString("label.post-process");
					}
					default: {
						return "?";
					}
				}
			}
			case STATUS: {
				return String.valueOf(dataRow.getStatus() == 0 ? TwisterSwingResources.getInstance().getString("label.stopped") : TwisterSwingResources.getInstance().getString("label.started"));
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
		// switch (columnIndex)
		// {
		// case NAME:
		// {
		// return true;
		// }
		//
		// case FRAME_NUMBER:
		// {
		// return true;
		// }
		//
		// case TILE_WIDTH:
		// {
		// return true;
		// }
		//
		// case TILE_HEIGHT:
		// {
		// return true;
		// }
		//
		// case TILE_OFFSETX:
		// {
		// return true;
		// }
		//
		// case TILE_OFFSETY:
		// {
		// return true;
		// }
		//
		// case BORDER_WIDTH:
		// {
		// return true;
		// }
		//
		// case BORDER_HEIGHT:
		// {
		// return true;
		// }
		//
		// default:
		// {
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
		// RenderJobDataRow dataRow = model.get(rowIndex);
		//		
		// if (aValue != null)
		// {
		// switch (columnIndex)
		// {
		// case NAME:
		// {
		// dataRow.setName((String) aValue);
		//
		// saveRow(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, NAME, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//	
		// case FRAME_NUMBER:
		// {
		// dataRow.setFrameNumber((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, FRAME_NUMBER, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//
		// case TILE_WIDTH:
		// {
		// dataRow.setTileWidth((Integer) aValue);
		//
		// save(rowIndex);
		//				
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, TILE_WIDTH, TableModelEvent.UPDATE));
		//			
		// break;
		// }
		//
		// case TILE_HEIGHT:
		// {
		// dataRow.setTileHeight((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, TILE_HEIGHT, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//
		// case BORDER_WIDTH:
		// {
		// dataRow.setBorderWidth((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, BORDER_WIDTH, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//
		// case BORDER_HEIGHT:
		// {
		// dataRow.setBorderHeight((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, BORDER_HEIGHT, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//
		// case TILE_OFFSETX:
		// {
		// dataRow.setTileOffsetX((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, TILE_OFFSETX, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//
		// case TILE_OFFSETY:
		// {
		// dataRow.setTileOffsetY((Integer) aValue);
		//
		// save(rowIndex);
		//					
		// fireTableChanged(new TableModelEvent(this, rowIndex, rowIndex, TILE_OFFSETY, TableModelEvent.UPDATE));
		//					
		// break;
		// }
		//				
		// default:
		// {
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

	// /**
	// * @param profileId
	// */
	// public void reload(final int profileId) {
	// service.loadJobs(new ServiceCallback<List<RenderJobDataRow>>() {
	// /**
	// * @param value
	// */
	// public void executed(final List<RenderJobDataRow> clips) {
	// try {
	// if (RenderJobTableModel.logger.isLoggable(Level.INFO)) {
	// RenderJobTableModel.logger.info("Reload executed");
	// }
	// SwingUtilities.invokeAndWait(new Runnable() {
	// /**
	// * @see java.lang.Runnable#run()
	// */
	// public void run() {
	// RenderJobTableModel.this.reload(clips);
	// }
	// });
	// }
	// catch (final Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceCallback#failed(java.lang.Throwable)
	// */
	// public void failed(final Throwable throwable) {
	// try {
	// RenderJobTableModel.logger.log(Level.WARNING, "Reload failed", throwable);
	// SwingUtilities.invokeAndWait(new Runnable() {
	// /**
	// * @see java.lang.Runnable#run()
	// */
	// public void run() {
	// RenderJobTableModel.this.reload(new ArrayList<RenderJobDataRow>());
	// }
	// });
	// }
	// catch (final Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }, profileId);
	// }
	/**
	 * 
	 */
	public void reload() {
		service.execute(new ServiceCallback<List<RenderJobDataRow>>() {
			/**
			 * @param value
			 */
			@Override
			public void executed(final List<RenderJobDataRow> jobs) {
				try {
					if (RenderJobTableModel.logger.isLoggable(Level.INFO)) {
						RenderJobTableModel.logger.info("Reload executed");
					}
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderJobTableModel.this.reload(jobs);
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
					RenderJobTableModel.logger.log(Level.WARNING, "Reload failed", throwable);
					GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
							RenderJobTableModel.this.reload(new LinkedList<RenderJobDataRow>());
							}
												}, true);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}

			/**
			 * @param service
			 * @return
			 * @throws Exception
			 */
			@Override
			public List<RenderJobDataRow> execute(final LibraryService service) throws Exception {
				return service.loadJobs();
			}
		});
	}

	private void reload(final List<RenderJobDataRow> jobs) {
		int lastRow = model.size() - 1;
		model.clear();
		for (final RenderJobDataRow job : jobs) {
			model.add(job);
		}
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
		}
		lastRow = model.size() - 1;
		if (lastRow >= 0) {
			fireTableChanged(new TableModelEvent(this, 0, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
		}
	}

	// private void saveRow(final int rowIndex)
	// {
	// final RenderJobDataRow dataRow = model.get(rowIndex);
	//
	// service.saveJob(new ServiceVoidCallback()
	// {
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#executed()
	// */
	// public void executed()
	// {
	// if (logger.isLoggable(Level.INFO))
	// {
	// logger.info("Save executed");
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.service.AsyncService.ServiceVoidCallback#failed(java.lang.Throwable)
	// */
	// public void failed(final Throwable throwable)
	// {
	// logger.log(Level.WARNING, "Save failed", throwable);
	// }
	// }, dataRow);
	// }
	/**
	 * @param rowIndex
	 * @return
	 */
	public RenderJobDataRow getJob(final int rowIndex) {
		return model.get(rowIndex);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobCreated(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobCreated(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.remove(rowIndex);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				final int rowIndex = model.size();
				model.add(job);
				RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobDeleted(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobDeleted(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.remove(rowIndex);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobUpdated(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobUpdated(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.set(rowIndex, job);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobResumed(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobResumed(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.set(rowIndex, job);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobStarted(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobStarted(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.set(rowIndex, job);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
						break;
					}
				}
				}
						}, true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.ServiceAdapter#jobStopped(com.nextbreakpoint.nextfractal.service.job.RenderJobDataRow)
	 */
	@Override
	public void jobStopped(final RenderJobDataRow job) {
		GUIUtil.executeTask(new TableModelAdapter(this) {
							/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int rowIndex = 0; rowIndex < model.size(); rowIndex++) {
					if (model.get(rowIndex).getJobId() == job.getJobId()) {
						model.set(rowIndex, job);
						RenderJobTableModel.this.fireTableChanged(new TableModelEvent(tableModel, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
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
