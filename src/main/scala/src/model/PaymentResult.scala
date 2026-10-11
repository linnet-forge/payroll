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

sealed trait FailureReason
object FailureReason {
  case object InvalidAccount extends FailureReason
  case object AccountClosed extends FailureReason
  case object InsufficientFunds extends FailureReason
  case object BankUnavailable extends FailureReason

  implicit val decoder: Decoder[FailureReason] = deriveDecoder
  implicit val encoder: Encoder[FailureReason] = deriveEncoder
}

sealed trait PaymentStatus
object PaymentStatus {
  case object Pending extends PaymentStatus
  case object Sent extends PaymentStatus
  case object Completed extends PaymentStatus
  case class Failed(reason: FailureReason) extends PaymentStatus

  implicit val decoder: Decoder[PaymentStatus] = deriveDecoder
  implicit val encoder: Encoder[PaymentStatus] = deriveEncoder
}