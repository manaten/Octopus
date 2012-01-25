//ログのアップデート
function updateLog(str) {
	var log = document.getElementById("log").innerHTML = str;
}
//sendボタンを押した時の処理
function onSend() {
	var data = {};
	data.user = document.getElementById("userName").value;
	data.message = document.getElementById("message").value;
	//サーバーの関数にメッセージ情報を渡す
	Octopus.getServer().sendMessage(data);
	//入力欄をクリア
	document.getElementById("message").value = "";
}
//ロード時にイベントハンドラを設定
window.addEventListener("load", function() {
	//sendボタンを押した時
	document.getElementById("chatButton").addEventListener("click", onSend);
	document.getElementById("message").addEventListener("keypress", function(e){ if (e.keyCode == 13) onSend();});
	//ログを更新する
	Octopus.getServer().updateClients();
});