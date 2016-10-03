<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:05:48</timestamp>
    <julia>false</julia>
    <point>0.8995848371619241</point>
    <point>-1.9145320739066554</point>
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
		trap circle0 [&lt;-1.7,-0.2&gt;] {
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
		loop [0, 200] (im(x) &gt; 100) {
			zn = x * x + w - 1;
			zd = 2 * x + w - 2;
			if (mod2(zd) &lt; 0.000000000000000001) {
				zd = &lt;0.000000001,0&gt;;
			}
			z = zn / zd;
			x = z * z;
			m = mod(x);
			if (m &gt; mmax) {
				mmax = m;
				z = x;
			}
			if (mod2(x - 1) &lt; 0.00000000000001) {
				stop;
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
			[#FF000088 &gt; #FFFFFF00, 40];
			[#FFFFFF00 &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF000000, 80];
		}
		init {
			m = mod(x);
			p = atan2(re(z), im(z)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			p = 199 * p;
			g = (n - 1) + (m - floor(m));
			m = mod(z);
			q = 199 * (m - floor(m));
		}
		rule (n &gt; 0) [1] {
			gradient1[n - 1]
		}
		rule (n &gt; 0) [0.7] {
			gradient1[g]
		}
		rule (n &gt; 0) [0.2] {
			gradient1[p]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>1.0699285944486185</translation>
    <translation>0.05592173869024064</translation>
    <translation>0.7106813301301181</translation>
    <translation>0.0</translation>
</mandelbrot>
