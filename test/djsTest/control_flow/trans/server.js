var Octopus = require('octopus'), exports = {};Octopus.__inner__.init(exports, {"basePath":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/control_flow","clientCode":"client.js","outputDir":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/control_flow/trans","port":8080,"serverCode":"server.js","startHtml":"index.html","staticFiles":["index.html"]});
Octopus.__inner__.set_cps(exports, 'serverObj', {}, function() {
  var o_t0 = function(return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  Octopus.__inner__.get_cps(exports, 'serverObj', function(o_t1) {
  Octopus.__inner__.each_cps(o_t1, function(key, break_1, continue_2) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t2) {
  Octopus.__inner__.get_cps(exports, 'serverObj', function(o_t4) {
  Octopus.__inner__.get_cps(o_t4, key, function(o_t3) {
  Octopus.__inner__.apply_cps(o_t2, console, [key, ":", o_t3], function() {
  continue_2();
});
});
});
});
}, function() {
  return_0();
});
});
};
  o_t0.isUserDefined = true;
  Octopus.__inner__.set_cps(exports, 'printObject', o_t0, function() {
});
});
