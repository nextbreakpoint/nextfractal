/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;

/**
 * @author Andrea Medeghini
 */
 public class PathReplacementRuntimeElement extends RuntimeElement {
	private PathReplacementConfigElement pathReplacementElement;
	private PathReplacementExtensionRuntime<?> extensionRuntime;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new PathReplacementRuntimeElement.
	 * 
	 * @param registry
	 * @param PathReplacementRuntimeElementElement
	 */
	public PathReplacementRuntimeElement(final PathReplacementConfigElement pathReplacementElement) {
		if (pathReplacementElement == null) {
			throw new IllegalArgumentException("pathReplacementElement is null");
		}
		this.pathReplacementElement = pathReplacementElement;
		createRuntime(pathReplacementElement.getExtensionReference());
		extensionListener = new ExtensionListener();
		pathReplacementElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((pathReplacementElement != null) && (extensionListener != null)) {
			pathReplacementElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (extensionRuntime != null) {
			extensionRuntime.dispose();
			extensionRuntime = null;
		}
		pathReplacementElement = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean pathReplacementChanged = false;
		pathReplacementChanged |= (extensionRuntime != null) && extensionRuntime.isChanged();
		return super.isChanged() || pathReplacementChanged;
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<PathReplacementExtensionConfig> reference) {
		try {
			if (reference != null) {
				final PathReplacementExtensionRuntime extensionRuntime = ContextFreeRegistry.getInstance().getPathReplacementExtension(reference.getExtensionId()).createExtensionRuntime();
				final PathReplacementExtensionConfig extensionConfig = reference.getExtensionConfig();
				extensionRuntime.setConfig(extensionConfig);
				setExtensionRuntime(extensionRuntime);
			}
			else {
				setExtensionRuntime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the PathReplacementExtensionRuntimeRuntime
	 */
	public PathReplacementExtensionRuntime<?> getExtensionRuntime() {
		return extensionRuntime;
	}

	private void setExtensionRuntime(final PathReplacementExtensionRuntime<?> extensionRuntime) {
		if (this.extensionRuntime != null) {
			this.extensionRuntime.dispose();
		}
		this.extensionRuntime = extensionRuntime;
	}
	
	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<PathReplacementExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public void process(CFRule rule) {
		if (extensionRuntime != null) {
			extensionRuntime.process(rule);
		}
	}
}
