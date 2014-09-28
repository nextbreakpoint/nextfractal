/*
 * $Id:$
 *
 */
package ${packageName};

<#if configPackageName?exists && configClassName?exists>
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import ${configPackageName}.${configClassName};

/**
 * @author ${author}
 */
public class ${elementName?cap_first}${subElementName?cap_first}ReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<${configClassName}>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ${elementName?cap_first}${subElementName?cap_first}ReferenceNodeValue(final ConfigurableExtensionReference<${configClassName}> value) {
		super(value);
	}
}
<#else>
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;

/**
 * @author ${author}
 */
public class ${elementName?cap_first}${subElementName?cap_first}ReferenceNodeValue extends ExtensionReferenceElementNodeValue<ExtensionReference> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ${elementName?cap_first}${subElementName?cap_first}ReferenceNodeValue(final ExtensionReference value) {
		super(value);
	}
}
</#if>