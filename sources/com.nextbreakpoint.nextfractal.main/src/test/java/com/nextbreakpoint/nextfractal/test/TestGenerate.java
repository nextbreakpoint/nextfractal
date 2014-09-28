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
package com.nextbreakpoint.nextfractal.test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.devtools.DevToolsRegistry;
import com.nextbreakpoint.nextfractal.devtools.ProcessorCardinality;
import com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor;
import com.nextbreakpoint.nextfractal.devtools.ProcessorParameters;
import com.nextbreakpoint.nextfractal.devtools.descriptor.extension.DescriptorExtensionRuntime;
import com.nextbreakpoint.nextfractal.devtools.processor.extension.ProcessorExtensionRuntime;

public class TestGenerate {
	private ProcessorParameters createComplexParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "Test", "Test", "com.nextbreakpoint.nextfractal.core.common", "ConfigurableExtensionReferenceElement", null, null, "com.nextbreakpoint.nextfractal.test", "TestExtensionConfig", "com.nextbreakpoint.nextfractal.test", "TestExtensionRuntime", null, null, "com.nextbreakpoint.nextfractal.test", "TestRegistry", "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, "get", "set", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Boolean").createDescriptor("enabled", "Boolean.TRUE", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.ONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("test", "Test", "Test", "com.nextbreakpoint.nextfractal.test", "TestConfigElement", "com.nextbreakpoint.nextfractal.test", "TestRuntimeElement", null, null, null, null, null, null, null, null, "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, "get", "set", ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createSimpleParameters() {
		return new ProcessorParameters(new ProcessorDescriptor("test", "Test", "TestBoolean", "com.nextbreakpoint.nextfractal.test", "TestElement", null, null, null, null, null, null, null, null, null, null, "com.nextbreakpoint.nextfractal.core", "CoreResources", "java.lang", "Boolean", "Boolean.TRUE", "get", "set", ProcessorCardinality.NONE), new LinkedList<ProcessorDescriptor>());
	}
	
	private ProcessorParameters createExtensionParameters(Map<String, DescriptorExtensionRuntime> descriptorExtensionMap) {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("extension", "Test", "Test", "com.nextbreakpoint.nextfractal.core.common", "ConfigurableExtensionReferenceElement", null, null, "com.nextbreakpoint.nextfractal.test", "TestExtensionConfig", "com.nextbreakpoint.nextfractal.test", "TestExtensionRuntime", null, null, "com.nextbreakpoint.nextfractal.test", "TestRegistry", "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, "get", "set", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Integer").createDescriptor("size", "new Integer(10)", ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.NONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.ONE));
		descriptors.add(descriptorExtensionMap.get("Effect").createDescriptor("effect", null, ProcessorCardinality.MANY));
		return new ProcessorParameters(new ProcessorDescriptor("test", "Extension", null, null, null, null, null, "com.nextbreakpoint.nextfractal.test", "TestExtensionConfig", "com.nextbreakpoint.nextfractal.test", "TestExtensionRuntime", "com.nextbreakpoint.nextfractal.test", "TestExtensionRegistry", null, null, "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private ProcessorParameters createRegistryParameters() {
		List<ProcessorDescriptor> descriptors = new LinkedList<ProcessorDescriptor>();
		descriptors.add(new ProcessorDescriptor("test", "Extension", null, null, null, null, null, "com.nextbreakpoint.nextfractal.test", "TestExtensionConfig", "com.nextbreakpoint.nextfractal.test", "TestExtensionRuntime", "com.nextbreakpoint.nextfractal.test", "TestExtensionRegistry", null, null, "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, null, null, ProcessorCardinality.NONE));
		return new ProcessorParameters(new ProcessorDescriptor("test", "Test", null, null, null, null, null, null, null, null, null, null, null, "com.nextbreakpoint.nextfractal.test", "TestRegistry", "com.nextbreakpoint.nextfractal.core", "CoreResources", null, null, null, null, null, ProcessorCardinality.NONE), descriptors);
	}
	
	private void populateProcessorExtensionMap(Map<String, ProcessorExtensionRuntime> map) {
		List<Extension<ProcessorExtensionRuntime>> extensions = DevToolsRegistry.getInstance().getProcessorRegistry().getExtensionList();
		for (Extension<ProcessorExtensionRuntime> extension : extensions) {
			try {
				ProcessorExtensionRuntime runtime = extension.createExtensionRuntime();
				map.put(runtime.getName(), runtime);
			}
			catch (ExtensionException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void populateDescriptorExtensionMap(Map<String, DescriptorExtensionRuntime> map) {
		List<Extension<DescriptorExtensionRuntime>> extensions = DevToolsRegistry.getInstance().getDescriptorRegistry().getExtensionList();
		for (Extension<DescriptorExtensionRuntime> extension : extensions) {
			try {
				DescriptorExtensionRuntime runtime = extension.createExtensionRuntime();
				map.put(runtime.getClassId(), runtime);
			}
			catch (ExtensionException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> createVariables() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("author", "Andrea Medeghini");
		map.put("extensionPointId", "com.nextbreakpoint.nextfractal.test.extensions");
		return map;
	}

	@Test
	public void testComplex() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			Map<String, String> variables = createVariables();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			ProcessorParameters parameters = createComplexParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSimple() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			Map<String, String> variables = createVariables();
			ProcessorParameters parameters = createSimpleParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExtension() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			Map<String, String> variables = createVariables();
			ProcessorParameters parameters = createExtensionParameters(descriptorExtensionMap);
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegistry() {
		try {
			File path = new File("build");
			Map<String, ProcessorExtensionRuntime> processorExtensionMap = new HashMap<String, ProcessorExtensionRuntime>();
			populateProcessorExtensionMap(processorExtensionMap);
			Map<String, DescriptorExtensionRuntime> descriptorExtensionMap = new HashMap<String, DescriptorExtensionRuntime>();
			populateDescriptorExtensionMap(descriptorExtensionMap);
			Map<String, String> variables = createVariables();
			ProcessorParameters parameters = createRegistryParameters();
			for (ProcessorExtensionRuntime processorRuntime : processorExtensionMap.values()) {
				processorRuntime.process(path, parameters, variables);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
