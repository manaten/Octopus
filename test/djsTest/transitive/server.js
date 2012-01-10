exports.serverFunc = function(obj) {
	return obj;
};
exports.serverObj = { value:"hi!" };
exports.getServerObj = function() {
	return exports.serverObj;
};