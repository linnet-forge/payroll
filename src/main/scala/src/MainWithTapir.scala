package src

import zio._
import sttp.tapir._
import sttp.tapir.server.netty.zio._
import sttp.tapir.ztapir.{RichZEndpoint, ZServerEndpoint}

object MainWithTapir extends ZIOAppDefault {
  // до добавления .zServerLogic у нас это тип Endpoint[SECURITY_INPUT, INPUT, ERROR_OUTPUT, OUTPUT, R]
  // после это уже ServerEndpoint а для нас конкретно
  // еще есть варик напрямую работать с ZIO http, но это хуня

  val helloEndpoint: ZServerEndpoint[Any, Any] = endpoint
    .get
    .in("hello" / "world")
    .in(query[String]("name"))
    .out(stringBody)
    //выше чисто описание, а вот ниже уже конкретное описание на логике ZIO
    .zServerLogic(name => ZIO.succeed(s"Hello $name"))

  override def run =
    NettyZioServer()
      .port(8080)
      .addEndpoints(List(helloEndpoint)) // тут лист бо в обычной апликухе куча endpoints!
      .start()
}