package com.nextbreakpoint.nextfractal.export;

import java.util.concurrent.Future;

public interface ExportRenderer {
	public Future<ExportJob> dispatch(ExportJob job);
}
