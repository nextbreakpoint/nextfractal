package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
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
	
	public abstract class GridItems<T extends ConfigElement> extends Pane {
		private MandelbrotConfig config;

		public GridItems(ViewContext viewContext, MandelbrotConfig config) {
			setPrefWidth(viewContext.getConfigViewSize().getWidth());
			setMinHeight(50);
			for (int i = 0; i < getElementCount(config); i++) {
				T element = getElement(config, i);
				String name = getElementName(element);
				GridItem item = new GridItem(new GridItemModel() {
				});
				Node node = makeDraggable(item);
				getChildren().add(node);
				node.setLayoutX((i % 4) * 60);
				node.setLayoutY((i / 4) * 60);
			}
			{
				GridItemAdd item = new GridItemAdd();
				getChildren().add(item);
				item.setLayoutX((getElementCount(config) % 4) * 60);
				item.setLayoutY((getElementCount(config) / 4) * 60);
				item.setOnMouseClicked(e -> {
					T element = createElement();
					appendElement(config, element);
					GridItem newItem = new GridItem(new GridItemModel() {
					});
					getChildren().remove(item);
					Node node = makeDraggable(newItem);
					getChildren().add(node);
					getChildren().add(item);
					for (int i = 0; i < getChildren().size(); i++) {
						Node child = getChildren().get(i);
						child.setLayoutX((i % 4) * 60);
						child.setLayoutY((i / 4) * 60);
						if (child instanceof Group) {
							((Group) child).getChildren().get(0).setTranslateX(0);
							((Group) child).getChildren().get(0).setTranslateY(0);
						}
					}
				});
			}
		}

		private Node makeDraggable(final Node node) {
			final DragContext dragContext = new DragContext();
			final Group wrapGroup = new Group(node);

			wrapGroup.addEventFilter(MouseEvent.ANY,
				new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						mouseEvent.consume();
					}
				});

			wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						dragContext.mouseAnchorX = mouseEvent.getX();
						dragContext.mouseAnchorY = mouseEvent.getY();
						dragContext.initialTranslateX = node.getTranslateX();
						dragContext.initialTranslateY = node.getTranslateY();
					}
				});

			wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
				new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
						double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
						if (x < -wrapGroup.getLayoutX()) {
							x = -wrapGroup.getLayoutX();
						}
						if (x >= getWidth() - wrapGroup.getLayoutX() - node.getBoundsInLocal().getWidth()) {
							x = getWidth() - wrapGroup.getLayoutX() - node.getBoundsInLocal().getWidth();
						}
						if (y < -wrapGroup.getLayoutY()) {
							y = -wrapGroup.getLayoutY();
						}
						if (y >= getHeight() - wrapGroup.getLayoutY() - node.getBoundsInLocal().getHeight()) {
							y = getHeight() - wrapGroup.getLayoutY() - node.getBoundsInLocal().getHeight();
						}
						node.setTranslateX(x);
						node.setTranslateY(y);
					}
				});


			wrapGroup.addEventFilter(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
						double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
						if (x < -wrapGroup.getLayoutX()) {
							x = -wrapGroup.getLayoutX();
						}
						if (x >= getWidth() - wrapGroup.getLayoutX() - node.getBoundsInLocal().getWidth()) {
							x = getWidth() - wrapGroup.getLayoutX() - node.getBoundsInLocal().getWidth();
						}
						if (y < -wrapGroup.getLayoutY()) {
							y = -wrapGroup.getLayoutY();
						}
						if (y >= getHeight() - wrapGroup.getLayoutY() - node.getBoundsInLocal().getHeight()) {
							y = getHeight() - wrapGroup.getLayoutY() - node.getBoundsInLocal().getHeight();
						}
						double nx = wrapGroup.getLayoutX() + x;
						double ny = wrapGroup.getLayoutY() + y;
//						System.out.println("A " + nx + "," + ny);
						for (Node child : getChildren()) {
							if (child instanceof Group && child != wrapGroup) {
								double tx = nx - child.getLayoutX();
								double ty = ny - child.getLayoutY();
//								System.out.println("B " + tx + "," + ty);
								if (child.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
									((Group)child).getChildren().get(0).setStyle("-fx-background-color:#ffff00");
									break;
								}
							}
						}
						node.setTranslateX(0);
						node.setTranslateY(0);
					}
				});
			
			return wrapGroup;
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
	
	private class DragContext {
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialTranslateX;
		private double initialTranslateY;
	}
}
