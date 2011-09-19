//This file implements common code between client and server.

var Octopus = {};
try {
	Octopus = exports;
} catch(e){}

(function() {

	var isPrimitive = function(obj) {
		if(obj == null || obj == undefined)
			return true;
		if(obj.constructor ===  Boolean)
			return true;
		if(obj.constructor ===  String)
			return true;
		if(obj.constructor ===  Number)
			return true;
		return false;
	};
	var isFunction = function(obj) {
		if(obj.constructor ===  Function)
			return true;
		return false;
	};

	/**
	 * RemoteObjectManager exists per client or server.
	 *
	 *
	 * @param clientSocket
	 * @param octopusServer
	 * @returns
	 */
	Octopus.RemoteObjectManager = function(socket, exportRoot) {
		this.socket = socket;
		this.objectTable = {};

		var exportedObjects = [];
		exportedObjects[0] = exportRoot;
		var exportId = 1;

		socket.on('octopus_send_message', function (data) {
			var result = undefined;

			try {
				switch (data.type) {
				case 'invoke':
					var reciever = exportedObjects[data.objectId];
					result = reciever[data.prop].apply(reciever, data.args);
					break;
				case 'set':
					var reciever = exportedObjects[data.objectId];
					result = (reciever[data.prop] = data.value);
					break;
				case 'get':
					var reciever = exportedObjects[data.objectId];
					result = reciever[data.prop];
					break;
				}
			} catch(e) {
				console.log("Error!");
				console.log(data);
			};
			var returnMes = {id:data.id};

			//if result is not primitive, value is export to remoteObject
			if (isPrimitive(result)) {
				returnMes.isPrimitive = true;
				returnMes.value = result;
			}
			else {
				returnMes.isPrimitive = false;
				var objectId;
				if (result.__octopus_object_id__ === undefined) {
					objectId = exportId++;
					exportedObjects[objectId] = result;
					result.__octopus_object_id__ = objectId;
				}
				else {
					objectId = result.__octopus_object_id__;
				}
				returnMes.objectId = objectId;
				if (isFunction(result)) {
					//TODO
				}
			}
			//octopus_return_ + id
			socket.emit('octopus_return_'+data.objectId+'_'+data.invocationId, returnMes);
		});
	};
	Octopus.RemoteObjectManager.prototype.getRemoteObject = function(objectId) {
		if (this.objectTable[objectId] !== undefined)
			return this.objectTable[objectId];
		return this.objectTable[objectId] = new RemoteObject(this, objectId);
	};

	/**
	 * A constructor of RemoteObject.
	 * @param clientManager
	 * @param objectId
	 * @returns
	 */
	var RemoteObject = function(manager, objectId) {
		this.manager = manager;
		this.objectId = objectId;
		this.invocationId = 0;
	};
	RemoteObject.prototype.sendMessage = function(msgObj, callback) {
		var manager = this.manager;
		var socket = manager ? manager.socket : null;
		var invocationId = this.invocationId++;
		msgObj.invocationId = invocationId;
		msgObj.objectId = this.objectId;
		socket.emit('octopus_send_message', msgObj);
		socket.on('octopus_return_'+this.objectId+'_'+invocationId, function(data) {
			if (callback)
				callback( data.isPrimitive ? data.value : manager.getRemoteObject(data.objectId) );
		});
	};
	RemoteObject.prototype.get = function(prop, callback) {
		this.sendMessage({type:'get', prop:prop}, callback);
	};
	RemoteObject.prototype.set = function(prop, value, callback) {
		this.sendMessage({type:'set', value:value, prop:prop}, callback);
	};
	RemoteObject.prototype.invoke = function(prop, args, callback) {
		this.sendMessage({type:'invoke', prop:prop, args:args}, callback);
	};
	RemoteObject.prototype.clone = function(callback) {
		//TODO
	};
	RemoteObject.prototype.each = function(callback) {
		//TODO
	};

	/**
	 * A constructor of RemoteFunction.
	 */
	var RemoteFunction = function(clientManager, objectId, codeId, env) {
		this.b = RemoteObject;
		this.b(clientManager, objectId);
		delete this.b;
		//TODO
	};
	RemoteFunction.prototype = new RemoteObject();
//	TODO
	RemoteFunction.prototype.call = function(prop, args, callback) {
		this.sendMessage({type:'call', prop:prop, args:args}, callback);
	};

//	TODO
	var createEnvironment = function(env) {
		if (env) {
			var envProto = {};
			envProto.prototype = env;
			return new envProto();
		}
		return {};
	};
})();
