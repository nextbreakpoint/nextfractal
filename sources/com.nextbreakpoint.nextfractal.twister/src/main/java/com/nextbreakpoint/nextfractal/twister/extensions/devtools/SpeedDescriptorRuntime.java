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
package com.nextbreakpoint.nextfractal.twister.extensions.devtools;

import com.nextbreakpoint.nextfractal.twister.common.SpeedElement;

import com.nextbreakpoint.nextfractal.devtools.ProcessorCardinality;
import com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor;
import com.nextbreakpoint.nextfractal.devtools.descriptor.extension.DescriptorExtensionRuntime;

public class SpeedDescriptorRuntime extends DescriptorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.descriptor.extension.DescriptorExtensionRuntime#createDescriptor()
	 */
	@Override
	public ProcessorDescriptor createDescriptor(String elementName, String defaultValue, ProcessorCardinality cardinality) {
		return new ProcessorDescriptor(elementName, "Speed", SpeedElement.CLASS_ID, "com.nextbreakpoint.nextfractal.twister.common", "SpeedElement", null, null, null, null, null, null, null, null, null, null, null, null, "com.nextbreakpoint.nextfractal.twister.util", "Speed", defaultValue, "get", "set", cardinality);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.descriptor.extension.DescriptorExtensionRuntime#getClassId()
	 */
	@Override
	public String getClassId() {
		return SpeedElement.CLASS_ID;
	}
}
