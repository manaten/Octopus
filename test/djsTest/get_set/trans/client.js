var clientValue = "im client!";
Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, Octopus, [], function(server) {
  Octopus.__inner__.set_cps(server, 'valueByClient', "hai! client!", function() {
  Octopus.__inner__.get_cps(server, 'log', function(o_t1) {
  Octopus.__inner__.apply_cps(o_t1, server, [window], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t2) {
  Octopus.__inner__.get_cps(server, 'serverValue', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t2, console, [o_t3], function() {
});
});
});
});
});
});
});
});
