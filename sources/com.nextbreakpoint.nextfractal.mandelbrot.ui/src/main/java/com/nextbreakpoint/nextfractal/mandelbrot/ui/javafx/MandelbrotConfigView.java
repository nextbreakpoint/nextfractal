package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;

public class MandelbrotConfigView extends View {

	public MandelbrotConfigView(MandelbrotConfig config, ViewContext viewContext, RenderContext context, NodeSession session) {
		VBox pane = new VBox(10);
		getChildren().add(pane);
		pane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		pane.setPrefHeight(viewContext.getConfigViewSize().getHeight());
		GridPane incolouringFormulaPane = new GridPane();
		incolouringFormulaPane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		incolouringFormulaPane.setMinHeight(70);
		incolouringFormulaPane.setPadding(new Insets(10));
		incolouringFormulaPane.setHgap(10);
		incolouringFormulaPane.setVgap(10);
		ScrollPane incolouringScrollPane = new ScrollPane(incolouringFormulaPane);
		incolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		incolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(incolouringScrollPane);
		GridPane outcolouringFormulaPane = new GridPane();
		outcolouringFormulaPane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		outcolouringFormulaPane.setMinHeight(70);
		outcolouringFormulaPane.setPadding(new Insets(10));
		outcolouringFormulaPane.setHgap(10);
		outcolouringFormulaPane.setVgap(10);
		ScrollPane outcolouringScrollPane = new ScrollPane(outcolouringFormulaPane);
		outcolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		outcolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(outcolouringScrollPane);
		for (int i = 0; i < config.getMandelbrotFractal().getIncolouringFormulaConfigElementCount(); i++) {
			IncolouringFormulaConfigElement element = config.getMandelbrotFractal().getIncolouringFormulaConfigElement(i);
			String name = element.getExtensionElement().getReference().getExtensionName();
			GridItem item = new GridItem(new GridItemModel() {
			});
			incolouringFormulaPane.getChildren().add(item);
			GridPane.setConstraints(item, i, 0);
			item = new GridItem(new GridItemModel() {
			});
			incolouringFormulaPane.getChildren().add(item);
			GridPane.setConstraints(item, i + 1, 0);
			item = new GridItem(new GridItemModel() {
			});
			incolouringFormulaPane.getChildren().add(item);
			GridPane.setConstraints(item, i + 2, 0);
			item = new GridItem(new GridItemModel() {
			});
			incolouringFormulaPane.getChildren().add(item);
			GridPane.setConstraints(item, i + 3, 0);
			item = new GridItem(new GridItemModel() {
			});
			incolouringFormulaPane.getChildren().add(item);
			GridPane.setConstraints(item, i + 4, 0);
		}
		for (int i = 0; i < config.getMandelbrotFractal().getOutcolouringFormulaConfigElementCount(); i++) {
			OutcolouringFormulaConfigElement element = config.getMandelbrotFractal().getOutcolouringFormulaConfigElement(i);
			String name = element.getExtensionElement().getReference().getExtensionName();
			GridItem item = new GridItem(new GridItemModel() {
			});
			outcolouringFormulaPane.getChildren().add(item);
		}
	}

	@Override
	public void dispose() {
	}

	public interface GridItemModel {
	}
	
	public class GridItem extends Pane {
		private GridItemModel model;
		
		public GridItem(GridItemModel model) {
			this.model = model;
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
			setStyle("-fx-background-color:#0000ff");
		}
	}
	
	public class GridItem2 extends Pane {
		private GridItemModel model;
		
		public GridItem2(GridItemModel model) {
			this.model = model;
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
			setStyle("-fx-background-color:#ff00ff");
		}
	}
}
