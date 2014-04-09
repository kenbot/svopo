package kenbot.svopo



object AdjectiveNouns extends MultipleFactory[AdjectiveNoun, AdjectiveNouns] {
  protected def newMultiple(ss: Set[AdjectiveNoun]) = new AdjectiveNouns(ss)
}

class AdjectiveNouns(protected val values: Set[AdjectiveNoun] = Set.empty) extends Multiple[AdjectiveNoun, AdjectiveNouns] {
  protected def factory = AdjectiveNouns
  
  val adj: Adjectives = map(_.adj)
  val noun: Nouns = map(_.noun)
}

case class AdjectiveNoun(override val adj: Adjective, override val noun: Noun) extends AdjectiveNouns with Single[AdjectiveNoun, AdjectiveNouns] {
  def v: this.type = this
  override def toString = adj + "+" + noun
  override def equalityValue = (adj, noun)
}

object + {
  
  def unapply(an: AdjectiveNoun): Option[(Adjective, Noun)] = Some((an.adj, an.noun))
  
}