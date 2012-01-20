var lastIndex = 0;
function log(logArray) {
	var l = $("#log").html();
	while (lastIndex < logArray.length)
		l += logArray[lastIndex++];
	$("#log").html(l);
}
function onSend() {
	Octopus.getServer().sendMessage($("#userName").val(), $("#message").val());
	$("#message").val("");
}
$(function() {
	$("#chatButton").click(onSend);
	$("#message").keypress( function(e){ if (e.keyCode == 13) onSend();});
	Octopus.getServer().updateClients();
});
