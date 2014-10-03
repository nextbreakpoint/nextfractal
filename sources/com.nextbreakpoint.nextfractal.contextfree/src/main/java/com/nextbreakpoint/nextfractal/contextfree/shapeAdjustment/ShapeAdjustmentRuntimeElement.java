/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.extension.ShapeAdjustmentExtensionRuntime;
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
 public class ShapeAdjustmentRuntimeElement extends RuntimeElement {
	private ShapeAdjustmentConfigElement shapeAdjustmentElement;
	private ShapeAdjustmentExtensionRuntime<?> extensionRuntime;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new ShapeAdjustmentRuntimeElement.
	 * 
	 * @param registry
	 * @param ShapeAdjustmentRuntimeElementElement
	 */
	public ShapeAdjustmentRuntimeElement(final ShapeAdjustmentConfigElement shapeAdjustmentElement) {
		if (shapeAdjustmentElement == null) {
			throw new IllegalArgumentException("shapeAdjustmentElement is null");
		}
		this.shapeAdjustmentElement = shapeAdjustmentElement;
		createRuntime(shapeAdjustmentElement.getExtensionReference());
		extensionListener = new ExtensionListener();
		shapeAdjustmentElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((shapeAdjustmentElement != null) && (extensionListener != null)) {
			shapeAdjustmentElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (extensionRuntime != null) {
			extensionRuntime.dispose();
			extensionRuntime = null;
		}
		shapeAdjustmentElement = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean shapeAdjustmentChanged = false;
		shapeAdjustmentChanged |= (extensionRuntime != null) && extensionRuntime.isChanged();
		return super.isChanged() || shapeAdjustmentChanged;
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference) {
		try {
			if (reference != null) {
				final ShapeAdjustmentExtensionRuntime extensionRuntime = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension(reference.getExtensionId()).createExtensionRuntime();
				final ShapeAdjustmentExtensionConfig extensionConfig = reference.getExtensionConfig();
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
	 * @return the ShapeAdjustmentExtensionRuntimeRuntime
	 */
	public ShapeAdjustmentExtensionRuntime<?> getExtensionRuntime() {
		return extensionRuntime;
	}

	private void setExtensionRuntime(final ShapeAdjustmentExtensionRuntime<?> extensionRuntime) {
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
					createRuntime((ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	
	public void apply(CFModification mod) {
		if (extensionRuntime != null) {
			extensionRuntime.apply(mod);
		}
	}

	public boolean isSizeChange() {
		if (extensionRuntime != null) {
			return extensionRuntime.isSizeChange();
		}
		return false;
	}

	public float getSize() {
		if (extensionRuntime != null) {
			return extensionRuntime.getSize();
		}
		return 1;
	}
}
