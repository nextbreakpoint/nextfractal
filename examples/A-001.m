<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-27 15:47:23</timestamp>
    <julia>true</julia>
    <point>0.3016666666666667</point>
    <point>-0.005</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,z] {
		trap rectangle [&lt;0,0&gt;] {
			MOVETO(&lt;0.0,0.0&gt;);
			LINETO(&lt;0.0,-1.0&gt;);
			LINETO(&lt;+1.0,-1.0&gt;);
			LINETO(&lt;+1.0,0.0&gt;);
			LINETO(&lt;0.0,0.0&gt;);
		}		
		loop [0, 200] (mod2(x) &gt; 40 | rectangle ? x) {
			x = x * x + w;
		}
		end {
			z = atan2(re(x),im(x)) / 2pi;
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 20];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		rule (re(n) = 0) [1.0] {
			1,0,0,0
		}
		rule (re(n) &gt; 0 &amp; re(z) &gt; 0) [1] {
			gradient[187 * re(z)]
		}
		rule (re(n) &gt; 0 &amp; re(z) &lt; 0) [1] {
			gradient[187 * (1 + re(z))]
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
