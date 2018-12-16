/*
 * NextFractal 2.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.core.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import javafx.scene.layout.Pane;

public interface UIFactory {
	/**
	 * @return
	 */
	public String getId();

	/**
	 * @param eventBus
     * @param session
     * @return
	 */
	public Pane createEditorPane(EventBus eventBus, Session session);

	/**
	 * @param eventBus
	 * @param session
	 * @param width
	 * @param height
	 * @return
	 */
	public Pane createRenderPane(EventBus eventBus, Session session, int width, int height);

	/**
	 * @param eventBus
	 * @param session
	 * @return
	 */
	public Pane createParamsPane(EventBus eventBus, Session session);

	public GridItemRenderer createRenderer(Bitmap bitmap) throws Exception;

	public BrowseBitmap createBitmap(Session session, RendererSize size) throws Exception;

	/**
	 * @param resourceName
	 * @return
	 */
	public Try<String, Exception> loadResource(String resourceName);
}
