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
package com.nextbreakpoint.nextfractal.core;

import com.nextbreakpoint.nextfractal.core.actionXMLExporter.extension.ActionXMLExporterExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.actionXMLExporter.extension.ActionXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.actionXMLImporter.extension.ActionXMLImporterExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.actionXMLImporter.extension.ActionXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.constructor.extension.ConstructorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.constructor.extension.ConstructorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.creator.extension.CreatorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.creator.extension.CreatorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;

/**
 * The core registry.
 * 
 * @author Andrea Medeghini
 */
public class CoreRegistry {
	private ExtensionRegistry<NodeBuilderExtensionRuntime> nodeBuilderRegistry;
	private ExtensionRegistry<ExtensionConfigXMLExporterExtensionRuntime> XMLExtensionConfigExporterRegistry;
	private ExtensionRegistry<ExtensionConfigXMLImporterExtensionRuntime> XMLExtensionConfigImporterRegistry;
	private ExtensionRegistry<ActionXMLExporterExtensionRuntime> XMLActionExporterRegistry;
	private ExtensionRegistry<ActionXMLImporterExtensionRuntime> XMLActionImporterRegistry;
	private ExtensionRegistry<ConstructorExtensionRuntime> constructorRegistry;
	private ExtensionRegistry<EnumeratorExtensionRuntime> enumeratorRegistry;
	private ExtensionRegistry<CreatorExtensionRuntime> creatorRegistry;

	private static class RegistryHolder {
		private static final CoreRegistry instance = new CoreRegistry();
	}

	private CoreRegistry() {
		setNodeBuilderRegistry(new NodeBuilderExtensionRegistry());
		setXMLExtensionConfigExporterRegistry(new ExtensionConfigXMLExporterExtensionRegistry());
		setXMLExtensionConfigImporterRegistry(new ExtensionConfigXMLImporterExtensionRegistry());
		setXMLActionExporterRegistry(new ActionXMLExporterExtensionRegistry());
		setXMLActionImporterRegistry(new ActionXMLImporterExtensionRegistry());
		setConstructorRegistry(new ConstructorExtensionRegistry());
		setEnumeratorRegistry(new EnumeratorExtensionRegistry());
		setCreatorRegistry(new CreatorExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static CoreRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a node builder extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<NodeBuilderExtensionRuntime> getNodeBuilderExtension(final String extensionId) throws ExtensionNotFoundException {
		return nodeBuilderRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a XML exporter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ExtensionConfigXMLExporterExtensionRuntime> getXMLExtensionConfigExporterExtension(final String extensionId) throws ExtensionNotFoundException {
		return XMLExtensionConfigExporterRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a XML importer extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ExtensionConfigXMLImporterExtensionRuntime> getXMLExtensionConfigImporterExtension(final String extensionId) throws ExtensionNotFoundException {
		return XMLExtensionConfigImporterRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a XML exporter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ActionXMLExporterExtensionRuntime> getXMLActionExporterExtension(final String extensionId) throws ExtensionNotFoundException {
		return XMLActionExporterRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a XML importer extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ActionXMLImporterExtensionRuntime> getXMLActionImporterExtension(final String extensionId) throws ExtensionNotFoundException {
		return XMLActionImporterRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a constructor extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ConstructorExtensionRuntime> getConstructorExtension(final String extensionId) throws ExtensionNotFoundException {
		return constructorRegistry.getExtension(extensionId);
	}

	/**
	 * Returns an enumerator extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<EnumeratorExtensionRuntime> getEnumeratorExtension(final String extensionId) throws ExtensionNotFoundException {
		return enumeratorRegistry.getExtension(extensionId);
	}

	/**
	 * Returns an creator extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<CreatorExtensionRuntime> getCreatorExtension(final String extensionId) throws ExtensionNotFoundException {
		return creatorRegistry.getExtension(extensionId);
	}

	private void setNodeBuilderRegistry(final ExtensionRegistry<NodeBuilderExtensionRuntime> nodeBuilderRegistry) {
		this.nodeBuilderRegistry = nodeBuilderRegistry;
	}

	private void setXMLExtensionConfigExporterRegistry(final ExtensionRegistry<ExtensionConfigXMLExporterExtensionRuntime> XMLExtensionConfigExporterRegistry) {
		this.XMLExtensionConfigExporterRegistry = XMLExtensionConfigExporterRegistry;
	}

	private void setXMLExtensionConfigImporterRegistry(final ExtensionRegistry<ExtensionConfigXMLImporterExtensionRuntime> XMLExtensionConfigImporterRegistry) {
		this.XMLExtensionConfigImporterRegistry = XMLExtensionConfigImporterRegistry;
	}

	private void setXMLActionExporterRegistry(final ExtensionRegistry<ActionXMLExporterExtensionRuntime> XMLActionExporterRegistry) {
		this.XMLActionExporterRegistry = XMLActionExporterRegistry;
	}

	private void setXMLActionImporterRegistry(final ExtensionRegistry<ActionXMLImporterExtensionRuntime> XMLActionImporterRegistry) {
		this.XMLActionImporterRegistry = XMLActionImporterRegistry;
	}

	private void setConstructorRegistry(final ExtensionRegistry<ConstructorExtensionRuntime> constructorRegistry) {
		this.constructorRegistry = constructorRegistry;
	}

	private void setEnumeratorRegistry(final ExtensionRegistry<EnumeratorExtensionRuntime> enumeratorRegistry) {
		this.enumeratorRegistry = enumeratorRegistry;
	}

	private void setCreatorRegistry(final ExtensionRegistry<CreatorExtensionRuntime> creatorRegistry) {
		this.creatorRegistry = creatorRegistry;
	}

	/**
	 * @return the nodeBuilderRegistry
	 */
	public ExtensionRegistry<NodeBuilderExtensionRuntime> getNodeBuilderRegistry() {
		return nodeBuilderRegistry;
	}

	/**
	 * @return the XMLExtensionConfigExporterRegistry
	 */
	public ExtensionRegistry<ExtensionConfigXMLExporterExtensionRuntime> getXMLExtensionConfigExporterRegistry() {
		return XMLExtensionConfigExporterRegistry;
	}

	/**
	 * @return the XMLExtensionConfigImporterRegistry
	 */
	public ExtensionRegistry<ExtensionConfigXMLImporterExtensionRuntime> getXMLExtensionConfigImporterRegistry() {
		return XMLExtensionConfigImporterRegistry;
	}

	/**
	 * @return the XMLActionExporterRegistry
	 */
	public ExtensionRegistry<ActionXMLExporterExtensionRuntime> getXMLActionExporterRegistry() {
		return XMLActionExporterRegistry;
	}

	/**
	 * @return the XMLActionImporterRegistry
	 */
	public ExtensionRegistry<ActionXMLImporterExtensionRuntime> getXMLActionImporterRegistry() {
		return XMLActionImporterRegistry;
	}

	/**
	 * @return the constructorRegistry
	 */
	public ExtensionRegistry<ConstructorExtensionRuntime> getConstructorRegistry() {
		return constructorRegistry;
	}

	/**
	 * @return the enumeratorRegistry
	 */
	public ExtensionRegistry<EnumeratorExtensionRuntime> getEnumeratorRegistry() {
		return enumeratorRegistry;
	}

	/**
	 * @return the creatorRegistry
	 */
	public ExtensionRegistry<CreatorExtensionRuntime> getCreatorRegistry() {
		return creatorRegistry;
	}
}
