package com.nextbreakpoint.nextfractal.contextfree.renderer;

import com.nextbreakpoint.nextfractal.contextfree.core.ExtendedGeneralPath;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFCanvas;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CommandInfo;
import com.nextbreakpoint.nextfractal.contextfree.grammar.PrimShape;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FlagType;
import com.nextbreakpoint.nextfractal.core.renderer.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import static com.nextbreakpoint.nextfractal.contextfree.core.ExtendedPathIterator.*;

public class RendererCanvas implements CFCanvas {
    private RendererGraphicsContext context;
    private RendererFactory factory;
    private int width;
    private int height;
    private RendererAffine normTransform;

    public RendererCanvas(RendererFactory factory, Object context, int width, int height) {
        this.context = factory.createGraphicsContext(context);
        this.factory = factory;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void primitive(int shapeType, double[] color, AffineTransform transform) {
        context.save();

        RendererAffine affine = factory.createAffine();
        affine.append(normTransform);

        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        RendererAffine newAffine = factory.createAffine(matrix);
        affine.append(newAffine);

        affine.setAffine(context);

        PrimShape primShape = PrimShape.getShapeMap().get(shapeType);
        if (primShape != null) {
            RendererColor c = factory.createColor((float) color[0], (float) color[1], (float) color[2], (float) color[3]);
            context.setFill(c);
            fill(primShape.getPath(), RendererGraphicsContext.EVEN_ODD);
        } else {
            throw new RuntimeException("Unexpected shape " + shapeType);
        }

        context.restore();
    }

    public void path(double[] color, AffineTransform transform, CommandInfo info) {
        context.save();

        RendererAffine affine = factory.createAffine();
        affine.append(normTransform);

        Shape shape;

        GeneralPath path = info.getPathStorage().getGeneralPath();

        if ((info.getFlags() & (FlagType.CF_EVEN_ODD.getMask() | FlagType.CF_FILL.getMask())) == (FlagType.CF_EVEN_ODD.getMask() | FlagType.CF_FILL.getMask())) {
            path.setWindingRule(RendererGraphicsContext.EVEN_ODD);
        } else {
            path.setWindingRule(RendererGraphicsContext.NON_ZERO);
        }

        if ((info.getFlags() & FlagType.CF_FILL.getMask()) != 0) {
            shape = path;
            double[] matrix = new double[6];
            transform.getMatrix(matrix);
            RendererAffine newAffine = factory.createAffine(matrix);
            affine.append(newAffine);
        } else if ((info.getFlags() & FlagType.CF_ISO_WIDTH.getMask()) != 0) {
            double scale = Math.sqrt(Math.abs(transform.getDeterminant()));
            context.setStrokeLine((float)(info.getStrokeWidth() * scale), mapToCap(info.getFlags()), mapToJoin(info.getFlags()), (float)info.getMiterLimit());
            shape = path;
            double[] matrix = new double[6];
            transform.getMatrix(matrix);
            RendererAffine newAffine = factory.createAffine(matrix);
            affine.append(newAffine);
        } else {
            context.setStrokeLine((float)info.getStrokeWidth(), mapToCap(info.getFlags()), mapToJoin(info.getFlags()), (float)info.getMiterLimit());
            shape = path.createTransformedShape(transform);
        }

        affine.setAffine(context);

        RendererColor c = factory.createColor((float) color[0], (float) color[1], (float) color[2], (float) color[3]);
        if ((info.getFlags() & FlagType.CF_FILL.getMask()) != 0) {
            context.setFill(c);
            fill(shape, path.getWindingRule());
        } else {
            context.setStroke(c);
            stroke(shape, path.getWindingRule());
        }

        context.restore();
    }

    private void fill(Shape path, int windingRule) {
        context.beginPath();
        createPath(path);
        context.setWindingRule(windingRule);
        context.fill();
    }

    private void stroke(Shape path, int windingRule) {
        context.beginPath();
        createPath(path);
        context.setWindingRule(windingRule);
        context.stroke();
    }

    private void createPath(Shape path) {
        PathIterator iterator = path.getPathIterator(new AffineTransform());
        float[] coords = new float[20];
        while (!iterator.isDone()) {
            int code = iterator.currentSegment(coords);
            switch (code) {
                case SEG_MOVETO: {
                    context.moveTo(coords[0], coords[1]);
                    break;
                }
                case SEG_LINETO: {
                    context.lineTo(coords[0], coords[1]);
                    break;
                }
                case SEG_QUADTO: {
                    context.quadTo(coords[0], coords[1], coords[2], coords[3]);
                    break;
                }
                case SEG_CUBICTO: {
                    context.cubicTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                    break;
                }
//                case SEG_ARCTO: {
//                    context.arcTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5], coords[6]);
//                    break;
//                }
                case SEG_CLOSE: {
                    context.closePath();
                    break;
                }
            }
            iterator.next();
        }
    }

    private int mapToJoin(int flags) {
        if ((flags & FlagType.CF_MITER_JOIN.getMask()) != 0) {
            return RendererGraphicsContext.JOIN_MITER;
        } else if ((flags & FlagType.CF_ROUND_JOIN.getMask()) != 0) {
            return RendererGraphicsContext.JOIN_ROUND;
        } else if ((flags & FlagType.CF_BEVEL_JOIN.getMask()) != 0) {
            return RendererGraphicsContext.JOIN_BEVEL;
        } else {
            throw new RuntimeException("Invalid flags " + flags);
        }
    }

    private int mapToCap(int flags) {
        if ((flags & FlagType.CF_BUTT_CAP.getMask()) != 0) {
            return RendererGraphicsContext.CAP_BUTT;
        } else if ((flags & FlagType.CF_ROUND_CAP.getMask()) != 0) {
            return RendererGraphicsContext.CAP_ROUND;
        } else if ((flags & FlagType.CF_SQUARE_CAP.getMask()) != 0) {
            return RendererGraphicsContext.CAP_SQUARE;
        } else {
            throw new RuntimeException("Invalid flags " + flags);
        }
    }

    public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight) {
        RendererColor c = factory.createColor((float) backgroundColor[0], (float) backgroundColor[1], (float) backgroundColor[2], (float) backgroundColor[3]);
        context.setFill(c);
        context.fillRect(0, 0, getWidth(), getHeight());
        normTransform = factory.createTranslateAffine(0, getHeight());
        normTransform.append(factory.createScaleAffine(1, -1));
        normTransform.append(factory.createTranslateAffine(-(currWidth - getWidth()) / 2, -(currHeight - getHeight()) / 2));
    }

    public void end() {
    }
}