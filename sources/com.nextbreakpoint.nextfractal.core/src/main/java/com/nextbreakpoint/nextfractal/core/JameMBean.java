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
package com.nextbreakpoint.nextfractal.core;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

/**
 * Experimental JMX MBean implementation.
 * 
 * @author Andrea Medeghini
 */
public class JameMBean extends StandardMBean implements JameMBeanInterface {
	private int value;

	/**
	 * Constructs a new JMX MBean.
	 * 
	 * @throws NotCompliantMBeanException
	 */
	public JameMBean() throws NotCompliantMBeanException {
		super(JameMBeanInterface.class);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.runtime.JameMBeanInterface#getAuthorName()
	 */
	@Override
	public String getAuthorName() {
		return "Andrea Medeghini";
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.runtime.JameMBeanInterface#getAuthorName()
	 */
	@Override
	public void setValue(final int value) {
		this.value = value;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.runtime.JameMBeanInterface#getValue()
	 */
	@Override
	public int getValue() {
		return value;
	}
}
