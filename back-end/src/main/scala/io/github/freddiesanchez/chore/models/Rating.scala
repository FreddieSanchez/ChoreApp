package io.github.freddiesanchez.chore.models

sealed abstract class Rating(val rating:String)


case object Easy extends Rating("easy")
case object Medium extends Rating("medium")
case object Hard extends Rating("hard")

object Rating {


  def fromEnum(rating:String ) : Option[Rating] = 
    Option(rating) collect {
      case "easy" => Easy
      case "medium" => Medium
      case "hard" => Hard
    }

  def unsafeFromString(str: String) = 
    fromEnum(str).getOrElse(throw doobie.util.invariant.InvalidEnum[Rating](str)) 


}

