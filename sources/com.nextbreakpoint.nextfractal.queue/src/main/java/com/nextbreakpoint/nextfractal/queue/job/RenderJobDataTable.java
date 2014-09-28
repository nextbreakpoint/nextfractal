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
package com.nextbreakpoint.nextfractal.queue.job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.queue.DataTableException;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.SessionException;

/**
 * @author Andrea Medeghini
 */
public class RenderJobDataTable {
	private static final String SELECT_FIELDS = "JobId, RenderProfile.ProfileId, RenderProfile.ClipId, FrameNumber, TileWidth, TileHeight, TileOffsetX, TileOffsetY, BorderWidth, BorderHeight, Status, ClipName, ProfileName, ImageWidth, ImageHeight, FrameRate, StartTime, StopTime, Quality, Status, JobType";
	private static final String SUB_SELECT_FIELDS = "RenderProfile.ProfileId, RenderProfile.ClipId, ClipName, ProfileName, ImageWidth, ImageHeight, FrameRate, StartTime, StopTime, Quality";
	private static final String CREATE_RENDER_JOB_TABLE = "CREATE TABLE RenderJob (JobId int not null primary key, ProfileId int not null, FrameNumber int not null, TileWidth int not null, TileHeight int not null, TileOffsetX int not null, TileOffsetY int not null, BorderWidth int not null, BorderHeight int not null, Status int not null, JobType int not null)";
	private static final String INSERT_RENDER_JOB = "INSERT INTO RenderJob (JobId, ProfileId, FrameNumber, TileWidth, TileHeight, TileOffsetX, TileOffsetY, BorderWidth, BorderHeight, Status, JobType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_RENDER_JOB = "UPDATE RenderJob SET ProfileId = ?, FrameNumber = ?, TileWidth = ?, TileHeight = ?, TileOffsetX = ?, TileOffsetY = ?, BorderWidth = ?, BorderHeight = ?, JobType = ? WHERE JobId = ? AND Status = 0";
	private static final String UPDATE_RENDER_JOB_STATUS = "UPDATE RenderJob SET FrameNumber = ?, Status = ? WHERE JobId = ?";
	private static final String DELETE_RENDER_JOB = "DELETE FROM RenderJob WHERE JobId = ? AND Status = 0";
	private static final String DELETE_ALL_RENDER_JOB = "DELETE FROM RenderJob WHERE Status = 0";
	private static final String DELETE_ALL_RENDER_JOB_BY_PROFILE_ID = "DELETE FROM RenderJob WHERE ProfileId = ?";
	private static final String SELECT_RENDER_JOB_BY_JOB_ID = "SELECT " + RenderJobDataTable.SELECT_FIELDS + " FROM RenderJob LEFT JOIN (select " + RenderJobDataTable.SUB_SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId)) AS RenderProfile ON (RenderProfile.ProfileId = RenderJob.ProfileId) WHERE JobId = ? ORDER BY JobId, RenderJob.ProfileId";
	private static final String SELECT_RENDER_JOB = "SELECT " + RenderJobDataTable.SELECT_FIELDS + " FROM RenderJob LEFT JOIN (select " + RenderJobDataTable.SUB_SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId)) AS RenderProfile ON (RenderProfile.ProfileId = RenderJob.ProfileId) WHERE JobId LIKE ? ORDER BY JobId, RenderJob.ProfileId";
	private static final String SELECT_ALL_RENDER_JOB = "SELECT " + RenderJobDataTable.SELECT_FIELDS + " FROM RenderJob LEFT JOIN (select " + RenderJobDataTable.SUB_SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId)) AS RenderProfile ON (RenderProfile.ProfileId = RenderJob.ProfileId) ORDER BY JobId, RenderJob.ProfileId";
	private static final String SELECT_ALL_RENDER_JOB_BY_PROFILE_ID = "SELECT " + RenderJobDataTable.SELECT_FIELDS + " FROM RenderJob LEFT JOIN (select " + RenderJobDataTable.SUB_SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId)) AS RenderProfile ON (RenderProfile.ProfileId = RenderJob.ProfileId) WHERE RenderJob.ProfileId = ? ORDER BY JobId, RenderJob.ProfileId";
	private static final String SELECT_LAST_PRIMARY_KEY = "SELECT MAX(JobId) FROM RenderJob";
	private int lastPrimaryKey = -1;

	/**
	 * @param session
	 * @param job
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderJobDataRow job) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.INSERT_RENDER_JOB);
			job.setJobId(getNextPrimaryKey(session));
			stmt.setInt(1, job.getJobId());
			stmt.setInt(2, job.getProfileId());
			stmt.setInt(3, job.getFrameNumber());
			stmt.setInt(4, job.getTileWidth());
			stmt.setInt(5, job.getTileHeight());
			stmt.setInt(6, job.getTileOffsetX());
			stmt.setInt(7, job.getTileOffsetY());
			stmt.setInt(8, job.getBorderWidth());
			stmt.setInt(9, job.getBorderHeight());
			stmt.setInt(10, job.getStatus());
			stmt.setInt(11, job.getJobType());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't create the job");
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the job", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param job
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderJobDataRow job) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.UPDATE_RENDER_JOB);
			stmt.setInt(1, job.getProfileId());
			stmt.setInt(2, job.getFrameNumber());
			stmt.setInt(3, job.getTileWidth());
			stmt.setInt(4, job.getTileHeight());
			stmt.setInt(5, job.getTileOffsetX());
			stmt.setInt(6, job.getTileOffsetY());
			stmt.setInt(7, job.getBorderWidth());
			stmt.setInt(8, job.getBorderHeight());
			stmt.setInt(9, job.getJobType());
			stmt.setInt(10, job.getJobId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the job: " + job.getJobId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the job: " + job.getJobId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param job
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderJobDataRow job) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.UPDATE_RENDER_JOB_STATUS);
			stmt.setInt(1, job.getFrameNumber());
			stmt.setInt(2, job.getStatus());
			stmt.setInt(3, job.getJobId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the job: " + job.getJobId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the job: " + job.getJobId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param id
	 * @return
	 * @throws DataTableException
	 */
	public RenderJobDataRow load(final Session session, final int id) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.SELECT_RENDER_JOB_BY_JOB_ID);
			stmt.setInt(1, id);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return createJob(rs);
			}
			else {
				throw new DataTableException("Can't load the job. ID not found: " + id);
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the job: " + id, e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param clip
	 * @param match
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderJobDataRow> load(final Session session, final RenderJobDataRow match) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderJobDataRow> list = new ArrayList<RenderJobDataRow>();
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.SELECT_RENDER_JOB);
			stmt.setString(1, match.getProfileName());
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(createJob(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the jobs", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderJobDataRow> loadAll(final Session session) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderJobDataRow> list = new ArrayList<RenderJobDataRow>();
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			final ResultSet rs = stmt.executeQuery(RenderJobDataTable.SELECT_ALL_RENDER_JOB);
			while (rs.next()) {
				list.add(createJob(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the jobs", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @param profileId
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderJobDataRow> loadAll(final Session session, final int profileId) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderJobDataRow> list = new ArrayList<RenderJobDataRow>();
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.SELECT_ALL_RENDER_JOB_BY_PROFILE_ID);
			stmt.setInt(1, profileId);
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(createJob(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the jobs", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @param job
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderJobDataRow job) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.DELETE_RENDER_JOB);
			stmt.setInt(1, job.getJobId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't delete the job. ID not found: " + job.getJobId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the job: " + job.getJobId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @throws DataTableException
	 */
	public void deleteAll(final Session session) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			stmt.executeUpdate(RenderJobDataTable.DELETE_ALL_RENDER_JOB);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the jobs", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param profileId
	 * @throws DataTableException
	 * @throws SessionException
	 */
	public void deleteAll(final Session session, final int profileId) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderJobDataTable.DELETE_ALL_RENDER_JOB_BY_PROFILE_ID);
			stmt.setInt(1, profileId);
			stmt.executeUpdate();
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the jobs", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	private RenderJobDataRow createJob(final ResultSet rs) throws SQLException {
		final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
		job.setJobId(rs.getInt("JobId"));
		job.setClipId(rs.getInt("ClipId"));
		job.setProfileId(rs.getInt("ProfileId"));
		job.setFrameNumber(rs.getInt("FrameNumber"));
		job.setTileWidth(rs.getInt("TileWidth"));
		job.setTileHeight(rs.getInt("TileHeight"));
		job.setTileOffsetX(rs.getInt("TileOffsetX"));
		job.setTileOffsetY(rs.getInt("TileOffsetY"));
		job.setBorderWidth(rs.getInt("BorderWidth"));
		job.setBorderHeight(rs.getInt("BorderHeight"));
		job.setStatus(rs.getInt("Status"));
		job.setJobType(rs.getInt("JobType"));
		job.setClipName(rs.getString("ClipName"));
		job.setProfileName(rs.getString("ProfileName"));
		job.setImageWidth(rs.getInt("ImageWidth"));
		job.setImageHeight(rs.getInt("ImageHeight"));
		job.setFrameRate(rs.getInt("FrameRate"));
		job.setStartTime(rs.getInt("StartTime"));
		job.setStopTime(rs.getInt("StopTime"));
		job.setQuality(rs.getInt("Quality"));
		return job;
	}

	private int getNextPrimaryKey(final Session session) throws DataTableException, SessionException {
		return lastPrimaryKey += 1;
	}

	private void loadLastPrimaryKey(final Session session) throws DataTableException, SessionException {
		if (lastPrimaryKey < 0) {
			Statement stmt = null;
			try {
				stmt = session.createStatement();
				final ResultSet rs = stmt.executeQuery(RenderJobDataTable.SELECT_LAST_PRIMARY_KEY);
				if (rs.next()) {
					lastPrimaryKey = rs.getInt(1);
				}
			}
			catch (final SQLException e) {
				createTable(session);
			}
			finally {
				session.closeStatement(stmt);
			}
		}
	}

	private void createTable(final Session session) throws DataTableException, SessionException {
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			stmt.execute(RenderJobDataTable.CREATE_RENDER_JOB_TABLE);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the RenderJob table", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @throws DataTableException
	 * @throws SessionException
	 */
	public void init(final Session session) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
	}

	/**
	 * @param session
	 * @throws DataTableException
	 * @throws SessionException
	 */
	public boolean updateTable(final Session session) throws DataTableException, SessionException {
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			final ResultSet rs = stmt.executeQuery("select * from RenderJob");
			try {
				if (rs.getMetaData().getColumnCount() < 11) {
					stmt.execute("drop table RenderJob");
					stmt.execute(RenderJobDataTable.CREATE_RENDER_JOB_TABLE);
					return true;
				}
			}
			catch (final SQLException e) {
				throw new DataTableException("Can't update the RenderJob table", e);
			}
		}
		catch (final SQLException e) {
			// be quiet...
		}
		finally {
			session.closeStatement(stmt);
		}
		return false;
	}
}
