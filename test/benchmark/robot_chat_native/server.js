var io = require('socket.io'),
fs = require('fs'),
sys = require('sys'),
http = require('http'),
url = require('url');


var server = http.createServer(function(req, res) {
	var path = url.parse(req.url).pathname;
	if (path == "/octopus/octopus.js") {
		res.writeHead(200, {'Content-Type': 'application/javascript'});
		var rs = fs.createReadStream(__dirname + '/client.js');
		sys.pump(rs, res);
	} else {
		res.writeHead(200, {'Content-Type': 'text/html'});
		var rs = fs.createReadStream(__dirname + '/' + "index.html");
		sys.pump(rs, res);
	}
});
server.listen(8080);

var that = this;
this.io = io.listen(server);
this.io.set( "log level", 1 );

var clients = [];
// On client connected.
this.io.sockets.on('connection', function (clientSocket) {
	clients.push[clientSocket];
	clientSocket.on('send_message', function(data) {
		sendMessage(data.user, data.message);
		clientSocket.emit('send_message_complete_'+data.id, null);
	});
	clientSocket.on('print_all_log', function(data) {
		clientSocket.emit('print_all_log', printAllLog());
	});
});

console.log('Server running at http://pom:' + 8080);


//ログを保持する配列
var chatLog = [];
//サーバーにメッセージを送り,ログに保存
var sendMessage = function(user, message) {
	var logstr = user + ":" + message + "<br>";
	//ログ配列に新しいログをpush
	chatLog.push( logstr );
	//ログ更新時に,クライアントのログ表示も更新する.
	//updateClients(logstr);
};
//現在保持されてるすべてのログを取得
var printAllLog = function() {
	var log = "";
	for (var i = 0; i < chatLog.length; i++) {
		log += chatLog[i];
	}
	return log;
};
//すべてのクライアントのログを更新
var updateClients = function(logstr) {
	//すべての接続中のクライアントのグローバルオブジェクトを保持する配列
	for (var i in clients) {
		clients[i].emit("add_log", logstr);
	}
};