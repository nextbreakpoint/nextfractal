$(function() {
	var script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n";
	var metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}";

    var map = L.map('canvas').setView([0, 0], 2);

//    var url = "http://localhost:8080/fractals";
    var url = "https://dg02mphys2.execute-api.eu-west-1.amazonaws.com/live";

    var layer = L.tileLayer(url + '/00000000-0000-0000-0000-000000000000/{z}/{x}/{y}.png', {
        attribution: '&copy; Andrea Medeghini',
        maxZoom: 22,
        tileSize: 128,
        noWrap: true
    });

    layer.addTo(map);

/*    L.marker([0, 0]).addTo(map)
        .bindPopup('A pretty fractal')
        .openPopup();*/

	$("#script").val(script);
	$("#metadata").val(metadata);
	$("#uuid").val("00000000-0000-0000-0000-000000000000");

    $("#formCreate").submit(function(event) {
        event.preventDefault();

        var script = $("#script").val();
        var metadata = $("#metadata").val();
        var manifest = "{\"pluginId\":\"Mandelbrot\"}";

        var data = {
            "manifest": manifest,
            "metadata": metadata,
            "script": script
        };

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                $("#uuid").val(data.uuid);
            }
        });

		return false;
	});

	$("#formRender").submit(function(event) {
        event.preventDefault();

        var uuid = $("#uuid").val();

        layer.setUrl(url + '/' + uuid + '/{z}/{x}/{y}.png');

		return false;
	});
});
