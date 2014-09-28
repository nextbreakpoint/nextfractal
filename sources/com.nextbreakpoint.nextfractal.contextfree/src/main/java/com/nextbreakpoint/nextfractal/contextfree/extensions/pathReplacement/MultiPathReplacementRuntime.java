/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentRuntimeElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementRuntimeElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFPathAttribute;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFPathCommand;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class MultiPathReplacementRuntime<T extends MultiPathReplacementConfig> extends PathReplacementExtensionRuntime<T> {
	private Integer times;
	private TimesListener timesListener;
	private CFModification stateChange;
	private ListRuntimeElement<PathReplacementRuntimeElement> pathReplacementListElement;
	private PathReplacementListElementListener pathReplacementListElementListener;
	private ListRuntimeElement<PathAdjustmentRuntimeElement> pathAdjustmentListElement;
	private PathAdjustmentListElementListener pathAdjustmentListElementListener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setTimes(getConfig().getTimes());
		timesListener = new TimesListener();
		getConfig().getTimesElement().addChangeListener(timesListener);
		pathReplacementListElement = new ListRuntimeElement<PathReplacementRuntimeElement>();
		for (int i = 0; i < getConfig().getPathReplacementConfigElementCount(); i++) {
			pathReplacementListElement.appendElement(new PathReplacementRuntimeElement(getConfig().getPathReplacementConfigElement(i)));
		}
		pathReplacementListElementListener = new PathReplacementListElementListener();
		getConfig().getPathReplacementListElement().addChangeListener(pathReplacementListElementListener);
		pathAdjustmentListElement = new ListRuntimeElement<PathAdjustmentRuntimeElement>();
		for (int i = 0; i < getConfig().getPathAdjustmentConfigElementCount(); i++) {
			pathAdjustmentListElement.appendElement(new PathAdjustmentRuntimeElement(getConfig().getPathAdjustmentConfigElement(i)));
		}
		pathAdjustmentListElementListener = new PathAdjustmentListElementListener();
		getConfig().getPathAdjustmentListElement().addChangeListener(pathAdjustmentListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (timesListener != null)) {
			getConfig().getTimesElement().removeChangeListener(timesListener);
		}
		timesListener = null;
		if ((getConfig() != null) && (pathReplacementListElementListener != null)) {
			getConfig().getPathReplacementListElement().removeChangeListener(pathReplacementListElementListener);
		}
		pathReplacementListElementListener = null;
		if ((getConfig() != null) && (pathAdjustmentListElementListener != null)) {
			getConfig().getPathAdjustmentListElement().removeChangeListener(pathAdjustmentListElementListener);
		}
		pathAdjustmentListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the times.
	 */
	public Integer getTimes() {
		return times;
	}

	private void setTimes(final Integer times) {
		this.times = times;
	}
	
	private class TimesListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTimes((Integer) e.getParams()[0]);
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
	 * Returns a pathReplacement element.
	 * 
	 * @param index the pathReplacement index.
	 * @return the pathReplacement.
	 */
	public PathReplacementRuntimeElement getPathReplacementElement(final int index) {
		return pathReplacementListElement.getElement(index);
	}

	/**
	 * Returns a pathReplacement element index.
	 * 
	 * @param pathReplacementElement the pathReplacement element.
	 * @return the index.
	 */
	public int indexOfPathReplacementElement(final PathReplacementRuntimeElement pathReplacementElement) {
		return pathReplacementListElement.indexOfElement(pathReplacementElement);
	}

	/**
	 * Returns the number of pathReplacement elements.
	 * 
	 * @return the number of pathReplacement elements.
	 */
	public int getPathReplacementElementCount() {
		return pathReplacementListElement.getElementCount();
	}

	private void setPathReplacementElement(final int index, PathReplacementRuntimeElement element) {
		pathReplacementListElement.setElement(index, element);
	}

	private void appendPathReplacementElement(final PathReplacementRuntimeElement pathReplacementElement) {
		pathReplacementListElement.appendElement(pathReplacementElement);
	}

	private void insertPathReplacementElementAfter(final int index, final PathReplacementRuntimeElement pathReplacementElement) {
		pathReplacementListElement.insertElementAfter(index, pathReplacementElement);
	}

	private void insertPathReplacementElementBefore(final int index, final PathReplacementRuntimeElement pathReplacementElement) {
		pathReplacementListElement.insertElementBefore(index, pathReplacementElement);
	}

	private void removePathReplacementElement(final int index) {
		pathReplacementListElement.removeElement(index);
	}

	private void moveUpPathReplacementElement(final int index) {
		pathReplacementListElement.moveElementUp(index);
	}

	private void moveDownPathReplacementElement(final int index) {
		pathReplacementListElement.moveElementDown(index);
	}
	
	private class PathReplacementListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendPathReplacementElement(new PathReplacementRuntimeElement ((PathReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertPathReplacementElementAfter(((Integer) e.getParams()[1]).intValue(), new PathReplacementRuntimeElement ((PathReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertPathReplacementElementBefore(((Integer) e.getParams()[1]).intValue(), new PathReplacementRuntimeElement ((PathReplacementConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removePathReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpPathReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownPathReplacementElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setPathReplacementElement(((Integer) e.getParams()[1]).intValue(), new PathReplacementRuntimeElement ((PathReplacementConfigElement) e.getParams()[0]));
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
	 * Returns a pathAdjustment element.
	 * 
	 * @param index the pathAdjustment index.
	 * @return the pathAdjustment.
	 */
	public PathAdjustmentRuntimeElement getPathAdjustmentElement(final int index) {
		return pathAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a pathAdjustment element index.
	 * 
	 * @param pathAdjustmentElement the pathAdjustment element.
	 * @return the index.
	 */
	public int indexOfPathAdjustmentElement(final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		return pathAdjustmentListElement.indexOfElement(pathAdjustmentElement);
	}

	/**
	 * Returns the number of pathAdjustment elements.
	 * 
	 * @return the number of pathAdjustment elements.
	 */
	public int getPathAdjustmentElementCount() {
		return pathAdjustmentListElement.getElementCount();
	}

	private void setPathAdjustmentElement(final int index, PathAdjustmentRuntimeElement element) {
		pathAdjustmentListElement.setElement(index, element);
	}

	private void appendPathAdjustmentElement(final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.appendElement(pathAdjustmentElement);
	}

	private void insertPathAdjustmentElementAfter(final int index, final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementAfter(index, pathAdjustmentElement);
	}

	private void insertPathAdjustmentElementBefore(final int index, final PathAdjustmentRuntimeElement pathAdjustmentElement) {
		pathAdjustmentListElement.insertElementBefore(index, pathAdjustmentElement);
	}

	private void removePathAdjustmentElement(final int index) {
		pathAdjustmentListElement.removeElement(index);
	}

	private void moveUpPathAdjustmentElement(final int index) {
		pathAdjustmentListElement.moveElementUp(index);
	}

	private void moveDownPathAdjustmentElement(final int index) {
		pathAdjustmentListElement.moveElementDown(index);
	}
	
	private class PathAdjustmentListElementListener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendPathAdjustmentElement(new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertPathAdjustmentElementAfter(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertPathAdjustmentElementBefore(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removePathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpPathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownPathAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setPathAdjustmentElement(((Integer) e.getParams()[1]).intValue(), new PathAdjustmentRuntimeElement ((PathAdjustmentConfigElement) e.getParams()[0]));
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
		if (stateChange == null) {
			stateChange = new CFModification();
			for (int i = 0; i < pathAdjustmentListElement.getElementCount(); i++) {
				PathAdjustmentRuntimeElement pathAdjustmentRuntime = pathAdjustmentListElement.getElement(i);
				pathAdjustmentRuntime.apply(stateChange);
			}
		}
		rule.addAttribute(new CFPathAttribute(CFPathCommand.LOOP_START));
		for (int i = 0; i < pathReplacementListElement.getElementCount(); i++) {
			PathReplacementRuntimeElement pathReplacementRuntime = pathReplacementListElement.getElement(i);
			pathReplacementRuntime.process(rule);
		}
		rule.addAttribute(new CFPathAttribute(CFPathCommand.LOOP_END, stateChange, times));
	}
}
