<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-14 18:57:19</timestamp>
    <julia>true</julia>
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
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000 | mod2(x) &gt; 40) {
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
			[#FF000000 &gt; #FF78B8D5, 10];
			[#FF78B8D5 &gt; #FF000000, 30];
			[#FF000000 &gt; #FFFFFFFF, 160];
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>-2.0063205653278744</translation>
    <translation>-1.6694371057558086</translation>
    <translation>1.4612915344367175E-4</translation>
    <translation>0.0</translation>
</mandelbrot>
