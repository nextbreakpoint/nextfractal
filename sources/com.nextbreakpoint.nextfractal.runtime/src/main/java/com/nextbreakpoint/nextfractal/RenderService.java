package com.nextbreakpoint.nextfractal;

import java.util.concurrent.Future;

public interface RenderService {
	public Future<ExportJob> dispatch(ExportJob job);
}
