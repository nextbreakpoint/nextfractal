<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2016-11-12 17:43:13</timestamp>
    <julia>true</julia>
    <point>0.4033755274261604</point>
    <point>-0.11772151898734177</point>
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
		begin {
			t = x;
			z = x * x + w;
			x = z - t;
			d0 = mod(x);
			l0 = 0;
			z0 = 1i;
			z1 = 1i;
			z2 = 1i;
			z0 = &lt;0,0&gt;;
			z1 = &lt;0,0&gt;;
			z2 = &lt;0,0&gt;;
			k = 0;
			f = 0;
			mz = 0;
			pz = 0;
		}
		loop [0, 200] (re(x) &gt; 100 | im(x) &gt; 100) {
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
			k = k + 1;
			z0 = z1;
			z1 = z2;
			z2 = z;
			if (k &gt; 2 &amp; f = 0) {
				t1 = (z2 - z1);
				t2 = (z1 - z0);
				if (mod2(t2) &lt; 0.0000000000001) {
					f = 1;
				}
				c = t1 / t2;
				mz = mz + mod(c);
				pz = pz + abs(atan2(re(c),im(c)));
			}
		}
		end {
			if (k &gt; 2) {
				mz = mz / (k - 2);
				pz = pz / (k - 2);
			} else {
				mz = 0;
				pz = 0;
			}
			q = &lt;mz * cos(pz), mz * sin(pz)&gt;;
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFff00ff &gt; #FF000000, 33];
			[#FF000000 &gt; #FFFFFFFF, 13];
			[#FFFFFFFF &gt; #FF000000, 200];
		}
		init {
			m = atan2(re(q),im(q)) / 2pi;
			if (m &lt; 0) {
				m = m + 1;
			}
		}
		rule (n &gt; 0) [1] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <translation>0.41172307697290933</translation>
    <translation>-0.6166226209987605</translation>
    <translation>0.5050679529955182</translation>
    <translation>0.0</translation>
</mandelbrot>
