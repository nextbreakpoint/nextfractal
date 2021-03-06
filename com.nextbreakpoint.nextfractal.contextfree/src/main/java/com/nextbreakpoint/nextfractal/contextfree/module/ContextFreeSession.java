/*
 * NextFractal 2.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.module;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.Metadata;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.common.SessionUtils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextFreeSession extends Session {
	private static final Logger logger = Logger.getLogger(ContextFreeSession.class.getName());

	private final ContextFreeMetadata metadata;
	private final String script;

	public ContextFreeSession() {
		this(getInitialSource(), new ContextFreeMetadata());
	}

	public ContextFreeSession(String script, ContextFreeMetadata metadata) {
		Objects.requireNonNull(metadata);
		Objects.requireNonNull(script);
		this.metadata = metadata;
		this.script = script;
	}

	@Override
    public String getPluginId() {
        return ContextFreeFactory.PLUGIN_ID;
    }

	@Override
	public String getGrammar() {
		return ContextFreeFactory.GRAMMAR;
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	private static String getInitialSource() {
		return Try.of(() -> SessionUtils.readAll(ContextFreeSession.class.getResourceAsStream("/contextfree.txt")))
				.onFailure(e -> logger.log(Level.WARNING, "Cannot load resource /contextfree.txt"))
				.orElse("");
	}

	@Override
	public String toString() {
		return "{pluginId=" + getPluginId() + ", metadata=" + metadata + ", script='" + script + "'}";
	}
}
