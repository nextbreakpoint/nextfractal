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
package com.nextbreakpoint.nextfractal.queue.profile;

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
public class RenderProfileDataTable {
	private static final String SELECT_FIELDS = "ProfileId, MovieClip.ClipId, ProfileName, ImageWidth, ImageHeight, OffsetX, OffsetY, FrameRate, StartTime, StopTime, Quality, RenderProfile.Status, TotalFrames, JobFrame, JobCreated, JobStored, MovieClip.ClipName";
	private static final String CREATE_RENDER_PROFILE_TABLE = "CREATE TABLE RenderProfile (ProfileId int not null primary key, ClipId int not null, ProfileName varchar(64), ImageWidth int not null, ImageHeight int not null, OffsetX int not null, OffsetY int not null, FrameRate int not null, StartTime int not null, StopTime int not null, Quality int not null, Status int not null, TotalFrames int not null, JobFrame int not null, JobCreated int not null, JobStored int not null)";
	private static final String INSERT_RENDER_PROFILE = "INSERT INTO RenderProfile (ProfileId, ClipId, ProfileName, ImageWidth, ImageHeight, OffsetX, OffsetY, FrameRate, StartTime, StopTime, Quality, Status, TotalFrames, JobFrame, JobCreated, JobStored) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_RENDER_PROFILE = "UPDATE RenderProfile SET ClipId = ?, ProfileName = ?, ImageWidth = ?, ImageHeight = ?, OffsetX = ?, OffsetY = ?, FrameRate = ?, StartTime = ?, StopTime = ?, Quality = ? WHERE ProfileId = ? AND Status = 0";
	private static final String UPDATE_RENDER_PROFILE_STATUS = "UPDATE RenderProfile SET Status = ? WHERE ProfileId = ?";
	private static final String UPDATE_RENDER_PROFILE_FRAMES = "UPDATE RenderProfile SET TotalFrames = ?, JobFrame = ? WHERE ProfileId = ?";
	private static final String UPDATE_RENDER_PROFILE_JOBS = "UPDATE RenderProfile SET JobCreated = ?, JobStored = ? WHERE ProfileId = ?";
	private static final String DELETE_RENDER_PROFILE = "DELETE FROM RenderProfile WHERE ProfileId = ? AND Status = 0";
	private static final String DELETE_ALL_RENDER_PROFILE = "DELETE FROM RenderProfile WHERE Status = 0";
	private static final String SELECT_RENDER_PROFILE_BY_PROFILE_ID = "SELECT " + RenderProfileDataTable.SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId) WHERE RenderProfile.ProfileId = ? ORDER BY ProfileId, RenderProfile.ClipId";
	private static final String SELECT_RENDER_PROFILE_BY_CLIP_ID = "SELECT " + RenderProfileDataTable.SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId) WHERE RenderProfile.ClipId = ? ORDER BY ProfileId, RenderProfile.ClipId";
	private static final String SELECT_RENDER_PROFILE = "SELECT " + RenderProfileDataTable.SELECT_FIELDS + " FROM RenderProfile WHERE LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId) RenderProfile.ClipId = ? AND ProfileName LIKE ? ORDER BY ProfileId, RenderProfile.ClipId";
	private static final String SELECT_ALL_RENDER_PROFILE = "SELECT " + RenderProfileDataTable.SELECT_FIELDS + " FROM RenderProfile LEFT JOIN MovieClip ON (MovieClip.ClipId = RenderProfile.ClipId) ORDER BY ProfileId, RenderProfile.ClipId";
	private static final String SELECT_LAST_PRIMARY_KEY = "SELECT MAX(ProfileId) FROM RenderProfile";
	private int lastPrimaryKey = -1;

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.INSERT_RENDER_PROFILE);
			profile.setProfileId(getNextPrimaryKey(session));
			stmt.setInt(1, profile.getProfileId());
			stmt.setInt(2, profile.getClipId());
			stmt.setString(3, profile.getProfileName());
			stmt.setInt(4, profile.getImageWidth());
			stmt.setInt(5, profile.getImageHeight());
			stmt.setInt(6, profile.getOffsetX());
			stmt.setInt(7, profile.getOffsetY());
			stmt.setInt(8, profile.getFrameRate());
			stmt.setInt(9, profile.getStartTime());
			stmt.setInt(10, profile.getStopTime());
			stmt.setInt(11, profile.getQuality());
			stmt.setInt(12, profile.getStatus());
			stmt.setInt(13, profile.getTotalFrames());
			stmt.setInt(14, profile.getJobFrame());
			stmt.setInt(15, profile.getJobCreated());
			stmt.setInt(16, profile.getJobStored());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't create the profile");
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the profile", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.UPDATE_RENDER_PROFILE);
			stmt.setInt(1, profile.getClipId());
			stmt.setString(2, profile.getProfileName());
			stmt.setInt(3, profile.getImageWidth());
			stmt.setInt(4, profile.getImageHeight());
			stmt.setInt(5, profile.getOffsetX());
			stmt.setInt(6, profile.getOffsetY());
			stmt.setInt(7, profile.getFrameRate());
			stmt.setInt(8, profile.getStartTime());
			stmt.setInt(9, profile.getStopTime());
			stmt.setInt(10, profile.getQuality());
			stmt.setInt(11, profile.getProfileId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the profile: " + profile.getProfileId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the profile: " + profile.getProfileId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.UPDATE_RENDER_PROFILE_STATUS);
			stmt.setInt(1, profile.getStatus());
			stmt.setInt(2, profile.getProfileId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the profile: " + profile.getProfileId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the profile: " + profile.getProfileId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void saveFrames(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.UPDATE_RENDER_PROFILE_FRAMES);
			stmt.setInt(1, profile.getTotalFrames());
			stmt.setInt(2, profile.getJobFrame());
			stmt.setInt(3, profile.getProfileId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the profile: " + profile.getProfileId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the profile: " + profile.getProfileId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void saveJobs(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.UPDATE_RENDER_PROFILE_JOBS);
			stmt.setInt(1, profile.getJobCreated());
			stmt.setInt(2, profile.getJobStored());
			stmt.setInt(3, profile.getProfileId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the profile: " + profile.getProfileId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the profile: " + profile.getProfileId(), e);
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
	public RenderProfileDataRow load(final Session session, final int id) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.SELECT_RENDER_PROFILE_BY_PROFILE_ID);
			stmt.setInt(1, id);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return createProfile(rs);
			}
			else {
				throw new DataTableException("Can't load the profile. ID not found: " + id);
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the profile: " + id, e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param clipId
	 * @param match
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderProfileDataRow> load(final Session session, final int clipId, final RenderProfileDataRow match) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderProfileDataRow> list = new ArrayList<RenderProfileDataRow>();
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.SELECT_RENDER_PROFILE);
			stmt.setInt(1, clipId);
			stmt.setString(2, match.getProfileName());
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(createProfile(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the profiles", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @param clipId
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderProfileDataRow> loadAll(final Session session, final int clipId) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderProfileDataRow> list = new ArrayList<RenderProfileDataRow>();
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.SELECT_RENDER_PROFILE_BY_CLIP_ID);
			stmt.setInt(1, clipId);
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(createProfile(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the profiles", e);
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
	public List<RenderProfileDataRow> loadAll(final Session session) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderProfileDataRow> list = new ArrayList<RenderProfileDataRow>();
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			final ResultSet rs = stmt.executeQuery(RenderProfileDataTable.SELECT_ALL_RENDER_PROFILE);
			while (rs.next()) {
				list.add(createProfile(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the profiles", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @param profile
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderProfileDataTable.DELETE_RENDER_PROFILE);
			stmt.setInt(1, profile.getProfileId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't delete the profile. ID not found: " + profile.getProfileId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the profile: " + profile.getProfileId(), e);
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
			stmt.executeUpdate(RenderProfileDataTable.DELETE_ALL_RENDER_PROFILE);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the profiles", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	private RenderProfileDataRow createProfile(final ResultSet rs) throws SQLException {
		final RenderProfileDataRow profile = new RenderProfileDataRow(new RenderProfile());
		profile.setProfileId(rs.getInt("ProfileId"));
		profile.setClipId(rs.getInt("ClipId"));
		profile.setClipName(rs.getString("ClipName"));
		profile.setProfileName(rs.getString("ProfileName"));
		profile.setImageWidth(rs.getInt("ImageWidth"));
		profile.setImageHeight(rs.getInt("ImageHeight"));
		profile.setOffsetX(rs.getInt("OffsetX"));
		profile.setOffsetY(rs.getInt("OffsetY"));
		profile.setFrameRate(rs.getInt("FrameRate"));
		profile.setStartTime(rs.getInt("StartTime"));
		profile.setStopTime(rs.getInt("StopTime"));
		profile.setQuality(rs.getInt("Quality"));
		profile.setStatus(rs.getInt("Status"));
		profile.setTotalFrames(rs.getInt("TotalFrames"));
		profile.setJobFrame(rs.getInt("JobFrame"));
		profile.setJobCreated(rs.getInt("JobCreated"));
		profile.setJobStored(rs.getInt("JobStored"));
		return profile;
	}

	private int getNextPrimaryKey(final Session session) throws DataTableException, SessionException {
		return lastPrimaryKey += 1;
	}

	private void loadLastPrimaryKey(final Session session) throws DataTableException, SessionException {
		if (lastPrimaryKey < 0) {
			Statement stmt = null;
			try {
				stmt = session.createStatement();
				final ResultSet rs = stmt.executeQuery(RenderProfileDataTable.SELECT_LAST_PRIMARY_KEY);
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
			stmt.execute(RenderProfileDataTable.CREATE_RENDER_PROFILE_TABLE);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the RenderProfile table", e);
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
}
