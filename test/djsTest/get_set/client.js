var clientValue = "im client!";
var server = Octopus.getServer();
server.valueByClient = "hai! client!";
server.log(window);
console.log(server.serverValue);