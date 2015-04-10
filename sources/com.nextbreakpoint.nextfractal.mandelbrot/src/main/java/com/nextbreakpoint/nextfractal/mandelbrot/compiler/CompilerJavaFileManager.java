/*
 * NextFractal 1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class CompilerJavaFileManager implements JavaFileManager {
	private Map<String, JavaFileObject> files = new HashMap<>();
	private JavaFileManager fileManager;
	private final String orbitClassName;
	private final String colorClassName;
	
	public CompilerJavaFileManager(JavaFileManager fileManager, String orbitClassName, String colorClassName) {
		this.orbitClassName = orbitClassName;
		this.colorClassName = colorClassName;
		this.fileManager = fileManager;
	}

	/**
	 * @see javax.tools.OptionChecker#isSupportedOption(java.lang.String)
	 */
	@Override
	public int isSupportedOption(String option) {
		return fileManager.isSupportedOption(option);
	}

	/**
	 * @see javax.tools.JavaFileManager#getClassLoader(javax.tools.JavaFileManager.Location)
	 */
	@Override
	public ClassLoader getClassLoader(Location location) {
		return fileManager.getClassLoader(location);
	}

	/**
	 * @see javax.tools.JavaFileManager#list(javax.tools.JavaFileManager.Location, java.lang.String, java.util.Set, boolean)
	 */
	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse) throws IOException {
		return fileManager.list(location, packageName, kinds, recurse);
	}

	/**
	 * @see javax.tools.JavaFileManager#inferBinaryName(javax.tools.JavaFileManager.Location, javax.tools.JavaFileObject)
	 */
	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		return fileManager.inferBinaryName(location, file);
	}

	/**
	 * @see javax.tools.JavaFileManager#isSameFile(javax.tools.FileObject, javax.tools.FileObject)
	 */
	@Override
	public boolean isSameFile(FileObject a, FileObject b) {
		return fileManager.isSameFile(a, b);
	}

	/**
	 * @see javax.tools.JavaFileManager#handleOption(java.lang.String, java.util.Iterator)
	 */
	@Override
	public boolean handleOption(String current, Iterator<String> remaining) {
		return fileManager.handleOption(current, remaining);
	}

	/**
	 * @see javax.tools.JavaFileManager#hasLocation(javax.tools.JavaFileManager.Location)
	 */
	@Override
	public boolean hasLocation(Location location) {
		return fileManager.hasLocation(location);
	}

	/**
	 * @see javax.tools.JavaFileManager#getJavaFileForInput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind)
	 */
	@Override
	public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
		return fileManager.getJavaFileForInput(location, className, kind);
	}

	/**
	 * @see javax.tools.JavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind, javax.tools.FileObject)
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
		if (className.equals(orbitClassName) || className.equals(colorClassName)) {
			JavaFileObject file = files.get(className);
			if (file == null) {
				file = new ClassJavaFileObject(className);
				files.put(className, file);
			}
			return file;
		} else {
			return fileManager.getJavaFileForOutput(location, className, kind, sibling);
		}
	}

	/**
	 * @see javax.tools.JavaFileManager#getFileForInput(javax.tools.JavaFileManager.Location, java.lang.String, java.lang.String)
	 */
	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		return fileManager.getFileForInput(location, packageName, relativeName);
	}

	/**
	 * @see javax.tools.JavaFileManager#getFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, java.lang.String, javax.tools.FileObject)
	 */
	@Override
	public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
		return fileManager.getFileForOutput(location, packageName, relativeName, sibling);
	}

	/**
	 * @see javax.tools.JavaFileManager#flush()
	 */
	@Override
	public void flush() throws IOException {
		fileManager.flush();
	}

	/**
	 * @see javax.tools.JavaFileManager#close()
	 */
	@Override
	public void close() throws IOException {
		fileManager.close();
		files.clear();
	}
}
