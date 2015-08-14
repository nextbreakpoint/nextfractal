<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-14 18:40:47</timestamp>
    <julia>true</julia>
    <point>-0.7170243839996369</point>
    <point>0.3277528649298041</point>
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
    <traslation>0.49031260924883985</traslation>
    <traslation>-0.18754252878448663</traslation>
    <traslation>0.3100679102826499</traslation>
    <traslation>0.0</traslation>
</mandelbrot>