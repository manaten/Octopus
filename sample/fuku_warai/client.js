//update DOM object position.
var updatePosition = function(name, x, y) {
	$("#"+name).css("top", y);
	$("#"+name).css("left", x);
};

$(function() {
	//register event handler and position initialization
	var parts = ["eye1","eye2","mouth"];
	var server = Octopus.getServer();
	for (var i in  parts) {
		var part = parts[i];
		$('#'+part).draggable({
			stop: function(e, ui) {
				server.updatePosition(part, ui.position.left, ui.position.top);
			}
		});
		updatePosition(part, server.positions[part].x, server.positions[part].y);
		$('#'+part).show();
	}
});