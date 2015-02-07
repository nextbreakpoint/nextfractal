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
package com.nextbreakpoint.nextfractal.net;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public abstract class ServiceMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MESSAGE_TYPE_EVENT = 10;
	public static final int MESSAGE_TYPE_REQUEST = 20;
	public static final int MESSAGE_TYPE_RESPONSE = 30;
	private final String messageId;
	private final int messageType;

	/**
	 * @param messageId
	 * @param messageType
	 */
	protected ServiceMessage(final String messageId, final int messageType) {
		this.messageId = messageId;
		this.messageType = messageType;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageTpe
	 * @return
	 */
	public static final String decodeMessageType(final int messageType) {
		switch (messageType) {
			case MESSAGE_TYPE_EVENT:
				return "EVENT";
			case MESSAGE_TYPE_REQUEST:
				return "REQUEST";
			case MESSAGE_TYPE_RESPONSE:
				return "RESPONSE";
			default:
				return "unknown";
		}
	}
}
