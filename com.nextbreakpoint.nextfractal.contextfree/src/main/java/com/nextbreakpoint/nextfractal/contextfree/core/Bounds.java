package com.nextbreakpoint.nextfractal.contextfree.core;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CommandInfo;

import java.awt.geom.*;

public class Bounds {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Bounds() {
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
    }

    public Bounds(AffineTransform transform, GeneralPath path, double scale, CommandInfo info) {
        if (!boundingRect(transform, path, scale)) {
            invalidate();
        }
    }

    private boolean boundingRect(AffineTransform transform, GeneralPath path, double scale) {
        java.awt.Shape shape = path.createTransformedShape(transform);

        Rectangle2D bounds = shape.getBounds2D();

        double accuracy = scale * 0.1;

        double pathScale = Math.sqrt(Math.abs(transform.getDeterminant()));

        // TODO completare

        minX = bounds.getMinX();
        minY = bounds.getMinY();
        maxX = bounds.getMaxX();
        maxY = bounds.getMaxY();

        return minX <= maxX && minY <= maxY;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void invalidate() {
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
    }

    public boolean valid() {
        return Double.isFinite(minX) && Double.isFinite(minY) && Double.isFinite(maxX) && Double.isFinite(maxY);
    }

    public Bounds interpolate(Bounds other, double alpha) {
        double beta = 1.0 - alpha;

        Bounds bounds = new Bounds();

        if (!valid() || !other.valid()) return bounds;

        bounds.maxX = beta * maxX + alpha * other.maxX;
        bounds.minX = beta * minX + alpha * other.minX;
        bounds.maxY = beta * maxY + alpha * other.maxY;
        bounds.minY = beta * minY + alpha * other.minY;

        return bounds;
    }

    public Bounds dilate(double dilation) {
        Bounds bounds = new Bounds();

        Point2D.Double center = new Point2D.Double((minX + maxX) * 0.5, (minY + maxY) * 0.5);

        bounds.minX = dilation * (minX - center.x) + center.x;
        bounds.maxX = dilation * (maxX - center.x) + center.x;
        bounds.minY = dilation * (minY - center.y) + center.y;
        bounds.maxY = dilation * (maxY - center.y) + center.y;

        return bounds;
    }

    public Bounds slewCenter(Bounds other, double alpha) {
        Bounds bounds = new Bounds();

        if (!valid() || !other.valid()) return bounds;

        double offsetX = alpha * ((other.maxX + other.minX) - (maxX + minX)) / 2.0;
        double offsetY = alpha * ((other.maxY + other.minY) - (maxY + minY)) / 2.0;

        double absX = Math.abs(offsetX);
        double absY = Math.abs(offsetY);

        bounds.maxX = maxX + absX + offsetX;
        bounds.minX = minX - absX + offsetX;
        bounds.maxY = maxY + absY + offsetY;
        bounds.minY = minY - absY + offsetY;

        return bounds;
    }

    public void gather(Bounds other, double weight) {
        if (!other.valid()) return;

        if (!valid()) {
            maxX = weight * other.maxX;
            minX = weight * other.minX;
            maxY = weight * other.maxY;
            minY = weight * other.minY;
        } else {
            maxX += weight * other.maxX;
            minX += weight * other.minX;
            maxY += weight * other.maxY;
            minY += weight * other.minY;
        }
    }

    public double computeScale(int[] width, int[] height, double borderX, double borderY, boolean modify, AffineTransform transform, boolean exact) {
        double scale = 0;

        double virtualWidth = maxX - minX;
        double virtualHeight = maxY - minY;

        double targetWidth = width[0] - 2.0 * borderX;
        double targetHeight = height[0] - 2.0 * borderY;

        if (!valid()) virtualWidth = virtualHeight = 1.0;

        int newWidth = width[0];
        int newHeight = height[0];

        if (virtualWidth / targetWidth > virtualHeight / targetHeight)
        {
            scale = targetWidth / virtualWidth;
            newHeight = (int)Math.floor(scale * virtualHeight + 2.0 * borderY + 0.5);
            if (!exact) {
                newHeight = newHeight + ((newHeight ^ height[0]) & 0x1);
            }
            if (modify) {
                height[0] = newHeight;
            }
        }
        else {
            scale = targetHeight / virtualHeight;
            newWidth = (int)Math.floor(scale * virtualWidth + 2.0 * borderX + 0.5);
            if (!exact) {
                newWidth = newWidth + ((newWidth ^ width[0]) & 0x1);
            }
            if (modify) {
                width[0] = newWidth;
            }
        }

        if (transform != null) {
            double offsetX = scale * (maxX + minX) / 2.0 - newWidth / 2.0;
            double offsetY = scale * (maxY + minY) / 2.0 - newHeight / 2.0;
            transform.setToScale(scale, scale);
            transform.translate(-offsetX, -offsetY);
        }

        return scale;
    }

    public void update(AffineTransform transform, GeneralPath path, double scale, CommandInfo info) {
        merge(new Bounds(transform, path, scale, info));
    }

    public void merge(Bounds bounds) {
        if (valid() && bounds.valid()) {
            if (bounds.minX < minX) minX = bounds.minX;
            if (bounds.maxX > maxX) maxX = bounds.maxX;
            if (bounds.minY < minY) minY = bounds.minY;
            if (bounds.maxY > maxY) maxY = bounds.maxY;
        } else if (bounds.valid()) {
            minX = bounds.minX;
            maxX = bounds.maxX;
            minY = bounds.minY;
            maxY = bounds.maxY;
        }
    }

    public void merge(double x, double y) {
        if (valid()) {
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        } else {
            minX = maxX = x;
            minY = maxY = y;
        }
    }

    public void merge(Point2D point) {
        merge(point.getX(), point.getY());
    }
}
