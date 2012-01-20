var clientLog = "";
var lastIndex = 0;
function log(logArray)
{
	for (; lastIndex < logArray.length; lastIndex++)
		clientLog += logArray[lastIndex];
	document.getElementById("log").innerHTML = clientLog;
}
function onSend() {
	var user = document.getElementById("userName").value;
	var mes = document.getElementById("message").value;
	Octopus.getServer().sendMessage(user, mes);
	document.getElementById("message").value = "";
}
window.addEventListener("load", function() {
	document.getElementById("chatButton").addEventListener("click", onSend);
	document.getElementById("message").addEventListener("keypress", function(e){ if (e.keyCode == 13) onSend();});
	Octopus.getServer().updateClients();
});
