// Read example05.js before to read this example.
// This example shows how to browse the configuration tree.

// The method getRootNode returns the root node of the configuration tree.
// The method getNodeByPath returns the node which has the specified path.
// This method returns null if the node doesn't exist. 
var node = tree.getRootNode().getNodeByPath("0,1,1");
// The method getClassId returns the classId of the node.
if (node != null && node.getClassId() == "node.class.BooleanElement") {
	// The method getId returns the name of the node.
	context.println("The node " + node.getId() + " is a node of class " + node.getClassId());
	// The method getLabel returns the label of the node.
	context.println("The node label is \"" + node.getLabel() + "\"");
	// The method getPath returns the path of the node.
	context.println("The node path is [" + node.getPath() + "]");
	// The method isAttribute returns true for attribute nodes.
	context.println("The node " + (node.isAttribute() ? "is" : "isn't") + " an attribute");
	// The method isEditable returns true for editable nodes (nodes which value could be changed).
	context.println("The node " + (node.isEditable() ? "is" : "isn't") + " editable");
	// The method isEditable returns true for mutable nodes (nodes which children could be added or removed).
	context.println("The node " + (node.isMutable() ? "is" : "isn't") + " mutable");
	// The method hasValue returns true if the node has a not null value.
	context.println("The node " + (node.hasValue() ? "has" : "hasn't") + " a value");
	// The method getIndex returns the position of the node in the parent's children list.
	context.println("The node index is " + node.getIndex());
	// The method getParentNode returns the parent node. This method returns null for the root node. 
	context.println("The parent node is " + node.getParentNode().getId());
	context.println("The class of the node is " + node.getClass());
	context.println("The string representing the node is " + node.toString());
	// The method getValue returns a NodeValue instance. 
	// This method returns every times a new instance (nodeValue can't be null).    
	var nodeValue = node.getValue();
	context.println("The class of the node value is " + nodeValue.getClass());
	context.println("The string representing the node value is " + nodeValue.toString());
	// The method toJSObject returns the object contained in the NodeValue instance. 
	var jsObject = nodeValue.toJSObject();  
	if (jsObject != null) {
		context.println("The class of the wrapped object is " + jsObject.getClass());
		context.println("The string representing the wrapped object is " + jsObject.toString());
	}
}
