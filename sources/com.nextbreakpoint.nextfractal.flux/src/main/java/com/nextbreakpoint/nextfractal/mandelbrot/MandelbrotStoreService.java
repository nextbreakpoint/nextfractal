package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.InputStream;
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;
import com.nextbreakpoint.nextfractal.spool.store.StoreData;
import com.nextbreakpoint.nextfractal.spool.store.StoreService;

public class MandelbrotStoreService extends StoreService<MandelbrotStoreData> {
	@Override
	public StoreData loadStoreData(InputStream is) {
		FileService service = new FileService();
		MandelbrotData data = service.loadFromStream(is);
		return new MandelbrotStoreData(data);
	}

	@Override
	public void saveStoreData(OutputStream os, StoreData spoolData) {
		FileService service = new FileService();
		MandelbrotData data = (MandelbrotData) spoolData.getData();
		service.saveToStream(os, data);
	}
}
