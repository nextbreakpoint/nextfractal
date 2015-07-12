$(function() {
	var xml = '<mandelbrot>\n'
	+ '<timestamp>2015-06-04 11:20:30</timestamp>\n'
	+ '<julia>false</julia>\n'
	+ '<point>0.0</point>\n'
	+ '<point>0.0</point>\n'
	+ '<rotation>0.0</rotation>\n'
	+ '<rotation>0.0</rotation>\n'
	+ '<rotation>0.0</rotation>\n'
	+ '<rotation>0.0</rotation>\n'
	+ '<scale>1.0</scale>\n'
	+ '<scale>1.0</scale>\n'
	+ '<scale>1.0</scale>\n'
	+ '<scale>1.0</scale>\n'
	+ '<source>fractal {\n'
	+ 'orbit [&lt;-2.5,-1.5&gt;,&lt;0.5,1.5&gt;] [x,n] {\n'
	+ '	loop [0, 200] (mod2(x) &gt; 4) {\n'
	+ '		x = x * x + w;\n'
	+ '	}\n'
	+ '}\n'
	+ 'color [(1,0,0,0)] {\n'
	+ '	rule (n = 0) [1.0] {\n'
	+ '		1,0,0,0\n'
	+ '	}\n'
	+ '	rule (n &gt; 0) [1.0] {\n'
	+ '		1,1,1,1\n'
	+ '	}\n'
	+ '}\n'
	+ '}\n'
	+ '</source>\n'
	+ '<time>0.0</time>\n'
	+ '<traslation>0.0</traslation>\n'
	+ '<traslation>0.0</traslation>\n'
	+ '<traslation>1.0</traslation>\n'
	+ '<traslation>0.0</traslation>\n'
	+ '</mandelbrot>\n';

	$("#xml").val(xml);

	var rows = 8;
	var cols = 8;
	var tileSize = 64;
	
	$("#form").submit(function() {
		$("#canvas").empty();
		
		for (var row = 0; row < rows; row++) {
			for (var col = 0; col < cols; col++) {

				var data = $("#xml").val();
				 
				var request = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n'
				+ '<mandelbrotrequest>\n'
				+ data
				+ '<tileSize>\n'
				+ tileSize
				+ '</tileSize>\n'
				+ '<rows>\n'
				+ rows
				+ '</rows>\n'
				+ '<cols>\n'
				+ cols
				+ '</cols>\n'
				+ '<row>\n'
				+ row
				+ '</row>\n'
				+ '<col>\n'
				+ col
				+ '</col>\n'
				+ '</mandelbrotrequest>\n'
				
				var encodedRequest = encodeURIComponent(Base64.encode(request));
				
				var left = col * tileSize;
				var top = row * tileSize;
				
				$("#canvas").append("<img style='position:absolute; left:" + left + "px; top:" + top + "px;' src='http://localhost:8080/mandelbrot?request=" + encodedRequest + "'/>");
			}
		}
		return false;
	});
});
