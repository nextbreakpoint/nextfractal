/*
 * $Id:$
 *
 */
package ${editorPackageName};

<#list imports as import>
import ${import};
</#list>
<#if extension.extension>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceEditorRuntime extends ReferenceEditorRuntime</#if> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceEditorRuntime#createModel()
	 */
	@Override
	protected ExtensionComboBoxModel createModel() {
		return new ExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ExtensionReference reference) {
		return new ${extension.elementName?cap_first}ExtensionReferenceNodeValue(reference);
	}
} 
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceEditorRuntime extends ConfigurableReferenceEditorRuntime</#if> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		return new ${extension.elementName?cap_first}ExtensionReferenceNodeValue(reference);
	}
}
</#if>
 