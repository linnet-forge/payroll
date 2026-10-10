package src.model

import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class Payment(id : Long, name : String, category: Category)

final case class Category(id : Long, name : String)

object Payment {
  implicit val decoder : Decoder[Payment] = deriveDecoder
  implicit val encoder : Encoder[Payment] = deriveEncoder
}
object Category {
  implicit val decoder : Decoder[Category] = deriveDecoder
  implicit val encoder : Encoder[Category] = deriveEncoder
}