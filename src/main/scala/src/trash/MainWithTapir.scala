package src.trash

import src.model.Payment
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.netty.zio._
import sttp.tapir.ztapir.{RichZEndpoint, ZServerEndpoint}
import zio._

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

  val paymentEndpoint: ZServerEndpoint[Any, Any] = endpoint
    .post
    .in("payment")
    .in(jsonBody[Payment])
    .out(stringBody)
    .zServerLogic { payment =>
      for {
        _ <- Console.printLine(s"Handling a request with data $payment")
          .orDie
      response <- ZIO.succeed("Success")
      }yield(response)
    }

  override def run =
    NettyZioServer()
      .port(8080)
      .addEndpoints(List(helloEndpoint,paymentEndpoint)) // тут лист бо в обычной апликухе куча endpoints!
      .start()
      .flatMap{ binding =>
        Console.printLine(s"Server started on port ${binding.port}") *>
          ZIO.never
      }
}