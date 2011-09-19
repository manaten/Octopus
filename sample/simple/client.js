var wakita = {name:"Ken"};
var mkato6 = {name:"Manato", number:"09M37117", boss:wakita};
var functions =
[
	function(_env)
	{
		var env = new Environment(_env);
		env.i = 0;
		env.timer = new Func(1, env);
		return env.timer;
	},
	function(_env)
	{
		var env = new Environment(_env);
		return env.i;
	}
];

var testObj = {};
var getUserAgent = function() { return navigator.userAgent; };
function init()
{
	return testObj;
}
function log(str)
{
	var log = document.getElementById("log");
	log.innerHTML = str;
}