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
public class ${extension.elementName?cap_first}CreatorRuntime extends CreatorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.extension.CreatorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		if ((args != null) && (args.length > 0)) {
			throw new JSException("${extension.elementName?cap_first}ConfigElement creator requires no arguments");
		}
		return new ${extension.elementName?cap_first}ConfigElement();
	}
}