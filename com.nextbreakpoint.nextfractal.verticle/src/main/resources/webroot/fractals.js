$(function() {
	var script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n";
	var metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}";

	$("#script").val(script);
	$("#metadata").val(metadata);
	$("#url").val("http://localhost:8080/api/fractals");

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

        var url = $("#url").val();

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

	$("#formDelete").submit(function(event) {
        event.preventDefault();

        var url = $("#url").val();
        var uuid = $("#uuid").val();

        $.ajax({
            type: "DELETE",
            url: url + "/" + uuid
        });

        $("#uuid").val("");

		return false;
	});
});
