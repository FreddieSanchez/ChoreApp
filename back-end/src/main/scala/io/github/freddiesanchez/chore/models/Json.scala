package io.github.freddiesanchez.chore.models

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.generic.semiauto._

object Json {

    implicit val ChoreEncoder: Encoder[Chore] = deriveEncoder[Chore]
    implicit val ChoreDecoder: Decoder[Chore] = deriveDecoder[Chore]

    implicit val RatingEncoder: Encoder[Rating] = deriveEncoder[Rating]
    implicit val RatingDecorder: Decoder[Rating] = deriveDecoder[Rating]
}
