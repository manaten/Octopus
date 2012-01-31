var start = Date.now();

var obj = { prop: 1 };
for (var i = 0; i < 20000; i++) {
	obj.prop = obj.prop + 1;
}

var time = Date.now() - start;
console.log(obj.prop);
console.log("require " + time + "ms.");