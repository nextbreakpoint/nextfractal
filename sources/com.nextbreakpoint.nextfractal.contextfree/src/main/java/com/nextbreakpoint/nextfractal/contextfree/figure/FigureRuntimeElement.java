/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.figure;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
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
 public class FigureRuntimeElement extends RuntimeElement {
	private FigureConfigElement figureElement;
	private FigureExtensionRuntime<?> extensionRuntime;
	private ExtensionListener extensionListener;

	/**
	 * Constructs a new FigureRuntimeElement.
	 * 
	 * @param registry
	 * @param FigureRuntimeElementElement
	 */
	public FigureRuntimeElement(final FigureConfigElement figureElement) {
		if (figureElement == null) {
			throw new IllegalArgumentException("figureElement is null");
		}
		this.figureElement = figureElement;
		createRuntime(figureElement.getExtensionReference());
		extensionListener = new ExtensionListener();
		figureElement.getExtensionElement().addChangeListener(extensionListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((figureElement != null) && (extensionListener != null)) {
			figureElement.getExtensionElement().removeChangeListener(extensionListener);
		}
		extensionListener = null;
		if (extensionRuntime != null) {
			extensionRuntime.dispose();
			extensionRuntime = null;
		}
		figureElement = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean figureChanged = false;
		figureChanged |= (extensionRuntime != null) && extensionRuntime.isChanged();
		return super.isChanged() || figureChanged;
	}

	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<FigureExtensionConfig> reference) {
		try {
			if (reference != null) {
				final FigureExtensionRuntime extensionRuntime = ContextFreeRegistry.getInstance().getFigureExtension(reference.getExtensionId()).createExtensionRuntime();
				final FigureExtensionConfig extensionConfig = reference.getExtensionConfig();
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
	 * @return the FigureExtensionRuntimeRuntime
	 */
	public FigureExtensionRuntime<?> getExtensionRuntime() {
		return extensionRuntime;
	}

	private void setExtensionRuntime(final FigureExtensionRuntime<?> extensionRuntime) {
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
					createRuntime((ConfigurableExtensionReference<FigureExtensionConfig>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public void process(CFBuilder context) {
		if (extensionRuntime != null) {
			extensionRuntime.process(context);
		}
	}
}
