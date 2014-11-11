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
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceElementListEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceElementListEditorRuntime extends ReferenceElementListEditorRuntime</#if> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#createModel()
	 */
	@Override
	protected ExtensionComboBoxModel createModel() {
		return new ExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ExtensionReference reference) {
		final ${configElementClassName} configElement = new ${configElementClassName}();
		configElement.setExtensionReference(reference);
		return new ${configElementClassName}NodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return ${resourcesClassName}.getInstance().getString("action.append${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return ${resourcesClassName}.getInstance().getString("action.removeAll${extension.elementName?cap_first}s");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.append${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.removeAll${extension.elementName?cap_first}s");
	}
} 
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceElementListEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceElementListEditorRuntime</#if> extends ConfigurableReferenceElementListEditorRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final ${configElementClassName} configElement = new ${configElementClassName}();
		configElement.setExtensionReference(reference);
		return new ${configElementClassName}NodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendLabel()
	 */
	@Override
	protected String getAppendLabel() {
		return ${resourcesClassName}.getInstance().getString("action.append${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllLabel()
	 */
	@Override
	protected String getRemoveAllLabel() {
		return ${resourcesClassName}.getInstance().getString("action.removeAll${extension.elementName?cap_first}s");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getAppendTooltip()
	 */
	@Override
	protected String getAppendTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.append${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementListEditorRuntime#getRemoveAllTooltip()
	 */
	@Override
	protected String getRemoveAllTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.removeAll${extension.elementName?cap_first}s");
	}
} 
</#if>
 