package kenbot.svopo


object Prepositions extends MultipleFactory[Preposition, Prepositions] {
  protected def newMultiple(ss: Set[Preposition]) = new Prepositions(ss)
}

class Prepositions(protected val values: Set[Preposition] = Set.empty) extends Multiple[Preposition, Prepositions] {
  protected def factory = Prepositions
}

case class Preposition(name: String) extends Prepositions with Single[Preposition, Prepositions] with Word {
  def pp: this.type = this
}
