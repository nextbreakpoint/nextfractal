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
package com.nextbreakpoint.nextfractal.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class for files manipulation.
 * 
 * @author Andrea Medeghini
 */
public class Files {
	private Files() {
	}

	/**
	 * Copyes a file.
	 * 
	 * @param srcFileName
	 * @param dstFileName
	 * @throws IOException
	 */
	public static void copyFile(final String srcFileName, final String dstFileName) throws IOException {
		Files.copyFile(new File(srcFileName), new File(dstFileName));
	}

	/**
	 * Copyes a file.
	 * 
	 * @param srcFile
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copyFile(final File srcFile, final File dstFile) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(dstFile);
			final byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				}
				catch (final Exception e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				}
				catch (final Exception e) {
				}
			}
		}
	}

	/**
	 * @param path
	 */
	public static void deleteFiles(final File path) {
		final File[] files = path.listFiles();
		if (files != null) {
			for (final File file : files) {
				if (file.isDirectory()) {
					deleteFiles(file);
				}
				file.delete();
			}
		}
	}
}
