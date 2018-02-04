package io.github.freddiesanchez.chore.repository

import doobie.imports._
import doobie.util.transactor.Transactor

import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._

import io.github.freddiesanchez.chore.models._

import scala.util.Random

class ChoreRepository(xa: Transactor[IOLite]) {

  private implicit val RatingMeta: Meta[Rating] = Meta[String].xmap(Rating.unsafeFromString, _.rating)

  def run[A](query:ConnectionIO[A]):A = {
    query.transact(xa).unsafePerformIO
  }

  val createChoreTable:ConnectionIO[Int] =
    sql"""CREATE TABLE chore 
        (id SERIAL PRIMARY KEY, 
         name TEXT NOT NULL, 
         description TEXT NOT NULL,
         rating TEXT NOT NULL)""".update.run

  def getChore(id:Long):ConnectionIO[Option[Chore]] = {
    sql"SELECT id, name, description, rating FROM chore WHERE id = ${id}".query[Chore].option
  }

  def dropChoreTable: ConnectionIO[Int]= 
    sql"""DROP TABLE chore""".update.run

  def addChore(newChore:Chore):ConnectionIO[Option[Chore]] = {

    for {
      id <- sql"""INSERT INTO chore (name, description, rating)
                  VALUES (
                    ${newChore.name}, 
                    ${newChore.description}, 
                    ${newChore.rating})""".update.withUniqueGeneratedKeys[Long]("id")
      chore <- getChore(id)
    } yield (chore)
  }

  def updateChore(chore:Chore):ConnectionIO[Option[Chore]] = {
    
    for {
      id <- sql"""UPDATE chore
                  SET name = ${chore.name},
                      description = ${chore.description},
                      rating = ${chore.rating}
                   WHERE id = ${chore.id.get}""".update.run
        chore <- getChore(id)
      } yield (chore)
    }


}
