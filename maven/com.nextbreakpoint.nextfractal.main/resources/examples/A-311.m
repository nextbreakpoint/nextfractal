<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-11 17:43:10</timestamp>
    <julia>true</julia>
    <point>-0.05739685893807346</point>
    <point>0.6694744622124109</point>
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
		loop [0, 200] (re(x) &gt; 10 | im(x) &gt; 10 | circle0 ~? x) {
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
			[#FF0000FF &gt; #FFFFFF00, 15];
			[#FFFFFF00 &gt; #FFFFFFFF, 15];
			[#FFFFFFFF &gt; #FF000000, 170];
		}
		init {
			m = mod(z);
			m = m - floor(m);
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient0[n + re(m)]
		}
		rule (n &gt; 0) [0.1] {
			gradient1[1000 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.006463212759426263</traslation>
    <traslation>-0.0032316063797131313</traslation>
    <traslation>0.6768393620286869</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
