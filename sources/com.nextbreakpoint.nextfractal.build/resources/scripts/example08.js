// Read example07.js before to read this example.
// This example shows how to modify the configuration tree.

var nodeList = tree.getRootNode().getNodeByPath("0,0,0,0,0");
if (nodeList != null && nodeList.getClassId() == "node.class.ImageLayerElementList") {
	// The method getChildNodeCount returns the number of children nodes.
	if (nodeList.getChildNodeCount() > 0) {
		// The method getChildNode returns the node at the specified position of children node list. 
		var node = nodeList.getChildNode(0);
		// The method clone creates a clone of the node value instance.
		var nodeValue1 = node.getValue().clone();
		var nodeValue2 = node.getValue().clone();
		var nodeValue3 = node.getValue().clone();
		var nodeValue4 = node.getValue().clone();
		// The method removeAllChildNodes removes all children nodes.
		nodeList.removeAllChildNodes();
		// The method appendChildNode appends a node to children node list. 
		nodeList.appendChildNode(nodeValue1);
		// The method insertChildNodeBefore inserts a node into children node list before the specified position. 
		nodeList.insertChildNodeBefore(0, nodeValue2);
		// The method insertChildNodeAfter inserts a node into children node list after the specified position.
		nodeList.insertChildNodeAfter(0, nodeValue3);
		// The method insertChildNodeAt inserts a node into children node list at the specified position. 
		nodeList.insertChildNodeAt(1, nodeValue4);
		for (var i = 0; i < nodeList.getChildNodeCount(); i++) {
			var node = nodeList.getChildNode(i);
			var nodeLabel = node.getChildNode(4);
			if (nodeLabel != null && nodeLabel.getClassId() == "node.class.StringElement") {
				nodeLabel.setValueByArgs("Level " + i);
				// The method getPreviousValue returns the previous node value.
				context.println("Label changed from " + nodeLabel.getPreviousValue().toJSObject() + " to " + nodeLabel.getValue().toJSObject());
			}
		}
		// The method moveDownChildNode moves down the node at the specified position of children node list. 
		nodeList.moveDownChildNode(0);
		// The method moveUpChildNode moves up the node at the specified position of children node list. 
		nodeList.moveUpChildNode(2);
		// The method removeChildNode removes the node at the specified position of children node list. 
		nodeList.removeChildNode(1);
		tree.accept();
		for (var i = 0; i < nodeList.getChildNodeCount(); i++) {
			var node = nodeList.getChildNode(i);
			var nodeLabel = node.getChildNode(4);
			if (nodeLabel != null && nodeLabel.getClassId() == "node.class.StringElement") {
				context.println("Layer " + i + " has label " + nodeLabel.getValue().toJSObject());
			}
		}
	}
}
