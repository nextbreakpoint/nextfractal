$(function() {
    var url = "/api/fractals";

    var map = L.map('fractal').setView([0, 0], 2);

    var regexp = /https?:\/\/.*:.*\/fractals\/(.*)/g;

    var match = regexp.exec(window.location.href);

    var uuid = "00000000-0000-0000-0000-000000000000";

    if (match != null && match.length == 2) {
        uuid = match[1];
    }

    var layer = L.tileLayer(url + '/' + uuid + '/{z}/{x}/{y}', {
        attribution: '&copy; Andrea Medeghini',
        maxZoom: 22,
        tileSize: 256
    });

    layer.addTo(map);

/*	$("#formRender").submit(function(event) {
        event.preventDefault();

        var regexp = /https?:\/\/.*:.*\/fractals\/(.*)/g;

        var match = regexp.exec(window.location.href);

        if (match != null && match.length == 2) {
            layer.setUrl(url + '/' + match[1] + '/{z}/{x}/{y}');
        }

		return false;
	});*/
});
