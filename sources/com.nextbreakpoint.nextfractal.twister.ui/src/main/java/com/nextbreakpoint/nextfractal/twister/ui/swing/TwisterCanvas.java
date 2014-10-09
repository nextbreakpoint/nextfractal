/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.twister.ui.swing;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.Semaphore;

import javax.swing.SingleSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.twister.ControllerListener;
import com.nextbreakpoint.nextfractal.twister.TwisterBookmark;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLImporter;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.converter.ConverterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.OverlayTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.java2D.Java2DRenderFactory;
import com.nextbreakpoint.nextfractal.twister.ui.RenderListener;

/**
 * @author Andrea Medeghini
 */
@SuppressWarnings("unchecked")
public class TwisterCanvas extends Canvas implements RenderContext {
	// private static final Logger logger = Logger.getLogger(TwisterCanvas.class);
	private static final int MIN_HEIGHT = 20;
	private static final int MIN_WITH = 20;
	private static final long frameRate = 25;
	private static final long serialVersionUID = 1L;
	private static final int SCALE = 5;
	public static final int SYMBOL_NONE = 0;
	public static final int SYMBOL_PLAY = 1;
	public static final int SYMBOL_PAUSE = 2;
	public static final int SYMBOL_RECORD = 3;
	public static final int STATE_INIT = 0;
	public static final int STATE_EDIT = 1;
	public static final int STATE_PLAY = 2;
	public static final int STATE_SCRIPT = 3;
	private Color messageColor = new Color(80, 80, 80, 180);
	private final SingleSelectionModel model;
	private final List<BookmarkIcon> bookmarkIcons;
	private TwisterRenderer[] renderers;
	private TwisterRuntime[] runtimes;
	private OverlayTwisterRenderer overlayRenderer;
	private TwisterRuntime overlayRuntime;
	private TwisterConfig config;
	private TwisterClip clip;
	private InputAdapter adapter;
	private boolean showBookmarkIcons;
	private RefreshTask refreshCanvas;
	private int selectedIndex = -1;
	private final int hcells;
	private final int vcells;
	private final Font messageFont = Font.decode("arial-normal-12");
	private final ChangeEvent event = new ChangeEvent(this);
	private final List<ChangeListener> changeListeners = new LinkedList<ChangeListener>();
	private final List<RenderContextListener> contextListeners = new LinkedList<RenderContextListener>();
	private IntegerVector2D size = new IntegerVector2D(200, 200);
	private int symbol = TwisterCanvas.SYMBOL_NONE;
	private int state = TwisterCanvas.STATE_INIT;
	private VolatileImage volatileImage;
	private long time;
	private Surface surface;
	private RenderListener listener;
	boolean paintBorder = false;
	boolean isTarget = true;
	private final DropTarget target;
	private final DragSource source;
	private boolean dragEnabled;
	private boolean dropEnabled = true;
	private TwisterClipController clipController;
	private final Semaphore semaphore = new Semaphore(1, true);
	private final List<Runnable>[] commands = new LinkedList[2];
	private TwisterMessage twisterMessage;
	private volatile Thread resizeThread;

