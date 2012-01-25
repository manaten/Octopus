//ログを保持する配列
var chatLog = [];
//サーバーにメッセージを送り,ログに保存
exports.sendMessage = function(data) {
	var logstr = data.user + ":" + data.message + "<br>";
	//ログ配列に新しいログをpush
	chatLog.push( logstr );
	//ログ更新時に,クライアントのログ表示も更新する.
	exports.updateClients(logstr);
};
//現在保持されてるすべてのログを取得
exports.getAllLog = function() {
	var log = "";
	for (var i = 0; i < chatLog.length; i++) {
		log += chatLog[i];
	}
	return log;
};
//すべてのクライアントのログを更新
exports.updateClients = function(logstr) {
	//すべての接続中のクライアントのグローバルオブジェクトを保持する配列
	var clients = Octopus.getClients();
	for (var i in clients) {
		//すべてのクライアントに対して,updateLog(chatLog)を実行
		clients[i].addLog(logstr);
	}
};