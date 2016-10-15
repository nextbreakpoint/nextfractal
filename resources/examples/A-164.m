<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 09:18:08</timestamp>
    <julia>true</julia>
    <point>0.39</point>
    <point>0.33499999999999996</point>
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
    <translation>0.0</translation>
    <translation>0.0</translation>
    <translation>1.0</translation>
    <translation>0.0</translation>
</mandelbrot>
