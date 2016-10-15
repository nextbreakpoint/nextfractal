<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:06:11</timestamp>
    <julia>true</julia>
    <point>0.7650199887427409</point>
    <point>-0.13989049127562414</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n,z] {
		trap circle0 [&lt;0.3,0.2&gt;] {
			MOVETO(&lt;2,0&gt;);
			ARCTO(&lt;2,2&gt;,&lt;0,2&gt;);
			ARCTO(&lt;-2,2&gt;,&lt;-2,0&gt;);
			ARCTO(&lt;-2,-2&gt;,&lt;0,-2&gt;);
			ARCTO(&lt;2,-2&gt;,&lt;2,0&gt;);
		}
		begin {
			mmax = 0;
			z = 0;
		}

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
			m = mod(x);
			if (m &gt; mmax) {
				mmax = m;
				z = x;
			}
			if (circle0 ~? x) {
				stop;
			}
		}
	}
	color [(1,0,0,0)] {
		palette gradient0 {
			[#FFFF0000 &gt; #FFFFFF00, 20];
			[#FFFFFF00 &gt; #FFFFFFFF, 90];
			[#FFFFFFFF &gt; #FF000000, 90];
		}
		palette gradient1 {
			[#FF000088 &gt; #FFFFFF00, 10];
			[#FFFFFF00 &gt; #FFFFFFFF, 10];
			[#FFFFFFFF &gt; #FF000000, 180];
		}
		init {
			m = mod(z);
			p = atan2(re(z), im(z)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			p = 199 * p;
			t = 199 * (m - floor(m));
		}
		rule (n &gt; 0) [1] {
			gradient1[n - 1]
		}
		rule (n &gt; 0) [0.1] {
			gradient1[t]
		}
		rule (n &gt; 0) [0.5] {
			gradient1[p]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>0.20972159877545074</translation>
    <translation>1.0409436496798807</translation>
    <translation>1.0499999999999972</translation>
    <translation>0.0</translation>
</mandelbrot>
