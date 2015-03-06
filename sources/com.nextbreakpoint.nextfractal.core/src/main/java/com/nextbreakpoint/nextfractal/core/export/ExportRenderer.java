package com.nextbreakpoint.nextfractal.core.export;

import java.util.concurrent.Future;

public interface ExportRenderer {
	public Future<ExportJob> dispatch(ExportJob job);
}
