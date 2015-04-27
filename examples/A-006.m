<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-21 20:19:22</timestamp>
    <julia>true</julia>
    <point>0.2969166666666668</point>
    <point>-0.001999999999999803</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,z] {
		trap rectangle [&lt;0,0&gt;] {
			MOVETO(&lt;0.0,0.0&gt;);
			LINETO(&lt;0.0,+1.0&gt;);
			LINETO(&lt;+1.0,+1.0&gt;);
			LINETO(&lt;+1.0,0.0&gt;);
			LINETO(&lt;0.0,0.0&gt;);
		}		
		loop [0, 200] (re(x) &gt; 100 | im(x) &gt; 1000 | rectangle ? x) {
			x = x * x + w;
		}
		end {
			z = atan2(re(x),im(x)) / 2pi;
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 80];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		rule (re(n) &gt; 0 &amp; re(z) &gt;= 0) [1] {
			gradient[239 * re(z)]
		}
		rule (re(n) &gt; 0 &amp; re(z) &lt; 0) [1] {
			gradient[239 * (1.0 + re(z))]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.14694878499039915</traslation>
    <traslation>0.20820394958207306</traslation>
    <traslation>0.05902291493050977</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
