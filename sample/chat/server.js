var chatLog = "";

exports.sendMessage = function(user, mes) {
	chatLog += user + ":" + mes + "<br>";
	console.log(user+":"+mes);
	exports.updateClients();
};
exports.updateClients = function() {
	for (var i in octServer.clients) {
		octServer.clients[i].log(chatLog);
	}
};
