var counter = 0;
exports.getCount = function() {
	console.log("Visit " + ++counter + "th user at " + (new Date()).toLocaleString());
	return counter;
};