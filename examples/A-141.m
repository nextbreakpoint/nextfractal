<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-30 16:29:25</timestamp>
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
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n] {
		begin {
			q = 2;
			wr = re(w);
			wi = im(w);
			tr = re(x);
			ti = im(x);
			xr = re(x);
			xi = im(x);
			ta = |x| ^ q;
			tb = atan2(xr,xi) * q;
			zr = ta * cos(tb) + wr;
			zi = ta * sin(tb) + wi;
			x = &lt;zr - tr,zi - ti&gt;;
			d0 = |x|;
			l0 = 0;
		}
		loop [0, 200] (xr &gt; 100 | xi &gt; 100) {
			xr = re(x);
			xi = im(x);
			ta = |x| ^ q;
			tb = atan2(xr,xi) * q;
			zr = ta * cos(tb) + wr;
			zi = ta * sin(tb) + wi;
			x = &lt;zr - tr,zi - ti&gt;;
			d1 = |x|;
			l1 = d1 / d0;
			d = abs(l1 - l0);
			if (d &lt; 0.000001 | d &gt; 1000 | d1 &lt; 0.000000001) {
				stop;
			}
			d0 = d1;
			l0 = l1;
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
