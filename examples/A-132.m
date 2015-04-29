<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-29 22:05:04</timestamp>
    <julia>false</julia>
    <point>1.5066666666666668</point>
    <point>0.2</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-5.5 - 5.5i,+5.5 + 5.5i] [x,n] {
		loop [0, 200] (re(x) &gt; 1000 | re(x) &gt; 100) {
			xr = re(x);
			xi = im(x);
			wr = re(w);
			wi = im(w);
			tc = exp(xr);
			ta = cos(xi) * tc;
			tb = sin(xi) * tc;
			zr = wr * ta - wi * tb;
			zi = wi * ta + wr * tb;			
			x = &lt;zr,zi&gt;;
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
