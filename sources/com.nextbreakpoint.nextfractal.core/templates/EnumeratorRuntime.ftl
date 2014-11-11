/*
 * $Id:$
 *
 */
package ${enumeratorPackageName};

<#list imports as import>
import ${import};
</#list>

/**
 * @author Andrea Medeghini
 */
public class ${extension.elementName?cap_first}EnumeratorRuntime extends EnumeratorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.extension.EnumeratorExtensionRuntime#listExtensions()
	 */
	@Override
	public List<String> listExtensions() throws JSException {
		List<Extension<${extension.elementName?cap_first}ExtensionRuntime<?>>> extensions = ${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry().getExtensionList();
		List<String> references = new LinkedList<String>();
		for (Extension<${extension.elementName?cap_first}ExtensionRuntime<?>> extension : extensions) {
			references.add(extension.getExtensionId());
		}
		return references;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.extension.EnumeratorExtensionRuntime#getExtension(java.lang.String)
	 */
	@Override
	public JSExtension getExtension(final String extensionId) throws JSException {
		try {
			return new JSExtension(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Extension(extensionId));
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
	}
}
 