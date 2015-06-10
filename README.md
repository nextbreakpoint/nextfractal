# NextFractal 1.1.0

Copyright 2015 Andrea Medeghini

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.


## DESCRIPTION

NextFractal is an application for creating fractals. Fractals are created processing a script written in M language which is a DSL designed to define Mandelbrot or Julia sets. NextFractal supports also continuous zooming of Mandelbrot and Julia sets and it supports exporting of images as PNG.

NextFractal is the continuation of my old software for generating fractals which I started in 2000. I aim to continue delivering free software which everybody can use to enjoy fractals.


## HOW TO INSTALL

Unzip the archive in your preferred location. NextFractal requires Java SDK 8 or later. You can download the latest SDK from http://www.oracle.com/technetwork/java/javase/downloads.


## HOW TO RUN

For Mac OS X click on the app. For Windows click on NextFractal.bat. For Linux run script NextFractal.sh.


## HOW TO USE

Please visit the official site http://nextfractal.nextbreakpoint.com.


## HOW TO BUILD

Download Maven and Ant. Make sure mvn and ant commands are in your system path. Define a M2_HOME environment variable equals to the Maven path. Checkout or download the source code from https://github.com/nextbreakpoint/nextfractal.

Open a terminal, change current folder to maven/com.nextbreakpoint.nextfractal.build and execute the command:

**ant all**
