<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-12 00:00:04</timestamp>
    <julia>false</julia>
    <point>0.9794719160016425</point>
    <point>0.24511734003590976</point>
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
		trap circle0 [&lt;-1,-0.2&gt;] {
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
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000 | mod2(x) &gt; 40) {
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
			p = atan2(re(z), im(z)) / pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			p = 199 * p;
			m = mod(z);
			g = 199 * m;
			q = (n - 1) * (m - floor(m));
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient0[q]
		}
		rule (n &gt; 0) [0.8] {
			gradient1[g]
		}
		rule (n &gt; 0) [0.7] {
			gradient1[p]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.9761138485116638</traslation>
    <traslation>-0.44736056436387944</traslation>
    <traslation>5.516015367592253</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
