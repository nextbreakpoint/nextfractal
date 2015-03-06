package com.nextbreakpoint.nextfractal.core.renderer;

public interface RendererGraphicsContext {
	public void setStroke(RendererColor c);

	public void setFill(RendererColor c);
	
	public void setFont(RendererFont font);
	
	public void rect(int x, int y, int width, int height);
	
	public void stroke();
	
	public void fill();

	public void clip();

	public void beginPath();

	public void closePath();

	public void moveTo(int x, int y);
	
	public void lineTo(int x, int y);
	
	public void strokeRect(int x, int y, int width, int height);
	
	public void fillRect(int x, int y, int width, int height);
	
	public void strokeText(String text, int x, int y);

	public void fillText(String text, int x, int y);

	public void drawImage(RendererImage image, int x, int y);

	public void drawImage(RendererImage image, int x, int y, int w, int h);

	public void clearRect(int x, int y, int width, int height);

	public void setAffine(RendererAffine t);

	public void save();

	public void restore();

	public void setClip(int x, int y, int width, int height);

	public void setAlpha(double alpha);
}
