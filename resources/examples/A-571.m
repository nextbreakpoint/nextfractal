<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2016-11-12 17:20:23</timestamp>
    <julia>true</julia>
    <point>-1.169620253164557</point>
    <point>-0.06075949367088608</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-1.5 - 1.5i,+1.5 + 1.5i] [x,n,m] {
		trap circle0 [&lt;0,0&gt;] {
			MOVETO(&lt;0.55,0&gt;);
			ARCTO(&lt;0.55,0.55&gt;,&lt;0,0.55&gt;);
			ARCTO(&lt;-0.55,0.55&gt;,&lt;-0.55,0&gt;);
			ARCTO(&lt;-0.55,-0.55&gt;,&lt;0,-0.55&gt;);
			ARCTO(&lt;0.55,-0.55&gt;,&lt;0.55,0&gt;);
		}
		trap circle1 [&lt;0,0&gt;] {
			MOVETO(&lt;0.6,0&gt;);
			ARCTO(&lt;0.6,0.6&gt;,&lt;0,0.6&gt;);
			ARCTO(&lt;-0.6,0.6&gt;,&lt;-0.6,0&gt;);
			ARCTO(&lt;-0.6,-0.6&gt;,&lt;0,-0.6&gt;);
			ARCTO(&lt;0.6,-0.6&gt;,&lt;0.6,0&gt;);
		}
		trap circle2 [&lt;0.5,0.5&gt;] {
			MOVETO(&lt;0.55,0&gt;);
			ARCTO(&lt;0.55,0.55&gt;,&lt;0,0.55&gt;);
			ARCTO(&lt;-0.55,0.55&gt;,&lt;-0.55,0&gt;);
			ARCTO(&lt;-0.55,-0.55&gt;,&lt;0,-0.55&gt;);
			ARCTO(&lt;0.55,-0.55&gt;,&lt;0.55,0&gt;);
		}
		trap circle3 [&lt;0.5,0.5&gt;] {
			MOVETO(&lt;0.6,0&gt;);
			ARCTO(&lt;0.6,0.6&gt;,&lt;0,0.6&gt;);
			ARCTO(&lt;-0.6,0.6&gt;,&lt;-0.6,0&gt;);
			ARCTO(&lt;-0.6,-0.6&gt;,&lt;0,-0.6&gt;);
			ARCTO(&lt;0.6,-0.6&gt;,&lt;0.6,0&gt;);
		}
		begin {
			dmax = 0;
			m = 0;
		}
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000 | ((circle0 ~? x &amp; circle1 ? x) | (circle2 ~? x &amp; circle3 ? x))) {
			x = x * x + w;
			dmax = max(dmax, mod2(x));
		}
		end {
			m = dmax - floor(dmax);
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FF0042A9, 60];
			[#FF0042A9 &gt; #FF000000, 120];
		}
		init {
			p = atan2(re(x), im(x)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
		}
		rule (n &gt; 0) [1] {
			gradient[n + re(m) * 2]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>-0.6918847182474644</translation>
    <translation>-0.025664186004119315</translation>
    <translation>4.948451807734237E-4</translation>
    <translation>0.0</translation>
</mandelbrot>
