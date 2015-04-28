<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-28 22:05:49</timestamp>
    <julia>true</julia>
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
			z = atan2(re(m),im(m)) / 2pi;
			if (z &lt; 0) {
				z = z + 1;
			}
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[179 * z]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.07732499999999988</traslation>
    <traslation>-0.010124999999999995</traslation>
    <traslation>1.1024999999999998</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
