var log = function(str, return_0) {
  if (return_0 === undefined || !(return_0.constructor === Function)) 
  {
    return_0 = function() {
};
  }
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t0) {
  Octopus.__inner__.apply_cps(o_t0, document, ["log"], function(log) {
  Octopus.__inner__.set_cps(log, 'innerHTML', str, function() {
  return_0();
});
});
});
};
log.isUserDefined = true;
var onSend = function(return_1) {
  if (return_1 === undefined || !(return_1.constructor === Function)) 
  {
    return_1 = function() {
};
  }
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t1) {
  Octopus.__inner__.apply_cps(o_t1, document, ["userName"], function(o_t2) {
  Octopus.__inner__.get_cps(o_t2, 'value', function(user) {
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t3) {
  Octopus.__inner__.apply_cps(o_t3, document, ["message"], function(o_t4) {
  Octopus.__inner__.get_cps(o_t4, 'value', function(mes) {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t5) {
  Octopus.__inner__.apply_cps(o_t5, Octopus, [], function(o_t6) {
  Octopus.__inner__.get_cps(o_t6, 'sendMessage', function(o_t7) {
  Octopus.__inner__.apply_cps(o_t7, o_t6, [user, mes], function() {
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t8) {
  Octopus.__inner__.apply_cps(o_t8, document, ["message"], function(o_t9) {
  Octopus.__inner__.set_cps(o_t9, 'value', "", function() {
  return_1();
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
onSend.isUserDefined = true;
Octopus.__inner__.get_cps(window, 'addEventListener', function(o_t10) {
  var o_t11 = function(return_2) {
  if (return_2 === undefined || !(return_2.constructor === Function)) 
  {
    return_2 = function() {
};
  }
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t12) {
  Octopus.__inner__.apply_cps(o_t12, document, ["chatButton"], function(o_t13) {
  Octopus.__inner__.get_cps(o_t13, 'addEventListener', function(o_t14) {
  Octopus.__inner__.apply_cps(o_t14, o_t13, ["click", onSend], function() {
  Octopus.__inner__.get_cps(document, 'getElementById', function(o_t15) {
  Octopus.__inner__.apply_cps(o_t15, document, ["message"], function(o_t16) {
  Octopus.__inner__.get_cps(o_t16, 'addEventListener', function(o_t17) {
  var o_t18 = function(e, return_3) {
  if (return_3 === undefined || !(return_3.constructor === Function)) 
  {
    return_3 = function() {
};
  }
  Octopus.__inner__.get_cps(e, 'keyCode', function(o_t19) {
  Octopus.__inner__.if_cps(function() {
  return o_t19 == 13;
}, function(k4) {
  Octopus.__inner__.apply_cps(onSend, null, [], function() {
  k4();
});
}, function(k4) {
  k4();
}, function() {
  return_3();
});
});
};
  o_t18.isUserDefined = true;
  Octopus.__inner__.apply_cps(o_t17, o_t16, ["keypress", o_t18], function() {
  Octopus.__inner__.get_cps(Octopus, 'getServer', function(o_t20) {
  Octopus.__inner__.apply_cps(o_t20, Octopus, [], function(o_t21) {
  Octopus.__inner__.get_cps(o_t21, 'updateClients', function(o_t22) {
  Octopus.__inner__.apply_cps(o_t22, o_t21, [], function() {
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
};
  o_t11.isUserDefined = true;
  Octopus.__inner__.apply_cps(o_t10, window, ["load", o_t11], function() {
});
});
