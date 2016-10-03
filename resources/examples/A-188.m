<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-14 18:41:52</timestamp>
    <julia>true</julia>
    <point>0.53</point>
    <point>-0.475</point>
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
		palette gradient {
			[#FF000000 &gt; #FFFFFFFF, 200];
		}
		init {
			m = |x|;
			m = (1 + sin((n + m - floor(m)) * 3 / 2pi)) / 2;
		}
		rule (n &gt; 0) [1.0] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>-0.12472506755274548</translation>
    <translation>0.5960536926860671</translation>
    <translation>0.18129028535257685</translation>
    <translation>0.0</translation>
</mandelbrot>
