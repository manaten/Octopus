var clientClass = function() {};
clientClass.prototype = new Octopus.getServer().serverClasss("I am prototype!");

var clientClass2 = function(prop) { this.prop = prop; };
clientClass2.prototype.method = Octopus.getServer().serverMethod;

var ins1 = new clientClass();
ins1.method();
ins1.prop = "I'm decl in client!!!";
ins1.method();
var ins2 = new clientClass2("I'm client obj !!!");
ins2.method();
var ins3 =  = new Octopus.getServer().serverClasss("Initialized from client !");
ins3.method();