package com.nextbreakpoint.nextfractal.core;

public class Frame {
    private String pluginId;
    private String script;
    private Object metadata;

    public Frame(String currentPluginId, String currentScript, Object currentMetadata) {
        this.pluginId = currentPluginId;
        this.script = currentScript;
        this.metadata = currentMetadata;
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
}
