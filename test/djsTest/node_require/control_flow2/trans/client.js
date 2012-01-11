var i = 0;
Octopus.__inner__.loop_cps(function(break_0, continue_1) {
  Octopus.__inner__.if_cps(function() {
  return !(i < 10);
}, function(k2) {
  break_0();
}, function(k2) {
  k2();
}, function() {
  Octopus.__inner__.if_cps(function() {
  return i == 3 || i == 7;
}, function(k3) {
  k3();
}, function(k3) {
  Octopus.__inner__.if_cps(function() {
  return i == 4;
}, function(k4) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, console, ["Foooooour!"], function() {
  k4();
});
});
}, function(k4) {
  Octopus.__inner__.if_cps(function() {
  return i == 9;
}, function(k5) {
  break_0();
}, function(k5) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t1) {
  Octopus.__inner__.apply_cps(o_t1, console, [i], function() {
  k5();
});
});
}, function() {
  k4();
});
}, function() {
  k3();
});
}, function() {
  i++;
  continue_1();
});
});
}, function() {
  var j = 0;
  Octopus.__inner__.loop_cps(function(break_6, continue_7) {
  Octopus.__inner__.if_cps(function() {
  return !(j < 10);
}, function(k8) {
  break_6();
}, function(k8) {
  k8();
}, function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t2) {
  Octopus.__inner__.apply_cps(o_t2, console, [j], function() {
  j++;
  continue_7();
});
});
});
}, function() {
  var k = 100;
  Octopus.__inner__.loop_cps(function(break_9, continue_10) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t3, console, [k], function() {
  k++;
  Octopus.__inner__.if_cps(function() {
  return !(k < 10);
}, function(k11) {
  break_9();
}, function(k11) {
  k11();
}, function() {
  continue_10();
});
});
});
}, function() {
});
});
});
