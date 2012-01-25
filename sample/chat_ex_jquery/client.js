//最後に取得したログのインデクス
var lastIndex = 0;
//配列からまだ表示してないログを取得し表示
function updateLog(logArray) {
	//logのDOM内のHTMLを取得
	var l = $("#log").html();
	//まだ表示してないログを追記
	while (lastIndex < logArray.length)
		l += logArray[lastIndex++];
	//DOMのHTMLを更新
	$("#log").html(l);
}
//Sendボタンが押された時の処理
function onSend() {
	//サーバーにユーザー名とメッセージ内容を送信
	Octopus.getServer().sendMessage( {user: $("#userName").val(), message: $("#message").val()} );
	//入力欄をクリア
	$("#message").val("");
}
//ロード時の処理
$(function() {
	//ボタンにハンドラを登録
	$("#chatButton").click(onSend);
	$("#message").keypress( function(e){ if (e.keyCode == 13) onSend();});
	//クライアントのログの更新
	Octopus.getServer().updateClients();
});
