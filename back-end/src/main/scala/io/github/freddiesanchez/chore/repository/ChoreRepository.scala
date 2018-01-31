package io.github.freddiesanchez.chore.repository

import doobie.imports._
import doobie.util.transactor.Transactor

import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._

import io.github.freddiesanchez.chore.models._

import scala.util.Random

class ChoreRepository {

  private implicit val RatingMeta: Meta[Rating] = Meta[String].xmap(Rating.unsafeFromString, _.rating)

	val xa: Transactor[IOLite] =
		DriverManagerTransactor[IOLite]( "org.h2.Driver"
			, "jdbc:h2:mem:db"
			, ""
			, ""
			)

  val createChoreTable =
    sql"""CREATE TABLE chore 
        (id SERIAL PRIMARY KEY, 
         name TEXT NOT NULL, 
         description TEXT NOT NULL,
         rating TEXT NOT NULL)""".update.run

  def getChore(id:Int) = {
    sql"SELECT id, name, description, rating FROM chore WHERE id = ${id}".query[Chore].option.transact(xa)
  }

  def addChore(newChore:ChoreInput) = {
    sql"""INSERT INTO chore 
          VALUES (${Random.nextInt},
                  ${newChore.name}, 
                  ${newChore.description}, 
                  ${newChore.rating})""".update.run
  }

}
