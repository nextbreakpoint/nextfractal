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
package com.nextbreakpoint.nextfractal.devtools.extensions.processor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nextbreakpoint.nextfractal.devtools.DevToolsException;
import com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor;
import com.nextbreakpoint.nextfractal.devtools.ProcessorParameters;
import com.nextbreakpoint.nextfractal.devtools.ProcessorTemplateLoader;
import com.nextbreakpoint.nextfractal.devtools.extensionPoints.processor.ProcessorExtensionRuntime;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExtensionConfigXMLExporterProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.extensionPoints.processor.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
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
			List<String> sortedImports = new LinkedList<String>(imports);
			Collections.sort(sortedImports);
			map.putAll(variables);
			map.put("imports", sortedImports);
			map.put("extension", element);
			map.put("subelements", elements);
			if (!variables.containsKey("generateAbstractClass") && element.isConfigurableExtension()) {
				File packagePath = new File(path, element.getExtensionConfigPackageName().replace('.', '/'));
				packagePath.mkdirs();
				Configuration config = new Configuration();
				config.setTemplateLoader(new ProcessorTemplateLoader());
				Template template = config.getTemplate("templates/ExtensionConfigXMLExporter.ftl");
				template.process(map, new PrintWriter(new File(packagePath, element.getExtensionConfigClassName() + "XMLExporterRuntime.java")));
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.extensionPoints.processor.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "ExtensionConfigXMLExporter";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
		imports.add("com.nextbreakpoint.nextfractal.core.xml.extension.ExtensionConfigXMLExporterExtensionRuntime");
		imports.add("com.nextbreakpoint.nextfractal.core.extension.ExtensionException");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.XMLExportException");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.XMLExporter");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder");
		imports.add("org.w3c.dom.Element");
	}

	private void prepare(Set<String> imports, List<ProcessorDescriptor> descriptors) {
		for (ProcessorDescriptor descriptor : descriptors) {
			if (descriptor.isExtensionElement() || descriptor.isConfigurableExtensionElement() || descriptor.isComplexElement()) {
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLExporter");
				if (descriptor.isConfigurableExtensionElement()) {
					imports.add(descriptor.getExtensionConfigPackageName() + "." + descriptor.getExtensionConfigClassName());
				}
			}
			else if (descriptor.isSimpleElement()) {
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLExporter");
			}
		}
	}
}
