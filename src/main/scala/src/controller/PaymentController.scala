package src.controller

import src.endpoint.PaymentEndpoint
import src.service.PaymentService
import sttp.tapir.ztapir.{RichZEndpoint, ZServerEndpoint}
import zio.ZIO

object PaymentController {
  val endpoint: ZIO[PaymentService, Nothing, ZServerEndpoint[Any, Any]] =
    ZIO.serviceWith[PaymentService] { service =>
      PaymentEndpoint.paymentEndpoint.zServerLogic { payment =>
        service.process(payment)
      }
    }
}
