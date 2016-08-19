package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.core.AffineTransformTime;
import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;

import java.util.*;
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
        if (rti.isRequestStop() || rti.isRequestFinishUp()) {
            throw  new StopException();
        }

        if (scale == 0.0) {
            scale = (width + height) / Math.sqrt(Math.abs(shape.getWorldState().getTransform().getDeterminant()));
        }

        // TODO rivedere

        AffineTransformTime frameTime = shape.getWorldState().getTransformTime();

        frameTime.translate(-timeBounds.getBegin());
        frameTime.scale(frameScale);

        int begin = frameTime.getBegin() < frames ? (int)Math.floor(frameTime.getBegin()) : (frames - 1);
        int end = frameTime.getEnd() < frames ? (int)Math.floor(frameTime.getEnd()) : (frames - 1);

        for (int frame = begin; frame <= end; frame++) {
            frameBounds[frame].merge(shape.bounds());
        }

        frameCounts[begin] += 1;
    }

    public void finalAccumulate() {
    }

    public void backwardFilter(double framesToHalf) {
        // TODO rivedere
        int frames = frameBounds.length;
        if (frames == 0) {
            return;
        }
        double alpha = Math.pow(0.5, 1.0 / framesToHalf);
        Bounds prev = frameBounds[frameBounds.length - 1];
        for (int i = frameBounds.length - 2; i >= 0; i--) {
            Bounds curr = frameBounds[i];
            frameBounds[i] = curr.interpolate(prev, alpha);
            prev = curr;
        }
    }

    public void smooth(int window) {
        int frames = frameBounds.length;

        if (frames == 0) {
            return;
        }

        Bounds[] backFrameBounds = frameBounds;
        frameBounds = new Bounds[frames + window - 1];
        System.arraycopy(backFrameBounds, 0, frameBounds, 0, frames);

        double factor = 1.0 - window;

        //TODO completare
    }
}
