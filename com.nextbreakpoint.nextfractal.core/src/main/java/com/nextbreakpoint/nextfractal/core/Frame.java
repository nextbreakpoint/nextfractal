package com.nextbreakpoint.nextfractal.core;

public class Frame {
    private String pluginId;
    private String script;
    private Object metadata;
    private boolean keyFrame;

    public Frame(String pluginId, String script, Object metadata) {
        this(pluginId, script, metadata, false);
    }

    public Frame(String pluginId, String script, Object metadata, boolean keyFrame) {
        this.pluginId = pluginId;
        this.script = script;
        this.metadata = metadata;
        this.keyFrame = keyFrame;
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getScript() {
        return script;
    }

    public Object getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame that = (Frame) o;

        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;
        if (script != null ? !script.equals(that.script) : that.script != null) return false;
        return metadata != null ? metadata.equals(that.metadata) : that.metadata == null;
    }

    @Override
    public int hashCode() {
        int result = pluginId != null ? pluginId.hashCode() : 0;
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }

    public boolean isKeyFrame() {
        return keyFrame;
    }
}
