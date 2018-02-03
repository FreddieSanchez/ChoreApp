package io.github.freddiesanchez.chore

import org.scalatest.FunSuite

import doobie.imports._
import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._

import io.github.freddiesanchez.chore.repository._
import io.github.freddiesanchez.chore.models._

class ChoreRepositoryTest extends FunSuite {

  test("create & get") {

    val xa1: Transactor[IOLite] =
      DriverManagerTransactor[IOLite]( "org.h2.Driver"
        , "jdbc:h2:mem:db"
        , ""
        , ""
        )
    val composed = 
      for {
        _ <- ChoreRepository.createChoreTable 
        id1 <- ChoreRepository.addChore(Chore(None,"name","description", Easy))
        id2 <- ChoreRepository.addChore(Chore(None,"name1","description1", Hard))
        chore <- ChoreRepository.getChore(id1)
      } yield chore

    val chore: Option[Chore] = composed.transact(xa1).unsafePerformIO

    assert( chore match {
        case Some(c) => c.id.get == 1
        case None => false
    })


  }
}
