/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.enumerator;

import java.util.LinkedList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extensionPoints.enumerator.EnumeratorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.scripting.JSException;
import com.nextbreakpoint.nextfractal.core.runtime.scripting.ExtensionWrapper;

/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentEnumeratorRuntime extends EnumeratorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.enumerator.EnumeratorExtensionRuntime#listExtensions()
	 */
	@Override
	public List<String> listExtensions() throws JSException {
		List<Extension<ShapeAdjustmentExtensionRuntime<?>>> extensions = ContextFreeRegistry.getInstance().getShapeAdjustmentRegistry().getExtensionList();
		List<String> references = new LinkedList<String>();
		for (Extension<ShapeAdjustmentExtensionRuntime<?>> extension : extensions) {
			references.add(extension.getExtensionId());
		}
		return references;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.enumerator.EnumeratorExtensionRuntime#getExtension(java.lang.String)
	 */
	@Override
	public ExtensionWrapper getExtension(final String extensionId) throws JSException {
		try {
			return new ExtensionWrapper(ContextFreeRegistry.getInstance().getShapeAdjustmentExtension(extensionId));
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
	}
}
