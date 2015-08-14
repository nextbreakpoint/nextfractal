<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-13 23:10:34</timestamp>
    <julia>true</julia>
    <point>0.2068944475345125</point>
    <point>-0.016480686141410628</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 2.5i,+2.5 + 2.5i] [x,n] {
		trap circle0 [&lt;0,0&gt;] {
			MOVETO(&lt;0.5,0&gt;);
			ARCTO(&lt;0.5,0.5&gt;,&lt;0,0.5&gt;);
			ARCTO(&lt;-0.5,0.5&gt;,&lt;-0.5,0&gt;);
			ARCTO(&lt;-0.5,-0.5&gt;,&lt;0,-0.5&gt;);
			ARCTO(&lt;0.5,-0.5&gt;,&lt;0.5,0&gt;);
		}
		trap circle1 [&lt;0,0&gt;] {
			MOVETO(&lt;0.1,0&gt;);
			ARCTO(&lt;0.1,0.1&gt;,&lt;0,0.1&gt;);
			ARCTO(&lt;-0.1,0.1&gt;,&lt;-0.1,0&gt;);
			ARCTO(&lt;-0.1,-0.1&gt;,&lt;0,-0.1&gt;);
			ARCTO(&lt;0.1,-0.1&gt;,&lt;0.1,0&gt;);
		}
		begin {
			if (~julia) {
				x = &lt;pi / 2,0&gt;;
			}
			d = mod2(w);
			if (d &gt; 0.00000001) {
				m = 1 / d;
				w = &lt;re(w) * m - 1.40115, -im(w) * m&gt;;
			}
			else {
				w = &lt;1000000000000000, 0&gt;;
			}
		}
		loop [0, 200] (abs(im(x)) &gt; 100) {
			xr = re(x);
			xi = im(x);
			wr = re(w);
			wi = im(w);
			ta = cos(xr);
			tb = sin(xr);
			tc = exp(+xi);
			td = exp(-xi);
			te = 0.5 * tb * (tc + td);
			tf = 0.5 * ta * (tc - td);
			zr = wr * te - wi * tf;
			zi = wi * te + wr * tf;			
			x = &lt;zr,zi&gt;;
			if (circle0 ~? x) {
				stop;
			}
			if (mod2(x) &lt; 0.0000000001) {
				stop;
			}
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FF000000 &gt; #FFD30000, 50];
			[#FFD30000 &gt; #FFFFFFFF, 20];
			[#FFFFFFFF &gt; #FFFFFFFF, 30];
			[#FFFFFFFF &gt; #FF090032, 50];
			[#FF090032 &gt; #FF000000, 50];
		}
		init {
			m = mod(x);
			m = 199 * (m - floor(m));
		}
		rule (n &gt; 0) [1] {
			gradient[m]
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
