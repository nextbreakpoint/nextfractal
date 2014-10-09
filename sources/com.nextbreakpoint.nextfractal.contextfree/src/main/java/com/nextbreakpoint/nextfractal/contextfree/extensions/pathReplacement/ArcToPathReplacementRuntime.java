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
public class ArcToPathReplacementRuntime extends PathReplacementExtensionRuntime<ArcToPathReplacementConfig> {
	private Float x;
	private XListener xListener;
	private Float y;
	private YListener yListener;
	private Float rx;
	private RxListener rxListener;
	private Float ry;
	private RyListener ryListener;
	private Float r;
	private RListener rListener;
	private Boolean sweep;
	private SweepListener sweepListener;
	private Boolean large;
	private LargeListener largeListener;

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
		setRx(getConfig().getRx());
		rxListener = new RxListener();
		getConfig().getRxElement().addChangeListener(rxListener);
		setRy(getConfig().getRy());
		ryListener = new RyListener();
		getConfig().getRyElement().addChangeListener(ryListener);
		setR(getConfig().getR());
		rListener = new RListener();
		getConfig().getRElement().addChangeListener(rListener);
		setSweep(getConfig().isSweep());
		sweepListener = new SweepListener();
		getConfig().getSweepElement().addChangeListener(sweepListener);
		setLarge(getConfig().isLarge());
		largeListener = new LargeListener();
		getConfig().getLargeElement().addChangeListener(largeListener);
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
		if ((getConfig() != null) && (rxListener != null)) {
			getConfig().getRxElement().removeChangeListener(rxListener);
		}
		rxListener = null;
		if ((getConfig() != null) && (ryListener != null)) {
			getConfig().getRyElement().removeChangeListener(ryListener);
		}
		ryListener = null;
		if ((getConfig() != null) && (rListener != null)) {
			getConfig().getRElement().removeChangeListener(rListener);
		}
		rListener = null;
		if ((getConfig() != null) && (sweepListener != null)) {
			getConfig().getSweepElement().removeChangeListener(sweepListener);
		}
		sweepListener = null;
		if ((getConfig() != null) && (largeListener != null)) {
			getConfig().getLargeElement().removeChangeListener(largeListener);
		}
		largeListener = null;
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
	 * @return the rx.
	 */
	public Float getRx() {
		return rx;
	}

	private void setRx(final Float rx) {
		this.rx = rx;
	}
	
	private class RxListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setRx((Float) e.getParams()[0]);
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
	 * @return the ry.
	 */
	public Float getRy() {
		return ry;
	}

	private void setRy(final Float ry) {
		this.ry = ry;
	}
	
	private class RyListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setRy((Float) e.getParams()[0]);
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
	 * @return the r.
	 */
	public Float getR() {
		return r;
	}

	private void setR(final Float r) {
		this.r = r;
	}
	
	private class RListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setR((Float) e.getParams()[0]);
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
	 * @return the sweep.
	 */
	public Boolean isSweep() {
		return sweep;
	}

	private void setSweep(final Boolean sweep) {
		this.sweep = sweep;
	}
	
	private class SweepListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setSweep((Boolean) e.getParams()[0]);
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
	 * @return the large.
	 */
	public Boolean isLarge() {
		return large;
	}

	private void setLarge(final Boolean large) {
		this.large = large;
	}
	
	private class LargeListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setLarge((Boolean) e.getParams()[0]);
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
		rule.getPath().arcTo(x, y, rx, ry, r, large, sweep);
	}
}

