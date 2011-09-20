var octopus = require('../../node_modules/octopus'),
sys = require('sys');

var port = 8080, clientHtml = __dirname + "/index.html", clientCode = __dirname + "/client.js";

sys.log(clientHtml);

var log = "";
var clientNum=0;
var octServer = octopus.create(clientCode, clientHtml, port);
octServer.on('connection', function(client) {
	sys.log('client connetcted !!!!');
	client.get('mkato6', function(result) {
		result.get('name', function(result2) {
			sys.log(result2);
		});
		client.invoke('getUserAgent', [], function(r) {
			log += '<br />'+(clientNum++)+':'+r;
			for (var i in octServer.clients)
			{
				sys.log('send ');
				octServer.clients[i].invoke('log', [log], null);
			}
		});
	});
});

sys.log('Server running at http://127.0.0.1:' + port + '/');
