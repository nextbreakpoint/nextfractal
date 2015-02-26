package com.nextbreakpoint.nextfractal;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportJob {
	private final ExportSession session;
	private final ExportProfile profile;
	private volatile ExportResult result;

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

	public String getPluginId() {
		return profile.getPluginId();
	}
	
	public boolean isCompleted() {
		return getResult() != null && getResult().getPixels() != null;
	}
	
	public boolean isInterrupted() {
		return getResult() != null && getResult().getPixels() == null;
	}
	
	@Override
	public String toString() {
		return "[sessionId = " + session.getSessionId() + ", profile=" + profile + "]";
	}
}
