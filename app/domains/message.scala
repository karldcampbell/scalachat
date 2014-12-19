package domains

import play.api.libs.json._


class Message(	val id: Int, 
		val sender: String, 
		val content_in: String){

	val content = process(content_in)

	def process(msg :String) :String = {
		val testIndex = msg.indexOf("://")
		if(testIndex != -1){
			var urlStart = msg.substring(0, testIndex).lastIndexOf(' ')+1;
			urlStart = if(urlStart < 0) 0 else urlStart
			var urlEnd = msg.substring(testIndex).indexOf(' ');
			urlEnd = if(urlEnd < 0) msg.length else (urlEnd + testIndex) 	
			
			val href = msg.substring(urlStart, urlEnd)
			val newMsg = msg.substring(0, urlStart) + "<a href=\"" + href + "\">" + 
				href +"</a>" +  msg.substring(urlEnd)
			println(newMsg)
			return(newMsg)
		}	
	
		return msg
	}

}
