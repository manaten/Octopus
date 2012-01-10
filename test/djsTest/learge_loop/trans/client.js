var i = 0;
Octopus.__inner__.loop_cps(function(break_0, continue_1) {
  Octopus.__inner__.if_cps(function() {
  return !(i < 10000);
}, function(k2) {
  break_0();
}, function(k2) {
  k2();
}, function() {
  Octopus.__inner__.if_cps(function() {
  return i % 100 == 0;
}, function(k3) {
  Octopus.__inner__.get_cps(console, 'log', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, console, [i], function() {
  k3();
});
});
}, function(k3) {
  k3();
}, function() {
  i++;
  continue_1();
});
});
}, function() {
});
