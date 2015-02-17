package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

public class ExportJob {
	private ExportSession session;
	private ExportProfile profile;
	private IntBuffer pixels;
	private boolean terminated;
	private String errorMessage;

	public ExportJob(ExportSession session) {
		this.session = session;
	}

	public ExportSession getSession() {
		return session;
	}

	public IntBuffer getPixels() {
		return pixels;
	}

	public void setPixels(IntBuffer pixels) {
		this.pixels = pixels;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setSession(ExportSession exportSession) {
		this.session = exportSession;
	}

	public ExportProfile getProfile() {
		return profile;
	}

	public void setProfile(ExportProfile profile) {
		this.profile = profile;
	}
}
