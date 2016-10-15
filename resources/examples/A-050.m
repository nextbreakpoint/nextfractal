<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 08:48:11</timestamp>
    <julia>false</julia>
    <point>0.0</point>
    <point>0.0</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-3.0 - 1.5i,+0.0 + 1.5i] [x,n] {
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
	}
	color [#FFF0F0F0] {
		init {
			m = (1 + sin(&lt;x&gt; * pi * 10)) / 2;
		}
		rule (n = 0) [1] {
			1,
			m,
			m,
			m
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
