package kenbot.svopo



object Adjectives extends MultipleFactory[Adjective, Adjectives] {
  protected def newMultiple(ss: Set[Adjective]) = new Adjectives(ss)
}

class Adjectives(protected val values: Set[Adjective] = Set.empty) extends Multiple[Adjective, Adjectives] {
  protected def factory = Adjectives
  
  def +(n: Noun): Noun = n
}

case class Adjective(name: String) extends Adjectives with Single[Adjective, Adjectives] with Word {
  def a: this.type = this

  
}