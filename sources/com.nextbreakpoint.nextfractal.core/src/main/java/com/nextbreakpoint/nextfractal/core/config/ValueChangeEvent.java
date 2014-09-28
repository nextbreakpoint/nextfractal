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
package com.nextbreakpoint.nextfractal.core.config;

import java.io.Serializable;

/**
 * Value change event.
 * 
 * @author Andrea Medeghini
 */
public class ValueChangeEvent {
	private final Serializable[] params;
	private final long timestamp;
	private final int eventType;

	private ValueChangeEvent(final int eventType, final long timestamp, final Serializable[] params) {
		this.params = params;
		this.eventType = eventType;
		this.timestamp = timestamp;
	}

	/**
	 * Constructs a new event.
	 * 
	 * @param eventType the event type.
	 * @param timestamp the timestamp.
	 * @param param0 the parameter.
	 */
	public ValueChangeEvent(final int eventType, final long timestamp, final Serializable param0) {
		this(eventType, timestamp, new Serializable[] { param0 });
	}

	/**
	 * Constructs a new event.
	 * 
	 * @param eventType the event type.
	 * @param timestamp the timestamp.
	 * @param param0 the first parameter.
	 * @param param1 the second parameter.
	 */
	public ValueChangeEvent(final int eventType, final long timestamp, final Serializable param0, final Serializable param1) {
		this(eventType, timestamp, new Serializable[] { param0, param1 });
	}

	/**
	 * Constructs a new event.
	 * 
	 * @param eventType the event type.
	 * @param timestamp the timestamp.
	 * @param param0 the first parameter.
	 * @param param1 the second parameter.
	 * @param param2 the third parameter.
	 */
	public ValueChangeEvent(final int eventType, final long timestamp, final Serializable param0, final Serializable param1, final Serializable param2) {
		this(eventType, timestamp, new Serializable[] { param0, param1, param2 });
	}

	/**
	 * Returns the event type.
	 * 
	 * @return the event type.
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * Returns the timestamp.
	 * 
	 * @return the timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the parameters.
	 * 
	 * @return the parameters.
	 */
	public Serializable[] getParams() {
		return params;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("eventType = ");
		builder.append(eventType);
		builder.append(", timestamp = ");
		builder.append(timestamp);
		builder.append(", params = [ ");
		builder.append(params[0]);
		for (int i = 1; i < params.length; i++) {
			builder.append(", ");
			builder.append(params[i]);
		}
		builder.append(" ] ");
		return builder.toString();
	}
}
