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
public class RequestMessage extends ServiceMessage {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_HELLO = 10;
	public static final int TYPE_PUT = 20;
	public static final int TYPE_GET = 30;
	public static final int TYPE_ABORT = 40;
	public static final int TYPE_DELETE = 50;
	private String requestId;
	private int requestType;
	private Serializable userData;

	/**
	 * 
	 */
	public RequestMessage() {
		super(MessageIDFactory.newMessageId(), MESSAGE_TYPE_REQUEST);
	}

	/**
	 * @param requestType
	 * @return
	 */
	public static final String decodeRequestType(final int requestType) {
		switch (requestType) {
			case TYPE_HELLO:
				return "HELLO";
			case TYPE_PUT:
				return "PUT";
			case TYPE_GET:
				return "GET";
			case TYPE_ABORT:
				return "ABORT";
			case TYPE_DELETE:
				return "DELETE";
			default:
				return "unknown";
		}
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestType
	 */
	public int getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(final int requestType) {
		this.requestType = requestType;
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
		builder.append(", requestId = ");
		builder.append(getRequestId());
		builder.append(", requestType = ");
		builder.append(decodeRequestType(getRequestType()));
		builder.append(", userData = ");
		builder.append("[");
		builder.append(getUserData());
		builder.append("]");
		builder.append("]");
		return builder.toString();
	}
}
