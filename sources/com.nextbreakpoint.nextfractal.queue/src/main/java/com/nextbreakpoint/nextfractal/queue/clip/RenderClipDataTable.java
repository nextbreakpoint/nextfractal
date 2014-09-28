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
package com.nextbreakpoint.nextfractal.queue.clip;

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
public class RenderClipDataTable {
	private static final String CREATE_MOVIE_CLIP_TABLE = "CREATE TABLE MovieClip (ClipId int not null primary key, ClipName varchar(64), Description varchar(255), Duration int not null, Status int not null)";
	private static final String INSERT_MOVIE_CLIP = "INSERT INTO MovieClip (ClipId, ClipName, Description, Duration, Status) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_MOVIE_CLIP = "UPDATE MovieClip SET ClipName = ?, Description = ?, Duration = ? WHERE ClipId = ? AND Status = 0";
	private static final String UPDATE_MOVIE_CLIP_STATUS = "UPDATE MovieClip SET Status = ? WHERE ClipId = ?";
	private static final String DELETE_MOVIE_CLIP = "DELETE FROM MovieClip WHERE ClipId = ? AND Status = 0";
	private static final String DELETE_ALL_MOVIE_CLIP = "DELETE FROM MovieClip WHERE Status = 0";
	private static final String SELECT_MOVIE_CLIP_BY_CLIP_ID = "SELECT * FROM MovieClip WHERE ClipId = ? ORDER BY ClipId";
	private static final String SELECT_MOVIE_CLIP = "SELECT * FROM MovieClip WHERE ClipName LIKE ? AND Description LIKE ? ORDER BY ClipId";
	private static final String SELECT_ALL_MOVIE_CLIP = "SELECT * FROM MovieClip ORDER BY ClipId";
	private static final String SELECT_LAST_PRIMARY_KEY = "SELECT MAX(ClipId) FROM MovieClip";
	private int lastPrimaryKey = -1;

	/**
	 * @param session
	 * @param clip
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderClipDataRow clip) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.INSERT_MOVIE_CLIP);
			clip.setClipId(getNextPrimaryKey(session));
			stmt.setInt(1, clip.getClipId());
			stmt.setString(2, clip.getClipName());
			stmt.setString(3, clip.getDescription());
			stmt.setLong(4, clip.getDuration());
			stmt.setInt(5, clip.getStatus());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't create the clip");
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the clip", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param clip
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderClipDataRow clip) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.UPDATE_MOVIE_CLIP);
			stmt.setString(1, clip.getClipName());
			stmt.setString(2, clip.getDescription());
			stmt.setLong(3, clip.getDuration());
			stmt.setInt(4, clip.getClipId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the clip: " + clip.getClipId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the clip: " + clip.getClipId(), e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param clip
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderClipDataRow clip) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.UPDATE_MOVIE_CLIP_STATUS);
			stmt.setInt(1, clip.getStatus());
			stmt.setInt(2, clip.getClipId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't save the clip: " + clip.getClipId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't save the clip: " + clip.getClipId(), e);
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
	public RenderClipDataRow load(final Session session, final int id) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.SELECT_MOVIE_CLIP_BY_CLIP_ID);
			stmt.setInt(1, id);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return createClip(rs);
			}
			else {
				throw new DataTableException("Can't load the clip. ID not found: " + id);
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the clip: " + id, e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	/**
	 * @param session
	 * @param match
	 * @return
	 * @throws DataTableException
	 */
	public List<RenderClipDataRow> load(final Session session, final RenderClipDataRow match) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderClipDataRow> list = new ArrayList<RenderClipDataRow>();
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.SELECT_MOVIE_CLIP);
			stmt.setString(1, match.getClipName());
			stmt.setString(2, match.getDescription());
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(createClip(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the clips", e);
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
	public List<RenderClipDataRow> loadAll(final Session session) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		final List<RenderClipDataRow> list = new ArrayList<RenderClipDataRow>();
		Statement stmt = null;
		try {
			stmt = session.createStatement();
			final ResultSet rs = stmt.executeQuery(RenderClipDataTable.SELECT_ALL_MOVIE_CLIP);
			while (rs.next()) {
				list.add(createClip(rs));
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't load the clips", e);
		}
		finally {
			session.closeStatement(stmt);
		}
		return list;
	}

	/**
	 * @param session
	 * @param clip
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderClipDataRow clip) throws DataTableException, SessionException {
		loadLastPrimaryKey(session);
		PreparedStatement stmt = null;
		try {
			stmt = session.prepareStatement(RenderClipDataTable.DELETE_MOVIE_CLIP);
			stmt.setInt(1, clip.getClipId());
			if (stmt.executeUpdate() != 1) {
				throw new DataTableException("Can't delete the clip. ID not found: " + clip.getClipId());
			}
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the clip: " + clip.getClipId(), e);
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
			stmt.executeUpdate(RenderClipDataTable.DELETE_ALL_MOVIE_CLIP);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't delete the clips", e);
		}
		finally {
			session.closeStatement(stmt);
		}
	}

	private RenderClipDataRow createClip(final ResultSet rs) throws SQLException {
		final RenderClipDataRow clip = new RenderClipDataRow(new RenderClip());
		clip.setClipId(rs.getInt("ClipId"));
		clip.setClipName(rs.getString("ClipName"));
		clip.setDescription(rs.getString("Description"));
		clip.setDuration(rs.getInt("Duration"));
		clip.setStatus(rs.getInt("Status"));
		return clip;
	}

	private int getNextPrimaryKey(final Session session) throws DataTableException, SessionException {
		return lastPrimaryKey += 1;
	}

	private void loadLastPrimaryKey(final Session session) throws DataTableException, SessionException {
		if (lastPrimaryKey < 0) {
			Statement stmt = null;
			try {
				stmt = session.createStatement();
				final ResultSet rs = stmt.executeQuery(RenderClipDataTable.SELECT_LAST_PRIMARY_KEY);
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
			stmt.execute(RenderClipDataTable.CREATE_MOVIE_CLIP_TABLE);
		}
		catch (final SQLException e) {
			throw new DataTableException("Can't create the MovieClip table", e);
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
