//ログを保持する配列
var chatLog = [];
//サーバーにメッセージを送り,ログに保存
exports.sendMessage = function(user, message) {
	var logstr = user + ":" + message + "<br>";
	//ログ配列に新しいログをpush
	chatLog.push( logstr );
	//ログ更新時に,クライアントのログ表示も更新する.
	exports.updateClients(logstr);
};
//現在参加してるユーザー(のaddLog関数)のリスト
var userList= {};
//新しいユーザーを追加
exports.regist = function(user, addLogFunc) {
	userList[user] = addLogFunc;
};
//ユーザーを指定してひそひそ話
//送り手と受け手,それぞれのログに適切な文字列を表示する
exports.sendMessageTo = function(user, message, talkTo) {
	if (userList[user] && userList[talkTo]) {
		userList[user]("(to " + talkTo + ") " + message + "<br>");
		userList[talkTo]("(from " + user + ") " + message + "<br>");
	}
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