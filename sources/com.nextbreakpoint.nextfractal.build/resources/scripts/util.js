// This script contains some useful functions.

function printExtensions(enumerator) {
	context.println("Available extensions for enumerator " + extension.getExtensionId() + ":");
	var extensions = enumerator.listExtensions();
	for (var i = 0; i < extensions.size(); i++) {
		context.println(extensions.get(i));
	}
}

function dumpTree() {
	context.println(tree.dump());
}

function show(message) {
    context.showMessage(message, 4, 5, 92, 1000, true);
}

