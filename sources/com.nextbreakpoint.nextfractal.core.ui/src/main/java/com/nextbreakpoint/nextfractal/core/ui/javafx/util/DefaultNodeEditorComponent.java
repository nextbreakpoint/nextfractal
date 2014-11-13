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
package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIResources;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

/**
 * @author Andrea Medeghini
 */
public class DefaultNodeEditorComponent extends AnchorPane {
	/**
	 * @param viewContext 
	 * @param nodeEditor
	 */
	public DefaultNodeEditorComponent(ViewContext viewContext, RenderContext context, NodeObject node) {
		Label label = new Label(node.getNodeLabel());
		Button edit = new Button(CoreUIResources.getInstance().getString("action.edit"));
		edit.setOnAction(e -> { 
			DefaultView view = new DefaultView(viewContext, context, node);
			viewContext.showConfigView(view); 
		});
		AnchorPane.setLeftAnchor(label, 0.0);
		AnchorPane.setRightAnchor(edit, 0.0);
		getChildren().add(label);
		getChildren().add(edit);
		setPrefWidth(viewContext.getConfigViewSize().getWidth());
	}
}
