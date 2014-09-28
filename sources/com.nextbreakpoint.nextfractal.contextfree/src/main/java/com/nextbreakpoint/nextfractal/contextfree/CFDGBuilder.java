package com.nextbreakpoint.nextfractal.contextfree;

import java.util.Stack;

public class CFDGBuilder {
	private StringBuilder builder = new StringBuilder();
	private Stack<String> tabs = new Stack<String>();
	
	public CFDGBuilder() {
		tabs.push("");
	}
	
	public void addTab() {
		tabs.push(tabs.peek() + "\t");
	}

	public void removeTab() {
		if (tabs.size() > 1) {
			tabs.pop(); 
		}
	}

	public void append(String m) {
		builder.append(m);
	}

	public void append(Integer value) {
		builder.append(value);
	}

	public void append(Float value) {
		builder.append(value);
	}

	public void append(Double value) {
		builder.append(value);
	}
	
	public void appendTabs() {
		builder.append(tabs.peek());
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
}
