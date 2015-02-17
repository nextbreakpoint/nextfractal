package com.nextbreakpoint.nextfractal;

public class ExportJob {
	private ExportProfile profile;
	private ExportResult result;
	private boolean terminated;

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
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
}
