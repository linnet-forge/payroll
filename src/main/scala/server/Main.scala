package server

import zio._
import zio.http._
import io.circe.syntax._

object Main extends ZIOAppDefault{

  def route = Routes(
    Method.POST / "payment" -> handler {request : Request => Response.json("ok")}
  )

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(route).provide(Server.defaultWithPort(8080))
}
