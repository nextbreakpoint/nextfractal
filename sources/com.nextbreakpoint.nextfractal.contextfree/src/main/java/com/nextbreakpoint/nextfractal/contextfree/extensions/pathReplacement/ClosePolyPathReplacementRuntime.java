/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class ClosePolyPathReplacementRuntime extends PathReplacementExtensionRuntime<ClosePolyPathReplacementConfig> {
	private Boolean align;
	private AlignListener alignListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setAlign(getConfig().isAlign());
		alignListener = new AlignListener();
		getConfig().getAlignElement().addChangeListener(alignListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (alignListener != null)) {
			getConfig().getAlignElement().removeChangeListener(alignListener);
		}
		alignListener = null;
		super.dispose();
	}
	
	/**
	 * @return the align.
	 */
	public Boolean isAlign() {
		return align;
	}

	private void setAlign(final Boolean align) {
		this.align = align;
	}
	
	private class AlignListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setAlign((Boolean) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	@Override
	public void process(CFRule rule) {
		rule.getPath().closePath(align);
	}
}
