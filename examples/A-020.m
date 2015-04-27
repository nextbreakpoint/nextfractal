<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-22 08:43:03</timestamp>
    <julia>false</julia>
    <point>0.3016666666666667</point>
    <point>0.025</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,z1,z2,z3] {		
		loop [0, 200] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
		end {
			z1 = log(mod2(x));
			z2 = atan2(re(x),im(x));
			z3 = re(x) * im(x);
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FF000000 &gt; #FFFFFFFF, 100];
			[#FFFFFFFF &gt; #FF000000, 100];
		}
		rule (re(n) &gt; 0) [1] {
			1,
			(1 + sin(re(z1) * 6 / pi) / 2),
			(1 + sin(re(z2) * 2 / pi) / 2),
			(1 + sin(re(z3) * 1 / pi) / 2)
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
