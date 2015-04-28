<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-28 18:40:02</timestamp>
    <julia>true</julia>
    <point>0.3066666666666667</point>
    <point>-0.005</point>
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
		trap rectangle [&lt;0,0&gt;] {
			MOVETO(&lt;0.0,0.0&gt;);
			LINETO(&lt;0.0,+1.0&gt;);
			LINETO(&lt;+1.0,+1.0&gt;);
			LINETO(&lt;+1.0,0.0&gt;);
			LINETO(&lt;0.0,0.0&gt;);
		}		
		loop [0, 200] (re(x) &gt; 100 | im(x) &gt; 100 | rectangle ? x) {
			x = x * x + w;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 20];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		init {
			z = atan2(re(x),im(x)) / 2pi;
			if (z &lt; 0) {
				z = z + 1;
			}
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[179 * z]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.5552372208641077</traslation>
    <traslation>-0.2616869222680887</traslation>
    <traslation>0.12270440108003647</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
