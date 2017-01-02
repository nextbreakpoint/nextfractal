package com.nextbreakpoint.nextfractal.core;

import com.nextbreakpoint.nextfractal.core.utils.Time;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ClipProcessor {
    private static final Logger logger = Logger.getLogger(ClipProcessor.class.getName());

    private List<Clip> clips;
    private int frameCount;
    private float frameRate;

    public ClipProcessor(List<Clip> clips, float frameRate) {
        this.clips = clips;
        this.frameRate = frameRate;
        long duration = clips.stream().mapToLong(clip -> clip.duration()).sum();
        this.frameCount = computeFrameCount(0, duration, frameRate);
    }

    private int computeFrameCount(double startTime, double stopTime, float frameRate) {
        return (int) Math.floor((stopTime - startTime) / frameRate);
    }

    public List<Frame> generateFrames() {
        List<Frame> frames = new LinkedList<>();
        if (clips.size() > 0 && clips.get(0).getEvents().size() > 1) {
            int currentClip = 0;
            int currentEvent = 0;
            int frameIndex = 0;
            float time = 0;
            float prevTime = 0;
            ClipEvent event = clips.get(0).getEvents().get(0);
            long baseTime = event.getDate().getTime();
            logger.fine("0) clip " + currentClip + ", event " + currentEvent);
            Frame lastFrame = null;
            Time lastTime = null;
            while (frameIndex < frameCount && currentClip < clips.size() && currentEvent < clips.get(currentClip).getEvents().size()) {
                currentEvent += 1;
                while (currentClip < clips.size() && currentEvent >= clips.get(currentClip).getEvents().size()) {
                    currentClip += 1;
                    currentEvent = 0;
                    if (currentClip < clips.size() && clips.get(currentClip).getEvents().size() > 0) {
                        baseTime = clips.get(currentClip).getEvents().get(0).getDate().getTime();
                        prevTime = time;
                    }
                    lastFrame = null;
                    lastTime = null;
                }
                logger.fine("1) clip " + currentClip + ", event " + currentEvent);
                if (currentClip < clips.size() && currentEvent < clips.get(currentClip).getEvents().size()) {
                    ClipEvent nextEvent = clips.get(currentClip).getEvents().get(currentEvent);
                    float frameTime = frameIndex * frameRate;
                    time = prevTime + (nextEvent.getDate().getTime() - baseTime) / 1000f;
                    while (frameTime - time < 0.02f) {
                        logger.fine("1) frame " + frameIndex + ", time " + frameTime);
                        Frame frame = new Frame(event.getPluginId(), event.getScript(), event.getMetadata(), true, false);
                        if (lastFrame != null && lastFrame.equals(frame)) {
                            logger.fine("1) not key frame");
                            boolean timeAnimation = lastTime == null || !lastTime.equals(event.getMetadata().getTime());
                            logger.fine("1) time " + event.getMetadata().getTime());
                            frame = new Frame(event.getPluginId(), event.getScript(), event.getMetadata(), false, true);
                        }
                        lastTime = event.getMetadata().getTime();
                        lastFrame = frame;
                        frames.add(frame);
                        frameIndex += 1;
                        frameTime = frameIndex * frameRate;
                    }
                    event = nextEvent;
                } else {
                    float frameTime = frameIndex * frameRate;
                    logger.fine("2) frame " + frameIndex + ", time " + frameTime);
                    Frame frame = new Frame(event.getPluginId(), event.getScript(), event.getMetadata(), true, false);
                    if (lastFrame != null && lastFrame.equals(frame)) {
                        logger.fine("2) not key frame");
                        boolean timeAnimation = lastTime == null || !lastTime.equals(event.getMetadata().getTime());
                        logger.fine("2) time " + event.getMetadata().getTime());
                        frame = new Frame(event.getPluginId(), event.getScript(), event.getMetadata(), false, true);
                    }
                    lastTime = event.getMetadata().getTime();
                    lastFrame = frame;
                    frames.add(frame);
                    frameIndex += 1;
                }
            }
        }
        logger.fine("3) frame count " + frames.size() + ", frame rate " + frameRate);
        logger.info("Generated " + frames.size() + " frames");
        return frames;
    }
}
