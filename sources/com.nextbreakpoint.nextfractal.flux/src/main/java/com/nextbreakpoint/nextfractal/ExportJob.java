package com.nextbreakpoint.nextfractal;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportJob {
	private final ExportSession session;
	private final ExportProfile profile;
	private volatile ExportResult result;
	private volatile boolean dispatched;
	private volatile boolean terminated;
	private volatile boolean suspended;

	public ExportJob(ExportSession session, ExportProfile profile) {
		this.session = session;
		this.profile = profile;
	}

	public boolean isDispatched() {
		return dispatched;
	}

	public void setDispatched(boolean dispatched) {
		this.dispatched = dispatched;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
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

	public boolean isRequestTerminate() {
		return session.isRequestTerminate();
	}

	public boolean isRequestSuspend() {
		return session.isRequestSuspend();
	}

	public String getPluginId() {
		return profile.getPluginId();
	}

	@Override
	public String toString() {
		return "ExportJob [profile=" + profile + "]";
	}
}
