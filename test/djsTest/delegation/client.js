var clientClass = function(prop) { this.prop = "I am client class !!"; };
clientClass.prototype = new (Octopus.getServer().serverClass)("I am prototype!");
var clientClass2 = function(prop) { this.prop = prop; };
clientClass2.prototype.method = Octopus.getServer().serverMethod;

var ins1 = new clientClass("");
var ins2 = new clientClass2("I'm client obj !!!");
var ins3 = new (Octopus.getServer().serverClass)("Initialized from client !");

console.log(ins1 instanceof clientClass);
console.log(ins2 instanceof clientClass2);

ins1.method();
ins1.prop = "I'm decl in client!!!";
ins1.method();
ins2.method();
ins3.method();