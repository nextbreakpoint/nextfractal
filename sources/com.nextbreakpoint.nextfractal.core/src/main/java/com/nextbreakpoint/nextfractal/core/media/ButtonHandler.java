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

public abstract class ButtonHandler extends Handler {
	private Controller engine;
	private Movie parent;
	private Layer layer;
	private Sequence sequence;

	final void build(final Controller engine, final Movie parent, final Layer layer, final Sequence sequence) {
		this.engine = engine;
		this.parent = parent;
		this.layer = layer;
		this.sequence = sequence;
	}

	protected void init() {
	}

	protected void kill() {
	}

	protected void reset() {
	}

	protected Layer getLayer() {
		return layer;
	}

	protected Sequence getSequence() {
		return sequence;
	}

	public final Movie getParent() {
		return parent;
	}

	public final Controller getController() {
		return engine;
	}

	final void process(final EngineButtonEvent e) {
		switch (e.event) {
			case EngineButtonEvent.RELEASED: {
				released(e);
				break;
			}
			case EngineButtonEvent.PRESSED: {
				pressed(e);
				break;
			}
			case EngineButtonEvent.ENTERED: {
				entered(e);
				break;
			}
			case EngineButtonEvent.EXITED: {
				exited(e);
				break;
			}
			default:
				break;
		}
	}

	protected abstract void released(EngineButtonEvent e);

	protected abstract void pressed(EngineButtonEvent e);

	protected abstract void entered(EngineButtonEvent e);

	protected abstract void exited(EngineButtonEvent e);

	@Override
	public abstract Object clone() throws CloneNotSupportedException;
}
