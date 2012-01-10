
var printClient = function() {
	console.log("client said, Hello!");
};
var printServerFromCilent = function(printServerFunc) {
	printServerFunc();
};
window.addEventListener("load", function() {
	document.getElementById("printClient").addEventListener("click", printClient);
	document.getElementById("printServer").addEventListener("click", Octopus.getServer().printServer);
	document.getElementById("printServerFromClient").addEventListener("click", function(){ printServerFromCilent(Octopus.getServer().printServer) });
});