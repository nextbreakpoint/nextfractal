/*
 * NextFractal 2.1.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2019 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.core.network;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class EventMessage extends ServiceMessage {
	private static final long serialVersionUID = 1L;
	private Serializable userData;

	/**
	 * 
	 */
	public EventMessage() {
		super(newMessageId(), MESSAGE_TYPE_EVENT);
	}

	/**
	 * @return the userData
	 */
	public Serializable getUserData() {
		return userData;
	}

	/**
	 * @param userData the userData to set
	 */
	public void setUserData(final Serializable userData) {
		this.userData = userData;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append("messageId = ");
		builder.append(getMessageId());
		builder.append(", messageType = ");
		builder.append(decodeMessageType(getMessageType()));
		builder.append(", userData = ");
		builder.append("[");
		builder.append(getUserData());
		builder.append("]");
		builder.append("]");
		return builder.toString();
	}
}
