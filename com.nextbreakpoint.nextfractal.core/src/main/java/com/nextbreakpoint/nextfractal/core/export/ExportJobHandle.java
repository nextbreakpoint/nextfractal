package com.nextbreakpoint.nextfractal.core.export;

public class ExportJobHandle {
    private final ExportJob job;
    private volatile ExportProfile profile;
    private volatile ExportJobState state;
    private volatile Throwable error;

    public ExportJobHandle(ExportJob job) {
        this.job = job;
        this.state = ExportJobState.READY;
    }

    public ExportProfile getProfile() {
        return profile;
    }

    public ExportJobState getState() {
        return state;
    }

    public Throwable getError() {
        return error;
    }

    public ExportJob getJob() {
        return job;
    }

    public void setProfile(ExportProfile profile) {
        this.profile = profile;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setState(ExportJobState state) {
        this.state = state;
    }

    public boolean isInterrupted() {
        return state == ExportJobState.INTERRUPTED;
    }

    public boolean isCompleted() {
        return state == ExportJobState.COMPLETED;
    }

    public boolean isReady() {
        return state == ExportJobState.READY;
    }
}
