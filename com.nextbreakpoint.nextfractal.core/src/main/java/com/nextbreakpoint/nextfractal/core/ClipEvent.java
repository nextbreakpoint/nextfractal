package com.nextbreakpoint.nextfractal.core;

import java.util.Date;
import java.util.Objects;

public class ClipEvent {
    private Date date;
    private String pluginId;
    private String script;
    private Object metadata;

    public ClipEvent(Date date, String pluginId, String script, Object metadata) {
        this.date = Objects.requireNonNull(date);
        this.pluginId = Objects.requireNonNull(pluginId);
        this.script = Objects.requireNonNull(script);
        this.metadata = Objects.requireNonNull(metadata);
    }

    public Date getDate() {
        return date;
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
}
