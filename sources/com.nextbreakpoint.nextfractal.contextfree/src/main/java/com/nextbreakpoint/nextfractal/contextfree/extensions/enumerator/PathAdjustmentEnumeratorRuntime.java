/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.enumerator;

import java.util.LinkedList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.extension.PathAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.scripting.JSException;
import com.nextbreakpoint.nextfractal.core.scripting.JSExtension;

/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentEnumeratorRuntime extends EnumeratorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime#listExtensions()
	 */
	@Override
	public List<String> listExtensions() throws JSException {
		List<Extension<PathAdjustmentExtensionRuntime<?>>> extensions = ContextFreeRegistry.getInstance().getPathAdjustmentRegistry().getExtensionList();
		List<String> references = new LinkedList<String>();
		for (Extension<PathAdjustmentExtensionRuntime<?>> extension : extensions) {
			references.add(extension.getExtensionId());
		}
		return references;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime#getExtension(java.lang.String)
	 */
	@Override
	public JSExtension getExtension(final String extensionId) throws JSException {
		try {
			return new JSExtension(ContextFreeRegistry.getInstance().getPathAdjustmentExtension(extensionId));
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
	}
}
