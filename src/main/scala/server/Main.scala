package server

import zio._
import zio.http._
import io.circe.syntax._
import model.Payment.Payment
import zio.kafka.producer._
import zio.kafka.serde.Serde
import io.circe.parser.decode


object Main extends ZIOAppDefault{

  def route: Routes[Producer, Response] = Routes(
    Method.POST / "payment" -> handler { request: Request =>
      val effect: ZIO[Producer, Response, Response] = for {
        body    <- request.body.asString
          .orElseFail(Response.json("fail"))
        payment <- ZIO.fromEither(decode[Payment](body))
          .orElseFail(Response.json("fail"))
        _       <- Console.printLine(s"Sending payment ${payment.id}")
          .orElseFail(Response.json("fail"))
        _       <- Producer.produce(
          topic           = "payments",
          key             = payment.id.toString,
          value           = payment.asJson.noSpaces,
          keySerializer   = Serde.string,
          valueSerializer = Serde.string
        ).orElseFail(Response.json("fail"))
          _       <- Console.printLine("Sent").orDie
      } yield Response.json("""{"status":"ok"}""")
      effect
    }
  )

  val producerLayer: ZLayer[Any, Throwable, Producer] =
    ZLayer.scoped(Producer.make(ProducerSettings(List("26.119.121.217:9092"))))



  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(route).provide(
      Server.defaultWithPort(8080),
      producerLayer
    )

}
