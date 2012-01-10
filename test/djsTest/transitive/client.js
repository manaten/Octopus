var clientObject = { value:"I'm Client!!" };
function clientFunc(obj) {
	return obj;
}
console.log(clientObject === clientFunc(clientObject) );
console.log( clientObject === Octopus.getServer().serverFunc(clientObject) );
console.log( Octopus.getServer().serverObj === Octopus.getServer().getServerObj() );

console.log(clientObject);
console.log(Octopus.getServer().serverFunc(clientObject).value);
