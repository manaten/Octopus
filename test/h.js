for (var i = 0; i < 21; i++) {
  var str = "if (length == "+i+") ";
  str += "result = new target(";
  for (var j = 0; j < i; j++) {
    str += "args["+j+"]";
    if (j != i-1) str += ",";
  }
  str += ");"
  console.log(str);
}
