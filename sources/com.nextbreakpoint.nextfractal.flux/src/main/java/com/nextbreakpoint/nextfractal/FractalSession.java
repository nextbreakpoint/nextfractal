package com.nextbreakpoint.nextfractal;

import java.io.File;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public interface FractalSession {
	/**
	 * @param listener
	 */
	public void addSessionListener(FractalSessionListener listener);

	/**
	 * @param listener
	 */
	public void removeSessionListener(FractalSessionListener listener);

	/**
	 * @param packageName
	 */
	public void setPackageName(String packageName);

	/**
	 * @return
	 */
	public String getPackageName();

	/**
	 * @param className
	 */
	public void setClassName(String className);

	/**
	 * @return
	 */
	public String getClassName();

	/**
	 * 
	 */
	public void terminate();

	/**
	 * @return
	 */
	public File getOutDir();

	/**
	 * @param outDir
	 */
	public void setOutDir(File outDir);

	/**
	 * @param file
	 * @param data
	 * @param size
	 * @return
	 * @throws SessionException 
	 */
	public ExportSession createExportSession(File file, Object data, RendererSize size) throws SessionException;
}
