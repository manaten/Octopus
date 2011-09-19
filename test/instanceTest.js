var func = function() {};
console.log(Function === func.constructor);
func = new Function("");
console.log(Function === func.constructor);
function func2() {};
console.log(Function === func2.constructor);