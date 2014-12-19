package service

import scala.concurrent._

import domains._
import service._

object MessageService {
	var messages  = List[Message](new Message(2, "ScalaChat", "You may now change your username."),
			new Message(1, "ScalaChat", "Welcome to ScalaChat!"))
	var nextMessageId = 3;

	def getAllMessages() : List[Message] = {
	return messages.take(20);
	}

	def getMessages(lastMessageSeen: Option[Int]): Future[List[Message]] = {
		val p = Promise[List[Message]]()
		lastMessageSeen match{
		case Some(lastMsg) => 
			val msgList = messages.takeWhile(_.id > lastMsg)
			
			if(msgList.isEmpty){
				val req = new WaitingRequest(lastMsg, p, p.future)
				WaitingRequestQueue.queueRequest(req)	
			}
			else{
				p.success(msgList)
			}
		case None =>
			p.success(getAllMessages())
		}
		return p.future 

	}

	def sendMessage(user: String, message :String) = {
		this.synchronized{
			messages = new Message(nextMessageId, user, message) :: messages;
			nextMessageId += 1;
		}
		for(req <- WaitingRequestQueue.getAll()){
			req.promise.success(messages.takeWhile(_.id > req.lastMessageSeen))
		}
	}

	def cancelOne() = {
		WaitingRequestQueue.cancelOne()
	}
}
