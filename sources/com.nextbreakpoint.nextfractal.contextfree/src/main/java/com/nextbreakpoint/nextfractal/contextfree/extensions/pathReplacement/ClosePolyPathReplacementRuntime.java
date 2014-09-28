/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class ClosePolyPathReplacementRuntime extends PathReplacementExtensionRuntime<ClosePolyPathReplacementConfig> {
	private Boolean align;
	private AlignListener alignListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
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
	
	private class AlignListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
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

	public void process(CFRule rule) {
		rule.getPath().closePath(align);
	}
}
