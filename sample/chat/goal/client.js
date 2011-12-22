
function log(str, k)
{
	var d = document;
	var e = d.get_cps(getElementById, function(r1) {
		var log = e.call_cps(d, "log", function(r2) {
			log.innerHTML.set_cps(str, k);
		});
	});
}
function onSend() {
	var d = document;
	var user = .getElementById("userName").value;
	var mes = document.getElementById("message").value;
	Octopus.getServer().invoke('sendMessage', [user, mes], function(){
		document.getElementById("message").value = "";
	});
}
window.addEventListener("load", function() {
	document.getElementById("chatButton").addEventListener("click", onSend);
	document.getElementById("message").addEventListener("keypress", function(e){ if (e.keyCode == 13) onSend();});
	Octopus.getServer().get_cps('updateClients', function(r1) {

	});
});
