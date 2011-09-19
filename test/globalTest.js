var global = (function() {return this;}).apply( null, [] );
var foo = 10;
function bar() {};
for (e in global)
	console.log(e);
console.log("");
for (e in GLOBAL)
	console.log(e);
console.log("foo" in global);

// mmm...