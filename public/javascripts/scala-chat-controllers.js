
var scalaChat = angular.module("scalaChat")

scalaChat.controller("chatBoxCtlr", function($scope, $http){
	
	$scope.msgs = [];
	var poll = function(lastSeen){	
		$http.get("/message?lastMessageSeen=" + lastSeen)
		.success(function(data){
			var last = lastSeen;
			angular.forEach(data, function(i){ 
				$scope.msgs.push(i); 
				last = Math.max(last,i.id)
			})
			poll(last)
		})
	}

	poll(0);	
});

scalaChat.controller("messageBox", function($scope, $http){
	$scope.msg = {}
	$scope.msg.user = window.localStorage.user || "change_me"
	$scope.msg.text = "Message"
	$scope.send = function(message){
		$http.post('/message', message).success(function(){
			$scope.msg.text = "";
			window.localStorage.user = message.user
		})
	}
})
