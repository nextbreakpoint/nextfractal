/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SmoothCurveToPathReplacementRuntime extends PathReplacementExtensionRuntime<SmoothCurveToPathReplacementConfig> {
	private Float x;
	private XListener xListener;
	private Float y;
	private YListener yListener;
	private Float x2;
	private X2Listener x2Listener;
	private Float y2;
	private Y2Listener y2Listener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setX(getConfig().getX());
		xListener = new XListener();
		getConfig().getXElement().addChangeListener(xListener);
		setY(getConfig().getY());
		yListener = new YListener();
		getConfig().getYElement().addChangeListener(yListener);
		setX2(getConfig().getX2());
		x2Listener = new X2Listener();
		getConfig().getX2Element().addChangeListener(x2Listener);
		setY2(getConfig().getY2());
		y2Listener = new Y2Listener();
		getConfig().getY2Element().addChangeListener(y2Listener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (xListener != null)) {
			getConfig().getXElement().removeChangeListener(xListener);
		}
		xListener = null;
		if ((getConfig() != null) && (yListener != null)) {
			getConfig().getYElement().removeChangeListener(yListener);
		}
		yListener = null;
		if ((getConfig() != null) && (x2Listener != null)) {
			getConfig().getX2Element().removeChangeListener(x2Listener);
		}
		x2Listener = null;
		if ((getConfig() != null) && (y2Listener != null)) {
			getConfig().getY2Element().removeChangeListener(y2Listener);
		}
		y2Listener = null;
		super.dispose();
	}
	
	/**
	 * @return the x.
	 */
	public Float getX() {
		return x;
	}

	private void setX(final Float x) {
		this.x = x;
	}
	
	private class XListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setX((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the y.
	 */
	public Float getY() {
		return y;
	}

	private void setY(final Float y) {
		this.y = y;
	}
	
	private class YListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setY((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the x2.
	 */
	public Float getX2() {
		return x2;
	}

	private void setX2(final Float x2) {
		this.x2 = x2;
	}
	
	private class X2Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setX2((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the y2.
	 */
	public Float getY2() {
		return y2;
	}

	private void setY2(final Float y2) {
		this.y2 = y2;
	}
	
	private class Y2Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setY2((Float) e.getParams()[0]);
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
		rule.getPath().curveTo(x, y, x2, y2);
	}
}
