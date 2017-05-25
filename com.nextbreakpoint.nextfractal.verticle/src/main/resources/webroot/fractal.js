$(function() {
//	$("#uuid").val("00000000-0000-0000-0000-000000000000");
	$("#url").val("http://localhost:8080/api/fractals");

    var map = L.map('canvas').setView([0, 0], 2);

    var url = $("#url").val();
    var uuid = $("#uuid").val();

    var layer = L.tileLayer(url + '/' + uuid + '/{z}/{x}/{y}', {
        attribution: '&copy; Andrea Medeghini',
        maxZoom: 22,
        tileSize: 256
    });

    layer.addTo(map);

/*    L.marker([0, 0]).addTo(map)
        .bindPopup('A pretty fractal')
        .openPopup();*/

	$("#formRender").submit(function(event) {
        event.preventDefault();

        var url = $("#url").val();
        var uuid = $("#uuid").val();

        layer.setUrl(url + '/' + uuid + '/{z}/{x}/{y}');

		return false;
	});
});
