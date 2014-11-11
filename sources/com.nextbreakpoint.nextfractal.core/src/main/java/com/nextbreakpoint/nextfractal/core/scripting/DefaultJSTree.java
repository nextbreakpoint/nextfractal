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
package com.nextbreakpoint.nextfractal.core.scripting;

import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

/**
 * @author Andrea Medeghini
 */
public class DefaultJSTree implements JSTree {
	private final RenderContext renderContext;
	private final JSNode jsRootNode;
	private final NodeObject rootNode;

	/**
	 * @param renderContext
	 * @param jsContext
	 * @param rootNode
	 */
	public DefaultJSTree(final RenderContext renderContext, final NodeObject rootNode) {
		this.renderContext = renderContext;
		this.rootNode = rootNode;
		jsRootNode = new DefaultJSNode(rootNode);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#accept()
	 */
	@Override
	public void accept() {
		try {
			renderContext.acquire();
			rootNode.getContext().updateTimestamp();
			rootNode.accept();
			renderContext.release();
			renderContext.refresh();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#cancel()
	 */
	@Override
	public void cancel() {
		rootNode.cancel();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#refresh()
	 */
	@Override
	public void refresh() {
		renderContext.refresh();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#redraw()
	 */
	@Override
	public void redraw() {
		try {
			renderContext.acquire();
			renderContext.stopRenderers();
			renderContext.startRenderers();
			renderContext.release();
			renderContext.refresh();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#getRootNode()
	 */
	@Override
	public JSNode getRootNode() {
		return jsRootNode;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSTree#dump()
	 */
	@Override
	public String dump() {
		return rootNode.dump();
	}
}
