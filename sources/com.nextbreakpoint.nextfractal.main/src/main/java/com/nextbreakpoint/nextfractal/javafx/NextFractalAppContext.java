package com.nextbreakpoint.nextfractal.javafx;

import javafx.scene.layout.Pane;

public interface NextFractalAppContext {
	public void showConfigNode(Pane node); 
	public void discardConfigNode(); 
	public void showEditorNode(Pane node); 
	public void discardEditorNode(); 
}
