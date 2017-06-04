$(function() {
    var map = L.map('fractal').setView([0, 0], 2);

    function getURL() {
        var url = $('#url').val();
        var uuid = $('#uuid').val();
        return url + '/' + uuid + '/{z}/{x}/{y}/256.png';
    }

    var layer = L.tileLayer(getURL(), {
        attribution: '&copy; Andrea Medeghini',
        maxZoom: 22,
        tileSize: 256
    });

    layer.addTo(map);

	$("#formRender").submit(function(event) {
        event.preventDefault();

        layer.setUrl(getURL());

		return false;
	});
});
