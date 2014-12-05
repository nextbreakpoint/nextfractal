package com.nextbreakpoint.nextfractal.flux.render;

import com.nextbreakpoint.nextfractal.core.math.Complex;

class MandelbrotRenderStrategy implements RenderStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.RenderStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
		public boolean isVerticalSymetrySupported() {
//			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
//				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
//				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed()) {
//					return false;
//				}
//			}
//			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
//				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
//				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed()) {
//					return false;
//				}
//			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.RenderStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		@Override
		public boolean isHorizontalSymetrySupported() {
//			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
//				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
//				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
//					return false;
//				}
//			}
//			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
//				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
//				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
//					return false;
//				}
//			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.RenderStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
		 */
		@Override
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
//			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
//				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(pw);
//				p.xr = px.r;
//				p.xi = px.i;
//				p.wr = pw.r;
//				p.wi = pw.i;
//				p.dr = 0;
//				p.di = 0;
//				p.tr = 0;
//				p.ti = 0;
//				return XaosRenderer.this.renderPoint(p);
//			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.RenderStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
		public void updateParameters() {
//			if (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) {
//				renderedData.x0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().r;
//				renderedData.y0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().i;
//			}
//			else {
//				renderedData.x0 = 0;
//				renderedData.y0 = 0;
//			}
		}
	}