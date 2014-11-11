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
package com.nextbreakpoint.nextfractal.twister;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;

/**
 * @author Andrea Medeghini
 */
public class TwisterSequence implements Serializable {
	private static final long serialVersionUID = 1L;
	private TwisterConfig initialConfig;
	private TwisterConfig finalConfig;
	private final List<NodeAction> actions = new ArrayList<NodeAction>();
	private long duration;

	/**
	 * 
	 */
	public TwisterSequence() {
	}

	/**
	 * @return the initialConfig
	 */
	public TwisterConfig getInitialConfig() {
		return initialConfig;
	}

	/**
	 * @param initialConfig the initialConfig to set
	 */
	public void setInitialConfig(final TwisterConfig initialConfig) {
		this.initialConfig = initialConfig;
	}

	/**
	 * @return the config
	 */
	public TwisterConfig getFinalConfig() {
		return finalConfig;
	}

	/**
	 * @param config the config to set
	 */
	public void setFinalConfig(final TwisterConfig config) {
		finalConfig = config;
	}

	/**
	 * @return
	 */
	public int getActionCount() {
		return actions.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public NodeAction getAction(final int index) {
		return actions.get(index);
	}

	/**
	 * @param value
	 */
	public void addAction(final NodeAction value) {
		actions.add(value);
	}

	/**
	 * @param value
	 */
	public void removeAction(final NodeAction value) {
		actions.remove(value);
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(final long duration) {
		this.duration = duration;
	}
}
