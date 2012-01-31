var start = Date.now();
var clazz = function(){};
clazz.prototype.method = function(n) { return n+1; };
var ins = new clazz();
var result = 1;
for (var i = 0; i < 20000; i++) {
	result = ins.method(result);
}
console.log(result);
var time = Date.now() - start;
console.log("require " + time + "ms.");