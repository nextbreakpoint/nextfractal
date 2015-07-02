<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 16:35:24</timestamp>
    <julia>false</julia>
    <point>0.42630508069430717</point>
    <point>0.2536917043616403</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [&lt;-2.5,-1.5&gt;,&lt;0.5,1.5&gt;] [x,n] {
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
	}
	color [(1,0,0,0)] {
		init {
			z = atan2(re(x),im(x));
			c = 0;
			if (z &gt; 0) {
				c = 1;
			} 
		}
		rule (n = 0) [1.0] {
			1,0,0,0
		}
		rule (n &gt; 0) [1.0] {
			c
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
