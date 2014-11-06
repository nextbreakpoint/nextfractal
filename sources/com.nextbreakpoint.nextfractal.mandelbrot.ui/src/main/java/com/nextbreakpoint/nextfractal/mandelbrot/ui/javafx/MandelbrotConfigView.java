package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.twister.ui.javafx.ElementGridPane;

public class MandelbrotConfigView extends View {

	public MandelbrotConfigView(MandelbrotConfig config, ViewContext viewContext, RenderContext context, NodeSession session) {
		VBox pane = new VBox(10);
		getChildren().add(pane);
		pane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		pane.setPrefHeight(viewContext.getConfigViewSize().getHeight());
		Pane incolouringFormulaPane = new IncolouringFormulaGridItems(viewContext, config);
		ScrollPane incolouringScrollPane = new ScrollPane(incolouringFormulaPane);
		incolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		incolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(incolouringScrollPane);
		Pane outcolouringFormulaPane = new OutcolouringFormulaGridItems(viewContext, config);
		ScrollPane outcolouringScrollPane = new ScrollPane(outcolouringFormulaPane);
		outcolouringScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		outcolouringScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		pane.getChildren().add(outcolouringScrollPane);
	}

	@Override
	public void dispose() {
	}

	public class IncolouringFormulaGridItems extends ElementGridPane<IncolouringFormulaConfigElement> {
		private MandelbrotConfig config;

		public IncolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(viewContext, 50);
			this.config = config;
			init();
		}

		protected MandelbrotConfig getConfig() {
			return config;
		}

		protected void appendElement(IncolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().appendIncolouringFormulaConfigElement(element);
		}

		protected void insertElementAfter(int index, IncolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().insertIncolouringFormulaConfigElementAfter(index, element);
		}

		protected void insertElementBefore(int index, IncolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().insertIncolouringFormulaConfigElementBefore(index, element);
		}

		protected void removeElement(int index) {
			getConfig().getMandelbrotFractal().removeIncolouringFormulaConfigElement(index);
		}

		protected IncolouringFormulaConfigElement createElement() {
			return new IncolouringFormulaConfigElement();
		}

		protected IncolouringFormulaConfigElement getElement(int index) {
			return getConfig().getMandelbrotFractal().getIncolouringFormulaConfigElement(index);
		}

		protected int getElementCount() {
			return getConfig().getMandelbrotFractal().getIncolouringFormulaConfigElementCount();
		}

		protected String getElementName(IncolouringFormulaConfigElement element) {
			if (element.getExtensionElement().getReference() != null) {
				return element.getExtensionElement().getReference().getExtensionName();
			}
			return null;
		}

		protected IncolouringFormulaConfigElement makeElement() {
			return new IncolouringFormulaConfigElement();
		}
	}

	public class OutcolouringFormulaGridItems extends ElementGridPane<OutcolouringFormulaConfigElement> {
		private MandelbrotConfig config;

		public OutcolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(viewContext, 50);
			this.config = config;
			init();
		}

		protected MandelbrotConfig getConfig() {
			return config;
		}

		protected void appendElement(OutcolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().appendOutcolouringFormulaConfigElement(element);
		}

		protected void insertElementAfter(int index, OutcolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().insertOutcolouringFormulaConfigElementAfter(index, element);
		}

		protected void insertElementBefore(int index, OutcolouringFormulaConfigElement element) {
			getConfig().getMandelbrotFractal().insertOutcolouringFormulaConfigElementBefore(index, element);
		}

		protected void removeElement(int index) {
			getConfig().getMandelbrotFractal().removeOutcolouringFormulaConfigElement(index);
		}

		protected OutcolouringFormulaConfigElement createElement() {
			return new OutcolouringFormulaConfigElement();
		}

		protected OutcolouringFormulaConfigElement getElement(int index) {
			return getConfig().getMandelbrotFractal().getOutcolouringFormulaConfigElement(index);
		}

		protected int getElementCount() {
			return getConfig().getMandelbrotFractal().getOutcolouringFormulaConfigElementCount();
		}

		protected String getElementName(OutcolouringFormulaConfigElement element) {
			if (element.getExtensionElement().getReference() != null) {
				return element.getExtensionElement().getReference().getExtensionName();
			}
			return null;
		}

		protected OutcolouringFormulaConfigElement makeElement() {
			return new OutcolouringFormulaConfigElement();
		}
	}
}
