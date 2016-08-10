<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:11:16</timestamp>
    <julia>false</julia>
    <point>0.11666666666666667</point>
    <point>0.9916666666666667</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 2.5i,+2.5 + 2.5i] [x,n] {
		begin {
			if (~julia) {
				x = &lt;pi / 2,0&gt;;
			}
		}
		loop [0, 200] (abs(im(x)) &gt; 100) {
			xr = re(x);
			xi = im(x);
			wr = re(w);
			wi = im(w);
			ta = cos(xr);
			tb = sin(xr);
			tc = exp(+xi);
			td = exp(-xi);
			te = 0.5 * tb * (tc + td);
			tf = 0.5 * ta * (tc - td);
			zr = wr * te - wi * tf;
			zi = wi * te + wr * tf;			
			x = &lt;zr,zi&gt;;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FF000000 &gt; #FFECFF0C, 20];
			[#FFECFF0C &gt; #FF000000, 20];
			[#00000000 &gt; #00000000, 160];
		}
		init {
			m = atan2(re(x), im(x));
			if (m &lt; 0) {
				m = m + 1;
			}
			c = m;
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1]
		}
		rule (n &gt; 0) [0.5] {
			1,c,c,0
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
