<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-17 08:30:33</timestamp>
    <julia>true</julia>
    <point>-0.735</point>
    <point>-0.34500000000000003</point>
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
		trap trap1 [&lt;0,0&gt;] {
			MOVETO(&lt;-1.0,+0&gt;);
			LINETO(&lt;1,4&gt;);
			LINETO(&lt;3,-6&gt;);
			LINETO(&lt;-4,0&gt;);
			CLOSE;
		}		
		trap trap2 [&lt;0,0&gt;] {
			MOVETO(&lt;-3.0,+0.25&gt;);
			LINETO(&lt;-3.0,+0.25&gt;);
			LINETO(&lt;+3.0,+0.45&gt;);
			LINETO(&lt;+3.0,-0.45&gt;);
			LINETO(&lt;-3.0,-0.25&gt;);
			CLOSE;
		}		
		loop [0, 1000] (trap1 ~? x &amp; trap2 ~? x) {
			x = x * x + w;
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FF000000 &gt; #90FFFFFF, 10];
			[#FFFF0000 &gt; #40FF0000, 10];
		}
		rule (n = 0) [0.5] {
			1,|x|,|x|,0
		}
		rule (n &gt; 0) [0.9] {
			gradient[n - 1]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.5713602727254233</traslation>
    <traslation>0.06921614561019815</traslation>
    <traslation>0.19035479962020577</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
