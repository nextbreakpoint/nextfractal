$(function() {
    var Base64 = {
        _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

        encode: function(input) {
            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;

            input = Base64._utf8_encode(input);

            while (i < input.length) {

                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

            }

            return output;
        },

        decode: function(input) {
            var output = "";
            var chr1, chr2, chr3;
            var enc1, enc2, enc3, enc4;
            var i = 0;

            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            while (i < input.length) {

                enc1 = this._keyStr.indexOf(input.charAt(i++));
                enc2 = this._keyStr.indexOf(input.charAt(i++));
                enc3 = this._keyStr.indexOf(input.charAt(i++));
                enc4 = this._keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }

            }

            output = Base64._utf8_decode(output);

            return output;

        },

        _utf8_encode: function(string) {
            string = string.replace(/\r\n/g, "\n");
            var utftext = "";

            for (var n = 0; n < string.length; n++) {

                var c = string.charCodeAt(n);

                if (c < 128) {
                    utftext += String.fromCharCode(c);
                }
                else if ((c > 127) && (c < 2048)) {
                    utftext += String.fromCharCode((c >> 6) | 192);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
                else {
                    utftext += String.fromCharCode((c >> 12) | 224);
                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                    utftext += String.fromCharCode((c & 63) | 128);
                }

            }

            return utftext;
        },

        _utf8_decode: function(utftext) {
            var string = "";
            var i = 0;
            var c = c1 = c2 = 0;

            while (i < utftext.length) {

                c = utftext.charCodeAt(i);

                if (c < 128) {
                    string += String.fromCharCode(c);
                    i++;
                }
                else if ((c > 191) && (c < 224)) {
                    c2 = utftext.charCodeAt(i + 1);
                    string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                    i += 2;
                }
                else {
                    c2 = utftext.charCodeAt(i + 1);
                    c3 = utftext.charCodeAt(i + 2);
                    string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                    i += 3;
                }

            }

            return string;
        }
    }

	var script = 'fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n';
	var metadata = '{"translation":{"x":0.0,"y":0.0,"z":1.0,"w":0.0},"rotation":{"x":0.0,"y":0.0,"z":0.0,"w":0.0},"scale":{"x":1.0,"y":1.0,"z":1.0,"w":1.0},"point":{"x":0.0,"y":0.0},"julia":false,"options":{"showPreview":false,"showTraps":false,"showOrbit":false,"showPoint":false,"previewOrigin":{"x":0.0,"y":0.0},"previewSize":{"x":0.25,"y":0.25}}}';

	$("#script").val(script);
	$("#metadata").val(metadata);

	var rows = 8;
	var cols = 8;
	var size = 128;
	
    var tasks = [];
    var requests = [];

	$("#form").submit(function() {
		$("#canvas").empty();
		
        var script = $("#script").val();
        var metadata = $("#metadata").val();
        var manifest = "{\"pluginId\":\"Mandelbrot\"}";

        var encodedManifest = encodeURIComponent(Base64.encode(manifest));
        var encodedMetadata = encodeURIComponent(Base64.encode(metadata));
        var encodedScript = encodeURIComponent(Base64.encode(script));

        for (var i = 0; i < tasks.length; i++) {
            clearTimeout(tasks[i]);
        }

        tasks = [];

        for (var i = 0; i < requests.length; i++) {
            requests[i].abort();
        }

        requests = [];

        function worker(url) {
            var request = $.ajax({
                method: "GET",
                url: url,
                dataType: 'json',
                error: function(jqXHR, status, error) {
                },
                success: function(data, status, jqXHR) {
                    $("#canvas").append("<img style='position:absolute; left:" + (data.col * size) + "px; top:" + (data.row * size) + "px;' src='http://localhost:8080/tiles/" + data.task_id + "/image?row=" + data.row + "&col=" + data.col + "'/>");
                },
                beforeSend: function(jqXHR, settings) {
                    jqXHR.url = settings.url;
                    jqXHR.count = 0;
                },
                complete: function(jqXHR, status) {
                    if (status != "success") {
                        jqXHR.count += 1;
                        if (jqXHR.count < 2) {
                            tasks.push(setTimeout(function() { worker(jqXHR.url); }, 1000));
                        }
                    }
                }
            });
            requests.push(request);
        }

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/tiles",
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            data: 'rows=' + rows + '&cols=' + cols + '&size=' + size + '&manifest=' + encodedManifest + '&metadata=' + encodedMetadata + '&script=' + encodedScript,
            error: function(jqXHR, status, error) {
            },
            success: function(data, status, jqXHR) {
                console.log("Data " + data.task_id);
                for (var row = 0; row < rows; row++) {
                    for (var col = 0; col < cols; col++) {
                        worker('http://localhost:8080/tiles/' + data.task_id + "/state?row=" + row + "&col=" + col);
                    }
                }
            }
        });

		return false;
	});
});
