$(function() {
    var url = $('#url').val();

    var map = L.map('fractal').setView([0, 0], 2);

    var uuid = $('#uuid').val();

    var layer = L.tileLayer(url + '/' + uuid + '/{z}/{x}/{y}', {
        attribution: '&copy; Andrea Medeghini',
        maxZoom: 22,
        tileSize: 256
    });

    layer.addTo(map);

	$("#formRender").submit(function(event) {
        event.preventDefault();

        var url = $('#url').val();

        var uuid = $('#uuid').val();

        layer.setUrl(url + '/' + uuid + '/{z}/{x}/{y}');

		return false;
	});
});
