<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-14 18:35:14</timestamp>
    <julia>true</julia>
    <point>0.2567677308377016</point>
    <point>-0.001072163857902552</point>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <rotation>0.0</rotation>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <scale>1.0</scale>
    <source>fractal {
	orbit [-3.0 - 1.5i,+0 + 1.5i] [x,n] {		
		loop [0, 400] (mod2(x) &gt; 40) {
			x = x * x + w;
		}
	}
	color [#FF000000] {
		palette gradient {
			[#FFFFFFFF &gt; #FF000000, 100];
			[#FF000000 &gt; #FFFFFFFF, 100];
		}
		init {
			m = 200 * ((1 + sin(mod(x) * 0.27 / pi)) / 2);
		}
		rule (n &gt; 0) [1] {
			gradient[m - 1]
		}
	}
}
</source>
    <time>0.0</time>
    <traslation>0.9861876583595336</traslation>
    <traslation>0.06752844723778245</traslation>
    <traslation>0.04194648369097167</traslation>
    <traslation>0.0</traslation>
</mandelbrot>
