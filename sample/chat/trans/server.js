var octopus = require('../../../node_modules/octopus'),sys = require('sys');var exports = {};var chatLog = "";
var o_t0 = function(user, mes, return_0) {
  if (return_0 === undefined) 
  {
    return_0 = function() {
};
  }
  chatLog += user + ":" + mes + "<br>";
  console.get_cps('log', function(o_t1) {
  o_t1.apply_cps(console, [user + ":" + mes], function() {
  exports.get_cps('updateClients', function(o_t2) {
  o_t2.apply_cps(exports, [], function() {
  return_0();
});
});
});
});
};
o_t0.isUserDefined = true;
exports.set_cps('sendMessage', o_t0, function() {
  var o_t3 = function(return_1) {
  if (return_1 === undefined) 
  {
    return_1 = function() {
};
  }
  octServer.get_cps('clients', function(o_t4) {
  o_t4.each_cps(function(i, break_2, continue_3) {
  octServer.get_cps('clients', function(o_t6) {
  o_t6.get_cps(i, function(o_t5) {
  o_t5.get_cps('log', function(o_t7) {
  o_t7.apply_cps(o_t5, [chatLog], function() {
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
  exports.set_cps('updateClients', o_t3, function() {
});
});
var port = 8080, clientHtml = __dirname + '/index.html', clientCode = __dirname + '/client.js';var octServer = octopus.create(clientCode, clientHtml, port);octServer.setExports(exports);octServer.on('connection', function(client) {sys.log('client connetcted !!!!');});sys.log('Server running at http://127.0.0.1:' + port + '/');