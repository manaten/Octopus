var log = function(str, return_0) {
  if (return_0 === undefined) 
  {
    return_0 = function() {
};
  }
  document.get_cps('getElementById', function(o_t0) {
  o_t0.apply_cps(document, ["log"], function(log) {
  log.set_cps('innerHTML', str, function() {
  return_0();
});
});
});
};
log.isUserDefined = true;
var onSend = function(return_1) {
  if (return_1 === undefined) 
  {
    return_1 = function() {
};
  }
  document.get_cps('getElementById', function(o_t1) {
  o_t1.apply_cps(document, ["userName"], function(o_t2) {
  o_t2.get_cps('value', function(user) {
  document.get_cps('getElementById', function(o_t3) {
  o_t3.apply_cps(document, ["message"], function(o_t4) {
  o_t4.get_cps('value', function(mes) {
  Octopus.get_cps('getServer', function(o_t5) {
  o_t5.apply_cps(Octopus, [], function(o_t6) {
  o_t6.get_cps('sendMessage', function(o_t7) {
  o_t7.apply_cps(o_t6, [user, mes], function() {
  document.get_cps('getElementById', function(o_t8) {
  o_t8.apply_cps(document, ["message"], function(o_t9) {
  o_t9.set_cps('value', "", function() {
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
window.get_cps('addEventListener', function(o_t10) {
  var o_t11 = function(return_2) {
  if (return_2 === undefined) 
  {
    return_2 = function() {
};
  }
  document.get_cps('getElementById', function(o_t12) {
  o_t12.apply_cps(document, ["chatButton"], function(o_t13) {
  o_t13.get_cps('addEventListener', function(o_t14) {
  o_t14.apply_cps(o_t13, ["click", onSend], function() {
  document.get_cps('getElementById', function(o_t15) {
  o_t15.apply_cps(document, ["message"], function(o_t16) {
  o_t16.get_cps('addEventListener', function(o_t17) {
  var o_t18 = function(e, return_3) {
  if (return_3 === undefined) 
  {
    return_3 = function() {
};
  }
  e.get_cps('keyCode', function(o_t19) {
  Octopus.__inner__.if_cps(function() {
  return o_t19 == 13;
}, function(k4) {
  onSend.apply_cps(null, [], function() {
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
  o_t17.apply_cps(o_t16, ["keypress", o_t18], function() {
  Octopus.get_cps('getServer', function(o_t20) {
  o_t20.apply_cps(Octopus, [], function(o_t21) {
  o_t21.get_cps('updateClients', function(o_t22) {
  o_t22.apply_cps(o_t21, [], function() {
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
  o_t10.apply_cps(window, ["load", o_t11], function() {
});
});
