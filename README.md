# NextFractal 2.1.5

Copyright 2015-2024 Andrea Medeghini


## NOTICE

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.

NextFractal depends on several thirdparty libraries including FreeImage, FFmpeg, ANTLR, and other software from Apache Software Foundation.

NextFractal contains code derived from Xaos and ContextFree (C/C++ code ported to Java).


## DESCRIPTION

NextFractal is an application for creating fractals and other algorithmically generated images. Images are generated from instructions contained in a script. NextFractal provides tools for rendering fractals, creating time-based and event-based animations, organising images, and exporting images and animations.

NextFractal is currently able to interpret two scripting languages: Mandelbrot and CFDG. Mandelbrot is a domain specific language for creating images of the Mandelbrot set and its variants (the Julia and Fatou sets). CFDG is a context-free grammar for creating images by an iterative process.


## SYSTEM REQUIREMENTS

NextFractal has been tested on OS X 14 (Intel), Windows 11, and Linux/Debian 12 and Linux/Fedora 38. NextFractal requires a machine with at least 4-cores CPU and 8Gb RAM.


## DOCUMENTATION

Please see the [Wiki](https://github.com/nextbreakpoint/nextfractal/wiki) for documentation. The wiki contains a tutorial for the scripting language, and examples of scripts.


## BUILD INSTRUCTIONS

Get the code from https://github.com/nextbreakpoint/nextfractal:

    git clone https://github.com/nextbreakpoint/nextfractal.git

Install Eclipse Temurin 21 JDK and configure environment variable JAVA_HOME if needed. Install Apache Ant 1.10 and Apache Maven 3.9. Install Xcode and the development command line tools (only for Mac). 

Use target build-mac to build the distribution for Mac:

    ant build-mac

Use target build-linux to build the distribution for Linux:

    ant build-linux

Use target build-windows to build the distribution for Windows:

    ant build-windows
