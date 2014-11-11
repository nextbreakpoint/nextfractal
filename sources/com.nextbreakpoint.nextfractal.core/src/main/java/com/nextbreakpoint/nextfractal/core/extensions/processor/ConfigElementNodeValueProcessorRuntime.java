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
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorDescriptor;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorParameters;
import com.nextbreakpoint.nextfractal.core.devtools.ProcessorTemplateLoader;
import com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ConfigElementNodeValueProcessorRuntime extends ProcessorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime#process(java.io.File, com.nextbreakpoint.nextfractal.core.devtools.ProcessorDescriptor, java.util.List, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
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
			map.put("element", element);
			map.put("subelements", elements);
			if (element.isComplexElement() || element.isSimpleElement()) {
				File packagePath = new File(path, element.getConfigElementPackageName().replace('.', '/'));
				packagePath.mkdirs();
				Configuration config = new Configuration();
				config.setTemplateLoader(new ProcessorTemplateLoader());
				Template template = config.getTemplate("templates/ConfigElementNodeValue.ftl");
				template.process(map, new PrintWriter(new File(packagePath, element.getConfigElementClassName() + "NodeValue.java")));
				List<ProcessorDescriptor> tmpDescriptors = (List<ProcessorDescriptor>) map.get("subelements");
				if (tmpDescriptors != null) {
					for (ProcessorDescriptor tmpDescriptor : tmpDescriptors) {
						if (tmpDescriptor.isExtensionElement()) {
							Template tmpTemplate = config.getTemplate("templates/ReferenceNodeValue.ftl");
							HashMap<String, Object> tmpMap = new HashMap<String, Object>();
							tmpMap.putAll(variables);
							tmpMap.put("packageName", element.getConfigElementPackageName());
							tmpMap.put("elementName", element.getElementName());
							tmpMap.put("subElementName", tmpDescriptor.getElementName());
							tmpMap.put("runtimeClassName", tmpDescriptor.getExtensionRuntimeClassName());
							tmpMap.put("runtimePackageName", tmpDescriptor.getExtensionRuntimePackageName());
							tmpTemplate.process(tmpMap, new PrintWriter(new File(packagePath, capitalize((String) tmpMap.get("elementName")) + capitalize((String) tmpMap.get("subElementName")) + "ReferenceNodeValue.java")));
						}
						else if (tmpDescriptor.isConfigurableExtensionElement()) {
							Template tmpTemplate = config.getTemplate("templates/ReferenceNodeValue.ftl");
							HashMap<String, Object> tmpMap = new HashMap<String, Object>();
							tmpMap.putAll(variables);
							tmpMap.put("packageName", element.getConfigElementPackageName());
							tmpMap.put("elementName", element.getElementName());
							tmpMap.put("subElementName", tmpDescriptor.getElementName());
							tmpMap.put("configClassName", tmpDescriptor.getExtensionConfigClassName());
							tmpMap.put("configPackageName", tmpDescriptor.getExtensionConfigPackageName());
							tmpMap.put("runtimeClassName", tmpDescriptor.getExtensionRuntimeClassName());
							tmpMap.put("runtimePackageName", tmpDescriptor.getExtensionRuntimePackageName());
							tmpTemplate.process(tmpMap, new PrintWriter(new File(packagePath, capitalize((String) tmpMap.get("elementName")) + capitalize((String) tmpMap.get("subElementName")) + "ReferenceNodeValue.java")));
						}
					}
				}
			}
		}
		catch (Exception e) {
			throw new DevToolsException(e);
		}
	}

	private static String capitalize(String word) {
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime#getName()
	 */
	@Override
	public String getName() {
		return "ConfigElementNodeValue";
	}

	private void prepare(Set<String> imports, ProcessorDescriptor descriptor) {
		if (descriptor.isSimpleElement() || descriptor.isComplexElement()) {
			imports.add("com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue");
		}
	}

	private void prepare(Set<String> imports, List<ProcessorDescriptor> descriptors) {
	}
}
