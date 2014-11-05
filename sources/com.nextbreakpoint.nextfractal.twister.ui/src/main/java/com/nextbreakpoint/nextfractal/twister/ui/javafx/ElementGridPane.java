package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;

public abstract class ElementGridPane<T extends ConfigElement> extends Pane {
	private GridGroup sentinelGroup;
	
	public ElementGridPane(ViewContext viewContext, int size) {
		setPrefWidth(viewContext.getConfigViewSize().getWidth());
		int cells = getCellCount(viewContext.getConfigViewSize().getWidth(), size);
		setMinWidth(cells * size);
		setMinHeight(size);
	}

	protected void init() {
		for (int i = 0; i < getElementCount(); i++) {
			getChildren().add(createItem(getElement(i)));
		}
		sentinelGroup = createSentinel();
		getChildren().add(sentinelGroup);
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

	private Node unwrapNode(Group group) {
		return group.getChildren().get(0);
	}
	
	private GridGroup createSentinel() {
		GridSentinel sentinel = new GridSentinel();
		GridGroup node = createGroup(sentinel);
		return node;
	}

	private GridGroup createItem(T element) {
		GridItem item = new GridItem(element);
		GridGroup node = createGroup(item);
		return node;
	}

	private GridGroup createGroup(final GridItem node) {
		final GroupItem group = new GroupItem(node);
		makeDraggable(group);
		return group;
	}

	private GridGroup createGroup(final GridSentinel node) {
		final GroupSentinel group = new GroupSentinel(node);
		makeDraggable(group);
		return group;
	}

	private void makeDraggable(final GridGroup sourceGroup) {
		final DragContext dragContext = new DragContext();
		
		sourceGroup.addEventFilter(MouseEvent.ANY,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					mouseEvent.consume();
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					Node node = unwrapNode(sourceGroup);
					dragContext.mouseAnchorX = mouseEvent.getX();
					dragContext.mouseAnchorY = mouseEvent.getY();
					dragContext.initialTranslateX = node.getTranslateX();
					dragContext.initialTranslateY = node.getTranslateY();
					sourceGroup.setSource(true);
					sourceGroup.beginDrag();
					if (sourceGroup != sentinelGroup) {
						sentinelGroup.setSource(false);
						sentinelGroup.beginDrag();
					}
					dragContext.index = getChildren().indexOf(sourceGroup);
					getChildren().remove(dragContext.index);
					getChildren().add(sourceGroup);
					dragContext.selectedIndex = -1;
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					Node node = unwrapNode(sourceGroup);
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
					int selectedIndex = -1;
					for (int i = 0; i < getChildren().size(); i++) {
						GridGroup group = (GridGroup)getChildren().get(i);
						double tx = nx - group.getLayoutX();
						double ty = ny - group.getLayoutY();
						if (group != sourceGroup && group.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
							selectedIndex = i;
							break;
						}
					}
					if (dragContext.selectedIndex != selectedIndex) {
						if (dragContext.selectedIndex != -1) {
							GridGroup group = (GridGroup)getChildren().get(dragContext.selectedIndex);
							group.setSelected(false);
							group.updateDrag();
						}
						dragContext.selectedIndex = selectedIndex;
						if (selectedIndex != -1) {
							GridGroup group = (GridGroup)getChildren().get(dragContext.selectedIndex);
							group.setSelected(true);
							group.updateDrag();
						}
					}
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_RELEASED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					Node node = unwrapNode(sourceGroup);
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
					GridGroup targetGroup = null;
					int targetIndex = 0;
					getChildren().remove(sourceGroup);
					for (int i = 0; i < getChildren().size(); i++) {
						Node group = getChildren().get(i);
						double tx = nx - group.getLayoutX();
						double ty = ny - group.getLayoutY();
						if (group.contains(tx + node.getBoundsInLocal().getWidth() / 2, ty + node.getBoundsInLocal().getHeight() / 2)) {
							targetGroup = (GridGroup)group;
							targetIndex = i;
							break;
						}
					}
					if (targetGroup != null) {
						double tx = nx - targetGroup.getLayoutX();
						if (sourceGroup instanceof ElementGridPane<?>.GroupItem) {
							if (targetGroup instanceof ElementGridPane<?>.GroupItem) {
								T element = getElement(sourceIndex);
								removeElement(sourceIndex);
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex, element);
									getChildren().add(targetIndex, sourceGroup);
								} else {
									insertElementAfter(targetIndex, element);
									getChildren().add(targetIndex + 1, sourceGroup);
								}
							} else if (targetGroup instanceof ElementGridPane<?>.GroupSentinel) {
								removeElement(sourceIndex);
							}
							doLayout();
						} else if (sourceGroup instanceof ElementGridPane<?>.GroupSentinel) {
							if (targetGroup instanceof ElementGridPane<?>.GroupItem) {
								T element = makeElement();
								Node newNode = createItem(element);
								if (tx + node.getBoundsInLocal().getWidth() / 2 <= targetGroup.getBoundsInParent().getWidth() / 2) {
									insertElementBefore(targetIndex, element);
									getChildren().add(targetIndex, newNode);
								} else {
									insertElementAfter(targetIndex, element);
									getChildren().add(targetIndex + 1, newNode);
								}
							}
							getChildren().add(sourceGroup);
							doLayout();
						}
					} else if (sourceGroup instanceof ElementGridPane<?>.GroupSentinel) {
						T element = makeElement();
						Node newNode = createItem(element);
						appendElement(element);
						getChildren().add(newNode);
						getChildren().add(sourceGroup);
						doLayout();
					} else {
						getChildren().add(dragContext.index, sourceGroup);
					}
					node.setTranslateX(0);
					node.setTranslateY(0);
					if (targetGroup != null) {
						targetGroup.endDrag();
					}
					sourceGroup.endDrag();
					if (sourceGroup != sentinelGroup) {
						sentinelGroup.endDrag();
					}
				}
			});
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
	
	private class GridGroup extends Group {
		private boolean selected = false;
		private boolean source = false;
		
		public GridGroup(Node node) {
			super(node);
		}

		public boolean isSelected() {
			return selected;
		}

		public boolean isSource() {
			return source;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public void setSource(boolean source) {
			this.source = source;
		}

		public void beginDrag() {
		}
		
		public void updateDrag() {
		}

		public void endDrag() {
		}
	}
	
	private class GroupItem extends GridGroup {
		public GroupItem(ElementGridPane<T>.GridItem node) {
			super(node);
		}

		public void beginDrag() {
			if (isSource()) {
				unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
			} else {
				unwrapNode(this).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
			}
		}

		public void updateDrag() {
			if (isSelected()) {
				unwrapNode(this).setStyle("-fx-border-color:#440000;-fx-background-color:#FF0000;-fx-opacity:1.0");
			} else {
				if (isSource()) {
					unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
				} else {
					unwrapNode(this).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
				}
			}
		}

		public void endDrag() {
			unwrapNode(this).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
		}
	}
	
	private class GroupSentinel extends GridGroup {
		public GroupSentinel(ElementGridPane<T>.GridSentinel node) {
			super(node);
		}

		public void beginDrag() {
			if (isSource()) {
				unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
			} else {
				unwrapNode(this).setStyle("-fx-border-color:#333333;-fx-background-color:#555555;-fx-opacity:1.0");
			}
		}

		public void updateDrag() {
			if (isSelected()) {
				unwrapNode(this).setStyle("-fx-border-color:#222222;-fx-background-color:#444444;-fx-opacity:1.0");
			} else {
				if (isSource()) {
					unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
				} else {
					unwrapNode(this).setStyle("-fx-border-color:#333333;-fx-background-color:#555555;-fx-opacity:1.0");
				}
			}
		}

		public void endDrag() {
			unwrapNode(this).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
		}
	}
}