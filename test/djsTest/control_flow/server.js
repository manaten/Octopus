exports.serverObj = {};
exports.printObject = function() {
	for (key in exports.serverObj)
		console.log(key, ":", exports.serverObj[key]);
};