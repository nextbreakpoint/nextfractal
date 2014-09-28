// Read examples from example01.js to example10.js before to read this example.
// This example shows how to zoom, rotate and shift the colors of a fractal.

// The method getConstructor returns a constructor object.
var integerVector4DConstructor = context.getConstructor("type.class.IntegerVector4D");
var doubleVector4DConstructor = context.getConstructor("type.class.DoubleVector4D");

// The method create returns a new instance of the object managed by the constructor, created by arguments.
// The status vector is a vector of four integer number which represent the zoom status in four dimensions.
// For Mandelbrot fractals the status is [0, 0, 1, 0] for continuous zoom and [0, 0, 0, 0] for normal zoom. 
var status = integerVector4DConstructor.create(0, 0, 1, 0);

// The method loadDefaultConfig loads the default configuration.
context.loadDefaultConfig();

// Waits for one second to complete the current fractal.
context.sleep(1000);

// Every fractal has a view node which represents the current view. 
var node = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,1");

// The variable running is used to save the current status.
var running = true;

if (node != null && node.getClassId() == 'node.class.ViewElement') {
	if (running) {
		for (j = 0; j < 40; j++) {
			zoom(node, 1 * 0.95);
			tree.accept();
			if (context.sleep(25)) {
				running = false;
				break;
			}
		}
	}
	if (running) {
		for (j = 0; j < 40; j++) {
			zoom(node, 1 / 0.95);
			tree.accept();
			if (context.sleep(25)) {
				running = false;
				break;
			}
		}
	}
}

function zoom(node, scale) {
	// The view contains zoom status, position and rotation of the fractal.  
	var view = node.getValue().toJSObject();
	// The position vector is a vector of four decimal number which represent the fractal position in four dimensions.
	// For Mandelbrot fractals the position is a vector of type [x, y, z, 0] where x, y, z are decimal numbers. 
	// The x and y axis represent the complex plane, the z axis represents the zoom factor and the w axis is not used.  
	var position = view.getPosition();
	// The rotation vector is a vector of four decimal number which represent the fractal rotation in four dimensions.
	// For Mandelbrot fractals the rotation is a vector of type [0, 0, z, w] where z, w are decimal numbers. 
	// The x and y axis are not used, the z axis represents the rotation angle of the complex plane and the w axis represents the color shift.  
	var rotation = view.getRotation();
	// The the zoom factor is multiplied by the scale parameter: if the zoom factor raise the image zoom in, otherwise the image zoom out. 
	position = doubleVector4DConstructor.create(position.getX(), position.getY(), position.getZ() * scale, position.getX());
	node.setValueByArgs(status, position, rotation);
}
