var chatLog = "";

exports.sendMessage = function(user, mes) {
	chatLog += user + ":" + mes + "<br>";
	console.log(user+":"+mes);
	exports.updateClients();
};
exports.updateClients = function() {
	var clients = Octopus.getClients();
	console.log(Octopus);
	console.log(clients);
	for (var i in clients) {
		console.log(clients[i]);
		clients[i].log("bagu no gennin oshieroooo");
	}
};