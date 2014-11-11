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
package com.nextbreakpoint.nextfractal.core.extensions.processor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nextbreakpoint.nextfractal.core.devtools.DevToolsException;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorCardinality;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorDescriptor;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorParameters;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorTemplateLoader;
import com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExtensionRuntimeProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.core.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@Override
	public void process(File path, ProcessorParameters parameters, Map<String, String> variables) throws DevToolsException {
		try {
			ProcessorDescriptor element = parameters.getElement();
			List<ProcessorDescriptor> elements = parameters.getElements();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Set<String> imports = new HashSet<String>();
			prepare(imports, element);
			prepare(imports, elements);
			removeUnused(imports, variables);
			List<String> sortedImports = new LinkedList<String>(imports);
			Collections.sort(sortedImports);
			map.putAll(variables);
			map.put("imports", sortedImports);
			map.put("extension", element);
			map.put("subelements", elements);
			if (element.isExtension() || element.isConfigurableExtension()) {
				File packagePath = new File(path, element.getExtensionRuntimePackageName().replace('.', '/'));
				packagePath.mkdirs();
				Configuration config = new Configuration();
				config.setTemplateLoader(new ProcessorTemplateLoader());
				Template template = config.getTemplate("templates/ExtensionRuntime.ftl");
				template.process(map, new PrintWriter(new File(packagePath, element.getExtensionRuntimeClassName() + ".java")));
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	private void removeUnused(Set<String> imports, Map<String, String> variables) {
		if (variables.containsKey("parentRuntimePackage") && variables.containsKey("parentRuntimeClass")) {
			imports.remove("com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime");
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "ExtensionRuntime";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
		imports.add("com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent");
		imports.add("com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener");
		if (descriptor.isExtension()) {
			imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionRuntime");
		}
		else if (descriptor.isConfigurableExtension()) {
			imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime");
		}
	}

	private void prepare(Set<String> imports, List<ProcessorDescriptor> descriptors) {
		for (ProcessorDescriptor descriptor : descriptors) {
			if (descriptor.isExtensionElement()) {
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement");
				imports.add("com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElement");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference");
				imports.add(descriptor.getExtensionRuntimePackageName() + "." + descriptor.getExtensionRuntimeClassName());
				imports.add(descriptor.getRegistryPackageName() + "." + descriptor.getRegistryClassName());
			}
			else if (descriptor.isConfigurableExtensionElement()) {
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement");
				imports.add("com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElement");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException");
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference");
				imports.add(descriptor.getExtensionConfigPackageName() + "." + descriptor.getExtensionConfigClassName());
				imports.add(descriptor.getExtensionRuntimePackageName() + "." + descriptor.getExtensionRuntimeClassName());
				imports.add(descriptor.getRegistryPackageName() + "." + descriptor.getRegistryClassName());
			}
			else if (descriptor.isComplexElement()) {
				if (descriptor.getCardinality() == ProcessorCardinality.ONE) {
					imports.add("com.nextbreakpoint.nextfractal.core.runtime.SingleConfigElement");
					imports.add("com.nextbreakpoint.nextfractal.core.runtime.SingleRuntimeElement");
					imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
				}
				if (descriptor.getCardinality() == ProcessorCardinality.MANY) {
					imports.add("com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement");
					imports.add("com.nextbreakpoint.nextfractal.core.runtime.ListRuntimeElement");
					imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
				}
				imports.add(descriptor.getRuntimeElementPackageName() + "." + descriptor.getRuntimeElementClassName());
			}
			else if (descriptor.isSimpleElement()) {
				imports.add("com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement");
				imports.add(descriptor.getValuePackageName() + "." + descriptor.getValueClassName());
			}
		}
	}
}
