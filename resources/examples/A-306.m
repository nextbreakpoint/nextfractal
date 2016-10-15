<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-15 08:37:20</timestamp>
    <julia>true</julia>
    <point>-0.8250000000000001</point>
    <point>0.185</point>
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
    <translation>-0.16708619141708173</translation>
    <translation>-0.024624392158756937</translation>
    <translation>0.08305116854512422</translation>
    <translation>0.0</translation>
</mandelbrot>
