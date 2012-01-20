var clientLog = "";
var lastIndex = 0;
function log(logArray)
{
	for (; lastIndex < logArray.length; lastIndex++)
		clientLog += logArray[lastIndex];
	$("#log").html(clientLog);
}
function onSend() {
	Octopus.getServer().sendMessage($("#userName").val(), $("#message").val());
	$("#message").val("");
}
$(window).load(function() {
	$("#chatButton").click(onSend);
	$("#message").keypress( function(e){ if (e.keyCode == 13) onSend();});
	Octopus.getServer().updateClients();
});
