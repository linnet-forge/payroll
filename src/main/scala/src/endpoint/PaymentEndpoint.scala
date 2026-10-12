package src.endpoint


import src.model.Payment
import sttp.tapir.{Endpoint, endpoint, stringBody}
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody

object PaymentEndpoint {
  val paymentEndpoint: Endpoint[Unit, Payment, Unit, String, Any] = endpoint
    .post
    .in("payment")
    .in(jsonBody[Payment])
    .out(stringBody)
}
