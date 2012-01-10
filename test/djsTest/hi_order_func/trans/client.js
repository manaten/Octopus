var printClient = function(return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  Octopus.__inner__.get_cps(console, 'log', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, console, ["client said, Hello!"], function() {
  return_0();
});
});
};
printClient.isUserDefined = true;
var printServerFromCilent = function(printServerFunc, return_1) {
  if (return_1 === undefined || !(return_1.constructor === Function)) 
  {
    return_1 = function() {
};
  }
  Octopus.__inner__.apply_cps(printServerFunc, null, [], function() {
  return_1();
});
};
printServerFromCilent.isUserDefined = true;
Octopus.__inner__.get_cps(window, 'addEventListener', function(o_t1) {
  var o_t2 = function(return_2) {
  if (return_2 === undefined || !(return_2.constructor === Function)) 
  {
    return_2 = function() {
};
  }
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t3, document, ["printClient"], function(o_t4) {
  Octopus.__inner__.get_cps(o_t4, 'addEventListener', function(o_t5) {
  Octopus.__inner__.apply_cps(o_t5, o_t4, ["click", printClient], function() {
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t6) {
  Octopus.__inner__.apply_cps(o_t6, document, ["printServer"], function(o_t7) {
  Octopus.__inner__.get_cps(o_t7, 'addEventListener', function(o_t8) {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t10) {
  Octopus.__inner__.apply_cps(o_t10, Octopus, [], function(o_t11) {
  Octopus.__inner__.get_cps(o_t11, 'printServer', function(o_t9) {
  Octopus.__inner__.apply_cps(o_t8, o_t7, ["click", o_t9], function() {
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t12) {
  Octopus.__inner__.apply_cps(o_t12, document, ["printServerFromClient"], function(o_t13) {
  Octopus.__inner__.get_cps(o_t13, 'addEventListener', function(o_t14) {
  var o_t15 = function(return_3) {
  if (return_3 === undefined || !(return_3.constructor === Function)) 
  {
    return_3 = function() {
};
  }
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t17) {
  Octopus.__inner__.apply_cps(o_t17, Octopus, [], function(o_t18) {
  Octopus.__inner__.get_cps(o_t18, 'printServer', function(o_t16) {
  Octopus.__inner__.apply_cps(printServerFromCilent, null, [o_t16], function() {
  return_3();
});
});
});
});
};
  o_t15.isUserDefined = true;
  Octopus.__inner__.apply_cps(o_t14, o_t13, ["click", o_t15], function() {
  return_2();
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
};
  o_t2.isUserDefined = true;
  Octopus.__inner__.apply_cps(o_t1, window, ["load", o_t2], function() {
});
});
