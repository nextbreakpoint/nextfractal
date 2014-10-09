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
package com.nextbreakpoint.nextfractal.core.devtools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import freemarker.cache.TemplateLoader;

public class ProcessorTemplateLoader implements TemplateLoader {
	@Override
	public void closeTemplateSource(Object source) throws IOException {
		((InputStream) source).close();
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return getClass().getResourceAsStream("/" + name.substring(0, name.indexOf("_")) + ".ftl");
	}

	@Override
	public long getLastModified(Object source) {
		return 0;
	}

	@Override
	public Reader getReader(Object source, String encoding) throws IOException {
		return new InputStreamReader((InputStream) source, encoding);
	}
}
