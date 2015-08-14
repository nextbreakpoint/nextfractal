<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:18:52</timestamp>
    <julia>true</julia>
    <point>-1.0</point>
    <point>-0.5750000000000001</point>
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
    <traslation>0.6058782768702173</traslation>
    <traslation>0.18249661142943113</traslation>
    <traslation>0.1288396211340386</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
