package com.nextbreakpoint.nextfractal;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportJob {
	private final ExportSession session;
	private final ExportProfile profile;
	private volatile ExportResult result;
	private volatile JobState state = JobState.READY;

	public ExportJob(ExportSession session, ExportProfile profile) {
		this.session = session;
		this.profile = profile;
	}

	public ExportProfile getProfile() {
		return profile;
	}
	
	public RendererTile getTile() {
		return profile.createTile();
	}

	public ExportResult getResult() {
		return result;
	}

	public void setResult(ExportResult result) {
		this.result = result;
	}

	public boolean isInterrupted() {
		return session.getState() == SessionState.PENDING_INTERRUPT || session.getState() == SessionState.PENDING_SUSPEND;
	}

	public String getPluginId() {
		return profile.getPluginId();
	}
	
	public JobState getState() {
		return state;
	}
	
	public void setState(JobState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "[sessionId = " + session.getSessionId() + ", profile=" + profile + ", state=" + state + "]";
	}
}
