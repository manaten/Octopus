var clientObj = { value:"I'm Client!!" };
function clientFunc(obj) {
	return obj;
}
var server = Octopus.getServer();
console.log(clientObj === clientFunc(clientObj) );
console.log( clientObj === server.serverFunc(clientObj) );
console.log( server.serverObj === server.getServerObj() );

console.log(clientObj.value);
console.log(server.serverFunc(clientObj).value);
console.log(server.getServerObj());
console.log(server.serverObj.value);