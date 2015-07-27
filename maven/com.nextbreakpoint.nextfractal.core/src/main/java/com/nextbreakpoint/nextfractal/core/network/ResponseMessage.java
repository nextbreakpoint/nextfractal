/*
 * NextFractal 1.1.2
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
package com.nextbreakpoint.nextfractal.core.network;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class ResponseMessage extends ServiceMessage {
	private static final long serialVersionUID = 1L;
	private String requestId;
	private int responseType;
	private int returnCode;
	private Serializable userData;

	/**
	 * 
	 */
	public ResponseMessage() {
		super(newMessageId(), MESSAGE_TYPE_RESPONSE);
	}

	/**
	 * @param responseType
	 * @return
	 */
	public static final String decodeResponseType(final int responseType) {
		switch (responseType) {
			case RequestMessage.TYPE_HELLO:
				return "HELLO";
			case RequestMessage.TYPE_PUT:
				return "PUT";
			case RequestMessage.TYPE_GET:
				return "GET";
			case RequestMessage.TYPE_ABORT:
				return "ABORT";
			case RequestMessage.TYPE_DELETE:
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
	 * @return the responseType
	 */
	public int getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(final int responseType) {
		this.responseType = responseType;
	}

	/**
	 * @return the returnCode
	 */
	public int getReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode the returnCode to set
	 */
	public void setReturnCode(final int returnCode) {
		this.returnCode = returnCode;
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
		builder.append(", responseType = ");
		builder.append(decodeResponseType(getResponseType()));
		builder.append(", returnCode = ");
		builder.append(getReturnCode() == 0 ? "OK" : ("ERROR " + getReturnCode()));
		builder.append(", userData = ");
		builder.append("[");
		builder.append(getUserData());
		builder.append("]");
		builder.append("]");
		return builder.toString();
	}
}
