// Read example08.js before to read this example.
// This example shows how to modify the configuration tree.

var nodeList = tree.getRootNode().getNodeByPath("0,0,0,0,0");
if (nodeList != null && nodeList.getClassId() == "node.class.ImageLayerElementList") {
	nodeList.appendChildNode(nodeList.createValueByArgs());
	var node = nodeList.getChildNode(nodeList.getChildNodeCount() - 1);
	var nodeLabel = node.getChildNode(4);
	if (nodeLabel != null && nodeLabel.getClassId() == "node.class.StringElement") {
		nodeLabel.setValueByArgs("Text Level");
	}
	var nodeImage = node.getChildNode(0);
	if (nodeImage != null && nodeImage.getClassId() == "node.class.ImageElement") {
		var nodeExtension = nodeImage.getChildNode(0);
		if (nodeExtension != null && nodeExtension.getClassId() == "node.class.ImageReference") {
			// The getEnumerator returns the enumerator to browse an extension point.
			// There are an enumerator for every node of class reference (see example10.js).
			var enumerator = context.getEnumerator(nodeExtension.getClassId());
			// Use to print the list of available extensions.
			// printExtensions(enumerator);
			try {
				// The method getExtension returns an extension by its extensionId. 
				// It throws an exception if the extensionId doesn't exist.   
				nodeExtension.setValueByArgs(enumerator.getExtension("twister.frame.layer.image.text"));
			}
			catch (e) {
				context.println(e);
			}
		}
		var nodeText = nodeExtension.getChildNode(3);
		if (nodeText != null && nodeText.getClassId() == "node.class.StringElement") {
			nodeText.setValueByArgs("This is a text layer");
		}
		var nodeColor = nodeExtension.getChildNode(0);
		if (nodeColor != null && nodeColor.getClassId() == "node.class.ColorElement") {
			nodeColor.setValueByArgs("FFFFFF00");
		}
		var nodeSize = nodeExtension.getChildNode(2);
		if (nodeSize != null && nodeSize.getClassId() == "node.class.DoubleElement") {
			nodeSize.setValueByArgs(10);
		}
		var nodeFont = nodeExtension.getChildNode(1);
		if (nodeFont != null && nodeFont.getClassId() == "node.class.FontElement") {
			nodeFont.setValueByArgs("arial-normal");
		}
		var nodeLeft = nodeExtension.getChildNode(4);
		if (nodeLeft != null && nodeLeft.getClassId() == "node.class.DoubleElement") {
			nodeLeft.setValueByArgs(10);
		}
		var nodeTop = nodeExtension.getChildNode(5);
		if (nodeTop != null && nodeLeft.getClassId() == "node.class.DoubleElement") {
			nodeTop.setValueByArgs(50);
		}
	}
	nodeList.appendChildNode(nodeList.createValueByArgs());
	var node = nodeList.getChildNode(nodeList.getChildNodeCount() - 1);
	var nodeLabel = node.getChildNode(4);
	if (nodeLabel != null && nodeLabel.getClassId() == "node.class.StringElement") {
		nodeLabel.setValueByArgs("Border Level");
	}
	var nodeImage = node.getChildNode(0);
	if (nodeImage != null && nodeImage.getClassId() == "node.class.ImageElement") {
		var nodeExtension = nodeImage.getChildNode(0);
		if (nodeExtension != null && nodeExtension.getClassId() == "node.class.ImageReference") {
			// The getEnumerator returns the enumerator to browse an extension point.
			// There are an enumerator for every node of class reference (see example10.js).
			var enumerator = context.getEnumerator(nodeExtension.getClassId());
			// Use to print the list of available extensions.
			// printExtensions(enumerator);
			try {
				// The method getExtension returns an extension by its extensionId. 
				// It throws an exception if the extensionId doesn't exist.   
				nodeExtension.setValueByArgs(enumerator.getExtension("twister.frame.layer.image.border"));
			}
			catch (e) {
				context.println(e);
			}
		}
		var nodeColor = nodeExtension.getChildNode(0);
		if (nodeColor != null && nodeColor.getClassId() == "node.class.ColorElement") {
			nodeColor.setValueByArgs("FFFFFFFF");
		}
		var nodeSize = nodeExtension.getChildNode(1);
		if (nodeSize != null && nodeSize.getClassId() == "node.class.DoubleElement") {
			nodeSize.setValueByArgs(4);
		}
	}
	tree.accept();
}

function printExtensions(enumerator) {
	context.println("Available extensions:");
	// The method listExtensions returns a list of string, the available extensionIds.
	var extensions = enumerator.listExtensions();
	for (var i = 0; i < extensions.size(); i++) {
		context.println(extensions.get(i));
	}
}
