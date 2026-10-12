package payroll.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class PaymentResult(
                          paymentId: String,
                          status: PaymentStatus
                        )

object PaymentResult {
  implicit val decoder: Decoder[PaymentResult] = deriveDecoder
  implicit val encoder: Encoder[PaymentResult] = deriveEncoder
}

sealed trait PaymentStatus
object PaymentStatus {
  case object Pending             extends PaymentStatus
  case object Sent                extends PaymentStatus
  case object Completed           extends PaymentStatus
  case object Failed              extends PaymentStatus

  implicit val decoder: Decoder[PaymentStatus] = deriveDecoder
  implicit val encoder: Encoder[PaymentStatus] = deriveEncoder
}