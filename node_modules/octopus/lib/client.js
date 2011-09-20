(function() {
	socket = io.connect();
	var manager = new Octopus.RemoteObjectManager(socket, window);
	Octopus.getServer = function() {
		return manager.getRemoteObject(0);
	};
})();

