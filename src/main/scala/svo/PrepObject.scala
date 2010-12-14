package svo

object PrepObjects extends MultipleFactory[PrepObject, PrepObjects] {
  protected def newMultiple(ss: Set[PrepObject]) = new PrepObjects(ss)
}


class PrepObjects(protected val values: Set[PrepObject] = Set.empty) extends Multiple[PrepObject, PrepObjects] {
  protected def factory = PrepObjects
  def prep: Prepositions = map(_.prep)
  def obj: Nouns = map(_.obj)
}

case class PrepObject(override val prep: Preposition, override val obj: Noun) extends PrepObjects with Single[PrepObject, PrepObjects] {
  override def toString = prep + "-" + obj
  override def equalityValue = (prep, obj)
}
