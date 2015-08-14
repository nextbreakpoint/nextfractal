<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 09:10:49</timestamp>
    <julia>false</julia>
    <point>0.3016666666666667</point>
    <point>-0.005</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-3.5 - 1.5i,-0.5 + 1.5i] [x,n,m] {
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
    <traslation>0.0</traslation>
    <traslation>0.0</traslation>
    <traslation>1.0</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
