<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 09:11:33</timestamp>
    <julia>true</julia>
    <point>0.4083333333333333</point>
    <point>-0.03</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n,m] {
		begin {
			m = 0;
		}		
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
			m = m + x;
		}
		end {
			m = m / n;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 20];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		init {
			p = atan2(re(m),im(m)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			p = 179 * p;
		}
		rule (n &gt; 0) [1] {
			gradient[p]
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
