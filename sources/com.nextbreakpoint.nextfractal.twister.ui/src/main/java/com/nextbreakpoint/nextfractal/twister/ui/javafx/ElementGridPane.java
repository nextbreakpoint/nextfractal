package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;

public abstract class ElementGridPane<T extends ConfigElement> extends Pane {
	private static final Integer TYPE_ITEM = new Integer(1);
	private static final Integer TYPE_SENTINEL = new Integer(2);
	
	public ElementGridPane(ViewContext viewContext, int size) {
		setPrefWidth(viewContext.getConfigViewSize().getWidth());
		int cells = getCellCount(viewContext.getConfigViewSize().getWidth(), size);
		setMinWidth(cells * size);
		setMinHeight(size);
	}

	protected void init() {
		for (int i = 0; i < getElementCount(); i++) {
			Node node = createItem(getElement(i));
			getChildren().add(node);
		}
		Node node = createSentinel();
		getChildren().add(node);
		doLayout();
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

	private int getCellCount(double width, double size) {
		return (int)Math.floor(width / (size + 10));
	}
	
	private Node createSentinel() {
		GridSentinel sentinel = new GridSentinel();
		Node node = makeDraggable(sentinel, TYPE_SENTINEL);
		return node;
	}

	private Node createItem(T element) {
		GridItem item = new GridItem(element);
		Node node = makeDraggable(item, TYPE_ITEM);
		return node;
	}

	private void doLayout() {
		int cells = getCellCount(getPrefWidth(), getMinHeight());
		for (int i = 0; i < getChildren().size(); i++) {
			Node child = getChildren().get(i);
			child.setLayoutX((i % cells) * (getMinHeight() + 10));
			child.setLayoutY((i / cells) * (getMinHeight() + 10));
			((Group) child).getChildren().get(0).setTranslateX(0);
			((Group) child).getChildren().get(0).setTranslateY(0);
		}
	}

	private Node unwrapNode(int index) {
		return ((Group)getChildren().get(index)).getChildren().get(0);
	}
	
	private Node unwrapNode(Group group) {
		return group.getChildren().get(0);
	}
	
	private Node makeDraggable(final Node node, final Integer type) {
		final DragContext dragContext = new DragContext();
		final Group sourceGroup = new Group(node);
		sourceGroup.setUserData(type);

		sourceGroup.addEventFilter(MouseEvent.ANY,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					mouseEvent.consume();
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					dragContext.mouseAnchorX = mouseEvent.getX();
					dragContext.mouseAnchorY = mouseEvent.getY();
					dragContext.initialTranslateX = node.getTranslateX();
					dragContext.initialTranslateY = node.getTranslateY();
					if (sourceGroup.getUserData().equals(TYPE_ITEM)) {
						unwrapNode(getChildren().size() - 1).setStyle("-fx-border-color:#333333;-fx-background-color:#555555;-fx-opacity:1.0");
					}
					unwrapNode(sourceGroup).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
					dragContext.index = getChildren().indexOf(sourceGroup);
					getChildren().remove(dragContext.index);
					getChildren().add(sourceGroup);
					dragContext.selectedIndex = -1;
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
					double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
					if (x < -sourceGroup.getLayoutX()) {
						x = -sourceGroup.getLayoutX();
					}
					if (x >= getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth()) {
						x = getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth();
					}
					if (y < -sourceGroup.getLayoutY()) {
						y = -sourceGroup.getLayoutY();
					}
					if (y >= getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight()) {
						y = getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight();
					}
					node.setTranslateX(x);
					node.setTranslateY(y);
					double nx = sourceGroup.getLayoutX() + x;
					double ny = sourceGroup.getLayoutY() + y;
					if (dragContext.selectedIndex != -1) {
						Group group = (Group)getChildren().get(dragContext.selectedIndex);
						if (group.getUserData().equals(TYPE_ITEM)) {
							unwrapNode(group).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
						} else {
							unwrapNode(group).setStyle("-fx-border-color:#333333;-fx-background-color:#555555;-fx-opacity:1.0");	
						}
						dragContext.selectedIndex = -1;
					}
					for (int i = 0; i < getChildren().size(); i++) {
						Group group = (Group)getChildren().get(i);
						double tx = nx - group.getLayoutX();
						double ty = ny - group.getLayoutY();
						if (group != sourceGroup && group.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
							if (group.getUserData().equals(TYPE_ITEM)) {
								unwrapNode(group).setStyle("-fx-border-color:#440000;-fx-background-color:#FF0000;-fx-opacity:1.0");
							} else {
								unwrapNode(group).setStyle("-fx-border-color:#222222;-fx-background-color:#444444;-fx-opacity:1.0");
							}
							dragContext.selectedIndex = i;
							break;
						}
					}
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_RELEASED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
					double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
					if (x < -sourceGroup.getLayoutX()) {
						x = -sourceGroup.getLayoutX();
					}
					if (x >= getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth()) {
						x = getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth();
					}
					if (y < -sourceGroup.getLayoutY()) {
						y = -sourceGroup.getLayoutY();
					}
					if (y >= getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight()) {
						y = getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight();
					}
					double nx = sourceGroup.getLayoutX() + x;
					double ny = sourceGroup.getLayoutY() + y;
					int sourceIndex = dragContext.index;
					Group targetGroup = null;
					int targetIndex = 0;
					for (int i = 0; i < getChildren().size(); i++) {
						Node group = getChildren().get(i);
						double tx = nx - group.getLayoutX();
						double ty = ny - group.getLayoutY();
						if (group != sourceGroup && group.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
							targetGroup = (Group)group;
							targetIndex = i;// - (sourceIndex < i ? 1 : 0);
							break;
						}
					}
					getChildren().remove(sourceGroup);
					if (targetGroup != null) {
						double tx = nx - targetGroup.getLayoutX();
						if (sourceGroup.getUserData().equals(TYPE_ITEM)) {
							if (targetGroup.getUserData().equals(TYPE_ITEM)) {
								T element = getElement(sourceIndex);
								removeElement(sourceIndex);
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex, element);
									getChildren().add(targetIndex, sourceGroup);
								} else {
									insertElementAfter(targetIndex, element);
									getChildren().add(targetIndex + 1, sourceGroup);
								}
								unwrapNode(getChildren().size() - 1).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
								unwrapNode(targetGroup).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
								unwrapNode(sourceGroup).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
							} else if (targetGroup.getUserData().equals(TYPE_SENTINEL)) {
								removeElement(sourceIndex);
								unwrapNode(targetGroup).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
								unwrapNode(sourceGroup).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
							}
							doLayout();
						} else if (sourceGroup.getUserData().equals(TYPE_SENTINEL)) {
							T element = makeElement();
							Node newNode = createItem(element);
							if (targetGroup.getUserData().equals(TYPE_ITEM)) {
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex, element);
									getChildren().add(targetIndex, newNode);
								} else {
									insertElementAfter(targetIndex, element);
									getChildren().add(targetIndex + 1, newNode);
								}
							} else {
								appendElement(element);
								getChildren().add(newNode);
							}
							getChildren().add(sourceGroup);
							doLayout();
							unwrapNode(targetGroup).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
							unwrapNode(sourceGroup).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
						}
					} else if (sourceGroup.getUserData().equals(TYPE_SENTINEL)) {
						T element = makeElement();
						Node newNode = createItem(element);
						appendElement(element);
						getChildren().add(newNode);
						getChildren().add(sourceGroup);
						doLayout();
						unwrapNode(sourceGroup).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
					} else {
						getChildren().add(dragContext.index, sourceGroup);
						unwrapNode(getChildren().size() - 1).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
						unwrapNode(sourceGroup).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
					}
					node.setTranslateX(0);
					node.setTranslateY(0);
				}
			});
		
		return sourceGroup;
	}
	
	private class DragContext {
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialTranslateX;
		private double initialTranslateY;
		private int selectedIndex;
		private int index;
	}
	
	private class GridItem extends Pane {
		private T element;
		
		public GridItem(T element) {
			this.element = element;
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
			setId("grid-item");
		}

		public String getName() {
			return getElementName(element);
		}
	}
	
	private class GridSentinel extends Pane {
		public GridSentinel() {
			setPrefWidth(50);
			setPrefHeight(50);
			setMinWidth(50);
			setMinHeight(50);
			setMaxWidth(50);
			setMaxHeight(50);
			setId("grid-sentinel");
		}
	}
}