package io.github.freddiesanchez.chore.service

import io.github.freddiesanchez.chore.models._
import io.github.freddiesanchez.chore.repository._
import io.github.freddiesanchez.chore.models.Rating._

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import io.circe.generic.auto._
import io.circe.syntax._

import doobie.imports._
import fs2.interop.cats._
import fs2.Task

import scala.util.Random

class ChoreRoutes(repository: ChoreRepository) {
  //val chore = Chore(Some(1), "test", "test chore", Easy)
  val service:HttpService = HttpService {
    /* get the chore by ID */
    /* curl -i -H "Accept: application/json"  -X GET http://127.0.0.1:8080/chore/112412 */
    case GET -> Root / "chore" / LongVar(id) =>   
    {
      val chore:ConnectionIO[Option[Chore]] = for {
        choreOption <- repository.getChore(id)
      } yield choreOption

     repository.run(chore) match {
        case Some(c) => Ok(c.asJson)
        case None => NotFound()
      }
    }

    /* update the chore by ID */
    /* curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":12, "name":"dishes", "description":"test", "rating":{"Hard":{}}}' -X PUT http://127.0.0.1:8080/chore/12 */
    case req @ PUT -> Root / "chore" / LongVar(id) =>
      for {
        chore <- req.as(jsonOf[Chore])
        choreIOOption = repository.updateChore(chore)
        choreOption   = repository.run(choreIOOption)
        resp <- Created(choreOption.asJson)
      } yield ( resp)

    /* create the chore */
    /* curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name":"dishes", "description":"test", "rating":{"Hard":{}}}' -X POST http://127.0.0.1:8080/chore */
    case req @ POST -> Root / "chore"  =>  
      for {
        choreInput <- req.as(jsonOf[Chore])
        choreIOOption = repository.addChore(choreInput)
        choreOption   = repository.run(choreIOOption)
        resp <- Created(choreOption.asJson)
      } yield (resp)
  }
}
