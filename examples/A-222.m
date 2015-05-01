<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-01 16:14:23</timestamp>
    <julia>true</julia>
    <point>-0.8183333333333334</point>
    <point>-0.27499999999999997</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n] {		
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FF000000 &gt; #FFFFFFFF, 100];
			[#FFFFFFFF &gt; #FF000000, 100];
		}
		init {
			z1 = mod2(x) / 1000;
			z2 = n / 8;
			z3 = (n + re(x)) / 50;
		}
		rule (n &gt; 0) [1] {
			1,
			(1 + cos(z1 * 3 / pi)) / 2,
			(1 + sin(z2 * 5 / pi)) / 2,
			(1 + sin(z3 * 2 / pi)) / 2
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.3495311091943235</traslation>
    <traslation>-0.023373701212028166</traslation>
    <traslation>1.3400956406250004</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
