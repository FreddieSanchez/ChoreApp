package io.github.freddiesanchez.chore

import io.github.freddiesanchez.chore.models._

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import io.circe.generic.auto._

import io.circe.syntax._


object ChoreService {
  val chore = Chore(1, "test", "test chore", Easy())
  val service:HttpService = HttpService {
    case GET -> Root / "chore" / id =>  
      Ok(chore.asJson)
  }
}
