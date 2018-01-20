package io.github.freddiesanchez.chore

import cats.effect.IO
import org.http4s._
import org.http4s.dsl._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult

class ChoreServerSpec extends org.specs2.mutable.Specification {
  "ChoreServer" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return chore" >> {
      uriReturnsChore()
    }
  }

  private[this] val retChore: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("/chore"))
    ChoreServer.service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retChore.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retChore.as[String].unsafeRunSync() must beEqualTo("{\"message\":\"Hello, world\"}")
}

