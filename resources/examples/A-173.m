<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 09:19:51</timestamp>
    <julia>true</julia>
    <point>-0.74</point>
    <point>0.135</point>
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
			m = |x|;
			m = m - floor(m);
			m1 = m / 4 + 0.5;
			m2 = m / 2 + 0.5;
			m3 = m / 2 + 0.5;
		}
		rule (n = 0) [1.0] {
			m1,m2,m3
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
