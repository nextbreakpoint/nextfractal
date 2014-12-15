package com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.editor;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class MandelbrotEditorPane extends BorderPane {
	public MandelbrotEditorPane() {
		TextArea textarea = new TextArea();
		setCenter(textarea);
		textarea.setText(getSource());
	}

	protected String getSource() {
		String source = ""
				+ "fractal [z,x,n] {\n"
				+ "\torbit [-1 - 1i,+1 + 1i] {\n"
				+ "\t\ttrap trap1 [0] {\n"
				+ "\t\t\tMOVETO(1);\n"
				+ "\t\t\tLINETO(2);\n"
				+ "\t\t\tLINETO(2 + 2i);\n"
				+ "\t\t\tLINETO(1 + 2i);\n"
				+ "\t\t\tLINETO(1);\n"
				+ "\t\t}\n"
				+ "\t\tloop [0, 2] (|z| > 4 & trap1[z]) {\n"
				+ "\t\t\ty = 0;\n"
				+ "\t\t\tt = 3;\n"
				+ "\t\t\tx = t + 4 + 1i;\n"
				+ "\t\t\tk = t + 4;\n"
				+ "\t\t\tz = x * (y + 5i);\n"
				+ "\t\t\tt = |z|;\n"
				+ "\t\t}\n"
				+ "\t}\n\tcolor [#FF000000] {\n"
				+ "\t\tpalette palette1 [200] {\n"
				+ "\t\t\t[0, #000000] > [100, #FFFFFF];\n"
				+ "\t\t\t[101, #FFFFFF] > [200, #FF0000];\n"
				+ "\t\t}\n"
				+ "\t\trule (real(n) = 0) [0.5] {\n"
				+ "\t\t\t|x|,5,5,5\n"
				+ "\t\t}\n"
				+ "\t\trule (real(n) > 0) [0.5] {\n"
				+ "\t\t\tpalette1[real(n)]\n"
				+ "\t\t}\n"
				+ "\t}\n"
				+ "}\n";
		return source;
	}
}
