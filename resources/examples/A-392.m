<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-15 08:42:48</timestamp>
    <julia>true</julia>
    <point>0.16999999999999998</point>
    <point>-0.75</point>
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
		trap circle0 [&lt;-0.1,-0.5&gt;] {
			MOVETO(&lt;1,0.5&gt;);
			LINETO(&lt;-1,0.5&gt;);
			ARCTO(&lt;-1.5,0.5&gt;,&lt;-1.5,0&gt;);
			ARCTO(&lt;-1.5,-0.5&gt;,&lt;-1,-0.5&gt;);
			LINETO(&lt;1,-0.5&gt;);
			ARCTO(&lt;1.5,-0.5&gt;,&lt;1.5,0&gt;);
			ARCTO(&lt;1.5,0.5&gt;,&lt;1,0.5&gt;);
		}
		begin {
			mmax = 0;
			z = 0;
		}
		loop [0, 400] (circle0 ~? x) {
			x = x * x * x * x + w;
			m = mod(x);
			if (m &gt; mmax) {
				mmax = m;
				z = x;
			}
		}
	}
	color [(1,0,0,0)] {
		palette gradient0 {
			[#FFFF9583 &gt; #FFD30000, 100];
			[#FFD30000 &gt; #FFFF9583, 100];
		}
		init {
			m = mod(z);
			q = 199 * (m - floor(m));
		}
		rule (n &gt; 1) [1] {
			gradient0[q]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>-0.0012689230168168631</translation>
    <translation>0.0038067690504505892</translation>
    <translation>0.7462153966366274</translation>
    <translation>0.0</translation>
</mandelbrot>
