package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.javafx.CoreUIResources;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.BooleanPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ConfigurableExtensionGridPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ConfigurableExtensionPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.ElementGridPane;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.IntegerPane;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.extensions.MandelbrotUIExtensionResources;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.util.PercentagePane;

public class MandelbrotConfigView extends Pane implements Disposable {
	private ViewContext viewContext;
	private RenderContext renderContext;
	private NodeSession session;
	private MandelbrotConfig config;

	public MandelbrotConfigView(MandelbrotConfig config, ViewContext viewContext, RenderContext renderContext, NodeSession session) {
		this.viewContext = viewContext;
		this.renderContext = renderContext;
		this.session = session;
		this.config = config;
		VBox pane = new VBox(10);
		getChildren().add(pane);
		pane.setPrefWidth(viewContext.getConfigViewSize().getWidth());
		pane.setPrefHeight(viewContext.getConfigViewSize().getHeight());
		IncolouringFormulaGridItems incolouringFormulaGridPane = new IncolouringFormulaGridItems(viewContext, config);
		pane.getChildren().add(incolouringFormulaGridPane);
		OutcolouringFormulaGridItems outcolouringFormulaGridPane = new OutcolouringFormulaGridItems(viewContext, config);
		pane.getChildren().add(outcolouringFormulaGridPane);
		incolouringFormulaGridPane.setDelegate(element -> {
			IncolouringFormulaPane incolouringFormulaPane = new IncolouringFormulaPane();
			incolouringFormulaPane.setId("config-view");
			incolouringFormulaPane.setElement(element);
			viewContext.showConfigView(incolouringFormulaPane);
		});
		outcolouringFormulaGridPane.setDelegate(element -> {
			OutcolouringFormulaPane outcolouringFormulaPane = new OutcolouringFormulaPane();
			outcolouringFormulaPane.setId("config-view");
			outcolouringFormulaPane.setElement(element);
			viewContext.showConfigView(outcolouringFormulaPane);
		});
	}

	@Override
	public void dispose() {
	}

	@FunctionalInterface
	public interface GridSelectionDelegate<T extends ConfigElement> {
		public void selectElement(T element);
	}
	
	public class IncolouringFormulaGridItems extends ElementGridPane<IncolouringFormulaConfigElement> {
		private GridSelectionDelegate<IncolouringFormulaConfigElement> delegate;
		private MandelbrotConfig config;

		public IncolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
			this.config = config;
			init();
		}

		public GridSelectionDelegate<IncolouringFormulaConfigElement> getDelegate() {
			return delegate;
		}

		public void setDelegate(GridSelectionDelegate<IncolouringFormulaConfigElement> delegate) {
			this.delegate = delegate;
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

		protected void selectElement(IncolouringFormulaConfigElement element) {
			if (delegate != null) {
				delegate.selectElement(element);
			}
		}
	}

	public class OutcolouringFormulaGridItems extends ElementGridPane<OutcolouringFormulaConfigElement> {
		private GridSelectionDelegate<OutcolouringFormulaConfigElement> delegate;
		private MandelbrotConfig config;

		public OutcolouringFormulaGridItems(ViewContext viewContext, MandelbrotConfig config) {
			super(new Dimension2D(viewContext.getConfigViewSize().getWidth(), 50));
			this.config = config;
			init();
		}

		public GridSelectionDelegate<OutcolouringFormulaConfigElement> getDelegate() {
			return delegate;
		}

		public void setDelegate(GridSelectionDelegate<OutcolouringFormulaConfigElement> delegate) {
			this.delegate = delegate;
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

		protected void selectElement(OutcolouringFormulaConfigElement element) {
			if (delegate != null) {
				delegate.selectElement(element);
			}
		}
	}

	public class IncolouringFormulaPane extends BorderPane implements Disposable {
		private ConfigurableExtensionGridPane<IncolouringFormulaExtensionRuntime<? extends IncolouringFormulaExtensionConfig>, IncolouringFormulaExtensionConfig> extensionGridPane;
		
		public void setElement(IncolouringFormulaConfigElement element) {
			VBox pane = new VBox(10);
//			StringPane labelPane = new StringPane(CoreUIResources.getInstance().getString("label.label"), element.getLabelElement());
//			pane.getChildren().add(labelPane);
			ConfigurableExtensionPane<IncolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<IncolouringFormulaExtensionConfig>(element.getExtensionElement());
			pane.getChildren().add(extPane);
			extPane.setOnAction(e -> { showExtensionGridPane(element); });
			IntegerPane iterationsPane = new IntegerPane(MandelbrotUIExtensionResources.getInstance().getString("label.iterations"), element.getIterationsElement());
			pane.getChildren().add(iterationsPane);
			PercentagePane opacityPane = new PercentagePane(MandelbrotUIExtensionResources.getInstance().getString("label.opacity"), element.getOpacityElement());
			pane.getChildren().add(opacityPane);
			BooleanPane autoIterationsPane = new BooleanPane(MandelbrotUIExtensionResources.getInstance().getString("label.autoIterations"), element.getAutoIterationsElement());
			pane.getChildren().add(autoIterationsPane);
			BooleanPane enabledPane = new BooleanPane(CoreUIResources.getInstance().getString("label.enabled"), element.getEnabledElement());
			pane.getChildren().add(enabledPane);
			BooleanPane lockedPane = new BooleanPane(CoreUIResources.getInstance().getString("label.locked"), element.getLockedElement());
			pane.getChildren().add(lockedPane);
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
			extensionGridPane.setId("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			dismissExtensionGridPane();
		}
	}

	public class OutcolouringFormulaPane extends BorderPane implements Disposable {
		private ConfigurableExtensionGridPane<OutcolouringFormulaExtensionRuntime<? extends OutcolouringFormulaExtensionConfig>, OutcolouringFormulaExtensionConfig> extensionGridPane;
		
		public void setElement(OutcolouringFormulaConfigElement element) {
			VBox pane = new VBox(10);
//			StringPane labelPane = new StringPane(CoreUIResources.getInstance().getString("label.label"), element.getLabelElement());
//			pane.getChildren().add(labelPane);
			ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig> extPane = new ConfigurableExtensionPane<OutcolouringFormulaExtensionConfig>(element.getExtensionElement());
			pane.getChildren().add(extPane);
			extPane.setOnAction(e -> { showExtensionGridPane(element); });
			IntegerPane iterationsPane = new IntegerPane(MandelbrotUIExtensionResources.getInstance().getString("label.iterations"), element.getIterationsElement());
			pane.getChildren().add(iterationsPane);
			PercentagePane opacityPane = new PercentagePane(MandelbrotUIExtensionResources.getInstance().getString("label.opacity"), element.getOpacityElement());
			pane.getChildren().add(opacityPane);
			BooleanPane autoIterationsPane = new BooleanPane(MandelbrotUIExtensionResources.getInstance().getString("label.autoIterations"), element.getAutoIterationsElement());
			pane.getChildren().add(autoIterationsPane);
			BooleanPane enabledPane = new BooleanPane(CoreUIResources.getInstance().getString("label.enabled"), element.getEnabledElement());
			pane.getChildren().add(enabledPane);
			BooleanPane lockedPane = new BooleanPane(CoreUIResources.getInstance().getString("label.locked"), element.getLockedElement());
			pane.getChildren().add(lockedPane);
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
			extensionGridPane.setId("config-view");
			return extensionGridPane;
		}

		@Override
		public void dispose() {
			dismissExtensionGridPane();
		}
	}
}
