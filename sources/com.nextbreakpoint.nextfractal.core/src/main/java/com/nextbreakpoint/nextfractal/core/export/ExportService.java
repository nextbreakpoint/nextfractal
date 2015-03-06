package com.nextbreakpoint.nextfractal.core.export;

public interface ExportService {
	public void shutdown();

	public int getTileSize();

	public void startSession(ExportSession session);

	public void stopSession(ExportSession session);

	public void suspendSession(ExportSession session);

	public void resumeSession(ExportSession session);
}
