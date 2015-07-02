<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-21 23:47:57</timestamp>
    <julia>true</julia>
    <point>0.2910728041678073</point>
    <point>-0.7430001791855614</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,q] {
		trap circle0 [&lt;0,0&gt;] {
			MOVETO(&lt;0.5,0&gt;);
			ARCTO(&lt;0.5,0.5&gt;,&lt;0,0.5&gt;);
			ARCTO(&lt;-0.5,0.5&gt;,&lt;-0.5,0&gt;);
			ARCTO(&lt;-0.5,-0.5&gt;,&lt;0,-0.5&gt;);
			ARCTO(&lt;0.5,-0.5&gt;,&lt;0.5,0&gt;);
		}
		trap circle1 [&lt;0,0&gt;] {
			MOVETO(&lt;0.4,0&gt;);
			ARCTO(&lt;0.4,0.4&gt;,&lt;0,0.4&gt;);
			ARCTO(&lt;-0.4,0.4&gt;,&lt;-0.4,0&gt;);
			ARCTO(&lt;-0.4,-0.4&gt;,&lt;0,-0.4&gt;);
			ARCTO(&lt;0.4,-0.4&gt;,&lt;0.4,0&gt;);
		}
		begin {
			mmax = 0;
			q = 0;
			t = x;
			z = x * x + w;
			x = z - t;
			d0 = mod(x);
			l0 = 0;
		}
		loop [0, 200] (re(x) &gt; 1000 | im(x) &gt; 1000) {
			z = z * z + w;
			x = z - t;
			if (d0 &lt; 0.0000001) {
				stop;
			}
			d1 = mod(x);
			l1 = d1 / d0;
			d = abs(l1 - l0);
			if (d &lt; 0.000000000001 | d &gt; 1000000) {
				stop;
			}
			d0 = d1;
			l0 = l1;
			m = mod2(z);
			if (m &gt; mmax) {
				mmax = m;
				q = x;
			}
			if (circle0 ? z &amp; circle1 ~? z) {
				stop;
			}
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FF00402B &gt; #FF4FFFFF, 20];
			[#FF4FFFFF &gt; #FFFFFFFF, 10];
			[#FFFFFFFF &gt; #FF000000, 170];
		}
		init {
			m = mod(q);
			m = m - floor(m);
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[n - 1 + m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.27082239875863673</traslation>
    <traslation>-0.6939386294089577</traslation>
    <traslation>0.5303213506452942</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
