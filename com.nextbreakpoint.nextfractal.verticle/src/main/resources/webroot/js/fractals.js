$(function() {
    var url = "/api/fractals";
    var urlTarget = "/fractals";

	var script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n";
	var metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}";

	$("#script").val(script);
	$("#metadata").val(metadata);

    function updateRow(source) {
        var uuid = $(source).attr("data");
        if ($('input[data="' + uuid + '"]').attr("checked")) {
            $('input[data="' + uuid + '"]').removeAttr("checked");
        } else {
            $('input[data="' + uuid + '"]').attr("checked", "on");
        }
    }

    window.updateRow = updateRow;

    function updateList(list) {
        $("td.data").remove();

        for (var i = 0; i < list.length; i++) {
            appendLine(list[i]);
        }
    }

    function appendLine(uuid) {
        $("#list").append($('<tr class="data" data="' + uuid + '"><td><input class="filled-in" name="uuid" type="checkbox" data="' + uuid + '"/><label data="' + uuid + '" for="uuid" onClick="updateRow(this)"/></td><td><img class="z-depth-3" width="128" height="128" src="' + url + '/' + uuid + '/0/0/0/256.png"/></td><td><a target="_blank" href="' + urlTarget + '/' + uuid + '">' + uuid + '</a></td></tr>'));
    }

    function removeLine(uuid) {
        $('tr[data="' + uuid + '"]').remove();
    }

    function reloadAll(callback) {
        var token = getCookie('XSRF-TOKEN');
        $.ajax({
            type: "GET",
            url: url,
            headers: {
                "X-XSRF-TOKEN" : token
            },
            dataType: "json",
            success: function(list) {
                callback(list);
            }
        });
    }

    function getCookie(name) {
        var re = new RegExp(name + "=([^;]+)");
        var value = re.exec(document.cookie);
        return (value != null) ? unescape(value[1]) : null;
    }

    function createNew(data, callback) {
        var token = getCookie('XSRF-TOKEN');
        $.ajax({
            type: "POST",
            url: url,
            headers: {
                "X-XSRF-TOKEN" : token
            },
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                callback(data);
            }
        });
    }

    function deleteSingle(uuid, callback) {
        var token = getCookie('XSRF-TOKEN');
        $.ajax({
            type: "DELETE",
            url: url + "/" + uuid,
            headers: {
                "X-XSRF-TOKEN" : token
            },
            success: function() {
                callback(uuid);
            }
        });
    }

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

        createNew(data, function(data) {
            appendLine(data.uuid);
        });

		return false;
	});

	$("#formDelete").submit(function(event) {
        event.preventDefault();

        var uuids = $('input[name="uuid"]:checked');

        for (var i = 0; i < uuids.length; i++) {
            deleteSingle($(uuids[i]).attr('data'), removeLine);
        }

		return false;
	});

    reloadAll(updateList);
});
