package com.nextbreakpoint.nextfractal;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportJob {
	private ExportSession session;
	private ExportProfile profile;
	private ExportResult result;
	private boolean completed;
	
	public ExportJob(ExportSession session) {
		this.session = session;
	}

	public boolean isSuspended() {
		return session.isSuspended();
	}
	
	public boolean isTerminated() {
		return session.isTerminated();
	}
	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean terminated) {
		this.completed = terminated;
	}
	
	public ExportProfile getProfile() {
		return profile;
	}

	public void setProfile(ExportProfile profile) {
		this.profile = profile;
	}

	public ExportResult getResult() {
		return result;
	}

	public void setResult(ExportResult result) {
		this.result = result;
	}

	public RendererTile getTile() {
		return profile.createTile();
	}
}
