package com.nextbreakpoint.nextfractal.service;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.ExportJob;
import com.nextbreakpoint.nextfractal.RenderService;

public class ComposedRenderService implements RenderService {
	private final List<RenderService> services = new ArrayList<RenderService>();
	
	/**
	 * @param services
	 */
	public ComposedRenderService(List<RenderService> services) {
		this.services.addAll(services);
	}
	
	@Override
	public void dispatch(ExportJob job) {
		synchronized (services) {
			RenderService foundService = null; 
			for (RenderService service : services) {
				service.dispatch(job);
				if (job.isDispatched()) {
					foundService = service;
					break;
				}
			}
			if (foundService != null) {
				services.remove(foundService);
				services.add(foundService);
			}
		}
	}
}
