package kenbot.svopo

object POs extends MultipleFactory[PO, POs] {
  protected def newMultiple(ss: Set[PO]) = new POs(ss)
}


class POs(protected val values: Set[PO] = Set.empty) extends Multiple[PO, POs] {
  protected def factory = POs
  def prep: Prepositions = map(_.prep)
  def obj: Nouns = map(_.obj)
}

case class PO(override val prep: Preposition, override val obj: Noun) extends POs with Single[PO, POs] {
  override def toString = prep + "-" + obj
  override def equalityValue = (prep, obj)
}
