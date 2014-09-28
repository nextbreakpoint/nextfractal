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
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceElementEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceElementEditorRuntime extends ReferenceElementEditorRuntime</#if> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#createModel()
	 */
	@Override
	protected ExtensionComboBoxModel createModel() {
		return new ExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ExtensionReference reference) {
		final ${configElementClassName} configElement = new ${configElementClassName}();
		configElement.setExtensionReference(reference);
		return new ${configElementClassName}NodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getInsertBeforeLabel()
	 */
	@Override
	protected String getInsertBeforeLabel() {
		return ${resourcesClassName}.getInstance().getString("action.insert${extension.elementName?cap_first}Before");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return ${resourcesClassName}.getInstance().getString("action.insert${extension.elementName?cap_first}After");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return ${resourcesClassName}.getInstance().getString("action.remove${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.insert${extension.elementName?cap_first}After");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.insert${extension.elementName?cap_first}Before");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.remove${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return ${resourcesClassName}.getInstance().getString("action.moveUp${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.moveDown${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return ${resourcesClassName}.getInstance().getString("action.moveUp${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.moveUp${extension.elementName?cap_first}");
	}
} 
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class <#if parentRuntimeClass?exists>${extension.elementName?cap_first}ReferenceElementEditorRuntime extends ${parentRuntimeClass}<#else>${extension.elementName?cap_first}ReferenceElementEditorRuntime extends ConfigurableReferenceElementEditorRuntime</#if> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createModel()
	 */
	@Override
	protected ConfigurableExtensionComboBoxModel createModel() {
		return new ConfigurableExtensionComboBoxModel(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected NodeValue createNodeValue(final ConfigurableExtensionReference reference) {
		final ${configElementClassName} configElement = new ${configElementClassName}();
		configElement.setExtensionReference(reference);
		return new ${configElementClassName}NodeValue(configElement);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#createChildValue()
	 */
	@Override
	protected NodeValue<?> createChildValue() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeLabel()
	 */
	@Override
	protected String getInsertBeforeLabel() {
		return ${resourcesClassName}.getInstance().getString("action.insert${extension.elementName?cap_first}Before");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterLabel()
	 */
	@Override
	protected String getInsertAfterLabel() {
		return ${resourcesClassName}.getInstance().getString("action.insert${extension.elementName?cap_first}After");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveLabel()
	 */
	@Override
	protected String getRemoveLabel() {
		return ${resourcesClassName}.getInstance().getString("action.remove${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertAfterTooltip()
	 */
	@Override
	protected String getInsertAfterTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.insert${extension.elementName?cap_first}After");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getInsertBeforeTooltip()
	 */
	@Override
	protected String getInsertBeforeTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.insert${extension.elementName?cap_first}Before");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getRemoveTooltip()
	 */
	@Override
	protected String getRemoveTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.remove${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownLabel()
	 */
	@Override
	protected String getMoveDownLabel() {
		return ${resourcesClassName}.getInstance().getString("action.moveUp${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveDownTooltip()
	 */
	@Override
	protected String getMoveDownTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.moveDown${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpLabel()
	 */
	@Override
	protected String getMoveUpLabel() {
		return ${resourcesClassName}.getInstance().getString("action.moveUp${extension.elementName?cap_first}");
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.swing.editor.ConfigurableReferenceElementEditorRuntime#getMoveUpTooltip()
	 */
	@Override
	protected String getMoveUpTooltip() {
		return ${resourcesClassName}.getInstance().getString("tooltip.moveUp${extension.elementName?cap_first}");
	}
} 
</#if>
 