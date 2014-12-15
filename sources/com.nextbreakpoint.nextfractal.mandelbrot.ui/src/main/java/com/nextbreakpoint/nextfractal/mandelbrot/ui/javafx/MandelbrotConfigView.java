package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.BooleanPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ConfigurableExtensionGridPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ConfigurableExtensionPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ElementGridPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.IntegerPane;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.util.PercentagePane;

public class MandelbrotConfigView extends Pane implements Disposable {
	private List<Disposable> disposables = new ArrayList<>();
	private ViewContext viewContext;
	private RenderContext renderContext;
	private NodeObject mandelbrotFractalNode;

	public MandelbrotConfigView(ViewContext viewContext, RenderContext renderContext, NodeObject rootNode) {
		this.viewContext = viewContext;
		this.renderContext = renderContext;
		this.mandelbrotFractalNode = rootNode.getChildNodeByClass(MandelbrotFractalConfigElementNode.NODE_CLASS);;
		VBox pane = new VBox(10);
		getChildren().add(pane);
		pane.setPrefWidth(viewContext.getEditorViewSize().getWidth());
		pane.setPrefHeight(viewContext.getEditorViewSize().getHeight());
		NodeObject incolouringFormulaListNode = mandelbrotFractalNode.getChildNodeByClass(IncolouringFormulaConfigElementNode.NODE_CLASS + "List");
		IncolouringFormulaGridItems incolouringFormulaGridPane = new IncolouringFormulaGridItems(viewContext, incolouringFormulaListNode);
		pane.getChildren().add(incolouringFormulaGridPane);
		NodeObject outcolouringFormulaListNode = mandelbrotFractalNode.getChildNodeByClass(OutcolouringFormulaConfigElementNode.NODE_CLASS + "List");
		OutcolouringFormulaGridItems outcolouringFormulaGridPane = new OutcolouringFormulaGridItems(viewContext, outcolouringFormulaListNode);
		pane.getChildren().add(outcolouringFormulaGridPane);
		incolouringFormulaGridPane.setDelegate(nodeObject -> {
			viewContext.showEditorView(new IncolouringFormulaPane(nodeObject));
		});
		outcolouringFormulaGridPane.setDelegate(nodeObject -> {
			viewContext.showEditorView(new OutcolouringFormulaPane(nodeObject));
		});
	}

	@Override
	public void dispose() {
		for (Disposable disposable : disposables) {
			disposable.dispose();
		}
		disposables.clear();
	}
	
	private NodeObject getChildNode(NodeObject nodeObject, String nodeId) {
		return nodeObject.getChildNodeById(nodeId);
	}

