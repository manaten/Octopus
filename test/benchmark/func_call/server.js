var start = Date.now();
var result = 1;
var f = function(n) {
	return n+1;
}
for (var i = 0; i < 20000; i++) {
	result = f(result);
}
console.log(result);
var time = Date.now() - start;
console.log("require " + time + "ms.");