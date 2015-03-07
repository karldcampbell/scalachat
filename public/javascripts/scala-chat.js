var getMessage = function(lastMessageSeen){
	var url = "/message";
	if(lastMessageSeen){
		url += "?lastMessageSeen=" + lastMessageSeen;
	}
	
	$.get(url, function(data){
		if(data){
		var chatBox = $('#chatBox')
		for(var i=data.length-1; i>=0; i--){
			chatBox.prepend('<div class="chatBubble"><b>'+data[i].sender + ":</b> " + data[i].message + "</div>");
		}
		lastMessageSeen = data[0].id
		chatBox.scrollTop(chatBox[0].scrollHeight)
		}
	getMessage(lastMessageSeen);
	});
};

function getSavedName(){
		if (localStorage && localStorage.nick){
			return(localStorage.nick);
		}
		else{
			return("change_me")
		}
	}

$(document).ready(function(){
	getMessage();

	$('#chatForm').submit(function(e){
		e.preventDefault()
		if(localStorage){
			localStorage.nick = $('#userBox')[0].value
		}
		$.post("message", { user: $('#userBox')[0].value, message: $('#msgBox')[0].value});
		$('#msgBox').val('')
	});
	
	$('#userBox').val(getSavedName())
	var i = "test"
});
