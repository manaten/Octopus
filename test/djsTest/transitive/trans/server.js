var Octopus = require('octopus'), exports = {};Octopus.__inner__.init(exports, {"basePath":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/transitive","clientCode":"client.js","outputDir":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/transitive/trans","port":8080,"serverCode":"server.js","startHtml":"index.html","staticFiles":["index.html"]});
var o_t0 = function(obj, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  Octopus.__inner__.get_cps(console, 'log', function(o_t1) {
  Octopus.__inner__.apply_cps(o_t1, console, [obj], function() {
  return_0(obj);
});
});
};
o_t0.isUserDefined = true;
Octopus.__inner__.set_cps(exports, 'serverFunc', o_t0, function() {
  Octopus.__inner__.set_cps(exports, 'serverObj', {prop: "hi!"}, function() {
  var o_t2 = function(return_1) {
  if (return_1 === undefined || !(return_1.constructor === Function)) 
  {
    return_1 = function() {
};
  }
  Octopus.__inner__.get_cps(exports, 'serverObj', function(o_t3) {
  return_1(o_t3);
});
};
  o_t2.isUserDefined = true;
  Octopus.__inner__.set_cps(exports, 'getServerObj', o_t2, function() {
});
});
});
