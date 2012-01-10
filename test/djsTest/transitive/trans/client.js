var clientObj = {value: "I'm Client!!"};
var clientFunc = function(obj, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  return_0(obj);
};
clientFunc.isUserDefined = true;
Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, Octopus, [], function(server) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t1) {
  Octopus.__inner__.apply_cps(clientFunc, null, [clientObj], function(o_t2) {
  Octopus.__inner__.apply_cps(o_t1, console, [clientObj === o_t2], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t3) {
  Octopus.__inner__.get_cps(server, 'serverFunc', function(o_t4) {
  Octopus.__inner__.apply_cps(o_t4, server, [clientObj], function(o_t5) {
  Octopus.__inner__.apply_cps(o_t3, console, [clientObj === o_t5], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t6) {
  Octopus.__inner__.get_cps(server, 'serverObj', function(o_t7) {
  Octopus.__inner__.get_cps(server, 'getServerObj', function(o_t8) {
  Octopus.__inner__.apply_cps(o_t8, server, [], function(o_t9) {
  Octopus.__inner__.apply_cps(o_t6, console, [o_t7 === o_t9], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t10) {
  Octopus.__inner__.get_cps(clientObj, 'value', function(o_t11) {
  Octopus.__inner__.apply_cps(o_t10, console, [o_t11], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t12) {
  Octopus.__inner__.get_cps(server, 'serverFunc', function(o_t14) {
  Octopus.__inner__.apply_cps(o_t14, server, [clientObj], function(o_t15) {
  Octopus.__inner__.get_cps(o_t15, 'value', function(o_t13) {
  Octopus.__inner__.apply_cps(o_t12, console, [o_t13], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t16) {
  Octopus.__inner__.get_cps(server, 'getServerObj', function(o_t17) {
  Octopus.__inner__.apply_cps(o_t17, server, [], function(o_t18) {
  Octopus.__inner__.apply_cps(o_t16, console, [o_t18], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t19) {
  Octopus.__inner__.get_cps(server, 'serverObj', function(o_t21) {
  Octopus.__inner__.get_cps(o_t21, 'value', function(o_t20) {
  Octopus.__inner__.apply_cps(o_t19, console, [o_t20], function() {
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
});
