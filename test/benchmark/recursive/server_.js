var start = Date.now();

var fib = function(n) {
	if (n == 0 || n == 1) return 1;
	return n+fib(n-1);
}
var result;
for(var i = 0; i < 1000; i++) {
	result += fib(20000);
}

console.log(result);
var time = Date.now() - start;
console.log("require " + time + "ms.");