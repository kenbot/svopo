package svo

object SVs extends MultipleFactory[SV, SVs]  {
  protected def newMultiple(ss: Set[SV]) = new SVs(ss)
}

class SVs(protected val values: Set[SV] = Set.empty) extends Multiple[SV, SVs] /*with SVOWithoutObjects */ {
  protected def factory = SVs
  def -(obj: Nouns): SVOs = for (sv <- this; o <- obj) yield sv-o
  def subj: Nouns = map(_.subj)
  def verb: Verbs = map(_.verb)

  // Query for all known objects to this query 
  def -*(implicit u: Universe): Nouns = for (sv <- this; o <- sv-*) yield o
}


case class SV(override val subj: Noun, override val verb: Verb) extends SVs with Single[SV, SVs] /*with SVOWithoutObject*/ {
  def -(obj: Noun) = SimpleSVO(this, obj)

  // Query for all known objects to this query 
  override def -*(implicit u: Universe): Nouns = u select {case `subj`-`verb`-o => o}
  override def toString() = subj + "-" + verb
  override def equalityValue = (subj, verb)
}
