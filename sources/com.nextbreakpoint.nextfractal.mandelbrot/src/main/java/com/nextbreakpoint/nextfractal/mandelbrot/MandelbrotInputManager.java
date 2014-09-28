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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotInputManager {
	protected static final boolean debug = false;
	private ZoomData oldZoomData = new ZoomData();
	private ZoomData newZoomData = new ZoomData();
	private boolean interrupted = false;
	private boolean enabled = false;
	// private boolean dirty = false;
	private final MandelbrotZoomHandler zoomHandler;
	private final MandelbrotInputHandler inputHandler;

	/**
	 * 
	 */
	public MandelbrotInputManager(final MandelbrotZoomHandler zoomHandler) {
		inputHandler = new MandelbrotInputHandlerImpl();
		this.zoomHandler = zoomHandler;
	}

	/**
	 * @return the handler
	 */
	public MandelbrotInputHandler getInputHandler() {
		return inputHandler;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		oldZoomData = null;
		newZoomData = null;
		super.finalize();
	}

	/**
	 * @param value
	 */
	public void setInputHandlerEnabled(final boolean value) {
		enabled = value;
	}

	/**
	 * 
	 */
	public void startInputHandler() {
		interrupted = false;
		// dirty = true;
	}

	/**
	 * 
	 */
	public void stopInputHandler() {
		interrupted = true;
	}

	/**
	 * 
	 */
	public void runInputHandler() {
		if (interrupted) {
			newZoomData.zoomDirection = 0;
			newZoomData.shiftDirection = 0;
			newZoomData.rotationDirection = 0;
			newZoomData.autoZoomEnabled = false;
			newZoomData.autoShiftEnabled = false;
			newZoomData.autoRotationEnabled = false;
			newZoomData.zoomEnabled = false;
			newZoomData.shiftEnabled = false;
			newZoomData.rotationEnabled = false;
			newZoomData.lastMousePosX = 0;
			newZoomData.lastMousePosY = 0;
			newZoomData.startMousePosX = 0;
			newZoomData.startMousePosY = 0;
			// dirty = false;
		}
		else {
			newZoomData.zoomDirection = oldZoomData.zoomDirection;
			newZoomData.shiftDirection = oldZoomData.shiftDirection;
			newZoomData.rotationDirection = oldZoomData.rotationDirection;
			newZoomData.autoZoomEnabled = oldZoomData.autoZoomEnabled;
			newZoomData.autoShiftEnabled = oldZoomData.autoShiftEnabled;
			newZoomData.autoRotationEnabled = oldZoomData.autoRotationEnabled;
			newZoomData.zoomEnabled = oldZoomData.zoomEnabled;
			newZoomData.shiftEnabled = oldZoomData.shiftEnabled;
			newZoomData.rotationEnabled = oldZoomData.rotationEnabled;
			newZoomData.lastMousePosX = oldZoomData.lastMousePosX;
			newZoomData.lastMousePosY = oldZoomData.lastMousePosY;
			newZoomData.startMousePosX = oldZoomData.startMousePosX;
			newZoomData.startMousePosY = oldZoomData.startMousePosY;
		}
		if (enabled && (/* dirty || */newZoomData.zoomEnabled || newZoomData.rotationEnabled || newZoomData.shiftEnabled)) {
			final boolean zoom = newZoomData.autoZoomEnabled & newZoomData.zoomEnabled;
			final boolean shift = newZoomData.autoShiftEnabled & newZoomData.shiftEnabled;
			final boolean rotation = newZoomData.autoRotationEnabled & newZoomData.rotationEnabled;
			zoomHandler.doZoom(new MandelbrotZoom(zoom || shift || rotation, newZoomData.zoomEnabled, newZoomData.shiftEnabled, newZoomData.rotationEnabled, newZoomData.zoomDirection, newZoomData.shiftDirection, newZoomData.rotationDirection, newZoomData.lastMousePosX, newZoomData.lastMousePosY, newZoomData.startMousePosX, newZoomData.startMousePosY));
			// dirty = false;
		}
		oldZoomData.startMousePosX = oldZoomData.lastMousePosX;
		oldZoomData.startMousePosY = oldZoomData.lastMousePosY;
	}

	private class MandelbrotInputHandlerImpl implements MandelbrotInputHandler {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setLastMousePosition(double, double)
		 */
		public void setLastMousePosition(final int x, final int y) {
			oldZoomData.lastMousePosX = (x - zoomHandler.getWidth() / 2d) / zoomHandler.getWidth();
			oldZoomData.lastMousePosY = (y - zoomHandler.getHeight() / 2d) / zoomHandler.getWidth();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setStartMousePosition(double, double)
		 */
		public void setStartMousePosition(final int x, final int y) {
			oldZoomData.startMousePosX = (x - zoomHandler.getWidth() / 2d) / zoomHandler.getWidth();
			oldZoomData.startMousePosY = (y - zoomHandler.getHeight() / 2d) / zoomHandler.getWidth();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setZoomMode(int)
		 */
		public void setZoomMode(final int mode) {
			switch (mode) {
				case MODE_MANUAL: {
					break;
				}
				case MODE_AUTOMATIC: {
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setShiftMode(int)
		 */
		public void setShiftMode(final int mode) {
			switch (mode) {
				case MODE_MANUAL: {
					break;
				}
				case MODE_AUTOMATIC: {
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setRotationMode(int)
		 */
		public void setRotationMode(final int mode) {
			switch (mode) {
				case MODE_MANUAL: {
					break;
				}
				case MODE_AUTOMATIC: {
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setZoomDirection(int)
		 */
		public void setZoomDirection(final int direction) {
			oldZoomData.autoZoomEnabled = true;
			switch (direction) {
				case DIRECTION_UNDEFINED: {
					oldZoomData.autoZoomEnabled = false;
					oldZoomData.zoomDirection = 0;
					break;
				}
				case DIRECTION_FORWARD: {
					oldZoomData.zoomDirection = -1;
					break;
				}
				case DIRECTION_BACKWARD: {
					oldZoomData.zoomDirection = +1;
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setShiftDirection(int)
		 */
		public void setShiftDirection(final int direction) {
			oldZoomData.autoShiftEnabled = true;
			switch (direction) {
				case DIRECTION_UNDEFINED: {
					oldZoomData.autoShiftEnabled = false;
					oldZoomData.shiftDirection = 0;
					break;
				}
				case DIRECTION_FORWARD: {
					oldZoomData.shiftDirection = -1;
					break;
				}
				case DIRECTION_BACKWARD: {
					oldZoomData.shiftDirection = +1;
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setRotationDirection(int)
		 */
		public void setRotationDirection(final int direction) {
			oldZoomData.autoRotationEnabled = true;
			switch (direction) {
				case DIRECTION_UNDEFINED: {
					oldZoomData.autoRotationEnabled = false;
					oldZoomData.rotationDirection = 0;
					break;
				}
				case DIRECTION_FORWARD: {
					oldZoomData.rotationDirection = -1;
					break;
				}
				case DIRECTION_BACKWARD: {
					oldZoomData.rotationDirection = +1;
					break;
				}
			}
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setZoomEnabled(boolean)
		 */
		public void setZoomEnabled(final boolean value) {
			oldZoomData.zoomEnabled = value;
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setShiftEnabled(boolean)
		 */
		public void setShiftEnabled(final boolean value) {
			oldZoomData.shiftEnabled = value;
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#setRotationEnabled(boolean)
		 */
		public void setRotationEnabled(final boolean value) {
			oldZoomData.rotationEnabled = value;
			// dirty = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#getNormalizedLastMousePositionX()
		 */
		public double getNormalizedLastMousePositionX() {
			return oldZoomData.lastMousePosX;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#getNormalizedLastMousePositionY()
		 */
		public double getNormalizedLastMousePositionY() {
			return oldZoomData.lastMousePosY;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#getNormalizedStartMousePositionX()
		 */
		public double getNormalizedStartMousePositionX() {
			return oldZoomData.startMousePosX;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotInputHandler#getNormalizedStartMousePositionY()
		 */
		public double getNormalizedStartMousePositionY() {
			return oldZoomData.startMousePosY;
		}
	}

	private class ZoomData implements Serializable {
		private static final long serialVersionUID = 1L;
		boolean autoZoomEnabled = false;
		boolean autoShiftEnabled = false;
		boolean autoRotationEnabled = false;
		boolean zoomEnabled = false;
		boolean shiftEnabled = false;
		boolean rotationEnabled = false;
		int zoomDirection = 0;
		int shiftDirection = 0;
		int rotationDirection = 0;
		double lastMousePosX = 0;
		double lastMousePosY = 0;
		double startMousePosX = 0;
		double startMousePosY = 0;

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			final StringBuffer data = new StringBuffer();
			data.append("zoomEnabled=" + zoomEnabled + ",");
			data.append("shiftEnabled=" + shiftEnabled + ",");
			data.append("rotationEnabled=" + rotationEnabled + ",");
			data.append("zoomDynamicEnabled=" + autoZoomEnabled + ",");
			data.append("shiftDynamicEnabled=" + autoShiftEnabled + ",");
			data.append("rotationDynamicEnabled=" + autoRotationEnabled + ",");
			data.append("zoomDirection=" + zoomDirection + ",");
			data.append("shiftDirection=" + shiftDirection + ",");
			data.append("rotationDirection=" + rotationDirection + ",");
			data.append("lastMousePosX=" + lastMousePosX + ",");
			data.append("lastMousePosY=" + lastMousePosY + ",");
			data.append("startMousePosX=" + startMousePosX + ",");
			data.append("startMousePosY=" + startMousePosY);
			return data.toString();
		}
	}
}
