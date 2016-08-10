<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:00:05</timestamp>
    <julia>true</julia>
    <point>0.425</point>
    <point>-0.13</point>
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
		trap circle0 [&lt;0,0&gt;] {
			MOVETO(&lt;0,0&gt;);
			LINETO(&lt;2,0&gt;);
			LINETO(&lt;2,-2&gt;);
			LINETO(&lt;0,-2&gt;);
			LINETO(&lt;0,0&gt;);
		}
		loop [0, 200] (re(x) &gt; 10 | im(x) &gt; 10 | circle0 ? x) {
			x = x * x + w;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FF0042A9, 60];
			[#FF0042A9 &gt; #FF000000, 120];
		}
		init {
			m = mod(x);
			m = m - floor(m);
		}
		rule (n &gt; 0) [1] {
			gradient[n + re(m)]
		}
		rule (n &gt; 0) [0.1] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.0</traslation>
    <traslation>0.0</traslation>
    <traslation>1.0</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
