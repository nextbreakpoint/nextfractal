<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:03:49</timestamp>
    <julia>true</julia>
    <point>-0.065</point>
    <point>0.66</point>
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
		trap circle0 [&lt;-0.6,-0.2&gt;] {
			MOVETO(&lt;0,0&gt;);
			LINETO(&lt;2,0&gt;);
			LINETO(&lt;2,-2&gt;);
			LINETO(&lt;0,-2&gt;);
			LINETO(&lt;0,0&gt;);
		}
		begin {
			mavg = 0;
			pavg = 0;
		}
		loop [0, 200] (mod2(x) &gt; 40 | circle0 ~? x) {
			x = x * x + w;
			mavg = mavg + mod(x);
			pavg = pavg + atan2(re(x), im(x));
		}
		end {
			mavg = mavg / n;			
			pavg = pavg / n;
			z = &lt;mavg * cos(pavg), mavg * sin(pavg)&gt;;			
		}
	}
	color [(1,0,0,0)] {
		palette gradient0 {
			[#FFFF0000 &gt; #FFFFFF00, 20];
			[#FFFFFF00 &gt; #FFFFFFFF, 90];
			[#FFFFFFFF &gt; #FF000000, 90];
		}
		palette gradient1 {
			[#FF440088 &gt; #FFFFFF00, 40];
			[#FFFFFF00 &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF000000, 80];
		}
		init {
			m = mod(x);
			p = atan2(re(x), im(x)) / 2pi;
			if (p &lt; 0) {
				p = p + 1;
			}
			q = (n - 1) + m - floor(m);
			m = mod(x);
			t = (n - 1) + (log(log(40)) - log (log(m))) / log(2);
		}
		rule (n &gt; 0) [0.2] {
			gradient0[t]
		}
		rule (n &gt; 0) [0.8] {
			gradient1[q]
		}
		rule (n &gt; 0) [0.9] {
			gradient1[199 * p]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>0.0</translation>
    <translation>0.0</translation>
    <translation>1.0</translation>
    <translation>0.0</translation>
</mandelbrot>
