package com.nextbreakpoint.nextfractal.contextfree.grammar;

enum EArgSource { 
	NoArgs, // shapeType has no arguments
	DynamicArgs, // shapeType has non-constant arguments
	StackArgs, // not shapeType, StackRule* pointer to shape
	SimpleArgs, // shapeType has constant arguments
	ParentArgs, // reusing parent args, child shape may be different
	SimpleParentArgs, // reusing shape args, child shape same as parent
	ShapeArgs // not shapeType, evalArgs arguments (non-constant) to get shape
}