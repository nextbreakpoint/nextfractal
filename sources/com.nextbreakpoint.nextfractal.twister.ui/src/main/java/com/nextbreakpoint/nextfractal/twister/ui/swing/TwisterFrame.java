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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nextbreakpoint.nextfractal.core.DefaultTree;
import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.scripting.DefaultJSContext;
import com.nextbreakpoint.nextfractal.core.scripting.JSManager;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.ui.swing.IconButton;
import com.nextbreakpoint.nextfractal.core.ui.swing.NavigatorFrame;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIFactory;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.JSFileFilter;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.RenderService.ServiceCallback;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.twister.TwisterBookmark;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;
import com.nextbreakpoint.nextfractal.twister.TwisterSessionController;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.java2D.Java2DRenderFactory;

/**
 * @author Andrea Medeghini
 */
public class TwisterFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String TWISTER_FRAME_TITLE = "twisterFrame.title";
	private static final String TWISTER_FRAME_WIDTH = "twisterFrame.width";
	private static final String TWISTER_FRAME_HEIGHT = "twisterFrame.height";
	private static final String TWISTER_FRAME_ICON = "twisterFrame.icon";
	private static final Logger logger = Logger.getLogger(TwisterFrame.class.getName());
	private final Semaphore semaphore = new Semaphore(0, true);
	private final TwisterSessionController sessionController;
	private final DefaultSingleSelectionModel model;
	private final DefaultTree twisterTree;
	private final RenderService service;
	private OutputFrame outputFrame;
	private ConfigFrame configFrame;
	private NavigatorFrame advancedConfigFrame;
	private static ServiceFrame serviceFrame;
	private final TwisterConfig config;
	private final TwisterCanvas canvas;
	private TwisterClip clip;
	private TwisterClip tmpClip;
	private TwisterClip playClip;
	private DefaultTree tree;
	private Thread scriptThread;
	private long clipDuration;
	private long sequenceStopTime;
	private long sequenceStartTime;
	private TwisterSequence sequence;
	private final TwisterPanel panel;
	private final TwisterContext context;
	private final RenderProfileTableModel renderProfileTableModel;
	private final RenderJobTableModel renderJobTableModel;
	private final RenderClipTableModel renderClipTableModel;
	private final DefaulServiceContext serviceContext;

	/**
	 * @param context
	 * @param service
	 * @param config
	 * @param hcells
	 * @param vcells
	 * @throws HeadlessException
	 * @throws ExtensionException
	 */
	public TwisterFrame(final TwisterContext context, final RenderService service, final TwisterConfig config, final int hcells, final int vcells) throws HeadlessException, ExtensionException {
		this.context = context;
		TwisterFrame.logger.fine("Frame created");
		final int defaultWidth = Integer.parseInt(TwisterSwingResources.getInstance().getString(TwisterFrame.TWISTER_FRAME_WIDTH));
		final int defaultHeight = Integer.parseInt(TwisterSwingResources.getInstance().getString(TwisterFrame.TWISTER_FRAME_HEIGHT));
		final int width = Integer.getInteger(TwisterFrame.TWISTER_FRAME_WIDTH, defaultWidth);
		final int height = Integer.getInteger(TwisterFrame.TWISTER_FRAME_HEIGHT, defaultHeight);
		model = new DefaultSingleSelectionModel();
		canvas = new TwisterCanvas(hcells, vcells, model);
		panel = new TwisterPanel(canvas);
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle(TwisterSwingResources.getInstance().getString(TwisterFrame.TWISTER_FRAME_TITLE));
		final URL resource = TwisterFrame.class.getClassLoader().getResource(TwisterSwingResources.getInstance().getString(TwisterFrame.TWISTER_FRAME_ICON));
		if (resource != null) {
			setIconImage(getToolkit().createImage(resource));
		}
		this.setSize(new Dimension(width, height));
		final Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		p.x -= getWidth() / 2;
		p.y -= getHeight() / 2;
		this.setLocation(p);
		this.service = service;
		this.config = config;
		sessionController = new TwisterSessionController("options", config);
		sessionController.init();
		sessionController.setRenderContext(canvas);
		twisterTree = new DefaultTree();
		final TwisterConfigNodeBuilder nodeBuilder = new TwisterConfigNodeBuilder(config);
		twisterTree.getRootNode().setContext(config.getContext());
		twisterTree.getRootNode().setSession(sessionController);
		nodeBuilder.createNodes(twisterTree.getRootNode());
		addWindowListener(new TwisterWindowListener(canvas));
		setBackground(new Color(0x2f2f2f));
		canvas.setShowBookmarkIcons(true);
		canvas.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				if (panel.isScriptState()) {
					if (scriptThread != null) {
						scriptThread.interrupt();
					}
				}
				else if (panel.isEditState()) {
					clip = canvas.getClip();
					tmpClip = canvas.getClip();
					sequence = null;
					tree = null;
					panel.updateButtons();
				}
				else if (panel.isPlayState()) {
					clip = canvas.getClip();
					tmpClip = canvas.getClip();
					playClip = tmpClip;
					sequence = null;
					tree = null;
					panel.updateButtons();
				}
			}
		});
		context.addFrame(this);
		renderProfileTableModel = new RenderProfileTableModel(service);
		renderJobTableModel = new RenderJobTableModel(service);
		renderClipTableModel = new RenderClipTableModel(service);
		serviceContext = new DefaulServiceContext(context, service, hcells, vcells);
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		TwisterFrame.logger.fine("Frame finalized");
		super.finalize();
	}

	/**
	 * @param clip
	 */
	public void loadClip(final TwisterClip clip) {
		try {
			if (clip != null) {
				final TwisterClipController controller = new TwisterClipController(clip);
				controller.init();
				if (controller.getDuration() > 0) {
					canvas.acquire();
					canvas.stopRenderers();
					canvas.stop();
					this.clip = clip;
					tmpClip = clip;
					playClip = tmpClip;
					sequence = null;
					tree = null;
					canvas.start(clip);
					canvas.startRenderers();
					canvas.release();
					canvas.refresh();
				}
				else {
					canvas.acquire();
					canvas.stopRenderers();
					sequence = null;
					tree = null;
					config.setFrameConfigElement(controller.getConfig().getFrameConfigElement().clone());
					config.setEffectConfigElement(controller.getConfig().getEffectConfigElement().clone());
					config.setBackground(controller.getConfig().getBackground());
					canvas.startRenderers();
					canvas.release();
					canvas.refresh();
				}
			}
			panel.updateButtons();
		}
		catch (final HeadlessException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void openLogWindow() {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				if (outputFrame == null) {
					outputFrame = new OutputFrame();
					outputFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							if (outputFrame != null) {
								outputFrame.setVisible(false);
							}
							}
												});
				}
				}
						}, false);
	}

	private void showLogWindow() {
		openLogWindow();
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				outputFrame.setVisible(true);
								// outputFrame.toFront();
			}
						}, true);
	}

	private void openConfigWindow(final TwisterConfig config, final TwisterCanvas canvas) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				if (configFrame == null) {
					configFrame = new ConfigFrame(new DefaultTwisterConfigContext(), config, canvas, sessionController);
					configFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							if (configFrame != null) {
								configFrame.dispose();
								configFrame = null;
							}
							}
												});
				}
				configFrame.setVisible(true);
				configFrame.toFront();
				}
						}, true);
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				configFrame.setup();
				}
						}, true);
	}

	private void openAdvancedConfigWindow(final TwisterConfig config, final TwisterCanvas canvas) {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				if (advancedConfigFrame == null) {
					advancedConfigFrame = new NavigatorFrame(twisterTree, canvas, sessionController);
					advancedConfigFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							if (advancedConfigFrame != null) {
								advancedConfigFrame.dispose();
								advancedConfigFrame = null;
							}
							}
												});
				}
				advancedConfigFrame.setVisible(true);
				advancedConfigFrame.toFront();
				}
						}, true);
	}

	private void openServiceWindow() {
		GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
				if (TwisterFrame.serviceFrame == null) {
					TwisterFrame.serviceFrame = new ServiceFrame(serviceContext, service, renderClipTableModel, renderProfileTableModel, renderJobTableModel);
					TwisterFrame.serviceFrame.addWindowListener(new WindowAdapter() {
													/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowClosing(final WindowEvent e) {
							context.removeFrame(TwisterFrame.serviceFrame);
							TwisterFrame.serviceFrame = null;
							if (context.getFrameCount() == 0) {
								service.stop();
								context.exit();
							}
							}

						/**
						 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
						 */
						@Override
						public void windowOpened(final WindowEvent e) {
							context.addFrame(TwisterFrame.serviceFrame);
							}
												});
				}
				TwisterFrame.serviceFrame.setVisible(true);
				TwisterFrame.serviceFrame.toFront();
				}
						}, true);
	}

	private TwisterBookmark createBookmark() throws Exception {
		final TwisterBookmark bookmark = new TwisterBookmark();
		bookmark.setConfig(config.clone());
		final TwisterRuntime runtime = new TwisterRuntime(bookmark.getConfig());
		final DefaultTwisterRenderer renderer = new DefaultTwisterRenderer(runtime);
		final Map<Object, Object> hints = new HashMap<Object, Object>();
		hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		renderer.setRenderFactory(new Java2DRenderFactory());
		renderer.setRenderingHints(hints);
		bookmark.setRenderer(renderer);
		return bookmark;
	}

	private class TwisterWindowListener extends WindowAdapter {
		private final TwisterCanvas canvas;

		/**
		 * @param canvas
		 */
		public TwisterWindowListener(final TwisterCanvas canvas) {
			this.canvas = canvas;
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowActivated(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowActivated(final WindowEvent e) {
			canvas.requestFocus();
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowOpened(final WindowEvent e) {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					if (canvas != null) {
						if (!canvas.isStarted()) {
							try {
								canvas.acquire();
								canvas.start(config);
								canvas.startRenderers();
								canvas.release();
								canvas.refresh();
							}
							catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							panel.updateButtons();
						}
					}
				}
			}, true);
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(final WindowEvent e) {
			if (canvas != null) {
				if (canvas.isStarted()) {
					try {
						canvas.acquire();
						canvas.stopRenderers();
						canvas.stop();
						canvas.release();
						canvas.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
					panel.updateButtons();
				}
			}
			dispose();
			if (outputFrame != null) {
				outputFrame.dispose();
				outputFrame = null;
			}
			if (configFrame != null) {
				configFrame.dispose();
				configFrame = null;
			}
			if (advancedConfigFrame != null) {
				advancedConfigFrame.dispose();
				advancedConfigFrame = null;
			}
			context.removeFrame(TwisterFrame.this);
			if (context.getFrameCount() == 0) {
				service.stop();
				context.exit();
			}
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowDeiconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowDeiconified(final WindowEvent e) {
			GUIUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					if (canvas != null) {
						if (!canvas.isStarted()) {
							try {
								canvas.acquire();
								canvas.start(config);
								canvas.startRenderers();
								canvas.release();
								canvas.refresh();
							}
							catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							panel.updateButtons();
						}
					}
				}
			}, true);
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowIconified(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowIconified(final WindowEvent e) {
			if (canvas != null) {
				if (canvas.isStarted()) {
					try {
						canvas.acquire();
						canvas.stopRenderers();
						canvas.stop();
						canvas.release();
						canvas.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
					panel.updateButtons();
				}
			}
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosed(final WindowEvent e) {
		}
	}

	private RenderClipDataRow createDefaultRenderClip(final long duration) {
		final RenderClipDataRow clip = new RenderClipDataRow(new RenderClip());
		clip.setClipName("New Clip");
		clip.setDescription("");
		clip.setDuration(duration);
		return clip;
	}

	public class TwisterPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JButton editConfigButton = GUIFactory.createButton(new EditConfigAction(), TwisterSwingResources.getInstance().getString("tooltip.editConfiguration"));
		private JButton showServiceButton = GUIFactory.createButton(new ShowServiceAction(), TwisterSwingResources.getInstance().getString("tooltip.showService"));
		private JButton playSessionButton = GUIFactory.createButton(new PlaySessionAction(), TwisterSwingResources.getInstance().getString("tooltip.playSession"));
		private JButton startSessionButton = GUIFactory.createButton(new StartSessionAction(), TwisterSwingResources.getInstance().getString("tooltip.startSession"));
		private JButton stopSessionButton = GUIFactory.createButton(new StopSessionAction(), TwisterSwingResources.getInstance().getString("tooltip.stopSession"));
		private JButton suspendSessionButton = GUIFactory.createButton(new SuspendSessionAction(), TwisterSwingResources.getInstance().getString("tooltip.suspendSession"));
		private JButton resumeSessionButton = GUIFactory.createButton(new ResumeSessionAction(), TwisterSwingResources.getInstance().getString("tooltip.resumeSession"));
		private JButton storePhotoButton = GUIFactory.createButton(new StorePhotoAction(), TwisterSwingResources.getInstance().getString("tooltip.storePhoto"));
		private JButton storeMovieButton = GUIFactory.createButton(new StoreMovieAction(), TwisterSwingResources.getInstance().getString("tooltip.storeMovie"));
		private JButton undoButton = GUIFactory.createButton(new UndoAction(), TwisterSwingResources.getInstance().getString("tooltip.undo"));
		private JButton redoButton = GUIFactory.createButton(new RedoAction(), TwisterSwingResources.getInstance().getString("tooltip.redo"));
		private JButton addBookmarkButton = GUIFactory.createButton(new AddBookmarkAction(), TwisterSwingResources.getInstance().getString("tooltip.addBookmark"));
		private JButton fullScreenButton = GUIFactory.createButton(new FullScreenAction(), TwisterSwingResources.getInstance().getString("tooltip.fullScreen"));
		private JButton executeScriptButton = GUIFactory.createButton(new ExecuteScriptAction(), TwisterSwingResources.getInstance().getString("tooltip.executeScript"));

		/**
		 * @param canvas
		 */
		public TwisterPanel(final TwisterCanvas canvas) {
			setLayout(new BorderLayout());
			final JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			final JPanel panelCanvas = new JPanel(new BorderLayout());
			panelButtons.setFocusable(false);
			panelButtons.setOpaque(false);
			panelCanvas.setOpaque(false);
			setFocusable(false);
			setBackground(new Color(0x2f2f2f));
			setOpaque(true);
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/options-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/options-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/options-pressed-button.png"));
				editConfigButton = new IconButton(normalImage, pressedImage, focusedImage, new EditConfigAction());
				editConfigButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.editConfiguration"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/clips-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/clips-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/clips-pressed-button.png"));
				showServiceButton = new IconButton(normalImage, pressedImage, focusedImage, new ShowServiceAction());
				showServiceButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.showService"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-pressed-button.png"));
				startSessionButton = new IconButton(normalImage, pressedImage, focusedImage, new StartSessionAction());
				startSessionButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.startSession"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/stop-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/stop-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/stop-pressed-button.png"));
				stopSessionButton = new IconButton(normalImage, pressedImage, focusedImage, new StopSessionAction());
				stopSessionButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.stopSession"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/pause-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/pause-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/pause-pressed-button.png"));
				suspendSessionButton = new IconButton(normalImage, pressedImage, focusedImage, new SuspendSessionAction());
				suspendSessionButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.suspendSession"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/play-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/play-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/play-pressed-button.png"));
				playSessionButton = new IconButton(normalImage, pressedImage, focusedImage, new PlaySessionAction());
				playSessionButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.playSession"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/record-pressed-button.png"));
				resumeSessionButton = new IconButton(normalImage, pressedImage, focusedImage, new ResumeSessionAction());
				resumeSessionButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.resumeSession"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/photo-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/photo-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/photo-pressed-button.png"));
				storePhotoButton = new IconButton(normalImage, pressedImage, focusedImage, new StorePhotoAction());
				storePhotoButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.storePhoto"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/movie-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/movie-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/movie-pressed-button.png"));
				storeMovieButton = new IconButton(normalImage, pressedImage, focusedImage, new StoreMovieAction());
				storeMovieButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.storeMovie"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/add-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/add-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/add-pressed-button.png"));
				addBookmarkButton = new IconButton(normalImage, pressedImage, focusedImage, new AddBookmarkAction());
				addBookmarkButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.addBookmark"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/left-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/left-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/left-pressed-button.png"));
				undoButton = new IconButton(normalImage, pressedImage, focusedImage, new UndoAction());
				undoButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.undo"));
				((IconButton) undoButton).setRepeatTime(50);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/right-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/right-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/right-pressed-button.png"));
				redoButton = new IconButton(normalImage, pressedImage, focusedImage, new RedoAction());
				redoButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.redo"));
				((IconButton) redoButton).setRepeatTime(50);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/up-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/up-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/up-pressed-button.png"));
				fullScreenButton = new IconButton(normalImage, pressedImage, focusedImage, new FullScreenAction());
				fullScreenButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.fullScreen"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			try {
				final Image normalImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/script-normal-button.png"));
				final Image focusedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/script-focused-button.png"));
				final Image pressedImage = ImageIO.read(TwisterFrame.class.getResourceAsStream("/icons/script-pressed-button.png"));
				executeScriptButton = new IconButton(normalImage, pressedImage, focusedImage, new ExecuteScriptAction());
				executeScriptButton.setToolTipText(TwisterSwingResources.getInstance().getString("tooltip.executeScript"));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			panelButtons.add(editConfigButton);
			panelButtons.add(showServiceButton);
			panelButtons.add(startSessionButton);
			panelButtons.add(stopSessionButton);
			panelButtons.add(suspendSessionButton);
			panelButtons.add(resumeSessionButton);
			panelButtons.add(playSessionButton);
			panelButtons.add(storePhotoButton);
			panelButtons.add(storeMovieButton);
			panelButtons.add(undoButton);
			panelButtons.add(redoButton);
			panelButtons.add(addBookmarkButton);
			// panelButtons.add(fullScreenButton);
			panelButtons.add(executeScriptButton);
			panelCanvas.add(canvas);
			panelCanvas.setBorder(new LineBorder(Color.BLACK));
			this.add(panelButtons, BorderLayout.SOUTH);
			this.add(panelCanvas, BorderLayout.CENTER);
			model.addChangeListener(new BookmarkSelectionListener());
			updateButtons();
		}

		public void updateButtons() {
			startSessionButton.setEnabled(canStartSession());
			startSessionButton.setVisible(canStartSession());
			stopSessionButton.setEnabled(canStopSession());
			stopSessionButton.setVisible(canStopSession());
			resumeSessionButton.setEnabled(canResumeSession());
			resumeSessionButton.setVisible(canResumeSession());
			suspendSessionButton.setEnabled(canSuspendSession());
			suspendSessionButton.setVisible(canSuspendSession());
			playSessionButton.setEnabled(canPlaySession());
			playSessionButton.setVisible(canPlaySession());
			storeMovieButton.setEnabled(canStoreMovie());
			storeMovieButton.setVisible(canStoreMovie());
			storePhotoButton.setEnabled(canStorePhoto());
			storePhotoButton.setVisible(canStorePhoto());
			undoButton.setEnabled(canUndo());
			undoButton.setVisible(canUndo());
			redoButton.setEnabled(canRedo());
			redoButton.setVisible(canRedo());
			addBookmarkButton.setEnabled(canAddBookmark());
			addBookmarkButton.setVisible(canAddBookmark());
			editConfigButton.setEnabled(canEditConfig());
			editConfigButton.setVisible(canEditConfig());
			executeScriptButton.setEnabled(canExecuteScript());
			executeScriptButton.setVisible(canExecuteScript());
		}

		/**
		 * @return
		 */
		private boolean isPlayState() {
			return canvas.getState() == TwisterCanvas.STATE_PLAY;
		}

		/**
		 * @return
		 */
		private boolean isEditState() {
			return canvas.getState() == TwisterCanvas.STATE_EDIT;
		}

		/**
		 * @return
		 */
		private boolean isScriptState() {
			return canvas.getState() == TwisterCanvas.STATE_SCRIPT;
		}

		/**
		 * @return
		 */
		private boolean canStorePhoto() {
			return isEditState() || isPlayState() || isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canStoreMovie() {
			return (tmpClip != null) && isEditState();
		}

		/**
		 * @return
		 */
		private boolean canPlaySession() {
			return ((clip == null) && (tmpClip != null) && isEditState()) || (((playClip == null) && isPlayState()) && !isScriptState());
		}

		/**
		 * @return
		 */
		private boolean canSuspendSession() {
			return ((clip != null) && (sequence != null) && isEditState()) || (((playClip != null) && isPlayState()) && !isScriptState());
		}

		/**
		 * @return
		 */
		private boolean canResumeSession() {
			return (clip != null) && (sequence == null) && isEditState() && !isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canStopSession() {
			return ((clip != null) && isEditState()) || (isPlayState() && !isScriptState());
		}

		/**
		 * @return
		 */
		private boolean canStartSession() {
			return (clip == null) && isEditState() && !isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canUndo() {
			return isEditState() && !isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canRedo() {
			return isEditState() && !isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canAddBookmark() {
			return isEditState();
		}

		/**
		 * @return
		 */
		private boolean canEditConfig() {
			return isEditState() && !isScriptState();
		}

		/**
		 * @return
		 */
		private boolean canExecuteScript() {
			return isEditState() && !isScriptState();
		}

		protected class EditConfigAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public EditConfigAction() {
				super(TwisterSwingResources.getInstance().getString("action.editConfig"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				openConfigWindow(config, canvas);
			}
		}

		protected class ShowServiceAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public ShowServiceAction() {
				super(TwisterSwingResources.getInstance().getString("action.showService"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				openServiceWindow();
			}
		}

		protected class StartSessionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public StartSessionAction() {
				super(TwisterSwingResources.getInstance().getString("action.startSession"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canStartSession()) {
					tree = new DefaultTree();
					clip = new TwisterClip();
					sequence = new TwisterSequence();
					sequence.setInitialConfig(config.clone());
					final TwisterConfigNodeBuilder builder = new TwisterConfigNodeBuilder(config);
					builder.createNodes(tree.getRootNode());
					tree.getRootNode().setContext(config.getContext());
					tree.getRootNode().setSession(new DefaultNodeSession("clip " + clip.getSequenceCount()));
					canvas.setSymbol(TwisterCanvas.SYMBOL_RECORD);
					sequenceStartTime = System.currentTimeMillis();
					clipDuration = 0;
				}
				updateButtons();
			}
		}

		protected class StopSessionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public StopSessionAction() {
				super(TwisterSwingResources.getInstance().getString("action.stopSession"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (isPlayState()) {
					try {
						canvas.acquire();
						canvas.stopRenderers();
						canvas.stop();
						playClip = null;
						sequence = null;
						clip = null;
						tree = null;
						config.getContext().updateTimestamp();
						config.setFrameConfigElement(canvas.getConfig().getFrameConfigElement().clone());
						config.setEffectConfigElement(canvas.getConfig().getEffectConfigElement().clone());
						config.setBackground(canvas.getConfig().getBackground());
						canvas.start(config);
						canvas.startRenderers();
						canvas.release();
						canvas.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
				else if (canStopSession()) {
					if (sequence != null) {
						sequenceStopTime = System.currentTimeMillis();
						final List<NodeAction> nodeActions = tree.getRootNode().getSession().getActions();
						sequence.setFinalConfig(config.clone());
						for (final NodeAction nodeAction : nodeActions) {
							final NodeActionValue value = nodeAction.toActionValue();
							value.setTimestamp(value.getTimestamp() - sequenceStartTime);
							final NodeAction action = new NodeAction(value);
							sequence.addAction(action);
						}
						sequence.setDuration(sequenceStopTime - sequenceStartTime);
						clipDuration += sequence.getDuration();
						clip.addSequence(sequence);
					}
					tmpClip = clip;
					sequence = null;
					clip = null;
					tree = null;
					canvas.setSymbol(TwisterCanvas.SYMBOL_NONE);
				}
				updateButtons();
			}
		}

		protected class SuspendSessionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public SuspendSessionAction() {
				super(TwisterSwingResources.getInstance().getString("action.suspendSession"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canSuspendSession()) {
					if (isEditState()) {
						sequenceStopTime = System.currentTimeMillis();
						final List<NodeAction> nodeActions = tree.getRootNode().getSession().getActions();
						sequence.setFinalConfig(config.clone());
						for (final NodeAction nodeAction : nodeActions) {
							final NodeActionValue value = nodeAction.toActionValue();
							value.setTimestamp(value.getTimestamp() - sequenceStartTime);
							sequence.addAction(new NodeAction(value));
						}
						sequence.setDuration(sequenceStopTime - sequenceStartTime);
						clipDuration += sequence.getDuration();
						clip.addSequence(sequence);
						sequence = null;
						tree = null;
						canvas.setSymbol(TwisterCanvas.SYMBOL_PAUSE);
					}
					else if (isPlayState()) {
						canvas.setSymbol(TwisterCanvas.SYMBOL_PAUSE);
						canvas.suspend();
						playClip = null;
					}
				}
				updateButtons();
			}
		}

		protected class ResumeSessionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public ResumeSessionAction() {
				super(TwisterSwingResources.getInstance().getString("action.resumeSession"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canResumeSession()) {
					sequence = new TwisterSequence();
					sequence.setInitialConfig(config.clone());
					tree = new DefaultTree();
					final TwisterConfigNodeBuilder builder = new TwisterConfigNodeBuilder(config);
					builder.createNodes(tree.getRootNode());
					tree.getRootNode().setContext(config.getContext());
					tree.getRootNode().setSession(new DefaultNodeSession("clip " + clip.getSequenceCount()));
					canvas.setSymbol(TwisterCanvas.SYMBOL_RECORD);
					sequenceStartTime = System.currentTimeMillis();
				}
				updateButtons();
			}
		}

		protected class PlaySessionAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public PlaySessionAction() {
				super(TwisterSwingResources.getInstance().getString("action.playSession"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canPlaySession()) {
					if (configFrame != null) {
						configFrame.dispose();
						configFrame = null;
					}
					if (advancedConfigFrame != null) {
						advancedConfigFrame.dispose();
						advancedConfigFrame = null;
					}
					try {
						canvas.acquire();
						if (canvas.isSuspended()) {
							playClip = tmpClip;
							canvas.setSymbol(TwisterCanvas.SYMBOL_PLAY);
							canvas.resume();
						}
						else {
							canvas.stopRenderers();
							canvas.stop();
							playClip = tmpClip;
							canvas.start(playClip);
							canvas.startRenderers();
						}
						canvas.release();
						canvas.refresh();
					}
					catch (InterruptedException x) {
						Thread.currentThread().interrupt();
					}
				}
				updateButtons();
			}
		}

		protected class StorePhotoAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public StorePhotoAction() {
				super(TwisterSwingResources.getInstance().getString("action.storePhoto"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canStorePhoto()) {
					if (isEditState()) {
						if (JOptionPane.showConfirmDialog(TwisterPanel.this, TwisterSwingResources.getInstance().getString("message.confirmSavePhoto"), TwisterSwingResources.getInstance().getString("label.savePhoto"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							final RenderClipDataRow clip = createDefaultRenderClip(0);
							service.execute(new ServiceCallback<Object>() {
								@Override
								public void executed(final Object value) {
									semaphore.release();
								}

								@Override
								public void failed(final Throwable throwable) {
									TwisterFrame.logger.log(Level.WARNING, "Can't create the clip", throwable);
									semaphore.release();
								}

								@Override
								public Object execute(final LibraryService service) throws Exception {
									final TwisterSequence sequence = new TwisterSequence();
									sequence.setInitialConfig(config.clone());
									sequence.setFinalConfig(config.clone());
									final TwisterClip twisterClip = new TwisterClip();
									twisterClip.addSequence(sequence);
									service.createClip(clip, twisterClip);
									return null;
								}
							});
							try {
								semaphore.acquire();
							}
							catch (final InterruptedException x) {
								x.printStackTrace();
							}
						}
					}
					else if (isPlayState() || isScriptState()) {
						final RenderClipDataRow clip = createDefaultRenderClip(0);
						service.execute(new ServiceCallback<Object>() {
							@Override
							public void executed(final Object value) {
								semaphore.release();
							}

							@Override
							public void failed(final Throwable throwable) {
								TwisterFrame.logger.log(Level.WARNING, "Can't create the clip", throwable);
								semaphore.release();
							}

							@Override
							public Object execute(final LibraryService service) throws Exception {
								final TwisterSequence sequence = new TwisterSequence();
								canvas.acquire();
								final TwisterConfig config = canvas.getConfig();
								sequence.setInitialConfig(config);
								sequence.setFinalConfig(config);
								final TwisterClip twisterClip = new TwisterClip();
								twisterClip.addSequence(sequence);
								service.createClip(clip, twisterClip);
								canvas.release();
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		}

		protected class StoreMovieAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public StoreMovieAction() {
				super(TwisterSwingResources.getInstance().getString("action.storeMovie"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (canStoreMovie()) {
					if (JOptionPane.showConfirmDialog(TwisterPanel.this, TwisterSwingResources.getInstance().getString("message.confirmSaveMovie"), TwisterSwingResources.getInstance().getString("label.saveMovie"), JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						final TwisterClipController controller = new TwisterClipController(tmpClip);
						controller.init();
						logger.fine("Clip duration = " + clipDuration + ", controller duration = " + controller.getDuration());
						final RenderClipDataRow clip = createDefaultRenderClip(controller.getDuration());
						service.execute(new ServiceCallback<Object>() {
							@Override
							public void executed(final Object value) {
								semaphore.release();
							}

							@Override
							public void failed(final Throwable throwable) {
								TwisterFrame.logger.log(Level.WARNING, "Can't create the clip", throwable);
								semaphore.release();
							}

							@Override
							public Object execute(final LibraryService service) throws Exception {
								service.createClip(clip, tmpClip);
								return null;
							}
						});
						try {
							semaphore.acquire();
						}
						catch (final InterruptedException x) {
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		}

		protected class UndoAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public UndoAction() {
				super(TwisterSwingResources.getInstance().getString("action.undo"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				canvas.submitCommand(new Runnable() {
					/**
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						if (sessionController != null) {
							sessionController.undoAction(true);
						}
					}
				});
			}
		}

		protected class RedoAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public RedoAction() {
				super(TwisterSwingResources.getInstance().getString("action.redo"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				canvas.submitCommand(new Runnable() {
					/**
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						if (sessionController != null) {
							sessionController.redoAction(true);
						}
					}
				});
			}
		}

		protected class AddBookmarkAction extends AbstractAction {
			private static final long serialVersionUID = 1L;

			public AddBookmarkAction() {
				super(TwisterSwingResources.getInstance().getString("action.addBookmark"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					canvas.addBookmark(createBookmark());
				}
				catch (final Exception x) {
					x.printStackTrace();
				}
			}
		}

		protected class FullScreenAction extends AbstractAction {
			private static final long serialVersionUID = 1L;
			private boolean isFullScreen;

			public FullScreenAction() {
				super(TwisterSwingResources.getInstance().getString("action.fullScreen"));
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				if (!GraphicsEnvironment.isHeadless()) {
					final GraphicsDevice device = environment.getDefaultScreenDevice();
					if (device.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
						if (device.isFullScreenSupported()) {
							try {
								canvas.acquire();
								canvas.stopRenderers();
								canvas.stop();
								if (!isFullScreen) {
									device.setFullScreenWindow(TwisterFrame.this);
									isFullScreen = true;
									if (device.isDisplayChangeSupported()) {
										device.setDisplayMode(new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN));
									}
								}
								else {
									device.setFullScreenWindow(null);
									isFullScreen = false;
								}
								canvas.start(config);
								canvas.startRenderers();
								canvas.release();
							}
							catch (InterruptedException x) {
								Thread.currentThread().interrupt();
							}
						}
					}
				}
			}
		}

		protected class ExecuteScriptAction extends AbstractAction {
			private static final long serialVersionUID = 1L;
			private JFileChooser fileChooser = new JFileChooser();

			public ExecuteScriptAction() {
				super(TwisterSwingResources.getInstance().getString("action.executeScript"));
				fileChooser.setDialogTitle(TwisterSwingResources.getInstance().getString("label.selectScript"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(new JSFileFilter());
			}

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (scriptThread == null) {
					File file = selectFile();
					if (file != null) {
						scriptThread = new Thread(new ScriptRunnable(file), "Script Thread");
						scriptThread.setPriority(Thread.NORM_PRIORITY);
						scriptThread.setDaemon(true);
						scriptThread.start();
					}
				}
			}

			private File selectFile() {
				File file = null;
				final int returnVal = fileChooser.showOpenDialog(new JFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile().getAbsoluteFile().getAbsoluteFile();
				}
				else {
					file = null;
				}
				return file;
			}

			private class ScriptRunnable implements Runnable {
				private File file;

				/**
				 * @param file
				 */
				public ScriptRunnable(File file) {
					this.file = file;
				}

				@Override
				public void run() {
					GUIUtil.executeTask(new Runnable() {
						@Override
						public void run() {
							try {
								if (configFrame != null) {
									configFrame.dispose();
									configFrame = null;
								}
								if (advancedConfigFrame != null) {
									advancedConfigFrame.dispose();
									advancedConfigFrame = null;
								}
								canvas.acquire();
								canvas.stopRenderers();
								canvas.startScript();
								canvas.startRenderers();
								canvas.release();
								canvas.refresh();
								updateButtons();
								openLogWindow();
								outputFrame.clear();
								canvas.showMessage(TwisterSwingResources.getInstance().getString("message.clickToInterrupt"), 2.5f, 5, 95, 2000, true);
							}
							catch (InterruptedException x) {
								Thread.currentThread().interrupt();
							}
						}
					}, false);
					try {
						JSManager.execute(canvas, new TwisterJSContext(), twisterTree.getRootNode(), file.getParentFile(), file);
					}
					catch (final Exception x) {
						x.printStackTrace();
						final ByteArrayOutputStream baos = new ByteArrayOutputStream();
						PrintStream ps = new PrintStream(baos);
						x.printStackTrace(ps);
						ps.close();
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								showLogWindow();
								outputFrame.append(baos.toString());
								// JOptionPane.showMessageDialog(new JFrame(), x.toString(), TwisterSwingResources.getInstance().getString("error.scriptFailed"), JOptionPane.ERROR_MESSAGE);
							}
						}, false);
					}
					finally {
						GUIUtil.executeTask(new Runnable() {
							@Override
							public void run() {
								try {
									canvas.acquire();
									canvas.stopRenderers();
									canvas.stopScript();
									canvas.startRenderers();
									canvas.release();
									canvas.refresh();
									updateButtons();
									scriptThread = null;
								}
								catch (InterruptedException x) {
									Thread.currentThread().interrupt();
								}
							}
						}, false);
					}
				}
			}

			private class TwisterJSContext extends DefaultJSContext {
				/**
				 * @see com.nextbreakpoint.nextfractal.core.scripting.DefaultJSContext#println(java.lang.String)
				 */
				@Override
				public void println(final String s) {
					GUIUtil.executeTask(new Runnable() {
						@Override
						public void run() {
							showLogWindow();
							outputFrame.append(s);
							logger.info(s);
						}
					}, true);
				}

				/**
				 * @see com.nextbreakpoint.nextfractal.core.scripting.DefaultJSContext#showMessage(java.lang.String, float, float, float, long, boolean)
				 */
				@Override
				public void showMessage(final String message, final float size, final float x, final float y, final long time, final boolean hasBackground) {
					GUIUtil.executeTask(new Runnable() {
						@Override
						public void run() {
							// TwisterFrame.this.toFront();
							canvas.showMessage(message, size, x, y, time, hasBackground);
						}
					}, true);
				}

				/**
				 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#loadDefaultConfig()
				 */
				@Override
				public void loadDefaultConfig() {
					try {
						final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
						final TwisterConfig tmpConfig = configBuilder.createDefaultConfig();
						canvas.acquire();
						canvas.stopRenderers();
						config.getContext().updateTimestamp();
						config.setFrameConfigElement(tmpConfig.getFrameConfigElement().clone());
						config.setEffectConfigElement(tmpConfig.getEffectConfigElement().clone());
						config.setBackground(tmpConfig.getBackground());
						canvas.startRenderers();
						canvas.release();
						canvas.refresh();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		private class BookmarkSelectionListener implements ChangeListener {
			/**
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			@Override
			public void stateChanged(final ChangeEvent e) {
				try {
					final TwisterBookmark bookmark = canvas.getSelectedBookmark();
					if (bookmark != null) {
						canvas.acquire();
						canvas.stopRenderers();
						config.getContext().updateTimestamp();
						config.setFrameConfigElement(bookmark.getConfig().getFrameConfigElement().clone());
						config.setEffectConfigElement(bookmark.getConfig().getEffectConfigElement().clone());
						canvas.startRenderers();
						canvas.release();
						canvas.refresh();
					}
					updateButtons();
				}
				catch (InterruptedException x) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	private class DefaulServiceContext implements ServiceContext {
		private final TwisterContext context;
		private final RenderService service;
		private final int vcells;
		private final int hcells;

		/**
		 * @param context
		 * @param service
		 * @param vcells
		 * @param hcells
		 */
		public DefaulServiceContext(final TwisterContext context, final RenderService service, final int vcells, final int hcells) {
			this.context = context;
			this.service = service;
			this.vcells = vcells;
			this.hcells = hcells;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#openClip(com.nextbreakpoint.nextfractal.twister.TwisterClip)
		 */
		@Override
		public void openClip(final TwisterClip clip) {
			if (clip != null) {
				try {
					final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
					final TwisterConfig config = configBuilder.createDefaultConfig();
					config.setContext(new DefaultConfigContext());
					final TwisterFrame frame = new TwisterFrame(context, service, config, hcells, vcells);
					frame.setVisible(true);
					GUIUtil.executeTask(new Runnable() {
						@Override
						public void run() {
							frame.loadClip(clip);
						}
					}, true);
				}
				catch (final ExtensionException x) {
					x.printStackTrace();
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#removeFrame(javax.swing.JFrame)
		 */
		@Override
		public void removeFrame(final JFrame frame) {
			context.removeFrame(frame);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#addFrame(javax.swing.JFrame)
		 */
		@Override
		public void addFrame(final JFrame frame) {
			context.addFrame(frame);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#getFrameCount()
		 */
		@Override
		public int getFrameCount() {
			return context.getFrameCount();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#exit()
		 */
		@Override
		public void exit() {
			context.exit();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.service.swing.ServiceContext#restart()
		 */
		@Override
		public void restart() {
			context.restart();
		}
	}

	private class DefaultTwisterConfigContext implements TwisterConfigContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterConfigContext#openAdvancedConfigWindow()
		 */
		@Override
		public void openAdvancedConfigWindow() {
			TwisterFrame.this.openAdvancedConfigWindow(config, canvas);
		}
	}
}
