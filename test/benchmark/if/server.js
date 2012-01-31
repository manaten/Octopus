var start = Date.now();

var result = 1;
for (var i = 0; i < 20000; i++) {
	if (i%2 == 0)
		result = result + 1;
	else
		result = result + 10000;
}
console.log(result);
var time = Date.now() - start;
console.log("require " + time + "ms.");