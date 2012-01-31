var start = Date.now();

for (var i = 0; i < 20000000; i++) {

}

var time = Date.now() - start;
console.log("require " + time + "ms.");