package com.nextbreakpoint.nextfractal.contextfree.test;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFColor;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFContext;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFPath;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRenderer;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFReplacement;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFShape;

public class TestCFContext {
	private int shapeTypeCircle;
	private int shapeTypeSquare;
	private int shapeTypeTriangle;
	private int shapeTypeLoopStart;
	private int shapeTypeLoopEnd;
	private CFRule ruleSquare;
	private CFRule ruleCircle;
	private CFRule ruleTriangle;
	private CFContext context;

	@Test
	public void findRule() {
		Assert.assertEquals(0, shapeTypeLoopStart);
		Assert.assertEquals(1, shapeTypeLoopEnd);
		Assert.assertEquals(2, shapeTypeCircle);
		Assert.assertEquals(3, shapeTypeSquare);
		Assert.assertEquals(4, shapeTypeTriangle);
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		int shapeTypeB = context.encodeShapeName("B");
		System.out.println("shapeTypeB = " + shapeTypeB);
		int shapeTypeC = context.encodeShapeName("C");
		System.out.println("shapeTypeC = " + shapeTypeC);
		Assert.assertEquals(5, shapeTypeA);
		Assert.assertEquals(6, shapeTypeB);
		Assert.assertEquals(7, shapeTypeC);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFRule ruleB = new CFRule(shapeTypeB, 1);
		CFRule ruleC = new CFRule(shapeTypeC, 1);
		CFRule ruleD = new CFRule(shapeTypeB, 1);
		context.addRule(ruleA);
		context.addRule(ruleB);
		context.addRule(ruleC);
		context.addRule(ruleD);
		context.reloadRules();
		CFRule rule = null;
		rule = context.findRule(shapeTypeA, 0.5);
		Assert.assertNotNull(rule);
		System.out.println("rule = " + rule.getInitialShapeType());
		Assert.assertEquals(shapeTypeA, rule.getInitialShapeType());
		Assert.assertEquals(ruleA, rule);
		rule = context.findRule(shapeTypeC, 0.5);
		Assert.assertNotNull(rule);
		System.out.println("rule = " + rule.getInitialShapeType());
		Assert.assertEquals(shapeTypeC, rule.getInitialShapeType());
		Assert.assertEquals(ruleC, rule);
		rule = context.findRule(shapeTypeB, 0.3);
		Assert.assertNotNull(rule);
		System.out.println("rule = " + rule.getInitialShapeType());
		Assert.assertEquals(shapeTypeB, rule.getInitialShapeType());
		Assert.assertEquals(ruleB, rule);
		rule = context.findRule(shapeTypeB, 0.7);
		Assert.assertNotNull(rule);
		System.out.println("rule = " + rule.getInitialShapeType());
		Assert.assertEquals(shapeTypeB, rule.getInitialShapeType());
		Assert.assertEquals(ruleD, rule);
	}

