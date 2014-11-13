package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Dimension2D;
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
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementNode;

public class MandelbrotConfigView extends Pane implements Disposable {
	private static final Logger logger = Logger.getLogger(MandelbrotConfigView.class.getName());
	private ViewContext viewContext;
	private RenderContext renderContext;
	private NodeObject mandelbrotFractalNode;

	public MandelbrotConfigView(ViewContext viewContext, RenderContext renderContext, NodeObject mandelbrotFractalNode) {
		this.viewContext = viewContext;
		this.renderContext = renderContext;
		this.mandelbrotFractalNode = mandelbrotFractalNode;
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
			IncolouringFormulaPane incolouringFormulaPane = new IncolouringFormulaPane(nodeObject);
			viewContext.showConfigView(incolouringFormulaPane);
		});
		outcolouringFormulaGridPane.setDelegate(nodeObject -> {
			OutcolouringFormulaPane outcolouringFormulaPane = new OutcolouringFormulaPane(nodeObject);
			viewContext.showConfigView(outcolouringFormulaPane);
		});
	}

	@Override
	public void dispose() {
	}

	protected NodeEditorComponent createEditor(NodeObject node) {
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

	public class IncolouringFormulaGridItems extends ElementGridPane<IncolouringFormulaConfigElement> {
		public IncolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
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
	}

	public class OutcolouringFormulaGridItems extends ElementGridPane<OutcolouringFormulaConfigElement> {
		public OutcolouringFormulaGridItems(ViewContext viewContext, NodeObject listNode) {
			super(listNode, new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
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
	}

	public class IncolouringFormulaPane extends BorderPane implements Disposable {
		private ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> extensionGridPane;
		
		public IncolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			// TODO Auto-generated constructor stub
		}

		public void setElement(NodeObject formulaNode) {
			VBox pane = new VBox(10);
//			formulaNode.getChildNodeById();
//			ConfigurableExtensionPane<IncolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<IncolouringFormulaExtensionConfig>(element.getExtensionElement());
//			pane.getChildren().add(extPane);
//			extPane.setOnAction(e -> { showExtensionGridPane(element); });
//			IntegerPane iterationsPane = new IntegerPane(MandelbrotUIExtensionResources.getInstance().getString("label.iterations"), element.getIterationsElement());
//			pane.getChildren().add(iterationsPane);
//			PercentagePane opacityPane = new PercentagePane(MandelbrotUIExtensionResources.getInstance().getString("label.opacity"), element.getOpacityElement());
//			pane.getChildren().add(opacityPane);
//			BooleanPane autoIterationsPane = new BooleanPane(MandelbrotUIExtensionResources.getInstance().getString("label.autoIterations"), element.getAutoIterationsElement());
//			pane.getChildren().add(autoIterationsPane);
//			BooleanPane enabledPane = new BooleanPane(CoreUIResources.getInstance().getString("label.enabled"), element.getEnabledElement());
//			pane.getChildren().add(enabledPane);
//			BooleanPane lockedPane = new BooleanPane(CoreUIResources.getInstance().getString("label.locked"), element.getLockedElement());
//			pane.getChildren().add(lockedPane);
			setCenter(pane);
		}

		private void showExtensionGridPane(IncolouringFormulaConfigElement element) {
			if (extensionGridPane == null) {
				extensionGridPane = createExtensionGridPane(element, viewContext.getEditorViewSize());
				viewContext.showEditorView(extensionGridPane);
			}
		}
		
		private void dismissExtensionGridPane() {
			if (extensionGridPane != null) {
				viewContext.discardEditorView();
				extensionGridPane = null;
			}
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
			dismissExtensionGridPane();
		}
	}

	public class OutcolouringFormulaPane extends BorderPane implements Disposable {
		private ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> extensionGridPane;
		
		public OutcolouringFormulaPane(NodeObject nodeObject) {
			getStyleClass().add("config-view");
			// TODO Auto-generated constructor stub
		}

		public void setElement(NodeObject formulaNode) {
			VBox pane = new VBox(10);
//			ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig>(element.getExtensionElement());
//			pane.getChildren().add(extPane);
//			extPane.setOnAction(e -> { showExtensionGridPane(element); });
//			IntegerPane iterationsPane = new IntegerPane(MandelbrotUIExtensionResources.getInstance().getString("label.iterations"), element.getIterationsElement());
//			pane.getChildren().add(iterationsPane);
//			PercentagePane opacityPane = new PercentagePane(MandelbrotUIExtensionResources.getInstance().getString("label.opacity"), element.getOpacityElement());
//			pane.getChildren().add(opacityPane);
//			BooleanPane autoIterationsPane = new BooleanPane(MandelbrotUIExtensionResources.getInstance().getString("label.autoIterations"), element.getAutoIterationsElement());
//			pane.getChildren().add(autoIterationsPane);
//			BooleanPane enabledPane = new BooleanPane(CoreUIResources.getInstance().getString("label.enabled"), element.getEnabledElement());
//			pane.getChildren().add(enabledPane);
//			BooleanPane lockedPane = new BooleanPane(CoreUIResources.getInstance().getString("label.locked"), element.getLockedElement());
//			pane.getChildren().add(lockedPane);
			setCenter(pane);
		}

		private void showExtensionGridPane(OutcolouringFormulaConfigElement element) {
			if (extensionGridPane == null) {
				extensionGridPane = createExtensionGridPane(element, viewContext.getEditorViewSize());
				viewContext.showEditorView(extensionGridPane);
			}
		}
		
		private void dismissExtensionGridPane() {
			if (extensionGridPane != null) {
				viewContext.discardEditorView();
				extensionGridPane = null;
			}
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
			dismissExtensionGridPane();
		}
	}
}
