function mesure(operation, time) {
	var old_time = new Date().getTime();
	for (var i = 0; i < time; i++)
		operation();
	var cur_time = new Date().getTime();
	console.log((cur_time-old_time)+" ms.");
}

Object.prototype.get = function(prop, k) {
	var self = this;
	k(self[prop]);
};

var obj = { name:"mkato6", num:"09M37117" };
function normalGet() {
	var foo = obj.name;
	var bar = obj.num;
	return foo+bar;
}
function extendGet() {
	obj.get("name", function(foo) {
		obj.get("num", function(bar) {
			return foo+bar;
		});
	});
}

mesure(normalGet, 10000000);
mesure(extendGet, 10000000);