	public class IncolouringFormulaGridItems extends ElementGridPane<IncolouringFormulaConfigElement> implements Disposable {
		public IncolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getEditorViewSize().getWidth(), 50));
			disposables.add(this);
			init();
		}

		protected String getElementName(IncolouringFormulaConfigElement element) {
			if (element.getExtensionElement().getReference() != null) {
				return element.getExtensionElement().getReference().getExtensionName();
			}
			return null;
		}

		protected IncolouringFormulaConfigElement createElement() {
			return new IncolouringFormulaConfigElement();
		}
		
		public void dispose() {
			disposables.remove(this);
		}
	}

	public class OutcolouringFormulaGridItems extends ElementGridPane<OutcolouringFormulaConfigElement> implements Disposable {
		public OutcolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getEditorViewSize().getWidth(), 50));
			disposables.add(this);
			init();
		}

		protected String getElementName(OutcolouringFormulaConfigElement element) {
			if (element.getExtensionElement().getReference() != null) {
				return element.getExtensionElement().getReference().getExtensionName();
			}
			return null;
		}

		protected OutcolouringFormulaConfigElement createElement() {
			return new OutcolouringFormulaConfigElement();
		}
		
		public void dispose() {
			disposables.remove(this);
		}
	}

	public class IncolouringFormulaPane extends BorderPane implements Disposable {
		private List<Runnable> tasks = new ArrayList<>();

		public IncolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			VBox pane = new VBox(10);
			ConfigurableExtensionPane<IncolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<IncolouringFormulaExtensionConfig>(getChildNode(nodeObject, "IncolouringFormula.extension"));
			pane.getChildren().add(extPane);
			extPane.setOnAction(e -> { showExtensionGridPane(getChildNode(nodeObject, "IncolouringFormula.extension")); });
			IntegerPane iterationsPane = new IntegerPane(getChildNode(nodeObject, "IncolouringFormula.iterations"));
			pane.getChildren().add(iterationsPane);
			PercentagePane opacityPane = new PercentagePane(getChildNode(nodeObject, "IncolouringFormula.opacity"));
			pane.getChildren().add(opacityPane);
			BooleanPane autoIterationsPane = new BooleanPane(getChildNode(nodeObject, "IncolouringFormula.autoIterations"));
			pane.getChildren().add(autoIterationsPane);
			BooleanPane enabledPane = new BooleanPane(getChildNode(nodeObject, "IncolouringFormula.enabled"));
			pane.getChildren().add(enabledPane);
			BooleanPane lockedPane = new BooleanPane(getChildNode(nodeObject, "IncolouringFormula.locked"));
			pane.getChildren().add(lockedPane);
			setCenter(pane);
			disposables.add(this);
		}

		private void showExtensionGridPane(NodeObject nodeObject) {
			IncolouringFormulaExtensionGridPane extensionGridPane = createExtensionGridPane(nodeObject, viewContext.getEditorViewSize());
			viewContext.showRenderView(extensionGridPane);
		}
		
		private void dismissExtensionGridPane() {
			viewContext.discardRenderView();
		}

		private IncolouringFormulaExtensionGridPane createExtensionGridPane(NodeObject nodeObject, Dimension2D size) {
			IncolouringFormulaExtensionGridPane extensionGridPane = new IncolouringFormulaExtensionGridPane(nodeObject, size);
			extensionGridPane.setOnAction(x -> {
				dismissExtensionGridPane();
			});
			extensionGridPane.getStyleClass().add("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			disposables.remove(this);
			for (Runnable task : new ArrayList<>(tasks)) {
				task.run();
			}
			tasks.clear();
		}
		
		public class IncolouringFormulaExtensionGridPane extends ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> implements Disposable {
			
			public IncolouringFormulaExtensionGridPane(NodeObject nodeObject,	Dimension2D size) {
				super(nodeObject, size);
				tasks.add(() -> { viewContext.discardRenderView(); });
			}
			
			@Override
			public void dispose() {
				tasks.remove(this);
			}
		}
	}

	public class OutcolouringFormulaPane extends BorderPane implements Disposable {
		private List<Runnable> tasks = new ArrayList<>();

		public OutcolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			VBox pane = new VBox(10);
			ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig>(getChildNode(nodeObject, "OutcolouringFormula.extension"));
			pane.getChildren().add(extPane);
			extPane.setOnAction(e -> { showExtensionGridPane(getChildNode(nodeObject, "OutcolouringFormula.extension")); });
			IntegerPane iterationsPane = new IntegerPane(getChildNode(nodeObject, "OutcolouringFormula.iterations"));
			pane.getChildren().add(iterationsPane);
			PercentagePane opacityPane = new PercentagePane(getChildNode(nodeObject, "OutcolouringFormula.opacity"));
			pane.getChildren().add(opacityPane);
			BooleanPane autoIterationsPane = new BooleanPane(getChildNode(nodeObject, "OutcolouringFormula.autoIterations"));
			pane.getChildren().add(autoIterationsPane);
			BooleanPane enabledPane = new BooleanPane(getChildNode(nodeObject, "OutcolouringFormula.enabled"));
			pane.getChildren().add(enabledPane);
			BooleanPane lockedPane = new BooleanPane(getChildNode(nodeObject, "OutcolouringFormula.locked"));
			pane.getChildren().add(lockedPane);
			setCenter(pane);
			disposables.add(this);
		}

		private void showExtensionGridPane(NodeObject nodeObject) {
			OutcolouringFormulaExtensionGridPane extensionGridPane = createExtensionGridPane(nodeObject, viewContext.getEditorViewSize());
			viewContext.showRenderView(extensionGridPane);
		}
		
		private void dismissExtensionGridPane() {
			viewContext.discardRenderView();
		}

		private OutcolouringFormulaExtensionGridPane createExtensionGridPane(NodeObject nodeObject, Dimension2D size) {
			OutcolouringFormulaExtensionGridPane extensionGridPane = new OutcolouringFormulaExtensionGridPane(nodeObject, size);
			extensionGridPane.setOnAction(x -> {
				dismissExtensionGridPane();
			});
			extensionGridPane.getStyleClass().add("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			disposables.remove(this);
			for (Runnable task : new ArrayList<>(tasks)) {
				task.run();
			}
			tasks.clear();
		}
		
		public class OutcolouringFormulaExtensionGridPane extends ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> implements Disposable {
			
			public OutcolouringFormulaExtensionGridPane(NodeObject nodeObject,	Dimension2D size) {
				super(nodeObject, size);
				tasks.add(() -> { viewContext.discardRenderView(); });
			}
			
			@Override
			public void dispose() {
				tasks.remove(this);
			}
		}
	}
}
