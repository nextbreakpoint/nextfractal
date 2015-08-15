<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-15 10:02:09</timestamp>
    <julia>true</julia>
    <point>0.5700000000000001</point>
    <point>0.07500000000000001</point>
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
		loop [0, 200] (mod2(x) &gt; 100) {
			x = x ^ 1.55 + w;
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
    <traslation>0.10594783511441552</traslation>
    <traslation>1.115400943965433</traslation>
    <traslation>0.35894236464095247</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
