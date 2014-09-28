// Read example06.js before to read this example.
// This example shows how to modify the configuration tree.

var node1 = tree.getRootNode().getNodeByPath("0,1,1");
if (node1 != null && node1.getClassId() == "node.class.BooleanElement") {
	// The method setValueByArgs changes the node value to a new value created by the arguments.
	// For a boolean element node there is only one argument of boolean type. 
	node1.setValueByArgs(!node1.getValue().toJSObject().booleanValue());
}

var node2 = tree.getRootNode().getNodeByPath("0,1,2");
if (node2 != null && node2.getClassId() == "node.class.BooleanElement") {
	// The method setValue sets the node value. The passed node value must be compatible with the node type. 
	node2.setValue(node1.getValue());
}

var node3 = tree.getRootNode().getNodeByPath("0,2");
if (node3 != null && node3.getClassId() == "node.class.ColorElement") {
	// The method createValueByArgs creates a node value by the arguments.
	// For a color element node there is only one argument, a hexdecimal string representing the colour. 
	node3.setValue(node3.createValueByArgs("FFFF0000"));
}

var node4 = tree.getRootNode().getNodeByPath("0,0,0,0,1");
if (node4 != null && node4.getClassId() == "node.class.PercentageElement") {
	// The method setValueByArgs changes the node value to a new value created by the arguments.
	// For a percentage element node there is only one argument of number type. 
	node4.setValueByArgs(50);
}

var node5 = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,0,0,0,3");
if (node5 != null && node5.getClassId() == "node.class.ComplexElement") {
	// The method setValueByArgs changes the node value to a new value created by the arguments.
	// For a complex element node there are two arguments of number type. 
	node5.setValueByArgs(2.5, 5.0);
}

var node6 = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,0,0,0,1");
if (node6 != null && node6.getClassId() == "node.class.ThresholdElement") {
	// The method setValueByArgs changes the node value to a new value created by the arguments.
	// For a threshold element node there is only one arguments of number type. 
	node6.setValueByArgs(10.5);
}

// If you print the configuration tree now, you can see the modified nodes marked by a *
// context.println(tree.dump());

// The accept method applies the changes to the configuration of the visualization window.
// There is also the method cancel which can be used to cancel the changes.
// tree.cancel();
tree.accept();

// If you print the configuration tree now, you can see the modified nodes were disappeared.
// context.println(tree.dump());
