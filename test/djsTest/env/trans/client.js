var keys = ['Alice', 'Bob', 'Catalina'];
Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, Octopus, [], function(o_t1) {
  Octopus.__inner__.get_cps(o_t1, 'serverObj', function(serverObj) {
  var clientObj = {};
  var i = 0;
  Octopus.__inner__.loop_cps(function(break_0, continue_1) {
  Octopus.__inner__.get_cps(keys, 'length', function(o_t2) {
  Octopus.__inner__.if_cps(function() {
  return !(i < o_t2);
}, function(k2) {
  break_0();
}, function(k2) {
  k2();
}, function() {
  Octopus.__inner__.get_cps(keys, i, function(o_t3) {
  Octopus.__inner__.set_cps(serverObj, o_t3, i, function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t4) {
  Octopus.__inner__.apply_cps(o_t4, console, [i], function() {
  i++;
  continue_1();
});
});
});
});
});
});
}, function() {
  Octopus.__inner__.each_cps(serverObj, function(key, break_3, continue_4) {
  Octopus.__inner__.get_cps(serverObj, key, function(o_t5) {
  Octopus.__inner__.set_cps(clientObj, key, o_t5, function() {
  continue_4();
});
});
}, function() {
  Octopus.__inner__.each_cps(clientObj, function(key, break_5, continue_6) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t6) {
  Octopus.__inner__.get_cps(clientObj, key, function(o_t7) {
  Octopus.__inner__.apply_cps(o_t6, console, [key, ":", o_t7], function() {
  continue_6();
});
});
});
}, function() {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t8) {
  Octopus.__inner__.apply_cps(o_t8, Octopus, [], function(o_t9) {
  Octopus.__inner__.get_cps(o_t9, 'printObject', function(o_t10) {
  Octopus.__inner__.apply_cps(o_t10, o_t9, [], function() {
  Octopus.__inner__.each_cps(clientObj, function(key, break_7, continue_8) {
  Octopus.__inner__.if_cps(function() {
  return key === 'Alice';
}, function(k9) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t11) {
  Octopus.__inner__.apply_cps(o_t11, console, ["I'm Alice!"], function() {
  k9();
});
});
}, function(k9) {
  Octopus.__inner__.if_cps(function() {
  return key === 'Bob';
}, function(k10) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t12) {
  Octopus.__inner__.apply_cps(o_t12, console, ["My name is Bob!"], function() {
  k10();
});
});
}, function(k10) {
  k10();
}, function() {
  k9();
});
}, function() {
  Octopus.__inner__.if_cps(function() {
  return key === 'Bob';
}, function(k11) {
  break_7();
}, function(k11) {
  k11();
}, function() {
  continue_8();
});
});
}, function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t13) {
  Octopus.__inner__.apply_cps(o_t13, console, ['done.'], function() {
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
