package io.github.freddiesanchez.chore.models

case class ChoreInput(name:String, description:String, rating: Rating)
case class Chore(id: Int, name:String, description: String, rating: Rating)

