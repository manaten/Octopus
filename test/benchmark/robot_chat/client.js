//配列からまだ表示してないログを取得し表示
function addLog(logstr) {
	//logのDOM内のHTMLを取得
	var l = $("#log").html();
	//DOMのHTMLを更新
	//$("#log").html(l + logstr);
}
var start = Date.now();
var sendMessage = Octopus.getServer().sendMessage;
for (var i = 0; i < 1000; i++)
	sendMessage( "robo", "hi" + i);
var time = Date.now() - start;
console.log("require " + time + "ms.");
$("#log").html(Octopus.getServer().getAllLog());