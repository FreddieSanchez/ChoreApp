package io.github.freddiesanchez.chore

import org.scalatest.FunSuite

import doobie.imports._
import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._

import io.github.freddiesanchez.chore.repository._
import io.github.freddiesanchez.chore.models._

class ChoreRepositoryTest extends FunSuite {

  val xa1: Transactor[IOLite] =
    DriverManagerTransactor[IOLite]( "org.h2.Driver"
      , "jdbc:h2:mem:db"
      , ""
      , ""
      )
  test("create & get") {

    val composed = 
      for {
        _ <- ChoreRepository.createChoreTable 
        chore1 <- ChoreRepository.addChore(Chore(None,"name","description", Easy))
      } yield chore1

    val chore: Option[Chore] = composed.transact(xa1).unsafePerformIO

    assert( chore match {
        case Some(c) => c.id.get == 1
        case None => false
    })


  }

  test("create & get wrong") {

    val composed = 
      for {
        _ <- ChoreRepository.createChoreTable 
        choreOption <- ChoreRepository.addChore(Chore(None,"name","description", Easy))
        chore1Option <- ChoreRepository.getChore(choreOption.get.id.get + 1)
      } yield chore1Option

    val chore: Option[Chore] = composed.transact(xa1).unsafePerformIO

    assert( chore match {
        case Some(_) => false
        case None => true 
    })
  }


  test("create & update") {

    val composed = 
      for {
        _ <- ChoreRepository.createChoreTable 
        chore1 <- ChoreRepository.addChore(Chore(None,"name","description", Easy))
        updatedChore <- ChoreRepository.updateChore(Chore(chore1.get.id,"updated_name","updated_description", Hard))
      } yield updatedChore 

    val chore: Option[Chore] = composed.transact(xa1).unsafePerformIO

    assert( chore match {
        case Some(c) => c.name == "updated_name" && c.description == "updated_description" && c.rating == Hard
        case None => false
    })
  }

}
