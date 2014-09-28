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

public class ExtensionConfigXMLImporterProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.processor.extension.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
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
				Template template = config.getTemplate("templates/ExtensionConfigXMLImporter.ftl");
				template.process(map, new PrintWriter(new File(packagePath, element.getExtensionConfigClassName() + "XMLImporterRuntime.java")));
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
		return "ExtensionConfigXMLImporter";
	}
	
	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
		imports.add("java.util.List");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime");
		imports.add("com.nextbreakpoint.nextfractal.core.extension.ExtensionException");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.XMLImportException");
		imports.add("com.nextbreakpoint.nextfractal.core.xml.XMLImporter");
		imports.add("org.w3c.dom.Element");
	}

	private void prepare(Set<String> imports, List<ProcessorDescriptor> descriptors) {
		for (ProcessorDescriptor descriptor : descriptors) {
			if (descriptor.isExtensionElement() || descriptor.isConfigurableExtensionElement() || descriptor.isComplexElement()) {
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLImporter");
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
				if (descriptor.isConfigurableExtensionElement()) {
					imports.add(descriptor.getExtensionConfigPackageName() + "." + descriptor.getExtensionConfigClassName());
				}
				if (descriptor.isExtensionElement() || descriptor.isConfigurableExtensionElement()) {
					imports.add(descriptor.getRegistryPackageName() + "." + descriptor.getRegistryClassName());
				}
			}
			else if (descriptor.isSimpleElement()) {
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName() + "XMLImporter");
				imports.add(descriptor.getConfigElementPackageName() + "." + descriptor.getConfigElementClassName());
			}
		}
	}
}
