package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.NodeEditorComponent;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.extensionPoints.editor.EditorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ConfigurableExtensionGridPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ElementGridPane;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNode;

public class MandelbrotConfigView extends Pane implements Disposable {
	private static final Logger logger = Logger.getLogger(MandelbrotConfigView.class.getName());
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
		pane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		pane.setPrefHeight(viewContext.getConfigViewSize().getHeight());
		NodeObject incolouringFormulaListNode = mandelbrotFractalNode.getChildNodeByClass(IncolouringFormulaConfigElementNode.NODE_CLASS + "List");
		IncolouringFormulaGridItems incolouringFormulaGridPane = new IncolouringFormulaGridItems(viewContext, incolouringFormulaListNode);
		pane.getChildren().add(incolouringFormulaGridPane);
		NodeObject outcolouringFormulaListNode = mandelbrotFractalNode.getChildNodeByClass(OutcolouringFormulaConfigElementNode.NODE_CLASS + "List");
		OutcolouringFormulaGridItems outcolouringFormulaGridPane = new OutcolouringFormulaGridItems(viewContext, outcolouringFormulaListNode);
		pane.getChildren().add(outcolouringFormulaGridPane);
		incolouringFormulaGridPane.setDelegate(nodeObject -> {
			viewContext.showConfigView(new IncolouringFormulaPane(nodeObject));
		});
		outcolouringFormulaGridPane.setDelegate(nodeObject -> {
			viewContext.showConfigView(new OutcolouringFormulaPane(nodeObject));
		});
	}

	@Override
	public void dispose() {
		for (Disposable disposable : disposables) {
			disposable.dispose();
		}
		disposables.clear();
		getChildren().clear();
	}
	
	private NodeEditorComponent createNodeEditorComponent(NodeObject node) {
		NodeEditorComponent editor = null;
		if (node.getNodeEditor() != null) {
			try {
				final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeId());
				final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
				if (MandelbrotConfigView.logger.isLoggable(Level.FINE)) {	
					MandelbrotConfigView.logger.fine("Editor found for node = " + node.getNodeId());
				}
				editor = runtime.createEditor(node.getNodeEditor());
			}
			catch (final ExtensionNotFoundException x) {
			}
			catch (final Exception x) {
				if (MandelbrotConfigView.logger.isLoggable(Level.WARNING)) {
					MandelbrotConfigView.logger.log(Level.WARNING, "Can't create editor for node = " + node.getNodeId(), x);
				}
			}
			if (editor == null) {
				try {
					final Extension<EditorExtensionRuntime> extension = CoreUIRegistry.getInstance().getEditorExtension(node.getNodeClass());
					final EditorExtensionRuntime runtime = extension.createExtensionRuntime();
					if (MandelbrotConfigView.logger.isLoggable(Level.FINE)) {	
						MandelbrotConfigView.logger.fine("Editor found for node class = " + node.getNodeClass());
					}
					editor = runtime.createEditor(node.getNodeEditor());
				}
				catch (final ExtensionNotFoundException x) {
				}
				catch (final Exception x) {
					if (MandelbrotConfigView.logger.isLoggable(Level.WARNING)) {
						MandelbrotConfigView.logger.log(Level.WARNING, "Can't create editor for node class = " + node.getNodeClass(), x);
					}
				}
			}
			if (editor == null) {
				if (MandelbrotConfigView.logger.isLoggable(Level.FINE)) {	
					MandelbrotConfigView.logger.fine("Can't find editor for node = " + node.getNodeId() + " (" + node.getNodeClass() + ")");
				}
			}
		}
		else {
			if (MandelbrotConfigView.logger.isLoggable(Level.FINE)) {	
				MandelbrotConfigView.logger.fine("Undefined editor for node = " + node.getNodeId());
			}
		}
		return editor;
	}

	private Node createNodeEditor(NodeObject nodeObject, String nodeId) {
		NodeObject childNodeObject = nodeObject.getChildNodeById(nodeId);
		NodeEditorComponent nodeEditorComponent = createNodeEditorComponent(childNodeObject);
		return nodeEditorComponent.getComponent();
	}

	public class IncolouringFormulaGridItems extends ElementGridPane<IncolouringFormulaConfigElement> {
		public IncolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
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
			super.dispose();
		}
	}

	public class OutcolouringFormulaGridItems extends ElementGridPane<OutcolouringFormulaConfigElement> {
		public OutcolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
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
			super.dispose();
		}
	}

	public class IncolouringFormulaPane extends BorderPane implements Disposable {
		
		public IncolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			VBox pane = new VBox(10);
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.extension"));
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.iterations"));
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.opacity"));
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.autoIterations"));
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.enabled"));
			pane.getChildren().add(createNodeEditor(nodeObject, "IncolouringFormula.locked"));
			setCenter(pane);
			disposables.add(this);
		}

		private void showExtensionGridPane(IncolouringFormulaConfigElement element) {
			ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> extensionGridPane = createExtensionGridPane(element, viewContext.getEditorViewSize());
			viewContext.showEditorView(extensionGridPane);
		}
		
		private void dismissExtensionGridPane() {
			viewContext.discardEditorView();
		}

		private ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> createExtensionGridPane(IncolouringFormulaConfigElement element, Dimension2D size) {
			ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> extensionGridPane = new ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig>(element.getExtensionElement(), MandelbrotRegistry.getInstance().getIncolouringFormulaRegistry(), size);
			extensionGridPane.setOnAction(x -> {
				dismissExtensionGridPane();
			});
			extensionGridPane.getStyleClass().add("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			disposables.remove(this);
			for (Node node : getChildren()) {
				if (node instanceof Disposable) {
					((Disposable)node).dispose();
				}
			}
			getChildren().clear();
		}
	}

	public class OutcolouringFormulaPane extends BorderPane implements Disposable {
		
		public OutcolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			// TODO Auto-generated constructor stub
			VBox pane = new VBox(10);
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.extension"));
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.iterations"));
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.opacity"));
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.autoIterations"));
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.enabled"));
			pane.getChildren().add(createNodeEditor(nodeObject, "OutcolouringFormula.locked"));
			setCenter(pane);
			disposables.add(this);
		}

		private void showExtensionGridPane(OutcolouringFormulaConfigElement element) {
			ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> extensionGridPane = createExtensionGridPane(element, viewContext.getEditorViewSize());
			viewContext.showEditorView(extensionGridPane);
		}
		
		private void dismissExtensionGridPane() {
			viewContext.discardEditorView();
		}

		private ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> createExtensionGridPane(OutcolouringFormulaConfigElement element, Dimension2D size) {
			ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> extensionGridPane = new ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig>(element.getExtensionElement(), MandelbrotRegistry.getInstance().getOutcolouringFormulaRegistry(), size);
			extensionGridPane.setOnAction(x -> {
				dismissExtensionGridPane();
			});
			extensionGridPane.getStyleClass().add("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			disposables.remove(this);
			for (Node node : getChildren()) {
				if (node instanceof Disposable) {
					((Disposable)node).dispose();
				}
			}
			getChildren().clear();
		}
	}
}
