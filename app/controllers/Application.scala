package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action { request => 
    Ok(views.html.index("Connecting..."))
  }

  def greet = Action { request =>

	val headers  = request.headers.keys.map(k =>k + " : "+ request.headers.get(k))
	val headerResp = headers.mkString("\n")
	Ok("Greetings,\n "+ headerResp  +
	 "\n Would you like to play a game?")
  }

}
