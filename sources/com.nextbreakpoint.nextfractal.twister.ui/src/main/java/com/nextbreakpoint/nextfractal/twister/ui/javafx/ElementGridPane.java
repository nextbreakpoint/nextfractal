package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;

public abstract class ElementGridPane<V extends ConfigElement, T extends ConfigElement> extends Pane {
	private static final Integer TYPE_ITEM = new Integer(1);
	private static final Integer TYPE_SENTINEL = new Integer(2);
	private V config;
	
	public ElementGridPane(ViewContext viewContext, V config) {
		this.config = config;
		setPrefWidth(viewContext.getConfigViewSize().getWidth());
		setMinHeight(50);
		for (int i = 0; i < getElementCount(); i++) {
			Node node = createItem(getElement(i));
			getChildren().add(node);
		}
		Node node = createSentinel();
		getChildren().add(node);
		doLayout();
	}

	private Node createSentinel() {
		GridSentinel sentinel = new GridSentinel();
		sentinel.setStyle("-fx-background-color:#ff0000");
		Node node = makeDraggable(sentinel, TYPE_SENTINEL);
		return node;
	}

	private Node createItem(T element) {
		GridItem item = new GridItem(element);
		if (getElementCount() % 2 == 0) {
			item.setStyle("-fx-background-color:#ffff00");
		} else {
			item.setStyle("-fx-background-color:#00ff00");
		}
		Node node = makeDraggable(item, TYPE_ITEM);
		return node;
	}

	private void doLayout() {
		for (int i = 0; i < getChildren().size(); i++) {
			Node child = getChildren().get(i);
			child.setLayoutX((i % 4) * 60);
			child.setLayoutY((i / 4) * 60);
			((Group) child).getChildren().get(0).setTranslateX(0);
			((Group) child).getChildren().get(0).setTranslateY(0);
		}
	}

	private class DragContext {
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialTranslateX;
		private double initialTranslateY;
	}
	
	private Node unwrapNode(int index) {
		return ((Group)getChildren().get(index)).getChildren().get(0);
	}
	
	private Node makeDraggable(final Node node, final Integer type) {
		final DragContext dragContext = new DragContext();
		final Group sourceNode = new Group(node);
		sourceNode.setUserData(type);

		sourceNode.addEventFilter(MouseEvent.ANY,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					mouseEvent.consume();
				}
			});

		sourceNode.addEventFilter(MouseEvent.MOUSE_PRESSED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					dragContext.mouseAnchorX = mouseEvent.getX();
					dragContext.mouseAnchorY = mouseEvent.getY();
					dragContext.initialTranslateX = node.getTranslateX();
					dragContext.initialTranslateY = node.getTranslateY();
					if (sourceNode.getUserData().equals(TYPE_ITEM)) {
						unwrapNode(getChildren().size() - 1).setStyle("-fx-background-color:#444444");
					} else if (sourceNode.getUserData().equals(TYPE_SENTINEL)) {
						unwrapNode(getChildren().size() - 1).setStyle("-fx-background-color:#FFFFFF");
					}
				}
			});

		sourceNode.addEventFilter(MouseEvent.MOUSE_DRAGGED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
					double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
					if (x < -sourceNode.getLayoutX()) {
						x = -sourceNode.getLayoutX();
					}
					if (x >= getWidth() - sourceNode.getLayoutX() - node.getBoundsInLocal().getWidth()) {
						x = getWidth() - sourceNode.getLayoutX() - node.getBoundsInLocal().getWidth();
					}
					if (y < -sourceNode.getLayoutY()) {
						y = -sourceNode.getLayoutY();
					}
					if (y >= getHeight() - sourceNode.getLayoutY() - node.getBoundsInLocal().getHeight()) {
						y = getHeight() - sourceNode.getLayoutY() - node.getBoundsInLocal().getHeight();
					}
					node.setTranslateX(x);
					node.setTranslateY(y);
				}
			});


		sourceNode.addEventFilter(MouseEvent.MOUSE_RELEASED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
					double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
					if (x < -sourceNode.getLayoutX()) {
						x = -sourceNode.getLayoutX();
					}
					if (x >= getWidth() - sourceNode.getLayoutX() - node.getBoundsInLocal().getWidth()) {
						x = getWidth() - sourceNode.getLayoutX() - node.getBoundsInLocal().getWidth();
					}
					if (y < -sourceNode.getLayoutY()) {
						y = -sourceNode.getLayoutY();
					}
					if (y >= getHeight() - sourceNode.getLayoutY() - node.getBoundsInLocal().getHeight()) {
						y = getHeight() - sourceNode.getLayoutY() - node.getBoundsInLocal().getHeight();
					}
					double nx = sourceNode.getLayoutX() + x;
					double ny = sourceNode.getLayoutY() + y;
					int sourceIndex = getChildren().indexOf(sourceNode);
					Node targetGroup = null;
					int targetIndex = 0;
					for (int i = 0; i < getChildren().size(); i++) {
						Node group = getChildren().get(i);
						double tx = nx - group.getLayoutX();
						double ty = ny - group.getLayoutY();
						if (group != sourceNode && group.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
							targetGroup = group;
							targetIndex = i;
							break;
						}
					}
					if (targetGroup != null) {
						double tx = nx - targetGroup.getLayoutX();
						if (sourceNode.getUserData().equals(TYPE_ITEM)) {
							if (targetGroup.getUserData().equals(TYPE_ITEM)) {
								getChildren().remove(sourceIndex);
								T element = getElement(sourceIndex);
								removeElement(sourceIndex);
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), element);
									getChildren().add(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), sourceNode);
								} else {
									insertElementAfter(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), element);
									getChildren().add(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0) + 1, sourceNode);
								}
								doLayout();
							} else if (targetGroup.getUserData().equals(TYPE_SENTINEL)) {
								getChildren().remove(sourceIndex);
								removeElement(sourceIndex);
								doLayout();
							}
						} else if (sourceNode.getUserData().equals(TYPE_SENTINEL)) {
							if (targetGroup.getUserData().equals(TYPE_ITEM)) {
								T element = makeElement();
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), element);
									Node newNode = createItem(element);
									getChildren().add(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), newNode);
								} else {
									insertElementAfter(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0), element);
									Node newNode = createItem(element);
									getChildren().add(targetIndex - ((sourceIndex < targetIndex) ? 1 : 0) + 1, newNode);
								}
								doLayout();
							}
						}
					} else if (sourceNode.getUserData().equals(TYPE_SENTINEL)) {
						T element = makeElement();
						appendElement(element);
						Node newNode = createItem(element);
						getChildren().add(sourceIndex, newNode);
						doLayout();
					}
					unwrapNode(getChildren().size() - 1).setStyle("-fx-background-color:#FF0000");
					node.setTranslateX(0);
					node.setTranslateY(0);
				}
			});
		
		return sourceNode;
	}
	
	protected V getConfig() {
		return config;
	}
	
	protected abstract void appendElement(T element);

	protected abstract void insertElementAfter(int index, T element);

	protected abstract void insertElementBefore(int index, T element);

	protected abstract void removeElement(int index);

	protected abstract T createElement();

	protected abstract T getElement(int index);

	protected abstract int getElementCount();

	protected abstract String getElementName(T element);

	protected abstract T makeElement();

	public class GridItem extends Pane {
		private T element;
		
		public GridItem(T element) {
			this.element = element;
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
		}

		public String getName() {
			return getElementName(element);
		}
	}
	
	public class GridSentinel extends Pane {
		public GridSentinel() {
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
		}
	}
}