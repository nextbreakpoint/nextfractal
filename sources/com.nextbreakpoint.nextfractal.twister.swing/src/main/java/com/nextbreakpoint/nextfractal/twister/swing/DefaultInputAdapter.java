/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.twister.swing;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.twister.swing.inputAdapter.DefaultInputAdapterRuntime;
import com.nextbreakpoint.nextfractal.twister.swing.inputAdapter.extension.InputAdapterExtensionRuntime;

import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.extension.FrameFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.extension.LayerFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.util.AdapterContext;

/**
 * @author Andrea Medeghini
 */
public class DefaultInputAdapter extends InputAdapter {
	private final RenderContext renderContext;
	private final TwisterConfig config;

	/**
	 * @param renderContext
	 * @param config
	 */
	public DefaultInputAdapter(final RenderContext renderContext, final TwisterConfig config) {
		this.renderContext = renderContext;
		this.config = config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		processEvent(e, new EventProcessor<KeyEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final KeyEvent e) {
				adapterRuntime.processKeyPressed(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(final KeyEvent e) {
		processEvent(e, new EventProcessor<KeyEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final KeyEvent e) {
				adapterRuntime.processKeyReleased(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(final KeyEvent e) {
		processEvent(e, new EventProcessor<KeyEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final KeyEvent e) {
				adapterRuntime.processKeyTyped(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseClicked(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseDragged(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseExited(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseEntered(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseMoved(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMousePressed(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		processEvent(e, new EventProcessor<MouseEvent>() {
			public void process(final InputAdapterExtensionRuntime adapterRuntime, final MouseEvent e) {
				adapterRuntime.processMouseReleased(e);
			}
		});
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.swing.InputAdapter#refresh()
	 */
	@Override
	public void refresh() {
		final FrameConfigElement frameElement = config.getFrameConfigElement();
		if (frameElement != null) {
			final int groupLayerCount = frameElement.getLayerConfigElementCount();
			for (int i = 0; i < groupLayerCount; i++) {
				final GroupLayerConfigElement groupLayerElement = frameElement.getLayerConfigElement(i);
				if (!groupLayerElement.isLocked() && groupLayerElement.isVisible()) {
					final int imageLayerCount = groupLayerElement.getLayerConfigElementCount();
					for (int j = 0; j < imageLayerCount; j++) {
						final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(j);
						if (!imageLayerElement.isLocked() && imageLayerElement.isVisible()) {
							final ImageConfigElement imageElement = imageLayerElement.getImageConfigElement();
							final ImageExtensionConfig imageConfig = getExtensionConfig(imageElement);
							if (imageConfig != null) {
								final AdapterContext context = imageConfig.getInputAdapterContext();
								if (context != null) {
									getInputAdapterRuntime(context, imageConfig).refresh();
								}
							}
							final int filterCount = imageLayerElement.getFilterConfigElementCount();
							for (int k = 0; k < filterCount; k++) {
								final LayerFilterConfigElement filterElement = imageLayerElement.getFilterConfigElement(k);
								if (!filterElement.isLocked() && filterElement.isEnabled()) {
									final LayerFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
									if (filterConfig != null) {
										final AdapterContext context = filterConfig.getInputAdapterContext();
										if (context != null) {
											getInputAdapterRuntime(context, filterConfig).refresh();
										}
									}
								}
							}
						}
					}
					final int filterCount = groupLayerElement.getFilterConfigElementCount();
					for (int k = 0; k < filterCount; k++) {
						final LayerFilterConfigElement filterElement = groupLayerElement.getFilterConfigElement(k);
						if (!filterElement.isLocked() && filterElement.isEnabled()) {
							final LayerFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
							if (filterConfig != null) {
								final AdapterContext context = filterConfig.getInputAdapterContext();
								if (context != null) {
									getInputAdapterRuntime(context, filterConfig).refresh();
								}
							}
						}
					}
				}
			}
			final int filterCount = frameElement.getFilterConfigElementCount();
			for (int k = 0; k < filterCount; k++) {
				final FrameFilterConfigElement filterElement = frameElement.getFilterConfigElement(k);
				if (!filterElement.isLocked() && filterElement.isEnabled()) {
					final FrameFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
					if (filterConfig != null) {
						final AdapterContext context = filterConfig.getInputAdapterContext();
						if (context != null) {
							getInputAdapterRuntime(context, filterConfig).refresh();
						}
					}
				}
			}
		}
		final EffectConfigElement effectElement = config.getEffectConfigElement();
		if (effectElement != null) {
			if (!effectElement.isLocked() && effectElement.isEnabled()) {
				final EffectExtensionConfig effectConfig = getExtensionConfig(effectElement);
				if (effectConfig != null) {
					final AdapterContext context = effectConfig.getInputAdapterContext();
					if (context != null) {
						getInputAdapterRuntime(context, effectConfig).refresh();
					}
				}
			}
		}
	}

	private ImageExtensionConfig getExtensionConfig(final ImageConfigElement element) {
		if ((element.getReference() != null) && (element.getReference().getExtensionConfig() != null)) {
			return element.getReference().getExtensionConfig();
		}
		return null;
	}

	private LayerFilterExtensionConfig getExtensionConfig(final LayerFilterConfigElement element) {
		if ((element.getReference() != null) && (element.getReference().getExtensionConfig() != null)) {
			return element.getReference().getExtensionConfig();
		}
		return null;
	}

	private FrameFilterExtensionConfig getExtensionConfig(final FrameFilterConfigElement element) {
		if ((element.getReference() != null) && (element.getReference().getExtensionConfig() != null)) {
			return element.getReference().getExtensionConfig();
		}
		return null;
	}

	private EffectExtensionConfig getExtensionConfig(final EffectConfigElement element) {
		if ((element.getReference() != null) && (element.getReference().getExtensionConfig() != null)) {
			return element.getReference().getExtensionConfig();
		}
		return null;
	}
	
	private InputAdapterExtensionRuntime getInputAdapterRuntime(final AdapterContext context, final ExtensionConfig config) {
		InputAdapterExtensionRuntime runtime = (InputAdapterExtensionRuntime) context.getAttribute("inputAdapterRuntime");
		try {
			if (runtime == null) {
				final Extension<InputAdapterExtensionRuntime> extension = TwisterSwingRegistry.getInstance().getInputAdapterExtension(config.getExtensionId());
				runtime = extension.createExtensionRuntime();
				runtime.init(renderContext, config);
				context.setAttribute("inputAdapterRuntime", runtime);
			}
		}
		catch (final ExtensionException x) {
			runtime = new DefaultInputAdapterRuntime();
			context.setAttribute("inputAdapterRuntime", runtime);
		}
		return runtime;
	}

	private <E> void processEvent(final E e, final EventProcessor<E> p) {
		final FrameConfigElement frameElement = config.getFrameConfigElement();
		if (frameElement != null) {
			final int groupLayerCount = frameElement.getLayerConfigElementCount();
			for (int i = 0; i < groupLayerCount; i++) {
				final GroupLayerConfigElement groupLayerElement = frameElement.getLayerConfigElement(i);
				if (!groupLayerElement.isLocked() && groupLayerElement.isVisible()) {
					final int imageLayerCount = groupLayerElement.getLayerConfigElementCount();
					for (int j = 0; j < imageLayerCount; j++) {
						final ImageLayerConfigElement imageLayerElement = groupLayerElement.getLayerConfigElement(j);
						if (!imageLayerElement.isLocked() && imageLayerElement.isVisible()) {
							final ImageConfigElement imageElement = imageLayerElement.getImageConfigElement();
							final ImageExtensionConfig imageConfig = getExtensionConfig(imageElement);
							if (imageConfig != null) {
								final AdapterContext context = imageConfig.getInputAdapterContext();
								if (context != null) {
									p.process(getInputAdapterRuntime(context, imageConfig), e);
								}
							}
							final int filterCount = imageLayerElement.getFilterConfigElementCount();
							for (int k = 0; k < filterCount; k++) {
								final LayerFilterConfigElement filterElement = imageLayerElement.getFilterConfigElement(k);
								if (!filterElement.isLocked() && filterElement.isEnabled()) {
									final LayerFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
									if (filterConfig != null) {
										final AdapterContext context = filterConfig.getInputAdapterContext();
										if (context != null) {
											p.process(getInputAdapterRuntime(context, filterConfig), e);
										}
									}
								}
							}
						}
					}
					final int filterCount = groupLayerElement.getFilterConfigElementCount();
					for (int k = 0; k < filterCount; k++) {
						final LayerFilterConfigElement filterElement = groupLayerElement.getFilterConfigElement(k);
						if (!filterElement.isLocked() && filterElement.isEnabled()) {
							final LayerFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
							if (filterConfig != null) {
								final AdapterContext context = filterConfig.getInputAdapterContext();
								if (context != null) {
									p.process(getInputAdapterRuntime(context, filterConfig), e);
								}
							}
						}
					}
				}
			}
			final int filterCount = frameElement.getFilterConfigElementCount();
			for (int k = 0; k < filterCount; k++) {
				final FrameFilterConfigElement filterElement = frameElement.getFilterConfigElement(k);
				if (!filterElement.isLocked() && filterElement.isEnabled()) {
					final FrameFilterExtensionConfig filterConfig = getExtensionConfig(filterElement);
					if (filterConfig != null) {
						final AdapterContext context = filterConfig.getInputAdapterContext();
						if (context != null) {
							p.process(getInputAdapterRuntime(context, filterConfig), e);
						}
					}
				}
			}
		}
		final EffectConfigElement effectElement = config.getEffectConfigElement();
		if (effectElement != null) {
			if (!effectElement.isLocked() && effectElement.isEnabled()) {
				final EffectExtensionConfig effectConfig = getExtensionConfig(effectElement);
				if (effectConfig != null) {
					final AdapterContext context = effectConfig.getInputAdapterContext();
					if (context != null) {
						p.process(getInputAdapterRuntime(context, effectConfig), e);
					}
				}
			}
		}
	}

	private interface EventProcessor<E> {
		public void process(InputAdapterExtensionRuntime adapterRuntime, E e);
	}
}
