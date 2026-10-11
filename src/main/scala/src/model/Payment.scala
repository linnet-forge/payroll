package src.model

import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class Payment(
                          paymentId : Long,
                          accountNumber : Long,
                          amount : BigDecimal,
                          currency: String
                        )

object Payment {
  implicit val decoder : Decoder[Payment] = deriveDecoder
  implicit val encoder : Encoder[Payment] = deriveEncoder
}
