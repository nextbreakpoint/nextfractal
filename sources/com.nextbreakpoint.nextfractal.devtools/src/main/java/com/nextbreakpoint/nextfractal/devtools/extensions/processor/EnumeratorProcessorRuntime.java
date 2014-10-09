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

public class EnumeratorProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.devtools.extensionPoints.processor.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@Override
	public void process(File path, ProcessorParameters parameters, Map<String, String> variables) throws DevToolsException {
		try {
			ProcessorDescriptor element = parameters.getElement();
			if (element.isExtension() || element.isConfigurableExtension()) {
				if (variables.containsKey("generateEnumerator")) {
					File packagePath = new File(path, variables.get("enumeratorPackageName").replace('.', '/'));
					packagePath.mkdirs();
					Configuration config = new Configuration();
					config.setTemplateLoader(new ProcessorTemplateLoader());
					HashMap<String, Object> map = new HashMap<String, Object>();
					Set<String> imports = new HashSet<String>();
					prepare(imports, element);
					map.putAll(variables);
					prepareEnumerator(imports, element, variables);
					List<String> sortedImports = new LinkedList<String>(imports);
					Collections.sort(sortedImports);
					map.put("imports", sortedImports);
					map.put("extension", element);
					Template template = config.getTemplate("templates/EnumeratorRuntime.ftl");
					template.process(map, new PrintWriter(new File(packagePath, capitalize(element.getElementName()) + "EnumeratorRuntime.java")));
				}
			}
			else if (element.isComplexElement() || element.isSimpleElement()) {
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
		return "EnumeratorRuntime";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
	}

	private void prepareEnumerator(Set<String> imports, ProcessorDescriptor descriptor, Map<String, String> variables) {
		imports.add("java.util.LinkedList");
		imports.add("java.util.List");
		imports.add("com.nextbreakpoint.nextfractal.core.extension.Extension");
		imports.add("com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException");
		imports.add("com.nextbreakpoint.nextfractal.core.scripting.JSException");
		imports.add("com.nextbreakpoint.nextfractal.core.scripting.JSExtension");
		imports.add("com.nextbreakpoint.nextfractal.core.scripting.extension.EnumeratorExtensionRuntime");
		imports.add(descriptor.getRegistryPackageName() + "." + descriptor.getRegistryClassName());
		imports.add(descriptor.getExtensionRuntimePackageName() + "." + descriptor.getExtensionRuntimeClassName());
	}

	private static String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }
}
