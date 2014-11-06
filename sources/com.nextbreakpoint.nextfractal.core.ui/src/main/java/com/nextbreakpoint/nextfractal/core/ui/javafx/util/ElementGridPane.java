package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

	protected abstract void selectElement(T element);

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

	private Node unwrapNode(GridGroup group) {
		return group.getChildren().get(0);
	}
	
	private void setCellSize(Pane node) {
		node.setPrefWidth(getMinHeight());
		node.setPrefHeight(getMinHeight());
		node.setMinWidth(getMinHeight());
		node.setMinHeight(getMinHeight());
		node.setMaxWidth(getMinHeight());
		node.setMaxHeight(getMinHeight());
	}

	private GridGroup createSentinel() {
		GridSentinel sentinel = new GridSentinel();
		setCellSize(sentinel);
		GridGroup node = createGroup(sentinel);
		return node;
	}

	private GridGroup createItem(T element) {
		GridItem item = new GridItem(element);
		setCellSize(item);
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
					beginDrag(dragContext, sourceGroup);
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = computeX(dragContext, sourceGroup, mouseEvent);
					double y = computeY(dragContext, sourceGroup, mouseEvent);
					updateDrag(dragContext, sourceGroup, x, y);
				}
			});

		sourceGroup.addEventFilter(MouseEvent.MOUSE_RELEASED,
			new EventHandler<MouseEvent>() {
				public void handle(final MouseEvent mouseEvent) {
					double x = computeX(dragContext, sourceGroup, mouseEvent);
					double y = computeY(dragContext, sourceGroup, mouseEvent);
					endDrag(dragContext, sourceGroup, x, y);
				}
			});
	}

	@SuppressWarnings("unchecked")
	private ElementGridPane<T>.GridGroup getGroup(int i) {
		return (GridGroup)getChildren().get(i);
	}

	@SuppressWarnings("unchecked")
	private T getElement(final GridGroup sourceGroup) {
		return ((GridItem)unwrapNode(sourceGroup)).getElement();
	}

	private double computeX(final DragContext dragContext, final GridGroup sourceGroup, final MouseEvent mouseEvent) {
		Node node = unwrapNode(sourceGroup);
		double x = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX;
		if (x < -sourceGroup.getLayoutX()) {
			x = -sourceGroup.getLayoutX();
		}
		if (x >= getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth()) {
			x = getWidth() - sourceGroup.getLayoutX() - node.getBoundsInLocal().getWidth();
		}
		return x;
	}

	private double computeY(final DragContext dragContext, final GridGroup sourceGroup, final MouseEvent mouseEvent) {
		Node node = unwrapNode(sourceGroup);
		double y = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY;
		if (y < -sourceGroup.getLayoutY()) {
			y = -sourceGroup.getLayoutY();
		}
		if (y >= getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight()) {
			y = getHeight() - sourceGroup.getLayoutY() - node.getBoundsInLocal().getHeight();
		}
		return y;
	}

	private int findGroup(final GridGroup sourceGroup, double x, double y) {
		int selectedIndex = -1;
		double nx = sourceGroup.getLayoutX() + x;
		double ny = sourceGroup.getLayoutY() + y;
		Node node1 = unwrapNode(sourceGroup);
		for (int i = 0; i < getChildren().size(); i++) {
			GridGroup group = getGroup(i);
			double tx = nx - group.getLayoutX();
			double ty = ny - group.getLayoutY();
			if (group != sourceGroup && group.contains(tx + node1.getBoundsInLocal().getWidth() / 2, ty + node1.getBoundsInLocal().getHeight() / 2)) {
				selectedIndex = i;
				break;
			}
		}
		return selectedIndex;
	}

	private void beginDrag(final DragContext dragContext, final GridGroup sourceGroup) {
		sourceGroup.setSource(true);
		sourceGroup.beginDrag();
		if (sourceGroup != sentinelGroup) {
			sentinelGroup.beginDrag();
		}
		dragContext.index = getChildren().indexOf(sourceGroup);
		getChildren().remove(dragContext.index);
		getChildren().add(sourceGroup);
		dragContext.selectedIndex = -1;
	}

	private void updateDrag(final DragContext dragContext, final GridGroup sourceGroup, double x, double y) {
		Node node = unwrapNode(sourceGroup);
		node.setTranslateX(x);
		node.setTranslateY(y);
		int selectedIndex = findGroup(sourceGroup, x, y);
		if (dragContext.selectedIndex != selectedIndex) {
			if (dragContext.selectedIndex != -1) {
				GridGroup group = getGroup(dragContext.selectedIndex);
				group.setSelected(false);
				group.updateDrag();
			}
			dragContext.selectedIndex = selectedIndex;
			if (selectedIndex != -1) {
				GridGroup group = getGroup(dragContext.selectedIndex);
				group.setSelected(true);
				group.updateDrag();
			}
		}
		sourceGroup.updateDrag();
		if (sourceGroup != sentinelGroup) {
			sentinelGroup.updateDrag();
		}
	}

	private void endDrag(final DragContext dragContext,	final GridGroup sourceGroup, double x, double y) {
		int sourceIndex = dragContext.index;
		Node node = unwrapNode(sourceGroup);
		getChildren().remove(sourceGroup);
		int targetIndex = findGroup(sourceGroup, x, y);
		GridGroup targetGroup = null;
		if (targetIndex != -1) {
			targetGroup = getGroup(targetIndex);
		}
		if (targetGroup != null) {
			double tx = sourceGroup.getLayoutX() + x - targetGroup.getLayoutX();
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
			selectElement(getElement(sourceGroup));
		}
		node.setTranslateX(0);
		node.setTranslateY(0);
		if (targetGroup != null && targetGroup != sentinelGroup) {
			targetGroup.setSelected(false);
			targetGroup.endDrag();
		}
		sourceGroup.setSource(false);
		sourceGroup.endDrag();
		if (sourceGroup != sentinelGroup) {
			sentinelGroup.setSelected(false);
			sentinelGroup.endDrag();
		}
	}

	private class DragContext {
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialTranslateX;
		private double initialTranslateY;
		private int selectedIndex;
		private int index;
	}
	
	private class GridItem extends BorderPane {
		private T element;
		
		public GridItem(T element) {
			this.element = element;
			setId("grid-item");
			Label label = new Label(getName());
			label.setAlignment(Pos.CENTER);
			setCenter(label);
		}

		public String getName() {
			String name = getElementName(element);
			if (name != null) {
				Pattern pattern = Pattern.compile("([A-Z])[a-z]*", 0);
				Matcher matcher = pattern.matcher(name);
				StringBuilder builder = new StringBuilder();
				while (matcher.find()) {
					builder.append(matcher.group(1));
				}
				return builder.toString();
			}
			return "?";
		}

		public T getElement() {
			return element;
		}
	}
	
	private class GridSentinel extends BorderPane {
		public GridSentinel() {
			setId("grid-sentinel");
			Label label = new Label("+");
			label.setAlignment(Pos.CENTER);
			setCenter(label);
		}
		
		public void setLabel(String text) {
			((Label)getCenter()).setText(text);
		}
	}
	
	private class GridGroup extends Group {
		private boolean selected = false;
		private boolean source = false;
		private boolean changed = false;
		
		public GridGroup(Node node) {
			super(node);
		}

		public boolean isSelected() {
			return selected;
		}

		public boolean isSource() {
			return source;
		}

		public boolean isChanged() {
			return changed;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			setChanged(true);
		}

		public void setSource(boolean source) {
			this.source = source;
			setChanged(true);
		}

		protected void setChanged(boolean source) {
			this.changed = true;
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
			setChanged(true);
		}

		public void updateDrag() {
			if (isChanged()) {
				if (isSelected()) {
					unwrapNode(this).setStyle("-fx-border-color:#440000;-fx-background-color:#FF0000;-fx-opacity:1.0");
				} else {
					if (isSource()) {
						unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
					} else {
						unwrapNode(this).setStyle("-fx-border-color:#444444;-fx-background-color:#666666;-fx-opacity:1.0");
					}
				}
				setChanged(false);
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

		@SuppressWarnings("unchecked")
		private void setLabel(String text) {
			((GridSentinel)unwrapNode(this)).setLabel(text);
			setChanged(true);
		}

		public void beginDrag() {
			setChanged(true);
		}

		public void updateDrag() {
			if (isChanged()) {
				if (isSelected()) {
					unwrapNode(this).setStyle("-fx-border-color:#222222;-fx-background-color:#444444;-fx-opacity:1.0");
				} else {
					if (isSource()) {
						setLabel("?");
						unwrapNode(this).setStyle("-fx-border-color:#004400;-fx-background-color:#00FF00;-fx-opacity:0.5");
					} else {
						setLabel("-");
						unwrapNode(this).setStyle("-fx-border-color:#333333;-fx-background-color:#555555;-fx-opacity:1.0");
					}
				}
				setChanged(false);
			}
		}

		public void endDrag() {
			setLabel("+");
			unwrapNode(this).setStyle("-fx-border-color:#444400;-fx-background-color:#ffff00;-fx-opacity:1.0");
		}
	}
}