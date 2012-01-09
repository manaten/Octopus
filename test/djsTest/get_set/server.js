exports.log = function(client) {
	console.log(exports.valueByClient);
	var cv = client.clientValue;
	console.log(cv);
};
exports.serverValue = "I am Server!!!";