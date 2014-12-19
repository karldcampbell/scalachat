package service

import scala.concurrent.Future
import scala.collection.immutable.Queue

import domains._

object WaitingRequestQueue {
	var requests =  Queue[WaitingRequest]();

	def queueRequest(req :WaitingRequest) = {
		this.synchronized{
			requests = requests :+ req;
		}
		println("adding request to queue : " + requests.size);
	}

	def getAll() : Queue[WaitingRequest] = {
		val reqs = requests;
		this.synchronized{
			requests = Queue[WaitingRequest]();
		}
		return(reqs)
	}
	
	def cancelOne() = {
		this.synchronized{
		 	val (a, queue) = requests.dequeue
			requests = queue;
		}
		println("removing request: " + requests.size);
	}
}
