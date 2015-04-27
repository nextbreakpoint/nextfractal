<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-03-31 21:12:21</timestamp>
    <julia>false</julia>
    <point>0.0</point>
    <point>0.0</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [z,n] {
		trap trap1 [&lt;0,0&gt;] {
			MOVETO(&lt;-1.0,+1.0&gt;);
			LINETO(&lt;-1.0,+1.0&gt;);
			LINETO(&lt;+1.0,+1.0&gt;);
			LINETO(&lt;+1.0,-1.0&gt;);
			LINETO(&lt;-1.0,-1.0&gt;);
		}		
		begin {
			z = x;
		}
		loop [0, 200] (trap1[z]) {
			z = z * z + w;
		}
	}
	color [#FF000000] {
		rule (re(n) = 0) [1.0] {
			1,0,0,0
		}
		rule (re(n) &gt; 0) [1.0] {
			1,1,1,1
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.11473113177171684</traslation>
    <traslation>-0.035718182532704326</traslation>
    <traslation>0.7835261664684587</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
