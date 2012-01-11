var sys = require('sys');

sys.log("node.js require() test");

exports.log = function(obj) {
	sys.log(obj);
};
