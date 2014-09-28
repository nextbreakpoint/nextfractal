// Read examples from example01.js to example10.js before to read this example.
// This example shows how to discover Julia sets from Mandelbrot Set.

// The variable running is used to save the current status.
var running = true;

// The method loadDefaultConfig loads the default configuration.
context.loadDefaultConfig();

// Waits for one second to complete the current fractal.
context.sleep(1000);

// Gets the fractal node in the image of the first layer of the first group.
var fractalNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,0");

if (fractalNode != null && fractalNode.getClassId() == 'node.class.MandelbrotFractalElement') {

	// Gets the show preview node.
	var showPreviewNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,6");

	if (showPreviewNode != null && showPreviewNode.getClassId() == 'node.class.BooleanElement') {
		// Sets the show preview value.
		showPreviewNode.setValueByArgs(true);
	}

	// Gets the show orbit node.
	var showOrbitNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,5");

	if (showOrbitNode != null && showOrbitNode.getClassId() == 'node.class.BooleanElement') {
		// Sets the show orbit value.
		showOrbitNode.setValueByArgs(true);
	}

	// Gets the image mode node.
	var imageModeNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,2");

	if (imageModeNode != null && imageModeNode.getClassId() == 'node.class.ImageModeElement') {
		// Sets the image mode value (0 = Mandelbrot fractal, 1 = Julia fractal).
		imageModeNode.setValueByArgs(0);
	}
	
	// Gets the complex constant node.
	var constantNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,4");
	
	if (constantNode != null && constantNode.getClassId() == 'node.class.ComplexElement') {
		if (running) {
			var steps = 200;
			for (var step = 0; step < steps; step++) {
				// Sets the complex constant value.
				constantNode.setValueByArgs(funPointX(step, steps), funPointY(step, steps));
				tree.accept();
				if (context.sleep(100)) {
					running = false;
					break;
				}
				if (step > 0 && step % 10 == 0) {
					// Switches to Julia fractal. 
					showPreviewNode.setValueByArgs(false);
					showOrbitNode.setValueByArgs(false);
					imageModeNode.setValueByArgs(1);
					tree.accept();
					if (context.sleep(1500)) {
						running = false;
						break;
					}
					// Switches to Mandelbrot fractal.
					showPreviewNode.setValueByArgs(true);
					showOrbitNode.setValueByArgs(true);
					imageModeNode.setValueByArgs(0);
					tree.accept();
					if (context.sleep(100)) {
						running = false;
						break;
					}
				}
			}
		}
	}
}

function funPointX(step, steps) {
	return -0.25 + 0.8 * java.lang.Math.cos((2 * java.lang.Math.PI * step) / (steps - 1));
}

function funPointY(step, steps) {
	return +0.05 + 0.6 * java.lang.Math.sin((2 * java.lang.Math.PI * step) / (steps - 1));
}
