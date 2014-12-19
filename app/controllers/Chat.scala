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

val msgForm = Form(
  mapping(
    "user" -> text,
    "message" -> text
  )(FormMsg.apply)(FormMsg.unapply)
)
def getMessage(lastMessageSeen : Option[Int]) = Action { request =>
	
	try{
		Ok(Json.toJson(Await.result(
			MessageService.getMessages(lastMessageSeen), 55.seconds)));
	}
	catch {
	case e: Exception =>
		MessageService.cancelOne()
		Ok("")
	}
	
}

def sendMessage() = Action { implicit request =>
	val formData = msgForm.bindFromRequest.get
	println(formData.user + " : " + formData.message);
	MessageService.sendMessage(formData.user, formData.message)
	Ok("")
}
}

