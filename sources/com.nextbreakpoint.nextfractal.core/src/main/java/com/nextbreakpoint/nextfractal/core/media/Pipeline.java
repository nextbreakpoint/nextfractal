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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

public final class Pipeline {
	private static final Stroke DEFAULT_STROKE = new BasicStroke(1f);
	// private static final boolean debug = false;
	private Graphics2D graphics;
	private Effect effect;
	private final MovieContext context;
	private final LinkedList<EngineEvent> enqueuedEvents;
	private final LinkedList<EngineEvent> dispatchedEvents;

	public Pipeline(final MovieContext context) {
		this.context = context;
		enqueuedEvents = new LinkedList<EngineEvent>();
		dispatchedEvents = new LinkedList<EngineEvent>();
		try {
			AudioSystem.getMixer(null).open();
			if (context.debug()) {
				context.println("mixer opened");
			}
		}
		catch (final LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void kill() {
		AudioSystem.getMixer(null).close();
		if (context.debug()) {
			context.println("mixer closed");
		}
	}

	public void enqueueEvent(final EngineEvent event) {
		synchronized (enqueuedEvents) {
			enqueuedEvents.addLast(event);
		}
	}

	private void processEvents() {
		synchronized (enqueuedEvents) {
			dispatchedEvents.addAll(enqueuedEvents);
			enqueuedEvents.clear();
		}
	}

	public void render(final Graphics2D graphics, final int w, final int h, final Movie movie) {
		this.graphics = graphics;
		graphics.setPaint(context.getColor());
		graphics.fillRect(0, 0, w, h);
		effect = null;
		processEvents();
		renderMovie(movie);
	}

	private void renderMovie(final Movie movie) {
		// if (context.debug() && debug)
		// {
		// if (movie.getParent() != null)
		// {
		// System.out.println(movie.getParent().toString() + "->" + movie.toString());
		// }
		// else
		// {
		// System.out.println("root->" + movie.toString());
		// }
		// }
		try {
			final Timeline timeline = movie.getTimeline();
			final AffineTransform old_transform = graphics.getTransform();
			final AffineTransform transform = movie.getTransform();
			Layer layer;
			Sequence sequence;
			Effect old_effect;
			AbstractObject object;
			Shape old_clip;
			Shape clip;
			if (transform != null) {
				graphics.transform(transform);
				graphics.translate(-movie.getCenter().getX(), -movie.getCenter().getY());
			}
			for (int i = timeline.getLayers() - 1; i >= 0; i--) {
				layer = timeline.getLayer(i);
				old_clip = graphics.getClip();
				clip = layer.getClip();
				if (clip != null) {
					graphics.clip(clip);
				}
				if (layer.getSequences() > 0) {
					sequence = layer.getSequence();
					if (sequence != null) {
						old_effect = effect;
						if (sequence instanceof GraphicsSequence) {
							if (effect != null) {
								effect = effect.add(((GraphicsSequence) sequence).getEffect());
							}
							else {
								effect = ((GraphicsSequence) sequence).getEffect();
							}
						}
						// if (context.debug() && debug && (effect != null))
						// {
						// System.out.println(effect.toString());
						// }
						object = sequence.getObject();
						if (object instanceof AbstractGraphics) {
							final AffineTransform old_transform2 = graphics.getTransform();
							final AffineTransform transform2 = ((AbstractGraphics) object).getTransform();
							if (transform2 != null) {
								graphics.translate(-((AbstractGraphics) object).getCenter().getX(), -((AbstractGraphics) object).getCenter().getY());
								graphics.transform(transform2);
							}
							final AffineTransform inverse = graphics.getTransform().createInverse();
							while (dispatchedEvents.size() > 0) {
								final EngineEvent event = dispatchedEvents.removeFirst();
								((AbstractGraphics) object).dispatchEvent(inverse, event);
							}
							graphics.setTransform(old_transform2);
						}
						dispatchedEvents.clear();
						if (object instanceof AbstractShape) {
							renderShape((AbstractShape) object);
						}
						else if (object instanceof AbstractRenderedImage) {
							renderRenderedImage((AbstractRenderedImage) object);
						}
						else if (object instanceof AbstractImage) {
							renderImage((AbstractImage) object);
						}
						else if (object instanceof Movie) {
							renderMovie((Movie) object);
						}
						else if (object instanceof AbstractButton) {
							renderButton((AbstractButton) object);
						}
						effect = old_effect;
					}
				}
				graphics.setClip(old_clip);
			}
			graphics.setTransform(old_transform);
		}
		catch (NoninvertibleTransformException e) {
		}
	}

	private void renderShape(final AbstractShape shape) {
		// if (context.debug() && debug)
		// {
		// if (shape.getParent() != null)
		// {
		// System.out.println(shape.getParent().toString() + "->" + shape.toString());
		// }
		// else
		// {
		// System.out.println("null->" + shape.toString());
		// }
		// }
		final Shape path = shape.getShape();
		if (path != null) {
			if (effect != null) {
				shape.applyEffect(effect);
			}
			final AffineTransform old_transform = graphics.getTransform();
			final Stroke old_stroke = graphics.getStroke();
			final Paint old_paint = graphics.getPaint();
			final Color old_color = graphics.getColor();
			final AffineTransform transform = shape.getTransform();
			final Stroke stroke = shape.getStroke();
			final Paint paint1 = shape.getPaint1();
			final Paint paint2 = shape.getPaint2();
			if (transform != null) {
				graphics.translate(-shape.getCenter().getX(), -shape.getCenter().getY());
				graphics.transform(transform);
			}
			if (stroke != null) {
				graphics.setStroke(stroke);
			}
			else {
				graphics.setStroke(DEFAULT_STROKE);
			}
			if (paint2 != null) {
				graphics.setPaint(paint2);
				graphics.fill(path);
			}
			if (paint1 != null) {
				graphics.setPaint(paint1);
				graphics.draw(path);
			}
			graphics.setTransform(old_transform);
			graphics.setStroke(old_stroke);
			graphics.setPaint(old_paint);
			graphics.setPaint(old_color);
		}
	}

	private void renderRenderedImage(final AbstractRenderedImage image) {
		// if (context.debug() && debug)
		// {
		// if (image.getParent() != null)
		// {
		// System.out.println(image.getParent().toString() + "->" + image.toString());
		// }
		// else
		// {
		// System.out.println("null->" + image.toString());
		// }
		// }
		image.render();
		if (effect != null) {
			image.applyEffect(effect);
		}
		image.update();
		final AffineTransform old_transform = graphics.getTransform();
		final AffineTransform transform = image.getTransform();
		if (transform != null) {
			graphics.translate(-image.getCenter().getX(), -image.getCenter().getY());
			graphics.transform(transform);
		}
		image.drawImage(graphics);
		graphics.setTransform(old_transform);
	}

	private void renderImage(final AbstractImage image) {
		// if (context.debug() && debug)
		// {
		// if (image.getParent() != null)
		// {
		// System.out.println(image.getParent().toString() + "->" + image.toString());
		// }
		// else
		// {
		// System.out.println("null->" + image.toString());
		// }
		// }
		if (effect != null) {
			image.applyEffect(effect);
		}
		final AffineTransform old_transform = graphics.getTransform();
		final AffineTransform transform = image.getTransform();
		if (transform != null) {
			graphics.translate(-image.getCenter().getX(), -image.getCenter().getY());
			graphics.transform(transform);
		}
		image.drawImage(graphics);
		graphics.setTransform(old_transform);
	}

	private void renderButton(final AbstractButton button) {
		// if (context.debug() && debug)
		// {
		// if (button.getParent() != null)
		// {
		// System.out.println(button.getParent().toString() + "->" + button.toString());
		// }
		// else
		// {
		// System.out.println("null->" + button.toString());
		// }
		// }
		final AbstractShape shape = button.getButtonShape();
		if (shape != null) {
			renderShape(shape);
		}
	}
}
