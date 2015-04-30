<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 16:24:23</timestamp>
    <julia>true</julia>
    <point>0.48</point>
    <point>0.02</point>
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
		begin {
			s = 0;
			if (~julia) {
				x = w;
				w = 0;
			}
		}
		loop [0, 200] (s = 1) {
			ta = x * x;
			tb = x * ta;
			tc = tb - w;
			if (mod2(tc) &lt; 0.0000001) {
				x = x - w;
				stop;
			}
			tc = 2 * tb + w;
			td = 3 * ta;
			if (mod2(td) &lt; 0.000000000000000000000001) {
				td = &lt;0.000000000001, 0&gt;;
			}
			x = tc / td;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FF0042A9, 60];
			[#FF0042A9 &gt; #FF000000, 120];
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1]
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
