var octopus = require('../../node_modules/octopus'),
sys = require('sys');

var port = 8080, clientHtml = __dirname + "/index.html", clientCode = __dirname + "/client.js";

sys.log(clientHtml);

function mesure(operation, time) {
	var old_time = new Date().getTime();
	for (var i = 0; i < time; i++)
		operation();
	var cur_time = new Date().getTime();
	console.log((cur_time-old_time)+" ms.");
}

var log = "";
var clientNum=0;
var octServer = octopus.create(clientCode, clientHtml, port);
octServer.on('connection', function(client) {

	function A() {
		client.get("name", function(foo) {
			client.get("num", function(bar) {
				return foo+bar;
			});
		});
	}
	function B() {
		client.get("namenum", function(obj) {
			return obj;
		});
	}
	mesure(A, 1000);
	mesure(B, 1000);


});

sys.log('Server running at http://127.0.0.1:' + port + '/');
