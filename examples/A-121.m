<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-29 16:37:06</timestamp>
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
	orbit [2.0 - 1.5i,+5.0 + 1.5i] [x,n] {
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000 | mod2(x) &gt; 40) {
			ta = x * x;
			tb = x * ta;
			tc = w - 1;
			td = w - 2;
			zn = x * ta + 3 * x * tc + tc * td;
			zd = 3 * ta + 3 * x * td + w * w - 3 * w + 3;
			if (mod2(ta) &lt; 0.000000000000000001) {
				ta = &lt;0.000000001,0&gt;;
			}
			z = zn / zd;
			x = z * z;
			if (mod2(x - 1) &lt; 0.00000000000001) {
				stop;
			}
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
