package io.github.freddiesanchez.chore.models

sealed trait Rating
case class Easy() extends Rating
case class Medium() extends Rating
case class Hard() extends Rating


