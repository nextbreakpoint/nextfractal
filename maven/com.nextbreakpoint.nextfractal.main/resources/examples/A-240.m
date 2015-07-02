<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-05-10 21:48:54</timestamp>
    <julia>true</julia>
    <point>0.37666666666666665</point>
    <point>0.005</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-2.5 - 1.5i,+0.5 + 1.5i] [x,n,z,q] {
		begin {
			t = x;
			z = x * x + w;
			x = z - t;
			d0 = mod(x);
			l0 = 0;
			mz = d0;
			pz = abs(atan2(re(x),im(x)));
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
			mz = mz + d1;
			pz = pz + abs(atan2(re(x),im(x)));
		}
		end {
			if (n &gt; 0) {
				mz = mz / n;
				pz = pz / n;
				q = &lt;mz * cos(pz), mz * sin(pz)&gt;;
			} else {
				mz = 0;
				pz = 0;
			}
		}
	}
	color [(1,0,0,0)] {
		palette gradient {
			[#FFFF0A0A &gt; #FFFFFFFF, 80];
			[#FFFFFFFF &gt; #FF0042A9, 120];
		}
		init {
			m = atan2(re(q),im(q)) / 2pi;
			if (m &lt; 0) {
				m = m + 1;
			}
		}
		rule (n = 0) [1] {
			1,0,0,0
		}
		rule (n &gt; 0) [1] {
			gradient[199 * m]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>-0.12364171945387192</traslation>
    <traslation>1.0766768752063494</traslation>
    <traslation>0.1726574146215016</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
