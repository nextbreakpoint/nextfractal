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
package com.nextbreakpoint.nextfractal.contextfree.ui.swing.extensions.view;

import com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageConfig;
import com.nextbreakpoint.nextfractal.contextfree.ui.swing.ContextFreeConfigPanel;
import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ViewPanel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeImageConfigViewRuntime extends ViewExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext, com.nextbreakpoint.nextfractal.core.RenderContext, com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession)
	 */
	@Override
	public ViewPanel createView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		return new ContextFreeConfigPanel(((ContextFreeImageConfig) config).getContextFreeConfig(), viewContext, context, session);
	}
}
