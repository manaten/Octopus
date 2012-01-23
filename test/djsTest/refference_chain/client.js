var hoge = Octopus.getServer().hoge = {prop:"hoge"};
var fuga = hoge.fuga = {prop:"fuga"};
var piyo = fuga.piyo = {prop:"piyo"};
console.log(Octopus.getServer().hoge.fuga.piyo.prop);
console.log(Octopus.getServer().getPiyo().prop);