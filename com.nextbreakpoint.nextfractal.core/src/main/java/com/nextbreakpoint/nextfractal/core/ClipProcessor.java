package com.nextbreakpoint.nextfractal.core;

import java.util.LinkedList;
import java.util.List;

public class ClipProcessor {
    private List<Clip> clips;
    private int frameCount;
    private float frameRate;

    public ClipProcessor(List<Clip> clips, int frameCount, float frameRate) {
        this.clips = clips;
        this.frameCount = frameCount;
        this.frameRate = frameRate;
    }

    public List<Frame> generateFrames() {
        List<Frame> frames = new LinkedList<>();
        if (clips.size() > 0 && clips.get(0).getEvents().size() > 1) {
            int currentClip = 0;
            int currentEvent = 0;
            int frameIndex = 0;
            ClipEvent event = clips.get(0).getEvents().get(0);
            long baseTime = event.getDate().getTime();
            while (frameIndex < frameCount) {
                while (currentClip < clips.size() && currentEvent < clips.get(currentClip).getEvents().size()) {
                    currentEvent += 1;
                    while (currentClip < clips.size() && currentEvent >= clips.get(currentClip).getEvents().size()) {
                        currentClip += 1;
                        currentEvent = 0;
                        if (currentClip < clips.size() && clips.get(currentClip).getEvents().size() > 0) {
                            ClipEvent firstEvent = clips.get(currentClip).getEvents().get(0);
                            baseTime = firstEvent.getDate().getTime();
                        }
                    }
                    System.out.println("clip " + currentClip + ", event " + currentEvent);
                    if (currentClip < clips.size() && currentEvent < clips.get(currentClip).getEvents().size()) {
                        ClipEvent nextEvent = clips.get(currentClip).getEvents().get(currentEvent);
                        float frameTime = frameIndex * frameRate;
                        while (frameTime < (nextEvent.getDate().getTime() - baseTime) / 1000f) {
                            frames.add(new Frame(event.getPluginId(), event.getScript(), event.getMetadata()));
                            frameIndex += 1;
                            frameTime = frameIndex * frameRate;
                            System.out.println("frame time " + frameIndex + " " + frameTime);
                        }
                        event = nextEvent;
                    } else {
                        frameIndex += 1;
                    }
                }
            }
        }
        return frames;
    }
}
