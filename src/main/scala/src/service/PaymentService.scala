package src.service

import src.model.Payment
import zio.{Console, IO, Task, ZLayer}

trait PaymentService {
  def process(payment: Payment): IO[Unit, String]
}

object PaymentService {
  val live: ZLayer[Any, Nothing, PaymentService] = ZLayer.succeed(
    new PaymentService {
      def process(payment: Payment): IO[Unit, String] =
        Console.printLine(s"Processing $payment")
          .orDie
          .as("Success")
    }
  )
}
