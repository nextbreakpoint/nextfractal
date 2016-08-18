package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.AffineTransformTime;
import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;
import java.util.List;
import java.util.stream.IntStream;

public class OutputBounds {
    private AffineTransformTime timeBounds;
    private double frameScale;
    private Bounds[] frameBounds;
    private Integer[] frameCounts;
    private double scale;
    private int width;
    private int height;
    private int frames;
    private RTI rti;

    public OutputBounds(int frames, AffineTransformTime timeBounds, int width, int height, RTI rti) {
        this.frames = frames;
        this.timeBounds = timeBounds;
        this.width = width;
        this.height = height;
        this.rti = rti;

        frameScale = (double)frames / (timeBounds.getEnd() / timeBounds.getBegin());
        frameBounds = new Bounds[frames];
        frameCounts = new Integer[frames];

        IntStream.range(0, frames).forEach(i -> frameBounds[i] = null);
        IntStream.range(0, frames).forEach(i -> frameCounts[i] = 0);
    }

    public void apply(FinishedShape shape) {
        // TODO Auto-generated method stub
    }

    public void finalAccumulate() {
        // TODO Auto-generated method stub
    }

    public void backwardFilter(double framesToHalf) {
        // TODO Auto-generated method stub
    }

    public void smooth(int window) {
        // TODO Auto-generated method stub
    }

    public void animate(Canvas canvas, int frames, boolean zoom) {
        // TODO Auto-generated method stub
    }
}
