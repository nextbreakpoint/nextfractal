<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-21 19:57:34</timestamp>
    <julia>false</julia>
    <point>0.3016666666666667</point>
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
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,z] {
		trap rectangle [&lt;0,0&gt;] {
			MOVETO(&lt;0.0,0.0&gt;);
			LINETO(&lt;0.0,+1.0&gt;);
			LINETO(&lt;+1.0,+1.0&gt;);
			LINETO(&lt;+1.0,0.0&gt;);
			LINETO(&lt;0.0,0.0&gt;);
		}		
		loop [0, 200] (rectangle ? x) {
			x = x * x + w;
		}
		end {
			z = atan2(re(x),im(x)) / 2pi;
		}
	}
	color [#FFff0000] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 80];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		rule (re(n) &gt;= 0 &amp; re(x) &gt;= 0) [1] {
			gradient[239 * re(x)]
		}
		rule (re(n) &gt;= 0 &amp; re(x) &lt; 0) [1] {
			gradient[239 * (1.0 + re(x))]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.043488906249999945</traslation>
    <traslation>0.002210915624999983</traslation>
    <traslation>1.21550625</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
