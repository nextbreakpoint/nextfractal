<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-28 21:46:11</timestamp>
    <julia>true</julia>
    <point>-0.7433333333333333</point>
    <point>0.08</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n] {
		trap rectangle [&lt;-1,0&gt;] {
			MOVETO(&lt;-3.0,+0.45&gt;);
			LINETO(&lt;-3.0,+0.45&gt;);
			LINETO(&lt;+3.0,+0.45&gt;);
			LINETO(&lt;+3.0,-0.45&gt;);
			LINETO(&lt;-3.0,-0.45&gt;);
		}		
		loop [0, 500] (rectangle ~? x) {
			x = x * x + w;
		}
	}
	color [#FF000000] {
		rule (n = 0) [1] {
			1,1,0,0
		}
	}
}
</source>
    <time>0.0</time>
    <translation>0.0</translation>
    <translation>0.0</translation>
    <translation>1.0</translation>
    <translation>0.0</translation>
</mandelbrot>
