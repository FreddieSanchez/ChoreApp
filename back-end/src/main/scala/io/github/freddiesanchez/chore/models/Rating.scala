package io.github.freddiesanchez.chore.models

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.generic.semiauto._

sealed trait Rating
case class Easy() extends Rating
case class Medium() extends Rating
case class Hard() extends Rating

object Rating {
  implicit val RatingEncoder: Encoder[Rating] = deriveEncoder[Rating]
  implicit val RatingDecorder: Decoder[Rating] = deriveDecoder[Rating]
}

