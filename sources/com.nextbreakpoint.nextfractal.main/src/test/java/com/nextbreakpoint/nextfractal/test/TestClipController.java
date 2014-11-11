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
package com.nextbreakpoint.nextfractal.test;

import java.util.List;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.runtime.ConfigContext;
import com.nextbreakpoint.nextfractal.core.runtime.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.model.DefaultRootNode;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeActionValue;
import com.nextbreakpoint.nextfractal.twister.ControllerListener;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;

/**
 * @author Andrea Medeghini
 */
public class TestClipController {
	@Test
	public void testUndoRedoAction() throws Exception {
		final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
		final ConfigContext context = new DefaultConfigContext();
		final TwisterConfig config = configBuilder.createDefaultConfig();
		final TwisterConfigNodeBuilder builder = new TwisterConfigNodeBuilder(config);
		config.setContext(context);
		DefaultRootNode rootNode = null;
		TwisterSequence sequence = null;
		List<NodeAction> actions = null;
		TwisterClipController controller = null;
		long refTimestamp = 0;
		long time = 0;
		final TwisterClip clip = new TwisterClip();
		sequence = new TwisterSequence();
		sequence.setInitialConfig(config.clone());
		sequence.setFinalConfig(config.clone());
		time = System.currentTimeMillis();
		time = System.currentTimeMillis() - time;
		sequence.setDuration(time);
		clip.addSequence(sequence);
		rootNode = new DefaultRootNode();
		builder.createNodes(rootNode);
		rootNode.setContext(context);
		rootNode.setSession(new DefaultNodeSession("clip"));
		time = System.currentTimeMillis();
		sequence = new TwisterSequence();
		sequence.setInitialConfig(config.clone());
		refTimestamp = time;
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(false);
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(true);
		Thread.sleep(500);
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(false);
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(true);
		Thread.sleep(1000);
		sequence.setFinalConfig(config.clone());
		time = System.currentTimeMillis() - time;
		sequence.setDuration(time);
		clip.addSequence(sequence);
		actions = rootNode.getSession().getActions();
		for (NodeAction action : actions) {
			final NodeActionValue value = action.toActionValue();
			value.setTimestamp(value.getTimestamp() - refTimestamp);
			action = new NodeAction(value);
			sequence.addAction(action);
			System.out.println(action);
		}
		rootNode = new DefaultRootNode();
		builder.createNodes(rootNode);
		rootNode.setContext(context);
		rootNode.setSession(new DefaultNodeSession("clip"));
		time = System.currentTimeMillis();
		sequence = new TwisterSequence();
		sequence.setInitialConfig(config.clone());
		refTimestamp = time;
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(false);
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(true);
		Thread.sleep(500);
		context.updateTimestamp();
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(false);
		Thread.sleep(500);
		config.getFrameConfigElement().getLayerConfigElement(0).setVisible(true);
		Thread.sleep(1000);
		sequence.setFinalConfig(config.clone());
		time = System.currentTimeMillis() - time;
		sequence.setDuration(time);
		clip.addSequence(sequence);
		actions = rootNode.getSession().getActions();
		for (NodeAction action : actions) {
			final NodeActionValue value = action.toActionValue();
			value.setTimestamp(value.getTimestamp() - refTimestamp);
			action = new NodeAction(value);
			sequence.addAction(action);
			System.out.println(action);
		}
		sequence = new TwisterSequence();
		sequence.setInitialConfig(config.clone());
		sequence.setFinalConfig(config.clone());
		time = System.currentTimeMillis();
		time = System.currentTimeMillis() - time;
		sequence.setDuration(time);
		clip.addSequence(sequence);
		controller = new DebugClipConroller(clip);
		controller.init();
		System.out.println("controller.redoAction(true)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			redoAction(controller, time, true);
		}
		controller.redoAll();
		System.out.println("controller.undoAction(true)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			undoAction(controller, time, true);
		}
		controller.init();
		System.out.println("controller.redoAction(false)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			redoAction(controller, time, false);
		}
		controller.redoAll();
		System.out.println("controller.undoAction(false)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			undoAction(controller, time, false);
		}
		controller.init();
		System.out.println("controller.redoActionAndSleep()");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			redoActionAndSleep(controller, time);
		}
		controller.redoAll();
		System.out.println("controller.undoActionAndSleep()");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			undoActionAndSleep(controller, time);
		}
		controller.init();
		System.out.println("controller.redoAction(500)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			redoAction(controller, time, 500);
		}
		controller.redoAll();
		System.out.println("controller.undoAction(500)");
		time = System.currentTimeMillis();
		for (int i = 0; i < 12; i++) {
			undoAction(controller, time, 500);
		}
		// controller.init();
		// System.out.println("controller.undoAll()");
		// controller.undoAll();
		// System.out.println("controller.redoAll()");
		// controller.redoAll();
	}

	private void print(final TwisterClipController controller, final long time, final long tvalue, final boolean value) {
		System.out.println((System.currentTimeMillis() - time) + ": " + (value ? tvalue : "-"));
	}

	private void redoAction(final TwisterClipController controller, final long time, final long timestamp) {
		final boolean value = controller.redoAction(timestamp, true);
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private void undoAction(final TwisterClipController controller, final long time, final long timestamp) {
		final boolean value = controller.undoAction(timestamp, true);
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private void redoActionAndSleep(final TwisterClipController controller, final long time) {
		final boolean value = controller.redoActionAndSleep();
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private void undoActionAndSleep(final TwisterClipController controller, final long time) {
		final boolean value = controller.undoActionAndSleep();
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private void redoAction(final TwisterClipController controller, final long time, final boolean sameTimestamp) {
		final boolean value = controller.redoAction(sameTimestamp);
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private void undoAction(final TwisterClipController controller, final long time, final boolean sameTimestamp) {
		final boolean value = controller.undoAction(sameTimestamp);
		final long tvalue = controller.getTime();
		print(controller, time, tvalue, value);
	}

	private class DebugClipConroller extends TwisterClipController {
		public DebugClipConroller(final TwisterClip clip) {
			super(clip);
			addControllerListener(new ControllerListener() {
				@Override
				public void actionRedone(final NodeAction action) {
					System.out.println("Redo: " + action);
				}

				@Override
				public void actionUndone(final NodeAction action) {
					System.out.println("Undo: " + action);
				}

				@Override
				public void configChanged() {
					System.out.println("Config changed");
				}
			});
		}
	}
}
