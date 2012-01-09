var keys = ['Alice', 'Bob', 'Catalina'];
var serverObj = Octopus.getServer().serverObj;
var clientObj = {};

for (var i = 0; i < keys.length; i++) {
	serverObj[keys[i]] = i;
	console.log(i);
}
for (key in serverObj)
	clientObj[key] = serverObj[key];
for (key in clientObj)
	console.log(key, ":", clientObj[key]);
Octopus.getServer().printObject();

for (key in clientObj) {
	if (key === 'Alice')
		console.log("I'm Alice!");
	else if (key === 'Bob')
		console.log("My name is Bob!")
	if (key === 'Bob')
		break;
}
console.log('done.');