	/**
	 * @param hcells
	 * @param vcells
	 * @param model
	 * @throws ExtensionException
	 */
	public TwisterCanvas(final int hcells, final int vcells, final SingleSelectionModel model) throws ExtensionException {
		this.hcells = hcells;
		this.vcells = vcells;
		this.model = model;
		commands[0] = new LinkedList<Runnable>();
		commands[1] = new LinkedList<Runnable>();
		source = new DragSource();
		target = new DropTarget();
		this.setSize(new Dimension(size.getX(), size.getY()));
		bookmarkIcons = new ArrayList<BookmarkIcon>();
		final CanvasListener listener = new CanvasListener();
		addComponentListener(listener);
		addFocusListener(listener);
		addKeyListener(listener);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setFocusable(true);
		setBackground(new Color(0x2f2f2f));
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
	 * @param listener
	 */
	public void addChangeListener(final ChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeChangeListener(final ChangeListener listener) {
		changeListeners.remove(listener);
	}

	/**
	 * 
	 */
	protected void fireChangeEvent() {
		for (final ChangeListener listener : changeListeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * @param message
	 * @param size
	 * @param x
	 * @param y
	 * @param time
	 * @param hasBackground
	 */
	public void showMessage(String message, float size, float x, float y, long time, boolean hasBackground) {
		try {
			acquire();
			twisterMessage = new TwisterMessage(message, size, x, y, time, hasBackground);
			release();
			refresh();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @param command
	 */
	public void submitCommand(final Runnable command) {
		synchronized (commands[0]) {
			commands[0].add(command);
		}
		// workaround to refresh layer preview
		config.getContext().updateTimestamp();
		refresh();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
	 */
	@Override
	public void acquire() throws InterruptedException {
		semaphore.acquire();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
	 */
	@Override
	public void release() {
		semaphore.release();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	@Override
	public void addRenderContextListener(RenderContextListener listener) {
		synchronized (contextListeners) {
			contextListeners.add(listener);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	@Override
	public void removeRenderContextListener(RenderContextListener listener) {
		synchronized (contextListeners) {
			contextListeners.remove(listener);
		}
	}

	/**
	 * 
	 */
	@Override
	public void refresh() {
		if (refreshCanvas != null) {
			refreshCanvas.refresh();
		}
		synchronized (contextListeners) {
			for (RenderContextListener listener : contextListeners) {
				listener.refresh();
			}
		}
	}

	/**
	 * @return
	 */
	public TwisterConfig getConfig() {
		return config;
	}

	/**
	 * @return
	 */
	public TwisterClip getClip() {
		return clip;
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
	public void start(TwisterClip clip) {
		clipController = new TwisterClipController(clip);
		clipController.setRenderContext(TwisterCanvas.this);
		clipController.addControllerListener(new ControllerListener() {
			@Override
			public void actionRedone(final NodeAction action) {
			}

			@Override
			public void actionUndone(final NodeAction action) {
			}

			@Override
			public void configChanged() {
			}
		});
		clipController.init();
		final TwisterConfig config = clipController.getConfig();
		if (clipController.getDuration() > 0) {
			this.clip = clip;
			setListener(new RenderListener() {
				@Override
				public void frameRendered() {
					if (clipController != null) {
						if (!clipController.redoAction(1000 / getFrameRate(), true)) {
							clipController.init();
						}
					}
				}
			});
			state = TwisterCanvas.STATE_PLAY;
			symbol = TwisterCanvas.SYMBOL_PLAY;
		}
		else {
			clip = null;
			clipController = null;
			setListener(null);
			state = TwisterCanvas.STATE_EDIT;
			symbol = TwisterCanvas.SYMBOL_NONE;
		}
		final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
		init(config, size, hcells, vcells);
		if (refreshCanvas == null) {
			refreshCanvas = new RefreshTask();
			refreshCanvas.start();
		}
	}

	/**
	 * 
	 */
	public void start(final TwisterConfig config) {
		final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
		init(config, size, hcells, vcells);
		clip = null;
		clipController = null;
		setListener(null);
		state = TwisterCanvas.STATE_EDIT;
		symbol = TwisterCanvas.SYMBOL_NONE;
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
		symbol = TwisterCanvas.SYMBOL_NONE;
		// refreshTask = null;
		clipController = null;
		setListener(null);
	}

	/**
	 * 
	 */
	public void startScript() {
		if (state == STATE_EDIT) {
			state = STATE_SCRIPT;
		}
	}

	/**
	 * 
	 */
	public void stopScript() {
		if (state == STATE_SCRIPT) {
			state = STATE_EDIT;
		}
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
			@Override
			public void frameRendered() {
				if (clipController != null) {
					if (!clipController.redoAction(1000 / getFrameRate(), true)) {
						clipController.init();
					}
				}
			}
		});
	}

	/**
	 * 
	 */
	public void suspend() {
		setListener(null);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#getImageSize()
	 */
	@Override
	public IntegerVector2D getImageSize() {
		return size;
	}

	/**
	 * @return
	 */
	public long getFrameRate() {
		return TwisterCanvas.frameRate;
	}

	private void init(final TwisterConfig config, final IntegerVector2D size, final int nx, final int ny) {
		final int dx = size.getX() / nx;
		final int dy = size.getY() / ny;
		final HashMap<Object, Object> hints = new HashMap<Object, Object>();
		hints.put(TwisterRenderingHints.KEY_QUALITY, TwisterRenderingHints.QUALITY_REALTIME);
		if (Boolean.getBoolean("nextfractal.lowMemory")) {
			hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		}
		if (runtimes != null) {
			for (int i = 0; i < runtimes.length; i++) {
				if (runtimes[i] != null) {
					runtimes[i].dispose();
				}
			}
		}
		if (overlayRuntime != null) {
			overlayRuntime.dispose();
		}
		renderers = new TwisterRenderer[nx * ny];
		runtimes = new TwisterRuntime[nx * ny];
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				final int k = j * nx + i;
				runtimes[k] = new TwisterRuntime(config);
				renderers[k] = new DefaultTwisterRenderer(runtimes[k]);
				renderers[k].setRenderFactory(new Java2DRenderFactory());
				renderers[k].setRenderingHints(hints);
				renderers[k].setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(dx, dy), new IntegerVector2D(dx * i, dy * j), new IntegerVector2D(24, 24)));
			}
		}
		final HashMap<Object, Object> overlayHints = new HashMap<Object, Object>();
		overlayHints.put(TwisterRenderingHints.KEY_QUALITY, TwisterRenderingHints.QUALITY_REALTIME);
		if (Boolean.getBoolean("nextfractal.lowMemory")) {
			overlayHints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		}
		overlayHints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_OVERLAY);
		overlayRuntime = new TwisterRuntime(config);
		overlayRenderer = new OverlayTwisterRenderer(overlayRuntime);
		overlayRenderer.setRenderFactory(new Java2DRenderFactory());
		overlayRenderer.setRenderingHints(overlayHints);
		overlayRenderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
		adapter = new DefaultInputAdapter(new DefaultRenderContext(), config);
		this.size = size;
		this.config = config;
	}

	private void dispose() {
		if (renderers != null) {
			for (final TwisterRenderer element : renderers) {
				element.dispose();
			}
		}
		if (overlayRenderer != null) {
			overlayRenderer.dispose();
		}
		renderers = null;
		overlayRenderer = null;
		if (runtimes != null) {
			for (final TwisterRuntime element : runtimes) {
				element.dispose();
			}
		}
		if (overlayRuntime != null) {
			overlayRuntime.dispose();
		}
		runtimes = null;
		overlayRuntime = null;
	}

	private void resize(final IntegerVector2D size, final int nx, final int ny) throws ExtensionException {
		try {
			if (!this.size.equals(size)) {
				final int dx = size.getX() / nx;
				final int dy = size.getY() / ny;
				acquire();
				stopRenderers();
				if (renderers != null) {
					for (int i = 0; i < nx; i++) {
						for (int j = 0; j < ny; j++) {
							final int k = j * nx + i;
							renderers[k].setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(dx, dy), new IntegerVector2D(dx * i, dy * j), new IntegerVector2D(32, 32)));
						}
					}
				}
				if (overlayRenderer != null) {
					overlayRenderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
				}
				startRenderers();
				final int width = getMaxWidth();
				final int height = getMaxHeight();
				for (int i = 0; i < bookmarkIcons.size(); i++) {
					final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
					bookmarkIcon.getBookmark().getRenderer().abortRenderer();
				}
				for (int i = 0; i < bookmarkIcons.size(); i++) {
					final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
					bookmarkIcon.getBookmark().getRenderer().joinRenderer();
					bookmarkIcon.getBookmark().getRenderer().setTile(new Tile(new IntegerVector2D(width, height), new IntegerVector2D(width, height), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
					bookmarkIcon.getBookmark().getRenderer().startRenderer();
				}
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
			configureGraphics(g);
			paintImage(g);
			g.dispose();
		}
	}

	private void clear() {
		if (volatileImage != null) {
			Graphics2D g = volatileImage.createGraphics();
			configureGraphics(g);
			clearImage(g);
			g.dispose();
		}
	}

	private void configureGraphics(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	private void clearImage(final Graphics g) {
		g.setColor(getBackground());
		((Graphics2D) g).setComposite(AlphaComposite.SrcOver);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintImage(final Graphics2D g) {
		synchronized (contextListeners) {
			if (state == TwisterCanvas.STATE_EDIT) {
				if ((renderers != null) && (overlayRenderer != null)) {
					for (final TwisterRenderer renderer : renderers) {
						renderer.prepareImage(true);
					}
					overlayRenderer.prepareImage(true);
					for (RenderContextListener listener : contextListeners) {
						listener.prepareImage(true);
					}
					final EffectExtensionRuntime<?> effectRuntime = overlayRenderer.getRuntime().getEffectElement().getEffectRuntime();
					if ((effectRuntime != null) && overlayRenderer.getRuntime().getEffectElement().isEnabled()) {
						if (surface == null) {
							surface = new Surface(size.getX(), size.getY());
						}
						for (final TwisterRenderer renderer : renderers) {
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
						for (final TwisterRenderer renderer : renderers) {
							renderer.drawImage(g);
						}
					}
					overlayRenderer.drawImage(g);
					for (RenderContextListener listener : contextListeners) {
						listener.drawImage();
					}
				}
				g.setComposite(AlphaComposite.SrcOver);
				if (showBookmarkIcons) {
					final Composite composite = g.getComposite();
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g.setColor(Color.BLACK);
					for (int i = 0; i < bookmarkIcons.size(); i++) {
						final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
						drawBookmarkIcon(g, bookmarkIcon);
					}
					// if (model.getSelectedIndex() != -1 && model.getSelectedIndex() != selectedIndex)
					// {
					// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
					//					
					// g.setColor(Color.BLUE);
					//					
					// BookmarkIcon bookmarkIcon = bookmarkIcons.get(model.getSelectedIndex());
					//					
					// drawBookmarkIcon(g, bookmarkIcon);
					// }
					//				
					if (selectedIndex != -1) {
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
						g.setColor(Color.RED);
						final BookmarkIcon bookmarkIcon = bookmarkIcons.get(selectedIndex);
						drawBookmarkIcon(g, bookmarkIcon);
					}
					g.setComposite(composite);
				}
			}
			else if ((state == TwisterCanvas.STATE_PLAY) || (state == TwisterCanvas.STATE_SCRIPT)) {
				if ((renderers != null) && (overlayRenderer != null)) {
					for (final TwisterRenderer renderer : renderers) {
						renderer.prepareImage(true);
					}
					overlayRenderer.prepareImage(true);
					final EffectExtensionRuntime<?> effectRuntime = overlayRenderer.getRuntime().getEffectElement().getEffectRuntime();
					if ((effectRuntime != null) && overlayRenderer.getRuntime().getEffectElement().isEnabled()) {
						if (surface == null) {
							surface = new Surface(size.getX(), size.getY());
						}
						for (final TwisterRenderer renderer : renderers) {
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
						for (final TwisterRenderer renderer : renderers) {
							renderer.drawImage(g);
						}
					}
					overlayRenderer.drawImage(g);
				}
			}
			g.setComposite(AlphaComposite.SrcOver);
			if (twisterMessage != null) {
				if (System.currentTimeMillis() < twisterMessage.getExpireTime()) {
					float x = (twisterMessage.getX() * getWidth()) / 100f;
					float y = (twisterMessage.getY() * getHeight()) / 100f;
					final Font font = twisterMessage.getFont(getHeight());
					g.setFont(font);
					if (twisterMessage.hasBackground()) {
						g.setColor(messageColor);
						Rectangle2D bounds = font.getStringBounds(twisterMessage.getMessage(), g.getFontRenderContext());
						g.fillRect(0, (int) Math.rint(y - font.getSize2D() - 5), getWidth(), (int) bounds.getHeight() + 10);
					}
					g.setColor(Color.YELLOW);
					g.drawString(twisterMessage.getMessage(), x, y);
				}
				else {
					twisterMessage = null;
				}
			}
			if (hasFocus()) {
				g.setClip(null);
				g.setColor(Color.BLUE);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
			switch (symbol) {
				case SYMBOL_NONE: {
					break;
				}
				case SYMBOL_PLAY: {
					g.setColor(Color.GREEN);
					final Polygon p = new Polygon();
					p.addPoint(10, 10);
					p.addPoint(25, 17);
					p.addPoint(10, 24);
					g.fill(p);
					break;
				}
				case SYMBOL_PAUSE: {
					g.setColor(Color.YELLOW);
					g.fillRect(10, 10, 6, 15);
					g.fillRect(19, 10, 6, 15);
					break;
				}
				case SYMBOL_RECORD: {
					if (((System.currentTimeMillis() - time) / 1000) % 2 == 0) {
						g.setColor(Color.RED);
						g.fillOval(10, 10, 15, 15);
					}
					break;
				}
			}
			if (paintBorder) {
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		}
	}

	private void drawBookmarkIcon(final Graphics2D g, final BookmarkIcon bookmarkIcon) {
		final int x = (int) Math.rint(bookmarkIcon.x);
		final int y = (int) Math.rint(bookmarkIcon.y);
		final int w = (int) Math.rint(bookmarkIcon.w);
		final int h = (int) Math.rint(bookmarkIcon.h);
		bookmarkIcon.getBookmark().getRenderer().drawImage(g, x, y - 2, w, h, 2, 2);
		g.setClip(null);
		g.drawRect(x + 2, y, w - 5, h - 5);
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
				refreshThread.setName("TwisterCanvas RefreshTask");
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
		@Override
		public void run() {
			try {
				long startTime = 0;
				long totalTime = 0;
				final long pauseTime = 1000 / TwisterCanvas.frameRate;
				long sleepTime = pauseTime;
				long idleTime = 0;
				while (running) {
					startTime = System.currentTimeMillis();
					synchronized (commands) {
						LinkedList<Runnable> tmpCommands = (LinkedList<Runnable>) commands[1];
						commands[0] = commands[1];
						commands[1] = tmpCommands;
					}
					acquire();
					draw();
					adapter.refresh();
					refresh |= needsRefresh();
					if (listener != null) {
						listener.frameRendered();
					}
					while (commands[1].size() > 0) {
						commands[1].remove(0).run();
					}
					release();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							repaint();
						}
					});
					synchronized (lock) {
						if (refresh || (twisterMessage != null) || (symbol == TwisterCanvas.SYMBOL_RECORD) || (symbol == TwisterCanvas.SYMBOL_PLAY)) {
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
			if (runtimes != null) {
				for (final TwisterRuntime runtime : runtimes) {
					needsRefresh |= runtime.isChanged();
				}
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
	@Override
	public void startRenderers() {
		synchronized (contextListeners) {
			if (renderers != null) {
				for (final TwisterRenderer renderer : renderers) {
					renderer.startRenderer();
				}
			}
			if (overlayRenderer != null) {
				overlayRenderer.startRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.startRenderer();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
	 */
	@Override
	public void stopRenderers() {
		synchronized (contextListeners) {
			if (renderers != null) {
				for (final TwisterRenderer renderer : renderers) {
					renderer.abortRenderer();
				}
			}
			if (overlayRenderer != null) {
				overlayRenderer.abortRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.abortRenderer();
			}
			if (renderers != null) {
				for (final TwisterRenderer renderer : renderers) {
					renderer.joinRenderer();
				}
			}
			if (overlayRenderer != null) {
				overlayRenderer.joinRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.joinRenderer();
			}
		}
	}

	private void resizeIcons(final double maxWidth, final double maxHeight) {
		double minWidth = maxWidth / 2.0;
		double minHeight = maxHeight / 2.0;
		if (minWidth * bookmarkIcons.size() > getWidth() - maxWidth) {
			minWidth = (getWidth() - maxWidth) / bookmarkIcons.size();
			minHeight = (minWidth * getHeight()) / getWidth();
			for (int i = 0; i < bookmarkIcons.size(); i++) {
				final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
				bookmarkIcon.w = minWidth;
				bookmarkIcon.h = minHeight;
				bookmarkIcon.tw = minWidth;
				bookmarkIcon.th = minHeight;
			}
		}
		else {
			for (int i = 0; i < bookmarkIcons.size(); i++) {
				final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
				bookmarkIcon.w = minWidth;
				bookmarkIcon.h = minHeight;
				bookmarkIcon.tw = minWidth;
				bookmarkIcon.th = minHeight;
			}
		}
		double w = 0;
		for (int i = 0; i < bookmarkIcons.size(); i++) {
			final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
			w += bookmarkIcon.w;
		}
		double x = (getWidth() - w) / 2.0;
		for (int i = 0; i < bookmarkIcons.size(); i++) {
			final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
			bookmarkIcon.y = getHeight() - bookmarkIcon.h;
			bookmarkIcon.x = x;
			bookmarkIcon.tx = bookmarkIcon.x;
			bookmarkIcon.ty = bookmarkIcon.y;
			x += bookmarkIcon.w;
		}
		selectedIndex = -1;
	}

	private void zoomIcons(final double maxWidth, final double maxHeight, final double px, final double py) {
		int k = -1;
		for (int i = 0; i < bookmarkIcons.size(); i++) {
			final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
			if ((bookmarkIcon.tx <= px) && (bookmarkIcon.tx + bookmarkIcon.tw >= px)) {
				if ((bookmarkIcon.y <= py) && (bookmarkIcon.y + bookmarkIcon.h >= py)) {
					k = i;
					break;
				}
			}
		}
		if (k != -1) {
			BookmarkIcon bookmarkIcon = bookmarkIcons.get(k);
			for (int i = 0; i < bookmarkIcons.size(); i++) {
				bookmarkIcon = bookmarkIcons.get(i);
				final double d = bookmarkIcon.tw;
				final double D = maxWidth;
				final double t = px - bookmarkIcon.tx;
				bookmarkIcon.x = bookmarkIcon.tx - getPos(t, D, d);
				bookmarkIcon.w = bookmarkIcon.tx + d - getPos(t - d, D, d) - bookmarkIcon.x;
				bookmarkIcon.h = (bookmarkIcon.th / bookmarkIcon.tw) * bookmarkIcon.w;
				bookmarkIcon.y = getHeight() - bookmarkIcon.h;
			}
			selectedIndex = k;
		}
		else {
			this.zoomIcons();
		}
	}

	private void zoomIcons() {
		for (int i = 0; i < bookmarkIcons.size(); i++) {
			final BookmarkIcon bookmarkIcon = bookmarkIcons.get(i);
			bookmarkIcon.x = bookmarkIcon.tx;
			bookmarkIcon.y = bookmarkIcon.ty;
			bookmarkIcon.w = bookmarkIcon.tw;
			bookmarkIcon.h = bookmarkIcon.th;
		}
		selectedIndex = -1;
	}

	private double getPos(final double x, final double D, final double d) {
		final double z = Math.abs(x / d * 2.0);
		final double s = (x >= 0) ? +1 : -1;
		if ((z >= 0) && (z <= 7)) {
			return s * (D * ((getA(1, 0.25, 7, 1) * z + getB(1, 0.25, 7, 1)) * z) + (D / 4.0 - d / 2.0));
		}
		else {
			return s * (D + (D / 4.0 - d / 2.0));
		}
	}

	private double getA(final double x0, final double y0, final double x1, final double y1) {
		return (y1 * x0 - y0 * x1) / (x0 * x1 * (x1 - x0));
	}

	private double getB(final double x0, final double y0, final double x1, final double y1) {
		return (y0 - (y1 * x0 - y0 * x1) / (x0 * x1 * (x1 - x0)) * x0) / x0;
	}

	private int getMaxHeight() {
		return getHeight() / TwisterCanvas.SCALE;
	}

	private int getMaxWidth() {
		return getWidth() / TwisterCanvas.SCALE;
	}

	/**
	 * @param bookmark
	 */
	public void addBookmark(final TwisterBookmark bookmark) {
		GUIUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				final int width = TwisterCanvas.this.getMaxWidth();
				final int height = TwisterCanvas.this.getMaxHeight();
				bookmark.getRenderer().setTile(new Tile(new IntegerVector2D(width, height), new IntegerVector2D(width, height), new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
				final BookmarkIcon bookmarkIcon = new BookmarkIcon(bookmark);
				bookmarkIcons.add(bookmarkIcon);
				TwisterCanvas.this.resizeIcons(width, height);
				bookmark.getRenderer().startRenderer();
				refresh();
			}
		}, true);
	}

	/**
	 * @param bookmark
	 */
	public void removeBookmark() {
		if (selectedIndex != -1) {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					bookmarkIcons.remove(selectedIndex);
					TwisterCanvas.this.resizeIcons(TwisterCanvas.this.getMaxWidth(), TwisterCanvas.this.getMaxHeight());
					model.setSelectedIndex(-1);
					refresh();
				}
			}, true);
		}
	}

	/**
	 * @return
	 */
	public TwisterBookmark getSelectedBookmark() {
		if (model.getSelectedIndex() != -1) {
			return bookmarkIcons.get(model.getSelectedIndex()).getBookmark();
		}
		return null;
	}

	/**
	 * @return the showBookmarks
	 */
	public boolean isShowBookmarkIcons() {
		return showBookmarkIcons;
	}

	/**
	 * @param showBookmarks the showBookmarks to set
	 */
	public void setShowBookmarkIcons(final boolean showBookmarks) {
		showBookmarkIcons = showBookmarks;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @return the symbol
	 */
	public int getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(final int symbol) {
		time = System.currentTimeMillis();
		this.symbol = symbol;
		if ((symbol == TwisterCanvas.SYMBOL_RECORD) || (symbol == TwisterCanvas.SYMBOL_PLAY)) {
			if (overlayRuntime != null) {
				if (overlayRuntime.getEffectElement().getEffectRuntime() != null) {
					overlayRuntime.getEffectElement().getEffectRuntime().reset();
				}
			}
		}
		refresh();
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

	private void resize() {
		if (state != TwisterCanvas.STATE_INIT) {
			try {
				final IntegerVector2D size = new IntegerVector2D(Math.max(getWidth(), MIN_WITH), Math.max(getHeight(), MIN_HEIGHT));
				TwisterCanvas.this.resize(size, hcells, vcells);
				final int width = getWidth() / TwisterCanvas.SCALE;
				final int height = getHeight() / TwisterCanvas.SCALE;
				resizeIcons(width, height);
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
	}

	private class CanvasListener implements ComponentListener, MouseListener, MouseMotionListener, KeyListener, FocusListener, DropTargetListener, DragGestureListener, DragSourceListener {
		/**
		 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentHidden(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentMoved(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentResized(final ComponentEvent e) {
			if ((e.getComponent().getWidth() > 0) && (e.getComponent().getHeight() > 0)) {
				if (resizeThread == null) {
					resizeThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							}
							catch (InterruptedException e) {
							}
							GUIUtil.executeTask(new Runnable() {
								@Override
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
		@Override
		public void componentShown(final ComponentEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				if (e.isShiftDown()) {
					if (showBookmarkIcons && (selectedIndex != -1)) {
						removeBookmark();
					}
					else {
						try {
							acquire();
							adapter.mouseClicked(e);
							release();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				}
				else {
					if (showBookmarkIcons && (selectedIndex != -1)) {
						model.setSelectedIndex(selectedIndex);
					}
					else {
						try {
							acquire();
							adapter.mouseClicked(e);
							release();
						}
						catch (InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				}
			}
			else if (state == TwisterCanvas.STATE_SCRIPT) {
				fireChangeEvent();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				final int maxWidth = getMaxWidth();
				final int maxHeight = getMaxHeight();
				TwisterCanvas.this.zoomIcons(maxWidth, maxHeight, e.getX(), e.getY());
				try {
					acquire();
					adapter.mouseEntered(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				TwisterCanvas.this.zoomIcons();
				try {
					acquire();
					adapter.mouseExited(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				if (!showBookmarkIcons || (selectedIndex == -1)) {
					try {
						acquire();
						adapter.mousePressed(e);
						release();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				try {
					acquire();
					adapter.mouseReleased(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				try {
					acquire();
					adapter.mouseDragged(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseMoved(final MouseEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				final int maxWidth = getMaxWidth();
				final int maxHeight = getMaxHeight();
				TwisterCanvas.this.zoomIcons(maxWidth, maxHeight, e.getX(), e.getY());
				try {
					acquire();
					adapter.mouseMoved(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
				if (selectedIndex == -1) {
					model.setSelectedIndex(-1);
				}
			}
		}

		/**
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(final KeyEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				try {
					acquire();
					adapter.keyTyped(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(final KeyEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				try {
					acquire();
					adapter.keyPressed(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(final KeyEvent e) {
			refresh();
			if (state == TwisterCanvas.STATE_EDIT) {
				if (e.getKeyCode() == KeyEvent.VK_B) {
					showBookmarkIcons = !showBookmarkIcons;
				}
				try {
					acquire();
					adapter.keyReleased(e);
					release();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}

		/**
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(final FocusEvent e) {
			refresh();
		}

		/**
		 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(final FocusEvent e) {
			refresh();
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dragEnter(final DropTargetDragEvent e) {
			if (!isDropEnabled()) {
				e.rejectDrag();
			}
			else if (isTarget) {
				final DataFlavor[] flavors = e.getCurrentDataFlavors();
				boolean accept = false;
				if (state == TwisterCanvas.STATE_EDIT) {
					for (final DataFlavor element : flavors) {
						if (element.equals(TransferableTwisterConfig.TWISTER_CONFIG_FLAVOR)) {
							accept = true;
							break;
						}
						else if (element.equals(TransferableTwisterClip.TWISTER_CLIP_FLAVOR)) {
							accept = true;
							break;
						}
						else if (element.equals(DataFlavor.javaFileListFlavor)) {
							accept = true;
							break;
						}
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
		@Override
		public void dragOver(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
		 */
		@Override
		public void dragExit(final DropTargetEvent e) {
			if (isTarget) {
				paintBorder = false;
				refresh();
			}
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
		 */
		@Override
		public void dropActionChanged(final DropTargetDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
		 */
		@Override
		public void drop(final DropTargetDropEvent e) {
			if (isTarget) {
				if (state == TwisterCanvas.STATE_EDIT) {
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
								TwisterCanvas.this.config.getContext().updateTimestamp();
								TwisterCanvas.this.config.setFrameConfigElement(config.getFrameConfigElement().clone());
								TwisterCanvas.this.config.setEffectConfigElement(config.getEffectConfigElement().clone());
								TwisterCanvas.this.config.setBackground(config.getBackground());
								start(TwisterCanvas.this.config);
								startRenderers();
								release();
								accept = true;
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
								accept = true;
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
							break;
						}
						else if (element.equals(DataFlavor.javaFileListFlavor)) {
							try {
								e.acceptDrop(DnDConstants.ACTION_COPY);
								final List<File> files = (List<File>) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
								if (files.size() > 0) {
									final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
									final InputStream is = new FileInputStream(files.get(0));
									TwisterClip clip = null;
									try {
										Document doc = XML.loadDocument(is, "twister-clip.xml");
										clip = importer.importFromElement(doc.getDocumentElement());
										is.close();
									}
									catch (Exception x) {
										List<Extension<ConverterExtensionRuntime>> extensions = TwisterRegistry.getInstance().getConverterRegistry().getExtensionList();
										for (Extension<ConverterExtensionRuntime> extension : extensions) {
											try {
												ConverterExtensionRuntime runtime = extension.createExtensionRuntime();
												TwisterConfig config = runtime.createConverter().convert(files.get(0));
												if (config != null) {
													clip = new TwisterClip();
													clip.addSequence(new TwisterSequence());
													clip.getSequence(0).setDuration(0);
													clip.getSequence(0).setInitialConfig(config);
												}
											}
											catch (Exception q) {
												q.printStackTrace();
											}
										}
										if (clip == null) {
											throw x;
										}
									}
									acquire();
									stopRenderers();
									stop();
									if (clip.getSequenceCount() == 1) {
										if (clip.getSequence(0).getDuration() == 0) {
											if (clip.getSequence(0).getInitialConfig() != null) {
												config.getContext().updateTimestamp();
												config.setFrameConfigElement(clip.getSequence(0).getInitialConfig().getFrameConfigElement().clone());
												config.setEffectConfigElement(clip.getSequence(0).getInitialConfig().getEffectConfigElement().clone());
												config.setBackground(clip.getSequence(0).getInitialConfig().getBackground());
												start(config);
											}
											else if (clip.getSequence(0).getFinalConfig() != null) {
												config.getContext().updateTimestamp();
												config.setFrameConfigElement(clip.getSequence(0).getFinalConfig().getFrameConfigElement().clone());
												config.setEffectConfigElement(clip.getSequence(0).getFinalConfig().getEffectConfigElement().clone());
												config.setBackground(clip.getSequence(0).getFinalConfig().getBackground());
												start(config);
											}
										}
										else {
											start(clip);
										}
									}
									else {
										start(clip);
									}
									startRenderers();
									release();
									accept = true;
								}
							}
							catch (InterruptedException x) {
								Thread.currentThread().interrupt();
							}
							catch (final UnsupportedFlavorException x) {
								x.printStackTrace();
							}
							catch (final IOException x) {
								x.printStackTrace();
							}
							catch (SAXException x) {
								x.printStackTrace();
							}
							catch (ParserConfigurationException x) {
								x.printStackTrace();
							}
							catch (XMLImportException x) {
								x.printStackTrace();
							}
							catch (Exception x) {
								x.printStackTrace();
							}
							break;
						}
					}
					if (accept) {
						e.dropComplete(true);
						paintBorder = false;
						fireChangeEvent();
						refresh();
					}
					else {
						e.rejectDrop();
					}
				}
			}
		}

		/**
		 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
		 */
		@Override
		public void dragGestureRecognized(final DragGestureEvent e) {
			if (isDragEnabled()) {
				try {
					source.startDrag(e, DragSource.DefaultCopyDrop, new TransferableTwisterConfig(config), this);
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
		@Override
		public void dragEnter(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
		 */
		@Override
		public void dragOver(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
		 */
		@Override
		public void dragExit(final DragSourceEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
		 */
		@Override
		public void dropActionChanged(final DragSourceDragEvent e) {
		}

		/**
		 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
		 */
		@Override
		public void dragDropEnd(final DragSourceDropEvent e) {
			paintBorder = false;
			isTarget = true;
			refresh();
		}
	}

	private class BookmarkIcon {
		private final TwisterBookmark bookmark;
		public double x;
		public double y;
		public double w;
		public double h;
		public double tx;
		public double ty;
		public double tw;
		public double th;

		/**
		 * @param bookmark
		 */
		public BookmarkIcon(final TwisterBookmark bookmark) {
			this.bookmark = bookmark;
		}

		/**
		 * @return the bookmark
		 */
		public TwisterBookmark getBookmark() {
			return bookmark;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			return bookmark.equals(((BookmarkIcon) obj).bookmark);
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return bookmark.hashCode();
		}
	}

	private class DefaultRenderContext implements RenderContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
		 */
		@Override
		public void startRenderers() {
			TwisterCanvas.this.startRenderers();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
		 */
		@Override
		public void stopRenderers() {
			TwisterCanvas.this.stopRenderers();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#getImageSize()
		 */
		@Override
		public IntegerVector2D getImageSize() {
			return size;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#refresh()
		 */
		@Override
		public void refresh() {
			TwisterCanvas.this.refresh();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
		 */
		@Override
		public void acquire() throws InterruptedException {
			TwisterCanvas.this.acquire();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
		 */
		@Override
		public void release() {
			TwisterCanvas.this.release();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void addRenderContextListener(RenderContextListener listener) {
			TwisterCanvas.this.addRenderContextListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void removeRenderContextListener(RenderContextListener listener) {
			TwisterCanvas.this.removeRenderContextListener(listener);
		}
	}

	private class TwisterMessage {
		private String message;
		private float x;
		private float y;
		private float size;
		private long time;
		private long expireTime;
		private Font font = messageFont;
		private int height;
		private boolean hasBackground;

		/**
		 * @return
		 */
		public Font getFont(int height) {
			if (this.height != height) {
				float fontSize = ((size * height) / 100f);
				if (fontSize > 0) {
					this.height = height;
					font = messageFont.deriveFont((fontSize * 96f) / 72f);
				}
			}
			return font;
		}

		/**
		 * @return
		 */
		public long getExpireTime() {
			return expireTime;
		}

		/**
		 * @return the background
		 */
		public boolean hasBackground() {
			return hasBackground;
		}

		/**
		 * @return the time
		 */
		public long getTime() {
			return time;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @return the x
		 */
		public float getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		public float getY() {
			return y;
		}

		/**
		 * @return the size
		 */
		public float getSize() {
			return size;
		}

		/**
		 * @param message
		 * @param size
		 * @param x
		 * @param y
		 * @param time
		 * @param hasBackground
		 */
		public TwisterMessage(String message, float size, float x, float y, long time, boolean hasBackground) {
			this.message = message;
			this.size = size;
			this.x = x;
			this.y = y;
			this.time = time;
			this.hasBackground = hasBackground;
			expireTime = System.currentTimeMillis() + time;
		}
	}
}
