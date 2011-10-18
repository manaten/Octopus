var octopus = require('../../../node_modules/octopus'),
sys = require('sys');


var chatLog = "";

var exports = {};
exports.sendMessage = function(user, mes) {
	chatLog += user + ":" + mes + "<br>";
	console.log(user+":"+mes);
	exports.updateClients();
};
exports.updateClients = function() {
	for (var i in octServer.clients) {
		//TODO available to return Array but String;
		octServer.clients[i].invoke('log', [chatLog], null);
	}
};


var port = 8080, clientHtml = __dirname + "/index.html", clientCode = __dirname + "/client.js";
var octServer = octopus.create(clientCode, clientHtml, port);
octServer.setExports(exports);
octServer.on('connection', function(client) {
	sys.log('client connetcted !!!!');
});
sys.log('Server running at http://127.0.0.1:' + port + '/');
