package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class ExportSession {
	private final List<ExportSessionListener> listeners = new ArrayList<>();
	private final String sessioinId;
	private final DataEncoder encoder;
	private final RendererSize size;
	private final Object data;
	private final File file;
	private float progress;

	public ExportSession(File file, Object data, RendererSize size, DataEncoder encoder) {
		this.file = file;
		this.data = data;
		this.size = size;
		this.encoder = encoder;
		sessioinId = UUID.randomUUID().toString();
	}
	
	public String getSessionId() {
		return sessioinId;
	}

	public Object getData() {
		return data;
	}

	public RendererSize getSize() {
		return size;
	}

	public DataEncoder getEncoder() {
		return encoder;
	}

	public File getFile() {
		return file;
	}

	public void addSessionListener(ExportSessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(ExportSessionListener listener) {
		listeners.remove(listener);
	}
	
	protected void setProgress(float progress) {
		this.progress = progress;
		fireStateChanged();
	}

	protected void fireStateChanged() {
		for (ExportSessionListener listener : listeners) {
			listener.stateChanged(this, progress);
		}
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
