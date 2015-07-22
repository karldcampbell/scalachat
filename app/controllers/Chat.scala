package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import scala.concurrent._
import scala.concurrent.duration._

import domains.Message
import service._

object Chat extends Controller {

implicit val messageWrites = new Writes[Message]{
	def writes(msg: Message) = Json.obj(
		"id" -> msg.id,
		"sender" -> msg.sender,
		"message" -> msg.content
	) 

}
case class FormMsg(user: String, message :String);

def getMessage(lastMessageSeen : Option[Int]) = Action { request =>
	
	try{
		Ok(Json.toJson(Await.result(
			MessageService.getMessages(lastMessageSeen), 60.seconds)));
	}
	catch {
	case e: Exception =>
		MessageService.cancelOne()
		Ok("")
	}
	
}

def sendMessage() = Action(parse.json) { implicit request =>
	val user = (request.body \ "user").as[String]
	val text = (request.body \ "text").as[String]
	println(user + " : " + text);
	MessageService.sendMessage(user, text)
	Ok("")
}
}

