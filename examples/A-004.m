<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-04-21 20:09:22</timestamp>
    <julia>false</julia>
    <point>0.09216666666666673</point>
    <point>-0.0754999999999998</point>
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
			LINETO(&lt;0.0,+1.0&gt;);
			LINETO(&lt;+1.0,+1.0&gt;);
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
			[#FFFFFFFF &gt; #FF0042A9, 80];
			[#FF0042A9 &gt; #FF000000, 80];
		}
		rule (re(n) &gt; 0 &amp; re(z) &gt;= 0) [1] {
			gradient[239 * re(z)]
		}
		rule (re(n) &gt; 0 &amp; re(z) &lt; 0) [1] {
			gradient[239 * (1.0 + re(z))]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>2.499999999999754E-4</traslation>
    <traslation>-0.007249999999999804</traslation>
    <traslation>1.05</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
