<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 18:05:39</timestamp>
    <julia>false</julia>
    <point>-0.2416666666666667</point>
    <point>0.275</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [2.5 - 2.5i,+7.5 + 2.5i] [x,n] {
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000 | mod2(x) &gt; 40) {
			zn = x * x + w - 1;
			zd = 2 * x + w - 2;
			if (mod2(zd) &lt; 0.000000000000000001) {
				tb = &lt;0.000000001,0&gt;;
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
			[#FF000000 &gt; #FFFFFFFF, 100];
			[#FFFFFFFF &gt; #FF000000, 100];
		}
		init {
			z1 = log(mod2(x));
			z2 = n + atan2(re(x),im(x));
			z3 = n + im(x) * im(x);
		}
		rule (n &gt; 0) [1] {
			1,
			(1 + sin(z1 * 2pi / 6) / 2),
			(1 + sin(z2 * 2pi / 2) / 2),
			(1 + sin(z3 * 2pi / 1) / 2)
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
