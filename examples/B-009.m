<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-27 15:51:13</timestamp>
    <julia>false</julia>
    <point>-0.7633333333333334</point>
    <point>-0.08499999999999999</point>
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
			ARCTO(&lt;0,1&gt;);
			ARCTO(&lt;1,0&gt;);
			ARCTO(&lt;0,-1&gt;);
			ARCTO(&lt;-1,0&gt;);
		}		
		begin {
			z = x;
		}
		loop [0, 1000] (trap1 ~? z) {
			z = z * z + w;
		}
	}
	color [#FF000000] {
		palette palette1 {
			[#000000 &gt; #FFFFFF, 10];
			[#FF0000 &gt; #FF0000, 10];
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
