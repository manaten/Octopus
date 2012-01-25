var chatLog = "";
//サーバーにメッセージを送り,ログに保存
exports.sendMessage = function(data) {
	chatLog += data.user + ":" + data.message + "<br>";
	//ログ更新時に,クライアントのログ表示も更新する.
	exports.updateClients();
};
//すべてのクライアントのログを更新
exports.updateClients = function() {
	//すべての接続中のクライアントのグローバルオブジェクトを保持する配列
	var clients = Octopus.getClients();
	for (var i in clients) {
		//すべてのクライアントに対して,updateLog(chatLog)を実行
		clients[i].updateLog(chatLog);
	}
};