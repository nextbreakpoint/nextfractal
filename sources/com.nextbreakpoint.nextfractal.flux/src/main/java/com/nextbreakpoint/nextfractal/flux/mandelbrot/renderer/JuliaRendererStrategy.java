package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;

class JuliaRendererStrategy implements RendererStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		@Override
		public boolean isHorizontalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
		public boolean isVerticalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererPoint.renderer.RenderedPoint)
		 */
		@Override
		public int renderPoint(RendererFractal rendererFractal, RendererPoint p, Number x, Number w) {
//			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
//				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(px);
//				p.xr = pw.r;
//				p.xi = pw.i;
//				p.wr = px.r;
//				p.wi = px.i;
//				p.dr = 0;
//				p.di = 0;
//				p.tr = 0;
//				p.ti = 0;
//				return XaosRenderer.this.renderPoint(p);
//			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
		public void updateParameters() {
//			renderedData.x0 = oldConstant.getX();
//			renderedData.y0 = oldConstant.getY();
		}
	}