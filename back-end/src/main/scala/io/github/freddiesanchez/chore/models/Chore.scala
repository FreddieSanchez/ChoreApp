package io.github.freddiesanchez.chore.models

import java.util.UUID

case class Chore(id: Option[Long], name:String, description: String, rating: Rating) 


