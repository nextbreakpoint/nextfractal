/*
 * NextFractal 2.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
import com.nextbreakpoint.nextfractal.core.common.ParamsStrategy;
import com.nextbreakpoint.nextfractal.core.common.ParserStrategy;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import javafx.scene.layout.Pane;

public interface UIFactory {
	/**
	 * @return
	 */
	String getId();

	/**
	 * @param eventBus
	 * @param session
	 * @param width
	 * @param height
	 * @return
	 */
	Pane createRenderPane(PlatformEventBus eventBus, Session session, int width, int height);

	/**
	 * @param bitmap
	 * @return
	 * @throws Exception
	 */
	GridItemRenderer createRenderer(Bitmap bitmap) throws Exception;

	BrowseBitmap createBitmap(Session session, RendererSize size) throws Exception;

	/**
	 * @param resourceName
	 * @return
	 */
	Try<String, Exception> loadResource(String resourceName);

	/**
	 * @return
	 */
	ParserStrategy createParserStrategy();

	/**
	 * @return
	 */
	ParamsStrategy createParamsStrategy();
}
