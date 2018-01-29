package io.github.freddiesanchez.chore

import io.github.freddiesanchez.chore.models._

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.util.Random

object ChoreRoutes {
  val chore = Chore(1, "test", "test chore", Easy())
  val service:HttpService = HttpService {
    /* get the chore by ID */
    /* curl -i -H "Accept: application/json"  -X GET http://127.0.0.1:8080/chore/112412 */
    case GET -> Root / "chore" / IntVar(id) =>  
      Ok(Chore(id, chore.name, chore.description, Easy()).asJson)

    /* update the chore by ID */
    /* curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"id":12, "name":"dishes", "description":"test", "rating":{"Hard":{}}}' -X PUT http://127.0.0.1:8080/chore/12 */
    case req @ PUT -> Root / "chore" / IntVar(id) =>  
      for {
        choreInput <- req.as(jsonOf[Chore])
        resp <- Ok()
      } yield (resp)

    /* create the chore */
    /* curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name":"dishes", "description":"test", "rating":{"Hard":{}}}' -X POST http://127.0.0.1:8080/chore */
    case req @ POST -> Root / "chore"  =>  
      for {
        choreInput <- req.as(jsonOf[ChoreInput])
        resp <- Created(Chore(Random.nextInt, choreInput.name, choreInput.description, choreInput.rating).asJson)
      } yield (resp)

  }
}
