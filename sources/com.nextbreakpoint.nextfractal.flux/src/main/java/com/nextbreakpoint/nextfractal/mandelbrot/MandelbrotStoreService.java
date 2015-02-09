package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.InputStream;
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;
import com.nextbreakpoint.nextfractal.spool.StoreData;
import com.nextbreakpoint.nextfractal.spool.StoreService;

public class MandelbrotStoreService extends StoreService<MandelbrotStoreData> {
	@Override
	public StoreData getSpoolData(InputStream is) {
		FileService service = new FileService();
		MandelbrotData data = service.loadFromStream(is);
		return new MandelbrotStoreData(data);
	}

	@Override
	public void saveToStream(OutputStream os, StoreData spoolData) {
		FileService service = new FileService();
		MandelbrotData data = (MandelbrotData) spoolData.getData();
		service.saveToStream(os, data);
	}
}
