/*
 * $Id:$
 *
 */
package ${element.configElementPackageName};

<#if element.simpleElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public abstract class ${element.configElementClassName}Node extends AttributeNode {
	public static final String NODE_CLASS = "node.class.${element.elementClassId}Element";
	private final ConfigElementListener listener;
	private final ValueConfigElement<${element.valueClassName}> configElement;

	/**
	 * @param nodeId
	 * @param configElement
	 */
	public ${element.configElementClassName}Node(final String nodeId, final ValueConfigElement<${element.valueClassName}> configElement) {
		super(nodeId);
		setNodeClass(${element.configElementClassName}Node.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		setNodeValue(new ${element.configElementClassName}NodeValue(configElement.getValue()));
		setNodeLabel(${element.resourcesClassName}.getInstance().getString("node.label.${element.elementClassId}Element"));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#dispose()
	 */
	@Override
	public void dispose() {
		if (configElement != null) {
			configElement.removeChangeListener(listener);
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#setSession(com.nextbreakpoint.nextfractal.core.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			configElement.addChangeListener(listener);
		}
		else {
			configElement.removeChangeListener(listener);
		}
		super.setSession(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		setNodeValue(new ${element.configElementClassName}NodeValue(configElement.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		if (getNodeValue() != null) {
			return String.valueOf(getNodeValue().getValue());
		}
		else {
			return "";
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ${element.elementName?cap_first}NodeEditor(this);
	}

	protected class ${element.elementName?cap_first}NodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ${element.elementName?cap_first}NodeEditor(final AttributeNode node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((${element.configElementClassName}NodeValue) value).getValue());
			configElement.addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ${element.configElementClassName}NodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new ${element.configElementClassName}NodeValue((${element.valueClassName}) value);
		}
	}

	protected class ConfigElementListener implements ValueChangeListener {
		public void valueChanged(final ValueChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new ${element.configElementClassName}NodeValue((${element.valueClassName}) e.getParams()[0]));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0], e.getParams()[1]));
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
<#elseif element.complexElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.configElementClassName}Node extends AbstractConfigElementNode<${element.configElementClassName}> {
	private static final String NODE_ID = ${element.configElementClassName}.CLASS_ID;
	private static final String NODE_CLASS = "node.class.${element.elementClassId}Element";
	private static final String NODE_LABEL = ${element.resourcesClassName}.getInstance().getString("node.label.${element.elementClassId}Element");
	private final ${element.configElementClassName} ${element.elementName?uncap_first};

	/**
	 * Constructs a new effect node.
	 * 
	 * @param ${element.elementName?uncap_first} the ${element.elementName} element.
	 */
	public ${element.configElementClassName}Node(final ${element.configElementClassName} ${element.elementName?uncap_first}) {
		super(${element.configElementClassName}Node.NODE_ID);
		if (${element.elementName?uncap_first} == null) {
			throw new IllegalArgumentException("${element.elementName?uncap_first} is null");
		}
		this.${element.elementName?uncap_first} = ${element.elementName?uncap_first};
		setNodeLabel(${element.configElementClassName}Node.NODE_LABEL);
		setNodeClass(${element.configElementClassName}Node.NODE_CLASS);
		setNodeValue(new ${element.configElementClassName}NodeValue(${element.elementName?uncap_first}));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ${element.configElementClassName}Node) {
			return (${element.elementName?uncap_first} == ((${element.configElementClassName}Node) o).${element.elementName?uncap_first});
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ${element.configElementClassName} getConfigElement() {
		return ${element.elementName?uncap_first};
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#addDescription(java.lang.StringBuilder)
	 */
	@Override
	protected void addDescription(final StringBuilder builder) {
		if (getChildNodeCount() > 0) {
			builder.append(getChildNode(0).getLabel());
		}
		else {
			super.addDescription(builder);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((${element.configElementClassName}NodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ${element.elementName?cap_first}NodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final ${element.configElementClassName}NodeValue value) {
		removeAllChildNodes();
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		appendChildNode(new ${subelement.elementName?cap_first}ElementNode(${element.configElementClassName}Node.NODE_ID + ".${subelement.elementName?uncap_first}", value.getValue()));
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(value.getValue()));
		<#elseif subelement.cardinality == "ONE">
		appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(${element.configElementClassName}Node.NODE_ID + ".${subelement.elementName?uncap_first}Single", value.getValue()));
		<#elseif subelement.cardinality == "MANY">
		appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(${element.configElementClassName}Node.NODE_ID + ".${subelement.elementName?uncap_first}List", value.getValue()));
		</#if>
		<#elseif subelement.simpleElement>
		appendChildNode(new ${subelement.elementName?cap_first}ElementNode(${element.configElementClassName}Node.NODE_ID + ".${subelement.elementName?uncap_first}", value.getValue()));
		</#if>
		</#list>
	}

	private static class ${element.elementName?cap_first}NodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ${element.elementName?cap_first}NodeEditor(final Node node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new ${element.configElementClassName}NodeValue((${element.configElementClassName}) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ${element.configElementClassName}NodeValue.class;
		}
	}

	<#list subelements as subelement>
	<#if subelement.configurableExtensionElement>
	private static class ${subelement.elementName?cap_first}ElementNode extends ${subelement.configElementClassName}Node<${subelement.extensionConfigClassName}> {
		public static final String NODE_CLASS = "node.class.${subelement.elementClassId}Reference";

		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}ElementNode(final String nodeId, final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(nodeId, ${element.elementName?uncap_first}.get${subelement.elementName?cap_first}Element());
			setNodeClass(${subelement.elementName?cap_first}ElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.${subelement.configElementClassName}Node#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<${subelement.extensionConfigClassName}> value) {
			return new ${element.elementName?cap_first}ExtensionReferenceNodeValue(value);
		}
	}
	<#elseif subelement.extensionElement>
	private static class ${subelement.elementName?cap_first}ElementNode extends ${subelement.configElementClassName}Node {
		public static final String NODE_CLASS = "node.class.${subelement.elementClassId}Reference";

		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}ElementNode(final String nodeId, final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(nodeId, ${element.elementName?uncap_first}.get${subelement.elementName?cap_first}Element());
			setNodeClass(${subelement.elementName?cap_first}ElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.${subelement.configElementClassName}Node#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ExtensionReference value) {
			return new ${element.elementName?cap_first}ExtensionReferenceNodeValue(value);
		}
	}
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private static class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends ${subelement.configElementClassName}Node {
		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(${element.elementName?uncap_first}.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
		}
	}
	<#elseif subelement.cardinality == "ONE">
	private static class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends AbstractConfigElementSingleNode<${subelement.configElementClassName}> {
		public static final String NODE_CLASS = "node.class.${subelement.elementClassId}${subelement.fieldNameSuffix}";

		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final String nodeId, final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(nodeId, ${element.elementName?uncap_first}.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
			setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<${subelement.configElementClassName}> createChildNode(final ${subelement.configElementClassName} value) {
			return new ${subelement.configElementClassName}Node(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<${subelement.configElementClassName}> createNodeValue(final Object value) {
			return new ${subelement.configElementClassName}NodeValue((${subelement.configElementClassName}) value);
		}

		private class ${subelement.configElementClassName}NodeValue extends ConfigElementSingleNodeValue<${subelement.configElementClassName}> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public ${subelement.configElementClassName}NodeValue(final ${subelement.configElementClassName} value) {
				super(value);
			}
		}
	}
	<#elseif subelement.cardinality == "MANY">
	private static class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends AbstractConfigElementListNode<${subelement.configElementClassName}> {
		public static final String NODE_CLASS = "node.class.${subelement.elementClassId}${subelement.fieldNameSuffix}";

		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final String nodeId, final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(nodeId, ${element.elementName?uncap_first}.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
			setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<${subelement.configElementClassName}> createChildNode(final ${subelement.configElementClassName} value) {
			return new ${subelement.configElementClassName}Node(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return ${subelement.configElementClassName}NodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<${subelement.configElementClassName}> createNodeValue(final Object value) {
			return new ${subelement.configElementClassName}NodeValue((${subelement.configElementClassName}) value);
		}

		private class ${subelement.configElementClassName}NodeValue extends ConfigElementListNodeValue<${subelement.configElementClassName}> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public ${subelement.configElementClassName}NodeValue(final ${subelement.configElementClassName} value) {
				super(value);
			}
		}
	}
	</#if>
	<#elseif subelement.simpleElement>
	private static class ${subelement.elementName?cap_first}ElementNode extends ${subelement.configElementClassName}Node {
		private static final String NODE_LABEL = ${element.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}");

		/**
		 * @param nodeId
		 * @param ${element.elementName?uncap_first}
		 */
		public ${subelement.elementName?cap_first}ElementNode(final String nodeId, final ${element.configElementClassName} ${element.elementName?uncap_first}) {
			super(nodeId, ${element.elementName?uncap_first}.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
			setNodeLabel(${subelement.elementName?cap_first}ElementNode.NODE_LABEL);
		}
	}
	</#if>
	</#list>
}
</#if>