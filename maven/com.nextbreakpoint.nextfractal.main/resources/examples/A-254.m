<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-10 22:44:41</timestamp>
    <julia>true</julia>
    <point>0.32500000000000007</point>
    <point>-0.055</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-3.0 - 1.5i,+0.0 + 1.5i] [x,n,m] {
		trap circle0 [&lt;0,0&gt;] {
			MOVETO(&lt;1,0&gt;);
			LINETO(&lt;0,1&gt;);
			LINETO(&lt;-1,0&gt;);
			LINETO(&lt;0,-1&gt;);
			LINETO(&lt;1,0&gt;);
		}
		trap circle1 [&lt;0,0&gt;] {
			MOVETO(&lt;1.1,0&gt;);
			CURVETO(&lt;1.1,1.1&gt;,&lt;0,1.1&gt;,&lt;0,1.1&gt;);
			CURVETO(&lt;-1.1,1.1&gt;,&lt;-1.1,0&gt;,&lt;-1.1,0&gt;);
			CURVETO(&lt;-1.1,-1.1&gt;,&lt;0,-1.1&gt;,&lt;0,-1.1&gt;);
			CURVETO(&lt;1.1,-1.1&gt;,&lt;1.1,0&gt;,&lt;1.1,0&gt;);
		}
		begin {
			dmax = 0;
			m = 0;
		}
		loop [0, 200] (mod2(x) &gt; 40 | (circle0 ~? x &amp; circle1 ? x)) {
			x = x * x + w;
			dmax = max(dmax, mod2(x));
		}
		end {
			m = dmax - floor(dmax);
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FF0042A9, 60];
			[#FF0042A9 &gt; #FF000000, 120];
		}
		init {
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[199 * re(m)]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.34939619180760895</traslation>
    <traslation>-0.012215268355494312</traslation>
    <traslation>1.0499999999999974</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
