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

import java.net.URL;
import java.util.LinkedList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public final class SimpleStreamedSound extends AbstractStreamedSound {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private AudioPlayer player;
	private final String name;
	private final URL url;
	private int frame;
	private final LinkedList<byte[]> queue;
	private SourceDataLine line;

	public SimpleStreamedSound(final String name, final URL url) {
		queue = new LinkedList<byte[]>();
		this.name = name;
		this.url = url;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new SimpleStreamedSound(name + "_copy", url);
	}

	protected Layer getLayer() {
		return layer;
	}

	protected Sequence getSequence() {
		return sequence;
	}

	protected Controller getController() {
		return controller;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Movie getParent() {
		return parent;
	}

	@Override
	void build(final Controller controller, final Movie parent, final Layer layer, final Sequence sequence) {
		this.controller = controller;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
		// frames = sequence.getFrames();
		final int rate = parent.getFrameRate();
		try {
			final Mixer mixer = AudioSystem.getMixer(null);
			final AudioFileFormat file = AudioSystem.getAudioFileFormat(url);
			final AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			final AudioFormat format = stream.getFormat();
			int size = (int) Math.rint((format.getFrameRate() * format.getFrameSize()) / rate);
			size = (size + format.getFrameSize()) - (size % format.getFrameSize());
			// size = size - size % format.getFrameSize();
			size = Math.min(size, file.getByteLength());
			byte[] buffer;
			while (stream.read(buffer = new byte[size], 0, size) > -1) {
				queue.addLast(buffer);
			}
			line = (SourceDataLine) mixer.getLine(new DataLine.Info(SourceDataLine.class, format));
			line.open();
		}
		catch (final Exception e) {
			e.printStackTrace();
			line = null;
		}
	}

	@Override
	void init() {
		if (line != null) {
			line.start();
			player = new AudioPlayer(getName() + " [player]", line);
		}
		frame = 0;
	}

	@Override
	void kill() {
		if (line != null) {
			line.close();
		}
		if (player != null) {
			player.kill();
		}
	}

	@Override
	void reset() {
		if (line != null) {
			line.flush();
		}
		frame = 0;
	}

	@Override
	void playFrame(final int frame) {
		if (player != null) {
			this.frame = frame;
			if ((frame >= 0) && (frame < queue.size())) {
				player.play(queue.get(frame));
				// System.out.println("play " + frame);
			}
		}
	}

	@Override
	void playNextFrame() {
		if (player != null) {
			frame = frame + 1;
			if ((frame >= 0) && (frame < queue.size())) {
				player.play(queue.get(frame));
				// System.out.println("play " + this.frame);
			}
		}
	}

	@Override
	void playPrevFrame() {
		if (player != null) {
			frame = frame - 1;
			if ((frame >= 0) && (frame < queue.size())) {
				player.play(queue.get(frame));
				// System.out.println("play " + this.frame);
			}
		}
	}
}
