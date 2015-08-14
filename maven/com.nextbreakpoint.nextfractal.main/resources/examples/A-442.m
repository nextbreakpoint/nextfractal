<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:10:58</timestamp>
    <julia>false</julia>
    <point>-1.0202019953957754E-4</point>
    <point>-0.7226903606044558</point>
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
		loop [0, 200] (abs(im(x)) &gt; 100) {
			xr = re(x);
			xi = im(x);
			wr = re(w);
			wi = im(w);
			ta = cos(xr);
			tb = sin(xr);
			tc = exp(+xi);
			td = exp(-xi);
			te = 0.5 * ta * (tc + td);
			tf = 0.5 * tb * (tc - td);
			zr = wr * te + wi * tf;
			zi = wi * te - wr * tf;			
			x = &lt;zr,zi&gt;;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FF080062 &gt; #FFFFFFFF, 5];
			[#FFFFFFFF &gt; #FFCBFFFF, 5];
			[#FFCBFFFF &gt; #FFFFFFFF, 5];
			[#FFFFFFFF &gt; #FFD7000A, 45];
			[#FFD7000A &gt; #FFD7000A, 150];
		}
		init {
			m = atan2(re(x), im(x));
			c = 0;
			if (m &gt; 0) {
				c = 1;
			}
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1]
		}
		rule (n &gt; 0) [0.1] {
			1,c,c,c
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
