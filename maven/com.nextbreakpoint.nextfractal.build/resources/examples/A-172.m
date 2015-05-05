<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 16:48:32</timestamp>
    <julia>true</julia>
    <point>0.2549999999999999</point>
    <point>0.495</point>
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
		rule (n &gt; 0) [1.0] {
			0
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.15708964441735282</traslation>
    <traslation>-0.05077422325346456</traslation>
    <traslation>1.2155062499999998</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
