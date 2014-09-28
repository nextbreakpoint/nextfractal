// Read example example11.js before to read this example.
// This example shows how to zoom, rotate and shift the colors of a fractal.

// The method getConstructor returns a constructor object.
var integerVector4DConstructor = context.getConstructor("type.class.IntegerVector4D");
var doubleVector4DConstructor = context.getConstructor("type.class.DoubleVector4D");

// The method loadDefaultConfig loads the default configuration.
context.loadDefaultConfig();

// Waits for one second to complete the current fractal.
context.sleep(1000);

// Every fractal has a view node which represents the current view. 
var node = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,1");

// The variable running is used to save the current status.
var running = true;

if (node != null && node.getClassId() == 'node.class.ViewElement') {

    // The variable view contains the current view.
	var view = node.getValue().toJSObject();
	if (running) {
        // The method create returns a new instance of the object managed by the constructor, created by arguments.
        // The status vector is a vector of four integer number which represent the zoom status in four dimensions.
        // For Mandelbrot fractals the status is [0, 0, 1, 0] for continuous zoom and [0, 0, 0, 0] for normal zoom. 
        // Sets continous zoom which is optimal to zoom and rotate the fractal.
        var status = integerVector4DConstructor.create(0, 0, 1, 0);
		var steps = 200;
		for (var step = 0; step < steps; step++) {
			zoom(node, view, status, step, steps);
			tree.accept();
			if (context.sleep(25)) {
				running = false;
				break;
			}
		}
	}
	if (running) {
        // Waits for two second to complete the current fractal.
		if (context.sleep(2000)) {
			running = false;
		}
    }
	if (running) {
        // Now restores the normal status which is optimal to shift the colours.
        var status = integerVector4DConstructor.create(0, 0, 0, 0);
		var steps = 200;
		for (step = 0; step < steps; step++) {
			shift(node, view, status, step, steps);
			tree.accept();
			if (context.sleep(50)) {
				running = false;
				break;
			}
		}
	}
}

function funPosX(step, steps) {
	return +java.lang.Math.sin((2 * java.lang.Math.PI * step) / (steps - 1));
}

function funPosY(step, steps) {
	return -java.lang.Math.sin((2 * java.lang.Math.PI * step) / (steps - 1));
}

function funPosZ(step, steps) {
	return step < steps / 2 ? 0.99 : 1 / 0.99;
}

function funRotZ(step, steps) {
	return java.lang.Math.PI / 20;
}

function funRotW(step, steps) {
	return step * 2;
}

function zoom(node, oldView, status, step, steps) {
	var view = node.getValue().toJSObject();
	var position = doubleVector4DConstructor.create(oldView.getPosition().getX() + funPosX(step, steps), oldView.getPosition().getY(), view.getPosition().getZ() * funPosZ(step, steps), 0);
	var rotation = doubleVector4DConstructor.create(0, 0, view.getRotation().getZ() + funRotZ(step, steps), 0);
	node.setValueByArgs(status, position, rotation);
}

function shift(node, oldView, status, step, steps) {
	var view = node.getValue().toJSObject();
	var rotation = doubleVector4DConstructor.create(view.getRotation().getX(), view.getRotation().getY(), view.getRotation().getZ(), oldView.getRotation().getW() + funRotW(step, steps));
	node.setValueByArgs(status, view.getPosition(), rotation);
}
