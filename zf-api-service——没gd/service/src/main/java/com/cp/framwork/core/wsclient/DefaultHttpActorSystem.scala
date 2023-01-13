package com.cp.framwork.core.wsclient

import akka.actor.ActorSystem

object DefaultHttpActorSystem extends HupoExecutorContext {
  lazy val defaultSystem = ActorSystem.apply("hupo-http", None, None, defaultExecutionContext = Option(hupoExecutionContextExecutor))
}
