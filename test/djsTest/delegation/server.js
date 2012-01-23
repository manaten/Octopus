exports.serverClass = function(prop) {
	this.prop = prop;
};
exports.serverClass.prototype = {
	method: function() { console.log("I'm server prototype method !! prop: ", this.prop); }
};
exports.serverMethod = function() {
	console.log("I'm method decl in  server ! prop: ", this.prop);
};