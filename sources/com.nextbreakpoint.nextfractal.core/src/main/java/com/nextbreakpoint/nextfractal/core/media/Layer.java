/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import java.awt.Shape;
import java.util.ListIterator;

public final class Layer {
	private final SequenceList sequences = new SequenceList();
	private Sequence[] sequence_table;
	private int[] sequence_frame;
	private Sequence sequence;
	private boolean dirty = false;
	private int frames = 0;
	private int frame = 0;
	private Shape clip;

	public Layer() {
	}

	public Layer(final SequenceList list) {
		sequences.addAll(list);
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			frames += (li.next()).getFrames();
		}
	}

	public Layer(final SequenceList list, final Shape clip) {
		this(list);
		this.clip = clip;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Layer((SequenceList) sequences.clone(), clip);
	}

	@Override
	public String toString() {
		return "layer " + hashCode();
	}

	public void removeSequence(final Sequence sequence) {
		sequences.remove(sequence);
		frames -= sequence.getFrames();
		dirty = false;
	}

	public void addSequenceFirst(final Sequence sequence) {
		sequences.addFirst(sequence);
		frames += sequence.getFrames();
		dirty = false;
	}

	public void addSequenceLast(final Sequence sequence) {
		sequences.addLast(sequence);
		frames += sequence.getFrames();
		dirty = false;
	}

	public void removeSequenceFirst() {
		final Sequence sequence = sequences.removeFirst();
		frames -= sequence.getFrames();
		dirty = false;
	}

	public void removeSequenceLast() {
		final Sequence sequence = sequences.removeLast();
		frames -= sequence.getFrames();
		dirty = false;
	}

	public void removeSequences() {
		sequences.clear();
		frames = 0;
		dirty = false;
	}

	public void setSequence(final int index, Sequence sequence) {
		frames += sequence.getFrames();
		sequence = sequences.set(index, sequence);
		frames -= sequence.getFrames();
		dirty = false;
	}

	public Sequence getSequence(final int index) {
		return sequences.get(index);
	}

	public int getSequences() {
		return sequences.size();
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

	public Shape getClip() {
		return clip;
	}

	public void setClip(final Shape clip) {
		this.clip = clip;
	}

	boolean isChanged() {
		return dirty;
	}

	void buildSequenceTable(final int frames) {
		int frame = 0;
		sequence_frame = new int[frames];
		sequence_table = new Sequence[frames];
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			final Sequence q = li.next();
			for (int i = 0; i < q.getFrames(); i++) {
				sequence_table[frame + i] = q;
				sequence_frame[frame + i] = i;
			}
			frame += q.getFrames();
		}
		for (final int i = frame; frame < frames; frame++) {
			sequence_table[i] = null;
			sequence_frame[i] = 0;
		}
		dirty = true;
	}

	void setFrame(final int frame) {
		if (!dirty) {
			buildSequenceTable(frames);
		}
		this.frame = frame;
		if ((this.frame >= 0) && (this.frame < frames)) {
			sequence = sequence_table[this.frame];
			if (sequence != null) {
				sequence.setFrame(sequence_frame[this.frame]);
			}
		}
		else {
			sequence = null;
		}
	}

	void nextFrame() {
		if (!dirty) {
			buildSequenceTable(frames);
		}
		frame = frame + 1;
		if ((frame >= 0) && (frame < frames)) {
			sequence = sequence_table[frame];
			if (sequence != null) {
				sequence.nextFrame();
			}
		}
		else {
			sequence = null;
		}
	}

	void prevFrame() {
		if (!dirty) {
			buildSequenceTable(frames);
		}
		frame = frame - 1;
		if ((frame >= 0) && (frame < frames)) {
			sequence = sequence_table[frame];
			if (sequence != null) {
				sequence.prevFrame();
			}
		}
		else {
			sequence = null;
		}
	}

	Sequence getSequence() {
		return sequence;
	}

	void build(final Controller engine, final Movie movie) {
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			(li.next()).build(engine, movie, this);
		}
	}

	void init() {
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			(li.next()).init();
		}
	}

	void kill() {
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			(li.next()).kill();
		}
	}

	void reset() {
		final ListIterator<Sequence> li = sequences.listIterator();
		while (li.hasNext()) {
			(li.next()).reset();
		}
	}
}
