/*
 * $Id:$
 *
 */
package ${resources.resourcesPackageName};

<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${resources.resourcesClassName} extends Resources {
	private static final ${resources.resourcesClassName} instance = new ${resources.resourcesClassName}();

	private ${resources.resourcesClassName}() {
		super(ResourceBundle.getBundle("resources"));
	}

	/**
	 * Returns the instance.
	 * 
	 * @return the instance.
	 */
	public static ${resources.resourcesClassName} getInstance() {
		return instance;
	}
}
