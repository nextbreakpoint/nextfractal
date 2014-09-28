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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;

public final class SimpleClipSound extends AbstractClipSound {
	private Controller controller;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;
	private final String name;
	private final URL url;
	private Clip line;
	private int loop;

	public SimpleClipSound(final String name, final URL url, final int loop) {
		if (loop == 0) {
			this.loop = Clip.LOOP_CONTINUOUSLY;
		}
		else {
			this.loop = loop;
		}
		this.name = name;
		this.url = url;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new SimpleClipSound(name + "_copy", url, loop);
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
	}

	@Override
	void init() {
		try {
			final Mixer mixer = AudioSystem.getMixer(null);
			final AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			line = (Clip) mixer.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
			line.open(stream);
			line.start();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	void kill() {
		stop();
	}

	@Override
	void reset() {
	}

	@Override
	void play() {
		if (line != null) {
			line.loop(loop);
		}
	}

	@Override
	void stop() {
		if (line != null) {
			line.close();
			line = null;
		}
	}
}
