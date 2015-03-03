package com.nextbreakpoint.nextfractal.render;

public interface RenderGraphicsContext {
	public void setStroke(RenderColor c);

	public void setFill(RenderColor c);
	
	public void setFont(RenderFont font);
	
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

	public void drawImage(RenderImage image, int x, int y);

	public void drawImage(RenderImage image, int x, int y, int w, int h);

	public void clearRect(int x, int y, int width, int height);

	public void setAffine(RenderAffine t);

	public void save();

	public void restore();

	public void setClip(int x, int y, int width, int height);

	public void setAlpha(double alpha);
}
