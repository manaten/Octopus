var start = Date.now();

var fib = function(n) {
	if (n == 0 || n == 1) return 1;
	return fib(n-1)+fib(n-2);
}

console.log(fib(30));
var time = Date.now() - start;
console.log("require " + time + "ms.");