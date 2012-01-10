var Octopus = require('octopus'), exports = {};Octopus.__inner__.init(exports, {"basePath":"/Users/mkato6/Dropbox/workspace/Octopus/sample/chat","clientCode":"client.js","outputDir":"/Users/mkato6/Dropbox/workspace/Octopus/sample/chat/trans","port":8080,"serverCode":"server.js","startHtml":"index.html","staticFiles":["index.html"]});
var chatLog = "";
var o_t0 = function(user, mes, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  chatLog += user + ":" + mes + "<br>";
  Octopus.__inner__.get_cps(console, 'log', function(o_t1) {
  Octopus.__inner__.apply_cps(o_t1, console, [user + ":" + mes], function() {
  Octopus.__inner__.get_cps(exports, 'updateClients', function(o_t2) {
  Octopus.__inner__.apply_cps(o_t2, exports, [], function() {
  return_0();
});
});
});
});
};
o_t0.isUserDefined = true;
Octopus.__inner__.set_cps(exports, 'sendMessage', o_t0, function() {
  var o_t3 = function(return_1) {
  if (return_1 === undefined || !(return_1.constructor === Function)) 
  {
    return_1 = function() {
};
  }
  Octopus.__inner__.get_cps(Octopus, 'getClients', function(o_t4) {
  Octopus.__inner__.apply_cps(o_t4, Octopus, [], function(clients) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t5) {
  Octopus.__inner__.apply_cps(o_t5, console, [Octopus], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t6) {
  Octopus.__inner__.apply_cps(o_t6, console, [clients], function() {
  Octopus.__inner__.each_cps(clients, function(i, break_2, continue_3) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t7) {
  Octopus.__inner__.get_cps(clients, i, function(o_t8) {
  Octopus.__inner__.apply_cps(o_t7, console, [o_t8], function() {
  Octopus.__inner__.get_cps(clients, i, function(o_t9) {
  Octopus.__inner__.get_cps(o_t9, 'log', function(o_t10) {
  Octopus.__inner__.apply_cps(o_t10, o_t9, ["bagu no gennin oshieroooo"], function() {
  continue_3();
});
});
});
});
});
});
}, function() {
  return_1();
});
});
});
});
});
});
});
};
  o_t3.isUserDefined = true;
  Octopus.__inner__.set_cps(exports, 'updateClients', o_t3, function() {
});
});
