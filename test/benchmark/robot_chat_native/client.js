var socket = io.connect();

socket.on('add_log', function(data) {
	//$("#log").html(data);
});
var start = Date.now();
var i = 0;
var loop = function() {
	if (i < 1000) {
		i++;
		socket.emit('send_message', {user:"robo", message:"hi" + i,id:i} );
		socket.on('send_message_complete_'+i, loop);
	} else {
		var time = Date.now() - start;
		console.log("require " + time + "ms.");
		socket.emit("print_all_log", null);
		socket.on('print_all_log', function(data) {
			$("#log").html(data);
		});
	}
};
loop();