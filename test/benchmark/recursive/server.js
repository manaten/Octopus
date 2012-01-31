var start = Date.now();

var fib = function(n) {
	if (n == 0 || n == 1) return 1;
	return n+fib(n-1);
}

console.log(fib(20000));
var time = Date.now() - start;
console.log("require " + time + "ms.");