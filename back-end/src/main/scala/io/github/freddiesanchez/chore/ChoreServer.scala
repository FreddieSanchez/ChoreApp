package io.github.freddiesanchez.chore


import fs2.Task
import scala.util.Properties.envOrNone
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

import io.github.freddiesanchez.chore.models._
import io.github.freddiesanchez.chore.service.ChoreRoutes
import io.github.freddiesanchez.chore.repository.ChoreRepository
import doobie.imports._
import doobie.util.transactor.Transactor
import doobie.imports._
import fs2.interop.cats._

object ChoreServer extends StreamApp {
  val port = envOrNone("HTTP_PORT").fold(8080)(_.toInt)


	val xa: Transactor[IOLite] =
		DriverManagerTransactor[IOLite]( "org.h2.Driver"
			, "jdbc:h2:file:./data/test"
			, ""
			, ""
			)

  val repo = new ChoreRepository(xa)
  repo.run(repo.createChoreTable)

  def stream(args: List[String]): fs2.Stream[Task, Nothing] = 
    BlazeBuilder .bindHttp(port)
      .mountService(new ChoreRoutes(repo).service, "/")
      .serve
}

