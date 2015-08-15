<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-15 09:59:45</timestamp>
    <julia>true</julia>
    <point>0.245</point>
    <point>0.01</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n] {
		loop [0, 200] (mod2(x) &gt; 120) {
			x = x ^ 1.33 + w;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#00000000 &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #00000000, 20];
			[#00000000 &gt; #00000000, 160];
		}
		init {
			m = atan2(re(x),im(x)) / 2pi;
			if (m &lt; 0) {
				m = m + 1;
			}
		}
		rule (n &gt; 0) [1] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.15609607158248306</traslation>
    <traslation>-0.7377279702120219</traslation>
    <traslation>0.3589423646409526</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