	@Test
	public void recursiveRule() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.95f, 0.95f, 1.0f);
		modificationA.translate(1.5f, 0f, 0f);
		modificationA.rotate(4f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeA, 0, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void loopWithOneReplacement() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.95f, 0.95f, 1.0f);
		modificationA.translate(1.5f, 0f, 0f);
		modificationA.rotate(4f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		ruleA.addReplacement(new CFReplacement(shapeTypeLoopStart));
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeLoopEnd, 10, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void loopWithTwoReplacements() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.95f, 0.95f, 1.0f);
		modificationA.translate(1.5f, 0f, 0f);
		modificationA.rotate(4f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		CFModification modificationC = new CFModification();
		modificationC.getColor().setHue(200);
		modificationC.getColor().setBrightness(0.4f);
		modificationC.getColor().setSaturation(0.8f);
		ruleA.addReplacement(new CFReplacement(shapeTypeLoopStart));
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeTriangle, 0, modificationC));
		ruleA.addReplacement(new CFReplacement(shapeTypeLoopEnd, 20, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void brightness() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		int shapeTypeB = context.encodeShapeName("B");
		System.out.println("shapeTypeB = " + shapeTypeA);
		CFRule ruleB = new CFRule(shapeTypeB, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.90f, 0.90f, 1.0f);
		modificationA.translate(1.2f, 0f, 0f);
		modificationA.getColor().setUseTarget(CFColor.BRIGHTNESS_TARGET);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(50);
		modificationB.getColor().setBrightness(0.0f);
		modificationB.getColor().setSaturation(1.0f);
		modificationB.getColorTarget().setBrightness(1.0f);
		ruleA.addReplacement(new CFReplacement(shapeTypeB, 0, modificationB));
		ruleB.addReplacement(new CFReplacement(shapeTypeSquare));
		ruleB.addReplacement(new CFReplacement(shapeTypeB, 0, modificationA));
		context.addRule(ruleA);
		context.addRule(ruleB);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void saturation() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		int shapeTypeB = context.encodeShapeName("B");
		System.out.println("shapeTypeB = " + shapeTypeA);
		CFRule ruleB = new CFRule(shapeTypeB, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.90f, 0.90f, 1.0f);
		modificationA.translate(1.2f, 0f, 0f);
		modificationA.getColor().setUseTarget(CFColor.SATURATION_TARGET);
		modificationA.getColor().setSaturation(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(50);
		modificationB.getColor().setBrightness(1.0f);
		modificationB.getColor().setSaturation(0.0f);
		modificationB.getColorTarget().setSaturation(1);
		ruleA.addReplacement(new CFReplacement(shapeTypeB, 0, modificationB));
		ruleB.addReplacement(new CFReplacement(shapeTypeSquare));
		ruleB.addReplacement(new CFReplacement(shapeTypeB, 0, modificationA));
		context.addRule(ruleA);
		context.addRule(ruleB);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void hue() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		int shapeTypeB = context.encodeShapeName("B");
		System.out.println("shapeTypeB = " + shapeTypeA);
		CFRule ruleB = new CFRule(shapeTypeB, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.98f, 0.98f, 1.0f);
		modificationA.translate(1.2f, 0f, 0f);
		modificationA.getColor().setUseTarget(CFColor.HUE_TARGET);
		modificationA.getColor().setHue(0.05f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(60);
		modificationB.getColor().setBrightness(1.0f);
		modificationB.getColor().setSaturation(1.0f);
		modificationB.getColorTarget().setHue(240);
		ruleA.addReplacement(new CFReplacement(shapeTypeB, 0, modificationB));
		ruleB.addReplacement(new CFReplacement(shapeTypeSquare));
		ruleB.addReplacement(new CFReplacement(shapeTypeB, 0, modificationA));
		context.addRule(ruleA);
		context.addRule(ruleB);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void alpha() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		int shapeTypeB = context.encodeShapeName("B");
		System.out.println("shapeTypeB = " + shapeTypeA);
		CFRule ruleB = new CFRule(shapeTypeB, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.90f, 0.90f, 1.0f);
		modificationA.translate(1.2f, 0f, 0f);
		modificationA.getColor().setUseTarget(CFColor.ALPHA_TARGET);
		modificationA.getColor().setAlpha(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(50);
		modificationB.getColor().setBrightness(1.0f);
		modificationB.getColor().setSaturation(1.0f);
		modificationB.getColorTarget().setAlpha(-1);
		ruleA.addReplacement(new CFReplacement(shapeTypeB, 0, modificationB));
		ruleB.addReplacement(new CFReplacement(shapeTypeSquare));
		ruleB.addReplacement(new CFReplacement(shapeTypeB, 0, modificationA));
		context.addRule(ruleA);
		context.addRule(ruleB);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void background() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.95f, 0.95f, 1.0f);
		modificationA.translate(1.5f, 0f, 0f);
		modificationA.rotate(4f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		context.getBackground().setBrightness(0f);
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeA, 0, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void tile() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.8f, 0.8f, 1.0f);
		modificationA.translate(2.5f, 0f, 0f);
		modificationA.rotate(10f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeA, 0, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		context.setTiled(AffineTransform.getScaleInstance(4, 4), 1, 1, true, false);
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	@Test
	public void size() {
		int shapeTypeA = context.encodeShapeName("A");
		System.out.println("shapeTypeA = " + shapeTypeA);
		CFRule ruleA = new CFRule(shapeTypeA, 1);
		CFModification modificationA = new CFModification();
		modificationA.scale(0.95f, 0.95f, 1.0f);
		modificationA.translate(1.5f, 0f, 0f);
		modificationA.rotate(4f);
		modificationA.getColor().setBrightness(0.1f);
		CFModification modificationB = new CFModification();
		modificationB.getColor().setHue(100);
		modificationB.getColor().setBrightness(0.4f);
		modificationB.getColor().setSaturation(0.8f);
		ruleA.addReplacement(new CFReplacement(shapeTypeSquare, 0, modificationB));
		ruleA.addReplacement(new CFReplacement(shapeTypeA, 0, modificationA));
		context.addRule(ruleA);
		context.reloadRules();
		context.setTiled(AffineTransform.getScaleInstance(50, 50), 0, 0, false, true);
		render(shapeTypeA, new Exception().getStackTrace()[0].getMethodName());
	}

	private void render(int initialShapeType, String suffix) {
		CFRenderer renderer = new CFRenderer(context, 1, 640, 480, 10f, 0.01f);
		CFModification worldState = new CFModification();
		boolean partialDraw = true;
		int reportAt = 250;
		CFShape shape = new CFShape(initialShapeType, worldState);
		shape.getModification().getColorTarget().setAlpha(1);
		shape.getModification().getColor().setAlpha(1);
		renderer.processShape(shape);
		for (;;) {
			if (renderer.getUnfinishedCount() == 0) {
				break;
			}
			if ((renderer.getFinishedCount() + renderer.getUnfinishedCount()) > CFRenderer.MAX_SHAPES) {
				break;
			}
			CFShape s = renderer.nextUnfinishedShape();
			renderer.executeShape(s);
			if (renderer.getFinishedCount() > reportAt) {
				if (partialDraw) {
				}
				reportAt = 2 * renderer.getFinishedCount();
			}
		}
        renderer.sortShapes();
        renderer.dump();
        BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        prepare(g2d);
		renderer.render(g2d, false);
        g2d.dispose();
        try {
			File output = new File("output/test-" + suffix + ".png");
			output.mkdirs();
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
        image.flush();
	}

	@Before
	public void setup() {
		context = new CFContext();
		shapeTypeCircle = context.encodeShapeName("CIRCLE");
		System.out.println("shapeTypeCircle = " + shapeTypeCircle);
		shapeTypeSquare = context.encodeShapeName("SQUARE");
		System.out.println("shapeTypeSquare = " + shapeTypeSquare);
		shapeTypeTriangle = context.encodeShapeName("TRIANGLE");
		System.out.println("shapeTypeTriangle = " + shapeTypeTriangle);
		shapeTypeLoopStart = context.encodeShapeName("~~LOOPSTART~~");
		System.out.println("shapeTypeLoopStart = " + shapeTypeLoopStart);
		shapeTypeLoopEnd = context.encodeShapeName("~~LOOPEND~~");
		System.out.println("shapeTypeLoopEnd = " + shapeTypeLoopEnd);
		ruleSquare = new CFRule(shapeTypeSquare, 1);
		ruleCircle = new CFRule(shapeTypeCircle, 1);
		ruleTriangle = new CFRule(shapeTypeTriangle, 1);
		ruleSquare.setPath(createSquare());
		ruleCircle.setPath(createCircle());
		ruleTriangle.setPath(createTriangle());
		context.addRule(ruleSquare);
		context.addRule(ruleCircle);
		context.addRule(ruleTriangle);
	}
	
	private void prepare(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	}

	private CFPath createSquare() {
		CFPath path = new CFPath();
		path.moveRel(-0.5f, -0.5f);
		path.lineRel(+1f, +0f);
		path.lineRel(+0f, +1f);
		path.lineRel(-1f, +0f);
		path.closePath(false);
		return path;
	}
	
	private CFPath createCircle() {
		CFPath path = new CFPath();
		path.circle();
		return path;
	}
	
	private CFPath createTriangle() {
		float a = (float) (0.5 * Math.tan(Math.PI / 6.0));
		float b = (float) (0.5 * Math.tan(Math.PI / 3.0));
		CFPath path = new CFPath();
		path.moveRel(-0.5f, -a);
		path.lineRel(+1f, +0f);
		path.lineRel(-0.5f, b);
		path.closePath(false);
		return path;
	}
}
