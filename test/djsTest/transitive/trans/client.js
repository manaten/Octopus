var clientObject = {value: "I'm Client!!"};
var clientFunc = function(obj, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  return_0(obj);
};
clientFunc.isUserDefined = true;
Octopus.__inner__.get_cps(console, 'log', function(o_t0) {
  Octopus.__inner__.apply_cps(clientFunc, null, [clientObject], function(o_t1) {
  Octopus.__inner__.apply_cps(o_t0, console, [clientObject === o_t1], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t2) {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t3, Octopus, [], function(o_t4) {
  Octopus.__inner__.get_cps(o_t4, 'serverFunc', function(o_t5) {
  Octopus.__inner__.apply_cps(o_t5, o_t4, [clientObject], function(o_t6) {
  Octopus.__inner__.apply_cps(o_t2, console, [clientObject === o_t6], function() {
  Octopus.__inner__.get_cps(console, 'log', function(o_t7) {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t9) {
  Octopus.__inner__.apply_cps(o_t9, Octopus, [], function(o_t10) {
  Octopus.__inner__.get_cps(o_t10, 'serverValue', function(o_t8) {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t12) {
  Octopus.__inner__.apply_cps(o_t12, Octopus, [], function(o_t13) {
  Octopus.__inner__.get_cps(o_t13, 'serverValue', function(o_t11) {
  Octopus.__inner__.apply_cps(o_t7, console, [o_t8 === o_t11], function() {
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
