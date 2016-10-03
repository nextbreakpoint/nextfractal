<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mandelbrot>
    <timestamp>2015-08-15 08:26:33</timestamp>
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
    <translation>1.0035415132680543</translation>
    <translation>0.08301072738552331</translation>
    <translation>0.015809188546631055</translation>
    <translation>0.0</translation>
</mandelbrot>
