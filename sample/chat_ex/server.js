var chatLog = [];

exports.sendMessage = function(user, mes) {
	chatLog.push( user + ":" + mes + "<br>" );
	console.log(user+":"+mes);
	exports.updateClients();
};
exports.updateClients = function() {
	var clients = Octopus.getClients();
	for (var i in clients) {
		clients[i].log(chatLog);
	}
};