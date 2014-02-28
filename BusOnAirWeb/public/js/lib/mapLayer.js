(function (w, L) {


// create a map in the "map" div, set the view to a given place and zoom
var map = L.map("map", {zoomControl: false});

// create the tile layer with correct attribution
var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib='Map data © OpenStreetMap contributors';
var osm = new L.TileLayer(osmUrl, {minZoom: 8, maxZoom: 20, attribution: osmAttrib});	

// start the map in South-East England
map.setView([42.351, 13.389], 15);
map.addLayer(osm);

map.addControl( L.control.zoom({position: 'topright'}) );


// map.addControl( L.myIcon('icon', {position: 'topright'}) );

var logo = L.control({position: 'bottomright'});

logo.onAdd = function (map) {
var div = L.DomUtil.create('div', 'info legend');
    div.innerHTML +=
    '<img src="img/BusOnAir.png" alt="legend" width="200" height="54">';
return div;
};

// Add this one (only) for now, as the Population layer is on by default
logo.addTo(map);


})(window, window.L);



