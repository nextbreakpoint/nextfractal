<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2016-01-16 21:15:36</timestamp>
    <julia>true</julia>
    <point>-0.8</point>
    <point>-0.07857142857142857</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n,m] {
		trap circle0 [&lt;0.3,0&gt;] {
			MOVETO(&lt;0.55,0&gt;);
			ARCTO(&lt;0.55,0.55&gt;,&lt;0,0.55&gt;);
			ARCTO(&lt;-0.55,0.55&gt;,&lt;-0.55,0&gt;);
			ARCTO(&lt;-0.55,-0.55&gt;,&lt;0,-0.55&gt;);
			ARCTO(&lt;0.55,-0.55&gt;,&lt;0.55,0&gt;);
		}
		trap circle1 [&lt;0.2,0&gt;] {
			MOVETO(&lt;0.6,0&gt;);
			ARCTO(&lt;0.6,0.6&gt;,&lt;0,0.6&gt;);
			ARCTO(&lt;-0.6,0.6&gt;,&lt;-0.6,0&gt;);
			ARCTO(&lt;-0.6,-0.6&gt;,&lt;0,-0.6&gt;);
			ARCTO(&lt;0.6,-0.6&gt;,&lt;0.6,0&gt;);
		}
		begin {
			dmax = 0;
			m = 0;
		}
		loop [0, 200] (re(x) &gt; 100 | im(x) &gt; 100 | (circle0 ~? x &amp; circle1 ? x)) {
			x = x * x * x * x + w;
			dmax = dmax + mod2(x);
		}
		end {
			m = dmax / n;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FF0042A9, 60];
			[#FF0042A9 &gt; #FF000000, 120];
		}
		init {
			p = atan2(re(x), im(x)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
		}
		rule (n &gt; 0) [1] {
			gradient[n + re(m) * 50]
		}
		rule (n &gt; 0) [0.1] {
			gradient[199 * p]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.11841442982394726</traslation>
    <traslation>-0.46315621527019074</traslation>
    <traslation>0.3100679102826498</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
