#!/usr/bin/env bash
sh grammar.sh $(pwd)/../com.nextbreakpoint.nextfractal.mandelbrot/src/main/java/com/nextbreakpoint/nextfractal/mandelbrot/grammar/Mandelbrot.g4 >Mandelbrot.txt
sh grammar.sh $(pwd)/../com.nextbreakpoint.nextfractal.contextfree/src/main/java/com/nextbreakpoint/nextfractal/contextfree/grammar/CFDG.g4 >CFDG.txt
