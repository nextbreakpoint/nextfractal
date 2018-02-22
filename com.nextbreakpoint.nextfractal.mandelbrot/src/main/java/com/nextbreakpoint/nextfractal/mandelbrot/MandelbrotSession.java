/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.Metadata;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.SessionUtils;
import com.nextbreakpoint.nextfractal.core.Double2D;
import com.nextbreakpoint.nextfractal.core.Double4D;
import com.nextbreakpoint.nextfractal.core.Time;

import java.io.IOException;
import java.util.Objects;

public class MandelbrotSession extends Session {
	private final MandelbrotMetadata metadata;
	private final String script;

	public MandelbrotSession() {
		this(getInitialScript(), new MandelbrotMetadata(new Double4D(0, 0, 1,0), new Double4D(0, 0, 0,0), new Double4D(1, 1, 1,1), new Double2D(0, 0), new Time(0, 1), false, new MandelbrotOptions()));
	}

	public MandelbrotSession(String script, MandelbrotMetadata metadata) {
		Objects.requireNonNull(metadata);
		Objects.requireNonNull(script);
		this.metadata = metadata;
		this.script = script;
	}

	@Override
    public String getPluginId() {
        return MandelbrotFactory.PLUGIN_ID;
    }

	@Override
	public String getGrammar() {
		return MandelbrotFactory.GRAMMAR;
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	private static String getInitialScript() {
		try {
            return SessionUtils.readResource("/mandelbrot.txt");
        } catch (IOException e) {
		}
		return "";
	}

	@Override
	public String toString() {
		return "{pluginId=" + getPluginId() + ", metadata=" + metadata + ", script='" + script + "'}";
	}
}
