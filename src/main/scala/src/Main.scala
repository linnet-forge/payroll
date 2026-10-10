package src

import io.circe.parser.decode
import io.circe.syntax._
import src.model.Payment
import zio._
import zio.http._
import zio.kafka.consumer._
import zio.kafka.producer._
import zio.kafka.serde.Serde


object Main extends ZIOAppDefault{

  def route: Routes[Producer, Response] = Routes(
    Method.POST / "payment" -> handler { request: Request =>
      val effect: ZIO[Producer, Response, Response] = for {
        body    <- request.body.asString
          .orElseFail(Response.json("fail")) // тут нужен сервис
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

  val consumer= for {
    _ <- Console.printLine("starting consumer").orDie
    consumer <- ZIO.service[Consumer]
    _ <-
      consumer
        .partitionedStream(
          Subscription.topics("payment-result"),
          Serde.string,
          Serde.string
        ).runDrain
        .tap(record => Console.printLine("we have a new record! "))
  }yield()

  val producerLayer: ZLayer[Any, Throwable, Producer] =
    ZLayer.scoped(Producer.make(ProducerSettings(List("26.119.121.217:9092"))))

  val consumerLayer: ZLayer[Any, Throwable, Consumer] =
    ZLayer.scoped(Consumer.make(ConsumerSettings(List("26.119.121.217:9092"))))

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(route).provide(
      Server.defaultWithPort(8080),
      producerLayer
    )<&> consumer

}
