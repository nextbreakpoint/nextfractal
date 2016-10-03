<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 18:16:16</timestamp>
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
	orbit [2.5 - 2.5i,+7.5 + 2.5i] [x,n] {
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
			[#FF000000 &gt; #FFFFFFFF, 100];
			[#FFFFFFFF &gt; #FF000000, 100];
		}
		init {
			z1 = log(mod2(x)) / 10;
			z2 = (n + atan2(re(x),im(x))) / 10;
			z3 = (n + im(x) * im(x)) / 100;
		}
		rule (n &gt; 0) [1] {
			1,
			(1 + sin(z1 * 6 / pi)) / 2,
			(1 + sin(z2 * 2 / pi)) / 2,
			(1 + sin(z3 * 1 / pi)) / 2
		}
	}
}
</source>
    <time>0.0</time>
    <translation>0.13616240146852407</translation>
    <translation>0.08396681423892319</translation>
    <translation>0.863837598531476</translation>
    <translation>0.0</translation>
</mandelbrot>
