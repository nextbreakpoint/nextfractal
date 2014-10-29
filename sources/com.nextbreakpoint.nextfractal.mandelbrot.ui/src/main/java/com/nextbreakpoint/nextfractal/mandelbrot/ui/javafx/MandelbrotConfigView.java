package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
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
		GridPane incolouringFormulaPane = new IncolouringFormulaGridItems(viewContext, config);
		ScrollPane incolouringScrollPane = new ScrollPane(incolouringFormulaPane);
		incolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		incolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(incolouringScrollPane);
		GridPane outcolouringFormulaPane = new OutcolouringFormulaGridItems(viewContext, config);
		ScrollPane outcolouringScrollPane = new ScrollPane(outcolouringFormulaPane);
		outcolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		outcolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(outcolouringScrollPane);
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
	
	public class GridItemAdd extends Pane {
		public GridItemAdd() {
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
			setStyle("-fx-background-color:#ff0000");
		}
	}
	
	public abstract class GridItems<T extends ConfigElement> extends GridPane {
		private MandelbrotConfig config;

		public GridItems(ViewContext viewContext, MandelbrotConfig config) {
			setPrefWidth(viewContext.getConfigViewSize().getWidth());
			setMinHeight(70);
			setPadding(new Insets(10));
			setHgap(10);
			setVgap(10);
			for (int i = 0; i < getElementCount(config); i++) {
				T element = getElement(config, i);
				String name = getElementName(element);
				GridItem item = new GridItem(new GridItemModel() {
				});
				getChildren().add(item);
				GridPane.setConstraints(item, i % 4, i / 4);
			}
			{
				GridItemAdd item = new GridItemAdd();
				getChildren().add(item);
				GridPane.setConstraints(item, getElementCount(config) % 4, getElementCount(config) / 4);
				item.setOnMouseClicked(e -> {
					T element = createElement();
					appendElement(config, element);
					GridPane.setConstraints(item, getElementCount(config) % 4, getElementCount(config) / 4);
					GridItem newItem = new GridItem(new GridItemModel() {
					});
					getChildren().add(newItem);
					GridPane.setConstraints(newItem, (getElementCount(config) - 1) % 4, (getElementCount(config) - 1) / 4);
				});
			}
		}

		protected MandelbrotConfig getConfig() {
			return config;
		}
		
		protected abstract void appendElement(MandelbrotConfig config, T element);

		protected abstract T createElement();

		protected abstract T getElement(MandelbrotConfig config, int index);

		protected abstract int getElementCount(MandelbrotConfig config);

		protected abstract String getElementName(T element);
	}

	public class IncolouringFormulaGridItems extends GridItems<IncolouringFormulaConfigElement> {
		public IncolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(viewContext, config);
		}

		protected void appendElement(MandelbrotConfig config, IncolouringFormulaConfigElement element) {
			config.getMandelbrotFractal().appendIncolouringFormulaConfigElement(element);
		}

		protected IncolouringFormulaConfigElement createElement() {
			return new IncolouringFormulaConfigElement();
		}

		protected IncolouringFormulaConfigElement getElement(MandelbrotConfig config, int index) {
			return config.getMandelbrotFractal().getIncolouringFormulaConfigElement(index);
		}

		protected int getElementCount(MandelbrotConfig config) {
			return config.getMandelbrotFractal().getIncolouringFormulaConfigElementCount();
		}

		protected String getElementName(IncolouringFormulaConfigElement element) {
			return element.getExtensionElement().getReference().getExtensionName();
		}
	}

	public class OutcolouringFormulaGridItems extends GridItems<OutcolouringFormulaConfigElement> {
		public OutcolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(viewContext, config);
		}

		protected void appendElement(MandelbrotConfig config, OutcolouringFormulaConfigElement element) {
			config.getMandelbrotFractal().appendOutcolouringFormulaConfigElement(element);
		}

		protected OutcolouringFormulaConfigElement createElement() {
			return new OutcolouringFormulaConfigElement();
		}

		protected OutcolouringFormulaConfigElement getElement(MandelbrotConfig config, int index) {
			return config.getMandelbrotFractal().getOutcolouringFormulaConfigElement(index);
		}

		protected int getElementCount(MandelbrotConfig config) {
			return config.getMandelbrotFractal().getOutcolouringFormulaConfigElementCount();
		}

		protected String getElementName(OutcolouringFormulaConfigElement element) {
			return element.getExtensionElement().getReference().getExtensionName();
		}
	}
}
