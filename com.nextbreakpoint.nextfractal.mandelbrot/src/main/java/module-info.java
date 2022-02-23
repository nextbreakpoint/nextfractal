/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2022 Andrea Medeghini
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
import com.nextbreakpoint.nextfractal.core.common.CoreFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotFactory;

module com.nextbreakpoint.nextfractal.mandelbrot {
    requires java.logging;
    requires java.desktop;
    requires java.compiler;
    requires commons.math3;
    requires org.antlr.antlr4.runtime;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    exports com.nextbreakpoint.nextfractal.mandelbrot.module;
    exports com.nextbreakpoint.nextfractal.mandelbrot.dsl;
    exports com.nextbreakpoint.nextfractal.mandelbrot.dsl.grammar;
    exports com.nextbreakpoint.nextfractal.mandelbrot.renderer;
    exports com.nextbreakpoint.nextfractal.mandelbrot.core;
    provides CoreFactory with MandelbrotFactory;
    opens com.nextbreakpoint.nextfractal.mandelbrot.module to com.fasterxml.jackson.databind;
}
