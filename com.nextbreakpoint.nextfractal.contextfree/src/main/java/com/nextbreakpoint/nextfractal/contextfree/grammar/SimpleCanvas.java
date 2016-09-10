package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;
import com.nextbreakpoint.nextfractal.contextfree.core.ExtendedGeneralPath;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FlagType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SimpleCanvas {
    private int width;
    private int height;
    private AffineTransform currTransform = new AffineTransform();
    private BufferedImage image;
    private Graphics2D g2d;

    public SimpleCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void primitive(int shapeType, double[] color, AffineTransform transform) {
        g2d.setColor(new Color((float)color[0], (float)color[1], (float)color[2], (float)color[3]));

        AffineTransform oldTransform = g2d.getTransform();

        AffineTransform t = new AffineTransform(currTransform);

        t.concatenate(transform);

        g2d.setTransform(t);

        PrimShape primShape = PrimShape.getShapeMap().get(shapeType);

        if (primShape != null) {
            g2d.fill(primShape.getPath());
        } else {
            throw new RuntimeException("Unexpected shape " + shapeType);
        }

        g2d.setTransform(oldTransform);
    }

    public void path(double[] color, AffineTransform transform, CommandInfo info) {
        g2d.setColor(new Color((float)color[0], (float)color[1], (float)color[2], (float)color[3]));

        AffineTransform oldTransform = g2d.getTransform();

        AffineTransform t = new AffineTransform(currTransform);

        java.awt.Shape shape = null;

        if ((info.getFlags() & FlagType.CF_FILL.getMask()) != 0) {
            shape = info.getPathStorage().getGeneralPath();
            t.concatenate(transform);
        } else if ((info.getFlags() & FlagType.CF_ISO_WIDTH.getMask()) != 0) {
            double scale = Math.sqrt(Math.abs(transform.getDeterminant()));
            g2d.setStroke(new BasicStroke((float)(info.getStrokeWidth() * scale), mapToCap(info.getFlags()), mapToJoin(info.getFlags()), (float)info.getMiterLimit()));
            shape = info.getPathStorage().getGeneralPath();
            t.concatenate(transform);
        } else {
            g2d.setStroke(new BasicStroke((float)info.getStrokeWidth(), mapToCap(info.getFlags()), mapToJoin(info.getFlags()), (float)info.getMiterLimit()));
            shape = info.getPathStorage().getGeneralPath().createTransformedShape(transform);
        }

        g2d.setTransform(t);

        if ((info.getFlags() & FlagType.CF_FILL.getMask()) != 0) {
            g2d.fill(shape);
        } else {
            g2d.draw(shape);
        }

        g2d.setTransform(oldTransform);
    }

    private int mapToJoin(int flags) {
        if ((flags & FlagType.CF_MITER_JOIN.getMask()) != 0) {
            return BasicStroke.JOIN_MITER;
        } else if ((flags & FlagType.CF_ROUND_JOIN.getMask()) != 0) {
            return BasicStroke.JOIN_ROUND;
        } else if ((flags & FlagType.CF_BEVEL_JOIN.getMask()) != 0) {
            return BasicStroke.JOIN_BEVEL;
        } else {
            throw new RuntimeException("Invalid flags " + flags);
        }
    }

    private int mapToCap(int flags) {
        if ((flags & FlagType.CF_BUTT_CAP.getMask()) != 0) {
            return BasicStroke.CAP_BUTT;
        } else if ((flags & FlagType.CF_ROUND_CAP.getMask()) != 0) {
            return BasicStroke.CAP_ROUND;
        } else if ((flags & FlagType.CF_SQUARE_CAP.getMask()) != 0) {
            return BasicStroke.CAP_SQUARE;
        } else {
            throw new RuntimeException("Invalid flags " + flags);
        }
    }

    public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight) {
        g2d = image.createGraphics();
        g2d.setColor(new Color((float)backgroundColor[0], (float)backgroundColor[1], (float)backgroundColor[2], (float)backgroundColor[3]));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        currTransform = AffineTransform.getTranslateInstance(0, getHeight());
        currTransform.scale(1, -1);
        currTransform.translate(-(currWidth - getWidth()) / 2, -(currHeight - getHeight()) / 2);
    }

    public void end() {
        g2d.dispose();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void tileTransform(Bounds bounds) {
        //TODO implementare tileTransform
    }
}
