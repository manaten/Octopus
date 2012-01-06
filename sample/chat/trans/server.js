var Octopus = require('../../../node_modules/octopus'),sys = require('sys');var exports = {};
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
  Octopus.__inner__.get_cps(octServer, 'clients', function(o_t4) {
  Octopus.__inner__.each_cps(o_t4, function(i, break_2, continue_3) {
  Octopus.__inner__.get_cps(octServer, 'clients', function(o_t6) {
  Octopus.__inner__.get_cps(o_t6, i, function(o_t5) {
  Octopus.__inner__.get_cps(o_t5, 'log', function(o_t7) {
  Octopus.__inner__.apply_cps(o_t7, o_t5, [chatLog], function() {
  continue_3();
});
});
});
});
}, function() {
  return_1();
});
});
};
  o_t3.isUserDefined = true;
  Octopus.__inner__.set_cps(exports, 'updateClients', o_t3, function() {
});
});
var port = 8080, clientHtml = __dirname + '/index.html', clientCode = __dirname + '/client.js';var octServer = Octopus.create(clientCode, clientHtml, port);octServer.setExports(exports);octServer.on('connection', function(client) {sys.log('client connetcted !!!!');});sys.log('Server running at http://127.0.0.1:' + port + '/');