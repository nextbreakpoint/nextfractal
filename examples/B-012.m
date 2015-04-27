<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-27 15:51:42</timestamp>
    <julia>true</julia>
    <point>-0.7433333333333333</point>
    <point>-0.185</point>
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
			MOVETO(&lt;-1.0,+0&gt;);
			ARCTO(&lt;1,4&gt;);
			ARCTO(&lt;2,5&gt;);
			ARCTO(&lt;3,-6&gt;);
			ARCTO(&lt;-4,0&gt;);
		}		
		trap trap2 [&lt;0,0&gt;] {
			MOVETO(&lt;-3.0,+0.25&gt;);
			LINETO(&lt;-3.0,+0.25&gt;);
			LINETO(&lt;+3.0,+0.45&gt;);
			LINETO(&lt;+3.0,-0.45&gt;);
			LINETO(&lt;-3.0,-0.2&gt;);
		}		
		begin {
			z = x;
		}
		loop [0, 1000] (trap1 ~? z &amp; trap2 ~? z) {
			z = z * z + w;
		}
	}
	color [#FF000000] {
		palette palette1 {
			[#FF000000 &gt; #90FFFFFF, 10];
			[#FFFF0000 &gt; #40FF0000, 10];
		}
		rule (re(n) = 0) [0.6] {
			1,sin(2*3.1415*|z|),0,0
		}
		rule (re(n) = 0) [0.4] {
			1,|z|,|z|,0
		}
		rule (re(n) &gt; 0) [0.5] {
			palette1[re(n)]
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
