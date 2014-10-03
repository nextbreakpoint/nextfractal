/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.core.media;

import java.util.ListIterator;

public final class Timeline {
	private final LayerList layers = new LayerList();
	private boolean dirty = false;
	private int frames = 0;
	private int frame = 0;

	public Timeline() {
	}

	public Timeline(final LayerList list) {
		layers.addAll(list);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Timeline((LayerList) layers.clone());
	}

	@Override
	public String toString() {
		return "timeline " + hashCode();
	}

	public void removeLayer(final Layer layer) {
		layers.remove(layer);
		dirty = false;
	}

	public void addLayerFirst(final Layer layer) {
		layers.addFirst(layer);
		dirty = false;
	}

	public void addLayerLast(final Layer layer) {
		layers.addLast(layer);
		dirty = false;
	}

	public void removeLayerFirst() {
		layers.removeFirst();
		dirty = false;
	}

	public void removeLayerLast() {
		layers.removeLast();
		dirty = false;
	}

	public void removeLayers() {
		layers.clear();
		dirty = false;
	}

	public void setLayer(final int index, final Layer layer) {
		layers.set(index, layer);
		dirty = false;
	}

	public Layer getLayer(final int index) {
		return layers.get(index);
	}

	public int getLayers() {
		return layers.size();
	}

	public int getFrames() {
		return frames;
	}

	public int getFrame() {
		return frame;
	}

	public boolean isFirstFrame() {
		return (frame == 0);
	}

	public boolean isLastFrame() {
		return (frame == (frames - 1));
	}

	boolean isChanged() {
		boolean changed = false;
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			if ((li.next()).isChanged()) {
				changed = true;
				break;
			}
		}
		return changed;
	}

	void buildFrames() {
		frames = 0;
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			frames = Math.max(frames, (li.next()).getFrames());
		}
		dirty = true;
	}

	void setFrame(final int frame) {
		if ((!dirty) || (isChanged())) {
			buildFrames();
		}
		this.frame = frame;
		if ((this.frame >= 0) && (this.frame < frames)) {
			final ListIterator<Layer> li = layers.listIterator();
			while (li.hasNext()) {
				(li.next()).setFrame(this.frame);
			}
		}
	}

	void nextFrame() {
		if ((!dirty) || (isChanged())) {
			buildFrames();
		}
		frame = frame + 1;
		if ((frame >= 0) && (frame < frames)) {
			final ListIterator<Layer> li = layers.listIterator();
			while (li.hasNext()) {
				(li.next()).nextFrame();
			}
		}
		else {
			setFrame(0);
		}
	}

	void prevFrame() {
		if ((!dirty) || (isChanged())) {
			buildFrames();
		}
		frame = frame - 1;
		if ((frame >= 0) && (frame < frames)) {
			final ListIterator<Layer> li = layers.listIterator();
			while (li.hasNext()) {
				(li.next()).prevFrame();
			}
		}
		else {
			setFrame(frames - 1);
		}
	}

	void build(final Controller engine, final Movie movie) {
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			(li.next()).build(engine, movie);
		}
	}

	void init() {
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			(li.next()).init();
		}
	}

	void kill() {
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			(li.next()).kill();
		}
	}

	void reset() {
		final ListIterator<Layer> li = layers.listIterator();
		while (li.hasNext()) {
			(li.next()).reset();
		}
	}
}
