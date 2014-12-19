package domains

import scala.concurrent._

case class WaitingRequest(lastMessageSeen: Int, promise: Promise[List[Message]],
	future: Future[List[Message]])
