/*
 * $Id:$
 *
 */
package ${creatorPackageName};

<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.elementName?cap_first}CreatorRuntime extends CreatorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.element.CreatorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		<#if element.simpleElement>
		if ((args != null) && (args.length > 0)) {
			throw new JSException("${element.elementName?cap_first}ConfigElement creator requires no arguments");
		}
		<#elseif element.complexElement>
		if ((args != null) && (args.length > 0)) {
			throw new JSException("${element.elementName?cap_first}ConfigElement creator requires no arguments");
		}
		</#if>
		return new ${element.elementName?cap_first}ConfigElement();
	}
}