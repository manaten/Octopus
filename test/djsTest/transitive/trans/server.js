var Octopus = require('octopus'), exports = {};Octopus.__inner__.init(exports, {"basePath":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/transitive","clientCode":"client.js","outputDir":"/Users/mkato6/Dropbox/workspace/Octopus/test/djsTest/transitive/trans","port":8080,"serverCode":"server.js","startHtml":"index.html","staticFiles":["index.html"]});
var o_t0 = function(obj, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  return_0(obj);
};
o_t0.isUserDefined = true;
Octopus.__inner__.set_cps(exports, 'serverFunc', o_t0, function() {
  Octopus.__inner__.set_cps(exports, 'serverObj', {prop: "hi!"}, function() {
});
});
