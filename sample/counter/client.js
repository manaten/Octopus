$(function() {
	var count = Octopus.getServer().getCount();
	$("#counter").text( "Hi! You are " + count + "th visitor !" );
});

