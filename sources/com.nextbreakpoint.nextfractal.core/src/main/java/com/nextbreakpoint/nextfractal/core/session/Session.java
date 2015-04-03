package com.nextbreakpoint.nextfractal.core.session;

import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;

public interface Session {
	public ExportService getExportService();

	public void setExportService(ExportService exportService);

	public void terminate();

	public void addExportSession(ExportSession exportSession);

	public void removeExportSession(ExportSession exportSession);

	public void addSessionListener(SessionListener sessionListener);

	public void removeSessionListener(SessionListener sessionListener);
}
