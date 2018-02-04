package io.github.freddiesanchez.chore

import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter


import io.github.freddiesanchez.chore.models._
import io.github.freddiesanchez.chore.repository._
import io.github.freddiesanchez.chore.service._

import doobie.imports._
import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._
import doobie.util.transactor.Transactor

class ChoreServerTest extends FunSuite with BeforeAndAfter {

  val xa1: Transactor[IOLite] =
    DriverManagerTransactor[IOLite]( "org.h2.Driver"
      , "jdbc:h2:mem:db"
      , ""
      , ""
      )
  val repo = new ChoreRepository(xa1)
  val choreRoutesService = new ChoreRoutes(repo).service

  before {
    val composed = 
      for {
        _ <- repo.createChoreTable 
        chore1 <- repo.addChore(Chore(None,"name","description", Easy))
      } yield chore1

    val chore: Option[Chore] = repo.run(composed)
  }

  after {
    repo.run(repo.dropChoreTable)
  }


  test("Read - GET should return 200") {
    val getRoot = Request(Method.GET, uri("chore/1"))
    val task = choreRoutesService.run(getRoot)
    val response = task.unsafeRun.orNotFound
    assert(response.status==Status.Ok)
 }

  test("Update - PUT should return 200") {

    val task = Request(Method.PUT, uri("chore/12"))
                    .withType(MediaType.`application/json`)
                    .withBody("""{"id":12, "name":"dishes", "description":"test", "rating":{"Hard":{}}}""")
    val request = task.unsafeRun
    val response = choreRoutesService.run(request).unsafeRun.orNotFound

    assert(response.status==Status.Ok)
 }

  test("Create - POST should return 201 - Created") {
    val task = Request(Method.POST, uri("chore"))
                    .withType(MediaType.`application/json`)
                    .withBody("""{"name":"dishes", "description":"test", "rating":{"Hard":{}}}""")
    val request = task.unsafeRun
    val response = choreRoutesService.run(request).unsafeRun.orNotFound

    assert(response.status==Status.Created)
 }
}
