// Read example04.js before to read this example.
// This example shows how to print the configuration tree.

// The object tree is provided by the NextFractal JavaScript API.

// The method dump returns a string representing the configuration tree.
context.println(tree.dump());

// Every node of the configuration tree is represented by a line
// containing the classId, the name, the value and the path of the node.
// Every node can have many children node; the path of a children
// is composed appending to the path of the parent node the position 
// of the children in the list. The root node is a special node which
// contains the main configuration node (Twister is the name of the
// graphics engine of NextFractal). This is an example of the printed output:
/*
node.class.Root (root) {
 node.class.TwisterConfig (node.twister.TwisterConfig) path = [0] {
  node.class.FrameElement (Frame) path = [0,0] {
   node.class.GroupLayerElementList (Frame.layers) path = [0,0,0] {
    node.class.GroupLayerElement (GroupLayer) path = [0,0,0,0] {
     node.class.ImageLayerElementList (GroupLayer.layers) path = [0,0,0,0,0] {
      node.class.ImageLayerElement (ImageLayer) path = [0,0,0,0,0,0] {
       node.class.ImageElement (Image) path = [0,0,0,0,0,0,0] {
        node.class.ImageReference (Image.extension) path = [0,0,0,0,0,0,0,0] {
         node.class.MandelbrotFractalElement (MandelbrotFractal) path = [0,0,0,0,0,0,0,0,0] {
          node.class.RenderingFormulaElement (RenderingFormula) path = [0,0,0,0,0,0,0,0,0,0] {
           node.class.RenderingFormulaReference (RenderingFormula.extension) path = [0,0,0,0,0,0,0,0,0,0,0] {
            node.class.IterationsElement (twister.mandelbrot.fractal.rendering.formula.z2.iterations) value = [200] path = [0,0,0,0,0,0,0,0,0,0,0,0]
            node.class.ThresholdElement (twister.mandelbrot.fractal.rendering.formula.z2.threshold) value = [40.0] path = [0,0,0,0,0,0,0,0,0,0,0,1]
            node.class.ComplexElement (twister.mandelbrot.fractal.rendering.formula.z2.center) value = [0.0, 0.0] path = [0,0,0,0,0,0,0,0,0,0,0,2]
            node.class.ComplexElement (twister.mandelbrot.fractal.rendering.formula.z2.scale) value = [5.0, 5.0] path = [0,0,0,0,0,0,0,0,0,0,0,3]
           }
          }
          node.class.TransformingFormulaElement (TransformingFormula) path = [0,0,0,0,0,0,0,0,0,1] {
           node.class.TransformingFormulaReference (TransformingFormula.extension) path = [0,0,0,0,0,0,0,0,0,1,0]
          }
          node.class.IncolouringFormulaElementList (MandelbrotFractal.incolouringFormulas) path = [0,0,0,0,0,0,0,0,0,2] {
           node.class.IncolouringFormulaElement (IncolouringFormula) path = [0,0,0,0,0,0,0,0,0,2,0] {
            node.class.IncolouringFormulaReference (IncolouringFormula.extension) path = [0,0,0,0,0,0,0,0,0,2,0,0] {
             node.class.ColorElement (twister.mandelbrot.fractal.incolouring.formula.color.color) value = [0xFF000000] path = [0,0,0,0,0,0,0,0,0,2,0,0,0]
            }
            node.class.BooleanElement (IncolouringFormula.locked) value = [false] path = [0,0,0,0,0,0,0,0,0,2,0,1]
            node.class.BooleanElement (IncolouringFormula.enabled) value = [true] path = [0,0,0,0,0,0,0,0,0,2,0,2]
            node.class.PercentageElement (IncolouringFormula.opacity) value = [100] path = [0,0,0,0,0,0,0,0,0,2,0,3]
            node.class.IterationsElement (IncolouringFormula.iterations) value = [200] path = [0,0,0,0,0,0,0,0,0,2,0,4]
            node.class.BooleanElement (IncolouringFormula.autoIterations) value = [true] path = [0,0,0,0,0,0,0,0,0,2,0,5]
            node.class.StringElement (IncolouringFormula.label) value = [New Incolouring Formula] path = [0,0,0,0,0,0,0,0,0,2,0,6]
           }
          }
          node.class.OutcolouringFormulaElementList (MandelbrotFractal.outcolouringFormulas) path = [0,0,0,0,0,0,0,0,0,3] {
           node.class.OutcolouringFormulaElement (OutcolouringFormula) path = [0,0,0,0,0,0,0,0,0,3,0] {
            node.class.OutcolouringFormulaReference (OutcolouringFormula.extension) path = [0,0,0,0,0,0,0,0,0,3,0,0] {
             node.class.PaletteRendererElement (PaletteRenderer) path = [0,0,0,0,0,0,0,0,0,3,0,0,0] {
              node.class.PaletteRendererReference (PaletteRenderer.extension) path = [0,0,0,0,0,0,0,0,0,3,0,0,0,0] {
               node.class.RenderedPaletteElement (twister.mandelbrot.palette.renderer.default.palette) value = [[3 blocks]] path = [0,0,0,0,0,0,0,0,0,3,0,0,0,0,0]
              }
             }
            }
            node.class.BooleanElement (OutcolouringFormula.locked) value = [false] path = [0,0,0,0,0,0,0,0,0,3,0,1]
            node.class.BooleanElement (OutcolouringFormula.enabled) value = [true] path = [0,0,0,0,0,0,0,0,0,3,0,2]
            node.class.PercentageElement (OutcolouringFormula.opacity) value = [100] path = [0,0,0,0,0,0,0,0,0,3,0,3]
            node.class.IterationsElement (OutcolouringFormula.iterations) value = [400] path = [0,0,0,0,0,0,0,0,0,3,0,4]
            node.class.BooleanElement (OutcolouringFormula.autoIterations) value = [false] path = [0,0,0,0,0,0,0,0,0,3,0,5]
            node.class.StringElement (OutcolouringFormula.label) value = [New Outcolouring Formula] path = [0,0,0,0,0,0,0,0,0,3,0,6]
           }
          }
          node.class.ProcessingFormulaElement (ProcessingFormula) path = [0,0,0,0,0,0,0,0,0,4] {
           node.class.ProcessingFormulaReference (ProcessingFormula.extension) path = [0,0,0,0,0,0,0,0,0,4,0]
          }
          node.class.OrbitTrapElement (OrbitTrap) path = [0,0,0,0,0,0,0,0,0,5] {
           node.class.OrbitTrapReference (OrbitTrap.extension) path = [0,0,0,0,0,0,0,0,0,5,0]
           node.class.ComplexElement (OrbitTrap.center) value = [0.0, 0.0] path = [0,0,0,0,0,0,0,0,0,5,1]
          }
         }
         node.class.ViewElement (attribute.view) path = [0,0,0,0,0,0,0,0,1]
         node.class.ImageModeElement (attribute.imageMode) value = [Mandelbrot] path = [0,0,0,0,0,0,0,0,2]
         node.class.InputModeElement (attribute.inputMode) value = [Zoom] path = [0,0,0,0,0,0,0,0,3]
         node.class.ComplexElement (attribute.constant) value = [0.0, 0.0] path = [0,0,0,0,0,0,0,0,4]
         node.class.BooleanElement (attribute.showOrbit) value = [false] path = [0,0,0,0,0,0,0,0,5]
         node.class.BooleanElement (attribute.showPreview) value = [true] path = [0,0,0,0,0,0,0,0,6]
         node.class.RectangleElement (attribute.previewArea) value = [0.73, 0.03, 0.25, 0.25] path = [0,0,0,0,0,0,0,0,7]
         node.class.SpeedElement (attribute.speed) path = [0,0,0,0,0,0,0,0,8]
         node.class.BooleanElement (attribute.showOrbitTrap) value = [false] path = [0,0,0,0,0,0,0,0,9]
        }
       }
       node.class.PercentageElement (ImageLayer.opacity) value = [100] path = [0,0,0,0,0,0,1]
       node.class.BooleanElement (ImageLayer.locked) value = [false] path = [0,0,0,0,0,0,2]
       node.class.BooleanElement (ImageLayer.visible) value = [true] path = [0,0,0,0,0,0,3]
       node.class.StringElement (ImageLayer.label) value = [New Layer] path = [0,0,0,0,0,0,4]
       node.class.LayerFilterElementList (ImageLayer.layers) path = [0,0,0,0,0,0,5]
      }
     }
     node.class.PercentageElement (GroupLayer.opacity) value = [100] path = [0,0,0,0,1]
     node.class.BooleanElement (GroupLayer.locked) value = [false] path = [0,0,0,0,2]
     node.class.BooleanElement (GroupLayer.visible) value = [true] path = [0,0,0,0,3]
     node.class.StringElement (GroupLayer.label) value = [New Group] path = [0,0,0,0,4]
     node.class.LayerFilterElementList (GroupLayer.layers) path = [0,0,0,0,5]
    }
   }
   node.class.FrameFilterElementList (Frame.filters) path = [0,0,1]
  }
  node.class.EffectElement (Effect) path = [0,1] {
   node.class.EffectReference (Effect.extension) path = [0,1,0]
   node.class.BooleanElement (Effect.enabled) value = [true] path = [0,1,1]
   node.class.BooleanElement (Effect.locked) value = [false] path = [0,1,2]
  }
  node.class.ColorElement (node.twister.TwisterConfig.background) value = [0xFF000000] path = [0,2]
 }
}
*/