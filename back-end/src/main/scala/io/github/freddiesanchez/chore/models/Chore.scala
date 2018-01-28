package io.github.freddiesanchez.chore.models

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.generic.semiauto._

case class ChoreInput(name:String, description:String, rating: Rating)

object ChoreIntput {
    implicit val ChoreEncoder: Encoder[ChoreInput] = deriveEncoder[ChoreInput]
    implicit val ChoreDecoder: Decoder[ChoreInput] = deriveDecoder[ChoreInput]
}

case class Chore(id: Int, name:String, description: String, rating: Rating)

object Chore {
    implicit val ChoreEncoder: Encoder[Chore] = deriveEncoder[Chore]
    implicit val ChoreDecoder: Decoder[Chore] = deriveDecoder[Chore]
}
