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

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.util.concurrent.Semaphore;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Rectangle;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;

import com.nextbreakpoint.nextfractal.twister.ControllerListener;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.OverlayTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class TwisterPreviewCanvas extends Canvas implements RenderContext {
	// private static final Logger logger = Logger.getLogger(PreviewCanvas.class);
	private static final int MIN_HEIGHT = 20;
	private static final int MIN_WITH = 20;
	private static final long serialVersionUID = 1L;
	private static final long frameRate = 25;
	private RefreshTask refreshCanvas;
	private TwisterRenderer renderer;
	private TwisterRuntime runtime;
	private OverlayTwisterRenderer overlayRenderer;
	private TwisterRuntime overlayRuntime;
	private TwisterConfig config;
	private TwisterClip clip;
	private Rectangle area;
	private IntegerVector2D size;
	private Surface surface;
	private RenderListener listener;
	boolean paintBorder = false;
	boolean isTarget = true;
	private VolatileImage volatileImage;
	private final DropTarget target;
	private final DragSource source;
	private boolean dragEnabled = true;
	private boolean dropEnabled;
	private TwisterClipController clipController;
	private final Semaphore semaphore = new Semaphore(1, true);
	private volatile Thread resizeThread;

	/**
	 * 
	 */
	public TwisterPreviewCanvas() {
		setIgnoreRepaint(true);
		setMinimumSize(new Dimension(40, 40));
		setMaximumSize(new Dimension(200, 200));
		setPreferredSize(new Dimension(120, 120));
		source = new DragSource();
		target = new DropTarget();
		final CanvasListener listener = new CanvasListener();
		addComponentListener(listener);
		addMouseListener(listener);
		setFocusable(true);
		setBackground(Color.DARK_GRAY);
		if (source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, listener) != null) {
			setDropTarget(target);
		}
		try {
			target.addDropTargetListener(listener);
		}
		catch (final TooManyListenersException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the listener
	 */
	public RenderListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(final RenderListener listener) {
		this.listener = listener;
	}

	/**
	 * @return the area
	 */
	public Rectangle getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final Rectangle area) {
		this.area = area;
		refresh();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
	 */
	public void acquire() throws InterruptedException {
		semaphore.acquire();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
	 */
	public void release() {
		semaphore.release();
	}

	/**
	 * 
	 */
	public void refresh() {
		if (refreshCanvas != null) {
			refreshCanvas.refresh();
		}
	}

	/**
	 * @return the dragEnabled
	 */
	public boolean isDragEnabled() {
		return dragEnabled;
	}

	/**
	 * @param dragEnabled the dragEnabled to set
	 */
	public void setDragEnabled(final boolean dragEnabled) {
		this.dragEnabled = dragEnabled;
	}

	/**
	 * @return the dropEnabled
	 */
	public boolean isDropEnabled() {
		return dropEnabled;
	}

	/**
	 * @param dropEnabled the dropEnabled to set
	 */
	public void setDropEnabled(final boolean dropEnabled) {
		this.dropEnabled = dropEnabled;
	}

	/**
	 * @see java.awt.Canvas#update(java.awt.Graphics)
	 */
	@Override
	public void update(final Graphics g) {
		paint(g);
	}

	/**
	 * @return
	 */
	public boolean isStarted() {
		return (refreshCanvas != null) && refreshCanvas.isStarted();
	}

	/**
	 * 
	 */
	public void start(final TwisterClip clip) {
		clipController = new TwisterClipController(clip);
		clipController.setRenderContext(TwisterPreviewCanvas.this);
		clipController.addControllerListener(new ControllerListener() {
			public void actionRedone(final NodeAction action) {
			}

			public void actionUndone(final NodeAction action) {
			}

			public void configChanged() {
			}
		});
		clipController.init();
		final TwisterConfig config = clipController.getConfig();
		if (clipController.getDuration() > 0) {
			this.clip = clip;
			setListener(new RenderListener() {
				public void frameRendered() {
					if (clipController != null) {
						if (!clipController.redoAction(1000 / getFrameRate(), true)) {
							// clipController.init();
							suspend();
						}
					}
				}
			});
		}
		else {
			this.clip = null;
			clipController = null;
			setListener(null);
		}
		final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
		init(config, size);
		if (refreshCanvas == null) {
			refreshCanvas = new RefreshTask();
			refreshCanvas.start();
		}
	}

	/**
	 * 
	 */
	public void start(final TwisterConfig config) {
		clip = null;
		clipController = null;
		setListener(null);
		final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
		init(config, size);
		if (refreshCanvas == null) {
			refreshCanvas = new RefreshTask();
			refreshCanvas.start();
		}
	}

	/**
	 * 
	 */
	public void start() {
		if (clip != null) {
			start(clip);
		}
		else {
			if (config != null) {
				start(config);
			}
		}
	}

	/**
	 * 
	 */
	public void stop() {
		if (refreshCanvas != null) {
			refreshCanvas.stop();
			refreshCanvas = null;
		}
		dispose();
		// refreshTask = null;
		clipController = null;
		setListener(null);
	}

	/**
	 * @return
	 */
	public boolean isSuspended() {
		return (clipController != null) && (listener == null);
	}

	/**
	 * @throws ExtensionException
	 */
	public void resume() {
		setListener(new RenderListener() {
			public void frameRendered() {
				if (clipController != null) {
					if (!clipController.redoAction(1000 / getFrameRate(), true)) {
						// clipController.init();
						suspend();
					}
				}
			}
		});
		if (!clipController.redoAction(1000 / getFrameRate(), true)) {
			clipController.init();
		}
	}

	/**
	 * 
	 */
	public void suspend() {
		setListener(null);
	}

	/**
	 * @return
	 */
	public IntegerVector2D getImageSize() {
		return size;
	}

	/**
	 * @return
	 */
	public long getFrameRate() {
		return TwisterPreviewCanvas.frameRate;
	}

	private void init(final TwisterConfig config, final IntegerVector2D size) {
		final HashMap<Object, Object> hints = new HashMap<Object, Object>();
		hints.put(TwisterRenderingHints.KEY_QUALITY, TwisterRenderingHints.QUALITY_REALTIME);
		if (Boolean.getBoolean("nextfractal.lowMemory")) {
			hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		}
		if (runtime != null) {
			runtime.dispose();
		}
		if (overlayRuntime != null) {
			overlayRuntime.dispose();
		}
		runtime = new TwisterRuntime(config);
		renderer = new DefaultTwisterRenderer(runtime);
		renderer.setRenderingHints(hints);
		renderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
		final HashMap<Object, Object> overlayHints = new HashMap<Object, Object>();
		overlayHints.put(TwisterRenderingHints.KEY_QUALITY, TwisterRenderingHints.QUALITY_REALTIME);
		if (Boolean.getBoolean("nextfractal.lowMemory")) {
			overlayHints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		}
		overlayHints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_OVERLAY);
		overlayRuntime = new TwisterRuntime(config);
		overlayRenderer = new OverlayTwisterRenderer(overlayRuntime);
		overlayRenderer.setRenderingHints(overlayHints);
		overlayRenderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
		this.config = config;
		this.size = size;
	}

	private void dispose() {
		if (renderer != null) {
			renderer.dispose();
		}
		if (overlayRenderer != null) {
			overlayRenderer.dispose();
		}
		renderer = null;
		overlayRenderer = null;
		if (runtime != null) {
			runtime.dispose();
		}
		if (overlayRuntime != null) {
			overlayRuntime.dispose();
		}
		runtime = null;
		overlayRuntime = null;
	}

	private void resize(final IntegerVector2D size) throws ExtensionException {
		try {
			acquire();
			stopRenderers();
			if (renderer != null) {
				renderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
			}
			if (overlayRenderer != null) {
				overlayRenderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
			}
			startRenderers();
			this.size = size;
			if (surface != null) {
				surface.dispose();
				surface = null;
			}
			if (volatileImage != null) {
				volatileImage.flush();
				volatileImage = null;
			}
			release();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		do {
			if ((volatileImage == null) || (volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)) {
				volatileImage = createVolatileImage(getWidth(), getHeight());
				refresh();
			}
			else if (volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_RESTORED) {
				refresh();
			}
			g.drawImage(volatileImage, 0, 0, this);
		}
		while (volatileImage.contentsLost());
	}

	private void draw() {
		if (volatileImage != null) {
			Graphics2D g = volatileImage.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			paintImage(g);
			g.dispose();
		}
	}

	private void clear() {
		if (volatileImage != null) {
			Graphics2D g = volatileImage.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			clearImage(g);
			g.dispose();
		}
	}

	private void clearImage(final Graphics g) {
		g.setColor(getBackground());
		((Graphics2D) g).setComposite(AlphaComposite.SrcOver);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintImage(final Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if (renderer != null) {
			renderer.prepareImage(true);
		}
		if (overlayRenderer != null) {
			overlayRenderer.prepareImage(true);
		}
		if (renderer != null) {
			final EffectExtensionRuntime<?> effectRuntime = renderer.getRuntime().getEffectElement().getEffectRuntime();
			if ((effectRuntime != null) && renderer.getRuntime().getEffectElement().isEnabled()) {
				if (surface == null) {
					surface = new Surface(size.getX(), size.getY());
				}
				if (renderer != null) {
					renderer.drawImage(surface.getGraphics2D());
				}
				effectRuntime.setSize(size);
				effectRuntime.prepareEffect();
				effectRuntime.renderImage(surface);
				g.setColor(new Color(config.getBackground().getARGB(), true));
				g.setComposite(AlphaComposite.SrcOver);
				g.fillRect(0, 0, surface.getWidth(), surface.getHeight());
				g.drawImage(surface.getImage(), 0, 0, null);
			}
			else {
				if (surface != null) {
					surface.dispose();
					surface = null;
				}
				if (renderer != null) {
					renderer.drawImage(g);
				}
			}
			if (overlayRenderer != null) {
				overlayRenderer.drawImage(g);
			}
		}
		g.setComposite(AlphaComposite.SrcOver);
		if (area != null) {
			final int x = (int) Math.rint(area.getX() * renderer.getTile().getImageSize().getX() / area.getW());
			final int y = (int) Math.rint(area.getY() * renderer.getTile().getImageSize().getX() / area.getW());
			final int w = renderer.getTile().getImageSize().getX();
			final int h = (int) Math.rint(area.getH() * renderer.getTile().getImageSize().getX() / area.getW());
			final int d = (renderer.getTile().getImageSize().getY() - h) / 2;
			g.setColor(Color.YELLOW);
			g.drawRect(x, y + d, w - 1, h - 1);
		}
		if (hasFocus()) {
			g.setClip(null);
			g.setColor(Color.BLUE);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		if (paintBorder) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	private class RefreshTask implements Runnable {
		private final Object lock = new Object();
		private Thread refreshThread;
		private boolean running;
		private boolean refresh;

		/**
		 * 
		 */
		public void refresh() {
			synchronized (lock) {
				refresh = true;
				lock.notify();
			}
		}

		/**
		 * 
		 */
		public void start() {
			if (refreshThread == null) {
				running = true;
				refreshThread = new Thread(this);
				refreshThread.setName("PreviewCanvas RefreshTask");
				refreshThread.setPriority(Thread.NORM_PRIORITY);
				refreshThread.setDaemon(true);
				refreshThread.start();
			}
		}

		/**
		 * 
		 */
		public void stop() {
			if (refreshThread != null) {
				running = false;
				refreshThread.interrupt();
				try {
					refreshThread.join();
				}
				catch (final InterruptedException e) {
				}
				refreshThread = null;
			}
		}

		/**
		 * @return
		 */
		public boolean isStarted() {
			return refreshThread != null;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				long startTime = 0;
				long totalTime = 0;
				final long pauseTime = 1000 / TwisterPreviewCanvas.frameRate;
				long sleepTime = pauseTime;
				long idleTime = 0;
				while (running) {
					startTime = System.currentTimeMillis();
					acquire();
					draw();
					refresh |= needsRefresh();
					if (listener != null) {
						listener.frameRendered();
					}
					release();
					repaint();
					synchronized (lock) {
						if (refresh || (clipController != null)) {
							idleTime = System.currentTimeMillis();
						}
						if (System.currentTimeMillis() - idleTime > 500) {
							if (!refresh) {
								lock.wait();
							}
							idleTime = System.currentTimeMillis();
						}
						refresh = false;
					}
					totalTime = System.currentTimeMillis() - startTime;
					sleepTime = pauseTime - totalTime;
					if (!running) {
						break;
					}
					if (sleepTime > 10) {
						Thread.sleep(sleepTime);
					}
					else {
						Thread.sleep(10);
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			clear();
		}

		private boolean needsRefresh() {
			boolean needsRefresh = false;
			if (runtime != null) {
				needsRefresh |= runtime.isChanged();
			}
			if (overlayRuntime != null) {
				needsRefresh |= overlayRuntime.isChanged();
			}
			return needsRefresh;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
	 */
	public void startRenderers() {
		if (renderer != null) {
			renderer.startRenderer();
		}
		if (overlayRenderer != null) {
			overlayRenderer.startRenderer();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
	 */
	public void stopRenderers() {
		if (renderer != null) {
			renderer.abortRenderer();
		}
		if (overlayRenderer != null) {
			overlayRenderer.abortRenderer();
		}
		if (renderer != null) {
			renderer.joinRenderer();
		}
		if (overlayRenderer != null) {
			overlayRenderer.joinRenderer();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	public void addRenderContextListener(RenderContextListener listener) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	public void removeRenderContextListener(RenderContextListener listener) {
	}

	private void resize() {
		try {
			final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
			TwisterPreviewCanvas.this.resize(size);
			refresh();
		}
		catch (final ExtensionException x) {
			x.printStackTrace();
		}
		catch (final Exception x) {
			x.printStackTrace();
		}
		catch (final Error x) {
			x.printStackTrace();
		}
	}

	private class CanvasListener implements ComponentListener, MouseListener, FocusListener, DropTargetListener, DragGestureListener, DragSourceListener {
		/**
		 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
		 */
		public void componentHidden(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
		 */
		public void componentMoved(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
		 */
		public void componentResized(final ComponentEvent e) {
			if ((e.getComponent().getWidth() > 0) && (e.getComponent().getHeight() > 0)) {
				if (resizeThread == null) {
					resizeThread = new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(250);
							}
							catch (InterruptedException e) {
							}
							GUIUtil.executeTask(new Runnable() {
									public void run() {
									resize();
									}
																}, false);
							resizeThread = null;
						}
					}, "Resize Thread");
					resizeThread.setDaemon(true);
					resizeThread.start();
				}
			}
		}

		/**
		 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
		 */
		public void componentShown(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(final MouseEvent e) {
			try {
				acquire();
				if (isSuspended()) {
					resume();
				}
				else {
					suspend();
				}
				release();
				refresh();
			}
			catch (InterruptedException x) {
				Thread.currentThread().interrupt();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		public void focusGained(final FocusEvent e) {
			refresh();
		}

		/**
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost(final FocusEvent e) {
			refresh();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragEnter(final DropTargetDragEvent e) {
			if (!isDropEnabled()) {
				e.rejectDrag();
			}
			else if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferableTwisterConfig.TWISTER_CONFIG_FLAVOR)) {
						accept = true;
						break;
					}
					else if (element.equals(TransferableTwisterClip.TWISTER_CLIP_FLAVOR)) {
						accept = true;
						break;
					}
				}
				if (accept) {
					e.acceptDrag(DnDConstants.ACTION_MOVE);
					paintBorder = true;
					refresh();
				}
				else {
					e.rejectDrag();
				}
			}
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dragOver(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
		 */
		public void dragExit(final DropTargetEvent e) {
			if (isTarget) {
				paintBorder = false;
				refresh();
			}
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
		 */
		public void dropActionChanged(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		public void drop(final DropTargetDropEvent e) {
			if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				for (final DataFlavor element : flavors) {
					if (element.equals(TransferableTwisterConfig.TWISTER_CONFIG_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							final TwisterConfig config = (TwisterConfig) e.getTransferable().getTransferData(TransferableTwisterConfig.TWISTER_CONFIG_FLAVOR);
							acquire();
							stopRenderers();
							stop();
							start(config);
							startRenderers();
							release();
						}
						catch (final UnsupportedFlavorException x) {
							x.printStackTrace();
						}
						catch (final IOException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						accept = true;
						break;
					}
					else if (element.equals(TransferableTwisterClip.TWISTER_CLIP_FLAVOR)) {
						try {
							e.acceptDrop(DnDConstants.ACTION_COPY);
							final TwisterClip clip = (TwisterClip) e.getTransferable().getTransferData(TransferableTwisterClip.TWISTER_CLIP_FLAVOR);
							acquire();
							stopRenderers();
							stop();
							start(clip);
							startRenderers();
							release();
						}
						catch (final UnsupportedFlavorException x) {
							x.printStackTrace();
						}
						catch (final IOException x) {
							x.printStackTrace();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
						accept = true;
						break;
					}
				}
				if (accept) {
					e.dropComplete(true);
					paintBorder = false;
					refresh();
				}
				else {
					e.rejectDrop();
				}
			}
		}

		/**
		 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
		 */
		public void dragGestureRecognized(final DragGestureEvent e) {
			if (isDragEnabled()) {
				try {
					if (clip != null) {
						source.startDrag(e, DragSource.DefaultCopyDrop, new TransferableTwisterClip(clip), this);
					}
					else {
						source.startDrag(e, DragSource.DefaultCopyDrop, new TransferableTwisterConfig(config), this);
					}
					paintBorder = true;
					isTarget = false;
					refresh();
				}
				catch (final InvalidDnDOperationException x) {
					x.printStackTrace();
				}
			}
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragEnter(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dragOver(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
		 */
		public void dragExit(final DragSourceEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
		 */
		public void dropActionChanged(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
		 */
		public void dragDropEnd(final DragSourceDropEvent e) {
			paintBorder = false;
			isTarget = true;
			refresh();
		}
	}
}
