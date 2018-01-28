package io.github.freddiesanchez.chore

import org.http4s._
import org.http4s.dsl._
import org.scalatest.FunSuite

class ChoreServerTest extends FunSuite {

  val choreRoutesService = ChoreRoutes.service

  test("GET should return 200") {

    val getRoot = Request(Method.GET, uri("chore/1"))
    val task = choreRoutesService.run(getRoot)
    val response = task.unsafeRun
    println(response)

    //assert(response
    
 }
}
