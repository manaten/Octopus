var Octopus = require('octopus'), exports = {};Octopus.__inner__.init(exports, {"basePath":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/get_set","clientCode":"client.js","outputDir":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/get_set/trans","port":8080,"serverCode":"server.js","startHtml":"index.html","staticFiles":["index.html"]});
var o_t0 = function(client, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  Octopus.__inner__.get_cps(console, 'log', function(o_t1) {
  Octopus.__inner__.get_cps(exports, 'valueByClient', function(o_t2) {
  Octopus.__inner__.apply_cps(o_t1, console, ["::::" + o_t2], function() {
  Octopus.__inner__.get_cps(client, 'clientValue', function(cv) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t3, console, ["::::" + cv], function() {
  return_0();
});
});
});
});
});
});
};
o_t0.isUserDefined = true;
Octopus.__inner__.set_cps(exports, 'log', o_t0, function() {
  Octopus.__inner__.set_cps(exports, 'serverValue', "I am Server!!!", function() {
});
});