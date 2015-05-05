<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-28 18:57:15</timestamp>
    <julia>false</julia>
    <point>-0.665022915654355</point>
    <point>-0.3271503647689797</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [&lt;-3.0,-1.5&gt;,&lt;0.0,1.5&gt;] [x,n] {
		loop [0, 200] (mod2(x) &gt; 4) {
			x = x * x + w;
		}
	}
	color [(1,0,0,0)] {
		init {
			z = log(re(x));
		}
		rule (n = 0) [1.0] {
			1,0,0,0
		}
		rule (re(x) &gt; 1) [1.0] {
			1,z,1,1
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
