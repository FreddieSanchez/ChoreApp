package io.github.freddiesanchez.chore

import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._

import org.scalatest.FunSuite

class ChoreServerTest extends FunSuite {

  val choreRoutesService = ChoreRoutes.service

  test("Read - GET should return 200") {
    val getRoot = Request(Method.GET, uri("chore/1"))
    val task = choreRoutesService.run(getRoot)
    val response = task.unsafeRun.orNotFound
    assert(response.status==Status.Ok)
 }

  test("Update - PUT should return 200") {

    val task = Request(Method.PUT, uri("chore/1"))
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
