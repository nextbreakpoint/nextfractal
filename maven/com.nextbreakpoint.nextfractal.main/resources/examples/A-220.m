<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-07-24 18:37:49</timestamp>
    <julia>false</julia>
    <point>0.3016666666666667</point>
    <point>0.025</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n] {		
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
	}
	color [#FF000000] {
		init {
			z1 = mod2(x) / 1000;
			z2 = n / 10;
			z3 = (n + re(x)) / 25;
		}
		rule (n &gt; 0) [1] {
			1,
			(1 + cos(z1 * 3 / pi)) / 2,
			(1 + sin(z2 * 5 / pi)) / 2,
			(1 + sin(z3 * 2 / pi)) / 2
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.04987836858099255</traslation>
    <traslation>0.10073493040451262</traslation>
    <traslation>0.8227024747918819</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
