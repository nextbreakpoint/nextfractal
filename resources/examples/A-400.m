<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:09:17</timestamp>
    <julia>false</julia>
    <point>1.3166666666666667</point>
    <point>0.016666666666666666</point>
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
		loop [0, 400] (re(x) &gt; 1000 | im(x) &gt; 1000 | mod2(x) &gt; 40) {
			zn = x * x + w - 1;
			zd = 2 * x + w - 2;
			if (mod2(zd) &lt; 0.000000000000000001) {
				zd = &lt;0.000000001,0&gt;;
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
			[#FFFD0000 &gt; #FF000000, 50];
			[#FF000000 &gt; #FFFFFFFF, 70];
			[#FFFFFFFF &gt; #FF000000, 280];
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>1.0972637344399763</translation>
    <translation>-1.682763002430122</translation>
    <translation>5.782826812775732E-5</translation>
    <translation>0.0</translation>
</mandelbrot>
