# NextFractal 2.0.0

Copyright 2015-2017 Andrea Medeghini

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.


## NOTICE

NextFractal depends on several thirdparty libraries including FreeImage, FFmpeg, ANTLR, RichTextFX, ControlsFX and software from Apache Software Foundation.

NextFractal contains code derivated from original code of open source projects Xaos and ContextFree.


## DESCRIPTION

NextFractal is an application for creating fractals and other algorithmically generated images. Images are generated processing a script and some user provided parameters depending on the selected grammar. The grammar defines the domain specific language to use to compose the script. 

NextFractal is currently able to interpret M scripts and CFDG scripts. The M scripts are based on a domain specific language designed for creating Mandelbrot and Julia sets. The CFDG scripts are based on a context-free grammar designed for creating geometric shapes iteratively. 

NextFractal provides also tools for exploring Mandelbrot and Julia sets, browsing images, creating time based and events based animations, and rendering image and video files. NextFractal exports to formats PNG, AVI and Quicktime.


## SYSTEM REQUIREMENTS

NextFractal requires Java SDK 8 or later. You can download the latest SDK from Oracle.

See download page http://www.oracle.com/technetwork/java/javase/downloads

    Java SDK contains the Java compiler which is required to compile code in memory and reduce the rendering time.

## USER GUIDE

Please see documentation and tutorials on http://nextbreakpoint.com/nextfractal.

NextFractal accepts few keyboard commands when focus is on rendering area:

    Press keys 1, 2, 3, 4, 5 to change zoom speed

    Press key T to show/hide traps

    Press key O to show/hide orbit

    Press key P to show/hide preview


## BUILD INSTRUCTIONS

You can build NextFractal from the source code. Checkout or download the source code from GitHub and run the build script. The build script requires Apache Ant and Apache Maven. By default the script will create a distribution for all platforms. Use a different target to build the distribution for a specific platform only. Apple command-line development tools are also required to build the distribution for MacOS. 

Get the code from https://github.com/nextbreakpoint/nextfractal:

    git checkout https://github.com/nextbreakpoint/nextfractal.git

Use default target to build the distribution for all platforms:
  
    ant
    
Use target build-linux to build Linux distribution:

    ant build-linux

Use target build-macos to build MacOS distribution:
  
    ant build-macos
    
Use target build-win32 to build Windows distribution:

    ant build-win32
