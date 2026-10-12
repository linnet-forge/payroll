import src.controller.PaymentController
import src.service.PaymentService
import sttp.tapir.server.netty.zio.NettyZioServer
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object Main extends ZIOAppDefault {
  override def run =
    (for {
      paymentEndpoint <- PaymentController.endpoint
      _ <- NettyZioServer()
        .port(8080)
        .addEndpoints(List(paymentEndpoint))
        .start()
        .flatMap(_ => ZIO.never)
    } yield ())
      .provide(PaymentService.live)
}