package com.nextbreakpoint.nextfractal.runtime.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTransform;

public class TestRendererTransform {
	@Test
	public void given_new_transform_should_be_identity() {
		RendererTransform t = new RendererTransform();
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(1, p[0], 0.00001);
		assertEquals(1, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_translate_should_return_traslated_point() {
		RendererTransform t = new RendererTransform();
		t.traslate(10, 20);
		double[] p = makePoint(0, 0);
		t.transform(p);
		assertEquals(10, p[0], 0.00001);
		assertEquals(20, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_scale_should_return_scaled_point() {
		RendererTransform t = new RendererTransform();
		t.scale(10, 20);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(10, p[0], 0.00001);
		assertEquals(20, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_rotate_by_90_degrees_should_return_rotated_point() {
		RendererTransform t = new RendererTransform();
		t.rotate(Math.PI / 2);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(-1, p[0], 0.00001);
		assertEquals(1, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_rotate_by_180_degrees_should_return_rotated_point() {
		RendererTransform t = new RendererTransform();
		t.rotate(Math.PI);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(-1, p[0], 0.00001);
		assertEquals(-1, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_rotate_should_return_rotated_point() {
		RendererTransform t = new RendererTransform();
		double a = Math.PI / 7;
		t.rotate(a);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(Math.cos(a)-Math.sin(a), p[0], 0.00001);
		assertEquals(Math.sin(a)+Math.cos(a), p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_rotate_and_scale_should_return_rotated_and_scaled_point() {
		RendererTransform t = new RendererTransform();
		double a = Math.PI / 7;
		t.rotate(a);
		t.scale(2,2);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(2*(Math.cos(a)-Math.sin(a)), p[0], 0.00001);
		assertEquals(2*(Math.sin(a)+Math.cos(a)), p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_rotate_and_scale_and_traslate_should_return_rotated_and_scaled_and_traslated_point() {
		RendererTransform t = new RendererTransform();
		double a = Math.PI / 2;
		t.rotate(a);
		t.scale(2,2);
		t.traslate(1,1);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(-4, p[0], 0.00001);
		assertEquals(4, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_traslate_and_rotate_and_scale_should_return_traslated_and_rotated_and_scaled_point() {
		RendererTransform t = new RendererTransform();
		double a = Math.PI / 2;
		t.traslate(1,1);
		t.rotate(a);
		t.scale(2,2);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(-1, p[0], 0.00001);
		assertEquals(3, p[1], 0.00001);
	}

	@Test
	public void given_new_transform_when_concat_should_return_traslated_and_rotated_and_scaled_point() {
		RendererTransform t = new RendererTransform();
		double a = Math.PI / 2;
		RendererTransform tt = RendererTransform.newTraslate(1, 1);
		RendererTransform rt = RendererTransform.newRotate(a);
		RendererTransform st = RendererTransform.newScale(2, 2);
		t.concat(tt);
		t.concat(rt);
		t.concat(st);
		double[] p = makePoint(1, 1);
		t.transform(p);
		assertEquals(-1, p[0], 0.00001);
		assertEquals(3, p[1], 0.00001);
	}

	private double[] makePoint(int x, int y) {
		return new double[] { x, y };
	}
}
