function log(str)
{
	var log = document.getElementById("log");
	log.innerHTML = str;
}
function onSend() {
	var userDOM = document.getElementById("userName");
	var user = userDOM.value;
	var mesDOM = document.getElementById("message");
	var mes = mesDOM.value;
	var server = Octopus.getServer();
	server.sendMessage(user, mes);
	mesDOM.value = "";
}
window.addEventListener("load", function() {
	document.getElementById("chatButton").addEventListener("click", onSend);
	document.getElementById("message").addEventListener("keypress", function(e){ if (e.keyCode == 13) onSend();});
	Octopus.getServer().updateClients();
});
