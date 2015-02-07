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
package com.nextbreakpoint.nextfractal.spool;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class JobEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int EVENT_TYPE_FRAME = 10;
	public static final int EVENT_TYPE_BEGIN = 20;
	public static final int EVENT_TYPE_END = 30;
	public static final int EVENT_TYPE_DONE = 40;
	private final int eventType;
	private final Serializable eventData;

	/**
	 * @param eventType
	 * @param eventData
	 */
	public JobEvent(final int eventType, final Serializable eventData) {
		this.eventType = eventType;
		this.eventData = eventData;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @return the eventData
	 */
	public Serializable getEventData() {
		return eventData;
	}

	/**
	 * @param eventType
	 * @return
	 */
	public static final String decodeEventType(final int eventType) {
		switch (eventType) {
			case EVENT_TYPE_BEGIN:
				return "BEGIN";
			case EVENT_TYPE_END:
				return "END";
			case EVENT_TYPE_FRAME:
				return "FRAME";
			case EVENT_TYPE_DONE:
				return "DONE";
			default:
				return "unknown";
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append("eventType = ");
		builder.append(decodeEventType(getEventType()));
		builder.append(", eventData = ");
		builder.append("[");
		builder.append(eventData);
		builder.append("]");
		builder.append("]");
		return builder.toString();
	}
}
