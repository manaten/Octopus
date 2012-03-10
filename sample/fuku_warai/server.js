//an object has positions.
exports.positions = {
	"eye1": {x:0,y:0},
	"eye2": {x:0,y:0},
	"mouth": {x:0,y:0},
};

//update position.
exports.updatePosition = function(name, x, y) {
	console.log("update "+name+" position: ("+x+", "+y+")");
	exports.positions[name].x = x;
	exports.positions[name].y = y;

	var clients = Octopus.getClients();
	for (var i in clients) {
		clients[i].updatePosition(name, x, y);
	}
}

