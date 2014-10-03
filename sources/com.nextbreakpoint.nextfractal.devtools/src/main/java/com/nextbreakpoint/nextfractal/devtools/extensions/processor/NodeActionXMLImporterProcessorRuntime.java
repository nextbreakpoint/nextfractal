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
import com.nextbreakpoint.nextfractal.devtools.processor.extension.ProcessorExtensionRuntime;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class NodeActionXMLImporterProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.processor.extension.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@Override
	public void process(File path, ProcessorParameters parameters, Map<String, String> variables) throws DevToolsException {
		try {
			ProcessorDescriptor element = parameters.getElement();
			if (element.isExtension() || element.isConfigurableExtension()) {
				if (variables.containsKey("generateReferenceNodeActionXMLImporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLImporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareReferenceNodeActionXMLImporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("extension", element);
					Template template = config.getTemplate("templates/ReferenceNodeActionXMLImporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ReferenceNodeActionXMLImporterRuntime.java")));
				}
			}
			else if (element.isComplexElement() || element.isSimpleElement()) {
				if (variables.containsKey("generateElementNodeActionXMLImporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLImporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareElementNodeActionXMLImporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("element", element);
					Template template = config.getTemplate("templates/ElementNodeActionXMLImporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ElementNodeActionXMLImporterRuntime.java")));
				} 
				if (variables.containsKey("generateElementListNodeActionXMLImporter")) {
					File packagePath = new File(path, variables.get("nodeActionXMLImporterPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareElementListNodeActionXMLImporter(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("element", element);
					Template template = config.getTemplate("templates/ElementListNodeActionXMLImporterRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "ElementListNodeActionXMLImporterRuntime.java")));
				}
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.processor.extension.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "NodeActionXMLImporterRuntime";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
	}

	private void prepareReferenceNodeActionXMLImporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		if (descriptor.isExtension()) {
			imports.add("com.nextbreakpoint.nextfractal.core.util.AbstractExtensionReferenceElementNodeActionXMLImporterRuntime");
		} else {
			imports.add("com.nextbreakpoint.nextfractal.core.util.AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime");
		}
		imports.add(descriptor.getExtensionConfigPackageName() + "." + descriptor.getExtensionConfigClassName());
		imports.add(descriptor.getRegistryPackageName() + "." + descriptor.getRegistryClassName());
	}

	private void prepareElementNodeActionXMLImporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		imports.add("com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNodeActionXMLImporterRuntime");
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLImporter");
	}

	private void prepareElementListNodeActionXMLImporter(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		imports.add("com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNodeActionXMLImporterRuntime");
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
		imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLImporter");
	}

	private static String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }
}
