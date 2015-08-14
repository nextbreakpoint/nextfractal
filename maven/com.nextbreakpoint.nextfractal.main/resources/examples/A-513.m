<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:18:28</timestamp>
    <julia>true</julia>
    <point>-0.745</point>
    <point>0.08</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [&lt;-1.5,-1.5&gt;,&lt;+1.5,1.5&gt;] [x,n] {
		loop [0, 200] (mod2(x) &gt; 4) {
			x = x * x + w;
			if (|x| &gt; 2.2) {
				x = 1;
			}
		}
	}
	color [(1,1,1,1)] {
		rule (n &gt; 0) [1.0] {
			1,0,0,0
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.1552117091237535</traslation>
    <traslation>0.5199662864989644</traslation>
    <traslation>0.050986213091899144</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
