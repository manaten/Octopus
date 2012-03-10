//an array has logs.
var chatLog = [];
//a function sending broadcast message.
exports.sendMessage = function(user, message) {
	var logstr = user + ":" + message + "<br>";

	chatLog.push( logstr );
	console.log(user + " send message: " + message);

	//update all clients with sent message.
	var clients = Octopus.getClients();
	for (var i in clients) {
		clients[i].addLog(logstr);
	}
};

//a list of users.
var userList= {};
//add user to list.
exports.regist = function(user, addLogFunc) {
	userList[user] = addLogFunc;
};
//a function sending one to another message.
exports.sendMessageTo = function(user, message, talkTo) {
	if (userList[user] && userList[talkTo]) {
		userList[user]("(to " + talkTo + ") " + message + "<br>");
		userList[talkTo]("(from " + user + ") " + message + "<br>");
		console.log(user + " send message to" + talkTo + ": " + message);
	}
};
//getting all log saving on server.
exports.getAllLog = function() {
	var log = "";
	for (var i = 0; i < chatLog.length; i++) {
		log += chatLog[i];
	}
	return log;
};
