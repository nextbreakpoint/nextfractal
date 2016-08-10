<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 07:54:40</timestamp>
    <julia>true</julia>
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
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n] {
		trap rectangle [&lt;0,0&gt;] {
			MOVETO(&lt;0.0,0.0&gt;);
			LINETO(&lt;0.0,-1.0&gt;);
			LINETO(&lt;+1.0,-1.0&gt;);
			LINETO(&lt;+1.0,0.0&gt;);
			LINETO(&lt;0.0,0.0&gt;);
		}		
		loop [0, 200] (mod2(x) &gt; 40 | rectangle ? x) {
			x = x * x + w;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 20];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		init {
			p = atan2(re(x),im(x)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			p = 179 * p;
		}
		rule (n &gt; 0) [1] {
			gradient[p]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.07732499999999988</traslation>
    <traslation>-0.010124999999999995</traslation>
    <traslation>1.1024999999999998</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
