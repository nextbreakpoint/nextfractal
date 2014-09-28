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
package com.nextbreakpoint.nextfractal.devtools.descriptor.extension;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime;
import com.nextbreakpoint.nextfractal.devtools.ProcessorCardinality;
import com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor;

/**
 * @author Andrea Medeghini
 */
public abstract class DescriptorExtensionRuntime extends ExtensionRuntime {
	/**
	 * @param elementName
	 * @param defaultValue
	 * @param cardinality 
	 * @return
	 */
	public abstract ProcessorDescriptor createDescriptor(String elementName, String defaultValue, ProcessorCardinality cardinality);
	
	/**
	 * @return
	 */
	public abstract String getClassId();
}
