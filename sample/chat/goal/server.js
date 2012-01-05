var chatLog = "";

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
