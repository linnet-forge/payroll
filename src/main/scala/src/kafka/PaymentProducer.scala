package src.kafka

import zio.ZLayer
import zio.kafka.producer.{Producer, ProducerSettings}

object PaymentProducer {
  val producerLayer: ZLayer[Any, Throwable, Producer] =
    ZLayer.scoped(
      Producer.make(ProducerSettings(List("localhost:9092")))
    )
}
