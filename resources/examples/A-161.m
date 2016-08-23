<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 09:16:48</timestamp>
    <julia>true</julia>
    <point>-0.11833333333333329</point>
    <point>-0.75</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [&lt;-1.5,-1.5&gt;,&lt;1.5,1.5&gt;] [x,n] {
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