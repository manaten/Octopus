//add string to log DOM object.
function addLog(logstr) {
	var l = $("#log").html();
	$("#log").html(l + logstr);
}
//a function when push the send button.
function onSend() {
	//register with user name.
	Octopus.getServer().regist( $("#userName").val(), addLog );
	//whether talkTo field is empty or not, changing send function.
	var talkTo = $("#talkTo").val();
	if (talkTo === "") {
		Octopus.getServer().sendMessage( $("#userName").val(), $("#message").val() );
	} else {
		Octopus.getServer().sendMessageTo( $("#userName").val(), $("#message").val(), talkTo );
	}
	$("#message").val("");
}
//on load function.
$(function() {
	//register event handler.
	$("#chatButton").click(onSend);
	$("#message").keypress( function(e){ if (e.keyCode == 13) onSend();});
	//update log.
	addLog(Octopus.getServer().getAllLog());
});