<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-22 08:26:56</timestamp>
    <julia>true</julia>
    <point>0.3259147384854972</point>
    <point>-0.004374728395137389</point>
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
		trap circle0 [&lt;0.3,0&gt;] {
			MOVETO(&lt;-1,-1&gt;);
			LINETO(&lt;1,-1&gt;);
			LINETO(&lt;1,1&gt;);
			LINETO(&lt;-1,1&gt;);
			LINETO(&lt;-1,-1&gt;);
		}
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x ^ 2.3 + w;
			if (circle0 ~? x) {
				stop;
			}
		}
	}
	color [(1,0.5,0.5,0.5)] {
		palette gradient {
			[#00000000 &gt; #FFFFFFFF, 10];
			[#FFFFFFFF &gt; #00000000, 60];
			[#00000000 &gt; #00000000, 130];
		}
		init {
			m = atan2(re(x),im(x)) / 2pi;
			if (m &lt; 0) {
				m = m + 1;
			}
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.38698069985019584</traslation>
    <traslation>-0.06070612933203136</traslation>
    <traslation>1.3400956406250004</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
