//配列からまだ表示してないログを取得し表示
function addLog(logstr) {
	//logのDOM内のHTMLを取得
	var l = $("#log").html();
	//DOMのHTMLを更新
	$("#log").html(l + logstr);
}
//Sendボタンが押された時の処理
function onSend() {
	//まず,自分のユーザー名で認証.
	Octopus.getServer().regist( $("#userName").val(), addLog );
	//talkToの有無によって全体発言モードとひそひそ話モードを切り替え
	var talkTo = $("#talkTo").val();
	if (talkTo === "") {
		Octopus.getServer().sendMessage( $("#userName").val(), $("#message").val() );
	} else {
		Octopus.getServer().sendMessageTo( $("#userName").val(), $("#message").val(), talkTo );
	}
	//入力欄をクリア
	$("#message").val("");
}
//ロード時の処理
$(function() {
	//ボタンにハンドラを登録
	$("#chatButton").click(onSend);
	$("#message").keypress( function(e){ if (e.keyCode == 13) onSend();});
	//クライアントのログの更新
	addLog(Octopus.getServer().getAllLog());
});