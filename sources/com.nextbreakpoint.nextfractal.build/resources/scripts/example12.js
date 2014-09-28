// Read examples from example01.js to example10.js before to read this example.
// This example shows how to change the palette of a Mandelbrot fractal.

var enumerator = context.getEnumerator("node.class.PaletteRendererFormulaReference");

// The method loadDefaultConfig loads the default configuration.
context.loadDefaultConfig();

// Waits for one second to complete the current fractal.
context.sleep(1000);

// Gets the fractal node in the image of the first layer of the first group.
var fractalNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,0");

// Gets the background node
var backgroundNode = tree.getRootNode().getNodeByPath("0,2");

if (fractalNode != null && fractalNode.getClassId() == 'node.class.MandelbrotFractalElement') {
	
	// Gets the palette node of the first outcolouring formula. 
	var paletteNode = tree.getRootNode().getNodeByPath("0,0,0,0,0,0,0,0,0,3,0,0,0,0,0");
	
	// The method getConstructor returns a constructor object.
	var paletteConstructor = context.getConstructor("type.class.RenderedPalette");
	var paramConstructor = context.getConstructor("type.class.RenderedPaletteParam");
	
	var extensionId1 = enumerator.getExtension("twister.mandelbrot.palette.renderer.formula.sin").getExtensionId();
	var extensionId2 = enumerator.getExtension("twister.mandelbrot.palette.renderer.formula.log").getExtensionId();
	var extensionId3 = enumerator.getExtension("twister.mandelbrot.palette.renderer.formula.lin").getExtensionId();

	// A palette is a sequence of param blocks. A param block has four formulas, a start colour, an end colour and 
	// a length in percentage of the whole palette. The formulas are used to interpolate the four colour components 
	// in the order A,R,G,B. The sum of the lengths must be 100 either some side effects can appear.  
	var param0 = paramConstructor.create(extensionId3, extensionId1, extensionId1, extensionId1, "FF000000", "FF0000FF", 10);
	var param1 = paramConstructor.create(extensionId3, extensionId1, extensionId1, extensionId1, "FF0000FF", "FFFF0000", 30);
	var param2 = paramConstructor.create(extensionId3, extensionId1, extensionId1, extensionId1, "FFFF0000", "FF00FF00", 60);
	var palette = paletteConstructor.create(param0, param1, param2);
	paletteNode.setValueByArgs(palette);
	
	tree.accept();
	
	context.sleep(2000);
	
	// Now we set a grayscale palette (note: colors are in format AARRGGBB). 
	var param0 = paramConstructor.create(extensionId3, extensionId3, extensionId3, extensionId3, "FFFFFFFF", "FF000000", 100);
	var palette = paletteConstructor.create(param0);
	paletteNode.setValueByArgs(palette);
	
	tree.accept();
	
	context.sleep(2000);
	
	// Now we set a palette which has some transparency (note: colors are in format AARRGGBB).
	var param0 = paramConstructor.create(extensionId3, extensionId3, extensionId3, extensionId3, "00000000", "FFFFFFFF", 20);
	var param1 = paramConstructor.create(extensionId3, extensionId3, extensionId3, extensionId3, "FFFFFFFF", "FFFFFFFF", 80);
	var palette = paletteConstructor.create(param0, param1);
	paletteNode.setValueByArgs(palette);

	// The background is visible because the transparency of the palette.
	backgroundNode.setValueByArgs("FFFF0000");
	
	tree.accept();
	
	context.sleep(2000);
}
