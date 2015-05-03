<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-02 13:30:00</timestamp>
    <julia>false</julia>
    <point>2.0909318768984955</point>
    <point>-0.001171337098545334</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [3.5 - 2.5i,+8.5 + 2.5i] [x,n] {
		loop [0, 200] (re(x) &gt; 10000 | im(x) &gt; 10000 | mod2(x) &gt; 100*100) {
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
			[#FF000000 &gt; #FFFFFFFF, 100];
			[#FFFFFFFF &gt; #FF000000, 100];
		}
		init {
			z1 = (n + log(mod2(x))) / 80;
			z2 = (n + atan2(re(x),im(x))) / 80;
		}
		rule (n &gt; 0) [1] {
			1,
			(1 + cos(z1 * 22 / pi)) / 2,
			(1 + cos(z2 * 22 / pi)) / 2,
			(1 + cos(z2 * 22 / pi)) / 2
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
