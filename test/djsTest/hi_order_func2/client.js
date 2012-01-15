window.addEventListener("load", function() {
	document.getElementById("printServerFromClient").addEventListener("click", function(){ 
          Octopus.getServer().getConsole().log("Hage"); });
});
