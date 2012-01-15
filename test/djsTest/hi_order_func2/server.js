var count = 0;
exports.printServer = function() {
	console.log("server said, Hello! :" + (count++));
};
exports.getConsole = function() {
  return console;
};
