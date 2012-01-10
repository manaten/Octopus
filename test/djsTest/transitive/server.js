exports.serverFunc = function(obj) {
	console.log(obj);
	return obj;
};
exports.serverObj = { prop:"hi!" };
exports.getServerObj = function() {
	return exports.serverObj;
};