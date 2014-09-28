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
package com.nextbreakpoint.nextfractal.queue;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;

/**
 * @author Andrea Medeghini
 */
public class DefaultConnectionFactory implements ConnectionFactory {
	private static final Logger logger = Logger.getLogger(DefaultConnectionFactory.class.getName());
	private String driverClass;
	private String shutdownUrl;
	private String createUrl;

	/**
	 * @param workspace
	 */
	public DefaultConnectionFactory(final File workspace) {
		ResourceBundle bundle = ResourceBundle.getBundle("derby");
		driverClass = bundle.getString("driverClass");
		shutdownUrl = bundle.getString("shutdownUrl").replace("${workspace}", workspace.getAbsolutePath());
		createUrl = bundle.getString("createUrl").replace("${workspace}", workspace.getAbsolutePath());
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Faild to load database driver", e);
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					DriverManager.getConnection(shutdownUrl);
				} catch (SQLException e) {
					logger.log(Level.WARNING, "Faild to close database", e);
				}
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.ConnectionFactory#createConnection()
	 */
	public Connection createConnection() throws SQLException {
		if (DefaultConnectionFactory.logger.isLoggable(Level.FINE)) {
			DefaultConnectionFactory.logger.fine("Create a connection");
		}
		try {
			return DriverManager.getConnection(createUrl);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Faild to open database", e);
			throw e;
		}
	}
}
