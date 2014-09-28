/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement;


import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionRuntime;
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
 public class ShapeReplacementRuntimeElement extends RuntimeElement {
	private ShapeReplacementConfigElement shapeReplacementElement;
	private ShapeReplacementExtensionRuntime<?> extensionRuntime;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new ShapeReplacementRuntimeElement.
	 * 
	 * @param registry
	 * @param ShapeReplacementRuntimeElementElement
	 */
	public ShapeReplacementRuntimeElement(final ShapeReplacementConfigElement shapeReplacementElement) {
		if (shapeReplacementElement == null) {
			throw new IllegalArgumentException("shapeReplacementElement is null");
		}
		this.shapeReplacementElement = shapeReplacementElement;
		createRuntime(shapeReplacementElement.getExtensionReference());
		extensionListener = new ExtensionListener();
		shapeReplacementElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((shapeReplacementElement != null) && (extensionListener != null)) {
			shapeReplacementElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (extensionRuntime != null) {
			extensionRuntime.dispose();
			extensionRuntime = null;
		}
		shapeReplacementElement = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean shapeReplacementChanged = false;
		shapeReplacementChanged |= (extensionRuntime != null) && extensionRuntime.isChanged();
		return super.isChanged() || shapeReplacementChanged;
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference) {
		try {
			if (reference != null) {
				final ShapeReplacementExtensionRuntime extensionRuntime = ContextFreeRegistry.getInstance().getShapeReplacementExtension(reference.getExtensionId()).createExtensionRuntime();
				final ShapeReplacementExtensionConfig extensionConfig = reference.getExtensionConfig();
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
	 * @return the ShapeReplacementExtensionRuntimeRuntime
	 */
	public ShapeReplacementExtensionRuntime<?> getExtensionRuntime() {
		return extensionRuntime;
	}

	private void setExtensionRuntime(final ShapeReplacementExtensionRuntime<?> extensionRuntime) {
		if (this.extensionRuntime != null) {
			this.extensionRuntime.dispose();
		}
		this.extensionRuntime = extensionRuntime;
	}
	
	private class ExtensionListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<ShapeReplacementExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public void process(CFBuilder context, CFRule rule) {
		if (extensionRuntime != null) {
			extensionRuntime.process(context, rule);
		}
	}
}
